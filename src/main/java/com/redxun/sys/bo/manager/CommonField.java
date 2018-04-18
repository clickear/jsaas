package com.redxun.sys.bo.manager;

public class CommonField {
	
	private String pk="";
	private String refId="";
	private String parentId="";
	
	

	private String userId="";
	private String groupId="";
	
	
	
	public CommonField() {
	}
	
	public CommonField(String userId, String groupId) {
		this.userId = userId;
		this.groupId = groupId;
	}
	
	public CommonField(String pk, String refId, String parentId, String userId, String groupId) {
		this.pk = pk;
		this.refId = refId;
		this.parentId = parentId;
		this.userId = userId;
		this.groupId = groupId;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
