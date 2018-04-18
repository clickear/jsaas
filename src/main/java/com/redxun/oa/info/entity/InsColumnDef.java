



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
 * 描述：ins_column_def实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-08-16 11:39:47
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "ins_column_def")
@TableDefine(title = "ins_column_def")
public class InsColumnDef extends BaseTenantEntity {

	@FieldDefine(title = "COL_ID_")
	@Id
	@Column(name = "COL_ID_")
	protected String colId;

	@FieldDefine(title = "NAME_")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "KEY_")
	@Column(name = "KEY_")
	protected String key; 
	@FieldDefine(title = "DATA_URL_")
	@Column(name = "DATA_URL_")
	protected String dataUrl; 
	@FieldDefine(title = "IS_DEFAULT_")
	@Column(name = "IS_DEFAULT_")
	protected String isDefault; 
	@FieldDefine(title = "TEMPLET_")
	@Column(name = "TEMPLET_")
	protected String templet; 
	@FieldDefine(title = "FUNCTION_")
	@Column(name = "FUNCTION_")
	protected String function;
	@FieldDefine(title = "是否是新闻公告")
	@Column(name = "IS_NEWS_")
	protected String isNews;
	
	
	
	
	public InsColumnDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsColumnDef(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.colId;
	}

	@Override
	public Serializable getPkId() {
		return this.colId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.colId = (String) pkId;
	}
	
	public String getColId() {
		return this.colId;
	}

	
	public void setColId(String aValue) {
		this.colId = aValue;
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
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	/**
	 * 返回 DATA_URL_
	 * @return
	 */
	public String getDataUrl() {
		return this.dataUrl;
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
	public void setTemplet(String templet) {
		this.templet = templet;
	}
	
	/**
	 * 返回 TEMPLET_
	 * @return
	 */
	public String getTemplet() {
		return this.templet;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	
	/**
	 * 返回 FUNCTION_
	 * @return
	 */
	public String getFunction() {
		return this.function;
	}		

	public String getIsNews() {
		return isNews;
	}

	public void setIsNews(String isNews) {
		this.isNews = isNews;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsColumnDef)) {
			return false;
		}
		InsColumnDef rhs = (InsColumnDef) object;
		return new EqualsBuilder()
		.append(this.colId, rhs.colId) 
		.append(this.name, rhs.name) 
		.append(this.key, rhs.key) 
		.append(this.dataUrl, rhs.dataUrl) 
		.append(this.isDefault, rhs.isDefault) 
		.append(this.templet, rhs.templet) 
		.append(this.function, rhs.function) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.colId) 
		.append(this.name) 
		.append(this.key) 
		.append(this.dataUrl) 
		.append(this.isDefault) 
		.append(this.templet) 
		.append(this.function) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("colId", this.colId) 
				.append("name", this.name) 
				.append("key", this.key) 
				.append("dataUrl", this.dataUrl) 
				.append("isDefault", this.isDefault) 
				.append("templet", this.templet) 
				.append("function", this.function) 
												.toString();
	}

}



