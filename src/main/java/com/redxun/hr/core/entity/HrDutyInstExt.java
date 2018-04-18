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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：HrDutyInstExt实体类定义
 * 排班实例扩展表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_DUTY_INST_EXT")
@TableDefine(title = "排班实例扩展表")
@JsonIgnoreProperties(value={"hrDutyInst"})
public class HrDutyInstExt extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "EXT_ID_")
	protected String extId;
	/* 开始签到 */
	@FieldDefine(title = "开始签到")
	@Column(name = "START_SIGN_IN_")
	protected Long startSignIn;
	/* 上班时间 */
	@FieldDefine(title = "上班时间")
	@Column(name = "DUTY_START_TIME_")
	@Size(max = 20)
	protected String dutyStartTime;
	/* 签到结束时间 */
	@FieldDefine(title = "签到结束时间")
	@Column(name = "END_SIGN_IN_")
	protected Long endSignIn;
	/* 早退计时 */
	@FieldDefine(title = "早退计时")
	@Column(name = "EARLY_OFF_TIME_")
	protected Long earlyOffTime;
	/* 下班时间 */
	@FieldDefine(title = "下班时间")
	@Column(name = "DUTY_END_TIME_")
	@Size(max = 20)
	protected String dutyEndTime;
	/* 签退结束 */
	@FieldDefine(title = "签退结束")
	@Column(name = "SIGN_OUT_TIME_")
	protected Long signOutTime;
	/* 班次ID */
	@FieldDefine(title = "班次ID")
	@Column(name = "SECTION_ID_")
	@Size(max = 64)
	protected String sectionId;
	
	@FieldDefine(title = "排班实例")
	@ManyToOne
	@JoinColumn(name = "DUTY_INST_ID_")
	protected com.redxun.hr.core.entity.HrDutyInst hrDutyInst;
	

	
	
	/**
	 * Default Empty Constructor for class HrDutyInstExt
	 */
	public HrDutyInstExt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrDutyInstExt
	 */
	public HrDutyInstExt(String in_extId) {
		this.setExtId(in_extId);
	}
	
	

	public com.redxun.hr.core.entity.HrDutyInst getHrDutyInst() {
		return hrDutyInst;
	}

	public void setHrDutyInst(com.redxun.hr.core.entity.HrDutyInst hrDutyInst) {
		this.hrDutyInst = hrDutyInst;
	}

	/**
	 * 排班实例扩展ID * @return String
	 */
	public String getExtId() {
		return this.extId;
	}

	/**
	 * 设置 排班实例扩展ID
	 */
	public void setExtId(String aValue) {
		this.extId = aValue;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	/**
	 * 开始签到 * @return Long
	 */
	public Long getStartSignIn() {
		return this.startSignIn;
	}

	/**
	 * 设置 开始签到
	 */
	public void setStartSignIn(Long aValue) {
		this.startSignIn = aValue;
	}

	/**
	 * 上班时间 * @return String
	 */
	public String getDutyStartTime() {
		return this.dutyStartTime;
	}

	/**
	 * 设置 上班时间
	 */
	public void setDutyStartTime(String aValue) {
		this.dutyStartTime = aValue;
	}

	/**
	 * 签到结束时间 * @return Long
	 */
	public Long getEndSignIn() {
		return this.endSignIn;
	}

	/**
	 * 设置 签到结束时间
	 */
	public void setEndSignIn(Long aValue) {
		this.endSignIn = aValue;
	}

	/**
	 * 早退计时 * @return Long
	 */
	public Long getEarlyOffTime() {
		return this.earlyOffTime;
	}

	/**
	 * 设置 早退计时
	 */
	public void setEarlyOffTime(Long aValue) {
		this.earlyOffTime = aValue;
	}

	/**
	 * 下班时间 * @return String
	 */
	public String getDutyEndTime() {
		return this.dutyEndTime;
	}

	/**
	 * 设置 下班时间
	 */
	public void setDutyEndTime(String aValue) {
		this.dutyEndTime = aValue;
	}

	/**
	 * 签退结束 * @return Long
	 */
	public Long getSignOutTime() {
		return this.signOutTime;
	}

	/**
	 * 设置 签退结束
	 */
	public void setSignOutTime(Long aValue) {
		this.signOutTime = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.extId;
	}

	@Override
	public Serializable getPkId() {
		return this.extId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.extId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrDutyInstExt)) {
			return false;
		}
		HrDutyInstExt rhs = (HrDutyInstExt) object;
		return new EqualsBuilder().append(this.extId, rhs.extId).append(this.startSignIn, rhs.startSignIn).append(this.dutyStartTime, rhs.dutyStartTime).append(this.endSignIn, rhs.endSignIn).append(this.earlyOffTime, rhs.earlyOffTime).append(this.dutyEndTime, rhs.dutyEndTime).append(this.signOutTime, rhs.signOutTime).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.extId).append(this.startSignIn).append(this.dutyStartTime).append(this.endSignIn).append(this.earlyOffTime).append(this.dutyEndTime).append(this.signOutTime).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("extId", this.extId).append("startSignIn", this.startSignIn).append("dutyStartTime", this.dutyStartTime).append("endSignIn", this.endSignIn).append("earlyOffTime", this.earlyOffTime).append("dutyEndTime", this.dutyEndTime).append("signOutTime", this.signOutTime).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
