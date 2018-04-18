package com.redxun.oa.personal.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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
 * 描述：联系人分组实体类定义
 * 通讯录分组
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_ADDR_GRP")
@TableDefine(title = "通讯录分组")
@JsonIgnoreProperties(value={"addrBooks"})
public class AddrGrp extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "GROUP_ID_")
	protected String groupId;
	/* 名字 */
	@FieldDefine(title = "名字")
	@Column(name = "NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String name;
	
	@Transient
	private String parentId="0";
	
	@Transient
	private String total;

	@FieldDefine(title = "通讯录分组下的联系人")
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="OA_ADDR_GPB",
	joinColumns=
	@JoinColumn(name="GROUP_ID_", referencedColumnName="GROUP_ID_"),
	inverseJoinColumns=
	@JoinColumn(name="ADDR_ID_", referencedColumnName="ADDR_ID_"))
	protected java.util.Set<AddrBook> addrBooks = new java.util.HashSet<AddrBook>();

	/**
	 * Default Empty Constructor for class AddrGrp
	 */
	public AddrGrp() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class AddrGrp
	 */
	public AddrGrp(String in_groupId) {
		this.setGroupId(in_groupId);
	}

	public java.util.Set<AddrBook> getAddrBooks() {
		return addrBooks;
	}

	public void setAddrBooks(java.util.Set<AddrBook> in_AddrBooks) {
		this.addrBooks = in_AddrBooks;
	}

	/**
	 * 分组ID * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置 分组ID
	 */
	public void setGroupId(String aValue) {
		this.groupId = aValue;
	}

	/**
	 * 名字 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名字
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 父Id * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父Id
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
	}
	
	
	/**
	 * 总数 * @return String
	 */
	public String getTotal() {
		return this.total;
	}

	/**
	 * 设置 总数
	 */
	public void setTotal(String aValue) {
		this.total = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.groupId;
	}

	@Override
	public Serializable getPkId() {
		return this.groupId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.groupId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AddrGrp)) {
			return false;
		}
		AddrGrp rhs = (AddrGrp) object;
		return new EqualsBuilder().append(this.groupId, rhs.groupId).append(this.name, rhs.name).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.groupId).append(this.name).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("groupId", this.groupId).append("name", this.name).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
