



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
 * 描述：人员属性值实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-12-14 14:09:43
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "OS_ATTRIBUTE_VALUE")
@TableDefine(title = "人员属性值")
public class OsAttributeValue extends BaseTenantEntity {

	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "参数值")
	@Column(name = "VALUE_")
	protected String value; 
	@FieldDefine(title = "目标ID")
	@Column(name = "TARGET_ID_")
	protected String targetId; 
	@FieldDefine(title = "目标ID")
	@Column(name = "ATTRIBUTE_ID_")
	protected String attributeId; 
	
	
	
	
	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public OsAttributeValue() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public OsAttributeValue(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.id;
	}

	@Override
	public Serializable getPkId() {
		return this.id;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.id = (String) pkId;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setId(String aValue) {
		this.id = aValue;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 返回 参数值
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	/**
	 * 返回 目标ID
	 * @return
	 */
	public String getTargetId() {
		return this.targetId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsAttributeValue)) {
			return false;
		}
		OsAttributeValue rhs = (OsAttributeValue) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.value, rhs.value) 
		.append(this.targetId, rhs.targetId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.value) 
		.append(this.targetId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("value", this.value) 
				.append("targetId", this.targetId) 
												.toString();
	}

}



