



package com.redxun.sys.org.entity;

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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 *  
 * 描述：自定义属性实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-12-14 14:02:29
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "OS_CUSTOM_ATTRIBUTE")
@TableDefine(title = "自定义属性")
public class OsCustomAttribute extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID")
	protected String ID;

	@FieldDefine(title = "属性名称")
	@Column(name = "ATTRIBUTE_NAME_")
	protected String attributeName; 
	@FieldDefine(title = "KEY_")
	@Column(name = "KEY_")
	protected String key; 
	@FieldDefine(title = "属性类型")
	@Column(name = "ATTRIBUTE_TYPE_")
	protected String attributeType; 
	@FieldDefine(title = "分类ID_")
	@Column(name = "TREE_ID_")
	protected String treeId; 
	@FieldDefine(title = "控件类型")
	@Column(name = "WIDGET_TYPE_")
	protected String widgetType; 
	@FieldDefine(title = "值来源")
	@Column(name = "VALUE_SOURCE_")
	protected String valueSource; 
	@FieldDefine(title = "维度ID")
	@Column(name = "DIM_ID_")
	protected String dimId; 
	@FieldDefine(title = "数据来源类型")
	@Column(name = "SOURCE_TYPE_")
	protected String sourceType;
	@Transient
	protected String value;

	
	
	
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public OsCustomAttribute() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public OsCustomAttribute(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.ID;
	}

	@Override
	public Serializable getPkId() {
		return this.ID;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.ID = (String) pkId;
	}
	
	public String getID() {
		return this.ID;
	}

	
	public void setID(String aValue) {
		this.ID = aValue;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	/**
	 * 返回 属性名称
	 * @return
	 */
	public String getAttributeName() {
		return this.attributeName;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 返回 KEY_
	 * @return
	 */
	public String getKey() {
		return this.key;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	
	/**
	 * 返回 属性类型
	 * @return
	 */
	public String getAttributeType() {
		return this.attributeType;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	
	/**
	 * 返回 分类ID_
	 * @return
	 */
	public String getTreeId() {
		return this.treeId;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	
	/**
	 * 返回 控件类型
	 * @return
	 */
	public String getWidgetType() {
		return this.widgetType;
	}
	public void setValueSource(String valueSource) {
		this.valueSource = valueSource;
	}
	
	/**
	 * 返回 值来源
	 * @return
	 */
	public String getValueSource() {
		return this.valueSource;
	}
	public void setDimId(String dimId) {
		this.dimId = dimId;
	}
	
	/**
	 * 返回 维度ID
	 * @return
	 */
	public String getDimId() {
		return this.dimId;
	}
	
	
	
		

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsCustomAttribute)) {
			return false;
		}
		OsCustomAttribute rhs = (OsCustomAttribute) object;
		return new EqualsBuilder()
		.append(this.ID, rhs.ID) 
		.append(this.attributeName, rhs.attributeName) 
		.append(this.key, rhs.key) 
		.append(this.attributeType, rhs.attributeType) 
		.append(this.treeId, rhs.treeId) 
		.append(this.widgetType, rhs.widgetType) 
		.append(this.valueSource, rhs.valueSource) 
		.append(this.dimId, rhs.dimId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.ID) 
		.append(this.attributeName) 
		.append(this.key) 
		.append(this.attributeType) 
		.append(this.treeId) 
		.append(this.widgetType) 
		.append(this.valueSource) 
		.append(this.dimId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("ID", this.ID) 
				.append("attributeName", this.attributeName) 
				.append("key", this.key) 
				.append("attributeType", this.attributeType) 
				.append("treeId", this.treeId) 
				.append("widgetType", this.widgetType) 
				.append("valueSource", this.valueSource) 
				.append("dimId", this.dimId) 
												.toString();
	}

}



