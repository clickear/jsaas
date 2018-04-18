package com.redxun.pub.anony.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
/**
 * 系统实例注册Controller
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/pub/anony/sysInst/")
public class SysInstRegController extends BaseController{
	@Resource 
	SysInstManager sysInstManager;
	
	/**
	 * 注册成功
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("regSuccess")
	public ModelAndView regSuccess(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String instId=request.getParameter("instId");
		SysInst sysInst=sysInstManager.get(instId);
		return getPathView(request).addObject("sysInst",sysInst)
				.addObject("loginUrl",WebAppUtil.getInstallHost()+"/login.jsp");
	}
}
