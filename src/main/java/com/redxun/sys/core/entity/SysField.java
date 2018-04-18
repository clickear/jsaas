package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import com.redxun.core.constants.WidgetType;
import com.redxun.core.entity.BaseEntity;
import com.redxun.sys.core.enums.FieldDataType;

/**
 * <pre>
 * 描述：SysField实体类定义
 * 功能模块字段
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_FIELD")
@TableDefine(title = "系统模块字段")
public class SysField extends BaseEntity {
	/**
	 * FIELD_COMMON=普通字段
	 */
	public final static String FIELD_COMMON="FIELD_COMMON";
	/**
	 * FIELD_PK=主键字段
	 */
	public final static String FIELD_PK="FIELD_PK";
	/**
	 * FIELD_RELATION=关系字段
	 */
	public final static String FIELD_RELATION="FIELD_RELATION";

	/**
	 * 一对多=oneToMany
	 */
	public final static String RELATION_ONE_TO_MANY="OneToMany";
	/**
	 * 一对一=oneToOne
	 */
	public final static String RELATION_ONE_TO_ONE="OneToOne";
	/**
	 * 多对一=manyToOne
	 */
	public final static String RELATION_MANY_TO_ONE="ManyToOne";
	/**
	 * 多对多=manyToMany
	 */
	public final static String RELATION_MANY_TO_MANY="ManyToMany";
	/**
	 * 无关系=NONE
	 */
	public final static String RELATION_NONE="NONE";
	/**
	 * 子记录添加的方式--弹出窗口新建=Window
	 */
	public final static String ADD_MODE_WIN="WINDOW";
	/**
	 * 子记录添加的方式--弹出窗口选择=SELECT
	 */
	public final static String ADD_MODE_SEL="SELECT";
	/**
	 * 子记录添加的方式--在Grid内进行编辑=INNER
	 */
	public final static String ADD_MODE_INNER="INNER";
	
	@FieldDefine(title = "字段ID", group = "基本信息")
	@Id
	@Column(name = "FIELD_ID_")
	protected String fieldId;
	/* 标题 */
	@FieldDefine(title = "标题", group = "基本信息")
	@Column(name = "TITLE_")
	@Size(max = 50)
	@NotEmpty
	protected String title;
	/* 字段名称 */
	@FieldDefine(title = "字段名称", group = "基本信息")
	@Column(name = "ATTR_NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String attrName;
	/* 字段类型 */
	@FieldDefine(title = "字段类型", group = "基本信息")
	@Column(name = "FIELD_TYPE_")
	@Size(max = 50)
	@NotEmpty
	protected String fieldType;
	/* 字段分组 */
	@FieldDefine(title = "字段分组", group = "基本信息")
	@Column(name = "FIELD_GROUP_")
	@Size(max = 50)
	protected String fieldGroup;
	
	@FieldDefine(title = "默认跨列数", group = "基本信息")
	@Column(name = "COLSPAN_")
	protected Integer colspan;
	
	/* 字段长度 */
	@FieldDefine(title = "字段长度", group = "基本信息")
	@Column(name = "FIELD_LENGTH_")
	protected Integer fieldLength;
	/* 字段精度 */
	@FieldDefine(title = "字段精度", group = "基本信息")
	@Column(name = "PRECISION_")
	protected Integer precision;
	/* 字段序号 */
	@FieldDefine(title = "字段序号", group = "基本信息")
	@Column(name = "SN_")
	protected Integer sn;
	/* 字段分类 */
	@FieldDefine(title = "字段分类", group = "基本信息")
	@Column(name = "FIELD_CAT_")
	@Size(max = 50)
	@NotEmpty
	protected String fieldCat;
	
	/* 字段分类 */
	@FieldDefine(title = "关系类型", group = "基本信息")
	@Column(name = "RELATION_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String relationType;

	/* 添加权限 */
	@FieldDefine(title = "添加权限", group = "基本信息")
	@Column(name = "ADD_RIGHT_")
	@Size(max = 20)
	protected String addRight;
	
	/* 编辑权限 */
	@FieldDefine(title = "编辑权限", group = "基本信息")
	@Column(name = "EDIT_RIGHT_")
	@Size(max = 20)
	protected String editRight;
	
	/* 是否隐藏 */
	@FieldDefine(title = "是否隐藏", group = "基本信息")
	@Column(name = "IS_HIDDEN_")
	@Size(max = 6)
	protected String isHidden;
	/* 是否只读 */
	@FieldDefine(title = "是否只读", group = "基本信息")
	@Column(name = "IS_READABLE_")
	@Size(max = 6)
	protected String isReadable;
	/* 是否必须 */
	@FieldDefine(title = "是否必须", group = "基本信息")
	@Column(name = "IS_REQUIRED_")
	@Size(max = 6)
	protected String isRequired;
	/* 是否禁用 */
	@FieldDefine(title = "是否禁用", group = "基本信息")
	@Column(name = "IS_DISABLED_")
	@Size(max = 6)
	protected String isDisabled;
	/* 是否允许分组 */
	@FieldDefine(title = "是否允许分组", group = "基本信息")
	@Column(name = "ALLOW_GROUP_")
	@Size(max = 6)
	protected String allowGroup;
	/* 是否允许统计 */
	@FieldDefine(title = "是否允许统计", group = "基本信息")
	@Column(name = "ALLOW_SUM_")
	@Size(max = 6)
	protected String allowSum;
	/* 缺省值 */
	@FieldDefine(title = "缺省值", group = "基本信息")
	@Column(name = "DEF_VALUE_")
	@Size(max = 50)
	protected String defValue;
	/* 备注 */
	@FieldDefine(title = "备注", group = "基本信息")
	@Column(name = "REMARK_")
	@Size(max = 65535)
	protected String remark;
	/* 是否在导航树上展示 */
	@FieldDefine(title = "是否在导航树上展示", group = "基本信息")
	@Column(name = "SHOW_NAV_TREE_")
	@Size(max = 6)
	protected String showNavTree;
	/* 数据库字段名 */
	@FieldDefine(title = "数据库字段名", group = "基本信息")
	@Column(name = "DB_FIELD_NAME_")
	@Size(max = 50)
	protected String dbFieldName;
	/* 数据库字段公式 */
	@FieldDefine(title = " 数据库字段公式", group = "基本信息")
	@Column(name = "DB_FIELD_FORMULA_")
	@Size(max = 65535)
	protected String dbFieldFormula;
	
	/* 是否允许排号 */
	@FieldDefine(title = "是否允许排号", group = "基本信息")
	@Column(name = "ALLOW_SORT_")
	@Size(max = 6)
	protected String allowSort;
	
	/* 组件类型 */
	@FieldDefine(title = "组件类型", group = "基本信息")
	@Column(name = "COMP_TYPE_")
	@Size(max = 50)
	protected String compType;

	/* 是否允许Excel插入 */
	@FieldDefine(title = "是否允许Excel插入", group = "基本信息")
	@Column(name = "ALLOW_EXCEL_INSERT_")
	@Size(max = 6)
	protected String allowExcelInsert;
	/* 是否允许Excel编辑 */
	@FieldDefine(title = "是否允许Excel编辑", group = "基本信息")
	@Column(name = "ALLOW_EXCEL_EDIT_")
	@Size(max = 6)
	protected String allowExcelEdit;
	/* 是否允许有附件 */
	@FieldDefine(title = "是否允许有附件", group = "基本信息")
	@Column(name = "HAS_ATTACH_")
	@Size(max = 6)
	protected String hasAttach;
	/* 是否图表分类 */
	@FieldDefine(title = "是否图表分类", group = "基本信息")
	@Column(name = "IS_CHAR_CAT_")
	@Size(max = 6)
	protected String isCharCat;

	/*展示定义*/
	@FieldDefine(title = "展示定义", group = "基本信息")
	@Column(name = "RENDERER_")
	@Size(max = 512)
	protected String renderer;
	
	/*是否用户自定义*/
	@FieldDefine(title = "是否用户自定义", group = "基本信息")
	@Column(name = "IS_USER_DEF_")
	@Size(max = 6)
	protected String isUserDef;
	@FieldDefine(title = "系统模块", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "MODULE_ID_")
	@JsonBackReference
	protected com.redxun.sys.core.entity.SysModule sysModule;
	
	@FieldDefine(title = "关联的系统模块", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "LINK_MOD_ID_")
	@JsonBackReference
	protected com.redxun.sys.core.entity.SysModule linkSysModule;
	
	@FieldDefine(title = "是否缺省显示列", group = "基本信息")
	@Column(name = "IS_DEFAULT_COL_")
	@Size(max = 6)
	protected String isDefaultCol;
	@FieldDefine(title = "是否缺省显示列", group = "基本信息")
	@Column(name = "IS_QUERY_COL_")
	@Size(max = 6)
	protected String isQueryCol;
	
	/* 备注 */
	@FieldDefine(title = "控件JSON配置", group = "基本信息")
	@Column(name = "JSON_CONFIG_")
	@Size(max = 65535)
	protected String jsonConfig;
	
	@FieldDefine(title = "关联字段值新增方式", group = "基本信息")
	@Column(name = "LINK_ADD_MODE_")
	@Size(max = 16)
	protected String linkAddMode;
	
	/**
	 * Default Empty Constructor for class SysField
	 */
	public SysField() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysField
	 */
	public SysField(String in_fieldId) {
		this.setFieldId(in_fieldId);
	}
	
	public com.redxun.sys.core.entity.SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(com.redxun.sys.core.entity.SysModule in_sysModule) {
		this.sysModule = in_sysModule;
	}

	/**
	 * 字段ID * @return String
	 */
	public String getFieldId() {
		return this.fieldId;
	}

	/**
	 * 设置 字段ID
	 */
	public void setFieldId(String aValue) {
		this.fieldId = aValue;
	}

	/**
	 * 模块ID * @return String
	 */
	public String getModuleId() {
		return this.getSysModule() == null ? null : this.getSysModule()
				.getModuleId();
	}

	public String getFieldCat() {
		return fieldCat;
	}

	public void setFieldCat(String fieldCat) {
		this.fieldCat = fieldCat;
	}

	/**
	 * 设置 模块ID
	 */
	public void setModuleId(String aValue) {
		if (StringUtils.isEmpty(aValue)) {
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
	 * 标题 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置 标题
	 */
	public void setTitle(String aValue) {
		this.title = aValue;
	}

	/**
	 * 字段名称 * @return String
	 */
	public String getAttrName() {
		return this.attrName;
	}

	/**
	 * 设置 字段名称
	 */
	public void setAttrName(String aValue) {
		this.attrName = aValue;
	}

	/**
	 * 字段类型 * @return String
	 */
	public String getFieldType() {
		return this.fieldType;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	/**
	 * 设置 字段类型
	 */
	public void setFieldType(String aValue) {
		this.fieldType = aValue;
	}

	/**
	 * 字段分组 * @return String
	 */
	public String getFieldGroup() {
		return this.fieldGroup;
	}

	/**
	 * 设置 字段分组
	 */
	public void setFieldGroup(String aValue) {
		this.fieldGroup = aValue;
	}

	/**
	 * 字段长度 * @return Integer
	 */
	public Integer getFieldLength() {
		return this.fieldLength;
	}

	/**
	 * 设置 字段长度
	 */
	public void setFieldLength(Integer aValue) {
		this.fieldLength = aValue;
	}

	/**
	 * 字段精度 * @return Integer
	 */
	public Integer getPrecision() {
		return this.precision;
	}

	/**
	 * 设置 字段精度
	 */
	public void setPrecision(Integer aValue) {
		this.precision = aValue;
	}

	/**
	 * 字段序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 字段序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

	/**
	 * 是否隐藏 * @return String
	 */
	public String getIsHidden() {
		return this.isHidden;
	}

	/**
	 * 设置 是否隐藏
	 */
	public void setIsHidden(String aValue) {
		this.isHidden = aValue;
	}

	/**
	 * 是否只读 * @return String
	 */
	public String getIsReadable() {
		return this.isReadable;
	}

	/**
	 * 设置 是否只读
	 */
	public void setIsReadable(String aValue) {
		this.isReadable = aValue;
	}

	/**
	 * 是否必须 * @return String
	 */
	public String getIsRequired() {
		return this.isRequired;
	}

	/**
	 * 设置 是否必须
	 */
	public void setIsRequired(String aValue) {
		this.isRequired = aValue;
	}

	/**
	 * 是否禁用 * @return String
	 */
	public String getIsDisabled() {
		return this.isDisabled;
	}

	/**
	 * 设置 是否禁用
	 */
	public void setIsDisabled(String aValue) {
		this.isDisabled = aValue;
	}

	/**
	 * 是否允许分组 * @return String
	 */
	public String getAllowGroup() {
		return this.allowGroup;
	}

	/**
	 * 设置 是否允许分组
	 */
	public void setAllowGroup(String aValue) {
		this.allowGroup = aValue;
	}

	/**
	 * 是否允许统计 * @return String
	 */
	public String getAllowSum() {
		return this.allowSum;
	}

	/**
	 * 设置 是否允许统计
	 */
	public void setAllowSum(String aValue) {
		this.allowSum = aValue;
	}

	/**
	 * 缺省值 * @return String
	 */
	public String getDefValue() {
		return this.defValue;
	}

	/**
	 * 设置 缺省值
	 */
	public void setDefValue(String aValue) {
		this.defValue = aValue;
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
	 * 是否在导航树上展示 * @return String
	 */
	public String getShowNavTree() {
		return this.showNavTree;
	}

	/**
	 * 设置 是否在导航树上展示
	 */
	public void setShowNavTree(String aValue) {
		this.showNavTree = aValue;
	}

	/**
	 * 数据库字段名 * @return String
	 */
	public String getDbFieldName() {
		return this.dbFieldName;
	}

	/**
	 * 设置 数据库字段名
	 */
	public void setDbFieldName(String aValue) {
		this.dbFieldName = aValue;
	}

	/**
	 * 数据库字段公式 * @return String
	 */
	public String getDbFieldFormula() {
		return this.dbFieldFormula;
	}

	/**
	 * 设置 数据库字段公式
	 */
	public void setDbFieldFormula(String aValue) {
		this.dbFieldFormula = aValue;
	}


	/**
	 * 是否允许Excel插入 * @return String
	 */
	public String getAllowExcelInsert() {
		return this.allowExcelInsert;
	}

	/**
	 * 设置 是否允许Excel插入
	 */
	public void setAllowExcelInsert(String aValue) {
		this.allowExcelInsert = aValue;
	}

	/**
	 * 是否允许Excel编辑 * @return String
	 */
	public String getAllowExcelEdit() {
		return this.allowExcelEdit;
	}

	/**
	 * 设置 是否允许Excel编辑
	 */
	public void setAllowExcelEdit(String aValue) {
		this.allowExcelEdit = aValue;
	}

	/**
	 * 是否允许有附件 * @return String
	 */
	public String getHasAttach() {
		return this.hasAttach;
	}

	/**
	 * 设置 是否允许有附件
	 */
	public void setHasAttach(String aValue) {
		this.hasAttach = aValue;
	}

	/**
	 * 是否图表分类 * @return String
	 */
	public String getIsCharCat() {
		return this.isCharCat;
	}

	/**
	 * 设置 是否图表分类
	 */
	public void setIsCharCat(String aValue) {
		this.isCharCat = aValue;
	}

	
	@Override
	public String getIdentifyLabel() {
		return this.getTitle();
	}

	@Override
	public Serializable getPkId() {
		return this.fieldId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.fieldId = (String) pkId;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public String getIsUserDef() {
		return isUserDef;
	}

	public void setIsUserDef(String isUserDef) {
		this.isUserDef = isUserDef;
	}

	public String getAllowSort() {
		return allowSort;
	}

	public void setAllowSort(String allowSort) {
		this.allowSort = allowSort;
	}

	public String getCompType() {
		if(compType==null){
			return getDefaultWidgetType();
		}
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public String getIsDefaultCol() {
		return isDefaultCol;
	}

	public void setIsDefaultCol(String isDefaultCol) {
		this.isDefaultCol = isDefaultCol;
	}

	public String getIsQueryCol() {
		return isQueryCol;
	}

	public void setIsQueryCol(String isQueryCol) {
		this.isQueryCol = isQueryCol;
	}
	

	public String getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(String jsonConfig) {
		this.jsonConfig = jsonConfig;
	}

	public com.redxun.sys.core.entity.SysModule getLinkSysModule() {
		return linkSysModule;
	}

	public void setLinkSysModule(com.redxun.sys.core.entity.SysModule linkSysModule) {
		this.linkSysModule = linkSysModule;
	}

	public String getLinkAddMode() {
		return linkAddMode;
	}

	public void setLinkAddMode(String linkAddMode) {
		this.linkAddMode = linkAddMode;
	}
	

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getAddRight() {
		return addRight;
	}

	public void setAddRight(String addRight) {
		this.addRight = addRight;
	}

	public String getEditRight() {
		return editRight;
	}

	public void setEditRight(String editRight) {
		this.editRight = editRight;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysField)) {
			return false;
		}
		SysField rhs = (SysField) object;
		return new EqualsBuilder().append(this.fieldId, rhs.fieldId)
				.append(this.title, rhs.title)
				.append(this.attrName, rhs.attrName)
				.append(this.fieldType, rhs.fieldType)
				.append(this.fieldGroup, rhs.fieldGroup)
				.append(this.fieldLength, rhs.fieldLength)
				.append(this.precision, rhs.precision).append(this.sn, rhs.sn)
				.append(this.fieldCat, rhs.fieldCat)
				.append(this.isHidden, rhs.isHidden)
				.append(this.isReadable, rhs.isReadable)
				.append(this.isRequired, rhs.isRequired)
				.append(this.isDisabled, rhs.isDisabled)
				.append(this.allowGroup, rhs.allowGroup)
				.append(this.allowSum, rhs.allowSum)
				.append(this.defValue, rhs.defValue)
				.append(this.remark, rhs.remark)
				.append(this.showNavTree, rhs.showNavTree)
				.append(this.dbFieldName, rhs.dbFieldName)
				.append(this.dbFieldFormula, rhs.dbFieldFormula)
				.append(this.allowExcelInsert, rhs.allowExcelInsert)
				.append(this.allowExcelEdit, rhs.allowExcelEdit)
				.append(this.hasAttach, rhs.hasAttach)
				.append(this.renderer,rhs.renderer)
				.append(this.allowSort,rhs.allowSort)
				.append(this.isUserDef, rhs.isUserDef).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.fieldId)
				.append(this.title).append(this.attrName)
				.append(this.fieldType).append(this.fieldGroup)
				.append(this.fieldLength).append(this.precision)
				.append(this.sn).append(this.fieldCat).append(this.isHidden)
				.append(this.isReadable).append(this.isRequired)
				.append(this.isDisabled).append(this.allowGroup)
				.append(this.allowSum).append(this.defValue)
				.append(this.remark)
				.append(this.showNavTree).append(this.dbFieldName)
				.append(this.dbFieldFormula).append(this.allowExcelInsert)
				.append(this.allowExcelEdit).append(this.hasAttach)
				.append(this.renderer).append(this.allowSort)
				.append(this.isCharCat).append(this.isUserDef).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("fieldId", this.fieldId)
				.append("title", this.title).append("attrName", this.attrName)
				.append("fieldType", this.fieldType)
				.append("fieldGroup", this.fieldGroup)
				.append("fieldLength", this.fieldLength)
				.append("precision", this.precision).append("sn", this.sn)
				.append("fieldCat", this.fieldCat).append("isHidden", this.isHidden)
				.append("isReadable", this.isReadable)
				.append("isRequired", this.isRequired)
				.append("isDisabled", this.isDisabled)
				.append("allowGroup", this.allowGroup)
				.append("allowSum", this.allowSum)
				.append("defValue", this.defValue)
				.append("remark", this.remark)
				.append("showNavTree", this.showNavTree)
				.append("dbFieldName", this.dbFieldName)
				.append("dbFieldFormula", this.dbFieldFormula)
				.append("allowSort",this.allowSort)
				.append("allowExcelInsert", this.allowExcelInsert)
				.append("allowExcelEdit", this.allowExcelEdit)
				.append("hasAttach", this.hasAttach)
				.append("isCharCat", this.isCharCat)
				.append("renderer",this.renderer)
				.append("isUserDef", this.isUserDef).toString();
	}
	/**
	 * 获得Ext的数据转换类型
	 * auto (Default, implies no conversion)
		string
		int
		number
		boolean
		date
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getFieldExtDataType(){
		if("java.lang.String".equals(fieldType)) return FieldDataType.STRING.toString();
		if("java.util.Date".equals(fieldType)) return FieldDataType.DATE.toString();
		if("java.lang.Short".equals(fieldType)) return FieldDataType.NUMBER.toString();
		if("java.lang.Integer".equals(fieldType)) return FieldDataType.NUMBER.toString();
		if("java.lang.Long".equals(fieldType)) return FieldDataType.NUMBER.toString();
		if("java.math.BigDecimal".equals(fieldType)) return FieldDataType.NUMBER.toString();
		if("java.lang.Double".equals(fieldType)) return FieldDataType.NUMBER.toString();
		if("java.lang.Float".equals(fieldType)) return FieldDataType.NUMBER.toString(); 
		return FieldDataType.AUTO.toString();
	}
	
	/**
	 * 获得Ext的数据控件类型
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getDefaultWidgetType(){
		if(this.fieldLength==null) fieldLength=0;
		if(SysField.FIELD_PK.equals(fieldCat)) return WidgetType.HIDDEN.getXtype();
		//默认为Boolean值
		//if(this.dbFieldName.toUpperCase().startsWith("IS_")) return WidgetType.BOOL_RADIO.getXtype();
		if("java.lang.String".equals(fieldType) && fieldLength>200) return WidgetType.TEXTAREA.getXtype();
		if("java.lang.String".equals(fieldType)) return WidgetType.TEXT.getXtype();
		if("java.util.Date".equals(fieldType)) return WidgetType.DATE.getXtype();
		if("java.lang.Short".equals(fieldType)) return WidgetType.NUMBER.getXtype();
		if("java.lang.Integer".equals(fieldType)) return WidgetType.NUMBER.getXtype();
		if("java.lang.Long".equals(fieldType)) return WidgetType.NUMBER.getXtype();
		if("java.math.BigDecimal".equals(fieldType)) WidgetType.NUMBER.getXtype();
		if("java.lang.Double".equals(fieldType)) return WidgetType.NUMBER.getXtype();
		if("java.lang.Float".equals(fieldType)) return WidgetType.NUMBER.getXtype();
		if("java.util.Set".equals(fieldType) || "java.util.List".equals(fieldType)) {
			return WidgetType.LISTMODULE.getXtype();
		}
		if(fieldType!=null&&fieldType.startsWith("com.redxun")){
			return WidgetType.MODULE.getXtype();
		}
		return WidgetType.TEXT.getXtype();
	}
	
}
