package com.redxun.restApi.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.constants.MStatus;
import com.redxun.core.util.EncryptUtil;
import com.redxun.org.api.service.UserService;
import com.redxun.restApi.auth.entity.LoginEntity;
import com.redxun.sys.org.entity.OsUser;

/**
 * 统一认证登录
 * @author mansan
 * 前置路径为/rest/
 */
@RestController
@RequestMapping("/auth/")
public class AuthLoginController {
	
	private Log logger=LogFactory.getLog(AuthLoginController.class);
	
	@Autowired
	UserService userService;

	/**
	 * 
	 * @param accountName
	 * @param passWord
	 * @param reLogin
	 * @param type
	 * @param request
	 * @return ,method = RequestMethod.POST,consumes="application/json" @RequestBody LoginEntity loginEntity
	 * @throws Exception
	 */
	@RequestMapping(value="login",method = RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public JSONObject login(@RequestBody LoginEntity loginEntity,HttpServletRequest request)throws Exception{
		JSONObject result=new JSONObject();
		result.put("flag", "");
		OsUser user=(OsUser) userService.getByUsername(loginEntity.getAccountName());
        //启用SAAS模式
    	if(user==null || !(user.getUsername()).equals(loginEntity.getAccountName()) || 
    			!user.getPwd().equals(EncryptUtil.hexToBase64(loginEntity.getPassWord().trim()))){
    		result.put("result", "ERROR");
    		result.put("message", "密码或用户名不正确！");
    		return result;
        }
        if(user.getTenant()==null || !MStatus.ENABLED.toString().equals(user.getTenant().getStatus())){
        	result.put("result", "ERROR");
    		result.put("message", "企业机构已经被禁用！");
    		return result;
        }

		result.put("message", "登录成功！");
		result.put("result", "SUCCESS");
		result.put("data", request.getSession().getId());

		return result;
	}
}
