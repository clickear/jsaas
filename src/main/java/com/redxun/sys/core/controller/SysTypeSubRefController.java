
package com.redxun.sys.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.security.provider.ISecurityDataProvider;
import com.redxun.saweb.util.QueryFilterBuilder;
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
 * 机构--子系统关系控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/sys/core/sysTypeSubRef/")
public class SysTypeSubRefController extends BaseMybatisListController{
    @Resource
    SysTypeSubRefManager sysTypeSubRefManager;
    @Resource
    SysInstTypeManager sysInstTypeManager;
    @Resource
    SysMenuManager sysMenuManager;
    @Resource
    SubsystemManager subsystemManager;

    @Resource
    ISecurityDataProvider securityDataProvider;
    
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysTypeSubRefManager.delete(id);
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
        SysTypeSubRef sysTypeSubRef=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysTypeSubRef=sysTypeSubRefManager.get(pkId);
        }else{
        	sysTypeSubRef=new SysTypeSubRef();
        }
        return getPathView(request).addObject("sysTypeSubRef",sysTypeSubRef);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysTypeSubRef sysTypeSubRef=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysTypeSubRef=sysTypeSubRefManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysTypeSubRef.setId(null);
    		}
    	}else{
    		sysTypeSubRef=new SysTypeSubRef();
    	}
    	return getPathView(request).addObject("sysTypeSubRef",sysTypeSubRef);
    }
    
    /**
     * 列出该机构的所有子类型
     * @param request
     * @param response
     * @return
     */
    //TODO 细化到菜单
    @RequestMapping("allotSubSys")
    public ModelAndView allotSubSys(HttpServletRequest request,HttpServletResponse response){
    	String typeId=RequestUtil.getString(request, "typeId");
    	return this.getPathView(request).addObject("typeId", typeId);
    }
    
    @RequestMapping("getAncientConfig")
    @ResponseBody
    public List<SysTypeSubRef> getAncientConfig(HttpServletRequest request,HttpServletResponse response){
    	String typeId=RequestUtil.getString(request, "typeId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("INST_TYPE_ID_", typeId);
    	List<SysTypeSubRef> sysTypeSubRefs=sysTypeSubRefManager.getAll(queryFilter);
    	return sysTypeSubRefs;
    }
     
    @RequestMapping("saveTypeConfig")
    @ResponseBody
    public JSONObject saveTypeConfig(HttpServletRequest request,HttpServletResponse response){
    	String typeId=RequestUtil.getString(request, "typeId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("INST_TYPE_ID_", typeId);
    	List<SysTypeSubRef> sysTypeSubRefs=sysTypeSubRefManager.getAll(queryFilter);
    	for (SysTypeSubRef sysTypeSubRef : sysTypeSubRefs) {
			sysTypeSubRefManager.delete(sysTypeSubRef.getId());
		}
    	String subSys=RequestUtil.getString(request, "subSys");
    	JSONArray subSysArray=JSONArray.parseArray(subSys);
    	for (int i = 0; i < subSysArray.size(); i++) {
			JSONObject subSysObj=(JSONObject) subSysArray.get(i);
			String subSysId=subSysObj.getString("pkId");
			SysTypeSubRef sysTypeSubRef=new SysTypeSubRef();
			sysTypeSubRef.setId(idGenerator.getSID());
			sysTypeSubRef.setInstTypeId(typeId);
			sysTypeSubRef.setSubSysId(subSysId);
			sysTypeSubRefManager.create(sysTypeSubRef);
		}
    	JSONObject jsonObject=new JSONObject();
    	jsonObject.put("success", true);
    	return jsonObject;
    }
    
    
    
    @RequestMapping("saveInstMenusRef")
    @ResponseBody
    public JSONObject saveInstMenusRef(HttpServletRequest request,HttpServletResponse response){
    	String typeId=RequestUtil.getString(request, "typeId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("INST_TYPE_ID_", typeId);
    	List<SysTypeSubRef> sysTypeSubRefs=sysTypeSubRefManager.getAll(queryFilter);
    	for (SysTypeSubRef sysTypeSubRef : sysTypeSubRefs) {
			sysTypeSubRefManager.delete(sysTypeSubRef.getId());
		}
    	String subSys=RequestUtil.getString(request, "subSys");
    	JSONArray subSysArray=JSONArray.parseArray(subSys);
    	for (int i = 0; i < subSysArray.size(); i++) {
			JSONObject subSysObj=(JSONObject) subSysArray.get(i);
			String subSysId=subSysObj.getString("pkId");
			SysTypeSubRef sysTypeSubRef=new SysTypeSubRef();
			sysTypeSubRef.setId(idGenerator.getSID());
			sysTypeSubRef.setInstTypeId(typeId);
			sysTypeSubRef.setSubSysId(subSysId);
			sysTypeSubRefManager.create(sysTypeSubRef);
		}
    	JSONObject jsonObject=new JSONObject();
    	jsonObject.put("success", true);
    	return jsonObject;
    }
    
    
    
   
    @RequestMapping("addRef")
    @ResponseBody
    public JSONObject addRef(HttpServletRequest request,HttpServletResponse response){
    	JSONObject rtnObj=new JSONObject();
    	String typeIds=RequestUtil.getString(request, "typeIds");
    	String subSysId=RequestUtil.getString(request, "subSysId");
    	List<SysTypeSubRef> existRef=sysTypeSubRefManager.getByInstId(subSysId);
    	for (SysTypeSubRef sysTypeSubRef : existRef) {//删除所有这个子系统的授权
    		sysTypeSubRefManager.delete(sysTypeSubRef.getId());
		}
    	JSONArray typeIdArray=JSONArray.parseArray(typeIds);
    	for (int i = 0; i < typeIdArray.size(); i++) {
    		String typeId=typeIdArray.getString(i);
    			SysTypeSubRef sysTypeSubRef=new SysTypeSubRef();
    			sysTypeSubRef.setId(idGenerator.getSID());
    			sysTypeSubRef.setInstTypeId(typeId);
    			sysTypeSubRef.setSubSysId(subSysId);
    			sysTypeSubRefManager.create(sysTypeSubRef);
		}
    	rtnObj.put("success", true);
    	return rtnObj;
    }
    
    
    //获取所有子系统-菜单树
    @RequestMapping("getAllMenus")
	@ResponseBody
	public List<JSONObject> getAllMenus(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		
		List<JSONObject> menus=new ArrayList<JSONObject>();		
		List<Subsystem> subsystems=subsystemManager.getByValidSystem();
		for(Subsystem system:subsystems){
			JSONObject topMenu=new JSONObject();
			topMenu.put("menuId", system.getSysId());
			topMenu.put("name", system.getName());
			topMenu.put("parentId", "0");
			//加上授权子系统
			menus.add(topMenu);	
			List<SysMenu> subMenus=sysMenuManager.getBySysId(system.getSysId());
			for(SysMenu menu:subMenus){
				
				JSONObject subMenu=new JSONObject();
				subMenu.put("menuId", menu.getMenuId());
				subMenu.put("name", menu.getName());
				subMenu.put("parentId", menu.getParentId());				
				menus.add(subMenu);
			}
		}
		return menus;
	}
    
    
    @RequestMapping("saveGrant")
	@ResponseBody
	public JsonResult saveGrant(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String typeId=request.getParameter("typeId");
		String menuIds=request.getParameter("menuIds");
		String sysIds=request.getParameter("sysIds");
		
		//带根节点的菜单及Id
		Set<String> includeSysIdMenuIds=StringUtil.toSet(menuIds);
		//子系统的Id
		Set<String> sysIdSet=StringUtil.toSet(sysIds);
		//获得有效的允许访问的子系统
		includeSysIdMenuIds.removeAll(sysIdSet);
		//保存授权 菜单，包括子系统
		sysTypeSubRefManager.saveGrantMenus(typeId,includeSysIdMenuIds,sysIdSet);
		//重新加载缓存菜单映射
		securityDataProvider.reloadTenantMenuUrls();
		
		return new JsonResult(true,"成功进行机构类型菜单授权!");
	}
 
	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return sysTypeSubRefManager;
	}
	
	
	/**
	 * 获得授权的资源ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrantedResourceIds")
	@ResponseBody
	public JsonResult getGrantedResourceIds(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String typeId=request.getParameter("typeId");
		List<SysMenu> grantMenus=sysMenuManager.getGrantMenusByTypeId(typeId);
		StringBuffer sb=new StringBuffer();
		for(SysMenu menu:grantMenus){		
			sb.append(menu.getMenuId()).append(",");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return new JsonResult(true,"成功查询",sb.toString());
	}
	
	
	
	
	

}
