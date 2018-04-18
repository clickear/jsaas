package com.redxun.hr.core.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrHoliday;
import com.redxun.hr.core.manager.HrDutyInstManager;
import com.redxun.hr.core.manager.HrHolidayManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * [HrHoliday]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrHoliday/")
public class HrHolidayController extends BaseListController {
	@Resource
	HrHolidayManager hrHolidayManager;
	@Resource
	HrDutyInstManager hrDutyInstManager;
	@Resource
	OsRelInstManager osRelInstManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OsGroupManager osGroupManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				hrHolidayManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
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
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		HrHoliday hrHoliday = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrHoliday = hrHolidayManager.get(pkId);
		} else {
			hrHoliday = new HrHoliday();
		}
		return getPathView(request).addObject("hrHoliday", hrHoliday);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		HrHoliday hrHoliday = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrHoliday = hrHolidayManager.get(pkId);
			if ("true".equals(forCopy)) {
				hrHoliday.setHolidayId(null);
			}
		} else {
			hrHoliday = new HrHoliday();
		}
		return getPathView(request).addObject("hrHoliday", hrHoliday);
	}
	
	@RequestMapping("getAll")
	@ResponseBody
	public List<HrHoliday> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<HrHoliday> hrHolidays=hrHolidayManager.getAll(queryFilter);
		return hrHolidays;
	}
	
	@RequestMapping("saveData")
	@ResponseBody
	public JsonResult saveData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String formData=request.getParameter("data");
		String hType=request.getParameter("hType");
		HrHoliday hrHoliday=(HrHoliday)JSONUtil.json2Bean(formData, HrHoliday.class);
		if("user".equals(hType)){
			hrHoliday.setGroupId(null);
			hrHoliday.setGroupName(null);
			hrHoliday.setSystemId(null);
		}else if("department".equals(hType)){
			hrHoliday.setUserId(null);
			hrHoliday.setUserName(null);
			hrHoliday.setSystemId(null);
		}else if("system".equals(hType)){
			hrHoliday.setUserId(null);
			hrHoliday.setUserName(null);
			hrHoliday.setGroupId(null);
			hrHoliday.setGroupName(null);
		}
		if(StringUtils.isNotEmpty(hrHoliday.getHolidayId())){      //编辑
			HrHoliday oHrHoliday=hrHolidayManager.get(hrHoliday.getHolidayId());
			//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtils.isNotEmpty(oHrHoliday.getUserId())){
				String[] userIds=oHrHoliday.getUserId().split(",");
				for (int j = 0; j < userIds.length; j++) {
					HrDutyInst hrDutyInst=hrDutyInstManager.getByHoliDayIdAndUserIdAndDate(oHrHoliday.getHolidayId(), userIds[j],oHrHoliday.getStartDay());
					if(null!=hrDutyInst){
						hrDutyInst.setHrHoliday(null);
						hrDutyInstManager.update(hrDutyInst);
					}
				}
			}
			if(StringUtils.isNotEmpty(oHrHoliday.getGroupId())){
				String[] groupIds=oHrHoliday.getGroupId().split(",");
				for (int j = 0; j < groupIds.length; j++) {
					List<OsRelInst> osRelInsts=osRelInstManager.getByParty1RelTypeIsMain(groupIds[j], OsRelType.REL_CAT_GROUP_USER_BELONG, MBoolean.YES.name());
					for (int k = 0; k < osRelInsts.size(); k++) {
						HrDutyInst hrDutyInst=hrDutyInstManager.getByHoliDayIdAndUserIdAndDate(oHrHoliday.getHolidayId(),osRelInsts.get(k).getParty2(),oHrHoliday.getStartDay());
						if(null!=hrDutyInst){
							hrDutyInst.setHrHoliday(null);
							hrDutyInstManager.update(hrDutyInst);
						}
					}
				}		
			}
			if(StringUtils.isNotEmpty(oHrHoliday.getSystemId())){
				String[] systemIds=oHrHoliday.getSystemId().split(",");
				for (int j = 0; j < systemIds.length; j++) {
					List<HrDutyInst> hrDutyInsts=hrDutyInstManager.getBySystemIdAndOneDay(systemIds[j],oHrHoliday.getStartDay());
					for (int k = 0; k < hrDutyInsts.size(); k++) {  
						HrDutyInst hrDutyInst=hrDutyInsts.get(k);
						if(null!=hrDutyInst){
							hrDutyInst.setHrHoliday(null);
							hrDutyInstManager.update(hrDutyInst);
						}
					}
				}
			}
			oHrHoliday.setUserId(null);
			oHrHoliday.setGroupId(null);
			oHrHoliday.setUserName(null);
			oHrHoliday.setGroupName(null);
			oHrHoliday.setSystemId(null);
			BeanUtil.copyNotNullProperties(oHrHoliday, hrHoliday);
			oHrHoliday.setEndDay(oHrHoliday.getStartDay());
			if(StringUtils.isNotEmpty(oHrHoliday.getUserId())){
				String[] userIds=oHrHoliday.getUserId().split(",");
				String userNames="";
				for (int i = 0; i < userIds.length; i++) {
					if(i==0){
						userNames=osUserManager.get(userIds[i]).getFullname();
						continue;
					}
					userNames+=","+osUserManager.get(userIds[i]).getFullname();
				}
				oHrHoliday.setUserName(userNames);
				hrHolidayManager.update(oHrHoliday);
				
				for (int i = 0; i < userIds.length; i++) {
					HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(userIds[i], oHrHoliday.getStartDay());
					hrDutyInst.setHrHoliday(oHrHoliday);
					hrDutyInstManager.update(hrDutyInst);
				}
			}
			else if(StringUtils.isNotEmpty(oHrHoliday.getGroupId())){
				String[] groupIds=oHrHoliday.getGroupId().split(",");
				String groupNames="";
				for (int i = 0; i < groupIds.length; i++) {
					if(i==0){
						groupNames=osGroupManager.get(groupIds[i]).getName();
						continue;
					}
					groupNames+=","+osGroupManager.get(groupIds[i]).getName();
				}
				oHrHoliday.setGroupName(groupNames);
				hrHolidayManager.update(oHrHoliday);
				
				for (int i = 0; i < groupIds.length; i++) {
					List<OsRelInst> osRelInsts=osRelInstManager.getByParty1RelTypeIsMain(groupIds[i], OsRelType.REL_CAT_GROUP_USER_BELONG, MBoolean.YES.name());
					for (int j = 0; j < osRelInsts.size(); j++) {
						HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(osRelInsts.get(j).getParty2(), oHrHoliday.getStartDay());
						hrDutyInst.setHrHoliday(oHrHoliday);
						hrDutyInstManager.update(hrDutyInst);
					}
				}		
			}
			else if(StringUtils.isNotEmpty(oHrHoliday.getSystemId())){
				hrHolidayManager.update(oHrHoliday);
				String[] systemIds=oHrHoliday.getSystemId().split(",");
				for (int i = 0; i < systemIds.length; i++) {
					List<HrDutyInst> hrDutyInsts=hrDutyInstManager.getBySystemIdAndOneDay(systemIds[i],oHrHoliday.getStartDay());
					for (int j = 0; j < hrDutyInsts.size(); j++) {
						HrDutyInst hrDutyInst=hrDutyInsts.get(j);
						hrDutyInst.setHrHoliday(oHrHoliday);
						hrDutyInstManager.update(hrDutyInst);
					}
				}
			}
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=sdf.parse(sdf.format(hrHoliday.getStartDay()));
			Date endDate=sdf.parse(sdf.format(hrHoliday.getEndDay()));
			Calendar startCal = Calendar.getInstance();  
	        startCal.setTime(startDate); 
	        //List<HrHoliday> hrHolidays=new ArrayList<HrHoliday>();
	        int dayCount=Integer.parseInt(String.valueOf((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)));
		    for (int i = 0; i < dayCount+1; i++) {
		    	HrHoliday tmpHrHoliday=new HrHoliday();
		    	tmpHrHoliday.setHolidayId(idGenerator.getSID());
		    	tmpHrHoliday.setStartDay(startCal.getTime());
		    	tmpHrHoliday.setEndDay(startCal.getTime());
		    	tmpHrHoliday.setName(hrHoliday.getName());
		    	tmpHrHoliday.setDesc(hrHoliday.getDesc());
				if(StringUtils.isNotEmpty(hrHoliday.getUserId())){
					tmpHrHoliday.setUserId(hrHoliday.getUserId());
					String[] userIds=hrHoliday.getUserId().split(",");
					String userNames="";
					for (int j = 0; j < userIds.length; j++) {
						if(j==0){
							userNames=osUserManager.get(userIds[j]).getFullname();
							continue;
						}
						userNames+=","+osUserManager.get(userIds[j]).getFullname();
					}
					tmpHrHoliday.setUserName(userNames);
					hrHolidayManager.create(tmpHrHoliday);
					
					
					for (int j = 0; j < userIds.length; j++) {
						HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(userIds[j], startCal.getTime());
						if(hrDutyInst!=null){
							hrDutyInst.setHrHoliday(tmpHrHoliday);
							hrDutyInstManager.update(hrDutyInst);
						}
						//tmpHrHoliday.setHrDutyInst(hrDutyInst);
					}
				}
				else if(StringUtils.isNotEmpty(hrHoliday.getGroupId())){
					tmpHrHoliday.setGroupId(hrHoliday.getGroupId());
					String[] groupIds=hrHoliday.getGroupId().split(",");
					String groupNames="";
					for (int j = 0; j < groupIds.length; j++) {
						if(j==0){
							groupNames=osGroupManager.get(groupIds[j]).getName();
							continue;
						}
						groupNames+=","+osGroupManager.get(groupIds[j]).getName();
					}
					tmpHrHoliday.setGroupName(groupNames);
					hrHolidayManager.create(tmpHrHoliday);
					
					for (int j = 0; j < groupIds.length; j++) {
						List<OsRelInst> osRelInsts=osRelInstManager.getByParty1RelTypeIsMain(groupIds[j], OsRelType.REL_CAT_GROUP_USER_BELONG, MBoolean.YES.name());
						for (int k = 0; k < osRelInsts.size(); k++) {
							HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(osRelInsts.get(k).getParty2(), startCal.getTime());
							if(hrDutyInst!=null){
								hrDutyInst.setHrHoliday(tmpHrHoliday);
								hrDutyInstManager.update(hrDutyInst);
							}
							//tmpHrHoliday.setHrDutyInst(hrDutyInst);
						}
					}		
				}
				else if(StringUtils.isNotEmpty(hrHoliday.getSystemId())){
					tmpHrHoliday.setSystemId(hrHoliday.getSystemId());
					hrHolidayManager.create(tmpHrHoliday);
					String[] systemIds=hrHoliday.getSystemId().split(",");
					for (int j = 0; j < systemIds.length; j++) {
						List<HrDutyInst> hrDutyInsts=hrDutyInstManager.getBySystemIdAndOneDay(systemIds[j],startCal.getTime());
						for (int k = 0; k < hrDutyInsts.size(); k++) {
							HrDutyInst hrDutyInst=hrDutyInsts.get(k);
							if(hrDutyInst!=null){
								hrDutyInst.setHrHoliday(tmpHrHoliday);
								hrDutyInstManager.update(hrDutyInst);
							}
							//tmpHrHoliday.setHrDutyInst(hrDutyInst);
						}
					}
				}
				//hrHolidayManager.create(tmpHrHoliday);
				startCal.add(Calendar.DAY_OF_MONTH,1);
			}
		}
	    
	    return new JsonResult(true,"成功！");
       

	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrHolidayManager;
	}

}
