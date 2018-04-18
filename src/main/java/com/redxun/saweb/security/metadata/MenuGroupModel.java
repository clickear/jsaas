package com.redxun.saweb.security.metadata;

/**
 * 菜单分组模型
 * @author mansan
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class MenuGroupModel {
	
	private String menuId;
	
	private String groupId;
	
	private String url;
	
	private String groupKey;
	
	public String getMenuId() {
		return menuId;
	}
	
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public MenuGroupModel() {
		
	}
	
	public MenuGroupModel(String menuId,String groupId,String url,String groupKey){
		this.menuId=menuId;
		this.groupId=groupId;
		this.url=url;
		this.groupKey=groupKey;
	}
}
