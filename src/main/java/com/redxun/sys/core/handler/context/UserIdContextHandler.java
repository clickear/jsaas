package com.redxun.sys.core.handler.context;

import java.util.Map;

import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.api.ContextHandler;

public class UserIdContextHandler implements ContextHandler {

	@Override
	public String getKey() {
		return "[USERID]";
	}

	@Override
	public String getName() {
		
		return "用户ID";
	}

	@Override
	public Object getValue(Map<String,Object> vars) {
		return ContextUtil.getCurrentUserId();
	}

}
