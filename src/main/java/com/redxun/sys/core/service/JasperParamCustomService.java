package com.redxun.sys.core.service;

import java.util.Map;
/**
 * 报表参数
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 */

public interface JasperParamCustomService {
	public Map<String, Object> convert(Map<String, Object> params)
			throws Exception;
}
