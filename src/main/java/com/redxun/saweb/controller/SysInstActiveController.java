package com.redxun.saweb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MStatus;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
/**
 *  注册企业激活
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/")
public class SysInstActiveController {
	@Resource
	private SysInstManager sysInstManager;
	/**
	 * 激活注册企业
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("activeInst")
	public ModelAndView activeInst(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String instId=request.getParameter("instId");
		if(StringUtils.isEmpty(instId)){
			response.sendRedirect("login.jsp");
		}else{
			SysInst sysInst= sysInstManager.get(instId);
			if(sysInst!=null && MStatus.INIT.toString().equals(sysInst.getStatus())){
				sysInst.setStatus(MStatus.ENABLED.toString());
				sysInstManager.update(sysInst);
				return new ModelAndView("sysIntActive.jsp").addObject("sysInst",sysInst);
			}
		}
		response.sendRedirect("login.jsp");
		return null;
	}
}
