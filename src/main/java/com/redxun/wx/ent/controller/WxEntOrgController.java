package com.redxun.wx.ent.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.wx.ent.manager.WxEntOrgManager;
import com.redxun.wx.ent.util.model.WxUser;

@Controller
@RequestMapping("/wx/ent/wxEntOrg/")
public class WxEntOrgController {

	@Resource
	WxEntOrgManager wxEntOrgManager ; 
	
	@RequestMapping("syncOrgUser")
	@ResponseBody
    public JsonResult<Void> list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//TODO 获取选中的用户，并绑定为租户下的用户
		String userIds = RequestUtil.getString(request, "userIds");
		String[] userIdsArray = null;
		if(StringUtils.isNotBlank(userIds)){
			userIdsArray = userIds.split(",");
		}
		 
		List<String> choiceIds = null;	
		if(userIdsArray!=null&&userIdsArray.length>0){
			choiceIds = Arrays.asList(userIds.split(","));
		}
		
		
		String tenantId=ContextUtil.getCurrentTenantId();
		JsonResult<List<String>> jsonResult= wxEntOrgManager.syncOrg(tenantId,choiceIds);
		
		JsonResult<Void> result=new JsonResult<Void>(true,"同步用户成功!");
		if(!jsonResult.isSuccess()){
			List<String> list=jsonResult.getData();
			String msg=StringUtil.join(list, ",");
			result.setMessage(msg);
			result.setSuccess(false);
		}
		return result;
    	
    }
	
	
	public static void main(String[] args){
		int a = 1;
		System.out.println(++a);
	}
}
