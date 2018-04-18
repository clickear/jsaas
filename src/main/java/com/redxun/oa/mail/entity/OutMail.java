package com.redxun.oa.mail.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;
import com.redxun.sys.core.entity.SysFile;

/**
 * <pre>
 * 描述：Mail实体类定义
 * 外部邮件
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_MAIL")
@TableDefine(title = "外部邮件")
@JsonIgnoreProperties(value={"mails","mailFolders","infMailFiles"})
public class OutMail extends BaseTenantEntity {
	
	public final static String STATUS_COMMEN="COMMEN";
	public final static String STATUS_DELETED="DELETED";
	
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "MAIL_ID_")
	protected String mailId;
	/* 外部邮箱标识ID */
	@FieldDefine(title = "外部邮箱标识ID")
	@Column(name = "UID_")
	@Size(max = 512)
	protected String uid;
	/* 用户ID */
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/* 主题 */
	@FieldDefine(title = "主题")
	@Column(name = "SUBJECT_")
	@Size(max = 512)
	@NotEmpty
	protected String subject;
	/* 内容 */
	@FieldDefine(title = "内容")
	@Column(name = "CONTENT_")
	@Size(max = 2147483647)
	protected String content;
	/* 发件人地址 */
	@FieldDefine(title = "发件人地址")
	@Column(name = "SENDER_ADDRS_")
	@Size(max = 65535)
	@NotEmpty
	protected String senderAddrs;
	/* 发件人地址别名 */
	@FieldDefine(title = "发件人地址别名")
	@Column(name = "SENDER_ALIAS_")
	@Size(max = 65535)
	protected String senderAlias;
	/* 收件人地址 */
	@FieldDefine(title = "收件人地址")
	@Column(name = "REC_ADDRS_")
	@Size(max = 65535)
	@NotEmpty
	protected String recAddrs;
	/* 收件人地址别名 */
	@FieldDefine(title = "收件人地址别名")
	@Column(name = "REC_ALIAS_")
	@Size(max = 65535)
	protected String recAlias;
	/* 抄送人地址 */
	@FieldDefine(title = "抄送人地址")
	@Column(name = "CC_ADDRS_")
	@Size(max = 65535)
	protected String ccAddrs;
	/* 抄送人地址别名 */
	@FieldDefine(title = "抄送人地址别名")
	@Column(name = "CC_ALIAS_")
	@Size(max = 65535)
	protected String ccAlias;
	/* 暗送人地址 */
	@FieldDefine(title = "暗送人地址")
	@Column(name = "BCC_ADDRS_")
	@Size(max = 65535)
	protected String bccAddrs;
	/* 暗送人地址别名 */
	@FieldDefine(title = "暗送人地址别名")
	@Column(name = "BCC_ALIAS_")
	@Size(max = 65535)
	protected String bccAlias;
	/* 发送日期 */
	@FieldDefine(title = "发送日期")
	@Column(name = "SEND_DATE_")
	protected java.util.Date sendDate;
	/*
	 * 阅读状态
	 */
	@FieldDefine(title = "阅读状态")
	@Column(name = "READ_FLAG_")
	@Size(max = 8)
	@NotEmpty
	protected String readFlag;
	/*
	 * 回复状态
	 */
	@FieldDefine(title = "回复状态")
	@Column(name = "REPLY_FLAG_")
	@Size(max = 8)
	@NotEmpty
	protected String replyFlag;
	@FieldDefine(title = "邮件状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	@FieldDefine(title = "外部邮箱设置")
	@ManyToOne
	@JoinColumn(name = "CONFIG_ID_")
	protected com.redxun.oa.mail.entity.MailConfig mailConfig;
	@FieldDefine(title = "文件夹ID")
	@ManyToOne
	@JoinColumn(name = "FOLDER_ID_")
	protected com.redxun.oa.mail.entity.MailFolder mailFolder;

	@FieldDefine(title = "外部邮箱附件")
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="INF_MAIL_FILE",
	joinColumns=
	@JoinColumn(name="MAIL_ID_", referencedColumnName="MAIL_ID_"),
	inverseJoinColumns=
	@JoinColumn(name="FILE_ID_", referencedColumnName="FILE_ID_"))
	protected java.util.Set<SysFile> sysFiles = new java.util.HashSet<SysFile>();

	/**
	 * Default Empty Constructor for class Mail
	 */
	public OutMail() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Mail
	 */
	public OutMail(String in_mailId) {
		this.setMailId(in_mailId);
	}

	public com.redxun.oa.mail.entity.MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(
			com.redxun.oa.mail.entity.MailConfig in_mailConfig) {
		this.mailConfig = in_mailConfig;
	}
	

	public com.redxun.oa.mail.entity.MailFolder getMailFolder() {
		return mailFolder;
	}

	public void setMailFolder(com.redxun.oa.mail.entity.MailFolder mailFolder) {
		this.mailFolder = mailFolder;
	}

	public java.util.Set<SysFile> getInfMailFiles() {
		return sysFiles;
	}

	public void setInfMailFiles(java.util.Set<SysFile> in_infMailFiles) {
		this.sysFiles = in_infMailFiles;
	}

	/**
	 * * @return String
	 */
	public String getMailId() {
		return this.mailId;
	}

	/**
	 * 设置
	 */
	public void setMailId(String aValue) {
		this.mailId = aValue;
	}

	/**
	 * 外部邮箱标识ID * @return String
	 */
	public String getUid() {
		return this.uid;
	}

	/**
	 * 设置 外部邮箱标识ID
	 */
	public void setUid(String aValue) {
		this.uid = aValue;
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

	/**
	 * 邮箱设置ID * @return String
	 */
	public String getConfigId() {
		return this.getMailConfig() == null ? null : this.getMailConfig()
				.getConfigId();
	}

	/**
	 * 设置 邮箱设置ID
	 */
	public void setConfigId(String aValue) {
		if (aValue == null) {
			mailConfig = null;
		} else if (mailConfig == null) {
			mailConfig = new com.redxun.oa.mail.entity.MailConfig(aValue);
		} else {
			mailConfig.setConfigId(aValue);
		}
	}
	
	/**
	 * 文件夹ID * @return String
	 */
	public String getFolderId() {
		return this.getMailFolder() == null ? null : this.getMailFolder()
				.getFolderId();
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
	 * 主题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 主题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 发件人地址 * @return String
	 */
	public String getSenderAddrs() {
		return this.senderAddrs;
	}

	/**
	 * 设置 发件人地址
	 */
	public void setSenderAddrs(String aValue) {
		this.senderAddrs = aValue;
	}

	/**
	 * 发件人地址别名 * @return String
	 */
	public String getSenderAlias() {
		return this.senderAlias;
	}

	/**
	 * 设置 发件人地址别名
	 */
	public void setSenderAlias(String aValue) {
		this.senderAlias = aValue;
	}

	/**
	 * 收件人地址 * @return String
	 */
	public String getRecAddrs() {
		return this.recAddrs;
	}

	/**
	 * 设置 收件人地址
	 */
	public void setRecAddrs(String aValue) {
		this.recAddrs = aValue;
	}

	/**
	 * 收件人地址别名 * @return String
	 */
	public String getRecAlias() {
		return this.recAlias;
	}

	/**
	 * 设置 收件人地址别名
	 */
	public void setRecAlias(String aValue) {
		this.recAlias = aValue;
	}

	/**
	 * 抄送人地址 * @return String
	 */
	public String getCcAddrs() {
		return this.ccAddrs;
	}

	/**
	 * 设置 抄送人地址
	 */
	public void setCcAddrs(String aValue) {
		this.ccAddrs = aValue;
	}

	/**
	 * 抄送人地址别名 * @return String
	 */
	public String getCcAlias() {
		return this.ccAlias;
	}

	/**
	 * 设置 抄送人地址别名
	 */
	public void setCcAlias(String aValue) {
		this.ccAlias = aValue;
	}

	/**
	 * 暗送人地址 * @return String
	 */
	public String getBccAddrs() {
		return this.bccAddrs;
	}

	/**
	 * 设置 暗送人地址
	 */
	public void setBccAddrs(String aValue) {
		this.bccAddrs = aValue;
	}

	/**
	 * 暗送人地址别名 * @return String
	 */
	public String getBccAlias() {
		return this.bccAlias;
	}

	/**
	 * 设置 暗送人地址别名
	 */
	public void setBccAlias(String aValue) {
		this.bccAlias = aValue;
	}

	/**
	 * 发送日期 * @return java.util.Date
	 */
	 @JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getSendDate() {
		return this.sendDate;
	}

	/**
	 * 设置 发送日期
	 */
	public void setSendDate(java.util.Date aValue) {
		this.sendDate = aValue;
	}

	/**
	 * 0:未阅 1:已阅 * @return String
	 */
	public String getReadFlag() {
		return this.readFlag;
	}

	/**
	 * 设置 0:未阅 1:已阅
	 */
	public void setReadFlag(String aValue) {
		this.readFlag = aValue;
	}

	/**
	 * 0:未回复 1;已回复 * @return String
	 */
	public String getReplyFlag() {
		return this.replyFlag;
	}

	/**
	 * 设置 0:未回复 1;已回复
	 */
	public void setReplyFlag(String aValue) {
		this.replyFlag = aValue;
	}

	/**
	 * COMMEN:正常 DELETED;删除 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 COMMEN:正常 DELETED;删除
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.mailId;
	}

	@Override
	public Serializable getPkId() {
		return this.mailId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.mailId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OutMail)) {
			return false;
		}
		OutMail rhs = (OutMail) object;
		return new EqualsBuilder().append(this.mailId, rhs.mailId)
				.append(this.uid, rhs.uid).append(this.userId, rhs.userId)
				.append(this.subject, rhs.subject)
				.append(this.content, rhs.content)
				.append(this.senderAddrs, rhs.senderAddrs)
				.append(this.senderAlias, rhs.senderAlias)
				.append(this.recAddrs, rhs.recAddrs)
				.append(this.recAlias, rhs.recAlias)
				.append(this.ccAddrs, rhs.ccAddrs)
				.append(this.ccAlias, rhs.ccAlias)
				.append(this.bccAddrs, rhs.bccAddrs)
				.append(this.bccAlias, rhs.bccAlias)
				.append(this.sendDate, rhs.sendDate)
				.append(this.readFlag, rhs.readFlag)
				.append(this.replyFlag, rhs.replyFlag)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.mailId)
				.append(this.uid).append(this.userId).append(this.subject)
				.append(this.content).append(this.senderAddrs)
				.append(this.senderAlias).append(this.recAddrs)
				.append(this.recAlias).append(this.ccAddrs)
				.append(this.ccAlias).append(this.bccAddrs)
				.append(this.bccAlias).append(this.sendDate)
				.append(this.readFlag).append(this.replyFlag)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("mailId", this.mailId)
				.append("uid", this.uid).append("userId", this.userId)
				.append("subject", this.subject)
				.append("content", this.content)
				.append("senderAddrs", this.senderAddrs)
				.append("senderAlias", this.senderAlias)
				.append("recAddrs", this.recAddrs)
				.append("recAlias", this.recAlias)
				.append("ccAddrs", this.ccAddrs)
				.append("ccAlias", this.ccAlias)
				.append("bccAddrs", this.bccAddrs)
				.append("bccAlias", this.bccAlias)
				.append("sendDate", this.sendDate)
				.append("readFlag", this.readFlag)
				.append("replyFlag", this.replyFlag)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
