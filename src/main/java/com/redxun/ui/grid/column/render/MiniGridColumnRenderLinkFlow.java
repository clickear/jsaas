package com.redxun.ui.grid.column.render;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.apache.commons.lang.StringUtils;

import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.identity.service.BpmIdentityCalService;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.model.IdentityInfo;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.core.enums.MiniGridColumnType;

/**
 * 表格列表中流程实例的列的展示
 * @author mansan
 *
 */
public class MiniGridColumnRenderLinkFlow implements MiniGridColumnRender{
	@Resource
	BpmInstManager bpmInstManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	UserService userService;
	@Resource
	RuntimeService runtimeService;
	@Resource
	BpmIdentityCalService bpmIdentityCalService;
	
	@Override
	public String getRenderType() {
		return MiniGridColumnType.LINK_FLOW.name();
	}

	@Override
	public String render(GridHeader gridHeader, Map<String,Object> rowData,Object val, boolean isExport) {
		if(val==null) return "";
		BpmInst bpmInst=bpmInstManager.get(val.toString());
		if(bpmInst==null || StringUtil.isEmpty(val.toString())){
			return "";
		}
		
		String curUserId=ContextUtil.getCurrentUserId();
		String showTitle=gridHeader.getRenderConfObj().getString("showTitle");
		String showBpmInstLink=gridHeader.getRenderConfObj().getString("showBpmInstLink");
		String showTask=gridHeader.getRenderConfObj().getString("showTask");;
		String showTaskHandler=gridHeader.getRenderConfObj().getString("showTaskHandler");
		
		if(!"RUNNING".equals(rowData.get("INST_STATUS_"))){
			if(!isExport && "true".equals(showBpmInstLink)){
				return "<a href=\"javascript:void(0);\" onclick=\"_ShowBpmInstInfo('"+bpmInst.getInstId()+"');\">";
			}
		}
		
		
		StringBuffer sb=new StringBuffer();
		if(!isExport && "true".equals(showBpmInstLink)){
			sb.append("<a href=\"javascript:void(0);\" onclick=\"_ShowBpmInstInfo('"+bpmInst.getInstId()+"');\">");
		}
		if("true".equals(showTitle)){
			sb.append(bpmInst.getSubject());
		}
		if(!isExport && "true".equals(showBpmInstLink)){
			sb.append("</a>");
		}
		
		if("true".equals(showTask)){
			List<BpmTask> bpmTasks=bpmTaskManager.getByActInstId(bpmInst.getActInstId());
			int i=0;
			if("true".equals(showTitle) && bpmTasks.size()>0){
				sb.append("[");
			}
			for(BpmTask task:bpmTasks){
				String taskUsers=null;
				//当前用户是否具有该任务的处理权限
				boolean isCurTask=false;
				if(!isExport && "true".equals(showTaskHandler)){
					if(StringUtils.isNotEmpty(task.getAssignee())){
						IUser iUser=userService.getByUserId(task.getAssignee());
						if(curUserId.equals(iUser.getUserId())){
							isCurTask=true;
						}
						if(iUser!=null){
							taskUsers=iUser.getFullname();
						}
					}else{
						Map<String,Object> variables=runtimeService.getVariables(bpmInst.getActInstId());
						Collection<IdentityInfo> bpmIdenties= bpmIdentityCalService.calNodeUsersOrGroups(bpmInst.getActDefId(), task.getTaskDefKey(), variables);
						KeyValEnt ent= bpmInstManager.getUserInfoIdNames(bpmIdenties);
						if(ent.getKey().indexOf(curUserId)!=-1){
							isCurTask=true;
						}
						taskUsers=ent.getVal().toString();
					}
				}
				
				if(i>0){
					sb.append(",");
				}
				
				if(!isExport && isCurTask){
					sb.append("<a href=\"javascript:void(0)\" onclick=\"_handleTask('"+task.getId()+"')\">");
				}
				
				sb.append(task.getName());
				if(StringUtils.isNotEmpty(taskUsers)){
					sb.append("(").append(taskUsers).append(")");
				}else{
					sb.append("(<span style='color:red'>无审批人</span>)");
				}
				
				if(!isExport && isCurTask){
					sb.append("</a>");
				}
				if("true".equals(showTitle) && bpmTasks.size()>0){
					sb.append("]");
				}
			}
		}
		return sb.toString();
	}

}
