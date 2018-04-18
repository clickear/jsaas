
package com.redxun.sys.core.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.security.provider.SecurityDataSourceProvider;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.entity.SysInstType;
import com.redxun.sys.core.entity.SysMenu;
import com.redxun.sys.core.entity.SysTypeSubRef;
import com.redxun.sys.core.manager.SubsystemManager;
import com.redxun.sys.core.manager.SysInstTypeManager;
import com.redxun.sys.core.manager.SysMenuManager;
import com.redxun.sys.core.manager.SysTypeSubRefManager;
import com.redxun.sys.log.LogEnt;

/**
 * 机构类型控制器
 * @author mansan
 */
@Controller
@RequestMapping("/sys/core/sysInstType/")
public class SysInstTypeController extends BaseMybatisListController{
    @Resource
    SysInstTypeManager sysInstTypeManager;
    @Resource
    SysTypeSubRefManager sysTypeSubRefManager;
   
    @Resource
    SecurityDataSourceProvider securityDataSourceProvider;
    
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "机构类型")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        StringBuffer sb=new StringBuffer();
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	SysInstType type=sysInstTypeManager.get(id);
            	if(type!=null && !"YES".equals(type.getIsDefault())){
            		sysInstTypeManager.delete(id);
            		//删除对应的子系统
            		sysTypeSubRefManager.deleteByInstType(id);
            	} else {
            		sb.append(type.getTypeName()+"是默认机构,不能删除！");
            	}
            }
        }
        securityDataSourceProvider.reloadSecurityDataCache();
        return new JsonResult(true,"成功操作! "+sb.toString());
    }
    
    /**
     * 获得所有的有效机构类型列表
     * @return
     */
    @RequestMapping("getAllValids")
    @ResponseBody
    public List<SysInstType> getAllValids(HttpServletRequest request,HttpServletResponse response){
    	ITenant curTenant=ContextUtil.getTenant();
    	if(ITenant.ADMIN_TENANT_ID.equals(curTenant.getTenantId())){
        	String exludePlatform=request.getParameter("excludePlatform");
        	if("true".equals(exludePlatform)){
        		return sysInstTypeManager.getValidExludePlatform();
        	}else{
        		return sysInstTypeManager.getValidAll();
        	}
    	}else{
    		SysInstType sysInstType= sysInstTypeManager.getByCode(curTenant.getInstType());
    		List<SysInstType> instTypeList=new ArrayList<SysInstType>();
    		instTypeList.add(sysInstType);
    		return instTypeList;
    	}
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
        SysInstType sysInstType=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysInstType=sysInstTypeManager.get(pkId);
        }else{
        	sysInstType=new SysInstType();
        }
        return getPathView(request).addObject("sysInstType",sysInstType);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysInstType sysInstType=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysInstType=sysInstTypeManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysInstType.setTypeId(null);
    		}
    	}else{
    		sysInstType=new SysInstType();
    	}
    	return getPathView(request).addObject("sysInstType",sysInstType);
    }
    
    @RequestMapping("selectDialog")
    public ModelAndView selectDialog(HttpServletRequest request,HttpServletResponse response){
    	String subSysId=RequestUtil.getString(request, "subSysId");
    	return this.getPathView(request).addObject("subSysId", subSysId);
    }
    
    @RequestMapping("showInstTypes")
    @ResponseBody
    public List<SysInstType> showInstTypes(HttpServletRequest request,HttpServletResponse response){
    	String subSysId=RequestUtil.getString(request, "subSysId");
    	List<SysInstType> sysInstTypes=getAllValids(request, response);
    	List<SysTypeSubRef> sysTypeSubRefs=sysTypeSubRefManager.getByInstId(subSysId);
    	
    	for (SysInstType sysInstType : sysInstTypes) {
    		sysInstType.setEnabled(null);
    		for (SysTypeSubRef sysTypeSubRef : sysTypeSubRefs) {
    			String checkId=sysTypeSubRef.getInstTypeId();
    			String existId=sysInstType.getTypeId();
    			if(checkId.equals(existId)){
    				sysInstType.setEnabled("checked");
    			}
    		}
		}
    	return sysInstTypes;
    }
    
    
   
    
    
    
	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysInstTypeManager;
	}

}
