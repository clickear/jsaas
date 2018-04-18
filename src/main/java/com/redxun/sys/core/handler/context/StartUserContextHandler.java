package com.redxun.sys.core.handler.context;

import java.util.Map;

import javax.annotation.Resource;

import com.redxun.core.util.BeanUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.sys.api.ContextHandler;

public class StartUserContextHandler implements ContextHandler {
	
	@Resource
	UserService userService;

	@Override
	public String getKey() {
		return  "[STARTUSER]";
	}

	@Override
	public String getName() {
		return "发起人";
	}

	@Override
	public Object getValue(Map<String, Object> vars) {
		if(BeanUtil.isEmpty(vars)) return "";
		if(vars.containsKey("startUserId")){
			String userId= (String) vars.get("startUserId");
			IUser user=userService.getByUserId(userId);
			return user.getFullname();
		}
		return "";
	}

}
