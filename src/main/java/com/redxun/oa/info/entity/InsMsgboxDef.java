



package com.redxun.oa.info.entity;

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
 * 描述：栏目消息盒子表实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-09-01 11:35:24
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "INS_MSGBOX_DEF")
@TableDefine(title = "栏目消息盒子表")
public class InsMsgboxDef extends BaseTenantEntity {

	@FieldDefine(title = "BOX_ID_")
	@Id
	@Column(name = "BOX_ID_")
	protected String boxId;

	@FieldDefine(title = "COL_ID_")
	@Column(name = "COL_ID_")
	protected String colId; 
	@FieldDefine(title = "KEY_")
	@Column(name = "KEY_")
	protected String key; 
	@FieldDefine(title = "NAME_")
	@Column(name = "NAME_")
	protected String name; 
	
	
	
	
	public InsMsgboxDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsMsgboxDef(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.boxId;
	}

	@Override
	public Serializable getPkId() {
		return this.boxId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.boxId = (String) pkId;
	}
	
	public String getBoxId() {
		return this.boxId;
	}

	
	public void setBoxId(String aValue) {
		this.boxId = aValue;
	}
	
	public void setColId(String colId) {
		this.colId = colId;
	}
	
	/**
	 * 返回 COL_ID_
	 * @return
	 */
	public String getColId() {
		return this.colId;
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
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 NAME_
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsMsgboxDef)) {
			return false;
		}
		InsMsgboxDef rhs = (InsMsgboxDef) object;
		return new EqualsBuilder()
		.append(this.boxId, rhs.boxId) 
		.append(this.colId, rhs.colId) 
		.append(this.key, rhs.key) 
		.append(this.name, rhs.name) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.boxId) 
		.append(this.colId) 
		.append(this.key) 
		.append(this.name) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("boxId", this.boxId) 
				.append("colId", this.colId) 
				.append("key", this.key) 
				.append("name", this.name) 
												.toString();
	}

}



