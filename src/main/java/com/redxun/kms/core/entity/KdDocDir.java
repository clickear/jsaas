package com.redxun.kms.core.entity;

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
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：KdDocDir实体类定义
 * 文档索引目录
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_DIR")
@TableDefine(title = "文档索引目录")
public class KdDocDir extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DIR_ID_")
	protected String dirId;
	/*
	 * 标题等级 1级标题 2组标题
	 */
	@FieldDefine(title = "标题等级")
	@Column(name = "LEVEL_")
	@Size(max = 20)
	@NotEmpty
	protected String level;
	/* 标题 */
	@FieldDefine(title = "标题")
	@Column(name = "SUBJECT_")
	@Size(max = 120)
	@NotEmpty
	protected String subject;
	/* 标题连接锚点 */
	@FieldDefine(title = "标题连接锚点")
	@Column(name = "ANCHOR_")
	@Size(max = 255)
	protected String anchor;
	/* 上级目录ID */
	@FieldDefine(title = "上级目录ID")
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocDir
	 */
	public KdDocDir() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocDir
	 */
	public KdDocDir(String in_dirId) {
		this.setDirId(in_dirId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
	}

	/**
	 * * @return String
	 */
	public String getDirId() {
		return this.dirId;
	}

	/**
	 * 设置
	 */
	public void setDirId(String aValue) {
		this.dirId = aValue;
	}

	/**
	 * 文档ID * @return String
	 */
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置 文档ID
	 */
	public void setDocId(String aValue) {
		if (aValue == null) {
			kdDoc = null;
		} else if (kdDoc == null) {
			kdDoc = new com.redxun.kms.core.entity.KdDoc(aValue);
		} else {
			kdDoc.setDocId(aValue);
		}
	}

	/**
	 * 标题等级 1级标题 2组标题 * @return String
	 */
	public String getLevel() {
		return this.level;
	}

	/**
	 * 设置 标题等级 1级标题 2组标题
	 */
	public void setLevel(String aValue) {
		this.level = aValue;
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
	 * 标题连接锚点 * @return String
	 */
	public String getAnchor() {
		return this.anchor;
	}

	/**
	 * 设置 标题连接锚点
	 */
	public void setAnchor(String aValue) {
		this.anchor = aValue;
	}

	/**
	 * 上级目录ID * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 上级目录ID
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.dirId;
	}

	@Override
	public Serializable getPkId() {
		return this.dirId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.dirId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocDir)) {
			return false;
		}
		KdDocDir rhs = (KdDocDir) object;
		return new EqualsBuilder().append(this.dirId, rhs.dirId).append(this.level, rhs.level).append(this.subject, rhs.subject).append(this.anchor, rhs.anchor).append(this.parentId, rhs.parentId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.dirId).append(this.level).append(this.subject).append(this.anchor).append(this.parentId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("dirId", this.dirId).append("level", this.level).append("subject", this.subject).append("anchor", this.anchor).append("parentId", this.parentId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
