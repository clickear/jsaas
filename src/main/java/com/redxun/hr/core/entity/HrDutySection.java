package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 *  
 * 描述：HrDutySection实体类定义
 * 班次\r\n
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_DUTY_SECTION")
@TableDefine(title = "班次")
@JsonIgnoreProperties(value={"hrDutySystems"})
public class HrDutySection extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SECTION_ID_")
	protected String sectionId;
	/* 班次名称 */
	@FieldDefine(title = "班次名称")
	@Column(name = "SECTION_NAME_")
	@Size(max = 16)
	@NotEmpty
	protected String sectionName;
	/* 班次简称 */
	@FieldDefine(title = "班次简称")
	@Column(name = "SECTION_SHORT_NAME_")
	@Size(max = 4)
	@NotEmpty
	protected String sectionShortName;
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
	/* 是否跨日 */
	@FieldDefine(title = "是否跨日")
	@Column(name = "IS_TWO_DAY_")
	@Size(max = 8)
	protected String isTwoDay;
	/* 组合班次列表 */
	@FieldDefine(title = "组合班次列表")
	@Column(name = "GROUP_LIST_")
	@Size(max = 2147483647)
	protected String groupList;
	/* 是否组合班次 */
	@FieldDefine(title = "是否组合班次")
	@Column(name = "IS_GROUP_")
	@Size(max = 8)
	@NotEmpty
	protected String isGroup;
	

/*	@FieldDefine(title = "班制班次表")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrDutySection", fetch = FetchType.LAZY)
	protected java.util.Set<HrSystemSection> hrSystemSections = new java.util.HashSet<HrSystemSection>();*/
	
	@FieldDefine(title = "班制列表")
	@ManyToMany(cascade={CascadeType.PERSIST},fetch = FetchType.LAZY)     
	@JoinTable(name="HR_SYSTEM_SECTION",
				joinColumns={ @JoinColumn(name="SECTION_ID_",referencedColumnName="SECTION_ID_")    },    
				inverseJoinColumns={ @JoinColumn(name="SYSTEM_ID_",referencedColumnName="SYSTEM_ID_") })  
    protected java.util.Set<HrDutySystem> hrDutySystems = new java.util.HashSet<HrDutySystem>();

	/**
	 * Default Empty Constructor for class HrDutySection
	 */
	public HrDutySection() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrDutySection
	 */
	public HrDutySection(String in_sectionId) {
		this.setSectionId(in_sectionId);
	}

/*	public java.util.Set<HrSystemSection> getHrSystemSections() {
		return hrSystemSections;
	}

	public void setHrSystemSections(java.util.Set<HrSystemSection> in_hrSystemSections) {
		this.hrSystemSections = in_hrSystemSections;
	}*/
	
	
	
	
	
	public String getGroupList() {
		return groupList;
	}

	public java.util.Set<HrDutySystem> getHrDutySystems() {
		return hrDutySystems;
	}

	public void setHrDutySystems(java.util.Set<HrDutySystem> hrDutySystems) {
		this.hrDutySystems = hrDutySystems;
	}

	public String getSectionShortName() {
		return sectionShortName;
	}

	public void setSectionShortName(String sectionShortName) {
		this.sectionShortName = sectionShortName;
	}

	public void setGroupList(String groupList) {
		this.groupList = groupList;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	

	/**
	 * 班次编号 * @return String
	 */
	public String getSectionId() {
		return this.sectionId;
	}

	

	/**
	 * 设置 班次编号
	 */
	public void setSectionId(String aValue) {
		this.sectionId = aValue;
	}

	/**
	 * 班次名称 * @return String
	 */
	public String getSectionName() {
		return this.sectionName;
	}

	/**
	 * 设置 班次名称
	 */
	public void setSectionName(String aValue) {
		this.sectionName = aValue;
	}

	/**
	 * 开始签到 * @return java.util.Date
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
	 * 上班时间 * @return java.util.Date
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
	 * 签到结束时间 * @return java.util.Date
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
	 * 早退计时 * @return java.util.Date
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
	 * 下班时间 * @return java.util.Date
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
	 * 签退结束 * @return java.util.Date
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

	/**
	 * 是否跨日 * @return String
	 */
	public String getIsTwoDay() {
		return this.isTwoDay;
	}

	/**
	 * 设置 是否跨日
	 */
	public void setIsTwoDay(String aValue) {
		this.isTwoDay = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.sectionId;
	}

	@Override
	public Serializable getPkId() {
		return this.sectionId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.sectionId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrDutySection)) {
			return false;
		}
		HrDutySection rhs = (HrDutySection) object;
		return new EqualsBuilder().append(this.sectionId, rhs.sectionId).append(this.sectionName, rhs.sectionName)
				.append(this.startSignIn, rhs.startSignIn).append(this.dutyStartTime, rhs.dutyStartTime)
				.append(this.endSignIn, rhs.endSignIn).append(this.earlyOffTime, rhs.earlyOffTime)
				.append(this.dutyEndTime, rhs.dutyEndTime).append(this.signOutTime, rhs.signOutTime)
				.append(this.isTwoDay, rhs.isTwoDay).append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sectionId).append(this.sectionName)
				.append(this.startSignIn).append(this.dutyStartTime).append(this.endSignIn).append(this.earlyOffTime)
				.append(this.dutyEndTime).append(this.signOutTime).append(this.isTwoDay).append(this.tenantId)
				.append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sectionId", this.sectionId).append("sectionNmae", this.sectionName)
				.append("startSignIn", this.startSignIn).append("dutyStartTime", this.dutyStartTime)
				.append("endSignIn", this.endSignIn).append("earlyOffTime", this.earlyOffTime)
				.append("dutyEndTime", this.dutyEndTime).append("signOutTime", this.signOutTime)
				.append("isTwoDay", this.isTwoDay).append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
