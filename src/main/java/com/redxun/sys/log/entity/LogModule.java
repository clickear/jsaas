



package com.redxun.sys.log.entity;

import com.redxun.core.entity.BaseEntity;
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
 * 描述：日志模块实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-09-21 14:38:42
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "LOG_MODULE")
@TableDefine(title = "日志模块")
public class LogModule extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "模块")
	@Column(name = "MODULE_")
	protected String module; 
	@FieldDefine(title = "子模块")
	@Column(name = "SUB_MODULE")
	protected String subModule; 
	@FieldDefine(title = "启用")
	@Column(name = "ENABLE_")
	protected String enable; 
	
	
	
	
	public LogModule() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public LogModule(String in_id) {
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
	
	public void setModule(String module) {
		this.module = module;
	}
	
	/**
	 * 返回 模块
	 * @return
	 */
	public String getModule() {
		return this.module;
	}
	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}
	
	/**
	 * 返回 子模块
	 * @return
	 */
	public String getSubModule() {
		return this.subModule;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	/**
	 * 返回 启用
	 * @return
	 */
	public String getEnable() {
		return this.enable;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof LogModule)) {
			return false;
		}
		LogModule rhs = (LogModule) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.module, rhs.module) 
		.append(this.subModule, rhs.subModule) 
		.append(this.enable, rhs.enable) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.module) 
		.append(this.subModule) 
		.append(this.enable) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("module", this.module) 
				.append("subModule", this.subModule) 
				.append("enable", this.enable) 
		.toString();
	}

}



