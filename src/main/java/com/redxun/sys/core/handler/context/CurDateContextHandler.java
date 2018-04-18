package com.redxun.sys.core.handler.context;

import java.util.Date;
import java.util.Map;

import com.redxun.sys.api.ContextHandler;

public class CurDateContextHandler implements ContextHandler {

	@Override
	public String getKey() {
		return "[CURDATE]";
	}

	@Override
	public String getName() {
		return "当前时间";
	}

	@Override
	public Object getValue(Map<String,Object> vars) {
		return new Date();
	}

}
