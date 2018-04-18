



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
 * 描述：ins_col_new_def实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-08-25 10:08:03
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "ins_col_new_def")
@TableDefine(title = "ins_col_new_def")
public class InsColNewDef extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "COL_ID_")
	@Column(name = "COL_ID_")
	protected String colId; 
	@FieldDefine(title = "NEW_ID_")
	@Column(name = "NEW_ID_")
	protected String newId; 
	
	
	
	
	public InsColNewDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsColNewDef(String in_id) {
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
	public void setNewId(String newId) {
		this.newId = newId;
	}
	
	/**
	 * 返回 NEW_ID_
	 * @return
	 */
	public String getNewId() {
		return this.newId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsColNewDef)) {
			return false;
		}
		InsColNewDef rhs = (InsColNewDef) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.colId, rhs.colId) 
		.append(this.newId, rhs.newId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.colId) 
		.append(this.newId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("colId", this.colId) 
				.append("newId", this.newId) 
		.toString();
	}

}



