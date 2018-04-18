
package com.redxun.sys.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysOfficeTemplate;
import com.redxun.sys.core.manager.SysOfficeTemplateManager;
import com.redxun.sys.log.LogEnt;

/**
 * office模板控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysOfficeTemplate/")
public class SysOfficeTemplateController extends BaseMybatisListController{
    @Resource
    SysOfficeTemplateManager sysOfficeTemplateManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "sys", submodule = "office模板")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysOfficeTemplateManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
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
        String pkId=RequestUtil.getString(request, "pkId");
        SysOfficeTemplate sysOfficeTemplate=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysOfficeTemplate=sysOfficeTemplateManager.get(pkId);
        }else{
        	sysOfficeTemplate=new SysOfficeTemplate();
        }
        return getPathView(request).addObject("sysOfficeTemplate",sysOfficeTemplate);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	SysOfficeTemplate sysOfficeTemplate=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysOfficeTemplate=sysOfficeTemplateManager.get(pkId);
    	}else{
    		sysOfficeTemplate=new SysOfficeTemplate();
    	}
    	return getPathView(request).addObject("sysOfficeTemplate",sysOfficeTemplate);
    }
    
    /**
     * 有子表的情况下编辑明细的json
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getJson")
    @ResponseBody
    public String getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=RequestUtil.getString(request, "ids");    	
        SysOfficeTemplate sysOfficeTemplate = sysOfficeTemplateManager.getSysOfficeTemplate(uId);
        String json = JSONObject.toJSONString(sysOfficeTemplate);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysOfficeTemplateManager;
	}

}
