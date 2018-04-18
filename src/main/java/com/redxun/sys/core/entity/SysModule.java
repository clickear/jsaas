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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 * 描述：SysModule实体类定义
 * 系统功能模块
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_MODULE")
@TableDefine(title = "系统功能模块")
public class SysModule extends BaseEntity {
	
	@FieldDefine(title = "模块ID", group = "基本信息")
	@Id
	@Column(name = "MODULE_ID_")
	protected String moduleId;
	/* 模块标题 */
	@FieldDefine(title = "模块标题", group = "基本信息")
	@Column(name = "TITLE_")
	@Size(max = 50)
	@NotEmpty
	protected String title;
	/* 描述 */
	@FieldDefine(title = "描述", group = "基本信息")
	@Column(name = "DESCP_")
	@Size(max = 50)
	protected String descp;
	/* 映射URL地址 */
	@FieldDefine(title = "映射URL地址", group = "基本信息")
	@Column(name = "REQ_URL_")
	@Size(max = 150)
	protected String reqUrl;
	/* icon地址样式 */
	@FieldDefine(title = "Icon样式", group = "基本信息")
	@Column(name = "ICON_CLS_")
	@Size(max = 20)
	protected String iconCls;
	/* 简称 */
	@FieldDefine(title = "简称", group = "基本信息")
	@Column(name = "SHORT_NAME_")
	@Size(max = 20)
	protected String shortName;
	/* 表名 */
	@FieldDefine(title = "表名", group = "基本信息")
	@Column(name = "TABLE_NAME_")
	@Size(max = 50)
	protected String tableName;
	/* 实体名 */
	@FieldDefine(title = "实体名", group = "基本信息")
	@Column(name = "ENTITY_NAME_")
	@Size(max = 100)
	protected String entityName;
	/* 命名空间 */
	@FieldDefine(title = "命名空间", group = "基本信息")
	@Column(name = "NAMESPACE_")
	@Size(max = 100)
	protected String namespace;
	/* 主键字段名 */
	@FieldDefine(title = "主键字段名", group = "基本信息")
	@Column(name = "PK_FIELD_")
	@Size(max = 50)
	@NotEmpty
	protected String pkField;
	
	/* 主键字段名 */
	@FieldDefine(title = "数据库主键字段名", group = "基本信息")
	@Column(name = "PK_DB_FIELD_")
	@Size(max = 50)
	@NotEmpty
	protected String pkDbField;
	
	/* 编码字段名 */
	@FieldDefine(title = "编码字段名", group = "基本信息")
	@Column(name = "CODE_FIELD_")
	@Size(max = 50)
	protected String codeField;
	/* 排序字段名 */
	@FieldDefine(title = "排序字段名", group = "基本信息")
	@Column(name = "ORDER_FIELD_")
	@Size(max = 50)
	protected String orderField;
	/* 日期字段 */
	@FieldDefine(title = "日期字段", group = "基本信息")
	@Column(name = "DATE_FIELD_")
	@Size(max = 50)
	protected String dateField;
	/* 年份字段 */
	@FieldDefine(title = "年份字段", group = "基本信息")
	@Column(name = "YEAR_FIELD_")
	@Size(max = 50)
	protected String yearField;
	/* 月份字段 */
	@FieldDefine(title = "月份字段", group = "基本信息")
	@Column(name = "MONTH_FIELD_")
	@Size(max = 50)
	protected String monthField;
	/* 季度字段 */
	@FieldDefine(title = "季度字段", group = "基本信息")
	@Column(name = "SENSON_FIELD_")
	@Size(max = 50)
	protected String sensonField;
	/* 文件字段 */
	@FieldDefine(title = " 文件字段", group = "基本信息")
	@Column(name = "FILE_FIELD_")
	@Size(max = 50)
	protected String fileField;
	/* 是否启用 */
	@FieldDefine(title = "是否启用", group = "基本信息")
	@Column(name = "IS_ENABLED_")
	@Size(max = 6)
	@NotEmpty
	protected String isEnabled;

