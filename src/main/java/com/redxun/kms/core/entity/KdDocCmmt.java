package com.redxun.kms.core.entity;

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

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：KdDocCmmt实体类定义
 * 知识文档点评
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2016-1-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_CMMT")
@TableDefine(title = "知识文档点评")
public class KdDocCmmt extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "COMMENT_ID_")
	protected String commentId;
	/* 分数 */
	@FieldDefine(title = "分数")
	@Column(name = "SCORE_")
	protected Integer score;
	/* 点评内容 */
	@FieldDefine(title = "点评内容")
	@Column(name = "CONTENT_")
	@Size(max = 1024)
	protected String content;
	/*
	 * 评价
	 */
	@FieldDefine(title = "评价")
	@Column(name = "LEVEL_")
	@Size(max = 20)
	protected String level;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocCmmt
	 */
	public KdDocCmmt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocCmmt
	 */
	public KdDocCmmt(String in_commentId) {
		this.setCommentId(in_commentId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
	}

	/**
	 * 点评ID * @return String
	 */
	public String getCommentId() {
		return this.commentId;
	}

	/**
	 * 设置 点评ID
	 */
	public void setCommentId(String aValue) {
		this.commentId = aValue;
	}

	/**
	 * 知识ID * @return String
	 */
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置 知识ID
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
	 * 分数 * @return Integer
	 */
	public Integer getScore() {
		return this.score;
	}

	/**
	 * 设置 分数
	 */
	public void setScore(Integer aValue) {
		this.score = aValue;
	}

	/**
	 * 点评内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 点评内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 非常好 很好 一般 差 很差 * @return String
	 */
	public String getLevel() {
		return this.level;
	}

	/**
	 * 设置 非常好 很好 一般 差 很差
	 */
	public void setLevel(String aValue) {
		this.level = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.commentId;
	}

	@Override
	public Serializable getPkId() {
		return this.commentId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.commentId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocCmmt)) {
			return false;
		}
		KdDocCmmt rhs = (KdDocCmmt) object;
		return new EqualsBuilder().append(this.commentId, rhs.commentId).append(this.score, rhs.score).append(this.content, rhs.content).append(this.level, rhs.level).append(this.tenantId, rhs.tenantId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.commentId).append(this.score).append(this.content).append(this.level).append(this.tenantId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("commentId", this.commentId).append("score", this.score).append("content", this.content).append("level", this.level).append("tenantId", this.tenantId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
