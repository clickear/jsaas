package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

/**
 * <pre>
 * 描述：SysQuartzLog实体类定义        
 * @author cjx
 * @Email: chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
@Entity
@Table(name = "SYS_QUARTZ_LOG")
@TableDefine(title = "定时器日志")
public class SysQuartzLog extends BaseTenantEntity {

	private static final long serialVersionUID = 1L;
	/**/
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "LOG_ID_")
	protected String logId;
	/**/
	@FieldDefine(title = "任务别名")
	@Column(name = "ALIAS_")
	@Size(max = 256)
	protected String alias;
	/* 定时器名称 */
	@FieldDefine(title = "任务名称")
	@Column(name = "JOB_NAME_")
	@Size(max = 256)
	protected String jobName;
	/**/
	// @FieldDefine(title = "计划名称")
	@Column(name = "TRIGGER_NAME_")
	@Size(max = 256)
	protected String triggerName;
	/* 日志内容 */
	@FieldDefine(title = "日志内容")
	@Column(name = "CONTENT_")
	@Size(max = 65535)
	protected String content;
	/* 开始时间 */
	// @FieldDefine(title = "开始时间")
	@Column(name = "START_TIME_")
	@JsonSerialize(using = JsonDateSerializer.class)
	protected java.util.Date startTime;
	/* 结束时间 */
	// @FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	@JsonSerialize(using = JsonDateSerializer.class)
	protected java.util.Date endTime;
	/* 持续时间 */
	// @FieldDefine(title = "持续时间")
	@Column(name = "RUN_TIME_")
	protected Long runTime;
	/* 状态 */
	// @FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 64)
	protected String status;
	
	

	/**
	 * Default Empty Constructor for class SysQuartzLog
	 */
	public SysQuartzLog() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysQuartzLog
	 */
	public SysQuartzLog(String in_logId) {
		this.setLogId(in_logId);
	}

	/**
	 * 日志主键ID * @return String
	 */
	public String getLogId() {
		return this.logId;
	}

	/**
	 * 设置 日志主键ID
	 */
	public void setLogId(String aValue) {
		this.logId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * 设置
	 */
	public void setAlias(String aValue) {
		this.alias = aValue;
	}

	/**
	 * 定时器名称 * @return String
	 */
	public String getJobName() {
		return this.jobName;
	}

	/**
	 * 设置 定时器名称
	 */
	public void setJobName(String aValue) {
		this.jobName = aValue;
	}

	/**
	 * * @return String
	 */
	public String getTriggerName() {
		return this.triggerName;
	}

	/**
	 * 设置
	 */
	public void setTriggerName(String aValue) {
		this.triggerName = aValue;
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
	 * 持续时间 * @return Long
	 */
	public Long getRunTime() {
		return this.runTime;
	}

	/**
	 * 设置 持续时间
	 */
	public void setRunTime(Long aValue) {
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
		if (!(object instanceof SysQuartzLog)) {
			return false;
		}
		SysQuartzLog rhs = (SysQuartzLog) object;
		return new EqualsBuilder().append(this.logId, rhs.logId).append(this.alias, rhs.alias).append(this.jobName, rhs.jobName).append(this.triggerName, rhs.triggerName).append(this.content, rhs.content).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.runTime, rhs.runTime).append(this.status, rhs.status).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.logId).append(this.alias).append(this.jobName).append(this.triggerName).append(this.content).append(this.startTime).append(this.endTime).append(this.runTime).append(this.status).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("logId", this.logId).append("alias", this.alias).append("jobName", this.jobName).append("triggerName", this.triggerName).append("content", this.content).append("startTime", this.startTime).append("endTime", this.endTime).append("runTime", this.runTime).append("status", this.status).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
