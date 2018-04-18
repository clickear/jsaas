



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
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 *  
 * 描述：数据源定义管理实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2017-02-07 09:03:54
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "sys_datasource_def")
@TableDefine(title = "数据源定义管理")
public class SysDataSource extends BaseEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "数据源名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "别名")
	@Column(name = "ALIAS_")
	protected String alias; 
	@FieldDefine(title = "是否使用")
	@Column(name = "ENABLE_")
	protected String enabled; 
	@FieldDefine(title = "数据源设定")
	@Column(name = "SETTING_")
	protected String setting; 
	@FieldDefine(title = "数据库类型")
	@Column(name = "DB_TYPE_")
	protected String dbType; 
	@FieldDefine(title = "启动时初始化")
	@Column(name = "INIT_ON_START_")
	protected String initOnStart; 
	
	
	
	
	public SysDataSource() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysDataSource(String in_id) {
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
	 * 返回 数据源名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	public void setEnabled(String enable) {
		this.enabled = enable;
	}
	
	/**
	 * 返回 是否使用
	 * @return
	 */
	public String getEnabled() {
		return this.enabled;
	}
	public void setSetting(String setting) {
		this.setting = setting;
	}
	
	/**
	 * 返回 数据源设定
	 * @return
	 */
	public String getSetting() {
		return this.setting;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	/**
	 * 返回 数据库类型
	 * @return
	 */
	public String getDbType() {
		return this.dbType;
	}
	public void setInitOnStart(String initOnStart) {
		this.initOnStart = initOnStart;
	}
	
	/**
	 * 返回 启动时初始化
	 * @return
	 */
	public String getInitOnStart() {
		return this.initOnStart;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysDataSource)) {
			return false;
		}
		SysDataSource rhs = (SysDataSource) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.alias, rhs.alias) 
		.append(this.enabled, rhs.enabled) 
		.append(this.setting, rhs.setting) 
		.append(this.dbType, rhs.dbType) 
		.append(this.initOnStart, rhs.initOnStart) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.alias) 
		.append(this.enabled) 
		.append(this.setting) 
		.append(this.dbType) 
		.append(this.initOnStart) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("alias", this.alias) 
				.append("enable", this.enabled) 
				.append("setting", this.setting) 
				.append("dbType", this.dbType) 
				.append("initOnStart", this.initOnStart) 
										.toString();
	}

}



