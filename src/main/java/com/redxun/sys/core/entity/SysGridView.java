package com.redxun.sys.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.entity.BaseEntity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：SysGridView实体类定义
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_GRID_VIEW")
public class SysGridView extends BaseEntity {

	@Id
	@Column(name = "GRID_VIEW_ID_")
	protected String gridViewId;
	/* 名称 */
	@Column(name = "NAME_")
	@Size(max = 60)
	@NotEmpty
	protected String name;
	/* 是否系统默认 */
	@Column(name = "IS_SYSTEM_")
	@Size(max = 8)
	protected String isSystem;
	
	/* 是否系统默认 */
	@Column(name = "IS_DEFAULT_")
	@Size(max = 8)
	protected String isDefault;
	
	/* 是否在表格中编辑 */
	@Column(name = "ALLOW_EDIT_")
	@Size(max = 8)
	protected String allowEdit;
	/* 点击行动作 */
	@Column(name = "CLICK_ROW_ACTION_")
	@Size(max = 120)
	protected String clickRowAction;
	/* 默认排序 */
	@Column(name = "DEF_SORT_FIELD_")
	@Size(max = 50)
	protected String defSortField;
	/* 序号 */
	@Column(name = "SN_")
	protected Integer sn;
	/* 备注 */
	@Column(name = "REMARK_")
	@Size(max = 65535)
	protected String remark;
	@ManyToOne
	@JoinColumn(name = "MODULE_ID_")
	protected com.redxun.sys.core.entity.SysModule sysModule;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sysGridView", fetch = FetchType.LAZY)
	protected java.util.Set<SysGridField> sysGridFields = new java.util.HashSet<SysGridField>();
	
	/**
	 * Default Empty Constructor for class SysGridView
	 */
	public SysGridView() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysGridView
	 */
	public SysGridView(String in_gridViewId) {
		this.setGridViewId(in_gridViewId);
	}
	@JsonBackReference
	public com.redxun.sys.core.entity.SysModule getSysModule() {
		return sysModule;
	}
	@JsonBackReference
	public void setSysModule(com.redxun.sys.core.entity.SysModule in_sysModule) {
		this.sysModule = in_sysModule;
	}
	@JsonBackReference
	public java.util.Set<SysGridField> getSysGridFields() {
		return sysGridFields;
	}
	@JsonBackReference
	public void setSysGridFields(java.util.Set<SysGridField> in_sysGridFields) {
		this.sysGridFields = in_sysGridFields;
	}
	
	/**
	 * * @return String
	 */
	public String getGridViewId() {
		return this.gridViewId;
	}

	/**
	 * 设置
	 */
	public void setGridViewId(String aValue) {
		this.gridViewId = aValue;
	}

	/**
	 * 模块ID * @return String
	 */
	public String getModuleId() {
		return this.getSysModule() == null ? null : this.getSysModule()
				.getModuleId();
	}

	/**
	 * 设置 模块ID
	 */
	public void setModuleId(String aValue) {
		if (aValue == null) {
			sysModule = null;
		} else if (sysModule == null) {
			sysModule = new com.redxun.sys.core.entity.SysModule(aValue);
			// sysModule.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysModule.setModuleId(aValue);
		}
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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
	 * 是否系统默认 * @return String
	 */
	public String getIsSystem() {
		return this.isSystem;
	}

	/**
	 * 设置 是否系统默认
	 */
	public void setIsSystem(String aValue) {
		this.isSystem = aValue;
	}

	/**
	 * 是否在表格中编辑 * @return String
	 */
	public String getAllowEdit() {
		return this.allowEdit;
	}

	/**
	 * 设置 是否在表格中编辑
	 */
	public void setAllowEdit(String aValue) {
		this.allowEdit = aValue;
	}

	/**
	 * 点击行动作 * @return String
	 */
	public String getClickRowAction() {
		return this.clickRowAction;
	}

	/**
	 * 设置 点击行动作
	 */
	public void setClickRowAction(String aValue) {
		this.clickRowAction = aValue;
	}

	/**
	 * 默认排序 * @return String
	 */
	public String getDefSortField() {
		return this.defSortField;
	}

	/**
	 * 设置 默认排序
	 */
	public void setDefSortField(String aValue) {
		this.defSortField = aValue;
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

	@Override
	public String getIdentifyLabel() {
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.gridViewId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.gridViewId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysGridView)) {
			return false;
		}
		SysGridView rhs = (SysGridView) object;
		return new EqualsBuilder().append(this.gridViewId, rhs.gridViewId)
				.append(this.name, rhs.name)
				.append(this.isSystem, rhs.isSystem)
				.append(this.allowEdit, rhs.allowEdit)
				.append(this.clickRowAction, rhs.clickRowAction)
				.append(this.defSortField, rhs.defSortField)
				.append(this.sn, rhs.sn).append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.gridViewId).append(this.name)
				.append(this.isSystem).append(this.allowEdit)
				.append(this.clickRowAction).append(this.defSortField)
				.append(this.sn).append(this.remark).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("gridViewId", this.gridViewId)
				.append("name", this.name).append("isSystem", this.isSystem)
				.append("allowEdit", this.allowEdit)
				.append("clickRowAction", this.clickRowAction)
				.append("defSortField", this.defSortField)
				.append("sn", this.sn).append("remark", this.remark).toString();
	}

}
