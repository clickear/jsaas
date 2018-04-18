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
 * 描述：SysLdapOu实体类定义
 * SYS_LDAP_OU【LDAP组织单元】
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "SYS_LDAP_OU")
@TableDefine(title = "SYS_LDAP_OU【LDAP组织单元】")
public class SysLdapOu extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SYS_LDAP_OU_ID_")
	protected String sysLdapOuId;
	/* 序号 */
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	protected Integer sn;
	/* 层次 */
	@FieldDefine(title = "层次")
	@Column(name = "DEPTH_")
	protected Integer depth;
	/* 路径 */
	@FieldDefine(title = "路径")
	@Column(name = "PATH_")
	@Size(max = 1024)
	protected String path;
	/* 父目录 */
	@FieldDefine(title = "父目录")
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 64)
	protected String status;
	/* 组织单元 */
	@FieldDefine(title = "组织单元")
	@Column(name = "OU_")
	@Size(max = 512)
	protected String ou;
	/* 组织单元名称 */
	@FieldDefine(title = "组织单元名称")
	@Column(name = "NAME_")
	@Size(max = 512)
	protected String name;
	/* 区分名 */
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
	@FieldDefine(title = "USN_CHANGED")
	@Column(name = "USN_CHANGED_")
	@Size(max = 64)
	protected String usnChanged;
	/*
	 * LDAP创建时间
	 */
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
	@FieldDefine(title = "系统用户组")
	@Column(name = "GROUP_ID_")
	protected String groupId;

	/**
	 * Default Empty Constructor for class SysLdapOu
	 */
	public SysLdapOu() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysLdapOu
	 */
	public SysLdapOu(String in_sysLdapOuId) {
		this.setSysLdapOuId(in_sysLdapOuId);
	}

	/**
	 * 组织单元（主键） * @return String
	 */
	public String getSysLdapOuId() {
		return this.sysLdapOuId;
	}
	/**
	 * 设置 组织单元（主键）
	 */
	public void setSysLdapOuId(String aValue) {
		this.sysLdapOuId = aValue;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * 序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}
	/**
	 * 设置 序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}
	/**
	 * 层次 * @return Integer
	 */
	public Integer getDepth() {
		return this.depth;
	}
	/**
	 * 设置 层次
	 */
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}
	/**
	 * 路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}
	/**
	 * 设置 路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}
	/**
	 * 父目录 * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}
	/**
	 * 设置 父目录
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
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
	 * 组织单元 * @return String
	 */
	public String getOu() {
		return this.ou;
	}
	/**
	 * 设置 组织单元
	 */
	public void setOu(String aValue) {
		this.ou = aValue;
	}
	/**
	 * 组织单元名称 * @return String
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 设置 组织单元名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}
	/**
	 * 区分名 * @return String
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
	 * LDAP创建时间
	 * 
	 * @return String
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

	@Override
	public String getIdentifyLabel() {
		return this.sysLdapOuId;
	}

	@Override
	public Serializable getPkId() {
		return this.sysLdapOuId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.sysLdapOuId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysLdapOu)) {
			return false;
		}
		SysLdapOu rhs = (SysLdapOu) object;
		return new EqualsBuilder().append(this.sysLdapOuId, rhs.sysLdapOuId).append(this.sn, rhs.sn)
				.append(this.depth, rhs.depth).append(this.path, rhs.path).append(this.parentId, rhs.parentId)
				.append(this.status, rhs.status).append(this.ou, rhs.ou).append(this.name, rhs.name)
				.append(this.dn, rhs.dn).append(this.oc, rhs.oc).append(this.usnCreated, rhs.usnCreated)
				.append(this.usnChanged, rhs.usnChanged).append(this.whenCreated, rhs.whenCreated)
				.append(this.whenChanged, rhs.whenChanged).append(this.tenantId, rhs.tenantId)
				.append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sysLdapOuId).append(this.sn).append(this.depth)
				.append(this.path).append(this.parentId).append(this.status).append(this.ou).append(this.name)
				.append(this.dn).append(this.oc).append(this.usnCreated).append(this.usnChanged)
				.append(this.whenCreated).append(this.whenChanged).append(this.tenantId).append(this.updateTime)
				.append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sysLdapOuId", this.sysLdapOuId).append("sn", this.sn)
				.append("depth", this.depth).append("path", this.path).append("parentId", this.parentId)
				.append("status", this.status).append("ou", this.ou).append("name", this.name).append("dn", this.dn)
				.append("oc", this.oc).append("usnCreated", this.usnCreated).append("usnChanged", this.usnChanged)
				.append("whenCreated", this.whenCreated).append("whenChanged", this.whenChanged)
				.append("tenantId", this.tenantId).append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy).append("createTime", this.createTime)
				.append("createBy", this.createBy).toString();
	}

}
