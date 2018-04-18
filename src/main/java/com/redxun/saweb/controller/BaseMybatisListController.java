package com.redxun.saweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
/**
 * 列表查询控制器
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public abstract class BaseMybatisListController extends AbstractListController {
	
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter filter= QueryFilterBuilder.createQueryFilter(request);
		String tenantId=ContextUtil.getCurrentTenantId();
		filter.addFieldParam("TENANT_ID_", tenantId);
		return filter;
	}


	@SuppressWarnings("rawtypes")
	public abstract ExtBaseManager getBaseManager();

	@Override
	public List<?> getPage(QueryFilter queryFilter) {
		return getBaseManager().getMybatisAll(queryFilter);
	}

	@Override
	public Long getTotalItems(QueryFilter queryFilter) {
		return getBaseManager().getMybatisTotalItems(queryFilter);
	}
	
}
