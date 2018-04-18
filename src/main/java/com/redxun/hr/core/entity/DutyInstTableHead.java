package com.redxun.hr.core.entity;

import java.io.Serializable;

import com.redxun.core.entity.BaseTenantEntity;

public class DutyInstTableHead extends BaseTenantEntity{
	String field;
	String header;
	String displayName;
	String dayOfWeek;
	
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public String getIdentifyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable getPkId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPkId(Serializable pkId) {
		// TODO Auto-generated method stub
		
	}

}
