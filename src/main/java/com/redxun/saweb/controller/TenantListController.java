package com.redxun.saweb.controller;

import javax.servlet.http.HttpServletRequest;

import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 *  用于处理带租用ID的列表的Controller
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public abstract class TenantListController extends BaseListController{
	/**
	 * 加上TenantId，即只允许查询自己机构的数据
	 */
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		//加上租户的过滤数据
		queryFilter.addTenantParam(ContextUtil.getCurrentTenantId());
		selfDefQueryFilter(queryFilter);
		return queryFilter;
	}
	/**
	 * 用于继续类自己构建带TenantId查询条件以外的条件，在子类只需要重写即可
	 * @param queryFilter
	 */
	protected void selfDefQueryFilter(QueryFilter queryFilter){
		
	}
}
