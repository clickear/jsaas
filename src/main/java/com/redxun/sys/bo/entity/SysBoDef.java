



package com.redxun.sys.bo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * <pre>
 *  
 * 描述：BO定义实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_BO_DEFINITION")
@TableDefine(title = "BO定义")
@XStreamAlias("sysBoDef")
public class SysBoDef extends BaseTenantEntity {
	/**
	 * 原来有的现在没有。
	 */
	public static final String VERSION_BASE="base";
	/**
	 * 新增的子表或者属性。
	 */
	public static final String VERSION_NEW="new";
	/**
	 * 字段属性发生了变化或者控件类型发生变化。
	 */
	public static final String VERSION_DIFF="diff";
	
	/**
	 * 直接添加。
	 */
	public static final String GEN_MODE_CREATE="create";
	/**
	 * 从表单创建
	 */
	public static final String GEN_MODE_FORM="form";
	
	public static final String BO_YES="yes";
	
	public static final String BO_NO="no";
	
	

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "别名")
	@Column(name = "ALAIS_")
	protected String alais; 
	@FieldDefine(title = "备注")
	@Column(name = "COMMENT_")
	protected String comment; 
	@FieldDefine(title = "支持数据表")
	@Column(name = "SUPPORT_DB_")
	protected String supportDb; 
	@FieldDefine(title = "创建方式 (form,create)")
	@Column(name = "GEN_MODE_")
	protected String genMode; 
	@FieldDefine(title = "TREE_ID_")
	@Column(name = "TREE_ID_")
	protected String treeId; 
	
	@FieldDefine(title = "表单定义")
	@Column(name = "OPINION_DEF_")
	protected String opinionDef="";
	
	
	
	@FieldDefine(title = "BO关系定义")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sysBoDef", fetch = FetchType.LAZY)
	@XStreamOmitField
	protected java.util.Set<SysBoRelation> sysBoRelations = new java.util.HashSet<SysBoRelation>();
	
	@Transient
	protected SysBoEnt SysBoEnt;
	
	
	
	public SysBoDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysBoDef(String in_id) {
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
	public void setAlais(String alais) {
		this.alais = alais;
	}
	
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlais() {
		return this.alais;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * 返回 备注
	 * @return
	 */
	public String getComment() {
		return this.comment;
	}
	public void setSupportDb(String supportDb) {
		this.supportDb = supportDb;
	}
	
	/**
	 * 返回 支持数据表
	 * @return
	 */
	public String getSupportDb() {
		return this.supportDb;
	}
	public void setGenMode(String genMode) {
		this.genMode = genMode;
	}
	
	/**
	 * 返回 创建方式 (form,create)
	 * @return
	 */
	public String getGenMode() {
		return this.genMode;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	
	/**
	 * 返回 TREE_ID_
	 * @return
	 */
	public String getTreeId() {
		return this.treeId;
	}
	
	
	public java.util.Set<SysBoRelation> getSysBoRelation() {
		return sysBoRelations;
	}

	public void setSysBoRelation(java.util.Set<SysBoRelation> in_sysBoRelation) {
		this.sysBoRelations = in_sysBoRelation;
	}
	

	public SysBoEnt getSysBoEnt() {
		return SysBoEnt;
	}

	public void setSysBoEnt(SysBoEnt sysBoEnt) {
		SysBoEnt = sysBoEnt;
	}

	public String getOpinionDef() {
		return opinionDef;
	}

	public void setOpinionDef(String opinionDef) {
		this.opinionDef = opinionDef;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysBoDef)) {
			return false;
		}
		SysBoDef rhs = (SysBoDef) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.alais, rhs.alais) 
		.append(this.comment, rhs.comment) 
		.append(this.supportDb, rhs.supportDb) 
		.append(this.genMode, rhs.genMode) 
		.append(this.treeId, rhs.treeId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.alais) 
		.append(this.comment) 
		.append(this.supportDb) 
		.append(this.genMode) 
		.append(this.treeId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("alais", this.alais) 
				.append("comment", this.comment) 
				.append("supportDb", this.supportDb) 
				.append("genMode", this.genMode) 
				.append("treeId", this.treeId) 
				.toString();
	}

}



