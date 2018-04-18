package com.redxun.wx.core.entity;

public class WxTagAndSubscribe {
	/**
	 * 标识符
	 */
	private String id;
	/**
	 * 类型 标签还是用户
	 */
	private String type;
	/**
	 * 父Id
	 */
	private String parentId;
	/**
	 * 显示值
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
