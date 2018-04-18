 package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysAccount实体类定义
 * 登录账号
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_ACCOUNT")
@TableDefine(title = "登录账号")
public class SysAccount extends BaseTenantEntity{

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ACCOUNT_ID_")
	protected String accountId;
	/* 账号名称 */
	@FieldDefine(title = "账号名称", defaultCol = MBoolean.YES)
	@Column(name = "NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String name;
	/* 密码 */
	@FieldDefine(title = "密码")
	@Column(name = "PWD_")
	@Size(max = 64)
	@NotEmpty
	protected String pwd;
	/* 加密算法 */
	@FieldDefine(title = "加密算法", defaultCol = MBoolean.YES)
	@Column(name = "ENC_TYPE_")
	@Size(max = 20)
	protected String encType;
	
	/* 加密算法 */
	@FieldDefine(title = "所属DOMAIN", defaultCol = MBoolean.YES)
	@Column(name = "DOMAIN_")
	@Size(max = 20)
	protected String domain;
	
	/* 用户名 */
	@FieldDefine(title = "用户名", defaultCol = MBoolean.YES)
	@Column(name = "FULLNAME_")
	@Size(max = 256)
	@NotEmpty
	protected String fullname;
	/* 绑定用户ID */
	@FieldDefine(title = "绑定用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	protected String userId;
	/* 备注 */
	@FieldDefine(title = "备注")
	@Column(name = "REMARK_")
	@Size(max = 200)
	protected String remark;
	/* 状态 */
	@FieldDefine(title = "状态", defaultCol = MBoolean.YES)
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;
	
	/**
	 * Default Empty Constructor for class SysAccount
	 */
	public SysAccount() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysAccount
	 */
	public SysAccount(String in_accountId) {
		this.setAccountId(in_accountId);
	}

	/**
	 * * @return String
	 */
	public String getAccountId() {
		return this.accountId;
	}

	/**
	 * 设置
	 */
	public void setAccountId(String aValue) {
		this.accountId = aValue;
	}

	/**
	 * 账号名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 账号名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 密码 * @return String
	 */
	public String getPwd() {
		return this.pwd;
	}

	/**
	 * 设置 密码
	 */
	public void setPwd(String aValue) {
		this.pwd = aValue;
	}

	/**
	 * 加密算法 * @return String
	 */
	public String getEncType() {
		return this.encType;
	}

	/**
	 * 设置 加密算法
	 */
	public void setEncType(String aValue) {
		this.encType = aValue;
	}

	/**
	 * 用户名 * @return String
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * 设置 用户名
	 */
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}

	/**
	 * 绑定用户ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 绑定用户ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 备注 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置 备注
	 */
	public void setRemark(String aValue) {
		this.remark = aValue;
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

	@Override
	public String getIdentifyLabel() {
		return this.accountId;
	}

	@Override
	public Serializable getPkId() {
		return this.accountId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.accountId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysAccount)) {
			return false;
		}
		SysAccount rhs = (SysAccount) object;
		return new EqualsBuilder().append(this.accountId, rhs.accountId)
				
				.append(this.tenantId, rhs.tenantId)
				.isEquals();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.accountId).append(this.name).append(this.pwd)
				.append(this.encType).append(this.createTime)
				.append(this.fullname).append(this.userId)
				.append(this.tenantId).append(this.remark).append(this.status)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("accountId", this.accountId)
				.append("name", this.name).append("pwd", this.pwd)
				.append("encType", this.encType)
				.append("createTime", this.createTime)
				.append("fullname", this.fullname)
				.append("userId", this.userId)
				.append("tenantId", this.tenantId)
				.append("remark", this.remark).append("status", this.status)
				.append("createBy", this.createBy)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}


}
