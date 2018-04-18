
package com.redxun.sys.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysPrivateProperties;
import com.redxun.sys.core.entity.SysProperties;
import com.redxun.sys.core.manager.SysPrivatePropertiesManager;
import com.redxun.sys.core.manager.SysPropertiesManager;
import com.redxun.sys.log.LogEnt;

/**
 * 私有参数控制器
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysPrivateProperties/")
public class SysPrivatePropertiesController extends BaseMybatisListController{
    @Resource
    SysPrivatePropertiesManager sysPrivatePropertiesManager;
    @Resource
    SysPropertiesManager sysPropertiesManager;
    
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "系统私有参数")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysPrivatePropertiesManager.delete(id);
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
        SysPrivateProperties sysPrivateProperties=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysPrivateProperties=sysPrivatePropertiesManager.get(pkId);
        }else{
        	sysPrivateProperties=new SysPrivateProperties();
        }
        return getPathView(request).addObject("sysPrivateProperties",sysPrivateProperties);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysPrivateProperties sysPrivateProperties=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysPrivateProperties=sysPrivatePropertiesManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysPrivateProperties.setId(null);
    		}
    	}else{
    		sysPrivateProperties=new SysPrivateProperties();
    	}
    	return getPathView(request).addObject("sysPrivateProperties",sysPrivateProperties);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysPrivatePropertiesManager;
	}
		
	@RequestMapping("getPrivateProperties")
	@ResponseBody
	public JsonPageResult<SysPrivateProperties> getPrivateProperties(HttpServletRequest request,HttpServletResponse response){
		List<SysProperties> sysProperties=sysPropertiesManager.getGlobalPropertiesByTenantId("1");
		String tenantId=ContextUtil.getCurrentTenantId();
		for (SysProperties commonProperties : sysProperties) {
			String originId=commonProperties.getProId();//获取时不覆盖原先有的,还没写完
			SysPrivateProperties spp=sysPrivatePropertiesManager.getByProId(originId,tenantId);
			if(spp==null){
				SysPrivateProperties sysPrivateProperties=new SysPrivateProperties();
				sysPrivateProperties.setId(idGenerator.getSID());
				sysPrivateProperties.setProId(commonProperties.getProId());
				sysPrivateProperties.setPriValue(commonProperties.getValue());
				sysPrivateProperties.setCreateBy(ContextUtil.getCurrentUserId());
				sysPrivateProperties.setTenantId(ContextUtil.getCurrentTenantId());
				sysPrivatePropertiesManager.create(sysPrivateProperties);
			}
		}
		QueryFilter filter=QueryFilterBuilder.createQueryFilter(request);
		filter.addFieldParam("TENANT_ID_", tenantId);
		List<SysPrivateProperties> tenantProperties=sysPrivatePropertiesManager.getAll(filter);//getAllByTenantId(tenantId, filter);
		JsonPageResult<SysPrivateProperties> jsonPageResult=new JsonPageResult<SysPrivateProperties>(tenantProperties, filter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	@RequestMapping("saveGridData")
	@ResponseBody
	@LogEnt(action = "saveGridData", module = "系统内核", submodule = "系统私有参数")
	public JSONObject saveGridData(HttpServletRequest request,HttpServletResponse response){
		String gridData=RequestUtil.getString(request,"gridData");
		JSONArray jsonArray=JSONArray.parseArray(gridData);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=(JSONObject) jsonArray.get(i);
			jsonObject.remove("_id");
			SysPrivateProperties sysPrivateProperties=jsonObject.toJavaObject(SysPrivateProperties.class);
			sysPrivatePropertiesManager.update(sysPrivateProperties);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("success", true);
		return jsonObject;
	}
	
}
