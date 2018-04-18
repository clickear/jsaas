package com.redxun.sys.ldap.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysLdapLog实体类定义
 * SYS_LDAP_LOG【LDAP同步日志】\r\n
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "SYS_LDAP_LOG")
@TableDefine(title = "SYS_LDAP_LOG【LDAP同步日志】\r\n")
public class SysLdapLog extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "LOG_ID_")
	protected String logId;
	/* 日志名称 */
	@FieldDefine(title = "日志名称")
	@Column(name = "LOG_NAME_")
	@Size(max = 256)
	protected String logName;
	/* 日志内容 */
	@FieldDefine(title = "日志内容")
	@Column(name = "CONTENT_")
	@Size(max = 65535)
	protected String content;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_TIME_")
	protected java.sql.Timestamp startTime;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	protected java.sql.Timestamp endTime;
	/* 持续时间 */
	@FieldDefine(title = "持续时间")
	@Column(name = "RUN_TIME_")
	protected Integer runTime;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 64)
	protected String status;
	/* 同步类型 */
	@FieldDefine(title = "同步类型")
	@Column(name = "SYNC_TYPE_")
	@Size(max = 64)
	protected String syncType;

	/**
	 * Default Empty Constructor for class SysLdapLog
	 */
	public SysLdapLog() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysLdapLog
	 */
	public SysLdapLog(String in_logId) {
		this.setLogId(in_logId);
	}

	/**
	 * 日志主键 * @return String
	 */
	public String getLogId() {
		return this.logId;
	}
	/**
	 * 设置 日志主键
	 */
	public void setLogId(String aValue) {
		this.logId = aValue;
	}
	/**
	 * 日志名称 * @return String
	 */
	public String getLogName() {
		return this.logName;
	}
	/**
	 * 设置 日志名称
	 */
	public void setLogName(String aValue) {
		this.logName = aValue;
	}
	/**
	 * 日志内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}
	/**
	 * 设置 日志内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
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
	/**
	 * 持续时间 * @return Integer
	 */
	public Integer getRunTime() {
		return this.runTime;
	}
	/**
	 * 设置 持续时间
	 */
	public void setRunTime(Integer aValue) {
		this.runTime = aValue;
	}
	/**
	 * 状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * 设置 状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}
	/**
	 * 同步类型 * @return String
	 */
	public String getSyncType() {
		return this.syncType;
	}
	/**
	 * 设置 同步类型
	 */
	public void setSyncType(String aValue) {
		this.syncType = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.logId;
	}

	@Override
	public Serializable getPkId() {
		return this.logId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.logId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysLdapLog)) {
			return false;
		}
		SysLdapLog rhs = (SysLdapLog) object;
		return new EqualsBuilder().append(this.logId, rhs.logId).append(this.logName, rhs.logName)
				.append(this.content, rhs.content).append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime).append(this.runTime, rhs.runTime).append(this.status, rhs.status)
				.append(this.syncType, rhs.syncType).append(this.tenantId, rhs.tenantId)
				.append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.logId).append(this.logName).append(this.content)
				.append(this.startTime).append(this.endTime).append(this.runTime).append(this.status)
				.append(this.syncType).append(this.tenantId).append(this.updateTime).append(this.updateBy)
				.append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("logId", this.logId).append("logName", this.logName)
				.append("content", this.content).append("startTime", this.startTime).append("endTime", this.endTime)
				.append("runTime", this.runTime).append("status", this.status).append("syncType", this.syncType)
				.append("tenantId", this.tenantId).append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy).append("createTime", this.createTime)
				.append("createBy", this.createBy).toString();
	}

}
