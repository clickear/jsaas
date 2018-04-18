
package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：OFFICE附件实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2018-01-15 15:34:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_OFFICE")
@TableDefine(title = "OFFICE附件")
public class SysOffice extends BaseTenantEntity {
	
	public static String SUPPORT_VER="YES";
	
	public static String NOT_SUPPORT_VER="NO";

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "NAME_")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "SUPPORT_VERSION_")
	@Column(name = "SUPPORT_VERSION_")
	protected String supportVersion; 
	@FieldDefine(title = "VERSION_")
	@Column(name = "VERSION_")
	protected Integer version; 
	
	
	
	
	
	
	public SysOffice() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysOffice(String in_id) {
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
	public void setSupportVersion(String supportVersion) {
		this.supportVersion = supportVersion;
	}
	
	/**
	 * 返回 SUPPORT_VERSION_
	 * @return
	 */
	public String getSupportVersion() {
		return this.supportVersion;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	/**
	 * 返回 VERSION_
	 * @return
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysOffice)) {
			return false;
		}
		SysOffice rhs = (SysOffice) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.supportVersion, rhs.supportVersion) 
		.append(this.version, rhs.version) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.supportVersion) 
		.append(this.version) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("supportVersion", this.supportVersion) 
				.append("version", this.version) 
												.toString();
	}

}



