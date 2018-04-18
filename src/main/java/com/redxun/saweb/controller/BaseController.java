package com.redxun.saweb.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 * @author csx
 */
public abstract class BaseController extends GenericController{
	
	
	
	/**
	 * 查找对应名称的Cookie
	 * @param request
	 * @param cookieName
	 * @return 
	 * Cookie
	 * @exception 
	 * @since  1.0.0
	 */
	public Cookie getCookie(HttpServletRequest request,String cookieName){
		Cookie[] cookies=request.getCookies();
		for(Cookie ck:cookies){
			if(ck.getName().equals(cookieName)){
				return ck;
			}
		}
		return null;
	}
	
	
	

}
