package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：HrDuty实体类定义
 * 排班
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_DUTY")
@TableDefine(title = "排班")
public class HrDuty extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DUTY_ID_")
	protected String dutyId;
	/* 排班名称 */
	@FieldDefine(title = "班次名称")
	@Column(name = "DUTY_NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String dutyName;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_TIME_")
	protected java.util.Date startTime;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	protected java.util.Date endTime;
	/* 使用者ID */
	@FieldDefine(title = "使用者ID")
	@Column(name = "USER_ID_")
	@Size(max = 65535)
	protected String userId;
	/* 用户组ID */
	@FieldDefine(title = "用户组ID")
	@Column(name = "GROUP_ID_")
	@Size(max = 65535)
	protected String groupId;
	/* 使用者姓名 */
	@FieldDefine(title = "使用者姓名")
	@Column(name = "USER_NAME_")
	@Size(max = 65535)
	protected String userName;
	/* 用户组姓名 */
	@FieldDefine(title = "用户组姓名")
	@Column(name = "GROUP_NAME_")
	@Size(max = 65535)
	protected String groupName;
	@FieldDefine(title = "班制\r\n")
	@ManyToOne
	@JoinColumn(name = "SYSTEM_ID_")
	protected com.redxun.hr.core.entity.HrDutySystem hrDutySystem;

	/**
	 * Default Empty Constructor for class HrDuty
	 */
	public HrDuty() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrDuty
	 */
	public HrDuty(String in_dutyId) {
		this.setDutyId(in_dutyId);
	}

	public com.redxun.hr.core.entity.HrDutySystem getHrDutySystem() {
		return hrDutySystem;
	}

	public void setHrDutySystem(com.redxun.hr.core.entity.HrDutySystem in_hrDutySystem) {
		this.hrDutySystem = in_hrDutySystem;
	}
	
	

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	/**
	 * 排班ID * @return String
	 */
	public String getDutyId() {
		return this.dutyId;
	}

	/**
	 * 设置 排班ID
	 */
	public void setDutyId(String aValue) {
		this.dutyId = aValue;
	}

	/**
	 * 班制编号 * @return String
	 */
	public String getSystemId() {
		return this.getHrDutySystem() == null ? null : this.getHrDutySystem().getSystemId();
	}

	/**
	 * 设置 班制编号
	 */
	public void setSystemId(String aValue) {
		if (aValue == null) {
			hrDutySystem = null;
		} else if (hrDutySystem == null) {
			hrDutySystem = new com.redxun.hr.core.entity.HrDutySystem(aValue);
		} else {
			hrDutySystem.setSystemId(aValue);
		}
	}

	/**
	 * 开始时间 * @return java.util.Date
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 开始时间
	 */
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}

	/**
	 * 结束时间 * @return java.util.Date
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 结束时间
	 */
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}

	/**
	 * 使用者ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 使用者ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 用户组ID * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置 用户组ID
	 */
	public void setGroupId(String aValue) {
		this.groupId = aValue;
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String getIdentifyLabel() {
		return this.dutyId;
	}

	@Override
	public Serializable getPkId() {
		return this.dutyId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.dutyId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrDuty)) {
			return false;
		}
		HrDuty rhs = (HrDuty) object;
		return new EqualsBuilder().append(this.dutyId, rhs.dutyId).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.tenantId, rhs.tenantId).append(this.userId, rhs.userId).append(this.groupId, rhs.groupId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.dutyId).append(this.startTime).append(this.endTime).append(this.tenantId).append(this.userId).append(this.groupId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("dutyId", this.dutyId).append("startTime", this.startTime).append("endTime", this.endTime).append("tenantId", this.tenantId).append("userId", this.userId).append("groupId", this.groupId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
