package com.redxun.sys.core.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.scheduler.AbstractJob;
import com.redxun.core.scheduler.BaseJob;
import com.redxun.core.scheduler.JobInfo;
import com.redxun.core.scheduler.SchedulerService;
import com.redxun.core.scheduler.TriggerModel;
import com.redxun.core.util.BeanUtil;
import com.redxun.saweb.controller.GenericController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysQuartzLog;
import com.redxun.sys.core.manager.SysQuartzLogManager;

@Controller
@RequestMapping("/sys/core/scheduler/")
public class SchedulerController extends GenericController {

	@Resource
	SchedulerService schedulerService;
	
	@Resource
	SysQuartzLogManager sysQuartzLogManager;

	/**
	 * 添加任务
	 * 
	 * @param response
	 * @param request
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addJob")
	@ResponseBody
	public JsonResult addJob(HttpServletResponse response, HttpServletRequest request) throws Exception {
		JsonResult obj=null;
		try {
			String className = RequestUtil.getString(request, "className");
			String jobName = RequestUtil.getString(request, "name");
			String parameterJson = RequestUtil.getString(request, "parameterJson");
			String description = RequestUtil.getString(request, "description");
			
			boolean rtn = BeanUtil.validClass(className);
			if(!rtn){
				obj = new JsonResult(false, "类名输入错误，请检查!");
				return obj;
			}
			Class cls= Class.forName(className);
			boolean isAssignable= Job.class.isAssignableFrom(cls);
			if(!isAssignable){
				obj = new JsonResult(false, "JOB必须实现JOB接口!");
				return obj;
			}
			
			
			boolean isExist = schedulerService.isJobExists(jobName);
			if (isExist) {
				obj = new JsonResult(false, "任务名称已经存在，添加失败");
			} else {
				schedulerService.addJob(jobName, className, parameterJson, description);
				obj = new JsonResult(true, "添加任务成功");
			}
		} catch (ClassNotFoundException ex) {
			obj = new JsonResult(false, "添加指定的任务类不存在，添加失败");
		}
		return obj;
	}

	/**
	 * 任务列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("jobList")
	public ModelAndView jobList(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		
		boolean isInStandbyMode = schedulerService.isInStandbyMode();
		ModelAndView mv=getPathView(request);
		mv.addObject("isStarted", !isInStandbyMode);
		return mv;
	}
	
	//JobInfo getJobByName(String name) 
	@RequestMapping("jobGet")
	public ModelAndView jobGet(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		String name=RequestUtil.getString(request, "name");
		ModelAndView mv=getPathView(request);
		JobInfo jobInfo=schedulerService.getJobByName(name);
		mv.addObject("job", jobInfo);
		return mv;
	}
	
	/**
	 * 定时器任务列表。
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("jobListJson")
	@ResponseBody
	public List<JobInfo> jobListJson(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		List<JobDetail> list = schedulerService.getJobList();
		List<JobInfo> infoList=new ArrayList<JobInfo>();
		for(JobDetail detail:list){
			JobInfo info=new JobInfo(detail.getKey().getName() ,detail.getJobClass().getName(),detail.getDescription() );
			infoList.add(info);
		}
		return infoList;
	}
	
	
	

	/**
	 * 删除任务
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping("/removeJob")
	@ResponseBody
	public JsonResult delJob(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException, ClassNotFoundException {
		JsonResult message = null;
		try {
			String jobName = RequestUtil.getString(request, "jobName");
			schedulerService.delJob(jobName);
			message = new JsonResult(true, "删除任务成功");
		} catch (Exception e) {
			message = new JsonResult(false, "删除任务失败:" + e.getMessage());
		}
		return message;
	}

	/**
	 * 添加计划
	 * 
	 * @param response
	 * @param request
	 * @param viewName
	 * @return 
	 * @return
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@RequestMapping("/addTrigger")
	@ResponseBody
	public JsonResult addTrigger(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException, ParseException {
		String trigName = RequestUtil.getString(request, "name");
		String jobName = RequestUtil.getString(request, "jobName");
		JsonResult obj = null;
		String planJson = RequestUtil.getString(request, "planJson");
		// 判断触发器是否存在
		boolean rtn = schedulerService.isTriggerExists(trigName);
		if (rtn) {
			 obj = new   JsonResult(false, "指定的计划名称已经存在!");
		}
		try {
			schedulerService.addTrigger(jobName, trigName, planJson);
			 obj = new   JsonResult(true, "添加计划成功");
		} catch (SchedulerException e) {
			 obj = new   JsonResult(false, e.getMessage());
		}
		return obj;
	}

	/**
	 * 计划列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws SchedulerException
	 */
	@RequestMapping("triggerList")
	public ModelAndView triggerList(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {
		String jobName = RequestUtil.getString(request, "jobName");
		ModelAndView mv = getPathView(request);
		mv.addObject("jobName", jobName);
		return mv;
	}
	
	@RequestMapping("jobTriggersJson")
	@ResponseBody
	public List<TriggerModel>   jobTriggersJson(HttpServletResponse response, HttpServletRequest request) throws SchedulerException {

		String jobName = RequestUtil.getString(request, "jobName");

		List<Trigger> list = schedulerService.getTriggersByJob(jobName);
		HashMap<String, Trigger.TriggerState> mapState = schedulerService.getTriggerStatus(list);
		
		List<TriggerModel> modelList=new ArrayList<TriggerModel>();
		
		for(Trigger trigger:list){
			String trigName = trigger.getKey().getName();
			TriggerModel model=new TriggerModel();
			model.setJobName(trigger.getJobKey().getName());
			model.setTriggerName(trigName);
			model.setDescription(trigger.getDescription());
			Trigger.TriggerState state = (Trigger.TriggerState) mapState.get(trigName);
			model.setState(state.name());

			modelList.add(model);
		}
		
		return modelList;
	}
	

	/**
	 * 执行任务
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException
	 * @throws SchedulerException
	 */
	@RequestMapping("runJob")
	@ResponseBody
	public JsonResult runJob(HttpServletRequest request, HttpServletResponse response) throws IOException, SchedulerException {
		JsonResult message = null;
		try{
			String jobName = RequestUtil.getString(request, "jobName");
			schedulerService.executeJob(jobName);
			 message = new JsonResult(true, "执行任务成功!");
		}
		catch(Exception ex){
			 message = new JsonResult(false, "执行任务失败!");
		}
		return message;
		
	
	}

	/**
	 * 验证类
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("validClass")
	@ResponseBody
	public JsonResult validClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String className = RequestUtil.getString(request, "className", "");
		boolean rtn = BeanUtil.validClass(className);
		JsonResult message = null;
		if (rtn) {
			message = new JsonResult(true, "验证类成功!");
		} else {
			message = new JsonResult(false, "验证类失败!");
		}
		return message; 
	}

	/**
	 * 删除触发器
	 * 
	 * @param response
	 * @param request
	 * @return 
	 * @throws IOException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping("/delTrigger")
	@ResponseBody
	public JsonResult delTrigger(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException, ClassNotFoundException {
		JsonResult message = null;
		try {
			String name = RequestUtil.getString(request, "name");
			schedulerService.delTrigger(name);
			message = new JsonResult(true, "删除计划成功");
		} catch (Exception e) {
			message = new JsonResult(false, "删除计划失败:" + e.getMessage());
		}
		return message;
	}

	/**
	 * 启用或禁用
	 * 
	 * @param response
	 * @param request
	 * @return 
	 * @throws IOException
	 * @throws SchedulerException
	 */
	@RequestMapping("/toggleTriggerRun")
	@ResponseBody
	public JsonResult toggleTriggerRun(HttpServletResponse response, HttpServletRequest request) throws IOException, SchedulerException {
		String name = RequestUtil.getString(request, "name");
		JsonResult message = null;
		try {
			schedulerService.toggleTriggerRun(name);
			message = new JsonResult(true, "操作成功");
		} catch (Exception e) {
			message = new JsonResult(false, "操作失败:" + e.getMessage());
		}
		return message;
		
	}

	
	/**
	 * 任务执行日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logListJson")
	@ResponseBody
	public  JsonPageResult logListJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		
		List<SysQuartzLog> list= sysQuartzLogManager.getAll(queryFilter);
		
		JsonPageResult<?> result=new JsonPageResult(list,queryFilter.getPage().getTotalItems());
		
		return result;
		
	}
	
	@RequestMapping("cleanLog")
	@ResponseBody
	public  JsonResult cleanLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JsonResult message = null;
		try {
			sysQuartzLogManager.cleanAll();
			message = new JsonResult(true, "操作成功");
		} catch (Exception e) {
			message = new JsonResult(false, "操作失败:" + e.getMessage());
		}
		return message;
		
		
	}
	
	
	@RequestMapping("del")
	@ResponseBody
	public  JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				sysQuartzLogManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
		
		
	}
	

	
	/**
	 * 修改定时器的状态
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("switchScheduler")
	@ResponseBody
	public JsonResult switchScheduler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult message  = null;
		String resultMsg = "";
		boolean start = RequestUtil.getBoolean(request, "start");
		try {
			// 如果是挂起状态就启动，否则就挂起
			if (start) {
				schedulerService.start();
				resultMsg = "启动定时器成功!";
			} else {
				schedulerService.shutdown(); 
				resultMsg = "停止定时器成功!";
			}
			message = new JsonResult(true, resultMsg);
		} catch (Exception e) {
			e.printStackTrace();
			if (start) {
				resultMsg = "启动定时器失败:";
			} else {
				resultMsg = "停止定时器失败:";
			}
			message = new JsonResult(false, resultMsg + e.getMessage());
		}
		return message;
	}

	
	public static void main(String[] args) {
		System.out.println( Job.class.isAssignableFrom(AbstractJob.class));
	}
	

}
