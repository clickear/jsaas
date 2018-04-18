package com.redxun.hr.core.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.hr.core.entity.HrDuty;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrDutyInstExt;
import com.redxun.hr.core.entity.HrDutySection;
import com.redxun.hr.core.entity.HrDutySystem;
import com.redxun.hr.core.entity.HrErrandsRegister;
import com.redxun.hr.core.entity.HrSystemSection;
import com.redxun.hr.core.entity.HrTransRestExt;
import com.redxun.hr.core.manager.HrDutyInstManager;
import com.redxun.hr.core.manager.HrDutyManager;
import com.redxun.hr.core.manager.HrDutySectionManager;
import com.redxun.hr.core.manager.HrErrandsRegisterManager;
import com.redxun.hr.core.manager.HrSystemSectionManager;
import com.redxun.oa.res.entity.OStatus;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * [HrDuty]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDuty/")
public class HrDutyController extends BaseListController{
    @Resource
    HrDutyManager hrDutyManager;
    @Resource
    HrSystemSectionManager hrSystemSectionManager;
    @Resource
    HrDutySectionManager hrDutySectionManager;
    @Resource
    HrDutyInstManager hrDutyInstManager;
    @Resource
    OsRelInstManager osRelInstManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    HrErrandsRegisterManager hrErrandsRegisterManager;
    
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
                hrDutyManager.delete(id);
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
        HrDuty hrDuty=null;
        if(StringUtils.isNotEmpty(pkId)){
           hrDuty=hrDutyManager.get(pkId);
        }else{
        	hrDuty=new HrDuty();
        }
        return getPathView(request).addObject("hrDuty",hrDuty);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	HrDuty hrDuty=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		hrDuty=hrDutyManager.get(pkId);
    		if("true".equals(forCopy)){
    			hrDuty.setDutyId(null);
    		}
    	}else{
    		hrDuty=new HrDuty();
    	}
    	return getPathView(request).addObject("hrDuty",hrDuty);
    }
    
    @RequestMapping("createDuty")
    @ResponseBody
    public JsonResult createDuty(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String dutyId=request.getParameter("dutyId");
    	HrDuty hrDuty=hrDutyManager.get(dutyId);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString= sdf.format(hrDuty.getStartTime());
        String endDateString= sdf.format(hrDuty.getEndTime());
        getDutyInst(hrDuty, sdf.parse(startDateString), sdf.parse(endDateString),hrDuty.getHrDutySystem());
        return new JsonResult(true,"成功创建！");
    }
    
    private void createNewDutyInst(String userId,HrDutySection hrDutySection,Date date,HrDutySystem hrDutySystem){
		if(null==hrDutySection){
			HrDutyInst hrDutyInst=new HrDutyInst();
			hrDutyInst.setDutyInstId(idGenerator.getSID());
			hrDutyInst.setUserId(userId);
			OsUser osUser=osUserManager.get(userId);
			if(osUser!=null)
				hrDutyInst.setUserName(osUser.getFullname());
			OsGroup osGroup=osGroupManager.getMainDeps(userId);
			if(osGroup!=null){
				hrDutyInst.setDepId(osGroup.getGroupId());
				hrDutyInst.setDepName(osGroup.getName());
			}
			hrDutyInst.setDate(date);
			hrDutyInst.setType(HrDutyInst.TYPE_REST);
			hrDutyInst.setSectionName("休息日");
			hrDutyInst.setSectionShortName("休");
			hrDutyInst.setSystemId(hrDutySystem.getSystemId());
			hrDutyInst.setSystemName(hrDutySystem.getName());
			
			//把旧的申请数据关联起来
			hrDutyInst=setApplicationData(hrDutyInst,userId,date);
			
			
			hrDutyInstManager.create(hrDutyInst);
		}else{
	    	HrDutyInst hrDutyInst=new HrDutyInst();
			hrDutyInst.setDutyInstId(idGenerator.getSID());
			hrDutyInst.setUserId(userId);
			OsUser osUser=osUserManager.get(userId);
			if(osUser!=null)
				hrDutyInst.setUserName(osUser.getFullname());
			OsGroup osGroup=osGroupManager.getMainDeps(userId);
			if(osGroup!=null){
				hrDutyInst.setDepId(osGroup.getGroupId());
				hrDutyInst.setDepName(osGroup.getName());
			}
			hrDutyInst.setSectionId(hrDutySection.getSectionId());
			hrDutyInst.setSectionName(hrDutySection.getSectionName());
			hrDutyInst.setSectionShortName(hrDutySection.getSectionShortName());
			hrDutyInst.setDate(date);
			hrDutyInst.setType(HrDutyInst.TYPE_DUTY);
			hrDutyInst.setSystemId(hrDutySystem.getSystemId());
			hrDutyInst.setSystemName(hrDutySystem.getName());
			hrDutyInst=setDutyInstExt(hrDutyInst,hrDutySection,hrDutySystem);
			
			//把旧的申请数据关联起来
			hrDutyInst=setApplicationData(hrDutyInst,userId,date);
			hrDutyInstManager.create(hrDutyInst);
		}
    }
    
    private HrDutyInst setApplicationData(HrDutyInst hrDutyInst,String userId,Date date){
    	List<HrErrandsRegister> hrErrandsRegisters=hrErrandsRegisterManager.getByUserIdAndStatusAndDate(userId,OStatus.APPROVED.name(),date);
		for (int i = 0; i < hrErrandsRegisters.size(); i++) {
			HrErrandsRegister hrErrandsRegister=hrErrandsRegisters.get(i);
			String type=hrErrandsRegister.getType();
			if(HrErrandsRegister.TYPE_VACATION.equals(type)){
				if(StringUtils.isEmpty(hrDutyInst.getVacApp())){
					JSONArray jsonArray=new JSONArray();
					String jsonString=JSON.toJSONString(hrErrandsRegister);
        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
					jsonArray.add(jsonObject);
					hrDutyInst.setVacApp(jsonArray.toString());
				}
				else{
					JSONArray jsonArray=JSONArray.fromObject(hrDutyInst.getVacApp());
					String jsonString=JSON.toJSONString(hrErrandsRegister);
        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
					jsonArray.add(jsonObject);
					hrDutyInst.setVacApp(jsonArray.toString());
				}
			}else if(HrErrandsRegister.TYPE_OVERTIME.equals(type)){
				if(StringUtils.isEmpty(hrDutyInst.getOtApp())){
					JSONArray jsonArray=new JSONArray();
					String jsonString=JSON.toJSONString(hrErrandsRegister);
        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
					jsonArray.add(jsonObject);
					hrDutyInst.setOtApp(jsonArray.toString());
				}
				else{
					JSONArray jsonArray=JSONArray.fromObject(hrDutyInst.getOtApp());
					String jsonString=JSON.toJSONString(hrErrandsRegister);
        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
					jsonArray.add(jsonObject);
					hrDutyInst.setOtApp(jsonArray.toString());
				}
			}else if(HrErrandsRegister.TYPE_TRANS_REST.equals(type)){
				Iterator<HrTransRestExt> iterator=hrErrandsRegister.getHrTransRestExts().iterator();
				HrTransRestExt hrTransRestExt=iterator.next();
				if(!"ZDTX".equals(hrTransRestExt.getType())){
					if(StringUtils.isEmpty(hrDutyInst.getTrApp())){
						JSONArray jsonArray=new JSONArray();
						String jsonString=JSON.toJSONString(hrErrandsRegister);
	        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
						jsonArray.add(jsonObject);
						hrDutyInst.setTrApp(jsonArray.toString());
					}
					else{
						JSONArray jsonArray=JSONArray.fromObject(hrDutyInst.getTrApp());
						String jsonString=JSON.toJSONString(hrErrandsRegister);
	        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
						jsonArray.add(jsonObject);
						hrDutyInst.setTrApp(jsonArray.toString());
					}
				}
			}else if(HrErrandsRegister.TYPE_OUT.equals(type)){
				if(StringUtils.isEmpty(hrDutyInst.getOutApp())){
					JSONArray jsonArray=new JSONArray();
					String jsonString=JSON.toJSONString(hrErrandsRegister);
        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
					jsonArray.add(jsonObject);
					hrDutyInst.setOutApp(jsonArray.toString());
				}
				else{
					JSONArray jsonArray=JSONArray.fromObject(hrDutyInst.getOutApp());
					String jsonString=JSON.toJSONString(hrErrandsRegister);
        			JSONObject jsonObject=JSONObject.fromObject(jsonString);
					jsonArray.add(jsonObject);
					hrDutyInst.setOutApp(jsonArray.toString());
				}
			}
		}
		return hrDutyInst;
    }
    
    private HrDutyInst setDutyInstExt(HrDutyInst hrDutyInst,HrDutySection hrDutySection,HrDutySystem hrDutySystem){
    	Set<HrDutyInstExt> hrDutyInstExts=hrDutyInst.getHrDutyInstExts();
    	if(MBoolean.NO.name().equals(hrDutySection.getIsGroup())){   //简单班次
    		HrDutyInstExt hrDutyInstExt=new HrDutyInstExt();
    		hrDutyInstExt.setExtId(idGenerator.getSID());
    		hrDutyInstExt.setStartSignIn(hrDutySection.getStartSignIn());
    		hrDutyInstExt.setDutyStartTime(hrDutySection.getDutyStartTime());
    		hrDutyInstExt.setEndSignIn(hrDutySection.getEndSignIn());
    		hrDutyInstExt.setEarlyOffTime(hrDutySection.getEarlyOffTime());
    		hrDutyInstExt.setDutyEndTime(hrDutySection.getDutyEndTime());
    		hrDutyInstExt.setSignOutTime(hrDutySection.getSignOutTime());
    		hrDutyInstExt.setHrDutyInst(hrDutyInst);
    		hrDutyInstExt.setSectionId(hrDutySection.getSectionId());
			hrDutyInstExts.add(hrDutyInstExt);
		}
    	else{          //组合班次
    		JSONObject jsonObject=JSONObject.fromObject(hrDutySection.getGroupList());
			JSONArray jsonArray=JSONArray.fromObject(JSONUtil.getString(jsonObject, "sections"));
			for (int j = 0; j < jsonArray.size(); j++) {
				HrDutySection itemSection=hrDutySectionManager.get(JSONUtil.getString((JSONObject)jsonArray.get(j),"sectionId"));//判断是否为空
				HrDutyInstExt hrDutyInstExt=new HrDutyInstExt();
				hrDutyInstExt.setExtId(idGenerator.getSID());
				hrDutyInstExt.setStartSignIn(itemSection.getStartSignIn());
				hrDutyInstExt.setDutyStartTime(itemSection.getDutyStartTime());
				hrDutyInstExt.setEndSignIn(itemSection.getEndSignIn());
				hrDutyInstExt.setEarlyOffTime(itemSection.getEarlyOffTime());
				hrDutyInstExt.setDutyEndTime(itemSection.getDutyEndTime());
				hrDutyInstExt.setSignOutTime(itemSection.getSignOutTime());
				hrDutyInstExt.setSectionId(itemSection.getSectionId());
				hrDutyInstExt.setHrDutyInst(hrDutyInst);
				hrDutyInstExts.add(hrDutyInstExt);
			}
    	}
    	hrDutyInst.setHrDutyInstExts(hrDutyInstExts);
    	return hrDutyInst;
    }
    
    private void getDutyInst(HrDuty hrDuty,Date startDate, Date endDate,HrDutySystem hrDutySystem) {
    	String uids=hrDuty.getUserId();
    	String[] userIds=null;
    	if(StringUtils.isNotEmpty(uids))
    		userIds=uids.split(",");
    	String gids=hrDuty.getGroupId();
    	String[] groupIds=null;
    	if(StringUtils.isNotEmpty(gids))
    		groupIds=gids.split(",");
    	String type=HrDutySystem.TYPE_WEEKLY;
    	List<HrSystemSection> hrSystemSections=hrSystemSectionManager.getBySystemId(hrDuty.getHrDutySystem().getSystemId());
    	if(hrSystemSections.size()==1){
    		if(null==hrSystemSections.get(0).getWorkday())
    			type=HrDutySystem.TYPE_WEEKLY;
    		else
    			type=HrDutySystem.TYPE_PERIODIC;
    	}
    	else if(hrSystemSections.size()>1){
    		type=HrDutySystem.TYPE_PERIODIC;
    	}
         
        if(HrDutySystem.TYPE_WEEKLY.equals(type)){  //标准一周
        	if(StringUtils.isNotEmpty(uids)){        //个人生成
        		Calendar initCal=Calendar.getInstance();
        		initCal.setTime(startDate);
        		int dayOfWeek=initCal.get(Calendar.DAY_OF_WEEK);
	        	for (int i = 0; i < userIds.length; i++) {
	        		boolean bContinue = true;  
	        		Calendar startCal = Calendar.getInstance();  
	                startCal.setTime(startDate); 
	                int count=dayOfWeek-2;
	                HrDutySection hrDutySection=hrSystemSections.get(0).getHrDutySection();
	                String restSection=hrDuty.getHrDutySystem().getRestSection();
	                String[] restSectionArray=restSection.split(",");
	                List<String> restList=Arrays.asList(restSectionArray);
	                
	                while (bContinue) {  
	                	Calendar cal=startCal;
	                	if (!endDate.after(cal.getTime())&&!endDate.equals(cal.getTime())){  
	                        break;  
	                    }
	                	else{
	                		hrDutyInstManager.deleteByUserIdAndDate(userIds[i],cal.getTime());
	                		if(restList.contains(String.valueOf((count+1)%7)))
	                			createNewDutyInst(userIds[i], null, cal.getTime(),hrDutySystem);  
	                		else
	                			createNewDutyInst(userIds[i], hrDutySection, cal.getTime(),hrDutySystem);  
	                    }
	                	cal.add(Calendar.DAY_OF_MONTH, 1);
	                	count++;
	                }
				}
        	}
        	if(StringUtils.isNotEmpty(gids)){        //部门生成
        		for (int i = 0; i < groupIds.length; i++) {
					List<OsRelInst> osRelInsts=osRelInstManager.getByParty1RelTypeIsMain(groupIds[i], OsRelType.REL_CAT_GROUP_USER_BELONG, MBoolean.YES.name());	
	        		for (int j = 0; j < osRelInsts.size(); j++) {
		        		boolean bContinue = true;  
		        		Calendar startCal = Calendar.getInstance();  
		                startCal.setTime(startDate); 
		                int count=0;
		                HrDutySection hrDutySection=hrSystemSections.get(0).getHrDutySection();
		                String restSection=hrDuty.getHrDutySystem().getRestSection();
		                String[] restSectionArray=restSection.split(",");
		                List<String> restList=Arrays.asList(restSectionArray);
		                
		                while (bContinue) {  
		                	Calendar cal=startCal;
		                	if (!endDate.after(cal.getTime())&&!endDate.equals(cal.getTime())){  
		                        break;  
		                    }
		                	else{
		                		hrDutyInstManager.deleteByUserIdAndDate(osRelInsts.get(j).getParty2(),cal.getTime());
		                		if(restList.contains(String.valueOf((count+1)%7)))
		                			createNewDutyInst(osRelInsts.get(j).getParty2(), null, cal.getTime(),hrDutySystem);  
		                		else
		                			createNewDutyInst(osRelInsts.get(j).getParty2(), hrDutySection, cal.getTime(),hrDutySystem);  
		                    }
		                	cal.add(Calendar.DAY_OF_MONTH, 1);
		                	count++;
		                }
					}
        		}
        	}
        }else if(HrDutySystem.TYPE_PERIODIC.equals(type)){  //周期生成
        	if(StringUtils.isNotEmpty(uids)){  //个人生成
	        	for (int i = 0; i < userIds.length; i++) { 
	        		Calendar startCal = Calendar.getInstance();  
	                startCal.setTime(startDate); 
	                Calendar cal=startCal;
	                int dayCount=Integer.parseInt(String.valueOf((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)));
	        	    for (int j = 0; j < dayCount+1; j++) {
	        	    	hrDutyInstManager.deleteByUserIdAndDate(userIds[i],cal.getTime());		
	            		HrDutySection hrDutySection=hrSystemSections.get(j%hrSystemSections.size()).getHrDutySection();
	            		createNewDutyInst(userIds[i],hrDutySection , cal.getTime(),hrDutySystem); 
	            		cal.add(Calendar.DAY_OF_MONTH, 1);	
					}
				}
        	}
        	if(StringUtils.isNotEmpty(gids)){        //部门生成
        		for (int i = 0; i < groupIds.length; i++) {
					List<OsRelInst> osRelInsts=osRelInstManager.getByParty1RelTypeIsMain(groupIds[i], OsRelType.REL_CAT_GROUP_USER_BELONG, MBoolean.YES.name());	
					for (int j = 0; j < osRelInsts.size(); j++) {
						Calendar startCal = Calendar.getInstance();  
		                startCal.setTime(startDate); 
		                Calendar cal=startCal;
		                int dayCount=Integer.parseInt(String.valueOf((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)));
		        	    for (int k = 0; k < dayCount+1; k++) {
		        	    	hrDutyInstManager.deleteByUserIdAndDate(osRelInsts.get(j).getParty2(),cal.getTime());		
		            		HrDutySection hrDutySection=hrSystemSections.get(k%hrSystemSections.size()).getHrDutySection();
		            		createNewDutyInst(osRelInsts.get(j).getParty2(),hrDutySection , cal.getTime(),hrDutySystem); 
		            		cal.add(Calendar.DAY_OF_MONTH, 1);	
						}
					}
        		}
        	}
        }
    } 

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrDutyManager;
	}

}
