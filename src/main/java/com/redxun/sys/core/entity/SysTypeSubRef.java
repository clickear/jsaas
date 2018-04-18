



package com.redxun.sys.core.entity;

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
 * 描述：机构--子系统关系实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-09-13 15:57:55
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_TYPE_SUB_REF")
@TableDefine(title = "机构--子系统关系")
public class SysTypeSubRef extends BaseTenantEntity {

	@FieldDefine(title = "主键ID")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "机构类型ID")
	@Column(name = "INST_TYPE_ID_")
	protected String instTypeId; 
	@FieldDefine(title = "子系统ID")
	@Column(name = "SUB_SYS_ID_")
	protected String subSysId; 
	
	
	
	
	public SysTypeSubRef() {
	}
	
	public SysTypeSubRef(String pkId,String typeId,String sysId) {
		this.id = pkId;
		this.instTypeId = typeId;
		this.subSysId = sysId;
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysTypeSubRef(String in_id) {
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
	
	public void setInstTypeId(String instTypeId) {
		this.instTypeId = instTypeId;
	}
	
	/**
	 * 返回 机构类型ID
	 * @return
	 */
	public String getInstTypeId() {
		return this.instTypeId;
	}
	public void setSubSysId(String subSysId) {
		this.subSysId = subSysId;
	}
	
	/**
	 * 返回 子系统ID
	 * @return
	 */
	public String getSubSysId() {
		return this.subSysId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysTypeSubRef)) {
			return false;
		}
		SysTypeSubRef rhs = (SysTypeSubRef) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.instTypeId, rhs.instTypeId) 
		.append(this.subSysId, rhs.subSysId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.instTypeId) 
		.append(this.subSysId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("instTypeId", this.instTypeId) 
				.append("subSysId", this.subSysId) 
												.toString();
	}

}



