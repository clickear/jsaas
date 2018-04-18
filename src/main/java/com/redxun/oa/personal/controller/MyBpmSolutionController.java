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
import org.springframework.web.servlet.ModelAndView;

import com.redxun.bpm.activiti.service.ActRepService;
import com.redxun.bpm.bm.manager.BpmFormInstManager;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.identity.service.BpmIdentityCalService;
import com.redxun.bpm.core.manager.BpmDefManager;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmNodeSetManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.bpm.form.manager.BpmFormViewManager;
import com.redxun.bpm.form.manager.FormViewRightManager;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SortParam;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.model.IdentityInfo;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.sys.core.entity.SysTree;
/**
 * 我发起的流程
 * 
 * @author csx
 */
import com.redxun.sys.core.manager.SysTreeManager;
@Controller
@RequestMapping("/oa/personal/MybpmSolution/")
public class MyBpmSolutionController extends TenantListController {
	@Resource
	BpmInstManager bpmInstManager;
	@Resource
	BpmSolutionManager bpmSolutionManager;
	@Resource
	BpmDefManager bpmDefManager;
	@Resource
	ActRepService actRepService;
	@Resource
	BpmFormViewManager bpmFormViewManager;
	@Resource
	BpmFormInstManager bpmFormInstManager;
	@Resource
	BpmNodeSetManager bpmNodeSetManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	FormViewRightManager formViewRightManager;
	
	@Resource
	TaskService taskService;
	@Resource
	UserService userService;
	@Resource
	SysTreeManager sysTreeManager;
	
	@Resource
	RuntimeService runtimeService;
	@Resource
	BpmIdentityCalService bpmIdentityCalService;
	
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter queryFilter = super.getQueryFilter(request);
		queryFilter.getOrderByList().add(
				new SortParam("createTime", SortParam.SORT_DESC));
		queryFilter
				.getFieldLogic()
				.getCommands()
				.add(new QueryParam("createBy", QueryParam.OP_EQUAL,
						ContextUtil.getCurrentUser().getUserId()));
		return queryFilter;
	}
	
	/**
	 * 我的申请 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myApp")
	@ResponseBody
	public JsonPageResult myApp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		QueryFilter filter=getQueryFilter(request);
		List<BpmInst> bpmInstList=bpmInstManager.getAll(filter);
		
		for(BpmInst bpmInst:bpmInstList){
			if(BpmInst.STATUS_RUNNING.equals(bpmInst.getStatus())){
				List<Task> taskList = taskService.createTaskQuery().processInstanceId(bpmInst.getActInstId()).list();
				Set<String> taskUserSet=new HashSet<String>();
				Set<String> taskNodeSet=new HashSet<String>();
				for(Task task:taskList){
					taskNodeSet.add(task.getName());
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
		
		return new JsonPageResult(bpmInstList,filter.getPage().getTotalItems());
	}
	
	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String actInstId = request.getParameter("actInstId");
		BpmInst bpmInst = null;
		ModelAndView mv = getPathView(request);
		if (StringUtils.isNotEmpty(pkId)) {
			bpmInst = bpmInstManager.get(pkId);
		} else if (StringUtils.isNotEmpty(actInstId)) {
			bpmInst = bpmInstManager.getByActInstId(actInstId);
		}
		// 加上业务实例数据
		return mv.addObject("bpmInst", bpmInst);
	}
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		BpmInst bpmInst = null;
		if (StringUtils.isNotEmpty(pkId)) {
			bpmInst = bpmInstManager.get(pkId);
			if ("true".equals(forCopy)) {
				bpmInst.setInstId(null);
			}
		} else {
			bpmInst = new BpmInst();
		}
		return getPathView(request).addObject("bpmInst", bpmInst);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return bpmInstManager;
	}
}
