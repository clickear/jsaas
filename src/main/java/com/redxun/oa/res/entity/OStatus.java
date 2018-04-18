package com.redxun.oa.res.entity;

/**
 * 状态常量
 * @author csx
 *
 */
public enum OStatus {
	/**
	 * 在使用状态
	 */
	INUSED("INUSED"),
	/**
	 * 空闲状态
	 */
	INFREE("INFREE"),
	/**
	 * 报废状态
	 */
	SCRAP("SCRAP"),
	/**
	 * 未审批状态
	 */
	NOTAPPROVED("NOTAPPROVED"),
	/**
	 * 审批中状态
	 */
	APPROVEDING("APPROVEDING"),
	/**
	 * 审批通过状态
	 */
	APPROVED("APPROVED"),
	/**
	 * 拒绝状态
	 */
	 REFUSED("REFUSED");
	
	private String val="";
	
	private OStatus(String val){
		this.val=val;
	}
	
	@Override
	public String toString() {
		return val;
	}
}
