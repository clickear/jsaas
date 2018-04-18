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
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：Subsystem实体类定义
 * 子系统
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_SUBSYS")
@TableDefine(title = "子系统")
public class Subsystem extends BaseTenantEntity implements Comparable<Subsystem>{
	/**
	 * 基础平台
	 */
	public final static String SYS_BASE = "SYS_BASE";
	/**
	 * 用户子系统
	 */
	public final static String SYS_ORG = "SYS_ORG";
	@FieldDefine(title="系统ID",group="基本信息")
	@Id
	@Column(name = "SYS_ID_")
	protected String sysId;
	/* 系统名称 */
	@FieldDefine(title="系统名称",group="基本信息")
	@Column(name = "NAME_")
	@Size(max = 80)
	@NotEmpty
	protected String name;
	/* 系统Key */
	@FieldDefine(title="系统Key",group="基本信息")
	@Column(name = "KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	/* 系统Logo */
	@FieldDefine(title="系统Logo",group="基本信息")
	@Column(name = "LOGO_")
	@Size(max = 120)
	protected String logo;
	/* 是否缺省 */
	@FieldDefine(title="是否缺省",group="基本信息")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 12)
	@NotEmpty
	protected String isDefault;
	/* 首页地址 */
	@FieldDefine(title="首页地址",group="基本信息")
	@Column(name = "HOME_URL_")
	@Size(max = 120)
	protected String homeUrl;
	
	/* 首页地址 */
	@FieldDefine(title="图标样式",group="基本信息")
	@Column(name = "ICON_CLS_")
	@Size(max = 50)
	protected String iconCls;
	
	@FieldDefine(title="序号",group="基本信息")
	@Column(name="SN_")
	protected Integer sn;
	
	/* 状态 */
	@FieldDefine(title="状态",group="基本信息")
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;
	/* 描述 */
	@FieldDefine(title="描述",group="基本信息")
	@Column(name = "DESCP_")
	@Size(max = 256)
	protected String descp;

	@FieldDefine(title="是否允许SAAS",group="基本信息")
	@Column(name="IS_SAAS_")
	protected String isSaas;
	
	@FieldDefine(title="机构类型编码",group="基本信息")
	@Column(name="INST_TYPE_")
	protected String instType;
	
	/**
	 * Default Empty Constructor for class Subsystem
	 */
	public Subsystem() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Subsystem
	 */
	public Subsystem(String in_sysId) {
		this.setSysId(in_sysId);
	}

	/**
	 * * @return String
	 */
	public String getSysId() {
		return this.sysId;
	}

	/**
	 * 设置
	 */
	public void setSysId(String aValue) {
		this.sysId = aValue;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	/**
	 * 系统名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 系统名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 系统Key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 系统Key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 系统Logo * @return String
	 */
	public String getLogo() {
		return this.logo;
	}

	/**
	 * 设置 系统Logo
	 */
	public void setLogo(String aValue) {
		this.logo = aValue;
	}

	/**
	 * 是否缺省 * @return String
	 */
	public String getIsDefault() {
		return this.isDefault;
	}

	/**
	 * 设置 是否缺省
	 */
	public void setIsDefault(String aValue) {
		this.isDefault = aValue;
	}

	/**
	 * 首页地址 * @return String
	 */
	public String getHomeUrl() {
		return this.homeUrl;
	}

	/**
	 * 设置 首页地址
	 */
	public void setHomeUrl(String aValue) {
		this.homeUrl = aValue;
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
	 * 描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * 设置 描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.sysId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.sysId = (String) pkId;
	}

	public Integer getSn() {
		if(sn==null) return 0;
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getIsSaas() {
		return isSaas;
	}

	public void setIsSaas(String isSaas) {
		this.isSaas = isSaas;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Subsystem)) {
			return false;
		}
		Subsystem rhs = (Subsystem) object;
		return new EqualsBuilder().append(this.sysId, rhs.sysId)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.logo, rhs.logo)
				.append(this.isDefault, rhs.isDefault)
				.append(this.homeUrl, rhs.homeUrl)
				.append(this.status, rhs.status).append(this.descp, rhs.descp)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sysId)
				.append(this.name).append(this.key).append(this.logo)
				.append(this.isDefault).append(this.homeUrl)
				.append(this.status).append(this.descp)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sysId", this.sysId)
				.append("name", this.name).append("key", this.key)
				.append("logo", this.logo).append("isDefault", this.isDefault)
				.append("homeUrl", this.homeUrl).append("status", this.status)
				.append("descp", this.descp)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

	@Override
	public int compareTo(Subsystem o) {
		return this.getSn()-o.getSn();
	}

}
