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

import com.redxun.core.constants.MStatus;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;

/**
 * 注册组织机构管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysInst/")
public class SysInstController extends BaseListController{
    @Resource
    SysInstManager sysInstManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "注册机构")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	SysInst sysInst=sysInstManager.get(id);
            	if(sysInst==null) continue;
            	if(WebAppUtil.getOrgMgrDomain().equals(sysInst.getDomain())){
            		return new JsonResult(false,"超级管理机构不允许删除！");
            	}else if(!MStatus.INIT.toString().equals(sysInst.getStatus())){
            		return new JsonResult(false,"仅允许删除INIT状态的机构！");
            	}
            	sysInstManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 获得对话框的信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listForDialog")
    @ResponseBody
    public JsonPageResult<SysInst> listForDialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	QueryFilter filter=QueryFilterBuilder.createQueryFilter(request);
    	List<SysInst> insts=sysInstManager.getAll(filter);
    	return new JsonPageResult(insts,filter.getPage().getTotalItems());
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
        SysInst inst=null;
        if(StringUtils.isNotBlank(pkId)){
           inst=sysInstManager.get(pkId);
        }else{
        	inst=new SysInst();
        }
        return getPathView(request).addObject("sysInst",inst);
    }
    
    /**
     * 获得单条记录
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("getRecord")
    @ResponseBody
    public SysInst getRecord(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        SysInst inst=null;
        if(StringUtils.isNotBlank(pkId)){
           inst=sysInstManager.get(pkId);
        }
        return inst;
    }
    
	/**
	 * 判断当前租户是否是平台用户
	 * @return
	 */
    @RequestMapping("isAdmin")
    @ResponseBody
	public boolean isAdmin(){
		String tenantId = ContextUtil.getCurrentTenantId();
		SysInst sysInst = sysInstManager.get(tenantId);
		if(sysInst!=null&&"PLATFORM".equals(sysInst.getInstType())){
			return true;
		}
		return false;
	}
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	SysInst sysInst=null;
    	ModelAndView mv=getPathView(request);
    	if(StringUtils.isNotEmpty(pkId)){
    		sysInst=sysInstManager.get(pkId);
    		if(StringUtils.isNotEmpty(sysInst.getPath()) && StringUtils.isNotEmpty(sysInst.getParentId())){
    			SysInst parentInst=sysInstManager.get(sysInst.getParentId());
    			mv.addObject("parentInst",parentInst);
    		}
    	}else{
    		sysInst=new SysInst();
    	}
    	return mv.addObject("sysInst",sysInst);
    }
    
    @RequestMapping("enable")
    @ResponseBody
    @LogEnt(action = "enable", module = "系统内核", submodule = "注册机构")
    public JsonResult enable(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String ids=request.getParameter("ids");
    	String enable=request.getParameter("enable");
    	if(StringUtils.isEmpty(ids) || StringUtils.isEmpty(enable)){
    		return new JsonResult(false,"提交的参数不正确！");
    	}
    	
    	String[] pkIds=ids.split("[,]");
    	for(String pk:pkIds){
    		SysInst sysInst=sysInstManager.get(pk);
    		if(sysInst!=null){
    			sysInst.setStatus(enable);
    			sysInstManager.update(sysInst);
    		}
    	}
    	
    	return new JsonResult(true,"成功操作！");
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysInstManager;
	}

}
