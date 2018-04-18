package com.redxun.saweb.security.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.redxun.core.util.StringUtil;
import com.redxun.sys.core.entity.SysInstType;
import com.redxun.sys.core.entity.SysMenu;
import com.redxun.sys.core.manager.SysInstTypeManager;
import com.redxun.sys.core.manager.SysMenuManager;

/**
 *  安全数据构建的Provider
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 *
 */
public class SecurityDataSourceProvider implements ISecurityDataProvider {
	
	@Resource
	private SysInstTypeManager sysInstTypeManager;
	
	private SysMenuManager sysMenuManager;
	
	public void setSysMenuManager(SysMenuManager sysMenuManager) {
		this.sysMenuManager = sysMenuManager;
	}

	public void setAnonymousUrls(Set<String> anonymousUrls) {
		this.anonymousUrls = anonymousUrls;
	}

	public void setPublicUrls(Set<String> publicUrls) {
		this.publicUrls = publicUrls;
	}

	/**
	 * 匿名访问的URLs
	 */
	private Set<String> anonymousUrls=null;
	/**
	 * 登录访问的共公URLs
	 */
	private Set<String> publicUrls=null;
	
	/**
	 * 角色权限映射列表源，用于权限的匹配
	 */
	private Map<String, Set<String>> menuGroupIdsMap=null;
	/**
	 *租用机构下的所有菜单URL
	 */
	private Map<String,  Set<String>> tenantUrlSet=null;

	
	public Set<String> getAnonymousUrls() {
		return anonymousUrls;
	}

	public Map<String,  Set<String>> getTenantUrlSet() {
		return tenantUrlSet;
	}

	public Set<String> getPublicUrls() {
		return publicUrls;
	}

	public void reloadMenuGroupIdsMap(){
		menuGroupIdsMap=sysMenuManager.getUrlGroupIdMap();
	}
	
	public void reloadSecurityDataCache(){
		reloadMenuGroupIdsMap();
		reloadTenantMenuUrls();
	}
	
	public Map<String, Set<String>> getMenuGroupIdsMap() {
		return menuGroupIdsMap;
	}

	
	public void reloadTenantMenuUrls(){
		tenantUrlSet=new HashMap<String, Set<String>>();
		List<SysInstType> instTypes = sysInstTypeManager.getValidAll();
		for(SysInstType instType:instTypes){
			String type=instType.getTypeCode();
			Set<String> urlSet=new HashSet<String>();
			List<SysMenu> menus= sysMenuManager.getByInstType(type);
			for(SysMenu menu:menus){
				if(StringUtil.isEmpty(menu.getUrl())) continue;
				urlSet.add(menu.getUrl());
			}
			tenantUrlSet.put(type, urlSet);
		}
	}
}
