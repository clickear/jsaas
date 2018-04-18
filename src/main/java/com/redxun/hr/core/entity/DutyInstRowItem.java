package com.redxun.hr.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.redxun.core.entity.BaseTenantEntity;

public class DutyInstRowItem extends BaseTenantEntity{
	String userId;
	String userName;
	String depId;
	String depName;
	
	List<HrDutyInst> hrDutyInsts=new ArrayList<HrDutyInst>();
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public List<HrDutyInst> getHrDutyInsts() {
		return hrDutyInsts;
	}

	public void setHrDutyInsts(List<HrDutyInst> hrDutyInsts) {
		this.hrDutyInsts = hrDutyInsts;
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
