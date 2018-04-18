package com.redxun.sys.core.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysSearch实体类定义
 * 高级搜索
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
//@XmlRootElement
@Table(name = "SYS_SEARCH")
@TableDefine(title = "高级搜索")
public class SysSearch extends BaseTenantEntity {

	@FieldDefine(title = "搜索ID", group = "基本信息")
	@Id
	@Column(name = "SEARCH_ID_")
	protected String searchId;
	/* 搜索名称 */
	@FieldDefine(title = "搜索名称", group = "基本信息")
	@Column(name = "NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String name;
	/* 实体名称 */
	@FieldDefine(title = "实体名称", group = "基本信息")
	@Column(name = "ENTITY_NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String entityName;
	/* 是否启用 */
	@FieldDefine(title = "是否启用", group = "基本信息")
	@Column(name = "ENABLED_")
	@Size(max = 8)
	@NotEmpty
	protected String enabled;
        
        @FieldDefine(title = "是否缺省", group = "基本信息")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 8)
	@NotEmpty
        protected String isDefault;
        
	@FieldDefine(title = "搜索条件项", group = "基本信息")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sysSearch", fetch = FetchType.LAZY)
	protected java.util.Set<SysSearchItem> sysSearchItems = new java.util.HashSet<SysSearchItem>();

	/**
	 * Default Empty Constructor for class SysSearch
	 */
	public SysSearch() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysSearch
	 */
	public SysSearch(String in_searchId) {
		this.setSearchId(in_searchId);
	}
	@JsonBackReference
	public java.util.Set<SysSearchItem> getSysSearchItems() {
		return sysSearchItems;
	}
	@JsonBackReference
	public void setSysSearchItems(java.util.Set<SysSearchItem> in_sysSearchItems) {
		this.sysSearchItems = in_sysSearchItems;
	}

	/**
	 * * @return String
	 */
	public String getSearchId() {
		return this.searchId;
	}

	/**
	 * 设置
	 */
	public void setSearchId(String aValue) {
		this.searchId = aValue;
	}

	/**
	 * 搜索名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 搜索名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 实体名称 * @return String
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * 设置 实体名称
	 */
	public void setEntityName(String aValue) {
		this.entityName = aValue;
	}

	/**
	 * 是否启用 * @return String
	 */
	public String getEnabled() {
		return this.enabled;
	}

	/**
	 * 设置 是否启用
	 */
	public void setEnabled(String aValue) {
		this.enabled = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.searchId;
	}

	@Override
	public Serializable getPkId() {
		return this.searchId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.searchId = (String) pkId;
	}

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysSearch)) {
			return false;
		}
		SysSearch rhs = (SysSearch) object;
		return new EqualsBuilder().append(this.searchId, rhs.searchId)
				.append(this.name, rhs.name)
				.append(this.entityName, rhs.entityName)
				.append(this.enabled, rhs.enabled)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.searchId)
				.append(this.name).append(this.entityName).append(this.enabled)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("searchId", this.searchId)
				.append("name", this.name)
				.append("entityName", this.entityName)
				.append("enabled", this.enabled)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime)
				.append("createOrgId").toString();
	}

}
