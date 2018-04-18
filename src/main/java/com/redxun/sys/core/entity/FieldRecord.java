package com.redxun.sys.core.entity;

/**
 * <pre>
 * 描述：字段记录
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年8月22日-上午11:21:07
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class FieldRecord {
	/**
	 * 主键ID
	 */
	private String pkId;
	/**
	 * 字段标签
	 */
	private String fieldLabel;
	/**
	 * 字段名
	 */
	private String fieldName;
	/**
	 * 字段值
	 */
	private Object fieldVal;
	/**
	 * 字段格式化
	 */
	private String format;
	/**
	 * 字段类型
	 */
	private String fieldType;
	
	/**
	 * 对应的实体名称
	 */
	private String entityName;
	/**
	 * 字段的类型
	 */
	private String fieldCat;
	/**
	 * 关系类型
	 */
	private String relationType;
	
	public FieldRecord() {
	
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldVal() {
		return fieldVal;
	}

	public void setFieldVal(Object fieldVal) {
		this.fieldVal = fieldVal;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getFieldCat() {
		return fieldCat;
	}

	public void setFieldCat(String fieldCat) {
		this.fieldCat = fieldCat;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	
}
