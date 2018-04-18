package com.redxun.sys.core.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.KeyValEnt;
import com.redxun.core.util.CookieUitl;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.saweb.controller.GenericController;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.api.ContextHandlerFactory;

@Controller
@RequestMapping("/sys/core/public/")
public class PublicController extends GenericController{
	
	@Resource
	ContextHandlerFactory contextHandlerFactory;
	
	/**
	 * 设置权限类型对话框。
	 * @param request
	 * @return
	 */
	@RequestMapping("profileDialog")
	public ModelAndView profileDialog(HttpServletRequest request){
		ModelAndView mv=getPathView(request);
		JSONObject typeJson=ProfileUtil.getProfileTypeJson();
		//Set<Entry<String, Object>> set= typeJson.entrySet();
		mv.addObject("typeJson", typeJson);
		return mv;
		
	}
	
	/**
	 * 取得公共提示信息页面。
	 * @param title
	 * @param message
	 * @return
	 */
	public static ModelAndView getTipInfo(String title,String message){
		ModelAndView mv=new ModelAndView("/pub/share/tipInfo.jsp");
		
		mv.addObject("title", title)
		.addObject("message", message);
		
		return mv;
	}
	
	
	@RequestMapping("changeSkin")
	public void changeSkin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String url= RequestUtil.getPrePage(request);
		
		String skin= RequestUtil.getString(request,"skin","default");
		
		CookieUitl.addCookie("skin", skin, true, request, response);
		
		response.sendRedirect(url);
		 
	}
	
	
	@RequestMapping("getConstantItem")
	@ResponseBody
	public List<KeyValEnt> getConstantItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<KeyValEnt> values=contextHandlerFactory.getHandlers();
		return values;
	}
	
	

}
