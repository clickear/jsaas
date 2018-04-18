package com.redxun.saweb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;

/**
 * 
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/")
public class SysInstRegistController extends BaseFormController{
	@Resource
	SysInstManager sysInstManager;
	
	 /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysInst")
    public SysInst processForm(HttpServletRequest request) {
    	SysInst  sysInst = new SysInst();
    	sysInst.setStatus(MBoolean.NO.toString());
        return sysInst;
    }
    
    /**
     * 注册企业
     * @param request
     * @param sysInst
     * @param result
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(HttpServletRequest request, @ModelAttribute("sysInst") @Valid SysInst sysInst, BindingResult result) {
    	//检查验证码
    	String code = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
    	String validCode=request.getParameter("validCode");
    	if(code==null || !code.equals(validCode)){
    		result.rejectValue(null, "valid.code.error","验证码不正确！");
    	}
    	SysInst inst=sysInstManager.getByDomain(sysInst.getDomain());
    	if(inst!=null){
    		result.rejectValue("domain", "sysInst.domain", "该域名【"+sysInst.getDomain()+"】已经被注册使用！");
    	}
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
       
       sysInst.setInstId(idGenerator.getSID());
       sysInstManager.doRegister(sysInst);
       String  msg = getMessage("sysInst.created", new Object[]{sysInst.getIdentifyLabel()}, "成功注册企业!");
        
        return new JsonResult(true, msg,sysInst);
    }
}
