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
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：计划实体类定义
 * 工作计划
 * 作者：cmc
 * 日期:2015-12-16-下午13:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PLAN_TASK")
@TableDefine(title = "工作计划")
@JsonIgnoreProperties("project")
public class PlanTask extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "PLAN_ID_")
	protected String planId;
	/* 计划标题 */
	@FieldDefine(title = "计划标题")
	@Column(name = "SUBJECT_")
	@Size(max = 128)
	@NotEmpty
	protected String subject;
	/* 计划内容 */
	@FieldDefine(title = "计划内容")
	@Column(name = "CONTENT_")
	@Size(max = 65535)
	protected String content;
	/* 计划开始时间 */
	@FieldDefine(title = "计划开始时间")
	@Column(name = "PSTART_TIME_")
	protected java.sql.Timestamp pstartTime;
	/* 计划结束时间 */
	@FieldDefine(title = "计划结束时间")
	@Column(name = "PEND_TIME_")
	protected java.sql.Timestamp pendTime;
	/* 实际开始时间 */
	@FieldDefine(title = "实际开始时间")
	@Column(name = "START_TIME_")
	protected java.util.Date startTime;
	/* 实际结束时间 */
	@FieldDefine(title = "实际结束时间")
	@Column(name = "END_TIME_")
	protected java.util.Date endTime;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	/* 计划标题 */
	@FieldDefine(title = "版本号")
	@Column(name = "VERSION_")
	@Size(max = 128)
	protected String version;
	/* 分配人 */
	@FieldDefine(title = "分配人")
	@Column(name = "ASSIGN_ID_")
	@Size(max = 64)
	protected String assignId;
	/* 所属人 */
	@FieldDefine(title = "所属人")
	@Column(name = "OWNER_ID_")
	@Size(max = 64)
	protected String ownerId;
	/* 执行人 */
	@FieldDefine(title = "执行人")
	@Column(name = "EXE_ID_")
	@Size(max = 64)
	protected String exeId;
	/* 项目或产品*/
	@FieldDefine(title = "项目或产品")
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID_")
	protected com.redxun.oa.pro.entity.Project project;
	/* 产品或项目需求*/
	@FieldDefine(title = "产品或项目需求")
	@ManyToOne
	@JoinColumn(name = "REQ_ID_")
	protected com.redxun.oa.pro.entity.ReqMgr reqMgr;
	/* 流程定义ID*/
	@FieldDefine(title = "流程定义ID")
	@Column(name = "BPM_DEF_ID_")
	@Size(max = 128)
	protected String BpmDefId;
	/* 流程实例ID*/
	@FieldDefine(title = "流程实例ID")
	@Column(name = "BPM_INST_ID_")
	@Size(max = 128)
	protected String BpmInstId;
	/* 流程任务ID*/
	@FieldDefine(title = "流程任务ID")
	@Column(name = "BPM_TASK_ID_")
	@Size(max = 128)
	protected String BpmTaskId;
	/* 耗时(分)*/
	@FieldDefine(title = "耗时(分)")
	@Column(name = "LAST_")
	protected Integer last;


	/**
	 * Default Empty Constructor for class Plan
	 */
	public PlanTask() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Plan
	 */
	public PlanTask(String in_planId) {
		this.setPlanId(in_planId);
	}

	public com.redxun.oa.pro.entity.Project getProject() {
		return project;
	}
	/**
	 * 设置 项目
	 */
	public void setProject(com.redxun.oa.pro.entity.Project in_project) {
		this.project = in_project;
	}

	public com.redxun.oa.pro.entity.ReqMgr getReqMgr() {
		return reqMgr;
	}
	/**
	 * 设置 需求
	 */
	public void setReqMgr(com.redxun.oa.pro.entity.ReqMgr in_reqMgr) {
		this.reqMgr = in_reqMgr;
	}

	
	public String getVersion() {
		return version;
	}
	/**
	 * 设置 版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public String getBpmDefId() {
		return BpmDefId;
	}
	/**
	 * 设置 工作流程定义
	 */
	public void setBpmDefId(String bpmDefId) {
		BpmDefId = bpmDefId;
	}

	public String getBpmInstId() {
		return BpmInstId;
	}
	/**
	 * 设置 工作流程实例
	 */
	public void setBpmInstId(String bpmInstId) {
		BpmInstId = bpmInstId;
	}

	public String getBpmTaskId() {
		return BpmTaskId;
	}

	/**
	 * 设置 工作流程任务
	 */
	public void setBpmTaskId(String bpmTaskId) {
		BpmTaskId = bpmTaskId;
	}

	public Integer getLast() {
		return last;
	}
	/**
	 * 设置 耗时
	 */
	public void setLast(Integer last) {
		this.last = last;
	}

	/**
	 * 计划ID * @return String
	 */
	public String getPlanId() {
		return this.planId;
	}

	/**
	 * 设置 计划ID
	 */
	public void setPlanId(String aValue) {
		this.planId = aValue;
	}

	/**
	 * 项目或产品ID * @return String
	 */
	public String getProjectId() {
		return this.getProject() == null ? null : this.getProject()
				.getProjectId();
	}

	/**
	 * 设置 项目或产品ID
	 */
	public void setProjectId(String aValue) {
		if (aValue == null) {
			project = null;
		} else if (project == null) {
			project = new com.redxun.oa.pro.entity.Project(aValue);
		} else {
			project.setProjectId(aValue);
		}
	}

	/**
	 * 需求ID * @return String
	 */
	public String getReqId() {
		return this.getReqMgr() == null ? null : this.getReqMgr().getReqId();
	}

	/**
	 * 设置 需求ID
	 */
	public void setReqId(String aValue) {
		if (aValue == null) {
			reqMgr = null;
		} else if (reqMgr == null) {
			reqMgr = new com.redxun.oa.pro.entity.ReqMgr(aValue);
		} else {
			reqMgr.setReqId(aValue);
		}
	}

	/**
	 * 计划标题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 计划标题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 计划内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 计划内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 计划开始时间 * @return java.util.Date
	 */
	public java.sql.Timestamp getPstartTime() {
		return this.pstartTime;
	}

	/**
	 * 设置 计划开始时间
	 */
	public void setPstartTime(java.sql.Timestamp aValue) {
		this.pstartTime = aValue;
	}

	/**
	 * 计划结束时间 * @return java.util.Date
	 */
	public java.sql.Timestamp getPendTime() {
		return this.pendTime;
	}

	/**
	 * 设置 计划结束时间
	 */
	public void setPendTime(java.sql.Timestamp aValue) {
		this.pendTime = aValue;
	}

	/**
	 * 实际开始时间 * @return java.util.Date
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 实际开始时间
	 */
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}

	/**
	 * 实际结束时间 * @return java.util.Date
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 实际结束时间
	 */
	public void setEndTime(java.util.Date aValue) {
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
	 * 分配人 * @return String
	 */
	public String getAssignId() {
		return this.assignId;
	}

	/**
	 * 设置 分配人
	 */
	public void setAssignId(String aValue) {
		this.assignId = aValue;
	}

	/**
	 * 所属人 * @return String
	 */
	public String getOwnerId() {
		return this.ownerId;
	}

	/**
	 * 设置 所属人
	 */
	public void setOwnerId(String aValue) {
		this.ownerId = aValue;
	}

	/**
	 * 执行人 * @return String
	 */
	public String getExeId() {
		return this.exeId;
	}

	/**
	 * 设置 执行人
	 */
	public void setExeId(String aValue) {
		this.exeId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.planId;
	}

	@Override
	public Serializable getPkId() {
		return this.planId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.planId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlanTask)) {
			return false;
		}
		PlanTask rhs = (PlanTask) object;
		return new EqualsBuilder().append(this.planId, rhs.planId)
				.append(this.subject, rhs.subject)
				.append(this.content, rhs.content)
				.append(this.pstartTime, rhs.pstartTime)
				.append(this.pendTime, rhs.pendTime)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.status, rhs.status)
				.append(this.assignId, rhs.assignId)
				.append(this.ownerId, rhs.ownerId)
				.append(this.exeId, rhs.exeId)
				.append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.planId)
				.append(this.subject).append(this.content)
				.append(this.pstartTime).append(this.pendTime)
				.append(this.startTime).append(this.endTime)
				.append(this.status).append(this.assignId).append(this.ownerId)
				.append(this.exeId).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("planId", this.planId)
				.append("subject", this.subject)
				.append("content", this.content)
				.append("pstartTime", this.pstartTime)
				.append("pendTime", this.pendTime)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime).append("status", this.status)
				.append("assignId", this.assignId)
				.append("ownerId", this.ownerId).append("exeId", this.exeId)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
