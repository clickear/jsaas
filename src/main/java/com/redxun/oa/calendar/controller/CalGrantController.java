package com.redxun.oa.calendar.controller;

import java.util.List;

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
import com.redxun.oa.calendar.entity.CalGrant;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.manager.CalGrantManager;
import com.redxun.oa.calendar.manager.CalSettingManager;
import com.redxun.org.api.service.GroupService;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [CalGrant]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/calGrant/")
public class CalGrantController extends BaseListController{
    @Resource
    CalGrantManager calGrantManager;
    @Resource
    CalSettingManager calSettingManager;
	@Resource
	UserService userService;  
	@Resource
	GroupService groupService;  
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
                calGrantManager.delete(id);
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
        CalGrant calGrant=null;
        if(StringUtils.isNotEmpty(pkId)){
           calGrant=calGrantManager.get(pkId);
        }else{
        	calGrant=new CalGrant();
        }
        return getPathView(request).addObject("calGrant",calGrant);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String settingId=request.getParameter("settingId");
    	CalGrant calGrant=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		calGrant=calGrantManager.get(pkId);
    		String belongWho=calGrant.getBelongWho();
        	String type=calGrant.getGrantType();
        	String  belongWhoName="";
        	if("GROUP".equals(type)){
        		belongWhoName=groupService.getById(belongWho).getIdentityName();
        	}else if("USER".equals(type)){
        		belongWhoName=userService.getByUserId(belongWho).getIdentityName();
        	}
    		return getPathView(request).addObject("calGrant",calGrant).addObject("settingId",settingId).addObject("belongWhoName", belongWhoName);
    	}else{
    		calGrant=new CalGrant();
    		return getPathView(request).addObject("calGrant",calGrant).addObject("settingId",settingId);
    	}
    	
    	
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletResponse response,HttpServletRequest request){
    	String settingId=request.getParameter("settingId");
    	return getPathView(request).addObject("settingId", settingId);
    }
    
    @RequestMapping("getSettingGrants")
    @ResponseBody
    public JsonPageResult<CalGrant> getSettingGrants(HttpServletRequest request,HttpServletResponse response){
    	String settingId=request.getParameter("settingId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("calSetting.settingId", settingId);
    	List<CalGrant> calGrants=calGrantManager.getAll(queryFilter);
    	for (CalGrant calGrant : calGrants) {
    		String  belongWhoName="";
    		String belongWho=calGrant.getBelongWho();
        	String type=calGrant.getGrantType();
        	if("GROUP".equals(type)){
        		belongWhoName=groupService.getById(belongWho).getIdentityName();
        	}else if("USER".equals(type)){
        		belongWhoName=userService.getByUserId(belongWho).getIdentityName();
        	}
			calGrant.setBelongWho(belongWhoName);
		}
    	JsonPageResult<CalGrant> jsonPageResult=new JsonPageResult<CalGrant>(calGrants, queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    }
    
    /**
     * 保存用户与组
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("saveUserGroups")
    @ResponseBody
    public JsonResult saveUserGroups(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	String userIds=request.getParameter("userIds");
    	String groupIds=request.getParameter("groupIds");
    	String settingId=request.getParameter("settingId");
    	CalSetting calSetting= calSettingManager.get(settingId);
    	if(StringUtils.isNotEmpty(userIds)){
    		String[]uIds=userIds.split("[,]");
    		for(String uId:uIds){
    			CalGrant cal=calGrantManager.getBySettingIdGrantTypeBelongWho(settingId, "USER", uId);
    			if(cal==null){
    				cal=new CalGrant();
    				cal.setBelongWho(uId);
    				cal.setCalSetting(calSetting);
    				cal.setGrantType("USER");
    				calGrantManager.create(cal);
    			}
    		}
    	}
    	
    	if(StringUtils.isNotEmpty(groupIds)){
    		String[]uIds=groupIds.split("[,]");
    		for(String uId:uIds){
    			CalGrant cal=calGrantManager.getBySettingIdGrantTypeBelongWho(settingId, "GROUP", uId);
    			if(cal==null){
    				cal=new CalGrant();
    				cal.setBelongWho(uId);
    				cal.setCalSetting(calSetting);
    				cal.setGrantType("GROUP");
    				calGrantManager.create(cal);
    			}
    		}
    	}
    	
    	return new JsonResult(true,"成功保存！");
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return calGrantManager;
	}
	
	@RequestMapping("view")
	public ModelAndView view(HttpServletRequest request,HttpServletResponse response){
		String settingId=request.getParameter("settingId");
		return getPathView(request).addObject("settingId", settingId);
	}
	
	

}
