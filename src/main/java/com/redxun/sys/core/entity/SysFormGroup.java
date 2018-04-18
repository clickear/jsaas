package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 * 描述：SysFormGroup实体类定义
 * 系统表单字段分组
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 版权：广州微通软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "SYS_FORM_GROUP")
@TableDefine(title = "系统表单字段分组")
public class SysFormGroup extends BaseEntity {

	@FieldDefine(title = "分组ID", group = "基本信息")
	@Id
	@Column(name = "GROUP_ID_")
	protected String groupId;
	/* 组标题 */
	@FieldDefine(title = "组标题", group = "基本信息")
	@Column(name = "TITLE_")
	@Size(max = 50)
	@NotEmpty
	protected String title;
	/* 序号 */
	@FieldDefine(title = "序号", group = "基本信息")
	@Column(name = "SN_")
	protected Integer sn;
	/* 显示模式 */
	@FieldDefine(title = "显示模式", group = "基本信息")
	@Column(name = "DISPLAY_MODE_")
	@Size(max = 50)
	protected String displayMode;
	/* 列数 */
	@FieldDefine(title = "列数", group = "基本信息")
	@Column(name = "COL_NUMS_")
	protected Integer colNums;
	/* 是否可收缩 */
	@FieldDefine(title = "是否可收缩", group = "基本信息")
	@Column(name = "COLLAPSIBLE_")
	@Size(max = 8)
	protected String collapsible;
	/* 默认收缩 */
	@FieldDefine(title = "默认收缩", group = "基本信息")
	@Column(name = "COLLAPSED_")
	@Size(max = 8)
	protected String collapsed;
	/* 子模块ID */
	@FieldDefine(title = "子模块ID", group = "基本信息")
	@Column(name = "SUB_MODEL_ID_")
	@Size(max = 64)
	protected String subModelId;
	/* 其他JSON配置 */
	@FieldDefine(title = "其他JSON配置", group = "基本信息")
	@Column(name = "JSON_CONFIG_")
	@Size(max = 65535)
	protected String jsonConfig;
	@FieldDefine(title = "所属表单方案", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "FORM_SCHEMA_ID_")
	protected com.redxun.sys.core.entity.SysFormSchema sysFormSchema;
	@FieldDefine(title = "表单字段列表", group = "基本信息")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sysFormGroup", fetch = FetchType.LAZY)
	@OrderBy("sn asc")
	protected java.util.Set<SysFormField> sysFormFields = new java.util.HashSet<SysFormField>();

	/**
	 * Default Empty Constructor for class SysFormGroup
	 */
	public SysFormGroup() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysFormGroup
	 */
	public SysFormGroup(String in_groupId) {
		this.setGroupId(in_groupId);
	}
	
	public com.redxun.sys.core.entity.SysFormSchema getSysFormSchema() {
		return sysFormSchema;
	}

	public void setSysFormSchema(
			com.redxun.sys.core.entity.SysFormSchema in_sysFormSchema) {
		this.sysFormSchema = in_sysFormSchema;
	}
	
	public java.util.Set<SysFormField> getSysFormFields() {
		return sysFormFields;
	}
	
	public void setSysFormFields(java.util.Set<SysFormField> in_sysFormFields) {
		this.sysFormFields = in_sysFormFields;
	}

	/**
	 * * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置
	 */
	public void setGroupId(String aValue) {
		this.groupId = aValue;
	}

	/**
	 * 表单方案ID * @return String
	 */
	public String getFormSchemaId() {
		return this.getSysFormSchema() == null ? null : this.getSysFormSchema()
				.getFormSchemaId();
	}

	/**
	 * 设置 表单方案ID
	 */
	public void setFormSchemaId(String aValue) {
		if (aValue == null) {
			sysFormSchema = null;
		} else if (sysFormSchema == null) {
			sysFormSchema = new com.redxun.sys.core.entity.SysFormSchema(aValue);
			// sysFormSchema.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysFormSchema.setFormSchemaId(aValue);
		}
	}

	/**
	 * 组标题 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置 组标题
	 */
	public void setTitle(String aValue) {
		this.title = aValue;
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
	 * 显示模式 * @return String
	 */
	public String getDisplayMode() {
		return this.displayMode;
	}

	/**
	 * 设置 显示模式
	 */
	public void setDisplayMode(String aValue) {
		this.displayMode = aValue;
	}

	/**
	 * 是否可收缩 * @return String
	 */
	public String getCollapsible() {
		return this.collapsible;
	}

	/**
	 * 设置 是否可收缩
	 */
	public void setCollapsible(String aValue) {
		this.collapsible = aValue;
	}

	/**
	 * 默认收缩 * @return String
	 */
	public String getCollapsed() {
		return this.collapsed;
	}

	/**
	 * 设置 默认收缩
	 */
	public void setCollapsed(String aValue) {
		this.collapsed = aValue;
	}

	/**
	 * 子模块ID * @return String
	 */
	public String getSubModelId() {
		return this.subModelId;
	}

	/**
	 * 设置 子模块ID
	 */
	public void setSubModelId(String aValue) {
		this.subModelId = aValue;
	}

	/**
	 * 其他JSON配置 * @return String
	 */
	public String getJsonConfig() {
		return this.jsonConfig;
	}

	/**
	 * 设置 其他JSON配置
	 */
	public void setJsonConfig(String aValue) {
		this.jsonConfig = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.title;
	}

	@Override
	public Serializable getPkId() {
		return this.groupId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.groupId = (String) pkId;
	}

	public Integer getColNums() {
		return colNums;
	}

	public void setColNums(Integer colNums) {
		this.colNums = colNums;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysFormGroup)) {
			return false;
		}
		SysFormGroup rhs = (SysFormGroup) object;
		return new EqualsBuilder().append(this.groupId, rhs.groupId)
				.append(this.title, rhs.title).append(this.sn, rhs.sn)
				.append(this.displayMode, rhs.displayMode)
				.append(this.collapsible, rhs.collapsible)
				.append(this.collapsed, rhs.collapsed)
				.append(this.subModelId, rhs.subModelId)
				.append(this.jsonConfig, rhs.jsonConfig).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.groupId)
				.append(this.title).append(this.sn).append(this.displayMode)
				.append(this.collapsible).append(this.collapsed)
				.append(this.subModelId).append(this.jsonConfig).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("groupId", this.groupId)
				.append("title", this.title).append("sn", this.sn)
				.append("displayMode", this.displayMode)
				.append("collapsible", this.collapsible)
				.append("collapsed", this.collapsed)
				.append("subModelId", this.subModelId)
				.append("jsonConfig", this.jsonConfig).toString();
	}

}
