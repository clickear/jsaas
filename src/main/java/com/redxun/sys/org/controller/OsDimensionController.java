package com.redxun.sys.org.controller;

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

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsRelTypeManager;

/**
 * 用户的维度管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@RequestMapping("/sys/org/osDimension/")
@Controller
public class OsDimensionController extends TenantListController{
	@Resource
	protected OsDimensionManager osDimensionManager;
	@Resource
	protected SysInstManager sysInstManager;
	@Resource
	OsRelTypeManager osRelTypeManager;
	
	protected SysInst getCurSysInst(HttpServletRequest request){
		String tenantId=getCurTenantId(request);
		return sysInstManager.get(tenantId);
	}
	
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		//加上租户过滤功能
		queryFilter.addTenantParam(ContextUtil.getCurrentTenantId());
		return queryFilter;
	}
	
	/**
	 * 取得某维度下的关系
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listRelsByDimId")
	@ResponseBody
	public JsonPageResult<OsRelType> listRelsByDimId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dimId=request.getParameter("dimId");
		List<OsRelType> relTypeList=osRelTypeManager.getByDimIdRelType(dimId);
		return new JsonPageResult<OsRelType>(relTypeList, relTypeList.size());
	}
	
	/**
	 * 进入维度关系管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("relsMgr")
	public ModelAndView relsMgr(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dimId=request.getParameter("dimId");
		OsDimension osDimension=osDimensionManager.get(dimId);
		return getPathView(request).addObject("osDimension",osDimension);
	}
	
	@RequestMapping("jsonAll")
	@ResponseBody
	public List<OsDimension> jsonAll(HttpServletRequest request,HttpServletResponse response){
		SysInst sysInst=getCurSysInst(request);
		String containContact=RequestUtil.getString(request, "containContact");
		String excludeAdmin=request.getParameter("excludeAdmin");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		//加上租户的过滤
//		queryFilter.addParam("TENANT_ID_", sysInst.getInstId());
		List<OsDimension> dimList=osDimensionManager.filterateAll("YES", sysInst.getInstId(),queryFilter);
		for (int i = 0; i < dimList.size(); i++) {
			if("true".equals(excludeAdmin)){
				if(OsDimension.DIM_ADMIN_ID.equals(dimList.get(i).getDimId())){
					dimList.remove(i);
					i--;
				}
			}
		}
		if(StringUtils.isNotBlank(containContact)){
			OsDimension topContacts=new OsDimension();
			topContacts.setName("常用联系人");
			topContacts.setDimId("topContacts");
			dimList.add(topContacts);//将虚拟设置的常用联系人加入
		}
		return dimList;
	}

    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	OsDimension osDimension=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		osDimension=osDimensionManager.get(pkId);
    	}else{
    		osDimension=new OsDimension();
    	}
    	SysInst sysInst=getCurSysInst(request);
    	return getPathView(request).addObject("osDimension",osDimension).addObject("sysInst", sysInst);
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
        OsDimension osDimension=null;
        if(StringUtils.isNotBlank(pkId)){
           osDimension=osDimensionManager.get(pkId);
        }else{
        	osDimension=new OsDimension();
        }
        return getPathView(request).addObject("osDimension",osDimension);
    }
    
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "组织架构", submodule = "组织维度")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        StringBuffer dimNames=new StringBuffer();
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	OsDimension osDimension=osDimensionManager.get(id);
            	dimNames.append(osDimension.getName()).append(",");
                try {
                	osDimensionManager.delete(id);
				} catch (Exception e) {
					return new JsonResult(false,"删除前请清空其绑定关系");
				}
            }
        }
        if(dimNames.length()>0){
        	dimNames.deleteCharAt(dimNames.length()-1);
        }
        return new JsonResult(true,"成功删除维度"+dimNames.toString()+"!");
    }

	@Override
	public BaseManager getBaseManager() {
		return osDimensionManager;
	}
	
	@RequestMapping("getAllByRight")
	@ResponseBody
	public JsonPageResult<OsDimension> getAllByRight(HttpServletRequest request,HttpServletResponse response){
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		String tenantId=ContextUtil.getCurrentTenantId();
		List<OsDimension> osDimensions=osDimensionManager.filterateAll("YES",tenantId, queryFilter);
		JsonPageResult<OsDimension> jsonPageResult=new JsonPageResult<OsDimension>(osDimensions, queryFilter.getPage().getTotalItems());
		return jsonPageResult;
	}
	
	@RequestMapping("getAllDimansion")
	@ResponseBody
	public List<OsDimension> getAllDimansion(HttpServletRequest request,HttpServletResponse response){
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		String tenantId=SysInst.PUBLIC_TENANT_ID;
		List<OsDimension> osDimensions=new ArrayList<OsDimension>();
		osDimensions.addAll(osDimensionManager.filterateAll("YES",tenantId, queryFilter));
		return osDimensions;
	}
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		Boolean isSuperAdmin=ContextUtil.getCurrentUser().isSuperAdmin();
		return this.getPathView(request).addObject("isSuperAdmin", isSuperAdmin);
	}
}
