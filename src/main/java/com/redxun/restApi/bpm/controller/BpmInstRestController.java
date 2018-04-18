package com.redxun.restApi.bpm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmSolution;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.identity.service.BpmIdentityCalService;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.model.IdentityInfo;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsUserManager;

@RestController
@RequestMapping("/bpm/bpmInst/")
public class BpmInstRestController {
	@Autowired
	BpmInstManager bpmInstManager;
	@Autowired
	TaskService taskService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	UserService userService;
	@Autowired
	OsUserManager osUserManager;
	@Autowired
	BpmIdentityCalService bpmIdentityCalService;
	@Autowired
	BpmSolutionManager bpmSolutionManager;
	@Autowired
	SysTreeManager sysTreeManager;
	
	@RequestMapping("getMyAttendCount")
	@ResponseBody
	public JsonResult getMyAttendCount(HttpServletRequest request){
		String userAcc=request.getParameter("userAcc");
		OsUser osUser=osUserManager.getSingleUserByAccName(userAcc,ITenant.ADMIN_TENANT_ID);
		if(osUser==null){
			return new JsonResult(true,"get data success!",0l);
		}
		
		SqlQueryFilter filter=QueryFilterBuilder.createSqlQueryFilter(request);
		
		filter.addFieldParam("userId", osUser.getUserId());
		
		Long counts=bpmInstManager.getMyCheckInstCounts(filter);
		
		return new JsonResult(true,"get data success!",counts);
	}
	
	/**
	 * 返回我已审批的事项实例
	 * @param request
	 * @return
	 */
	@RequestMapping("getMyAttends")
	@ResponseBody
	public JsonPageResult getMyAttends(HttpServletRequest request){
		String userAcc=request.getParameter("userAcc");
		OsUser osUser=osUserManager.getSingleUserByAccName(userAcc,ITenant.ADMIN_TENANT_ID);
		if(osUser==null){
			return new JsonPageResult<BpmTask>(true,new ArrayList(),0,"get data success!");
		}
		
		SqlQueryFilter filter=QueryFilterBuilder.createSqlQueryFilter(request);
		
		filter.addFieldParam("userId", osUser.getUserId());
		
		List<BpmInst> bpmInstList=bpmInstManager.getMyInsts(filter);	
		
		for(BpmInst bpmInst:bpmInstList){
			if(BpmInst.STATUS_RUNNING.equals(bpmInst.getStatus())){
				List<Task> taskList = taskService.createTaskQuery().processInstanceId(bpmInst.getActInstId()).list();
				Set<String> taskUserSet=new HashSet<String>();
				Set<String> taskNodeSet=new HashSet<String>();
				for(Task task:taskList){
					taskNodeSet.add(task.getName());
					if(StringUtils.isNotEmpty(task.getAssignee())){
						IUser user=userService.getByUserId(task.getAssignee());
						if(user!=null){
							taskUserSet.add(user.getFullname());
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
		
		JsonPageResult<BpmInst> result=new JsonPageResult(true,bpmInstList,filter.getPage().getTotalItems(),"get data success!");
		return result;
	}
}
