package com.redxun.sys.ldap.entity;
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
 * 描述：SysLdapCn实体类定义
 * SYS_LDAP_CN【LADP用户】
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "SYS_LDAP_CN")
@TableDefine(title = "SYS_LDAP_CN【LADP用户】")
public class SysLdapCn extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SYS_LDAP_USER_ID_")
	protected String sysLdapUserId;
	/* 账户 */
	@FieldDefine(title = "账户")
	@Column(name = "USER_ACCOUNT_")
	@Size(max = 64)
	protected String userAccount;
	/* 用户编号 */
	@FieldDefine(title = "用户编号")
	@Column(name = "USER_CODE_")
	@Size(max = 64)
	protected String userCode;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 64)
	protected String name;
	/* 电话 */
	@FieldDefine(title = "电话")
	@Column(name = "TEL_")
	@Size(max = 64)
	protected String tel;
	/* 邮件 */
	@FieldDefine(title = "邮件")
	@Column(name = "MAIL_")
	@Size(max = 512)
	protected String mail;
	/*
	 * USN_CREATED
	 */
	@FieldDefine(title = "USN_CREATED")
	@Column(name = "USN_CREATED_")
	@Size(max = 64)
	protected String usnCreated;
	/*
	 * USN_CHANGED
	 */
	@FieldDefine(title = "USN_CHANGED ")
	@Column(name = "USN_CHANGED_")
	@Size(max = 64)
	protected String usnChanged;
	/* LDAP创建时间 */
	@FieldDefine(title = "LDAP创建时间")
	@Column(name = "WHEN_CREATED_")
	@Size(max = 64)
	protected String whenCreated;
	/*
	 * LDAP更新时间
	 */
	@FieldDefine(title = "LDAP更新时间")
	@Column(name = "WHEN_CHANGED_")
	@Size(max = 64)
	protected String whenChanged;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 64)
	protected String status;
	/* 用户主要名称 */
	@FieldDefine(title = "用户主要名称")
	@Column(name = "USER_PRINCIPAL_NAME_")
	@Size(max = 512)
	protected String userPrincipalName;
	/*
	 * 区分名称
	 */
	@FieldDefine(title = "区分名")
	@Column(name = "DN_")
	@Size(max = 512)
	protected String dn;
	/*
	 * 对象类型
	 */
	@FieldDefine(title = "对象类型")
	@Column(name = "OC_")
	@Size(max = 512)
	protected String oc;
	@FieldDefine(title = "用户信息表")
	@Column(name = "USER_ID_")
	protected String userId;
	@FieldDefine(title = "SYS_LDAP_OU【LDAP组织单元】")
	@Column(name = "SYS_LDAP_OU_ID_")
	protected String sysLdapOuId;

	/**
	 * Default Empty Constructor for class SysLdapCn
	 */
	public SysLdapCn() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysLdapCn
	 */
	public SysLdapCn(String in_sysLdapUserId) {
		this.setSysLdapUserId(in_sysLdapUserId);
	}

	/**
	 * LDAP用户（主键） * @return String
	 */
	public String getSysLdapUserId() {
		return this.sysLdapUserId;
	}
	/**
	 * 设置 LDAP用户（主键）
	 */
	public void setSysLdapUserId(String aValue) {
		this.sysLdapUserId = aValue;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSysLdapOuId() {
		return sysLdapOuId;
	}

	public void setSysLdapOuId(String sysLdapOuId) {
		this.sysLdapOuId = sysLdapOuId;
	}

	/**
	 * 账户 * @return String
	 */
	public String getUserAccount() {
		return this.userAccount;
	}
	/**
	 * 设置 账户
	 */
	public void setUserAccount(String aValue) {
		this.userAccount = aValue;
	}
	/**
	 * 用户编号 * @return String
	 */
	public String getUserCode() {
		return this.userCode;
	}
	/**
	 * 设置 用户编号
	 */
	public void setUserCode(String aValue) {
		this.userCode = aValue;
	}
	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}
	/**
	 * 电话 * @return String
	 */
	public String getTel() {
		return this.tel;
	}
	/**
	 * 设置 电话
	 */
	public void setTel(String aValue) {
		this.tel = aValue;
	}
	/**
	 * 邮件 * @return String
	 */
	public String getMail() {
		return this.mail;
	}
	/**
	 * 设置 邮件
	 */
	public void setMail(String aValue) {
		this.mail = aValue;
	}
	/**
	 * USN_CREATED
	 * 
	 * @return String
	 */
	public String getUsnCreated() {
		return this.usnCreated;
	}
	/**
	 * 设置 USN_CREATED
	 */
	public void setUsnCreated(String aValue) {
		this.usnCreated = aValue;
	}
	/**
	 * USN_CHANGED
	 * 
	 * @return String
	 */
	public String getUsnChanged() {
		return this.usnChanged;
	}
	/**
	 * 设置 USN_CHANGED
	 */
	public void setUsnChanged(String aValue) {
		this.usnChanged = aValue;
	}
	/**
	 * LDAP创建时间 * @return String
	 */
	public String getWhenCreated() {
		return this.whenCreated;
	}
	/**
	 * 设置 LDAP创建时间
	 */
	public void setWhenCreated(String aValue) {
		this.whenCreated = aValue;
	}
	/**
	 * LDAP更新时间
	 * 
	 * @return String
	 */
	public String getWhenChanged() {
		return this.whenChanged;
	}
	/**
	 * 设置 LDAP更新时间
	 */
	public void setWhenChanged(String aValue) {
		this.whenChanged = aValue;
	}
	/**
	 * 状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * 设置 状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}
	/**
	 * 用户主要名称 * @return String
	 */
	public String getUserPrincipalName() {
		return this.userPrincipalName;
	}
	/**
	 * 设置 用户主要名称
	 */
	public void setUserPrincipalName(String aValue) {
		this.userPrincipalName = aValue;
	}
	/**
	 * 区分名
	 * 
	 * @return String
	 */
	public String getDn() {
		return this.dn;
	}
	/**
	 * 设置 区分名
	 */
	public void setDn(String aValue) {
		this.dn = aValue;
	}
	/**
	 * 对象类型
	 * 
	 * @return String
	 */
	public String getOc() {
		return this.oc;
	}
	/**
	 * 设置 对象类型
	 */
	public void setOc(String aValue) {
		this.oc = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.sysLdapUserId;
	}

	@Override
	public Serializable getPkId() {
		return this.sysLdapUserId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.sysLdapUserId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysLdapCn)) {
			return false;
		}
		SysLdapCn rhs = (SysLdapCn) object;
		return new EqualsBuilder().append(this.sysLdapUserId, rhs.sysLdapUserId)
				.append(this.userAccount, rhs.userAccount).append(this.userCode, rhs.userCode)
				.append(this.name, rhs.name).append(this.tel, rhs.tel).append(this.mail, rhs.mail)
				.append(this.usnCreated, rhs.usnCreated).append(this.usnChanged, rhs.usnChanged)
				.append(this.whenCreated, rhs.whenCreated).append(this.whenChanged, rhs.whenChanged)
				.append(this.status, rhs.status).append(this.userPrincipalName, rhs.userPrincipalName)
				.append(this.dn, rhs.dn).append(this.oc, rhs.oc).append(this.tenantId, rhs.tenantId)
				.append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sysLdapUserId).append(this.userAccount)
				.append(this.userCode).append(this.name).append(this.tel).append(this.mail).append(this.usnCreated)
				.append(this.usnChanged).append(this.whenCreated).append(this.whenChanged).append(this.status)
				.append(this.userPrincipalName).append(this.dn).append(this.oc).append(this.tenantId)
				.append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sysLdapUserId", this.sysLdapUserId)
				.append("userAccount", this.userAccount).append("userCode", this.userCode).append("name", this.name)
				.append("tel", this.tel).append("mail", this.mail).append("usnCreated", this.usnCreated)
				.append("usnChanged", this.usnChanged).append("whenCreated", this.whenCreated)
				.append("whenChanged", this.whenChanged).append("status", this.status)
				.append("userPrincipalName", this.userPrincipalName).append("dn", this.dn).append("oc", this.oc)
				.append("tenantId", this.tenantId).append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy).append("createTime", this.createTime)
				.append("createBy", this.createBy).toString();
	}

}
