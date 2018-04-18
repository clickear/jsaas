package com.redxun.sys.api;

import java.util.Date;

public class ExtraTimeModel {

	/**
	 * 开始时间
	 */
	private java.sql.Timestamp startTime;
	/**
	 * 结束时间
	 */
	private java.sql.Timestamp endTime;
	
	/**
	 * 是否加班情况。
	 */
	private Boolean isPositive=true;

	public java.sql.Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}

	public java.sql.Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
	}
	
	
	
	
}
