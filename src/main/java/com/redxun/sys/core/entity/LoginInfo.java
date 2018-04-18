package com.redxun.sys.core.entity;

import java.util.List;

import com.redxun.org.api.model.IUser;
/**
 * 
 * <pre> 
 * 描述：放置用户的登录信息，包括用户、权限、菜单组等0
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年8月3日-下午3:57:17
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class LoginInfo {
	private IUser user=null;
	private List<MenuNode> menuGroups=null;

	public IUser getUser() {
		return user;
	}
	public void setUser(IUser user) {
		this.user = user;
	}
	public List<MenuNode> getMenuGroups() {
		return menuGroups;
	}
	public void setMenuGroups(List<MenuNode> menuGroups) {
		this.menuGroups = menuGroups;
	}
}
