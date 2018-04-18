package com.redxun.oa.info.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.junit.Ignore;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：InsPortal实体类定义
 * PORTAL门户定义
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_PORTAL")
@TableDefine(title = "PORTAL门户定义")
@JsonIgnoreProperties({"insPortCols"})
public class InsPortal extends BaseTenantEntity {

	public final static String TYPE_GLOBAL_COMPANY="GLOBAL-COMPANY";
	public final static String TYPE_COMPANY="COMPANY";
	public final static String TYPE_GLOBAL_PERSONAL="GLOBAL-PERSONAL";
	public final static String TYPE_PERSONAL="PERSONAL";
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "PORT_ID_")
	protected String portId;
	/* 门户名称 */
	@FieldDefine(title = "门户名称")
	@Column(name = "NAME_")
	@Size(max = 128)
	@NotEmpty
	protected String name;
	/* 门户KEY */
	@FieldDefine(title = "门户KEY")
	@Column(name = "KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	/* 门户列数 */
	@FieldDefine(title = "列数")
	@Column(name = "COL_NUMS_")
	protected String colNums;
	/* 门户栏目宽 */
	@FieldDefine(title = "栏目宽")
	@Column(name = "COL_WIDTHS_")
	@Size(max = 50)
	protected String colWidths;
	/* 是否为系统缺省 */
	@FieldDefine(title = "是否为系统缺省")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 20)
	@NotEmpty
	protected String isDefault;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESC_")
	@Size(max = 512)
	protected String desc;
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	protected String userId;
	
	@FieldDefine(title = "门户栏目配置")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "insPortal", fetch = FetchType.LAZY)
	protected java.util.Set<InsPortCol> insPortCols = new java.util.HashSet<InsPortCol>();

	/**
	 * Default Empty Constructor for class InsPortal
	 */
	public InsPortal() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InsPortal
	 */
	
	public InsPortal(String in_portId) {
		this.setPortId(in_portId);
	}

	public java.util.Set<InsPortCol> getInsPortCols() {
		return insPortCols;
	}

	public void setInsPortCols(java.util.Set<InsPortCol> in_insPortCols) {
		this.insPortCols = in_insPortCols;
	}

	/**
	 * * @return String
	 */
	public String getPortId() {
		return this.portId;
	}

	/**
	 * 设置
	 */
	public void setPortId(String aValue) {
		this.portId = aValue;
	}

	/**
	 * 门户名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 门户名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 门户KEY * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 门户KEY
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 是否为系统缺省 * @return String
	 */
	public String getIsDefault() {
		return this.isDefault;
	}

	/**
	 * 设置 是否为系统缺省
	 */
	public void setIsDefault(String aValue) {
		this.isDefault = aValue;
	}

	/**
	 * 描述 * @return String
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 设置 描述
	 */
	public void setDesc(String aValue) {
		this.desc = aValue;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getIdentifyLabel() {
		return this.portId;
	}

	@Override
	public Serializable getPkId() {
		return this.portId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.portId = (String) pkId;
	}
	
	public String getColNums() {
		return colNums;
	}

	public void setColNums(String colNums) {
		this.colNums = colNums;
	}

	public String getColWidths() {
		return colWidths;
	}

	public void setColWidths(String colWidth) {
		this.colWidths = colWidth;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsPortal)) {
			return false;
		}
		InsPortal rhs = (InsPortal) object;
		return new EqualsBuilder().append(this.portId, rhs.portId)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.isDefault, rhs.isDefault)
				.append(this.desc, rhs.desc)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.portId)
				.append(this.name).append(this.key).append(this.isDefault)
				.append(this.desc).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("portId", this.portId)
				.append("name", this.name).append("key", this.key)
				.append("isDefault", this.isDefault).append("desc", this.desc)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
