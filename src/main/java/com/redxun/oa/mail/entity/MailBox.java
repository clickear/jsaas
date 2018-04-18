package com.redxun.oa.mail.entity;

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
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：MailBox实体类定义
 * 内部邮件收件箱
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_MAIL_BOX")
@TableDefine(title = "内部邮件收件箱")
@JsonIgnoreProperties(value={"innerMail"})
public class MailBox extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "BOX_ID_")
	protected String boxId;
	/* 员工ID */
	@FieldDefine(title = "员工ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	protected String userId;
	/* 删除标识*/
	@FieldDefine(title = "删除标识")
	@Column(name = "IS_DEL_")
	@Size(max = 20)
	@NotEmpty
	protected String isDel;
	/* 阅读标识 */
	@FieldDefine(title = "阅读标识")
	@Column(name = "IS_READ_")
	@Size(max = 20)
	@NotEmpty
	protected String isRead;
	/* 回复标识 */
	@FieldDefine(title = "回复标识")
	@Column(name = "REPLY_")
	@Size(max = 20)
	@NotEmpty
	protected String reply;
	@FieldDefine(title = "内部邮件")
	@ManyToOne
	@JoinColumn(name = "MAIL_ID_")
	protected com.redxun.oa.mail.entity.InnerMail innerMail;
	@FieldDefine(title = "邮件文件夹")
	@ManyToOne
	@JoinColumn(name = "FOLDER_ID_")
	protected com.redxun.oa.mail.entity.MailFolder mailFolder;

	/**
	 * Default Empty Constructor for class MailBox
	 */
	public MailBox() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class MailBox
	 */
	public MailBox(String in_boxId) {
		this.setBoxId(in_boxId);
	}

	public com.redxun.oa.mail.entity.InnerMail getInnerMail() {
		return innerMail;
	}

	public void setInnerMail(com.redxun.oa.mail.entity.InnerMail in_innerMail) {
		this.innerMail = in_innerMail;
	}

	public com.redxun.oa.mail.entity.MailFolder getMailFolder() {
		return mailFolder;
	}

	public void setMailFolder(com.redxun.oa.mail.entity.MailFolder in_mailFolder) {
		this.mailFolder = in_mailFolder;
	}

	/**
	 * * @return String
	 */
	public String getBoxId() {
		return this.boxId;
	}

	/**
	 * 设置
	 */
	public void setBoxId(String aValue) {
		this.boxId = aValue;
	}

	/**
	 * 邮件ID * @return String
	 */
	public String getMailId() {
		return this.getInnerMail() == null ? null : this.getInnerMail().getMailId();
	}

	/**
	 * 设置 邮件ID
	 */
	public void setMailId(String aValue) {
		if (aValue == null) {
			innerMail = null;
		} else if (innerMail == null) {
			innerMail = new com.redxun.oa.mail.entity.InnerMail(aValue);
		} else {
			innerMail.setMailId(aValue);
		}
	}

	/**
	 * 文件夹ID * @return String
	 */
	public String getFolderId() {
		return this.getMailFolder() == null ? null : this.getMailFolder().getFolderId();
	}

	/**
	 * 设置 文件夹ID
	 */
	public void setFolderId(String aValue) {
		if (aValue == null) {
			mailFolder = null;
		} else if (mailFolder == null) {
			mailFolder = new com.redxun.oa.mail.entity.MailFolder(aValue);
		} else {
			mailFolder.setFolderId(aValue);
		}
	}

	/**
	 * 员工ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 员工ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 删除标识=YES * @return String
	 */
	public String getIsDel() {
		return this.isDel;
	}

	/**
	 * 设置 删除标识=YES
	 */
	public void setIsDel(String aValue) {
		this.isDel = aValue;
	}

	/**
	 * 阅读标识 * @return String
	 */
	public String getIsRead() {
		return this.isRead;
	}

	/**
	 * 设置 阅读标识
	 */
	public void setIsRead(String aValue) {
		this.isRead = aValue;
	}

	/**
	 * 回复标识 * @return String
	 */
	public String getReply() {
		return this.reply;
	}

	/**
	 * 设置 回复标识
	 */
	public void setReply(String aValue) {
		this.reply = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.boxId;
	}

	@Override
	public Serializable getPkId() {
		return this.boxId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.boxId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof MailBox)) {
			return false;
		}
		MailBox rhs = (MailBox) object;
		return new EqualsBuilder().append(this.boxId, rhs.boxId).append(this.userId, rhs.userId).append(this.isDel, rhs.isDel).append(this.isRead, rhs.isRead).append(this.reply, rhs.reply).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.boxId).append(this.userId).append(this.isDel).append(this.isRead).append(this.reply).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("boxId", this.boxId).append("userId", this.userId).append("isDel", this.isDel).append("isRead", this.isRead).append("reply", this.reply).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
