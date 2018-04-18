package com.redxun.offdoc.core.entity;

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
import com.redxun.sys.core.entity.SysTree;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：OdDocTemplate实体类定义
 * 公文模板
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:00:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OD_DOC_TEMPLATE")
@TableDefine(title = "公文模板")
public class OdDocTemplate extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "TEMP_ID_")
	protected String tempId;
	/* 模板名称 */
	@FieldDefine(title = "模板名称")
	@Column(name = "NAME_")
	@Size(max = 20)
	@NotEmpty
	protected String name;
	/* 模板描述 */
	@FieldDefine(title = "模板描述")
	@Column(name = "DESCP_")
	@Size(max = 512)
	protected String descp;
	/* 模板状态 */
	@FieldDefine(title = "模板状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;
	/* 文件ID */
	@FieldDefine(title = "文件ID")
	@Column(name = "FILE_ID_")
	@Size(max = 64)
	protected String fileId;
	/* 模板文件路径 */
	@FieldDefine(title = "模板文件路径")
	@Column(name = "FILE_PATH_")
	@Size(max = 255)
	protected String filePath;
	/* 模板分类 */
	@FieldDefine(title = "模板分类")
	@Column(name = "TEMP_TYPE_")
	@Size(max = 20)
	protected String tempType;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;

	/**
	 * Default Empty Constructor for class OdDocTemplate
	 */
	public OdDocTemplate() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OdDocTemplate
	 */
	public OdDocTemplate(String in_tempId) {
		this.setTempId(in_tempId);
	}

	public SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	/**
	 * * @return String
	 */
	public String getTempId() {
		return this.tempId;
	}

	/**
	 * 设置
	 */
	public void setTempId(String aValue) {
		this.tempId = aValue;
	}

	/**
	 * 模板名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 模板名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 模板描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 模板描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 模板状态启用=ENABLED禁用=DISABLED * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 模板状态启用=ENABLED禁用=DISABLED
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 模板分类ID * @return String
	 */
	public String getTreeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 模板分类ID
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
	 * 文件ID * @return String
	 */
	public String getFileId() {
		return this.fileId;
	}

	/**
	 * 设置 文件ID
	 */
	public void setFileId(String aValue) {
		this.fileId = aValue;
	}

	/**
	 * 模板文件路径 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 设置 模板文件路径
	 */
	public void setFilePath(String aValue) {
		this.filePath = aValue;
	}

	/**
	 * 套红模板公文发文模板收文模板 * @return String
	 */
	public String getTempType() {
		return this.tempType;
	}

	/**
	 * 设置 套红模板公文发文模板收文模板
	 */
	public void setTempType(String aValue) {
		this.tempType = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.tempId;
	}

	@Override
	public Serializable getPkId() {
		return this.tempId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.tempId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OdDocTemplate)) {
			return false;
		}
		OdDocTemplate rhs = (OdDocTemplate) object;
		return new EqualsBuilder().append(this.tempId, rhs.tempId).append(this.name, rhs.name).append(this.descp, rhs.descp).append(this.status, rhs.status).append(this.fileId, rhs.fileId).append(this.filePath, rhs.filePath)
				.append(this.tempType, rhs.tempType).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.tempId).append(this.name).append(this.descp).append(this.status).append(this.fileId).append(this.filePath).append(this.tempType).append(this.tenantId)
				.append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("tempId", this.tempId).append("name", this.name).append("descp", this.descp).append("status", this.status).append("fileId", this.fileId).append("filePath", this.filePath)
				.append("tempType", this.tempType).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
