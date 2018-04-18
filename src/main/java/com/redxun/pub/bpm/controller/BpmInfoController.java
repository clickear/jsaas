package com.redxun.pub.bpm.controller;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.model.IdentityInfo;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsUserManager;

@Controller
@RequestMapping("/pub/bpm/")
public class BpmInfoController {
	@Resource 
	TaskService taskService;
	@Resource 
	OsUserManager osUserManager;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmInstManager bpmInstManager;
	
	
	/**
	 * 按实例获得实例及其对应的实例任务列表
	 * @param request
	 * @param respnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTaskInfos")
	@ResponseBody
	public JSONArray getTaskInfos(HttpServletRequest request,HttpServletResponse respnse) throws Exception{
		JSONArray taskInfoMapArr=new JSONArray();
		String actInstIds=request.getParameter("instIds");
		String[] aryInstId=actInstIds.split("[,]");
		String curUserId=ContextUtil.getCurrentUserId();
		
		for(String instId:aryInstId){
			BpmInst inst=bpmInstManager.get(instId);
			String actInstId=inst.getActInstId();
			JSONObject actInfo=new JSONObject();
			JSONArray taskUserInfos=new JSONArray();
			actInfo.put("actInstId", actInstId);
			actInfo.put("instId", inst.getInstId());
			actInfo.put("taskUserInfos", taskUserInfos);
			//获得当前的待办任务
			List<Task> curTasks=taskService.createTaskQuery().processInstanceId(actInstId).list();
			
			for(Task td:curTasks){
				JSONObject taskUserInfo=new JSONObject();
				taskUserInfo.put("nodeId",td.getTaskDefinitionKey());
				taskUserInfo.put("nodeName", td.getName());
				taskUserInfo.put("taskId",td.getId());

				Collection<IdentityInfo> taskIdentites=bpmTaskManager.getTaskHandleIdentityInfos(td.getId());
				StringBuffer userIds=new StringBuffer();
				StringBuffer uNames=new StringBuffer();
				
				//构造执行用户
				for(IdentityInfo info:taskIdentites){
					if(info instanceof IUser){
						//为当前用户，则加上
						if(info.getIdentityId().equals(curUserId)){				
							taskUserInfo.put("curUserTask", true);
						}
						if(userIds.length()>0){
							userIds.append(",");
							uNames.append(",");
						}
						userIds.append(info.getIdentityId());
						uNames.append(info.getIdentityName());
					}else if(info instanceof IGroup){
						List<OsUser> osUsers=osUserManager.getByGroupIdRelTypeId(info.getIdentityId(), OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
						for(OsUser osUser:osUsers){
							if(osUser.getIdentityId().equals(curUserId)){
								taskUserInfo.put("curUserTask", true);
							}
							if(userIds.length()>0){
								userIds.append(",");
								uNames.append(",");
							}
							userIds.append(info.getIdentityId());
							uNames.append(info.getIdentityName());
						}
					}
				}
				taskUserInfo.put("exeUserIds",userIds.toString());
				taskUserInfo.put("exeFullnames",uNames.toString());
				taskUserInfos.add(taskUserInfo);
			}
			taskInfoMapArr.add(actInfo);
		}
		return taskInfoMapArr;
	}
}
