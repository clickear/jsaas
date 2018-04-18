



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
 * 描述：机构类型授权菜单实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-12-19 11:00:46
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_INST_TYPE_MENU")
@TableDefine(title = "机构类型授权菜单")
public class SysInstTypeMenu extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "机构类型ID")
	@Column(name = "INST_TYPE_ID_")
	protected String instTypeId; 
	@FieldDefine(title = "子系统ID")
	@Column(name = "SYS_ID_")
	protected String sysId; 
	@FieldDefine(title = "菜单ID")
	@Column(name = "MENU_ID_")
	protected String menuId; 
	
	
	
	
	public SysInstTypeMenu() {
	}
	
	public SysInstTypeMenu(String pkId,String typeId,String menuId,String subSysId) {
		this.id = pkId;
		this.instTypeId = typeId;
		this.menuId = menuId;
		this.sysId = subSysId;
	}
	

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysInstTypeMenu(String in_id) {
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
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
	/**
	 * 返回 子系统ID
	 * @return
	 */
	public String getSysId() {
		return this.sysId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	/**
	 * 返回 菜单ID
	 * @return
	 */
	public String getMenuId() {
		return this.menuId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysInstTypeMenu)) {
			return false;
		}
		SysInstTypeMenu rhs = (SysInstTypeMenu) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.instTypeId, rhs.instTypeId) 
		.append(this.sysId, rhs.sysId) 
		.append(this.menuId, rhs.menuId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.instTypeId) 
		.append(this.sysId) 
		.append(this.menuId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("instTypeId", this.instTypeId) 
				.append("sysId", this.sysId) 
				.append("menuId", this.menuId) 
		.toString();
	}

}



