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
import javax.persistence.Transient;
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
 * 描述：Plan实体类定义
 * 产品或项目需求
 * 作者：cmc
 * 日期:2015-12-16-上午13:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_REQ_MGR")
@TableDefine(title = "产品或项目需求")
@JsonIgnoreProperties(value={"project","plans"})
public class ReqMgr extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "REQ_ID_")
	protected String reqId;
	/* 需求编码 */
	@FieldDefine(title = "需求编码")
	@Column(name = "REQ_CODE_")
	@Size(max = 50)
	@NotEmpty
	protected String reqCode;
	/* 标题 */
	@FieldDefine(title = "标题")
	@Column(name = "SUBJECT_")
	@Size(max = 128)
	@NotEmpty
	protected String subject;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	@Size(max = 65535)
	protected String descp;
	/* 父需求ID */
	@FieldDefine(title = "父需求ID")
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 50)
	protected String status;
	/* 层次 */
	@FieldDefine(title = "层次")
	@Column(name = "LEVEL_")
	protected Integer level;
	/* 审批人 */
	@FieldDefine(title = "审批人")
	@Column(name = "CHECKER_ID_")
	@Size(max = 64)
	protected String checkerId;
	/* 审批人名 */
    @Transient
	protected String checker;
	/* 负责人 */
	@FieldDefine(title = "负责人")
	@Column(name = "REP_ID_")
	@Size(max = 64)
	protected String repId;
	/* 负责人名 */
	@Transient
	protected String rep;
	/* 执行人 */
	@FieldDefine(title = "执行人")
	@Column(name = "EXE_ID_")
	@Size(max = 64)
	protected String exeId;
	/* 执行人名 */
	@Transient
	protected String exe;
	/*描述的不含html代码的文本*/
	@Transient
	protected String myDescp;
	/* 版本号 */
	@FieldDefine(title = "版本号")
	@Column(name = "VERSION_")
	@Size(max = 50)
	@NotEmpty
	protected String version;
	/*路径*/
	@FieldDefine(title = "路径")
	@Column(name = "PATH_")
	@Size(max = 512)
	protected String path;
	@FieldDefine(title = "项目或产品")
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID_")
	protected com.redxun.oa.pro.entity.Project project;

	@FieldDefine(title = "工作计划")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reqMgr", fetch = FetchType.LAZY)
	protected java.util.Set<PlanTask> plans = new java.util.HashSet<PlanTask>();

	/**
	 * Default Empty Constructor for class ReqMgr
	 */
	public ReqMgr() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ReqMgr
	 */
	public ReqMgr(String in_reqId) {
		this.setReqId(in_reqId);
	}

	public com.redxun.oa.pro.entity.Project getProject() {
		return project;
	}

	public void setProject(com.redxun.oa.pro.entity.Project in_project) {
		this.project = in_project;
	}

	public java.util.Set<PlanTask> getPlans() {
		return plans;
	}

	public void setPlans(java.util.Set<PlanTask> in_plans) {
		this.plans = in_plans;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getMyDescp() {
		return myDescp;
	}

	public void setMyDescp(String myDescp) {
		this.myDescp = myDescp;
	}

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public String getExe() {
		return exe;
	}

	public void setExe(String exe) {
		this.exe = exe;
	}

	/**
	 * 需求ID * @return String
	 */
	public String getReqId() {
		return this.reqId;
	}

	/**
	 * 设置 需求ID
	 */
	public void setReqId(String aValue) {
		this.reqId = aValue;
	}

	/**
	 * 项目或产品Id * @return String
	 */
	public String getProjectId() {
		return this.getProject() == null ? null : this.getProject()
				.getProjectId();
	}

	/**
	 * 设置 项目或产品Id
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
	 * 需求编码 * @return String
	 */
	public String getReqCode() {
		return this.reqCode;
	}

	/**
	 * 设置 需求编码
	 */
	public void setReqCode(String aValue) {
		this.reqCode = aValue;
	}

	/**
	 * 标题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 标题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
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
	 * 父需求ID * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父需求ID
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
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
	 * 层次 * @return Integer
	 */
	public Integer getLevel() {
		return this.level;
	}
	
    /**
     * 路径
     */
	public String getPath() {
		return path;
	}
    
	/**
	 * 设置路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 设置 层次
	 */
	public void setLevel(Integer aValue) {
		this.level = aValue;
	}

	/**
	 * 审批人 * @return String
	 */
	public String getCheckerId() {
		return this.checkerId;
	}

	/**
	 * 设置 审批人
	 */
	public void setCheckerId(String aValue) {
		this.checkerId = aValue;
	}

	/**
	 * 负责人 * @return String
	 */
	public String getRepId() {
		return this.repId;
	}

	/**
	 * 设置 负责人
	 */
	public void setRepId(String aValue) {
		this.repId = aValue;
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

	/**
	 * 版本号 * @return String
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 设置 版本号
	 */
	public void setVersion(String aValue) {
		this.version = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.reqId;
	}

	@Override
	public Serializable getPkId() {
		return this.reqId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.reqId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ReqMgr)) {
			return false;
		}
		ReqMgr rhs = (ReqMgr) object;
		return new EqualsBuilder().append(this.reqId, rhs.reqId)
				.append(this.reqCode, rhs.reqCode)
				.append(this.subject, rhs.subject)
				.append(this.descp, rhs.descp)
				.append(this.parentId, rhs.parentId)
				.append(this.status, rhs.status).append(this.level, rhs.level)
				.append(this.checkerId, rhs.checkerId)
				.append(this.repId, rhs.repId).append(this.exeId, rhs.exeId)
				.append(this.version, rhs.version)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.reqId)
				.append(this.reqCode).append(this.subject).append(this.descp)
				.append(this.parentId).append(this.status).append(this.level)
				.append(this.checkerId).append(this.repId).append(this.exeId)
				.append(this.version).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("reqId", this.reqId)
				.append("reqCode", this.reqCode)
				.append("subject", this.subject).append("descp", this.descp)
				.append("parentId", this.parentId)
				.append("status", this.status).append("level", this.level)
				.append("checkerId", this.checkerId)
				.append("repId", this.repId).append("exeId", this.exeId)
				.append("version", this.version)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
