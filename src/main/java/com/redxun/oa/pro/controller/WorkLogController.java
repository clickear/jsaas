package com.redxun.oa.pro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.PlanTask;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.WorkLog;
import com.redxun.oa.pro.manager.PlanTaskManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ReqMgrManager;
import com.redxun.oa.pro.manager.WorkLogManager;
import com.redxun.oa.pro.manager.ProjectManager;

/**
 * 项目日志管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/workLog/")
public class WorkLogController extends BaseListController{
    @Resource
    WorkLogManager workLogManager;
    @Resource
    PlanTaskManager planTaskManager;
    @Resource
    ProjectManager projectManager;
    @Resource
    ReqMgrManager reqMgrManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    ProWorkMatManager proWorkMatManager;
    @Resource
    InfInnerMsgManager  infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
    
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	ProWorkMat proWorkMat=new ProWorkMat();
                proWorkMat.setActionId(idGenerator.getSID());
                proWorkMat.setType("MYACTION");//设置为该项目的动态的区别符
                proWorkMat.setTypePk(workLogManager.get(id).getLogId());
                proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了日志");
                String context=proWorkMat.getContent();
                List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                		String userId=proWorkAtt.getUserId();
                		infInnerMsgManager.sendMessage(userId, null,context , "", true);
                	}
    			}
                proWorkMatManager.create(proWorkMat);
            	workLogManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        WorkLog workLog=null;
        String plan="";
        if(StringUtils.isNotEmpty(pkId)){
           workLog=workLogManager.get(pkId);
           plan=planTaskManager.get(workLog.getPlanTask().getPlanId()).getSubject();//回填日志隶属的计划的标题
        }else{
        	workLog=new WorkLog();
        }
        return getPathView(request).addObject("workLog",workLog).addObject("plan", plan);	
    }
    	
    /**
     * 编辑和创建
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String planId=request.getParameter("planId");
    	String planTask=request.getParameter("planTask");
    	String planSubject="";
    	WorkLog workLog=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		workLog=workLogManager.get(pkId);
    	}else{
    		workLog=new WorkLog();
    		if(StringUtils.isNotBlank(planId))
    		workLog.setPlan(planTaskManager.get(planId));
    	}
    	if(StringUtils.isNotBlank(planId))
    	planSubject=planTaskManager.get(workLog.getPlanTask().getPlanId()).getSubject();//给edit页面回填计划的标题
    	
    	return getPathView(request).addObject("workLog",workLog).addObject("planSubject", planSubject).addObject("planTask", planTask);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return workLogManager;
	}
	/**
	 * 获取该计划下的日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByProject")
	@ResponseBody
	public JsonPageResult<WorkLog> listByProject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String planId=request.getParameter("planId");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("PlanTask.planId", planId);
		List<WorkLog> list=workLogManager.getAll(queryFilter);//getByPlanId(planId);
		JsonPageResult<WorkLog> result=new JsonPageResult<WorkLog>(list, list.size());
		return result;}

	/**
	 * 返回planId传到前台再传到list1去处理
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletResponse response,HttpServletRequest request)throws Exception{
		String planId=request.getParameter("planId");
		String planTask=request.getParameter("planTask");
		return getPathView(request).addObject("planId", planId).addObject("planTask", planTask);
		}
	
	
	/**
	 * 给新建日志传计划列表(所属人是当前用户的计划)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMyPlan")
	@ResponseBody
	public List<PlanTask> getMyPlan(HttpServletRequest request,HttpServletResponse response)throws Exception{
	    String user=ContextUtil.getCurrentUserId();
	    QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
	    queryFilter.addFieldParam("ownerId", user);
	    List<PlanTask> list=planTaskManager.getAll(queryFilter);//getPlanByMyId(user);
		return list;
	}
	/**
	 * 根据创建人(createBy)与现在登录的账号在查询属于自己的日志
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myTask")
	@ResponseBody
	public JsonPageResult<WorkLog> myTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String userId=ContextUtil.getCurrentUserId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("createBy", userId);
		List<WorkLog> proTasks=workLogManager.getAll(queryFilter);
		JsonPageResult<WorkLog> result=new JsonPageResult(proTasks,queryFilter.getPage().getTotalItems());
		return result;}
	
	
	
	
}
