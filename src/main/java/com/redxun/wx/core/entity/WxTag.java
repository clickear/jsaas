package com.redxun.wx.core.entity;

public class WxTag {
	/**
	 * tag的唯一标识
	 */
	private String id;
	/**
	 * tag的名称
	 */
	private String name;
	/**
	 * tag下的数量
	 */
	private String count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}
