package com.redxun.sys.org.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.redxun.saweb.controller.BaseController;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
/**
 * 基础组织服务
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class BaseOrgController extends BaseController{
	@Resource
	protected SysInstManager sysInstManager;
	/**
	 * 获得当前有效的操作组织机构
	 * @param request
	 * @return
	 */
	protected SysInst getCurSysInst(HttpServletRequest request){
		String instId=getCurTenantId(request);
		return sysInstManager.get(instId);
	}
}
