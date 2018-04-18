



package com.redxun.oa.info.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：布局权限设置实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-08-28 15:58:17
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "INS_PORTAL_PERMISSION")
@TableDefine(title = "布局权限设置")
public class InsPortalPermission extends BaseTenantEntity {
	
	public static final String TYPE_ALL = "ALL";
	public static final String TYPE_USER = "user";
	public static final String TYPE_GROUP = "group";
	public static final String TYPE_SUBGROUP = "subGroup";

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "LAYOUT_ID_")
	@Column(name = "LAYOUT_ID_")
	protected String layoutId; 
	@FieldDefine(title = "TYPE_")
	@Column(name = "TYPE_")
	protected String type; 
	@FieldDefine(title = "OWNER_ID_")
	@Column(name = "OWNER_ID_")
	protected String ownerId; 
	
	@Transient
	protected String ownerName;
	
	
	public InsPortalPermission() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsPortalPermission(String in_id) {
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


	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}
	
	/**
	 * 返回 LAYOUT_ID_
	 * @return
	 */
	public String getLayoutId() {
		return this.layoutId;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 TYPE_
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	/**
	 * 返回 OWNER_ID_
	 * @return
	 */
	public String getOwnerId() {
		return this.ownerId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsPortalPermission)) {
			return false;
		}
		InsPortalPermission rhs = (InsPortalPermission) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.layoutId, rhs.layoutId) 
		.append(this.type, rhs.type) 
		.append(this.ownerId, rhs.ownerId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.layoutId) 
		.append(this.type) 
		.append(this.ownerId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("layoutId", this.layoutId) 
				.append("type", this.type) 
				.append("ownerId", this.ownerId) 
												.toString();
	}

}



