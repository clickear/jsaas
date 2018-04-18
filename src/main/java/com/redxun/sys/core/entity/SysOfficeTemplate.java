



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
 * 描述：office模板实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2018-01-28 11:11:46
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_OFFICE_TEMPLATE")
@TableDefine(title = "office模板")
public class SysOfficeTemplate extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "类型(normal,red)")
	@Column(name = "TYPE_")
	protected String type; 
	@FieldDefine(title = "文档ID")
	@Column(name = "DOC_ID_")
	protected String docId; 
	@FieldDefine(title = "文件名")
	@Column(name = "DOC_NAME_")
	protected String docName; 
	@FieldDefine(title = "描述")
	@Column(name = "DESCRIPTION_")
	protected String description; 
	
	
	
	
	public SysOfficeTemplate() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysOfficeTemplate(String in_id) {
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
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 类型(normal,red)
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	/**
	 * 返回 文档ID
	 * @return
	 */
	public String getDocId() {
		return this.docId;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	/**
	 * 返回 文件名
	 * @return
	 */
	public String getDocName() {
		return this.docName;
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
		if (!(object instanceof SysOfficeTemplate)) {
			return false;
		}
		SysOfficeTemplate rhs = (SysOfficeTemplate) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.type, rhs.type) 
		.append(this.docId, rhs.docId) 
		.append(this.docName, rhs.docName) 
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
		.append(this.type) 
		.append(this.docId) 
		.append(this.docName) 
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
				.append("type", this.type) 
				.append("docId", this.docId) 
				.append("docName", this.docName) 
				.append("description", this.description) 
												.toString();
	}

}



