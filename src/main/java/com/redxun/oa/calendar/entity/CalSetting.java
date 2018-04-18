package com.redxun.oa.calendar.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.entity.BaseTenantEntity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：CalSetting实体类定义
 * 日历设定
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "CAL_SETTING")
@TableDefine(title = "日历设定")
@JsonIgnoreProperties("calGrants")
public class CalSetting extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SETTING_ID_")
	protected String settingId;
	/* 日历名称 */
	@FieldDefine(title = "日历名称")
	@Column(name = "CAL_NAME_")
	@Size(max = 64)
	protected String calName;
	@FieldDefine(title = "是否默认")
	@Column(name = "IS_COMMON_")
	@Size(max = 64)
	protected String isCommon;
	@FieldDefine(title = "日历分配")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "calSetting", fetch = FetchType.LAZY)
	protected java.util.Set<CalGrant> calGrants = new java.util.HashSet<CalGrant>();
	@FieldDefine(title = "工作日历安排")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "calSetting", fetch = FetchType.LAZY)
	protected java.util.Set<WorkCalendar> workCalendars = new java.util.HashSet<WorkCalendar>();

	/**
	 * Default Empty Constructor for class CalSetting
	 */
	public CalSetting() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class CalSetting
	 */
	public CalSetting(String in_settingId) {
		this.setSettingId(in_settingId);
	}

	public java.util.Set<CalGrant> getCalGrants() {
		return calGrants;
	}

	public void setCalGrants(java.util.Set<CalGrant> in_calGrants) {
		this.calGrants = in_calGrants;
	}

	public java.util.Set<WorkCalendar> getWorkCalendars() {
		return workCalendars;
	}

	public void setWorkCalendars(java.util.Set<WorkCalendar> in_workCalendars) {
		this.workCalendars = in_workCalendars;
	}

	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}

	/**
	 * 设定ID * @return String
	 */
	public String getSettingId() {
		return this.settingId;
	}

	/**
	 * 设置 设定ID
	 */
	public void setSettingId(String aValue) {
		this.settingId = aValue;
	}

	/**
	 * 日历名称 * @return String
	 */
	public String getCalName() {
		return this.calName;
	}

	/**
	 * 设置 日历名称
	 */
	public void setCalName(String aValue) {
		this.calName = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.settingId;
	}

	@Override
	public Serializable getPkId() {
		return this.settingId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.settingId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CalSetting)) {
			return false;
		}
		CalSetting rhs = (CalSetting) object;
		return new EqualsBuilder().append(this.settingId, rhs.settingId).append(this.calName, rhs.calName).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.settingId).append(this.calName).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("settingId", this.settingId).append("calName", this.calName).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime)
				.append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
