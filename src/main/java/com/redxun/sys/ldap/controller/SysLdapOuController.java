package com.redxun.sys.ldap.controller;

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
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.ldap.entity.SysLdapOu;
import com.redxun.sys.ldap.manager.SysLdapOuManager;

/**
 * LDAP部门管理
 * @author csx
 */
@Controller
@RequestMapping("/sys/ldap/sysLdapOu/")
public class SysLdapOuController extends BaseListController{
    @Resource
    SysLdapOuManager sysLdapOuManager;
   

   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysLdapOuManager.delete(id);
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
        SysLdapOu sysLdapOu=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysLdapOu=sysLdapOuManager.get(pkId);
        }else{
        	sysLdapOu=new SysLdapOu();
        }
        return getPathView(request).addObject("sysLdapOu",sysLdapOu);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysLdapOu sysLdapOu=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysLdapOu=sysLdapOuManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysLdapOu.setSysLdapOuId(null);
    		}
    	}else{
    		sysLdapOu=new SysLdapOu();
    	}
    	return getPathView(request).addObject("sysLdapOu",sysLdapOu);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysLdapOuManager;
	}

}
