package com.redxun.oa.pro.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

import java.io.Serializable;
import java.util.Date;

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
 * 描述：Plan实体类定义
 * 项目版本
 * 作者：cmc
 * 日期:2015-12-16-上午13:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PRO_VERS")
@TableDefine(title = "项目或产品版本")
@JsonIgnoreProperties("project")
public class ProVers extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "VERSION_ID_")
	protected String versionId;
	/* 版本号 */
	@FieldDefine(title = "版本号")
	@Column(name = "VERSION_")
	@Size(max = 50)
	@NotEmpty
	protected String version;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_TIME_")
	protected Date startTime;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_TIME_")
	protected Date endTime;
	/*
	 * 状态 DRAFTED=草稿 RUNNING=进行中 FINISHED=完成
	 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	@Size(max = 65535)
	protected String descp;
	@FieldDefine(title = "项目或产品")
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID_")
	protected com.redxun.oa.pro.entity.Project project;

	/**
	 * Default Empty Constructor for class ProVers
	 */
	public ProVers() {
		super();
	}

	/**
	 * 开始时间 * @return Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 设置开始时间
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间
	 * 
	 * @return Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置结束时间
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 状态
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Default Key Fields Constructor for class ProVers
	 */
	public ProVers(String in_versionId) {
		this.setVersionId(in_versionId);
	}

	public com.redxun.oa.pro.entity.Project getProject() {
		return project;
	}

	public void setProject(com.redxun.oa.pro.entity.Project in_project) {
		this.project = in_project;
	}

	/**
	 * 版本ID * @return String
	 */
	public String getVersionId() {
		return this.versionId;
	}

	/**
	 * 设置 版本ID
	 */
	public void setVersionId(String aValue) {
		this.versionId = aValue;
	}

	/**
	 * 项目或产品ID * @return String
	 */
	public String getProjectId() {
		return this.getProject() == null ? null : this.getProject().getProjectId();
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

	@Override
	public String getIdentifyLabel() {
		return this.versionId;
	}

	@Override
	public Serializable getPkId() {
		return this.versionId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.versionId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProVers)) {
			return false;
		}
		ProVers rhs = (ProVers) object;
		return new EqualsBuilder().append(this.versionId, rhs.versionId).append(this.version, rhs.version).append(this.descp, rhs.descp).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.versionId).append(this.version).append(this.descp).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("versionId", this.versionId).append("version", this.version).append("descp", this.descp).append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
