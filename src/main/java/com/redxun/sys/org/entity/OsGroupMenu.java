package com.redxun.sys.org.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  授权访问的菜单
 * @author csx
 *@Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Entity
@Table(name = "OS_GROUP_MENU")
public class OsGroupMenu implements Serializable{
	@Id
	@Column(name = "ID_")
	private String id;
	//用户组ID
	@Column(name = "GROUP_ID_")
	private String groupId;
	
	//菜单ID
	@Column(name = "MENU_ID_")
	private String menuId;
	
	//菜单ID
	@Column(name = "SYS_ID_")
	private String sysId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public OsGroupMenu() {
	
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OsGroupMenu(String id,String groupId,String menuId,String sysId){
		this.id=id;
		this.groupId=groupId;
		this.menuId=menuId;
		this.sysId=sysId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
	
}
