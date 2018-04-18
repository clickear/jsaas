



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
 * 描述：私有参数实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-06-21 10:30:22
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_PRIVATE_PROPERTIES")
@TableDefine(title = "私有参数")
public class SysPrivateProperties extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "参数ID")
	@Column(name = "PRO_ID_")
	protected String proId; 
	@FieldDefine(title = "私有值")
	@Column(name = "PRI_VALUE_")
	protected String priValue; 
	
	
	
	
	public SysPrivateProperties() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysPrivateProperties(String in_id) {
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
	
	public void setProId(String proId) {
		this.proId = proId;
	}
	
	/**
	 * 返回 参数ID
	 * @return
	 */
	public String getProId() {
		return this.proId;
	}
	public void setPriValue(String priValue) {
		this.priValue = priValue;
	}
	
	/**
	 * 返回 私有值
	 * @return
	 */
	public String getPriValue() {
		return this.priValue;
	}
	
	
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysPrivateProperties)) {
			return false;
		}
		SysPrivateProperties rhs = (SysPrivateProperties) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.proId, rhs.proId) 
		.append(this.priValue, rhs.priValue) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.proId) 
		.append(this.priValue) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("proId", this.proId) 
				.append("priValue", this.priValue) 
												.toString();
	}

}



