package com.redxun.sys.core.handler.context;

import java.util.Map;

import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.api.ContextHandler;

public class OsGroupContextHandler implements ContextHandler {

	@Override
	public String getKey() {
		return "[USERGROUP]";
	}

	@Override
	public String getName() {
		return "用户主部门";
	}

	@Override
	public Object getValue(Map<String,Object> vars) {
		return ContextUtil.getCurrentUser().getMainGroupId();
	}

}
