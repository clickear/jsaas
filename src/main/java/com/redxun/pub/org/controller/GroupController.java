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
import com.redxun.org.api.model.IGroup;
import com.redxun.org.api.service.GroupService;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

/**
 * 用户组控制器
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/pub/org/group")
public class GroupController {
	@Resource(name="iJson")
	ObjectMapper objectMapper;
	@Resource
	GroupService groupService;
	
	/**
	 * 获得用户组的信息，一般显示用户组名即可
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGroupJsons")
	@ResponseBody
	public ArrayNode getGroupJsons(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String gIds=request.getParameter("groupIds");
		String[]uIds=gIds.split("[,]");
		Set<String> uIdSet=new HashSet<String>();
		uIdSet.addAll(Arrays.asList(uIds));
		ArrayNode arrayNode=objectMapper.createArrayNode();
		for(String uid:uIdSet){
			IGroup osGroup=groupService.getById(uid);
			if(osGroup!=null){
				ObjectNode objNode=objectMapper.createObjectNode();
				objNode.put("groupId", osGroup.getIdentityId());
				objNode.put("name", osGroup.getIdentityName());
				arrayNode.add(objNode);
			}
		}
		return arrayNode;
	}
	
	
	@RequestMapping("getGroup")
	@ResponseBody
	public IGroup getGroup(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String groupId=request.getParameter("groupId");
		IGroup osGroup=groupService.getById(groupId);
		return osGroup;		
	}
}