	/* 是否审计执行日记 */
	@FieldDefine(title = "是否审计执行日记", group = "基本信息")
	@Column(name = "ALLOW_AUDIT_")
	@Size(max = 6)
	@NotEmpty
	protected String allowAudit;
	/* 是否启动审批 */
	@FieldDefine(title = "是否启动审批", group = "基本信息")
	@Column(name = "ALLOW_APPROVE_")
	@Size(max = 6)
	@NotEmpty
	protected String allowApprove;
	/* 是否有附件 */
	@FieldDefine(title = "是否有附件", group = "基本信息")
	@Column(name = "HAS_ATTACHS_")
	@Size(max = 6)
	@NotEmpty
	protected String hasAttachs;
	/* 缺省排序字段 */
	@FieldDefine(title = "缺省排序字段", group = "基本信息")
	@Column(name = "DEF_ORDER_FIELD_")
	@Size(max = 50)
	protected String defOrderField;
	/* 编码流水键 */
	@FieldDefine(title = "编码流水键", group = "基本信息")
	@Column(name = "SEQ_CODE_")
	@Size(max = 50)
	protected String seqCode;
	/* 是否有图表 */
	@FieldDefine(title = "是否有图表", group = "基本信息")
	@Column(name = "HAS_CHART_")
	@Size(max = 6)
	@NotEmpty
	protected String hasChart;
	
	/* 帮助内容 */
	@FieldDefine(title = "帮助内容", group = "基本信息")
	@Column(name = "HELP_HTML_")
	@Size(max = 65535)
	protected String helpHtml;
	
