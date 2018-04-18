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
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysFormSchema实体类定义
 * 表单方案
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_FORM_SCHEMA")
@TableDefine(title = "表单方案")
public class SysFormSchema extends BaseTenantEntity {
	@FieldDefine(title = "表单方案ID", group = "基本信息")
	@Id
	@Column(name = "FORM_SCHEMA_ID_")
	protected String formSchemaId;
	/* 方案名称 */
	@FieldDefine(title = "方案名称", group = "基本信息")
	@Column(name = "SCHEMA_NAME_")
	@Size(max = 64)
	@NotEmpty
	protected String schemaName;
	/* 表单标题 */
	@FieldDefine(title = "表单标题", group = "基本信息")
	@Column(name = "TITLE_")
	@Size(max = 50)
	protected String title;
	/* 方案排序 */
	@FieldDefine(title = "方案排序", group = "基本信息")
	@Column(name = "SN_")
	protected Integer sn;
	/* 是否为系统默认 */
	@FieldDefine(title = "是否为系统默认", group = "基本信息")
	@Column(name = "IS_SYSTEM_")
	@Size(max = 8)
	@NotEmpty
	protected String isSystem;
	/* 方案Key */
	@FieldDefine(title = "方案Key", group = "基本信息")
	@Column(name = "SCHEMA_KEY_")
	@Size(max = 50)
	@NotEmpty
	protected String schemaKey;
	/* 窗口宽 */
	@FieldDefine(title = "窗口宽", group = "基本信息")
	@Column(name = "WIN_WIDTH_")
	protected Integer winWidth;
	/* 窗口高 */
	@FieldDefine(title = "窗口高", group = "基本信息")
	@Column(name = "WIN_HEIGHT_")
	protected Integer winHeight;
	/* 列数 */
	@FieldDefine(title = "列数", group = "基本信息")
	@Column(name = "COL_NUMS_")
	protected Integer colNums;
	/* 显示模式 */
	@FieldDefine(title = "显示模式", group = "基本信息")
	@Column(name = "DISPLAY_MODE_")
	@Size(max = 50)
	@NotEmpty
	protected String displayMode;
	/* 其他JSON配置 */
	@FieldDefine(title = "其他JSON配置", group = "基本信息")
	@Column(name = "JSON_CONFIG_")
	@Size(max = 65535)
	protected String jsonConfig;
	@FieldDefine(title = "所属系统模块", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "MODULE_ID_")
	protected com.redxun.sys.core.entity.SysModule sysModule;
	@FieldDefine(title = "表单分组类型", group = "基本信息")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sysFormSchema", fetch = FetchType.LAZY)
	@OrderBy("sn asc")
	protected java.util.Set<SysFormGroup> sysFormGroups = new java.util.HashSet<SysFormGroup>();

	/**
	 * Default Empty Constructor for class SysFormSchema
	 */
	public SysFormSchema() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysFormSchema
	 */
	public SysFormSchema(String in_formSchemaId) {
		this.setFormSchemaId(in_formSchemaId);
	}
	@JsonManagedReference
	public com.redxun.sys.core.entity.SysModule getSysModule() {
		return sysModule;
	}
	@JsonManagedReference
	public void setSysModule(com.redxun.sys.core.entity.SysModule in_sysModule) {
		this.sysModule = in_sysModule;
	}
	@JsonBackReference
	public java.util.Set<SysFormGroup> getSysFormGroups() {
		return sysFormGroups;
	}
	@JsonBackReference
	public void setSysFormGroups(java.util.Set<SysFormGroup> in_sysFormGroups) {
		this.sysFormGroups = in_sysFormGroups;
	}

	/**
	 * 表单方案ID * @return String
	 */
	public String getFormSchemaId() {
		return this.formSchemaId;
	}

