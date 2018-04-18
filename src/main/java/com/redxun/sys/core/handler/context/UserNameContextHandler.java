package com.redxun.sys.core.handler.context;

import java.util.Map;

import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.api.ContextHandler;

public class UserNameContextHandler implements ContextHandler {

	@Override
	public String getKey() {
		return "[USERNAME]";
	}

	@Override
	public String getName() {
		return "用户名";
	}

	@Override
	public Object getValue(Map<String,Object> vars) {
		return ContextUtil.getCurrentUser().getFullname();
	}

}
