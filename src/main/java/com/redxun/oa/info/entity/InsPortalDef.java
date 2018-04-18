



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
 * 描述：ins_portal_def实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-08-15 16:07:14
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "ins_portal_def")
@TableDefine(title = "ins_portal_def")
public class InsPortalDef extends BaseTenantEntity {
	
	public final static String KEY_GLOBAL_PERSONAL="GLOBAL-PERSONAL";
	public final static String KEY_PERSONAL="GLOBAL-PERSONAL";

	@FieldDefine(title = "PORT_ID_")
	@Id
	@Column(name = "PORT_ID_")
	protected String portId;

	@FieldDefine(title = "NAME_")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "KEY_")
	@Column(name = "KEY_")
	protected String key; 
	@FieldDefine(title = "IS_DEFAULT_")
	@Column(name = "IS_DEFAULT_")
	protected String isDefault; 
	@FieldDefine(title = "USER_ID_")
	@Column(name = "USER_ID_")
	protected String userId; 
	@FieldDefine(title = "LAYOUT_HTML_")
	@Column(name = "LAYOUT_HTML_")
	protected String layoutHtml;
	@FieldDefine(title = "EDIT_HTML_")
	@Column(name = "EDIT_HTML_")
	protected String editHtml;
	
	//优先级
	@FieldDefine(title = "PRIORITY_")
	@Column(name = "PRIORITY_")
	protected int priority = 0;
	
	
	
	
	public InsPortalDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsPortalDef(String in_id) {
		this.setPkId(in_id);
	}
	
	
	public String getPortId() {
		return this.portId;
	}

	
	public void setPortId(String aValue) {
		this.portId = aValue;
	}
	
		
	public String getEditHtml() {
		return editHtml;
	}

	public void setEditHtml(String editHtml) {
		this.editHtml = editHtml;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * 返回 IS_DEFAULT_
	 * @return
	 */
	public String getIsDefault() {
		return this.isDefault;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 USER_ID_
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	public void setLayoutHtml(String layoutHtml) {
		this.layoutHtml = layoutHtml;
	}
	
	/**
	 * 返回 LAYOUT_HTML_
	 * @return
	 */
	public String getLayoutHtml() {
		return this.layoutHtml;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsPortalDef)) {
			return false;
		}
		InsPortalDef rhs = (InsPortalDef) object;
		return new EqualsBuilder()
		.append(this.portId, rhs.portId) 
		.append(this.name, rhs.name) 
		.append(this.key, rhs.key) 
		.append(this.isDefault, rhs.isDefault) 
		.append(this.userId, rhs.userId) 
		.append(this.layoutHtml, rhs.layoutHtml) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.portId) 
		.append(this.name) 
		.append(this.key) 
		.append(this.isDefault) 
		.append(this.userId) 
		.append(this.layoutHtml) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("protId", this.portId) 
				.append("name", this.name) 
				.append("key", this.key) 
				.append("isDefault", this.isDefault) 
				.append("userId", this.userId) 
				.append("layoutHtml", this.layoutHtml) 
												.toString();
	}

	@Override
	public String getIdentifyLabel() {
		return this.portId;
	}

	@Override
	public Serializable getPkId() {
		return this.getPortId();
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.portId = (String) pkId;
		
	}

}