	@FieldDefine(title="是否系统缺省", group="基本信息")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 8)
	protected String isDefault;
	
	@FieldDefine(title = "所属子系统", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "SYS_ID_")
	protected com.redxun.sys.core.entity.Subsystem subsystem;
	
	@FieldDefine(title = "字段列表", group = "基本信息")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sysModule", fetch = FetchType.LAZY)
	protected java.util.Set<SysField> sysFields = new java.util.HashSet<SysField>();

	/**
	 * Default Empty Constructor for class SysModule
	 */
	public SysModule() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysModule
	 */
	public SysModule(String in_moduleId) {
		this.setModuleId(in_moduleId);
	}

	public com.redxun.sys.core.entity.Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(com.redxun.sys.core.entity.Subsystem in_subsystem) {
		this.subsystem = in_subsystem;
	}
	@JsonBackReference
	public java.util.Set<SysField> getSysFields() {
		return sysFields;
	}
	@JsonBackReference
	public void setSysFields(java.util.Set<SysField> in_sysFields) {
		this.sysFields = in_sysFields;
	}

	/**
	 * 模块ID * @return String
	 */
	public String getModuleId() {
		return this.moduleId;
	}

	/**
	 * 设置 模块ID
	 */
	public void setModuleId(String aValue) {
		this.moduleId = aValue;
	}

	/**
	 * 模块标题 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置 模块标题
	 */
	public void setTitle(String aValue) {
		this.title = aValue;
	}

	/**
	 * 描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 映射URL地址 * @return String
	 */
	public String getReqUrl() {
		return this.reqUrl;
	}

	/**
	 * 设置 映射URL地址
	 */
	public void setReqUrl(String aValue) {
		this.reqUrl = aValue;
	}

	/**
	 * icon地址样式 * @return String
	 */
	public String getIconCls() {
		return this.iconCls;
	}

	/**
	 * 设置 icon地址样式
	 */
	public void setIconCls(String aValue) {
		this.iconCls = aValue;
	}

	/**
	 * 简称 * @return String
	 */
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * 设置 简称
	 */
	public void setShortName(String aValue) {
		this.shortName = aValue;
	}

	/**
	 * 所属子系统 * @return String
	 */
	public String getSysId() {
		return this.getSubsystem() == null ? null : this.getSubsystem()
				.getSysId();
	}

	/**
	 * 设置 所属子系统
	 */
	public void setSysId(String aValue) {
		if (StringUtils.isEmpty(aValue)) {
			subsystem = null;
		} else if (subsystem == null) {
			subsystem = new com.redxun.sys.core.entity.Subsystem(aValue);
			// subsystem.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			subsystem.setSysId(aValue);
		}
	}

	/**
	 * 表名 * @return String
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 设置 表名
	 */
	public void setTableName(String aValue) {
		this.tableName = aValue;
	}

	/**
	 * 实体名 * @return String
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * 设置 实体名
	 */
	public void setEntityName(String aValue) {
		this.entityName = aValue;
	}

	/**
	 * 命名空间 * @return String
	 */
	public String getNamespace() {
		return this.namespace;
	}

	/**
	 * 设置 命名空间
	 */
	public void setNamespace(String aValue) {
		this.namespace = aValue;
	}

	/**
	 * 主键字段名 * @return String
	 */
	public String getPkField() {
		return this.pkField;
	}

	/**
	 * 设置 主键字段名
	 */
	public void setPkField(String aValue) {
		this.pkField = aValue;
	}

	/**
	 * 编码字段名 * @return String
	 */
	public String getCodeField() {
		return this.codeField;
	}

	/**
	 * 设置 编码字段名
	 */
	public void setCodeField(String aValue) {
		this.codeField = aValue;
	}

	/**
	 * 排序字段名 * @return String
	 */
	public String getOrderField() {
		return this.orderField;
	}

	/**
	 * 设置 排序字段名
	 */
	public void setOrderField(String aValue) {
		this.orderField = aValue;
	}

	/**
	 * 日期字段 * @return String
	 */
	public String getDateField() {
		return this.dateField;
	}

	/**
	 * 设置 日期字段
	 */
	public void setDateField(String aValue) {
		this.dateField = aValue;
	}

	/**
	 * 年份字段 * @return String
	 */
	public String getYearField() {
		return this.yearField;
	}

	/**
	 * 设置 年份字段
	 */
	public void setYearField(String aValue) {
		this.yearField = aValue;
	}

	/**
	 * 月份字段 * @return String
	 */
	public String getMonthField() {
		return this.monthField;
	}

	/**
	 * 设置 月份字段
	 */
	public void setMonthField(String aValue) {
		this.monthField = aValue;
	}

	/**
	 * 季度字段 * @return String
	 */
	public String getSensonField() {
		return this.sensonField;
	}

	/**
	 * 设置 季度字段
	 */
	public void setSensonField(String aValue) {
		this.sensonField = aValue;
	}

	/**
	 * 文件字段 * @return String
	 */
	public String getFileField() {
		return this.fileField;
	}

	/**
	 * 设置 文件字段
	 */
	public void setFileField(String aValue) {
		this.fileField = aValue;
	}

	/**
	 * 是否启用 * @return String
	 */
	public String getIsEnabled() {
		return this.isEnabled;
	}

	/**
	 * 设置 是否启用
	 */
	public void setIsEnabled(String aValue) {
		this.isEnabled = aValue;
	}

	
	/**
	 * 是否审计执行日记 * @return String
	 */
	public String getAllowAudit() {
		return this.allowAudit;
	}

	/**
	 * 设置 是否审计执行日记
	 */
	public void setAllowAudit(String aValue) {
		this.allowAudit = aValue;
	}

	/**
	 * 是否启动审批 * @return String
	 */
	public String getAllowApprove() {
		return this.allowApprove;
	}

	/**
	 * 设置 是否启动审批
	 */
	public void setAllowApprove(String aValue) {
		this.allowApprove = aValue;
	}

	/**
	 * 是否有附件 * @return String
	 */
	public String getHasAttachs() {
		return this.hasAttachs;
	}

	/**
	 * 设置 是否有附件
	 */
	public void setHasAttachs(String aValue) {
		this.hasAttachs = aValue;
	}

	/**
	 * 缺省排序字段 * @return String
	 */
	public String getDefOrderField() {
		return this.defOrderField;
	}

	/**
	 * 设置 缺省排序字段
	 */
	public void setDefOrderField(String aValue) {
		this.defOrderField = aValue;
	}

	/**
	 * 编码流水键 * @return String
	 */
	public String getSeqCode() {
		return this.seqCode;
	}

	/**
	 * 设置 编码流水键
	 */
	public void setSeqCode(String aValue) {
		this.seqCode = aValue;
	}

	/**
	 * 是否有图表 * @return String
	 */
	public String getHasChart() {
		return this.hasChart;
	}

	/**
	 * 设置 是否有图表
	 */
	public void setHasChart(String aValue) {
		this.hasChart = aValue;
	}

	/**
	 * 帮助内容 * @return String
	 */
	public String getHelpHtml() {
		return this.helpHtml;
	}

	/**
	 * 设置 帮助内容
	 */
	public void setHelpHtml(String aValue) {
		this.helpHtml = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.title;
	}

	@Override
	public Serializable getPkId() {
		return this.moduleId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.moduleId = (String) pkId;
	}

	public String getPkDbField() {
		return pkDbField;
	}

	public void setPkDbField(String pkDbField) {
		this.pkDbField = pkDbField;
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
		if (!(object instanceof SysModule)) {
			return false;
		}
		SysModule rhs = (SysModule) object;
		return new EqualsBuilder().append(this.moduleId, rhs.moduleId)
				.append(this.title, rhs.title).append(this.descp, rhs.descp)
				.append(this.reqUrl, rhs.reqUrl)
				.append(this.iconCls, rhs.iconCls)
				.append(this.shortName, rhs.shortName)
				.append(this.tableName, rhs.tableName)
				.append(this.entityName, rhs.entityName)
				.append(this.namespace, rhs.namespace)
				.append(this.pkField, rhs.pkField)
				.append(this.codeField, rhs.codeField)
				.append(this.orderField, rhs.orderField)
				.append(this.dateField, rhs.dateField)
				.append(this.yearField, rhs.yearField)
				.append(this.monthField, rhs.monthField)
				.append(this.sensonField, rhs.sensonField)
				.append(this.fileField, rhs.fileField)
				.append(this.isEnabled, rhs.isEnabled)
				.append(this.allowAudit, rhs.allowAudit)
				.append(this.allowApprove, rhs.allowApprove)
				.append(this.hasAttachs, rhs.hasAttachs)
				.append(this.defOrderField, rhs.defOrderField)
				.append(this.seqCode, rhs.seqCode)
				.append(this.hasChart, rhs.hasChart)
				.append(this.helpHtml, rhs.helpHtml)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.moduleId)
				.append(this.title).append(this.descp).append(this.reqUrl)
				.append(this.iconCls).append(this.shortName)
				.append(this.tableName).append(this.entityName)
				.append(this.namespace).append(this.pkField)
				.append(this.codeField).append(this.orderField)
				.append(this.dateField).append(this.yearField)
				.append(this.monthField).append(this.sensonField)
				.append(this.fileField).append(this.isEnabled)
				.append(this.allowAudit).append(this.allowApprove)
				.append(this.hasAttachs)
				.append(this.defOrderField).append(this.seqCode)
				.append(this.hasChart).append(this.helpHtml)
				.append(this.createTime).append(this.updateBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("moduleId", this.moduleId)
				.append("title", this.title).append("descp", this.descp)
				.append("reqUrl", this.reqUrl).append("iconCls", this.iconCls)
				.append("shortName", this.shortName)
				.append("tableName", this.tableName)
				.append("entityName", this.entityName)
				.append("namespace", this.namespace)
				.append("pkField", this.pkField)
				.append("codeField", this.codeField)
				.append("orderField", this.orderField)
				.append("dateField", this.dateField)
				.append("yearField", this.yearField)
				.append("monthField", this.monthField)
				.append("sensonField", this.sensonField)
				.append("fileField", this.fileField)
				.append("isEnabled", this.isEnabled)
				.append("allowAudit", this.allowAudit)
				.append("allowApprove", this.allowApprove)
				.append("hasAttachs", this.hasAttachs)
				.append("defOrderField", this.defOrderField)
				.append("seqCode", this.seqCode)
				.append("hasChart", this.hasChart)
				.append("helpHtml", this.helpHtml)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
