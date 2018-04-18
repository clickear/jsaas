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
 * 描述：KdDocRem实体类定义
 * 文档推荐
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_REM")
@TableDefine(title = "文档推荐")
public class KdDocRem extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "REM_ID_")
	protected String remId;
	/**/
	@FieldDefine(title = "推荐部门ID")
	@Column(name = "DEP_ID_")
	@Size(max = 64)
	protected String depId;
	/**/
	@FieldDefine(title = "推荐个人ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	protected String userId;
	/**/
	@FieldDefine(title = "推荐级别")
	@Column(name = "LEVEL_")
	protected Integer level;
	/**/
	@FieldDefine(title = "推荐理由")
	@Column(name = "MEMO_")
	@Size(max = 1024)
	protected String memo;
	/* 推荐精华库分类ID */
	@FieldDefine(title = "推荐精华库分类ID")
	@Column(name = "REC_TREE_ID_")
	@Size(max = 64)
	protected String recTreeId;
	/**/
	@FieldDefine(title = "是否通知创建人")
	@Column(name = "NOTICE_CREATOR_")
	@Size(max = 20)
	protected String noticeCreator;
	/**/
	@FieldDefine(title = "通知方式")
	@Column(name = "NOTICE_TYPE_")
	@Size(max = 20)
	protected String noticeType;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocRem
	 */
	public KdDocRem() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocRem
	 */
	public KdDocRem(String in_remId) {
		this.setRemId(in_remId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
	}

	/**
	 * 推荐ID * @return String
	 */
	public String getRemId() {
		return this.remId;
	}

	/**
	 * 设置 推荐ID
	 */
	public void setRemId(String aValue) {
		this.remId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置
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
	 * * @return String
	 */
	public String getDepId() {
		return this.depId;
	}

	/**
	 * 设置
	 */
	public void setDepId(String aValue) {
		this.depId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * * @return Integer
	 */
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 设置
	 */
	public void setLevel(Integer aValue) {
		this.level = aValue;
	}

	/**
	 * * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	/**
	 * 推荐精华库分类ID * @return String
	 */
	public String getRecTreeId() {
		return this.recTreeId;
	}

	/**
	 * 设置 推荐精华库分类ID
	 */
	public void setRecTreeId(String aValue) {
		this.recTreeId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getNoticeCreator() {
		return this.noticeCreator;
	}

	/**
	 * 设置
	 */
	public void setNoticeCreator(String aValue) {
		this.noticeCreator = aValue;
	}

	/**
	 * * @return String
	 */
	public String getNoticeType() {
		return this.noticeType;
	}

	/**
	 * 设置
	 */
	public void setNoticeType(String aValue) {
		this.noticeType = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.remId;
	}

	@Override
	public Serializable getPkId() {
		return this.remId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.remId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocRem)) {
			return false;
		}
		KdDocRem rhs = (KdDocRem) object;
		return new EqualsBuilder().append(this.remId, rhs.remId).append(this.depId, rhs.depId).append(this.userId, rhs.userId).append(this.level, rhs.level).append(this.memo, rhs.memo).append(this.recTreeId, rhs.recTreeId).append(this.noticeCreator, rhs.noticeCreator).append(this.noticeType, rhs.noticeType).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.remId).append(this.depId).append(this.userId).append(this.level).append(this.memo).append(this.recTreeId).append(this.noticeCreator).append(this.noticeType).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("remId", this.remId).append("depId", this.depId).append("userId", this.userId).append("level", this.level).append("memo", this.memo).append("recTreeId", this.recTreeId).append("noticeCreator", this.noticeCreator).append("noticeType", this.noticeType).toString();
	}

}
