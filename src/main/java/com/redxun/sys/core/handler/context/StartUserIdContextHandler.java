package com.redxun.sys.core.handler.context;

import java.util.Map;

import com.redxun.core.util.BeanUtil;
import com.redxun.sys.api.ContextHandler;

public class StartUserIdContextHandler implements ContextHandler {

	@Override
	public String getKey() {
		return  "[STARTUSERID]";
	}

	@Override
	public String getName() {
		return "发起人ID";
	}

	@Override
	public Object getValue(Map<String, Object> vars) {
		if(BeanUtil.isEmpty(vars)) return "";
		if(vars.containsKey("startUserId")){
			return vars.get("startUserId");
		}
		return "";
	}

}
