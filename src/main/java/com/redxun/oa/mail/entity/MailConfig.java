package com.redxun.oa.mail.entity;

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
 * 描述：MailConfig实体类定义
 * 外部邮箱设置
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_MAIL_CONFIG")
@TableDefine(title = "外部邮箱设置")
@JsonIgnoreProperties(value={"mails","mailFolders"})
public class MailConfig extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CONFIG_ID_")
	protected String configId;
	/* 用户ID */
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/* 用户名称 */
	@Column(name = "USER_NAME_")
	@Size(max = 128)
	protected String userName;
	/* 帐号名称 */
	@FieldDefine(title = "帐号名称")
	@Column(name = "ACCOUNT_")
	@Size(max = 128)
	protected String account;
	/* 外部邮件地址 */
	@FieldDefine(title = "外部邮件地址")
	@Column(name = "MAIL_ACCOUNT_")
	@Size(max = 128)
	@NotEmpty
	protected String mailAccount;
	/* 外部邮件密码 */
	@Column(name = "MAIL_PWD_")
	@Size(max = 128)
	@NotEmpty
	protected String mailPwd;
	/* 协议类型 IMAP POP3 */
/*	@FieldDefine(title = "协议类型 IMAP POP3")*/
	@Column(name = "PROTOCOL_")
	@Size(max = 32)
	@NotEmpty
	protected String protocol;
	/* 邮件发送主机 */
/*	@FieldDefine(title = "邮件发送主机")*/
	@Column(name = "SMTP_HOST_")
	@Size(max = 128)
	@NotEmpty
	protected String smtpHost;
	/* 邮件发送端口 */
	@Column(name = "SMTP_PORT_")
	@Size(max = 64)
	@NotEmpty
	protected String smtpPort;
	/* 接收主机 */
	@Column(name = "RECP_HOST_")
	@Size(max = 128)
	@NotEmpty
	protected String recpHost;
	/* 接收端口 */
	@Column(name = "RECP_PORT_")
	@Size(max = 64)
	@NotEmpty
	protected String recpPort;
	/* 是否使用SSL连接*/
