package com.redxun.pub.org.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
/**
 * 
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/pub/org/user")
public class UserController {
	@Resource(name="iJson")
	ObjectMapper objectMapper;
	@Resource
	UserService userService ;
	
	@RequestMapping("getUserJsons")
	@ResponseBody
	public ArrayNode getUserJsons(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userIds=request.getParameter("userIds");
		String[]uIds=userIds.split("[,]");
		Set<String> uIdSet=new HashSet<String>();
		uIdSet.addAll(Arrays.asList(uIds));
		ArrayNode arrayNode=objectMapper.createArrayNode();
		for(String uid:uIdSet){
			IUser osUser=userService.getByUserId(uid);
			if(osUser!=null){
				ObjectNode objNode=objectMapper.createObjectNode();
				objNode.put("userId", osUser.getUserId());
				objNode.put("fullname", osUser.getFullname());
				arrayNode.add(objNode);
			}
		}
		return arrayNode;
	}
	
	@RequestMapping("getUser")
	@ResponseBody
	public IUser getUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId=request.getParameter("userId");
		IUser osUser=userService.getByUserId(userId);
		return osUser;		
	}
}
