package com.redxun.sys.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.util.OpenOfficeUtil;

@Controller
@RequestMapping("/sys/core/openoffice/")
public class OpenOfficeController {
	
	@RequestMapping("switchSoffice")
	@ResponseBody
	public JSONObject switchSoffice(HttpServletRequest request,HttpServletResponse response){
		boolean switchParam=RequestUtil.getBoolean(request, "switchParam");
		JSONObject rtnObj=new JSONObject();
		if(switchParam){
			rtnObj.put("success",OpenOfficeUtil.startService());
		}else{
			rtnObj.put("success",OpenOfficeUtil.endService());
		}
		return rtnObj;
	}
	
	@RequestMapping("getConnectStatus")
	@ResponseBody
	public boolean getConnectStatus(HttpServletResponse response,HttpServletRequest request){
		return OpenOfficeUtil.getConnectStatus();
	}

}
