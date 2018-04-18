



package com.redxun.oa.article.entity;

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
 * 描述：项目实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-09-29 14:38:27
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "PRO_ITEM")
@TableDefine(title = "项目")
public class ProItem extends BaseTenantEntity {

	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID")
	protected String ID;

	@FieldDefine(title = "项目名")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "描述")
	@Column(name = "DESC_")
	protected String desc; 
	@FieldDefine(title = "版本")
	@Column(name = "VERSION_")
	protected String version; 
	@FieldDefine(title = "文档生成路径")
	@Column(name = "GEN_SRC_")
	protected String genSrc; 
	
	
	
	
	public ProItem() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public ProItem(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.ID;
	}

	@Override
	public Serializable getPkId() {
		return this.ID;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.ID = (String) pkId;
	}
	
	public String getID() {
		return this.ID;
	}

	
	public void setID(String aValue) {
		this.ID = aValue;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 项目名
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDesc() {
		return this.desc;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * 返回 版本
	 * @return
	 */
	public String getVersion() {
		return this.version;
	}
	public void setGenSrc(String genSrc) {
		this.genSrc = genSrc;
	}
	
	/**
	 * 返回 文档生成路径
	 * @return
	 */
	public String getGenSrc() {
		return this.genSrc;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProItem)) {
			return false;
		}
		ProItem rhs = (ProItem) object;
		return new EqualsBuilder()
		.append(this.ID, rhs.ID) 
		.append(this.name, rhs.name) 
		.append(this.desc, rhs.desc) 
		.append(this.version, rhs.version) 
		.append(this.genSrc, rhs.genSrc) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.ID) 
		.append(this.name) 
		.append(this.desc) 
		.append(this.version) 
		.append(this.genSrc) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("ID", this.ID) 
				.append("name", this.name) 
				.append("desc", this.desc) 
				.append("version", this.version) 
				.append("genSrc", this.genSrc) 
												.toString();
	}

}