/*	@FieldDefine(title = "是否使用SSL连接")*/
	@Column(name = "SSL_")
	@Size(max = 12)
	@NotEmpty
	protected String ssl;
	/* 是否默认 YES NO */
	@Column(name = "IS_DEFAULT_")
	@Size(max = 20)
	@NotEmpty
	protected String isDefault;

	@FieldDefine(title = "外部邮件")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mailConfig", fetch = FetchType.LAZY)
	protected java.util.Set<OutMail> mails = new java.util.HashSet<OutMail>();
	@FieldDefine(title = "邮件文件夹")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mailConfig", fetch = FetchType.LAZY)
	protected java.util.Set<MailFolder> mailFolders = new java.util.HashSet<MailFolder>();

	/**
	 * Default Empty Constructor for class MailConfig
	 */
	public MailConfig() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class MailConfig
	 */
	public MailConfig(String in_configId) {
		this.setConfigId(in_configId);
	}

	public java.util.Set<OutMail> getMails() {
		return mails;
	}

	public void setMails(java.util.Set<OutMail> in_mails) {
		this.mails = in_mails;
	}

	public java.util.Set<MailFolder> getMailFolders() {
		return mailFolders;
	}

	public void setMailFolders(java.util.Set<MailFolder> in_mailFolders) {
		this.mailFolders = in_mailFolders;
	}

	/**
	 * 配置ID * @return String
	 */
	public String getConfigId() {
		return this.configId;
	}

	/**
	 * 设置 配置ID
	 */
	public void setConfigId(String aValue) {
		this.configId = aValue;
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
	 * 用户名称 * @return String
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 设置 用户名称
	 */
	public void setUserName(String aValue) {
		this.userName = aValue;
	}

	/**
	 * 帐号名称 * @return String
	 */
	public String getAccount() {
		return this.account;
	}

	/**
	 * 设置 帐号名称
	 */
	public void setAccount(String aValue) {
		this.account = aValue;
	}

	/**
	 * 外部邮件地址 * @return String
	 */
	public String getMailAccount() {
		return this.mailAccount;
	}

	/**
	 * 设置 外部邮件地址
	 */
	public void setMailAccount(String aValue) {
		this.mailAccount = aValue;
	}

	/**
	 * 外部邮件密码 * @return String
	 */
	public String getMailPwd() {
		return this.mailPwd;
	}

	/**
	 * 设置 外部邮件密码
	 */
	public void setMailPwd(String aValue) {
		this.mailPwd = aValue;
	}

	/**
	 * 协议类型 IMAP POP3 * @return String
	 */
	public String getProtocol() {
		return this.protocol;
	}

	/**
	 * 设置 协议类型 IMAP POP3
	 */
	public void setProtocol(String aValue) {
		this.protocol = aValue;
	}

	/**
	 * 邮件发送主机 * @return String
	 */
	public String getSmtpHost() {
		return this.smtpHost;
	}

	/**
	 * 设置 邮件发送主机
	 */
	public void setSmtpHost(String aValue) {
		this.smtpHost = aValue;
	}

	/**
	 * 邮件发送端口 * @return String
	 */
	public String getSmtpPort() {
		return this.smtpPort;
	}

	/**
	 * 设置 邮件发送端口
	 */
	public void setSmtpPort(String aValue) {
		this.smtpPort = aValue;
	}

	/**
	 * 接收主机 * @return String
	 */
	public String getRecpHost() {
		return this.recpHost;
	}

	/**
	 * 设置 接收主机
	 */
	public void setRecpHost(String aValue) {
		this.recpHost = aValue;
	}

	/**
	 * 接收端口 * @return String
	 */
	public String getRecpPort() {
		return this.recpPort;
	}

	/**
	 * 设置 接收端口
	 */
	public void setRecpPort(String aValue) {
		this.recpPort = aValue;
	}
	
	/**
	 * 是否使用SSL连接* @return String
	 */
	public String getSsl() {
		return this.ssl;
	}

	/**
	 * 设置是否使用SSL连接*
	 */
	public void setSsl(String aValue) {
		this.ssl = aValue;
	}

	/**
	 * 是否默认 YES NO * @return String
	 */
	public String getIsDefault() {
		return this.isDefault;
	}

	/**
	 * 设置 是否默认 YES NO
	 */
	public void setIsDefault(String aValue) {
		this.isDefault = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.configId;
	}

	@Override
	public Serializable getPkId() {
		return this.configId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.configId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof MailConfig)) {
			return false;
		}
		MailConfig rhs = (MailConfig) object;
		return new EqualsBuilder().append(this.configId, rhs.configId)
				.append(this.userId, rhs.userId)
				.append(this.userName, rhs.userName)
				.append(this.account, rhs.account)
				.append(this.mailAccount, rhs.mailAccount)
				.append(this.mailPwd, rhs.mailPwd)
				.append(this.protocol, rhs.protocol)
				.append(this.smtpHost, rhs.smtpHost)
				.append(this.smtpPort, rhs.smtpPort)
				.append(this.recpHost, rhs.recpHost)
				.append(this.recpPort, rhs.recpPort)
				.append(this.isDefault, rhs.isDefault)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.configId)
				.append(this.userId).append(this.userName).append(this.account)
				.append(this.mailAccount).append(this.mailPwd)
				.append(this.protocol).append(this.smtpHost)
				.append(this.smtpPort).append(this.recpHost)
				.append(this.recpPort).append(this.isDefault)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("configId", this.configId)
				.append("userId", this.userId)
				.append("userName", this.userName)
				.append("account", this.account)
				.append("mailAccount", this.mailAccount)
				.append("mailPwd", this.mailPwd)
				.append("protocol", this.protocol)
				.append("smtpHost", this.smtpHost)
				.append("smtpPort", this.smtpPort)
				.append("recpHost", this.recpHost)
				.append("recpPort", this.recpPort)
				.append("isDefault", this.isDefault)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
