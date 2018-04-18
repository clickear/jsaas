



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
 * 描述：office印章实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2018-02-01 09:41:38
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_STAMP")
@TableDefine(title = "office印章")
public class SysStamp extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "签章名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "签章用户")
	@Column(name = "SIGN_USER_")
	protected String signUser; 
	@FieldDefine(title = "签章密码")
	@Column(name = "PASSWORD_")
	protected String password; 
	@FieldDefine(title = "印章文件ID")
	@Column(name = "STAMP_ID_")
	protected String stampId; 
	@FieldDefine(title = "描述")
	@Column(name = "DESCRIPTION_")
	protected String description; 
	
	
	
	
	public SysStamp() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysStamp(String in_id) {
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
	 * 返回 签章名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}
	
	/**
	 * 返回 签章用户
	 * @return
	 */
	public String getSignUser() {
		return this.signUser;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 返回 签章密码
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}
	public void setStampId(String stampId) {
		this.stampId = stampId;
	}
	
	/**
	 * 返回 印章文件ID
	 * @return
	 */
	public String getStampId() {
		return this.stampId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysStamp)) {
			return false;
		}
		SysStamp rhs = (SysStamp) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.signUser, rhs.signUser) 
		.append(this.password, rhs.password) 
		.append(this.stampId, rhs.stampId) 
		.append(this.description, rhs.description) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.signUser) 
		.append(this.password) 
		.append(this.stampId) 
		.append(this.description) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("signUser", this.signUser) 
				.append("password", this.password) 
				.append("stampId", this.stampId) 
				.append("description", this.description) 
												.toString();
	}

}



