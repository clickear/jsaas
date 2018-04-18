package com.redxun.oa.calendar.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：WorkCalendar实体类定义
 * 工作日历安排
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "CAL_CALENDAR")
@TableDefine(title = "工作日历安排")
@JsonIgnoreProperties("calSetting")
public class WorkCalendar extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CALENDER_ID_")
	protected String calenderId;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_TIME_")
	protected java.sql.Timestamp startTime;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	protected java.sql.Timestamp endTime;
	@FieldDefine(title = "日历设定")
	@ManyToOne
	@JoinColumn(name = "SETTING_ID_")
	protected com.redxun.oa.calendar.entity.CalSetting calSetting;

	/**
	 * Default Empty Constructor for class WorkCalendar
	 */
	public WorkCalendar() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class WorkCalendar
	 */
	public WorkCalendar(String in_calenderId) {
		this.setCalenderId(in_calenderId);
	}

	public com.redxun.oa.calendar.entity.CalSetting getCalSetting() {
		return calSetting;
	}

	public void setCalSetting(com.redxun.oa.calendar.entity.CalSetting in_calSetting) {
		this.calSetting = in_calSetting;
	}

	/**
	 * 日历Id * @return String
	 */
	public String getCalenderId() {
		return this.calenderId;
	}

	/**
	 * 设置 日历Id
	 */
	public void setCalenderId(String aValue) {
		this.calenderId = aValue;
	}

	/**
	 * 设定ID * @return String
	 */
	public String getSettingId() {
		return this.getCalSetting() == null ? null : this.getCalSetting().getSettingId();
	}

	/**
	 * 设置 设定ID
	 */
	public void setSettingId(String aValue) {
		if (aValue == null) {
			calSetting = null;
		} else if (calSetting == null) {
			calSetting = new com.redxun.oa.calendar.entity.CalSetting(aValue);
		} else {
			calSetting.setSettingId(aValue);
		}
	}

	/**
	 * 开始时间 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 开始时间
	 */
	public void setStartTime(java.sql.Timestamp aValue) {
		this.startTime = aValue;
	}

	/**
	 * 结束时间 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 结束时间
	 */
	public void setEndTime(java.sql.Timestamp aValue) {
		this.endTime = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.calenderId;
	}

	@Override
	public Serializable getPkId() {
		return this.calenderId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.calenderId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WorkCalendar)) {
			return false;
		}
		WorkCalendar rhs = (WorkCalendar) object;
		return new EqualsBuilder().append(this.calenderId, rhs.calenderId).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime)
				.append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.calenderId).append(this.startTime).append(this.endTime).append(this.updateBy).append(this.updateTime).append(this.createTime).append(this.createBy).append(this.tenantId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("calenderId", this.calenderId).append("startTime", this.startTime).append("endTime", this.endTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime)
				.append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
