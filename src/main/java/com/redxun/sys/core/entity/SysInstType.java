package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：机构类型实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-07-10 18:35:31
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_INST_TYPE")
@TableDefine(title = "机构类型")
public class SysInstType extends BaseTenantEntity {
	/**
	 * 机构类型-平台机构=PLATFORM
	 */
	public final static String INST_TYPE_PLATFORM="PLATFORM";

	@FieldDefine(title = "类型")
	@Id
	@Column(name = "TYPE_ID_")
	protected String typeId;

	@FieldDefine(title = "类型编码")
	@Column(name = "TYPE_CODE_")
	protected String typeCode; 
	@FieldDefine(title = "类型名称")
	@Column(name = "TYPE_NAME_")
	protected String typeName; 
	@FieldDefine(title = "是否启用")
	@Column(name = "ENABLED_")
	protected String enabled; 
	@FieldDefine(title = "是否系统缺省")
	@Column(name = "IS_DEFAULT_")
	protected String isDefault; 
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	protected String descp; 
	
	@FieldDefine(title = "后台访问首页", defaultCol = MBoolean.YES)
	@Column(name = "HOME_URL_")
	@Size(max = 200)
	protected String homeUrl;
	
	
	public SysInstType() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysInstType(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.typeId;
	}

	@Override
	public Serializable getPkId() {
		return this.typeId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.typeId = (String) pkId;
	}
	
	public String getTypeId() {
		return this.typeId;
	}

	
	public void setTypeId(String aValue) {
		this.typeId = aValue;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	/**
	 * 返回 类型编码
	 * @return
	 */
	public String getTypeCode() {
		return this.typeCode;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * 返回 类型名称
	 * @return
	 */
	public String getTypeName() {
		return this.typeName;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * 返回 是否启用
	 * @return
	 */
	public String getEnabled() {
		return this.enabled;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * 返回 是否系统缺省
	 * @return
	 */
	public String getIsDefault() {
		return this.isDefault;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescp() {
		return this.descp;
	}
	
	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysInstType)) {
			return false;
		}
		SysInstType rhs = (SysInstType) object;
		return new EqualsBuilder()
		.append(this.typeId, rhs.typeId) 
		.append(this.typeCode, rhs.typeCode) 
		.append(this.typeName, rhs.typeName) 
		.append(this.enabled, rhs.enabled) 
		.append(this.isDefault, rhs.isDefault) 
		.append(this.descp, rhs.descp) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.typeId) 
		.append(this.typeCode) 
		.append(this.typeName) 
		.append(this.enabled) 
		.append(this.isDefault) 
		.append(this.descp) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("typeId", this.typeId) 
				.append("typeCode", this.typeCode) 
				.append("typeName", this.typeName) 
				.append("enabled", this.enabled) 
				.append("isDefault", this.isDefault) 
				.append("descp", this.descp) 
												.toString();
	}

}



