



package com.redxun.sys.core.entity;

import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.util.EncryptUtil;

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
 * 描述：系统参数实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-06-21 11:22:36
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_PROPERTIES")
@TableDefine(title = "系统参数")
public class SysProperties extends BaseTenantEntity {

	@FieldDefine(title = "参数ID")
	@Id
	@Column(name = "PRO_ID_")
	protected String proId;

	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "别名")
	@Column(name = "ALIAS_")
	protected String alias; 
	@FieldDefine(title = "是否全局")
	@Column(name = "GLOBAL_")
	protected String global; 
	@FieldDefine(title = "是否加密存储")
	@Column(name = "ENCRYPT_")
	protected String encrypt; 
	@FieldDefine(title = "参数值")
	@Column(name = "VALUE_")
	protected String value; 
	@FieldDefine(title = "分类")
	@Column(name = "CATEGORY_")
	protected String category; 
	@FieldDefine(title = "描述")
	@Column(name = "DESCRIPTION_")
	protected String description; 
	
	
	
	
	public SysProperties() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysProperties(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.proId;
	}

	@Override
	public Serializable getPkId() {
		return this.proId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.proId = (String) pkId;
	}
	
	public String getProId() {
		return this.proId;
	}

	
	public void setProId(String aValue) {
		this.proId = aValue;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名称
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
	public void setGlobal(String global) {
		this.global = global;
	}
	
	/**
	 * 返回 是否全局
	 * @return
	 */
	public String getGlobal() {
		return this.global;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	
	/**
	 * 返回 是否加密存储
	 * @return
	 */
	public String getEncrypt() {
		return this.encrypt;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 返回 参数值
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	
	public String getVal() throws Exception{
		if("YES".equals( this.encrypt)){
			return EncryptUtil.decrypt(this.value);
		}
		return this.value;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * 返回 分类
	 * @return
	 */
	public String getCategory() {
		return this.category;
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
		if (!(object instanceof SysProperties)) {
			return false;
		}
		SysProperties rhs = (SysProperties) object;
		return new EqualsBuilder()
		.append(this.proId, rhs.proId) 
		.append(this.name, rhs.name) 
		.append(this.alias, rhs.alias) 
		.append(this.global, rhs.global) 
		.append(this.encrypt, rhs.encrypt) 
		.append(this.value, rhs.value) 
		.append(this.category, rhs.category) 
		.append(this.description, rhs.description) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.proId) 
		.append(this.name) 
		.append(this.alias) 
		.append(this.global) 
		.append(this.encrypt) 
		.append(this.value) 
		.append(this.category) 
		.append(this.description) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("proId", this.proId) 
				.append("name", this.name) 
				.append("alias", this.alias) 
				.append("global", this.global) 
				.append("encrypt", this.encrypt) 
				.append("value", this.value) 
				.append("category", this.category) 
				.append("description", this.description) 
												.toString();
	}

}



