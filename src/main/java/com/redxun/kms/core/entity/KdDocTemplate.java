package com.redxun.kms.core.entity;

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

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：KdDocTemplate实体类定义
 * 文档模板
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_TEMPLATE")
@TableDefine(title = "文档模板")
public class KdDocTemplate extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "TEMP_ID_")
	protected String tempId;
	/* 模板分类ID */
	@FieldDefine(title = "模板分类ID")
	@Column(name = "TREE_ID_")
	@Size(max = 64)
	protected String treeId;
	/* 模板名称 */
	@FieldDefine(title = "模板名称")
	@Column(name = "NAME_")
	@Size(max = 80)
	@NotEmpty
	protected String name;
	/* 模板内容 */
	@FieldDefine(title = "模板内容")
	@Column(name = "CONTENT_")
	@Size(max = 65535)
	protected String content;
	/* 模板类型 词条模板 文档模板 */
	@FieldDefine(title = "模板类型")
	@Column(name = "TYPE_")
	@Size(max = 20)
	protected String type;
	/* 模板状态 */
	@FieldDefine(title = "模板状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;

	@FieldDefine(title = "知识文档")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDocTemplate", fetch = FetchType.LAZY)
	protected java.util.Set<KdDoc> kdDocs = new java.util.HashSet<KdDoc>();

	/**
	 * Default Empty Constructor for class KdDocTemplate
	 */
	public KdDocTemplate() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocTemplate
	 */
	public KdDocTemplate(String in_tempId) {
		this.setTempId(in_tempId);
	}

	public java.util.Set<KdDoc> getKdDocs() {
		return kdDocs;
	}

	public void setKdDocs(java.util.Set<KdDoc> in_kdDocs) {
		this.kdDocs = in_kdDocs;
	}

	/**
	 * 模板ID * @return String
	 */
	public String getTempId() {
		return this.tempId;
	}

	/**
	 * 设置 模板ID
	 */
	public void setTempId(String aValue) {
		this.tempId = aValue;
	}

	/**
	 * 模板分类ID * @return String
	 */
	public String getTreeId() {
		return this.treeId;
	}

	/**
	 * 设置 模板分类ID
	 */
	public void setTreeId(String aValue) {
		this.treeId = aValue;
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
	 * 模板内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 模板内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 模板类型 词条模板 文档模板 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 模板类型 词条模板 文档模板
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 模板状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 模板状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
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
		if (!(object instanceof KdDocTemplate)) {
			return false;
		}
		KdDocTemplate rhs = (KdDocTemplate) object;
		return new EqualsBuilder().append(this.tempId, rhs.tempId).append(this.treeId, rhs.treeId).append(this.name, rhs.name).append(this.content, rhs.content).append(this.type, rhs.type).append(this.status, rhs.status).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.tempId).append(this.treeId).append(this.name).append(this.content).append(this.type).append(this.status).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("tempId", this.tempId).append("treeId", this.treeId).append("name", this.name).append("content", this.content).append("type", this.type).append("status", this.status).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
