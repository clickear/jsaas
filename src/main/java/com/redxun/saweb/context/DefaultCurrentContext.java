package com.redxun.saweb.context;

import com.redxun.org.api.context.CurrentContext;
import com.redxun.org.api.model.ITenant;
import com.redxun.org.api.model.IUser;
/**
 * 缺省上下文
 * @author mansan
 *@Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class DefaultCurrentContext implements CurrentContext{

	@Override
	public IUser getCurrentUser() {
		return ContextUtil.getCurrentUser();
	}
	
	@Override
	public String getCurrentTenantId() {
		return ContextUtil.getCurrentTenantId();
	}
	
	@Override
	public ITenant getCurrentTenant() {
		return ContextUtil.getTenant();
	}

}
