package com.redxun.pub.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.controller.BaseController;
/**
 *  基础服务类,用于提供系统中一些常用的转化服务
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/pub/base/baseService/")
public class BaseServiceController extends BaseController{
	/**
	 * 获得请求的拼音值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getPinyin")
	@ResponseBody
	public JsonResult getPinyin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//必须传入的拼音值
		String words=request.getParameter("words");
		//是否返回汉字拼音的首字母
		String isHead=request.getParameter("isHead");
		//是否返回大小
		String isCap=request.getParameter("isCap");

		if(StringUtils.isEmpty(isHead)){
			isHead="false";
		}
		
		if(StringUtils.isEmpty(isCap)){
			isCap="false";
		}
		String pinyin=null;
		if("true".equals(isHead)){
			pinyin=StringUtil.getPinYinHeadChar(words);
		}else{
			pinyin=StringUtil.getPingYin(words);
		}
		
		JsonResult result=new JsonResult(true);
		
		if("true".equals(isCap)){
			result.setData(pinyin.toUpperCase());
		}else{
			result.setData(pinyin);
		}
		return result;
	}
}
