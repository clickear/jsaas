



package com.redxun.sys.bo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * 描述：BO关系定义实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_BO_RELATION")
@TableDefine(title = "BO关系定义")
public class SysBoRelation extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;
	@FieldDefine(title = "实体ID")
	@Column(name = "BO_ENTID_")
	protected String boEntid; 
	@FieldDefine(title = "关系类型_(main,onetoone,ontomany)")
	@Column(name = "RELATION_TYPE_")
	protected String relationType; 
	@FieldDefine(title = "BO定义")
	@ManyToOne
	@JoinColumn(name = "BO_DEFID_")
	protected  com.redxun.sys.bo.entity.SysBoDef sysBoDef;	
	
	@FieldDefine(title = "是否引用实体")
	@Column(name = "IS_REF_")
	protected int isRef=0;
	
	@FieldDefine(title = "关联表单")
	@Column(name = "FORM_ALIAS_")
	protected String formAlias="";
	
	
	
	
	public SysBoRelation() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysBoRelation(String in_id) {
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
	
	public void setBoEntid(String boEntid) {
		this.boEntid = boEntid;
	}
	
	/**
	 * 返回 实体ID
	 * @return
	 */
	public String getBoEntid() {
		return this.boEntid;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	/**
	 * 返回 关系类型_(main,onetoone,ontomany)
	 * @return
	 */
	public String getRelationType() {
		return this.relationType;
	}
	
	
	
	public com.redxun.sys.bo.entity.SysBoDef getSysBoDef() {
		return sysBoDef;
	}

	public void setSysBoDef(com.redxun.sys.bo.entity.SysBoDef in_sysBoDef) {
		this.sysBoDef = in_sysBoDef;
	}
	
	/**
	 * 外键 
	 * @return String
	 */
	public String getBoDefid() {
		return this.getSysBoDef() == null ? null : this.getSysBoDef().getId();
	}

	/**
	 * 设置 外键
	 */
	public void setBoDefid(String aValue) {
		if (aValue == null) {
			sysBoDef = null;
		} else if (sysBoDef == null) {
			sysBoDef = new com.redxun.sys.bo.entity.SysBoDef(aValue);
		} else {
			sysBoDef.setId(aValue);
		}
	}
	
		

	public int getIsRef() {
		return isRef;
	}

	public void setIsRef(int isRef) {
		this.isRef = isRef;
	}

	public String getFormAlias() {
		return formAlias;
	}

	public void setFormAlias(String formAlias) {
		this.formAlias = formAlias;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysBoRelation)) {
			return false;
		}
		SysBoRelation rhs = (SysBoRelation) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.boEntid, rhs.boEntid) 
		.append(this.relationType, rhs.relationType) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.boEntid) 
		.append(this.relationType) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
						.append("boEntid", this.boEntid) 
				.append("relationType", this.relationType) 
										.toString();
	}

}



