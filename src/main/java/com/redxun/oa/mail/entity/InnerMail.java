package com.redxun.oa.mail.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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

/**
 * <pre>
 * 描述：InnerMail实体类定义
 * 内部邮件
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_INNER_MAIL")
@TableDefine(title = "内部邮件")
@JsonIgnoreProperties(value={"mailBoxs"})
public class InnerMail extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "MAIL_ID_")
	protected String mailId;
	/* 用户ID */
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/*发信人*/
	@FieldDefine(title = "发信人")
	@Column(name = "SENDER_")
	@Size(max = 32)
	@NotEmpty
	protected String sender;
	/* 抄送人ID列表*/
	@FieldDefine(title = "抄送人ID列表")
	@Column(name = "CC_IDS_")
	@Size(max = 65535)
	protected String ccIds;
	/* 抄送人姓名列表 */
	@FieldDefine(title = "抄送人姓名列表")
	@Column(name = "CC_NAMES_")
	@Size(max = 65535)
	protected String ccNames;
	/* 邮件标题 */
	@FieldDefine(title = "邮件标题")
	@Column(name = "SUBJECT_")
	@Size(max = 256)
	@NotEmpty
	protected String subject;
	/* 邮件内容 */
	@FieldDefine(title = "邮件内容")
	@Column(name = "CONTENT_")
	@Size(max = 2147483647)
	@NotEmpty
	protected String content;
	/*发信人Id*/
	@FieldDefine(title = "发信者Id")
	@Column(name = "SENDER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String senderId;
	/* 紧急程度 */
	@FieldDefine(title = "紧急程度")
	@Column(name = "URGE_")
	@Size(max = 32)
	@NotEmpty
	protected String urge;
	/*发送时间*/
	@FieldDefine(title = "发送时间")
	@Column(name = "SENDER_TIME_")
	protected java.util.Date senderTime;
	/* 收件人姓名列表 */
	@FieldDefine(title = "收件人姓名列表")
	@Column(name = "REC_NAMES_")
	@Size(max = 65535)
	@NotEmpty
	protected String recNames;
	/* 收件人ID列表*/
	@FieldDefine(title = "收件人ID列表")
	@Column(name = "REC_IDS_")
	@Size(max = 65535)
	@NotEmpty
	protected String recIds;
	/* 邮件状态 */
	@FieldDefine(title = "邮件状态")
	@Column(name = "STATUS_")
	protected Short status;
	/* 附件Ids */
	@FieldDefine(title = "附件Ids")
	@Column(name = "FILE_IDS_")
	@Size(max = 500)
	protected String fileIds;
	/* 附件名称列表 */
	@FieldDefine(title = "附件名称列表")
	@Column(name = "FILE_NAMES_")
	@Size(max = 500)
	protected String fileNames;
	/* 文件夹ID */
	@FieldDefine(title = "文件夹ID")
	@Column(name = "FOLDER_ID_")
	@Size(max = 64)
	protected String folderId;
	/* 删除标识*/
	@FieldDefine(title = "删除标识")
	@Column(name = "DEL_FLAG_")
	@Size(max = 20)
	protected String delFlag;
	/*删除标识*/
	@Transient
	private String isDel;
	/*阅读标识*/
	@Transient
	private String isRead;   
	/*回复标识*/
	@Transient
	private String isReply;  

	@FieldDefine(title = "内部邮件收件箱")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "innerMail", fetch = FetchType.LAZY)
	protected java.util.Set<MailBox> mailBoxs = new java.util.HashSet<MailBox>();

	/**
	 * Default Empty Constructor for class InnerMail
	 */
	public InnerMail() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InnerMail
	 */
	public InnerMail(String in_mailId) {
		this.setMailId(in_mailId);
	}

	public java.util.Set<MailBox> getMailBoxs() {
		return mailBoxs;
	}

	public void setMailBoxs(java.util.Set<MailBox> in_mailBoxs) {
		this.mailBoxs = in_mailBoxs;
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
	 *发信人 * @return String
	 */
	public String getSender() {
		return this.sender;
	}

	/**
	 * 设置 发信人
	 */
	public void setSender(String aValue) {
		this.sender = aValue;
	}

	/**
	 * 抄送人ID列表 用','分开 * @return String
	 */
	public String getCcIds() {
		return this.ccIds;
	}

	/**
	 * 设置 抄送人ID列表 用','分开
	 */
	public void setCcIds(String aValue) {
		this.ccIds = aValue;
	}

	/**
	 * 抄送人姓名列表 * @return String
	 */
	public String getCcNames() {
		return this.ccNames;
	}

	/**
	 * 设置 抄送人姓名列表
	 */
	public void setCcNames(String aValue) {
		this.ccNames = aValue;
	}

	/**
	 * 邮件标题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 邮件标题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 邮件内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 邮件内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 发信人Id * @return String
	 */
	public String getSenderId() {
		return this.senderId;
	}

	/**
	 * 设置 发信人Id
	 */
	public void setSenderId(String aValue) {
		this.senderId = aValue;
	}

	/**
	 * 1=一般 2=重要 3=非常重要 * @return String
	 */
	public String getUrge() {
		return this.urge;
	}

	/**
	 * 设置 1=一般 2=重要 3=非常重要
	 */
	public void setUrge(String aValue) {
		this.urge = aValue;
	}

	/**
	 * 发信时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getSenderTime() {
		return this.senderTime;
	}

	/**
	 * 设置 发信时间
	 */
	public void setSenderTime(java.util.Date aValue) {
		this.senderTime = aValue;
	}

	/**
	 * 收件人姓名列表 * @return String
	 */
	public String getRecNames() {
		return this.recNames;
	}

	/**
	 * 设置 收件人姓名列表
	 */
	public void setRecNames(String aValue) {
		this.recNames = aValue;
	}

	/**
	 * 收件人ID列表 用','分隔 * @return String
	 */
	public String getRecIds() {
		return this.recIds;
	}

	/**
	 * 设置 收件人ID列表 用','分隔
	 */
	public void setRecIds(String aValue) {
		this.recIds = aValue;
	}

	/**
	 * 邮件状态 1=正式邮件 0=草稿邮件 * @return Short
	 */
	public Short getStatus() {
		return this.status;
	}

	/**
	 * 设置 邮件状态 1=正式邮件 0=草稿邮件
	 */
	public void setStatus(Short aValue) {
		this.status = aValue;
	}

	/**
	 * 附件Ids，多个附件的ID，通过,分割 * @return String
	 */
	public String getFileIds() {
		return this.fileIds;
	}

	/**
	 * 设置 附件Ids，多个附件的ID，通过,分割
	 */
	public void setFileIds(String aValue) {
		this.fileIds = aValue;
	}

	/**
	 * 附件名称列表，通过,进行分割 * @return String
	 */
	public String getFileNames() {
		return this.fileNames;
	}

	/**
	 * 设置 附件名称列表，通过,进行分割
	 */
	public void setFileNames(String aValue) {
		this.fileNames = aValue;
	}

	/**
	 * 文件夹ID * @return String
	 */
	public String getFolderId() {
		return this.folderId;
	}

	/**
	 * 设置 文件夹ID
	 */
	public void setFolderId(String aValue) {
		this.folderId = aValue;
	}

	/**
	 * 删除标识 YES NO * @return String
	 */
	public String getDelFlag() {
		return this.delFlag;
	}

	/**
	 * 设置 删除标识 YES NO
	 */
	public void setDelFlag(String aValue) {
		this.delFlag = aValue;
	}
	
	

	/**
	 *  删除标识 YES NO * @return String
	 */
	public String getIsDel() {
		return this.isDel;
	}

	/**
	 * 设置 删除标识 YES NO
	 */
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
	 *  阅读标识 YES NO * @return String
	 */
	public String getIsRead() {
		return this.isRead;
	}

	/**
	 * 设置 阅读标识 YES NO
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 *  回复标识 YES NO * @return String
	 */
	public String getIsReply() {
		return this.isReply;
	}

	/**
	 * 设置 回复标识 YES NO
	 */
	public void setIsReply(String isReply) {
		this.isReply = isReply;
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
		if (!(object instanceof InnerMail)) {
			return false;
		}
		InnerMail rhs = (InnerMail) object;
		return new EqualsBuilder().append(this.mailId, rhs.mailId).append(this.userId, rhs.userId).append(this.sender, rhs.sender).append(this.ccIds, rhs.ccIds).append(this.ccNames, rhs.ccNames).append(this.subject, rhs.subject).append(this.content, rhs.content).append(this.senderId, rhs.senderId).append(this.urge, rhs.urge).append(this.senderTime, rhs.senderTime).append(this.recNames, rhs.recNames).append(this.recIds, rhs.recIds).append(this.status, rhs.status).append(this.fileIds, rhs.fileIds).append(this.fileNames, rhs.fileNames).append(this.folderId, rhs.folderId).append(this.delFlag, rhs.delFlag).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.mailId).append(this.userId).append(this.sender).append(this.ccIds).append(this.ccNames).append(this.subject).append(this.content).append(this.senderId).append(this.urge).append(this.senderTime).append(this.recNames).append(this.recIds).append(this.status).append(this.fileIds).append(this.fileNames).append(this.folderId).append(this.delFlag).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("mailId", this.mailId).append("userId", this.userId).append("sender", this.sender).append("ccIds", this.ccIds).append("ccNames", this.ccNames).append("subject", this.subject).append("content", this.content).append("senderId", this.senderId).append("urge", this.urge).append("senderTime", this.senderTime).append("recNames", this.recNames).append("recIds", this.recIds).append("status", this.status).append("fileIds", this.fileIds).append("fileNames", this.fileNames).append("folderId", this.folderId).append("delFlag", this.delFlag).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
