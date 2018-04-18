package com.redxun.oa.doc.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
 * 描述：文件夹实体类定义
 * 文档文件夹
 * 作者：陈茂昌
 * 日期:2015-11-25-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_DOC_FOLDER")
@TableDefine(title = "文档文件夹")
@JsonIgnoreProperties(value = { "docRights", "docs" })
public class DocFolder extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "FOLDER_ID_")
	protected String folderId;
	/* 目录名称 */
	@FieldDefine(title = "目录名称")
	@Column(name = "NAME_")
	@Size(max = 128)
	@NotEmpty
	protected String name;
	/* 父目录 */
	@FieldDefine(title = "父目录")
	@Column(name = "PARENT_")
	@Size(max = 64)
	protected String parent;
	/* 路径 */
	@FieldDefine(title = "路径")
	@Column(name = "PATH_")
	@Size(max = 128)
	protected String path;
	
	///新加的
	/* 序号 */
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	protected Integer sn;
	/* 层次 */
	@FieldDefine(title = "层次")
	@Column(name = "DEPTH_")
	protected Integer depth;

	/* 文档目录类型 */
	@FieldDefine(title = "文档目录类型")
	@Column(name = "TYPE_")
	protected String type;
	/* 共享 */
	@FieldDefine(title = "共享")
	@Column(name = "SHARE_")
	@Size(max = 8)
	@NotEmpty
	protected String share;
	/* 文档描述 */
	@FieldDefine(title = "文档描述")
	@Column(name = "DESCP")
	@Size(max = 256)
	protected String descp;
	/* 用户ID */
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;

	@FieldDefine(title = "文档")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "docFolder", fetch = FetchType.LAZY)
	protected java.util.Set<Doc> docs = new java.util.HashSet<Doc>();
	@FieldDefine(title = "文档或目录的权限")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "docFolder", fetch = FetchType.LAZY)
	protected java.util.Set<DocRight> docRights = new java.util.HashSet<DocRight>();

	/**
	 * Default Empty Constructor for class DocFolder
	 */
	public DocFolder() {
		super();
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Default Key Fields Constructor for class DocFolder
	 */
	public DocFolder(String in_folderId) {
		this.setFolderId(in_folderId);
	}

	public java.util.Set<Doc> getDocs() {
		return docs;
	}

	public void setDocs(java.util.Set<Doc> in_docs) {
		this.docs = in_docs;
	}

	public java.util.Set<DocRight> getDocRights() {
		return docRights;
	}

	public void setDocRights(java.util.Set<DocRight> in_docRights) {
		this.docRights = in_docRights;
	}

	/**
	 * * @return String
	 */
	public String getFolderId() {
		return this.folderId;
	}

	/**
	 * 设置
	 */
	public void setFolderId(String aValue) {
		this.folderId = aValue;
	}

	/**
	 * 目录名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 目录名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 父目录 * @return String
	 */
	public String getParent() {
		return this.parent;
	}

	/**
	 * 设置 父目录
	 */
	public void setParent(String aValue) {
		this.parent = aValue;
	}

	/**
	 * 路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 设置 路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}

	/**
	 * 层次 * @return Integer
	 */
	public Integer getDepth() {
		return this.depth;
	}

	/**
	 * 设置 层次
	 */
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}

	/**
	 * 共享 * @return String
	 */
	public String getShare() {
		return this.share;
	}

	/**
	 * 设置 共享
	 */
	public void setShare(String aValue) {
		this.share = aValue;
	}

	/**
	 * 文档描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 文档描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 用户ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 用户ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.folderId;
	}

	@Override
	public Serializable getPkId() {
		return this.folderId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.folderId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DocFolder)) {
			return false;
		}
		DocFolder rhs = (DocFolder) object;
		return new EqualsBuilder().append(this.folderId, rhs.folderId)
				.append(this.name, rhs.name).append(this.parent, rhs.parent)
				.append(this.path, rhs.path).append(this.depth, rhs.depth)
				.append(this.share, rhs.share).append(this.descp, rhs.descp)
				.append(this.userId, rhs.userId)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.folderId)
				.append(this.name).append(this.parent).append(this.path)
				.append(this.depth).append(this.share).append(this.descp)
				.append(this.userId).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("folderId", this.folderId)
				.append("name", this.name).append("parent", this.parent)
				.append("path", this.path).append("depth", this.depth)
				.append("share", this.share).append("descp", this.descp)
				.append("userId", this.userId)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
