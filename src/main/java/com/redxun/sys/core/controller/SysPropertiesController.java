
package com.redxun.sys.core.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysPrivateProperties;
import com.redxun.sys.core.entity.SysProperties;
import com.redxun.sys.core.manager.SysPrivatePropertiesManager;
import com.redxun.sys.core.manager.SysPropertiesManager;
import com.redxun.sys.core.util.OpenOfficeUtil;
import com.redxun.sys.log.LogEnt;

/**
 * 系统参数控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysProperties/")
public class SysPropertiesController extends BaseMybatisListController{
    @Resource
    SysPropertiesManager sysPropertiesManager;
    @Resource
    SysPrivatePropertiesManager sysPrivatePropertiesManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
	
	
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "系统参数")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	sysPrivatePropertiesManager.deleteByProId(id);
                sysPropertiesManager.delete(id);
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
        SysProperties sysProperties=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysProperties=sysPropertiesManager.get(pkId);
        }else{
        	sysProperties=new SysProperties();
        }
        return getPathView(request).addObject("sysProperties",sysProperties);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	SysProperties sysProperties=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysProperties=sysPropertiesManager.get(pkId);
    		String encrypt=sysProperties.getEncrypt();
    		if(encrypt.equals("YES")){
    			EncryptUtil encryptUtil=WebAppUtil.getBean(EncryptUtil.class);
    			String decrypt=encryptUtil.decrypt(sysProperties.getValue());
    			sysProperties.setValue(decrypt);
    		}
    	}else{
    		sysProperties=new SysProperties();
    	}
    	return getPathView(request).addObject("sysProperties",sysProperties);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysPropertiesManager;
	}
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		return this.getPathView(request);
	}

	
	@RequestMapping("listAll")
	@ResponseBody
	public JsonPageResult<SysProperties> listALL(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		//queryFilter.addFieldParam("TENANT_ID_", tenantId);
		queryFilter.addSortParam("CATEGORY_", "ASC");
		List<SysProperties> sysProperties=sysPropertiesManager.getAll(queryFilter);
		JsonPageResult<SysProperties> jsonPageResult=new JsonPageResult<SysProperties>(sysProperties, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	@RequestMapping("getCommonProperties")
	@ResponseBody
	public List<SysProperties> getCommonProperties(HttpServletRequest request,HttpServletResponse response){
		String tenantId="1";
		List<SysProperties> sysProperties=sysPropertiesManager.getAllByTenantId(tenantId);
		return sysProperties;
	}
	
	@RequestMapping("getCategories")
	@ResponseBody
	public List<KeyValEnt> getCategories(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		List<KeyValEnt> keyValEnts=new ArrayList<KeyValEnt>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("TENANT_ID_", tenantId);
		List<String> list=sysPropertiesManager.getCategory(params);
		for (String string : list) {
			KeyValEnt keyValEnt=new KeyValEnt(string, string);
			keyValEnts.add(keyValEnt);
		}
		return keyValEnts;
	}
	
	
	@RequestMapping("getProperties")
	@ResponseBody
	public JSONObject getPropertyById(HttpServletRequest request,HttpServletResponse response){
		String proId=RequestUtil.getString(request,"proId");
		SysProperties sysProperties=sysPropertiesManager.get(proId);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name", sysProperties.getName());
		return jsonObject;
	}
	
	@RequestMapping("uniqueValidation")
	@ResponseBody
	public JSONObject uniqueValidation(HttpServletRequest request,HttpServletResponse response){
		String alias=RequestUtil.getString(request, "alias");
		String id=RequestUtil.getString(request, "id");
		SysProperties sysProperties=sysPropertiesManager.getPropertiesByName(alias);
		JSONObject jsonObject=new JSONObject();
		if(sysProperties==null){//如果没有找到这个alias的说明没有重复冲突
			jsonObject.put("success", true);
		}else if(sysProperties.getProId().equals(id)){
			jsonObject.put("success", true);
		}else{
			jsonObject.put("success", false);
		}
		return jsonObject;
	}
	
}
