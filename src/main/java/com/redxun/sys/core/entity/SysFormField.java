package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 * 描述：SysFormField实体类定义
 * 表单组内字段
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 版权：广州微通软件有限公司版权所有
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_FORM_FIELD")
@TableDefine(title = "表单组内字段")
public class SysFormField extends BaseEntity {

	@FieldDefine(title = "表单字段ID", group = "基本信息")
	@Id
	@Column(name = "FORM_FIELD_ID_")
	protected String formFieldId;
	/* 序号 */
	@FieldDefine(title = "序号", group = "基本信息")
	@Column(name = "SN_")
	protected Integer sn;
	/* 高 */
	@FieldDefine(title = "高", group = "基本信息")
	@Column(name = "HEIGHT_")
	protected Integer height;
	/* 宽 */
	@FieldDefine(title = "宽", group = "基本信息")
	@Column(name = "WIDTH_")
	protected Integer width;
	/* 列跨度 */
	@FieldDefine(title = "列跨度", group = "基本信息")
	@Column(name = "COLSPAN_")
	protected Integer colspan;
	/* 其他JSON设置 */
	@FieldDefine(title = "其他JSON设置", group = "基本信息")
	@Column(name = "JSON_CONF_")
	@Size(max = 65535)
	protected String jsonConf;
	@FieldDefine(title = "控件类型", group = "基本信息")
	@Column(name="COMP_TYPE_")
	@Size(max=50)
	protected String compType;
	
	@FieldDefine(title = "字段名称", group = "基本信息")
	@Column(name="FIELD_NAME_")
	@Size(max=50)
	protected String fieldName;
	
	@FieldDefine(title = "字段标签", group = "基本信息")
	@Column(name="FIELD_LABEL_")
	@Size(max=64)
	protected String fieldLabel;
	//字段值
	@Transient
	protected Object fieldValue;
	
	@FieldDefine(title = "字段定义", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "FIELD_ID_")
	protected com.redxun.sys.core.entity.SysField sysField;
	
	@FieldDefine(title = "所属组ID", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "GROUP_ID_")
	@JsonBackReference 
	protected com.redxun.sys.core.entity.SysFormGroup sysFormGroup;

	/**
	 * Default Empty Constructor for class SysFormField
	 */
	public SysFormField() {
		super();
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	/**
	 * Default Key Fields Constructor for class SysFormField
	 */
	public SysFormField(String in_formFieldId) {
		this.setFormFieldId(in_formFieldId);
	}

	public com.redxun.sys.core.entity.SysField getSysField() {
		return sysField;
	}

	public void setSysField(com.redxun.sys.core.entity.SysField in_sysField) {
		this.sysField = in_sysField;
	}
	@JsonBackReference
	public com.redxun.sys.core.entity.SysFormGroup getSysFormGroup() {
		return sysFormGroup;
	}
	@JsonBackReference
	public void setSysFormGroup(
			com.redxun.sys.core.entity.SysFormGroup in_sysFormGroup) {
		this.sysFormGroup = in_sysFormGroup;
	}

	/**
	 * 表单字段ID * @return String
	 */
	public String getFormFieldId() {
		return this.formFieldId;
	}

	/**
	 * 设置 表单字段ID
	 */
	public void setFormFieldId(String aValue) {
		this.formFieldId = aValue;
	}

	/**
	 * 分组ID * @return String
	 */
	public String getGroupId() {
		return this.getSysFormGroup() == null ? null : this.getSysFormGroup()
				.getGroupId();
	}

	/**
	 * 设置 分组ID
	 */
	public void setGroupId(String aValue) {
		if (aValue == null) {
			sysFormGroup = null;
		} else if (sysFormGroup == null) {
			sysFormGroup = new com.redxun.sys.core.entity.SysFormGroup(aValue);
			// sysFormGroup.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysFormGroup.setGroupId(aValue);
		}
	}

	/**
	 * 字段ID * @return String
	 */
	public String getFieldId() {
		return this.getSysField() == null ? null : this.getSysField()
				.getFieldId();
	}

	/**
	 * 设置 字段ID
	 */
	public void setFieldId(String aValue) {
		if (aValue == null) {
			sysField = null;
		} else if (sysField == null) {
			sysField = new com.redxun.sys.core.entity.SysField(aValue);
			// sysField.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysField.setFieldId(aValue);
		}
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
	 * 高 * @return Integer
	 */
	public Integer getHeight() {
		return this.height;
	}

	/**
	 * 设置 高
	 */
	public void setHeight(Integer aValue) {
		this.height = aValue;
	}

	/**
	 * 宽 * @return Integer
	 */
	public Integer getWidth() {
		return this.width;
	}

	/**
	 * 设置 宽
	 */
	public void setWidth(Integer aValue) {
		this.width = aValue;
	}

	/**
	 * 列跨度 * @return Integer
	 */
	public Integer getColspan() {
		return this.colspan;
	}

	/**
	 * 设置 列跨度
	 */
	public void setColspan(Integer aValue) {
		this.colspan = aValue;
	}

	/**
	 * 其他JSON设置 * @return String
	 */
	public String getJsonConf() {
		return this.jsonConf;
	}

	/**
	 * 设置 其他JSON设置
	 */
	public void setJsonConf(String aValue) {
		this.jsonConf = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.formFieldId;
	}

	@Override
	public Serializable getPkId() {
		return this.formFieldId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.formFieldId = (String) pkId;
	}

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysFormField)) {
			return false;
		}
		SysFormField rhs = (SysFormField) object;
		return new EqualsBuilder().append(this.formFieldId, rhs.formFieldId)
				.append(this.sn, rhs.sn).append(this.height, rhs.height)
				.append(this.width, rhs.width)
				.append(this.colspan, rhs.colspan)
				.append(this.jsonConf, rhs.jsonConf).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.formFieldId).append(this.sn).append(this.height)
				.append(this.compType).append(this.fieldName).append(this.fieldLabel).append(this.fieldValue)
				.append(this.width).append(this.colspan).append(this.jsonConf)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("formFieldId", this.formFieldId).append("sn", this.sn)
				.append("height", this.height).append("width", this.width)
				.append("colspan", this.colspan)
				.append("jsonConf", this.jsonConf).toString();
	}

}
