package com.redxun.sys.org.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户组授权访问的子系统
 * @author 
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Entity
@Table(name = "OS_GROUP_SYS")
public class OsGroupSys implements Serializable{
	@Id
	@Column(name = "ID_")
	private String id;
	//用户组ID
	@Column(name = "GROUP_ID_")
	private String groupId;
	
	//子系统ID
	@Column(name = "SYS_ID_")
	private String sysId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public OsGroupSys() {
	
	}
	
	public OsGroupSys(String id,String groupId,String sysId){
		this.id=id;
		this.groupId=groupId;
		this.sysId=sysId;
	}
}
