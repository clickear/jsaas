package com.redxun.oa.calendar.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.entity.WorkCalendar;
import com.redxun.oa.calendar.entity.WorkTimeBlock;
import com.redxun.oa.calendar.manager.CalSettingManager;
import com.redxun.oa.calendar.manager.WorkCalendarManager;
import com.redxun.oa.calendar.manager.WorkTimeBlockManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.api.ICalendarService;



/**
 * [WorkCalendar]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/workCalendar/")
public class WorkCalendarController extends BaseListController{
    @Resource
    WorkCalendarManager workCalendarManager;
    @Resource
    WorkTimeBlockManager workTimeBlockManager;
    @Resource
    CalSettingManager calSettingManager;
    @Autowired(required=false)
    ICalendarService iCalendarService;
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
                workCalendarManager.delete(id);
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
        WorkCalendar workCalendar=null;
        if(StringUtils.isNotEmpty(pkId)){
           workCalendar=workCalendarManager.get(pkId);
        }else{
        	workCalendar=new WorkCalendar();
        }
        return getPathView(request).addObject("workCalendar",workCalendar);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
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

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return workCalendarManager;
	}

	@RequestMapping("getAllLimitByTime")
	@ResponseBody
	public List getAllLimitByTime(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String 	settingId=request.getParameter("settingId");
		String tenantId=ContextUtil.getCurrentTenantId();
		String start=request.getParameter("start");
		String end=request.getParameter("end");
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<WorkCalendar> workCalendars=workCalendarManager.getCalendarsLimitByDate(sdf.parse(start), sdf.parse(end), tenantId,settingId);
		List<Map> list =new ArrayList<Map>(); 
		for (WorkCalendar workCalendar : workCalendars) {
			Map map=new HashMap();
			map.put("id",workCalendar.getCalenderId());
			map.put("start",workCalendar.getStartTime());
			map.put("end",workCalendar.getEndTime());
			map.put("title",workCalendar.getCalSetting().getCalName());
			map.put("allDay",false);
			map.put("color","#3CB371");
			map.put("textColor","#EEEEE0");
			
			list.add(map);
		}
		
		return list;}
	/**
	 * 逐日创建工作时间
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("drawCalendar")
	@ResponseBody
	public JSONObject drawCalendar(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String timeBlockValue=request.getParameter("timeBlockValue");
		String whereCross="";
		JSONObject jsonObjectReuslt=new JSONObject();
		boolean removeOrNot=true;
		Long longStart=Long.parseLong(start);
		Long longEnd=Long.parseLong(end);
		WorkTimeBlock workTimeBlock=workTimeBlockManager.get(timeBlockValue);
		String name=workTimeBlock.getSettingName();
		String calSettingId=request.getParameter("calSettingId");
		CalSetting calSetting;
		if(StringUtils.isNotBlank(calSettingId)){
			calSetting=calSettingManager.get(calSettingId);
		}else{
			calSetting=calSettingManager.getByName(name,tenantId);
		}
		String timeIntervals=workTimeBlock.getTimeIntervals();
		JSONObject jsonObject=JSONObject.fromObject(timeIntervals);
		JSONArray jsonArray=jsonObject.names();
		Map<String, Long> stamp=new HashMap<String, Long>();
		for (Object object : jsonArray) {
			String intervalName=object.toString();
			String value=(String) jsonObject.get(intervalName);
			String[] time=value.split(":");
			Long millisecond=calculateTimeStamp(Long.valueOf(time[0]), Long.valueOf(time[1]));//毫秒数
			stamp.put(intervalName, millisecond+longStart);
		}
		for (Map.Entry<String, Long> entry : stamp.entrySet()) {
			String key=entry.getKey();
			long value=stamp.get(key);
			if("endTime".equals(key)){//与前一个做对比
				if(value<stamp.get("startTime")){
					whereCross+="endTime";
				}
			}else if(key.contains("plusStart")){
				String whereN=key.substring(9);
				String where="plusStart"+whereN;//这个键
				String whereprefix="";//下一个键
				long whereNum=Long.parseLong(whereN);
				if(whereNum>0){
					whereprefix="plusEnd"+(whereNum-1);
					if(stamp.get(whereprefix)>value){//如果小于的话则记录跨天
						whereCross+=where;
					}
				}else{
					if(value<stamp.get("endTime")){
						whereCross+="plusStart0";
					}
				}
			}else if(key.contains("plusEnd")){
				String whereN=key.substring(7);
				String where="plusEnd"+whereN;//这个键
				String whereprefix="";//下一个键
				long whereNum=Long.parseLong(whereN);
				if(whereNum>0){
					whereprefix="plusStart"+whereN;
					if(stamp.get(whereprefix)>value){//如果小于的话则记录跨天
						whereCross+=where;
					}
				}else {
					whereprefix="plusStart0";
					if(stamp.get(whereprefix)>value){//如果小于的话则记录跨天
						whereCross+=where;
					}
				}
			}
		}

		for (Map.Entry<String, Long> entry : stamp.entrySet()) {//调整map的时间
			   String key=entry.getKey();
			   if(whereCross.contains("endTime")){//假如包含原始数据
				   if(!"startTime".equals(key)){
					   stamp.put(key, stamp.get(key)+86400000);//每天的时间戳往后推一天
				   }
			   }else if(whereCross.contains("plusStart")){
				   if(key.contains("plus")){
					   long whereNum=Long.parseLong(key.substring(key.length()-1));
					   if(Long.parseLong(key.substring(key.length()-1))>=whereNum){
						   stamp.put(key, stamp.get(key)+86400000);//每天的时间戳往后推一天
					   }  
				   }
			   }else if(whereCross.contains("plusEnd")){
				   if(key.contains("plus")){
					   long whereNum=Long.parseLong(key.substring(key.length()-1));
					   if(Long.parseLong(key.substring(key.length()-1))>whereNum){
						   stamp.put(key, stamp.get(key)+86400000);//每天的时间戳往后推一天
					   }else if(whereCross.equals(key)){
						   stamp.put(key, stamp.get(key)+86400000);
					   }   
				   }
			   }
			  }
		
		Long result=longEnd-longStart;//所选中的区间的总毫秒数量
		Long dayNum=result/86400000;//选中多少天
		Long beforeSaveStartTime=stamp.get("startTime");
		Long beforeSaveEndTime=stamp.get("endTime");
		for (long i = 0; i < dayNum; i++) {
			//start+i*86400000
			int stampSize=stamp.size();	
			WorkCalendar workCalendar=new WorkCalendar();
			workCalendar.setCalenderId(idGenerator.getSID());
			workCalendar.setStartTime(new Timestamp(beforeSaveStartTime+i*86400000));
			workCalendar.setEndTime(new Timestamp(beforeSaveEndTime+i*86400000));
			workCalendar.setCalSetting(calSetting);
			workCalendar.setTenantId(tenantId);
			 List<WorkCalendar> workCalendars=workCalendarManager.getCalendarInDayBySettingId(workCalendar.getStartTime(), tenantId, calSetting.getSettingId());
		        for (WorkCalendar workCalendar2 : workCalendars) {//如果有重合的地方则不允许移动
					if ((workCalendar2.getStartTime().getTime() <= workCalendar.getStartTime().getTime() && workCalendar2.getEndTime().getTime() >= workCalendar.getStartTime().getTime())||
						(workCalendar2.getStartTime().getTime() <= workCalendar.getEndTime().getTime() && workCalendar2.getEndTime().getTime() >= workCalendar.getEndTime().getTime()))
					{
						removeOrNot=false;
					}
				}
		        if(removeOrNot){
		        	// workCalendarManager.saveOrUpdate(workCalendar);
		        	 jsonObjectReuslt.put("success", true);
		        }else{
		        	jsonObjectReuslt.put("success", false);
		        }
			
			
			
			if(removeOrNot){
				calSetting.getWorkCalendars().add(workCalendar);	
			}
			
			for (long j = 0; j < stampSize/2-1; j++) {
				String plusStartName="plusStart"+j;
				String plusEndName="plusEnd"+j;
				WorkCalendar plusWorkCalendar=new WorkCalendar();
				plusWorkCalendar.setCalenderId(idGenerator.getSID());
				plusWorkCalendar.setStartTime(new Timestamp(stamp.get(plusStartName)+i*86400000));
				plusWorkCalendar.setEndTime(new Timestamp(stamp.get(plusEndName)+i*86400000));
				plusWorkCalendar.setCalSetting(calSetting);
				plusWorkCalendar.setTenantId(tenantId);
				//if(workCalendarManager.getCalendarInDay(workCalendar.getStartTime(), tenantId).size()<=1)
				calSetting.getWorkCalendars().add(plusWorkCalendar);
			}
		}
		if(removeOrNot){
			calSettingManager.saveOrUpdate(calSetting);
		}
    	return jsonObjectReuslt;
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
    public JSONObject totalMove(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String timeChange=request.getParameter("timeChange");
       // String changeAllDay=request.getParameter("changeAllDay");//是否变成全天日程
        boolean removeOrNot=true;
        JSONObject jsonObject=new JSONObject();
        String pkId=request.getParameter("pkId");
        String tenantId=ContextUtil.getCurrentTenantId();
        WorkCalendar workCalendar=workCalendarManager.get(pkId);
        String settingId=workCalendar.getCalSetting().getSettingId();
        Long s;
        Long e;
        SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date starttime=format1.parse(format1.format(workCalendar.getStartTime().getTime()));
		Date endtime=format1.parse(format1.format(workCalendar.getEndTime().getTime()));
		s=starttime.getTime()+Long.parseLong(timeChange);
		e=endtime.getTime()+Long.parseLong(timeChange);
         
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startdate=format.parse(format.format(s));//时间戳转化成date	
        Date enddate=format.parse(format.format(e));//时间戳转化成date
        workCalendar.setStartTime(new Timestamp(startdate.getTime()));
        workCalendar.setEndTime(new Timestamp(enddate.getTime()));
        List<WorkCalendar> workCalendars=workCalendarManager.getCalendarInDayBySettingId(workCalendar.getStartTime(), tenantId, settingId);
        for (WorkCalendar workCalendar2 : workCalendars) {//如果有重合的地方则不允许移动
			if ((workCalendar2.getStartTime().getTime() <= workCalendar.getStartTime().getTime() && workCalendar2.getEndTime().getTime() >= workCalendar.getStartTime().getTime())||
				(workCalendar2.getStartTime().getTime() <= workCalendar.getEndTime().getTime() && workCalendar2.getEndTime().getTime() >= workCalendar.getEndTime().getTime()))
			{
				removeOrNot=false;
			}
		}
        if(removeOrNot){
        	 workCalendarManager.saveOrUpdate(workCalendar);
        	 jsonObject.put("success", true);
        	 return jsonObject;
        }else{
        	jsonObject.put("success", false);
        	return jsonObject;
        }
       
        
    }
	
	@RequestMapping("testCalendar")
	@ResponseBody
	public JSONObject testCalendar(HttpServletRequest request,HttpServletResponse response) throws ParseException, Exception{
	     String settingId=request.getParameter("settingId");
	     String computeWay=request.getParameter("computeWay");
	     String startDate=request.getParameter("startDate");
	     String endDate=request.getParameter("endDate");
	     String minuteNum=request.getParameter("minuteNum");
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     //Date date = sdf.parse("2008-08-08 12:10:12");
	     JSONObject jsonObject=new JSONObject();
	     if("time".equals(computeWay)){
	    	 int result=iCalendarService.getActualTimeCalenderId(settingId,sdf.parse(startDate),sdf.parse(endDate));
	    	 jsonObject.put("result", result);
	    	 
	     }else if("date".equals(computeWay)){
	    	 Date result=iCalendarService.getByCalendarId(settingId,sdf.parse(startDate), Integer.parseInt(minuteNum));
	    	 jsonObject.put("result", result.toString());
	     }
	     return jsonObject;
	}
	
	@RequestMapping("removeCalendar")
	@ResponseBody
	public JSONObject removeCalendar(HttpServletRequest request,HttpServletResponse response){
		String settingId=request.getParameter("calSettingId");
		String tenantId=ContextUtil.getCurrentTenantId();
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		Long longStart=Long.parseLong(start);
		Long longEnd=Long.parseLong(end);
		Timestamp startTime=new Timestamp(longStart);
		Timestamp endTime=new Timestamp(longEnd);
		List<WorkCalendar> workCalendars=workCalendarManager.getCalendarsLimitByDate(startTime, endTime, tenantId, settingId);
		for (WorkCalendar workCalendar : workCalendars) {
			workCalendarManager.deleteObject(workCalendar);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		return jsonObject;
	}
	
	/**
	 * 计算有多少毫秒
	 * @param hour
	 * @param minute
	 * @return
	 */
	public Long calculateTimeStamp(Long hour,Long minute){
		if(hour!=null&&minute!=null){
			Long result=(hour*60+minute)*60000;
			return result;
		}else{
			return null;
		}
		
	}
}
