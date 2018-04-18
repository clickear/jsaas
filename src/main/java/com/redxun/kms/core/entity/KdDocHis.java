package com.redxun.kms.core.entity;

import com.redxun.core.entity.BaseTenantEntity;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * 描述：KdDocHis实体类定义
 * 知识文档历史版本
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2016-1-8-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_HIS")
@TableDefine(title = "知识文档历史版本")
public class KdDocHis extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "HIS_ID_")
	protected String hisId;
	/* 版本号 */
	@FieldDefine(title = "版本号")
	@Column(name = "VERSION_")
	protected int version;
	/* 文档内容 */
	@FieldDefine(title = "文档内容")
	@Column(name = "CONTENT_")
	@Size(max = 2147483647)
	@NotEmpty
	protected String content;
	/* 文档作者 */
	@FieldDefine(title = "文档作者")
	@Column(name = "AUTHOR_")
	@Size(max = 50)
	@NotEmpty
	protected String author;
	/* 文档标题 */
	@FieldDefine(title = "文档作者")
	@Column(name = "SUBJECT_")
	@Size(max = 128)
	@NotEmpty
	protected String subject;
	/* 文档封面 */
	@FieldDefine(title = "文档封面")
	@Column(name = "COVER_FILE_ID_")
	@Size(max = 64)
	protected String coverFileId;
	/* 文档附件IDS */
	@FieldDefine(title = "文档附件IDS")
	@Column(name = "ATTACH_IDS_")
	@Size(max = 512)
	protected String attachIds;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocHis
	 */
	public KdDocHis() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocHis
	 */
	public KdDocHis(String in_hisId) {
		this.setHisId(in_hisId);
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
	public String getHisId() {
		return this.hisId;
	}

	/**
	 * 设置
	 */
	public void setHisId(String aValue) {
		this.hisId = aValue;
	}

	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
	 * 版本号 * @return Integer
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * 设置 版本号
	 */
	public void setVersion(int aValue) {
		this.version = aValue;
	}

	/**
	 * 文档内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 文档内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 文档作者 * @return String
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * 设置 文档作者
	 */
	public void setAuthor(String aValue) {
		this.author = aValue;
	}

	/**
	 * 文档封面 * @return String
	 */
	public String getCoverFileId() {
		return this.coverFileId;
	}

	/**
	 * 设置 文档封面
	 */
	public void setCoverFileId(String aValue) {
		this.coverFileId = aValue;
	}

	/**
	 * 文档附件IDS * @return String
	 */
	public String getAttachIds() {
		return this.attachIds;
	}

	/**
	 * 设置 文档附件IDS
	 */
	public void setAttachIds(String aValue) {
		this.attachIds = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.hisId;
	}

	@Override
	public Serializable getPkId() {
		return this.hisId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.hisId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocHis)) {
			return false;
		}
		KdDocHis rhs = (KdDocHis) object;
		return new EqualsBuilder().append(this.hisId, rhs.hisId).append(this.version, rhs.version).append(this.content, rhs.content).append(this.author, rhs.author).append(this.coverFileId, rhs.coverFileId).append(this.attachIds, rhs.attachIds).append(this.tenantId, rhs.tenantId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.hisId).append(this.version).append(this.content).append(this.author).append(this.coverFileId).append(this.attachIds).append(this.tenantId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("hisId", this.hisId).append("version", this.version).append("content", this.content).append("author", this.author).append("coverFileId", this.coverFileId).append("attachIds", this.attachIds).append("tenantId", this.tenantId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
