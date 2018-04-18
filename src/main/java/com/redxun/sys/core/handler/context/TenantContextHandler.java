package com.redxun.sys.core.handler.context;

import java.util.Map;

import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.api.ContextHandler;

public class TenantContextHandler implements ContextHandler {

	@Override
	public String getKey() {
		return "[TENANTID]";
	}

	@Override
	public String getName() {
		return "当前机构ID";
	}

	@Override
	public Object getValue(Map<String,Object> vars) {
		return ContextUtil.getCurrentTenantId();
	}

}
