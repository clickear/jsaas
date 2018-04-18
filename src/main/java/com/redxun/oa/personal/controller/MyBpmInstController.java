package com.redxun.oa.personal.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.identity.service.BpmIdentityCalService;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.model.IdentityInfo;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

@Controller
@RequestMapping("/oa/personal/bpmInst/")
public class MyBpmInstController  extends TenantListController{
	@Resource
	BpmInstManager bpmInstManager;
	@Resource
	TaskService taskService;
	@Resource
	UserService userService;
	@Resource
	BpmSolutionManager bpmSolutionManager;
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	RuntimeService runtimeService;
	@Resource
	BpmIdentityCalService bpmIdentityCalService;
	
	@Override
	public BaseManager getBaseManager() {
		return bpmInstManager;
	}
	/**
	 * 我参与的流程实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myAttendsData")
	@ResponseBody
	public JsonPageResult<BpmInst>  myAttendsData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId=ContextUtil.getCurrentUserId();
		SqlQueryFilter filter=QueryFilterBuilder.createSqlQueryFilter(request);
		
		String subject=request.getParameter("subject");
		String billNo=request.getParameter("billNo");
		String createtime1=request.getParameter("createtime1");
		String createtime2=request.getParameter("createtime2");
		String createtime3=request.getParameter("createtime3");
		String createtime4=request.getParameter("createtime4");
		
		if(StringUtils.isNotBlank(subject)){
			filter.addFieldParam("subject","%"+subject+"%");
		}
		if(StringUtils.isNotBlank(billNo)){
			filter.addFieldParam("billNo","%"+billNo+"%");
		}
		if(StringUtils.isNotBlank(createtime1)){
			filter.addFieldParam("createtime1",DateUtil.parseDate(createtime1));
		}
		if(StringUtils.isNotBlank(createtime2)){
			filter.addFieldParam("createtime2",DateUtil.parseDate(createtime2));
		}
		if(StringUtils.isNotBlank(createtime3)){
			filter.addFieldParam("createtime3",DateUtil.parseDate(createtime3));
		}
		if(StringUtils.isNotBlank(createtime4)){
			filter.addFieldParam("createtime4",DateUtil.parseDate(createtime4));
		}
		filter.addFieldParam("userId", userId);
		
		List<BpmInst> bpmInstList=bpmInstManager.getMyInsts(filter);	
		
		for(BpmInst bpmInst:bpmInstList){
			if(BpmInst.STATUS_RUNNING.equals(bpmInst.getStatus())){
				List<Task> taskList = taskService.createTaskQuery().processInstanceId(bpmInst.getActInstId()).list();
				Set<String> taskUserSet=new HashSet<String>();
				Set<String> taskNodeSet=new HashSet<String>();
				for(Task task:taskList){
					taskNodeSet.add(task.getName());
					if(StringUtils.isNotEmpty(task.getAssignee())){
						IUser osUser=userService.getByUserId(task.getAssignee());
						if(osUser!=null){
							taskUserSet.add(osUser.getFullname());
						}
					}else{
						
						Map<String,Object> variables=runtimeService.getVariables(bpmInst.getActInstId());
						Collection<IdentityInfo> bpmIdenties= bpmIdentityCalService.calNodeUsersOrGroups(bpmInst.getActDefId(),task.getTaskDefinitionKey(), variables);
						KeyValEnt ent= bpmInstManager.getUserInfoIdNames(bpmIdenties);
						taskUserSet.add(ent.getVal().toString());
					}
				}
				//当前环节
				bpmInst.setTaskNodes(StringUtil.getCollectionString(taskNodeSet, ","));
				//当前执行人
				bpmInst.setTaskNodeUsers(StringUtil.getCollectionString(taskUserSet, ","));
			}
			//分类
			BpmSolution sol=bpmSolutionManager.get(bpmInst.getSolId());
			if(sol!=null && StringUtils.isNotEmpty(sol.getTreeId())){
				SysTree sysTree=sysTreeManager.get(sol.getTreeId());
				bpmInst.setTreeName(sysTree.getName());
			}
		}
		
		JsonPageResult<BpmInst> result=new JsonPageResult(bpmInstList,filter.getPage().getTotalItems());
		return result;
	}
	
}
