package com.redxun.oa.pro.entity;

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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

/**
 * <pre>
 * 描述：ProTask实体类定义
 * 工作任务
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_WORK_LOG")
@TableDefine(title = "工作日志")
@JsonIgnoreProperties(value="Plan")
public class WorkLog extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "LOG_ID_")
	protected String logId;
		
	/* 任务内容 */
	@FieldDefine(title = "任务内容")
	@Column(name = "CONTENT_")
	@Size(max = 1024)
	@NotEmpty
	protected String content;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_TIME_")
	protected java.sql.Timestamp startTime;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	protected java.sql.Timestamp endTime;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 32)
	protected String status;
	/* 耗时(分） */
	@FieldDefine(title = "耗时(分）")
	@Column(name = "LAST_")
	protected Integer last;
	/* 审核人 */
	@FieldDefine(title = "审核人")
	@Column(name = "CHECKER_")
	@Size(max = 64)
	protected String checker;
	
	@FieldDefine(title = "工作计划")
	@ManyToOne
	@JoinColumn(name = "PLAN_ID_")
	protected com.redxun.oa.pro.entity.PlanTask PlanTask;

	/**
	 * Default Empty Constructor for class ProTask
	 */
	public WorkLog() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ProTask
	 */
	public WorkLog(String in_taskId) {
		this.setLogId(in_taskId);
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public void setPlanTask(com.redxun.oa.pro.entity.PlanTask planTask) {
		PlanTask = planTask;
	}

	public com.redxun.oa.pro.entity.PlanTask getOaPlanTask() {
		return PlanTask;
	}

	public void setOaPlanTask(com.redxun.oa.pro.entity.PlanTask PlanTask) {
		this.PlanTask = PlanTask;
	}

	

	/**
	 * 计划ID * @return String
	 */
	public String getPlanTaskId() {
		return this.getOaPlanTask() == null ? null : this.getOaPlanTask().getPlanId();
	}

	/**
	 * 设置 计划ID
	 */
	public void setPlanTaskId(String aValue) {
		if (aValue == null) {
			PlanTask = null;
		} else if (PlanTask == null) {
			PlanTask = new com.redxun.oa.pro.entity.PlanTask(aValue);
		} else {
			PlanTask.setPlanId(aValue);
		}
	}

	public com.redxun.oa.pro.entity.PlanTask getPlanTask() {
		return PlanTask;
	}

	public void setPlan(com.redxun.oa.pro.entity.PlanTask planTask) {
		PlanTask = planTask;
	}

	/**
	 * 任务内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 任务内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 开始时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 开始时间
	 */
	public void setStartTime(java.sql.Timestamp aValue) {
		this.startTime = aValue;
	}

	/**
	 * 结束时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 结束时间
	 */
	public void setEndTime(java.sql.Timestamp aValue) {
		this.endTime = aValue;
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
	 * 耗时(分） * @return Integer
	 */
	public Integer getLast() {
		return this.last;
	}

	/**
	 * 设置 耗时(分）
	 */
	public void setLast(Integer aValue) {
		this.last = aValue;
	}

	/**
	 * 审核人 * @return String
	 */
	public String getChecker() {
		return this.checker;
	}

	/**
	 * 设置 审核人
	 */
	public void setChecker(String aValue) {
		this.checker = aValue;
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
		if (!(object instanceof WorkLog)) {
			return false;
		}
		WorkLog rhs = (WorkLog) object;
		return new EqualsBuilder().append(this.logId, rhs.logId).append(this.content, rhs.content).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.status, rhs.status).append(this.last, rhs.last)
				.append(this.checker, rhs.checker).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.logId).append(this.content).append(this.startTime).append(this.endTime).append(this.status).append(this.last).append(this.checker).append(this.createBy)
				.append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("logId", this.logId).append("content", this.content).append("startTime", this.startTime).append("endTime", this.endTime).append("status", this.status).append("last", this.last)
				.append("checker", this.checker).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
