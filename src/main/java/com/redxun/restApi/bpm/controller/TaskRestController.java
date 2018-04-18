package com.redxun.restApi.bpm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.QueryFilter;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsUserManager;

@RestController
@RequestMapping("/bpm/task/")
public class TaskRestController {
	@Autowired
	BpmTaskManager bpmTaskManager;
	@Autowired
	OsUserManager osUserManager;
	
	/**
	 * 显示待办列表
	 * @param userAcc
	 * @param request
	 * @return
	 */
	@RequestMapping(value="myTasks")
	@ResponseBody
	public JsonPageResult<BpmTask> myTasks(HttpServletRequest request){
		List<BpmTask> bpmTasks=null;
		String userAcc=request.getParameter("userAcc");
		OsUser osUser=osUserManager.getSingleUserByAccName(userAcc,ITenant.ADMIN_TENANT_ID);
		if(osUser==null){
			return new JsonPageResult<BpmTask>(true,new ArrayList(),0,"get data success!");
		}
		QueryFilter filter=QueryFilterBuilder.createQueryFilter(request);
		
		bpmTasks=bpmTaskManager.getByUserId(osUser.getUserId(),filter);
				
		return new JsonPageResult<BpmTask>(true,bpmTasks,filter.getPage().getTotalItems(),"get data success!");
	}
	
	/**
	 * 获得任务统计数
	 * @param userAcc
	 * @param request
	 * @return
	 */
	@RequestMapping(value="myTaskCount")
	@ResponseBody
	public JsonResult myTaskCounts(HttpServletRequest request){
		String userAcc=request.getParameter("userAcc");
		List<BpmTask> bpmTasks=null;
		OsUser osUser=osUserManager.getSingleUserByAccName(userAcc,ITenant.ADMIN_TENANT_ID);
		if(osUser==null){
			return new JsonResult(true,"success",new Long(0));
		}
		Long counts=bpmTaskManager.getTaskCountsByUserId(osUser.getUserId());
		
		return new JsonResult(true,"get data success!",counts);
	}
}
