package com.redxun.oa.pro.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import com.redxun.sys.core.entity.SysTree;

/**
 * <pre>
 * 描述：Plan实体类定义
 * 项目
 * 作者：cmc
 * 日期:2015-12-16-上午13:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PROJECT")
@TableDefine(title = "项目或产品")
public class Project extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "PROJECT_ID_")
	protected String projectId;
	/* 编号 */
	@FieldDefine(title = "编号")
	@Column(name = "PRO_NO_")
	@Size(max = 50)
	@NotEmpty
	protected String proNo;
	/* 标签名称 */
	@FieldDefine(title = "标签名称")
	@Column(name = "TAG_")
	@Size(max = 50)
	protected String tag;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String name;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	@Size(max = 65535)
	protected String descp;
	/* 负责人 */
	@FieldDefine(title = "负责人")
	@Column(name = "REPOR_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String reporId;
	/* 预计费用 */
	@FieldDefine(title = "预计费用")
	@Column(name = "COSTS_")
	protected java.math.BigDecimal costs;
	/* 启动时间 */
	@FieldDefine(title = "启动时间")
	@Column(name = "START_TIME_")
	protected java.util.Date startTime;
	/* 结束时间 */

	@FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	protected java.util.Date endTime;
	/*
	 * 状态 未开始=UNSTART 暂停中=SUSPEND 已延迟=DELAYED 已取消=CANCELED 进行中=UNDERWAY
	 * 已完成=FINISHED
	 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	/* 当前版本 */
	@FieldDefine(title = "当前版本")
	@Column(name = "VERSION_")
	@Size(max = 50)
	protected String version;
	/*
	 * 类型 PROJECT=项目 PRODUCT=产品
	 */
	@FieldDefine(title = "类型")
	@Column(name = "TYPE_")
	@Size(max = 20)
	protected String type;
	/*
	 * 项目属于什么分类
	 * */
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;

	@FieldDefine(title = "工作计划")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
	protected java.util.Set<PlanTask> plans = new java.util.HashSet<PlanTask>();
	@FieldDefine(title = "参与人、负责人、关注人")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
	protected java.util.Set<ProAttend> proAttends = new java.util.HashSet<ProAttend>();
	@FieldDefine(title = "项目或产品版本")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
	protected java.util.Set<ProVers> proVerss = new java.util.HashSet<ProVers>();
	@FieldDefine(title = "产品或项目需求")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.LAZY)
	protected java.util.Set<ReqMgr> reqMgrs = new java.util.HashSet<ReqMgr>();

	/**
	 * Default Empty Constructor for class Project
	 */
	public Project() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Project
	 */
	public Project(String in_projectId) {
		this.setProjectId(in_projectId);
	}

	public SysTree getSysTree() {	
		return sysTree;
	}

	public void setSysTree(SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	public java.util.Set<PlanTask> getPlans() {
		return plans;
	}

	public void setPlans(java.util.Set<PlanTask> in_plans) {
		this.plans = in_plans;
	}

	public java.util.Set<ProAttend> getProAttends() {
		return proAttends;
	}

	public void setProAttends(java.util.Set<ProAttend> in_proAttends) {
		this.proAttends = in_proAttends;
	}

	public java.util.Set<ProVers> getProVerss() {
		return proVerss;
	}

	public void setProVerss(java.util.Set<ProVers> in_proVerss) {
		this.proVerss = in_proVerss;
	}

	public java.util.Set<ReqMgr> getReqMgrs() {
		return reqMgrs;
	}

	public void setReqMgrs(java.util.Set<ReqMgr> in_reqMgrs) {
		this.reqMgrs = in_reqMgrs;
	}

	/**
	 * 项目ID * @return String
	 */
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * 设置 项目ID
	 */
	public void setProjectId(String aValue) {
		this.projectId = aValue;
	}

	/**
	 * 分类Id * @return String
	 */
	public String getTreeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 分类Id
	 */
	public void setTreeId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 编号 * @return String
	 */
	public String getProNo() {
		return this.proNo;
	}

	/**
	 * 设置 编号
	 */
	public void setProNo(String aValue) {
		this.proNo = aValue;
	}

	/**
	 * 标签名称 * @return String
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * 设置 标签名称
	 */
	public void setTag(String aValue) {
		this.tag = aValue;
	}

	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 负责人 * @return String
	 */
	public String getReporId() {
		return this.reporId;
	}

	/**
	 * 设置 负责人
	 */
	public void setReporId(String aValue) {
		this.reporId = aValue;
	}

	/**
	 * 预计费用 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getCosts() {
		return this.costs;
	}

	/**
	 * 设置 预计费用
	 */
	public void setCosts(java.math.BigDecimal aValue) {
		this.costs = aValue;
	}

	/**
	 * 启动时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 启动时间
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setStartTime(java.util.Date aValue) {
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
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}

	/**
	 * 状态 未开始=UNSTART 暂停中=SUSPEND 已延迟=DELAYED 已取消=CANCELED 进行中=UNDERWAY
	 * 已完成=FINISHED * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态 未开始=UNSTART 暂停中=SUSPEND 已延迟=DELAYED 已取消=CANCELED 进行中=UNDERWAY
	 * 已完成=FINISHED
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 当前版本 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 设置 当前版本
	 */
	public void setVersion(String aValue) {
		this.version = aValue;
	}

	/**
	 * 类型 PROJECT=项目 PRODUCT=产品 * @return Short
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 类型 PROJECT=项目 PRODUCT=产品
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.projectId;
	}

	@Override
	public Serializable getPkId() {
		return this.projectId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.projectId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Project)) {
			return false;
		}
		Project rhs = (Project) object;
		return new EqualsBuilder().append(this.projectId, rhs.projectId)
				.append(this.proNo, rhs.proNo).append(this.tag, rhs.tag)
				.append(this.name, rhs.name).append(this.descp, rhs.descp)
				.append(this.reporId, rhs.reporId)
				.append(this.costs, rhs.costs)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version).append(this.type, rhs.type)
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
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projectId).append(this.proNo).append(this.tag)
				.append(this.name).append(this.descp).append(this.reporId)
				.append(this.costs).append(this.startTime).append(this.endTime)
				.append(this.status).append(this.version).append(this.type)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("projectId", this.projectId)
				.append("proNo", this.proNo).append("tag", this.tag)
				.append("name", this.name).append("descp", this.descp)
				.append("reporId", this.reporId).append("costs", this.costs)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime).append("status", this.status)
				.append("version", this.version).append("type", this.type)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
