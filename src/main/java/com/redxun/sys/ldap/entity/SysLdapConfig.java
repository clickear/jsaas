package com.redxun.sys.ldap.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * 描述：SysLdapConfig实体类定义
 * TODO: add class/table comments
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "SYS_LDAP_CONFIG")
@TableDefine(title = "TODO: add class/table comments")
public class SysLdapConfig extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SYS_LDAP_CONFIG_ID_")
	protected String sysLdapConfigId;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 64)
	protected String status;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_CN_")
	@Size(max = 64)
	protected String statusCn;
	/* 基本DN */
	@FieldDefine(title = "基本DN")
	@Column(name = "DN_BASE_")
	@Size(max = 1024)
	protected String dnBase;
	/* 基准DN */
	@FieldDefine(title = "基准DN")
	@Column(name = "DN_DATUM_")
	@Size(max = 1024)
	protected String dnDatum;
	/* 地址 */
	@FieldDefine(title = "地址")
	@Column(name = "URL_")
	@Size(max = 1024)
	protected String url;
	/* 账号名称 */
	@FieldDefine(title = "账号名称")
	@Column(name = "ACCOUNT_")
	@Size(max = 64)
	protected String account;
	/* 密码 */
	@FieldDefine(title = "密码")
	@Column(name = "PASSWORD_")
	@Size(max = 64)
	protected String password;
	/* 部门过滤器 */
	@FieldDefine(title = "部门过滤器")
	@Column(name = "DEPT_FILTER_")
	@Size(max = 1024)
	protected String deptFilter;
	/* 用户过滤器 */
	@FieldDefine(title = "用户过滤器")
	@Column(name = "USER_FILTER_")
	@Size(max = 1024)
	protected String userFilter;
	/* 用户编号属性 */
	@FieldDefine(title = "用户编号属性")
	@Column(name = "ATT_USER_NO_")
	@Size(max = 64)
	protected String attUserNo;
	/* 用户账户属性 */
	@FieldDefine(title = "用户账户属性")
	@Column(name = "ATT_USER_ACC_")
	@Size(max = 64)
	protected String attUserAcc;
	/* 用户名称属性 */
	@FieldDefine(title = "用户名称属性")
	@Column(name = "ATT_USER_NAME_")
	@Size(max = 64)
	protected String attUserName;
	/* 用户密码属性 */
	@FieldDefine(title = "用户密码属性")
	@Column(name = "ATT_USER_PWD_")
	@Size(max = 1024)
	protected String attUserPwd;
	/* 用户电话属性 */
	@FieldDefine(title = "用户电话属性")
	@Column(name = "ATT_USER_TEL_")
	@Size(max = 64)
	protected String attUserTel;
	/* 用户邮件属性 */
	@FieldDefine(title = "用户邮件属性")
	@Column(name = "ATT_USER_MAIL_")
	@Size(max = 64)
	protected String attUserMail;
	/* 部门名称属性 */
	@FieldDefine(title = "部门名称属性")
	@Column(name = "ATT_DEPT_NAME_")
	@Size(max = 64)
	protected String attDeptName;

	/**
	 * Default Empty Constructor for class SysLdapConfig
	 */
	public SysLdapConfig() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysLdapConfig
	 */
	public SysLdapConfig(String in_sysLdapConfigId) {
		this.setSysLdapConfigId(in_sysLdapConfigId);
	}

	/**
	 * LDAP配置(主键) * @return String
	 */
	public String getSysLdapConfigId() {
		return this.sysLdapConfigId;
	}
	/**
	 * 设置 LDAP配置(主键)
	 */
	public void setSysLdapConfigId(String aValue) {
		this.sysLdapConfigId = aValue;
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
	 * 状态 * @return String
	 */
	public String getStatusCn() {
		return this.statusCn;
	}
	/**
	 * 设置 状态
	 */
	public void setStatusCn(String aValue) {
		this.statusCn = aValue;
	}
	/**
	 * 基本DN * @return String
	 */
	public String getDnBase() {
		return this.dnBase;
	}
	/**
	 * 设置 基本DN
	 */
	public void setDnBase(String aValue) {
		this.dnBase = aValue;
	}
	/**
	 * 基准DN * @return String
	 */
	public String getDnDatum() {
		return this.dnDatum;
	}
	/**
	 * 设置 基准DN
	 */
	public void setDnDatum(String aValue) {
		this.dnDatum = aValue;
	}
	/**
	 * 地址 * @return String
	 */
	public String getUrl() {
		return this.url;
	}
	/**
	 * 设置 地址
	 */
	public void setUrl(String aValue) {
		this.url = aValue;
	}
	/**
	 * 账号名称 * @return String
	 */
	public String getAccount() {
		return this.account;
	}
	/**
	 * 设置 账号名称
	 */
	public void setAccount(String aValue) {
		this.account = aValue;
	}
	/**
	 * 密码 * @return String
	 */
	public String getPassword() {
		return this.password;
	}
	/**
	 * 设置 密码
	 */
	public void setPassword(String aValue) {
		this.password = aValue;
	}
	/**
	 * 部门过滤器 * @return String
	 */
	public String getDeptFilter() {
		return this.deptFilter;
	}
	/**
	 * 设置 部门过滤器
	 */
	public void setDeptFilter(String aValue) {
		this.deptFilter = aValue;
	}
	/**
	 * 用户过滤器 * @return String
	 */
	public String getUserFilter() {
		return this.userFilter;
	}
	/**
	 * 设置 用户过滤器
	 */
	public void setUserFilter(String aValue) {
		this.userFilter = aValue;
	}
	/**
	 * 用户编号属性 * @return String
	 */
	public String getAttUserNo() {
		return this.attUserNo;
	}
	/**
	 * 设置 用户编号属性
	 */
	public void setAttUserNo(String aValue) {
		this.attUserNo = aValue;
	}
	/**
	 * 用户账户属性 * @return String
	 */
	public String getAttUserAcc() {
		return this.attUserAcc;
	}
	/**
	 * 设置 用户账户属性
	 */
	public void setAttUserAcc(String aValue) {
		this.attUserAcc = aValue;
	}
	/**
	 * 用户名称属性 * @return String
	 */
	public String getAttUserName() {
		return this.attUserName;
	}
	/**
	 * 设置 用户名称属性
	 */
	public void setAttUserName(String aValue) {
		this.attUserName = aValue;
	}
	/**
	 * 用户密码属性 * @return String
	 */
	public String getAttUserPwd() {
		return this.attUserPwd;
	}
	/**
	 * 设置 用户密码属性
	 */
	public void setAttUserPwd(String aValue) {
		this.attUserPwd = aValue;
	}
	/**
	 * 用户电话属性 * @return String
	 */
	public String getAttUserTel() {
		return this.attUserTel;
	}
	/**
	 * 设置 用户电话属性
	 */
	public void setAttUserTel(String aValue) {
		this.attUserTel = aValue;
	}
	/**
	 * 用户邮件属性 * @return String
	 */
	public String getAttUserMail() {
		return this.attUserMail;
	}
	/**
	 * 设置 用户邮件属性
	 */
	public void setAttUserMail(String aValue) {
		this.attUserMail = aValue;
	}
	/**
	 * 部门名称属性 * @return String
	 */
	public String getAttDeptName() {
		return this.attDeptName;
	}
	/**
	 * 设置 部门名称属性
	 */
	public void setAttDeptName(String aValue) {
		this.attDeptName = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.sysLdapConfigId;
	}

	@Override
	public Serializable getPkId() {
		return this.sysLdapConfigId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.sysLdapConfigId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysLdapConfig)) {
			return false;
		}
		SysLdapConfig rhs = (SysLdapConfig) object;
		return new EqualsBuilder().append(this.sysLdapConfigId, rhs.sysLdapConfigId).append(this.status, rhs.status)
				.append(this.statusCn, rhs.statusCn).append(this.dnBase, rhs.dnBase).append(this.dnDatum, rhs.dnDatum)
				.append(this.url, rhs.url).append(this.account, rhs.account).append(this.password, rhs.password)
				.append(this.deptFilter, rhs.deptFilter).append(this.userFilter, rhs.userFilter)
				.append(this.attUserNo, rhs.attUserNo).append(this.attUserAcc, rhs.attUserAcc)
				.append(this.attUserName, rhs.attUserName).append(this.attUserPwd, rhs.attUserPwd)
				.append(this.attUserTel, rhs.attUserTel).append(this.attUserMail, rhs.attUserMail)
				.append(this.attDeptName, rhs.attDeptName).append(this.tenantId, rhs.tenantId)
				.append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sysLdapConfigId).append(this.status)
				.append(this.statusCn).append(this.dnBase).append(this.dnDatum).append(this.url).append(this.account)
				.append(this.password).append(this.deptFilter).append(this.userFilter).append(this.attUserNo)
				.append(this.attUserAcc).append(this.attUserName).append(this.attUserPwd).append(this.attUserTel)
				.append(this.attUserMail).append(this.attDeptName).append(this.tenantId).append(this.updateTime)
				.append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sysLdapConfigId", this.sysLdapConfigId).append("status", this.status)
				.append("statusCn", this.statusCn).append("dnBase", this.dnBase).append("dnDatum", this.dnDatum)
				.append("url", this.url).append("account", this.account).append("password", this.password)
				.append("deptFilter", this.deptFilter).append("userFilter", this.userFilter)
				.append("attUserNo", this.attUserNo).append("attUserAcc", this.attUserAcc)
				.append("attUserName", this.attUserName).append("attUserPwd", this.attUserPwd)
				.append("attUserTel", this.attUserTel).append("attUserMail", this.attUserMail)
				.append("attDeptName", this.attDeptName).append("tenantId", this.tenantId)
				.append("updateTime", this.updateTime).append("updateBy", this.updateBy)
				.append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
