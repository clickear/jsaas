package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

/**
 * <pre>
 *  
 * 描述：HrDutyRegister实体类定义
 * TODO: add class/table comments
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_DUTY_REGISTER")
@TableDefine(title = "考勤登记记录")
public class HrDutyRegister extends BaseTenantEntity {

	public final static String TYPE_INFLAG="1";
	public final static String TYPE_OFFFLAG="2";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "REGISTER_ID_")
	protected String registerId;
	/* 登记时间 */
	@FieldDefine(title = "登记时间")
	@Column(name = "REGISTER_TIME_")
	protected java.util.Date registerTime;
	/* 登记标识  */
	@FieldDefine(title = "登记标识")
	@Column(name = "REG_FLAG_")
	protected Short regFlag;
	/* 迟到或早退分钟 正常上班时为0 */
	@FieldDefine(title = "迟到或早退分钟 正常上班时为0")
	@Column(name = "REG_MINS_")
	protected Long regMins;
	/* 迟到原因 */
	@FieldDefine(title = "迟到原因")
	@Column(name = "REASON_")
	@Size(max = 128)
	protected String reason;
	/* 周几 */
	@FieldDefine(title = "周几")
	@Column(name = "DAYOFWEEK_")
	protected Long dayofweek;
	/* 上下班标识 1=签到 2=签退 */
	@FieldDefine(title = "上下班标识 1=签到 2=签退")
	@Column(name = "IN_OFF_FLAG_")
	@Size(max = 8)
	@NotEmpty
	protected String inOffFlag;
	/* 班制ID */
	@FieldDefine(title = "班制ID")
	@Column(name = "SYSTEM_ID_")
	@Size(max = 64)
	protected String systemId;
	/* 班次ID */
	@FieldDefine(title = "班次ID")
	@Column(name = "SECTION_ID_")
	@Size(max = 64)
	protected String sectionId;
	/* 日期 */
	@FieldDefine(title = "日期")
	@Column(name = "DATE_")
	protected java.util.Date date;
	/* 用户名 */
	@FieldDefine(title = "用户名")
	@Column(name = "USER_NAME_")
	@Size(max = 64)
	protected String userName;
	/* 经度 */
	@FieldDefine(title = "经度")
	@Column(name = "LONGITUDE_")
	protected Double longitude;
	/* 纬度 */
	@FieldDefine(title = "纬度")
	@Column(name = "LATITUDE_")
	protected Double latitude;
	/* 地址详情 */
	@FieldDefine(title = "地址详情")
	@Column(name = "ADDRESSES_")
	protected String addresses;
	/* 签到备注 */
	@FieldDefine(title = "签到备注")
	@Column(name = "SIGNREMARK_")
	protected String signRemark;
	/* 签到距离 */
	@FieldDefine(title = "签到距离")
	@Column(name = "DISTANCE_")
	protected Integer distance;
	
	
	
	

	/**
	 * Default Empty Constructor for class HrDutyRegister
	 */
	public HrDutyRegister() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrDutyRegister
	 */
	public HrDutyRegister(String in_registerId) {
		this.setRegisterId(in_registerId);
	}

	/**
	 * 登记ID * @return String
	 */
	public String getRegisterId() {
		return this.registerId;
	}

	/**
	 * 设置 登记ID
	 */
	public void setRegisterId(String aValue) {
		this.registerId = aValue;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	/**
	 * 登记时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getRegisterTime() {
		return this.registerTime;
	}

	/**
	 * 设置 登记时间
	 */
	public void setRegisterTime(java.util.Date aValue) {
		this.registerTime = aValue;
	}

	/**
	 * 登记标识 1=正常登记（上班，下班） 2＝迟到 3=早退 4＝休息 5＝旷工 6=放假 * @return Short
	 */
	public Short getRegFlag() {
		return this.regFlag;
	}

	/**
	 * 设置 登记标识 1=正常登记（上班，下班） 2＝迟到 3=早退 4＝休息 5＝旷工 6=放假
	 */
	public void setRegFlag(Short aValue) {
		this.regFlag = aValue;
	}

	/**
	 * 迟到或早退分钟 正常上班时为0 * @return Long
	 */
	public Long getRegMins() {
		return this.regMins;
	}

	/**
	 * 设置 迟到或早退分钟 正常上班时为0
	 */
	public void setRegMins(Long aValue) {
		this.regMins = aValue;
	}

	/**
	 * 迟到原因 * @return String
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * 设置 迟到原因
	 */
	public void setReason(String aValue) {
		this.reason = aValue;
	}

	/**
	 * 周几 * @return Long
	 */
	public Long getDayofweek() {
		return this.dayofweek;
	}

	/**
	 * 设置 周几
	 */
	public void setDayofweek(Long aValue) {
		this.dayofweek = aValue;
	}

	/**
	 * 上下班标识 1=签到 2=签退 * @return String
	 */
	public String getInOffFlag() {
		return this.inOffFlag;
	}

	/**
	 * 设置 上下班标识 1=签到 2=签退
	 */
	public void setInOffFlag(String aValue) {
		this.inOffFlag = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.registerId;
	}

	@Override
	public Serializable getPkId() {
		return this.registerId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.registerId = (String) pkId;
	}
	

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getSignRemark() {
		return signRemark;
	}

	public void setSignRemark(String signRemark) {
		this.signRemark = signRemark;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrDutyRegister)) {
			return false;
		}
		HrDutyRegister rhs = (HrDutyRegister) object;
		return new EqualsBuilder().append(this.registerId, rhs.registerId).append(this.registerTime, rhs.registerTime)
				.append(this.regFlag, rhs.regFlag).append(this.regMins, rhs.regMins).append(this.reason, rhs.reason)
				.append(this.dayofweek, rhs.dayofweek).append(this.inOffFlag, rhs.inOffFlag)
				.append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.registerId).append(this.registerTime)
				.append(this.regFlag).append(this.regMins).append(this.reason).append(this.dayofweek)
				.append(this.inOffFlag).append(this.tenantId).append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("registerId", this.registerId).append("registerTime", this.registerTime)
				.append("regFlag", this.regFlag).append("regMins", this.regMins).append("reason", this.reason)
				.append("dayofweek", this.dayofweek).append("inOffFlag", this.inOffFlag)
				.append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
