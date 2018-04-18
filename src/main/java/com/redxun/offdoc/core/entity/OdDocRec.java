package com.redxun.offdoc.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

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
 * 描述：OdDocRec实体类定义
 * TODO: add class/table comments
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:00:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OD_DOC_REC")
@TableDefine(title = "TODO: add class/table comments")
public class OdDocRec extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "REC_ID_")
	protected String recId;
	/* 收文部门ID */
	@FieldDefine(title = "收文部门ID")
	@Column(name = "REC_DEP_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String recDepId;
	/* 收文单位类型 */
	@FieldDefine(title = "收文单位类型")
	@Column(name = "REC_DTYPE_")
	@Size(max = 20)
	protected String recDtype;
	/* 是否阅读 */
	@FieldDefine(title = "是否阅读")
	@Column(name = "IS_READ_")
	@Size(max = 20)
	protected String isRead;
	/* 阅读时间 */
	@FieldDefine(title = "阅读时间")
	@Column(name = "READ_TIME_")
	protected java.util.Date readTime;
	/* 反馈意见 */
	@FieldDefine(title = "反馈意见")
	@Column(name = "FEED_BACK_")
	@Size(max = 200)
	protected String feedBack;
	/* 签收人ID */
	@FieldDefine(title = "签收人ID")
	@Column(name = "SIGN_USR_ID_")
	@Size(max = 64)
	protected String signUsrId;
	/* 签收状态 */
	@FieldDefine(title = "签收状态")
	@Column(name = "SIGN_STATUS_")
	@Size(max = 20)
	protected String signStatus;
	/* 签收时间 */
	@FieldDefine(title = "签收时间")
	@Column(name = "SIGN_TIME_")
	protected java.util.Date signTime;
	@FieldDefine(title = "TODO: add class/table comments")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.offdoc.core.entity.OdDocument odDocument;

	/**
	 * Default Empty Constructor for class OdDocRec
	 */
	public OdDocRec() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OdDocRec
	 */
	public OdDocRec(String in_recId) {
		this.setRecId(in_recId);
	}

	public com.redxun.offdoc.core.entity.OdDocument getOdDocument() {
		return odDocument;
	}

	public void setOdDocument(com.redxun.offdoc.core.entity.OdDocument in_odDocument) {
		this.odDocument = in_odDocument;
	}

	/**
	 * 接收ID * @return String
	 */
	public String getRecId() {
		return this.recId;
	}

	/**
	 * 设置 接收ID
	 */
	public void setRecId(String aValue) {
		this.recId = aValue;
	}

	/**
	 * 收发文ID * @return String
	 */
	public String getDocId() {
		return this.getOdDocument() == null ? null : this.getOdDocument().getDocId();
	}

	/**
	 * 设置 收发文ID
	 */
	public void setDocId(String aValue) {
		if (aValue == null) {
			odDocument = null;
		} else if (odDocument == null) {
			odDocument = new com.redxun.offdoc.core.entity.OdDocument(aValue);
		} else {
			odDocument.setDocId(aValue);
		}
	}

	/**
	 * 收文部门ID * @return String
	 */
	public String getRecDepId() {
		return this.recDepId;
	}

	/**
	 * 设置 收文部门ID
	 */
	public void setRecDepId(String aValue) {
		this.recDepId = aValue;
	}

	/**
	 * 收文单位类型 * @return String
	 */
	public String getRecDtype() {
		return this.recDtype;
	}

	/**
	 * 设置 收文单位类型
	 */
	public void setRecDtype(String aValue) {
		this.recDtype = aValue;
	}

	/**
	 * 是否阅读 * @return String
	 */
	public String getIsRead() {
		return this.isRead;
	}

	/**
	 * 设置 是否阅读
	 */
	public void setIsRead(String aValue) {
		this.isRead = aValue;
	}

	/**
	 * 阅读时间 * @return java.util.Date
	 */
	public java.util.Date getReadTime() {
		return this.readTime;
	}

	/**
	 * 设置 阅读时间
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setReadTime(java.util.Date aValue) {
		this.readTime = aValue;
	}

	/**
	 * 反馈意见 * @return String
	 */
	public String getFeedBack() {
		return this.feedBack;
	}

	/**
	 * 设置 反馈意见
	 */
	public void setFeedBack(String aValue) {
		this.feedBack = aValue;
	}

	/**
	 * 签收人ID * @return String
	 */
	public String getSignUsrId() {
		return this.signUsrId;
	}

	/**
	 * 设置 签收人ID
	 */
	public void setSignUsrId(String aValue) {
		this.signUsrId = aValue;
	}

	/**
	 * 签收状态 * @return String
	 */
	public String getSignStatus() {
		return this.signStatus;
	}

	/**
	 * 设置 签收状态
	 */
	public void setSignStatus(String aValue) {
		this.signStatus = aValue;
	}

	/**
	 * 签收时间 * @return java.util.Date
	 */
	public java.util.Date getSignTime() {
		return this.signTime;
	}

	/**
	 * 设置 签收时间
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setSignTime(java.util.Date aValue) {
		this.signTime = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.recId;
	}

	@Override
	public Serializable getPkId() {
		return this.recId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.recId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OdDocRec)) {
			return false;
		}
		OdDocRec rhs = (OdDocRec) object;
		return new EqualsBuilder().append(this.recId, rhs.recId).append(this.recDepId, rhs.recDepId).append(this.recDtype, rhs.recDtype).append(this.isRead, rhs.isRead).append(this.readTime, rhs.readTime)
				.append(this.feedBack, rhs.feedBack).append(this.signUsrId, rhs.signUsrId).append(this.signStatus, rhs.signStatus).append(this.signTime, rhs.signTime).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.recId).append(this.recDepId).append(this.recDtype).append(this.isRead).append(this.readTime).append(this.feedBack).append(this.signUsrId).append(this.signStatus)
				.append(this.signTime).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("recId", this.recId).append("recDepId", this.recDepId).append("recDtype", this.recDtype).append("isRead", this.isRead).append("readTime", this.readTime).append("feedBack", this.feedBack)
				.append("signUsrId", this.signUsrId).append("signStatus", this.signStatus).append("signTime", this.signTime).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
