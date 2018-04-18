package com.redxun.sys.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;

/**
 * 账号管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysAccount/")
public class SysAccountController extends TenantListController{
    @Resource
    SysAccountManager sysAccountManager;
    @Resource
    SysInstManager sysInstManager;
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "系统账号")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysAccountManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
 
    @Override
    protected QueryFilter getQueryFilter(HttpServletRequest request) {
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	String tenantId=getCurTenantId(request);
    	queryFilter.addFieldParam("tenantId", tenantId);
    	return queryFilter;
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
        SysAccount sysAccount=null;
        if(StringUtils.isNotBlank(pkId)){
           sysAccount=sysAccountManager.get(pkId);
        }else{
        	sysAccount=new SysAccount();
        }
        return getPathView(request).addObject("sysAccount",sysAccount);
    }
    
    /**
     * 修改密码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("modifyPassword")
    @ResponseBody
    @LogEnt(action = "modifyPassword", module = "系统内核", submodule = "子系统")
    public JsonResult modifyPassword(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String accountId=request.getParameter("accountId");
    	String password=request.getParameter("password");
    	SysAccount sysAccount=sysAccountManager.get(accountId);
    	sysAccount.setPwd(EncryptUtil.encryptSha256(password.trim()));
    	sysAccountManager.saveOrUpdate(sysAccount);
    	return new JsonResult(true,"成功修改密码！");
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysAccount sysAccount=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysAccount=sysAccountManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysAccount.setAccountId(null);
    		}
    	}else{
    		sysAccount=new SysAccount();
    	}
    	
    	String tenantId=request.getParameter("tenantId");
    	String domain=null;
    	if(StringUtils.isNotEmpty(tenantId)){
    		SysInst sysInst=sysInstManager.get(tenantId);
    		if(sysInst!=null){
    			domain=sysInst.getDomain();
    		}
    	}else{
    		domain=ContextUtil.getTenant().getDomain();
    	}
    	
    	return getPathView(request)
    			.addObject("sysAccount",sysAccount).addObject("domain",domain);
    }
    
    /**
     * 用户修改用户密码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("editPwd")
    @ResponseBody
    @LogEnt(action = "editPwd", module = "系统内核", submodule = "子系统")
    public JsonResult editPwd(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String oldpwd=request.getParameter("oldPwd");
    	String password=request.getParameter("newPwd");
    	String userId=ContextUtil.getCurrentUserId();
    	SysAccount sysAccount=sysAccountManager.getByUserId(userId).get(0);
    	if(!(EncryptUtil.encryptSha256(oldpwd.trim()).equals(sysAccount.getPwd())))
    		return new JsonResult(true, "你输入的原始密码错误！","false");
    	if(EncryptUtil.encryptSha256(password.trim()).equals(sysAccount.getPwd()))
    		return new JsonResult(true, "新密码和原始密码不能一样！","false");
    	sysAccount.setPwd(EncryptUtil.encryptSha256(password.trim()));
    	sysAccountManager.saveOrUpdate(sysAccount);
    	return new JsonResult(true,"成功修改密码！","true");
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysAccountManager;
	}

}
