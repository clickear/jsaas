package com.redxun.oa.calendar.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.manager.CalSettingManager;
import com.redxun.oa.calendar.manager.WorkCalendarManager;

/**
 * [CalSetting]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/calSetting/")
public class CalSettingController extends BaseListController{
    @Resource
    CalSettingManager calSettingManager;
    @Resource
    WorkCalendarManager workCalendarManager;
   
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
                calSettingManager.delete(id);
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
        CalSetting calSetting=null;
        if(StringUtils.isNotEmpty(pkId)){
           calSetting=calSettingManager.get(pkId);
        }else{
        	calSetting=new CalSetting();
        }
        return getPathView(request).addObject("calSetting",calSetting);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	CalSetting calSetting=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		calSetting=calSettingManager.get(pkId);
    	}else{
    		calSetting=new CalSetting();
    	}
    	return getPathView(request).addObject("calSetting",calSetting);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return calSettingManager;
	}
	
	
	@RequestMapping("setDefault")
	@ResponseBody
	public JSONObject setDefault(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject=new JSONObject();
		String settingId=request.getParameter("settingId");
		
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());;
		List<CalSetting> calSettings=calSettingManager.getAll(queryFilter);
		for (CalSetting calSetting : calSettings) {
			if("YES".equals(calSetting.getIsCommon())){
				calSetting.setIsCommon("NO");
				calSetting.setUpdateTime(new Date());
				calSettingManager.saveOrUpdate(calSetting);
			}
		}
		CalSetting setting=calSettingManager.get(settingId);
		setting.setIsCommon("YES");
		setting.setUpdateTime(new Date());
		calSettingManager.saveOrUpdate(setting);
		jsonObject.put("success", true);
		return jsonObject;
	}
	
	@RequestMapping("tenantList")
	@ResponseBody
	public JsonPageResult<CalSetting> tenantList(HttpServletRequest request,HttpServletResponse response){
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());;
		List<CalSetting> calSettings=calSettingManager.getAll(queryFilter);
		JsonPageResult<CalSetting> jsonPageResult=new JsonPageResult<CalSetting>(calSettings, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
		
	}
	

}