	/**
	 * 设置 表单方案ID
	 */
	public void setFormSchemaId(String aValue) {
		this.formSchemaId = aValue;
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

	/**
	 * 方案名称 * @return String
	 */
	public String getSchemaName() {
		return this.schemaName;
	}

	/**
	 * 设置 方案名称
	 */
	public void setSchemaName(String aValue) {
		this.schemaName = aValue;
	}

	/**
	 * 表单标题 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置 表单标题
	 */
	public void setTitle(String aValue) {
		this.title = aValue;
	}

	/**
	 * 方案排序 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 方案排序
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

	/**
	 * 是否为系统默认 * @return String
	 */
	public String getIsSystem() {
		return this.isSystem;
	}

	/**
	 * 设置 是否为系统默认
	 */
	public void setIsSystem(String aValue) {
		this.isSystem = aValue;
	}

	/**
	 * 方案Key * @return String
	 */
	public String getSchemaKey() {
		return this.schemaKey;
	}

	/**
	 * 设置 方案Key
	 */
	public void setSchemaKey(String aValue) {
		this.schemaKey = aValue;
	}

	/**
	 * 窗口宽 * @return Integer
	 */
	public Integer getWinWidth() {
		return this.winWidth;
	}

	/**
	 * 设置 窗口宽
	 */
	public void setWinWidth(Integer aValue) {
		this.winWidth = aValue;
	}

	/**
	 * 窗口高 * @return Integer
	 */
	public Integer getWinHeight() {
		return this.winHeight;
	}

	/**
	 * 设置 窗口高
	 */
	public void setWinHeight(Integer aValue) {
		this.winHeight = aValue;
	}

	/**
	 * 列数 * @return Integer
	 */
	public Integer getColNums() {
		return this.colNums;
	}

	/**
	 * 设置 列数
	 */
	public void setColNums(Integer aValue) {
		this.colNums = aValue;
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
		return this.formSchemaId;
	}

	@Override
	public Serializable getPkId() {
		return this.formSchemaId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.formSchemaId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysFormSchema)) {
			return false;
		}
		SysFormSchema rhs = (SysFormSchema) object;
		return new EqualsBuilder()
				.append(this.formSchemaId, rhs.formSchemaId)
						.append(this.schemaName, rhs.schemaName)
				.append(this.title, rhs.title)
				.append(this.sn, rhs.sn)
				.append(this.isSystem, rhs.isSystem)
				.append(this.schemaKey, rhs.schemaKey)
				.append(this.winWidth, rhs.winWidth)
				.append(this.winHeight, rhs.winHeight)
				.append(this.colNums, rhs.colNums)
				.append(this.displayMode, rhs.displayMode)
				.append(this.jsonConfig, rhs.jsonConfig)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.tenantId, rhs.tenantId)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.formSchemaId) 
						.append(this.schemaName) 
				.append(this.title) 
				.append(this.sn) 
				.append(this.isSystem) 
				.append(this.schemaKey) 
				.append(this.winWidth) 
				.append(this.winHeight) 
				.append(this.colNums) 
				.append(this.displayMode) 
				.append(this.jsonConfig) 
				.append(this.createBy) 
				.append(this.createTime) 
				.append(this.tenantId) 
				.append(this.updateBy) 
				.append(this.updateTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("formSchemaId", this.formSchemaId) 
						.append("schemaName", this.schemaName) 
				.append("title", this.title) 
				.append("sn", this.sn) 
				.append("isSystem", this.isSystem) 
				.append("schemaKey", this.schemaKey) 
				.append("winWidth", this.winWidth) 
				.append("winHeight", this.winHeight) 
				.append("colNums", this.colNums) 
				.append("displayMode", this.displayMode) 
				.append("jsonConfig", this.jsonConfig) 
				.append("createBy", this.createBy) 
				.append("createTime", this.createTime) 
				.append("tenantId", this.tenantId) 
				.append("updateBy", this.updateBy) 
				.append("updateTime", this.updateTime) 
				.toString();
	}


}
