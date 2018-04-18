package com.redxun.oa.pro.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.redxun.oa.info.manager.InfInnerMsgManager;
import com.redxun.oa.pro.entity.PlanTask;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.entity.ProWorkMat;
import com.redxun.oa.pro.entity.Project;
import com.redxun.oa.pro.manager.PlanTaskManager;
import com.redxun.oa.pro.manager.ProWorkAttManager;
import com.redxun.oa.pro.manager.ProWorkMatManager;
import com.redxun.oa.pro.manager.ProjectManager;
import com.redxun.oa.pro.manager.ReqMgrManager;
import com.redxun.oa.pro.manager.WorkLogManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 计划管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/pro/planTask/")
public class PlanTaskController extends BaseListController{
    @Resource
    PlanTaskManager planTaskManager;
    @Resource
    ProjectManager projectManager;
    @Resource
    ProWorkMatManager proWorkMatManager;
    @Resource
    ReqMgrManager reqMgrManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    WorkLogManager workLogManager;
    @Resource
    InfInnerMsgManager infInnerMsgManager;
    @Resource
    ProWorkAttManager proWorkAttManager;
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
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
                if(planTaskManager.get(id).getProject()!=null){//如果是建立项目的需求则
                	 proWorkMat.setTypePk(planTaskManager.get(id).getProject().getProjectId());
                     proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了项目'"+projectManager.get(planTaskManager.get(id).getProject().getProjectId()).getName()+"'的计划:"+planTaskManager.get(id).getSubject());
                     String context=proWorkMat.getContent();
                     List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                     for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                     	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                     		String userId=proWorkAtt.getUserId();
            				infInnerMsgManager.sendMessage(userId, "", context, "", true);
                     	}
         			}
                 }else{
                	 proWorkMat.setTypePk(planTaskManager.get(id).getReqMgr().getReqId());
                     proWorkMat.setContent(osUserManager.get(ContextUtil.getCurrentUserId()).getFullname()+"删除了需求'"+reqMgrManager.get(planTaskManager.get(id).getReqMgr().getReqId()).getSubject()+"'的计划:"+planTaskManager.get(id).getSubject());
                     String context=proWorkMat.getContent();
                     List<ProWorkAtt> proWorkAtts=proWorkAttManager.getAllByTenantId(ContextUtil.getCurrentTenantId());
                     for (ProWorkAtt proWorkAtt : proWorkAtts) {//为所有关注人发送msg
                     	if(proWorkAtt.getTypePk().equals(proWorkMat.getTypePk())){
                     		String userId=proWorkAtt.getUserId();
                     		infInnerMsgManager.sendMessage(userId, "", context, "", true);
                     	}
         			}
                 }
                proWorkMatManager.create(proWorkMat);
                planTaskManager.delete(id);
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
        PlanTask planTask=null;
        String assign="";
    	String exe="";
    	String owner="";
    	String projectName="";
    	String reqSubject="";
    	String tasknum="";

        if(StringUtils.isNotEmpty(pkId)){
           planTask=planTaskManager.get(pkId);
           if(planTask.getProject()!=null)
     		  projectName=projectManager.get(planTask.getProject().getProjectId()).getName();//回显计划的项目名
     		  if(planTask.getReqMgr()!=null)//如果存在计划的需求,则回显需求的标题
     		  {reqSubject=reqMgrManager.get(planTask.getReqMgr().getReqId()).getSubject();}
     		 
         tasknum= String.valueOf(workLogManager.getByPlanId(pkId).size());//日志数量
           if(StringUtils.isNotBlank(planTask.getAssignId()))//回填人的名字
           assign=osUserManager.get(planTask.getAssignId()).getFullname();
           if(StringUtils.isNotBlank(planTask.getExeId()))
   		   exe=osUserManager.get(planTask.getExeId()).getFullname();
           if(StringUtils.isNotBlank(planTask.getOwnerId()))
   		   owner=osUserManager.get(planTask.getOwnerId()).getFullname();
        }else{
        	planTask=new PlanTask();
        }
        return getPathView(request).addObject("planTask",planTask).addObject("exe", exe).addObject("assign", assign).addObject("owner", owner).addObject("projectName", projectName).addObject("reqSubject", reqSubject).addObject("tasknum", tasknum);
    }
    /**
     * 返回projectId和负责人Id(reqId)给PlanList页面以便数据过滤
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String projectId=request.getParameter("projectId");
    	String reqId=request.getParameter("reqId");
		return getPathView(request).addObject("projectId",projectId).addObject("reqId", reqId);
    }
    
    /**
     * 返回reqId(负责人Id)给PlanList页面以便数据过滤
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listByReq")
    public ModelAndView listByReq(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String reqId=request.getParameter("reqId");
		return getPathView(request).addObject("reqId", reqId);
    }
    
    /**
     * 根据页面传来的projectId列出所属项目的计划的list
     * @param request
     * @param response
     * @return result
     * @throws Exception
     */
    @RequestMapping("listProjectPlan")
    @ResponseBody
    public JsonPageResult<PlanTask> listProjectPlan(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String projectId=request.getParameter("projectId");
    	List<PlanTask> list=new ArrayList<PlanTask>();
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("project.projectId", projectId);
    	if(StringUtils.isNotBlank(projectId)){
    		 list=planTaskManager.getAll(queryFilter);
    	}else{
    		list=planTaskManager.getAll();//如果没有projectId就找出所有的plan
    	}
    	
    	 JsonPageResult<PlanTask> result=new JsonPageResult(list,list.size());
			return result;
    	
    }
    /**
     * 根据需求ID(reqId)列出需求的计划的list
     * @param request
     * @param response
     * @return result
     * @throws Exception
     */
    @RequestMapping("listReqPlan")
    @ResponseBody
    public JsonPageResult<PlanTask> listReqPlan(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String reqId=request.getParameter("reqId");
    	List<PlanTask> list=new ArrayList<PlanTask>();
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("reqMgr.reqId", reqId);

    	list=planTaskManager.getAll(queryFilter);
    	JsonPageResult<PlanTask> result=new JsonPageResult(list,list.size());
			return result;
    }
    
    /**
     * 列出我的计划的list
     * @param request
     * @param response
     * @return result
     * @throws Exception
     */
    @RequestMapping("myList")
    @ResponseBody
    public JsonPageResult<PlanTask> myList(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String projectId=request.getParameter("projectId");
    	String userId=ContextUtil.getCurrentUserId();
    	List<PlanTask> list=new ArrayList<PlanTask>();
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("ownerId", userId);
    
    	list=planTaskManager.getAll(queryFilter);
    		//list=planTaskManager.getByUserId(userId);
    	 JsonPageResult<PlanTask> result=new JsonPageResult(list,queryFilter.getPage().getTotalItems());
			return result;
    	
    }
    
    /**
     * 编辑页面,并且给edit页面传入负责人执行人所属人
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String projectId=request.getParameter("projectId");
    	String reqId=request.getParameter("reqId");
    	String assign="";
    	String exe="";
    	String owner="";
    	PlanTask planTask=null;
    	if(StringUtils.isNotEmpty(pkId)){//更新
    		planTask=planTaskManager.get(pkId);
    		   planTask=planTaskManager.get(pkId);
               if(StringUtils.isNotBlank(planTask.getAssignId()))
               assign=osUserManager.get(planTask.getAssignId()).getFullname();
               if(StringUtils.isNotBlank(planTask.getExeId()))
       		   exe=osUserManager.get(planTask.getExeId()).getFullname();
               if(StringUtils.isNotBlank(planTask.getOwnerId()))
       		   owner=osUserManager.get(planTask.getOwnerId()).getFullname();
    	}else{//创建
    		planTask=new PlanTask();
    		if(StringUtils.isNotBlank(projectId)){planTask.setProject(projectManager.get(projectId));}//如果有projectId则设置planTask的project(项目)
    		if(StringUtils.isNotBlank(reqId)){planTask.setReqMgr(reqMgrManager.get(reqId));}//如果有reqId则设置planTask的reqMgr(需求)
    	}
    	return getPathView(request).addObject("planTask",planTask).addObject("assign", assign).addObject("exe", exe).addObject("owner", owner);
    }
    
    /**
     * 我的计划的edit页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("myedit")
    public ModelAndView myEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String assign="";
    	String exe="";
    	String owner="";
    	String projectName="";
    	String reqSubject="";
    	PlanTask planTask=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		planTask=planTaskManager.get(pkId);
    		if(planTask.getProject()!=null)
    		  projectName=projectManager.get(planTask.getProject().getProjectId()).getName();//回显计划的项目名
    		  if(planTask.getReqMgr()!=null)//如果存在计划的需求,则回显需求的标题
    		  {reqSubject=reqMgrManager.get(planTask.getReqMgr().getReqId()).getSubject();}
               if(StringUtils.isNotBlank(planTask.getAssignId()))
               assign=osUserManager.get(planTask.getAssignId()).getFullname();
               if(StringUtils.isNotBlank(planTask.getExeId()))
       		   exe=osUserManager.get(planTask.getExeId()).getFullname();
               if(StringUtils.isNotBlank(planTask.getOwnerId()))
       		   owner=osUserManager.get(planTask.getOwnerId()).getFullname();
    	}else{
    		planTask=new PlanTask();
    	}
    	return getPathView(request).addObject("planTask",planTask).addObject("assign", assign).addObject("exe", exe).addObject("owner", owner).addObject("projectName", projectName).addObject("reqSubject", reqSubject);
    }
    
    
    /**
     * 返回我负责的项目
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getMyPlan")
    @ResponseBody
    public List<Project> getMyPlan(HttpServletRequest request,HttpServletResponse response){
    	List<Project> list=projectManager.getByReporId(ContextUtil.getCurrentUserId());
		return list;}
    
    
    

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return planTaskManager;
	}
	
	
	
	
	@RequestMapping("fullCalender")
	@ResponseBody
	public List fullCalender(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String tenantId=ContextUtil.getCurrentTenantId();
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String  userId=ContextUtil.getCurrentUserId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", tenantId);
		queryFilter.addFieldParam("ownerId", userId);
		
		
		
		List<PlanTask> planTasks=planTaskManager.getAll(queryFilter);//new ArrayList<PlanTask>();
		
		List<Map> list =new ArrayList<Map>(); 
		//Date date=new Date();
		for (PlanTask planTask : planTasks) {
			Map map=new HashMap();
			map.put("id",planTask.getPlanId());
			map.put("start",planTask.getPstartTime());
			map.put("end",planTask.getPendTime());
			map.put("title",planTask.getSubject());
			if(((planTask.getPstartTime().getTime()+28800000)%86400000==0)&&((planTask.getPendTime().getTime()+28800000)%86400000==0)){    //北京时间的时间戳
				map.put("allDay",true);
				
			}else{
				map.put("allDay",false);
				map.put("color","#3CB371");
				map.put("textColor","#EEEEE0");
			}
			//map.put("allDay",false);
			list.add(map);
		}
		
		return list;}
	
	/**
	 * 日程视图版修改,传入start,end(日期),回填到edit页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("calenderEdit")
	 public ModelAndView calenderEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		String start=request.getParameter("start");
		String end =request.getParameter("end");
		String allDay=request.getParameter("allDay");
		String view=request.getParameter("view");
		String userId=ContextUtil.getCurrentUserId();
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startdate;
		Date enddate;
		if("agendamonth".equals(view)){
			 startdate=format.parse(format.format(Long.parseLong(start)));
			 enddate=format.parse(format.format(Long.parseLong(end)));
		}else{
        startdate=format.parse(format.format(Long.parseLong(start)));
		enddate=format.parse(format.format(Long.parseLong(end)));}//时间戳转化成date
		return getPathView(request).addObject("startdate",startdate ).addObject("enddate",enddate).addObject("allDay",allDay).addObject("userId", userId);
	}
	
	
	
	/**
	 * 日程视图后延修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lastMove")
    @ResponseBody
    public JsonResult lastMove(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String timeChange=request.getParameter("timeChange");
        String pkId=request.getParameter("pkId");
        PlanTask planTask=planTaskManager.get(pkId);
      Long l=  planTask.getPendTime().getTime()+Long.parseLong(timeChange);
      SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date enddate=format.parse(format.format(l));//时间戳转化成date
     //planTask.setPendTime(null);
      planTask.setPendTime(new Timestamp(enddate.getTime()));
      planTaskManager.saveOrUpdate(planTask);
        return new JsonResult(true,"成功修改！");
    }
	
	
	/**
	 * 日程视图整体移动修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("totalMove")
    @ResponseBody
    public JsonResult totalMove(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String timeChange=request.getParameter("timeChange");
        String changeAllDay=request.getParameter("changeAllDay");//是否变成全天日程
        String pkId=request.getParameter("pkId");
        PlanTask planTask=planTaskManager.get(pkId);
        Long s;
        Long e;
        SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        if("YES".equals(changeAllDay)){
        		Date starttime=format1.parse(format1.format(planTask.getPstartTime().getTime()));
        		Date endtime=format1.parse(format1.format(planTask.getPendTime().getTime()));
        		s=starttime.getTime()+Long.parseLong(timeChange);
        		if(planTask.getPendTime().getTime()%86400000!=0){
        			e=endtime.getTime()+86400000+Long.parseLong(timeChange);
        		}else{e=endtime.getTime()+Long.parseLong(timeChange);}
        }else{
        s= planTask.getPstartTime().getTime()+ Long.parseLong(timeChange);
        e= planTask.getPendTime().getTime()+ Long.parseLong(timeChange);}
         
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startdate=format.parse(format.format(s));//时间戳转化成date	
        Date enddate=format.parse(format.format(e));//时间戳转化成date
        planTask.setPstartTime(new Timestamp(startdate.getTime()));
        planTask.setPendTime(new Timestamp(enddate.getTime()));
        planTaskManager.saveOrUpdate(planTask);
        return new JsonResult(true,"成功修改！");
    }
}
