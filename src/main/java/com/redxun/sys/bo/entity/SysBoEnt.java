package com.redxun.sys.bo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.database.util.DbUtil;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.util.StringUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 *  
 * 描述：表单实体对象实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@javax.persistence.Table(name = "SYS_BO_ENTITY")
@TableDefine(title = "表单实体对象")
@XStreamAlias("sysBoEnt")
public class SysBoEnt extends BaseTenantEntity {
	
	public final static String RELATION_TYPE_MAIN="main";
	public final static String RELATION_TYPE_SUB="sub";
	
	public final static String SQL_FK="REF_ID_";
	
	public final static String SQL_PATH="PATH_";
	
	public final static String SQL_FK_STATEMENT="#{fk}";
	
	public final static String SQL_PK="ID_";
	
	public final static String COMPLEX_NAME="_NAME";
	
	public static  final String SUB_PRE="SUB_";
	
	public static  final String FIELD_USER="CREATE_USER_ID_";
	
	public static  final String FIELD_USER_NAME_="CREATE_USER_NAME_";
	
	public static  final String FIELD_TENANT="TENANT_ID_";
	
	public static  final String FIELD_INST="INST_ID_";
	
	public static  final String FIELD_INST_STATUS_="INST_STATUS_";
	
	public static  final String FIELD_GROUP="CREATE_GROUP_ID_";
	
	public static  final String FIELD_GROUP_NAME="CREATE_GROUP_NAME_";
	
	public static  final String FIELD_CREATE_TIME="CREATE_TIME_";
	
	public static  final String FIELD_CREATE_DATE="CREATE_DATE_";
	
	public static  final String FIELD_UPDTIME_TIME="UPDATE_TIME_";
	
	public static  final String FIELD_UPDTIME_DATE="UPDATE_DATE_";
	
	public static  final String FIELD_PARENTID="PARENT_ID_";
	
	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "注释")
	@Column(name = "COMMENT_")
	protected String comment; 
	@FieldDefine(title = "表名")
	@Column(name = "TABLE_NAME_")
	protected String tableName; 
	@FieldDefine(title = "数据源名称")
	@Column(name = "DS_NAME_")
	protected String dsName; 
	@FieldDefine(title = "是否生成物理表")
	@Column(name = "GEN_TABLE_")
	protected String genTable; 
	
	@FieldDefine(title = "分类ID")
	@Column(name = "TREE_ID_")
	protected String treeId; 
	
	@FieldDefine(title = "是否引用实体")
	@Column(name = "IS_REF_")
	protected int isRef=0;
	
	@FieldDefine(title = "关联表单")
	@Column(name = "FORM_ALIAS_")
	protected String formAlias="";
	
	@FieldDefine(title = "扩展JSON")
	@Column(name = "EXT_JSON_")
	protected String extJson="";
	
	
	@FieldDefine(title = "是否树形")
	@Column(name = "TREE_")
	protected String tree ="NO";
	
	@Transient
	protected String boDefId;
	
	/**
	 * 关联关系。
	 */
	@Transient
	protected String relationType=RELATION_TYPE_MAIN;
	
	@Transient
	protected List<SysBoAttr> sysBoAttrs = new ArrayList<SysBoAttr>();
	
	@Transient
	protected List<SysBoEnt> boEntList=new ArrayList<SysBoEnt>();
	
	@Transient
	protected String version=SysBoDef.VERSION_NEW;
	
	@Transient
	protected boolean hasConflict=false;
	
	public SysBoEnt() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysBoEnt(String in_id) {
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
	
	public String getBoDefId() {
		return boDefId;
	}

	public void setBoDefId(String boDefId) {
		this.boDefId = boDefId;
	}

	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * 返回 注释
	 * @return
	 */
	public String getComment() {
		return this.comment;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 返回 表名
	 * @return
	 */
	public String getTableName() {
		if(StringUtil.isEmpty(this.tableName)){
			String tbName=  DbUtil.getTablePre() + this.name;
			return tbName;
		}
		return this.tableName;
	}
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	/**
	 * 返回 数据源名称
	 * @return
	 */
	public String getDsName() {
		return this.dsName;
	}
	public void setGenTable(String genTable) {
		this.genTable = genTable;
	}
	
	/**
	 * 返回 是否生成物理表
	 * @return
	 */
	public String getGenTable() {
		return this.genTable;
	}
	
	public List<SysBoAttr> getSysBoAttrs() {
		return sysBoAttrs;
	}

	public void setSysBoAttrs(List<SysBoAttr> in_sysBoAttr) {
		this.sysBoAttrs = in_sysBoAttr;
	}
	
	
		

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public List<SysBoEnt> getBoEntList() {
		return boEntList;
	}

	public void setBoEntList(List<SysBoEnt> boEntList) {
		this.boEntList = boEntList;
	}
	
	public void clearSubBoEnt(){
		this.boEntList.clear();
	}
	
	/**
	 * 添加实体。
	 * @param boEnt
	 */
	public void addBoEnt(SysBoEnt boEnt){
		boEnt.setRelationType(RELATION_TYPE_SUB);
		this.boEntList.add(boEnt);
	}
	
	public void addBoAttr(SysBoAttr boAttr){
		//相同属性。
		hasEqualName(boAttr);
		this.sysBoAttrs.add(boAttr);
	}
	
	private void hasEqualName(SysBoAttr boAttr){
		String name=boAttr.getName();
		for(SysBoAttr attr:this.sysBoAttrs){
			if(name.equalsIgnoreCase(attr.getName())){
				boAttr.setContain(true);
				//设置有字段冲突。
				this.setHasConflict(true);
				return ;
			}
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public boolean isHasConflict() {
		return hasConflict;
	}

	public void setHasConflict(boolean hasConflict) {
		this.hasConflict = hasConflict;
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

	public String getExtJson() {
		return extJson;
	}

	public void setExtJson(String extJson) {
		this.extJson = extJson;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysBoEnt)) {
			return false;
		}
		SysBoEnt rhs = (SysBoEnt) object;
		return new EqualsBuilder()
		.append(this.name, rhs.name) 
		.append(this.tableName, rhs.tableName) 
		.append(this.dsName, rhs.dsName) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("comment", this.comment) 
				.append("tableName", this.tableName) 
				.append("dsName", this.dsName) 
				.append("genTable", this.genTable) 
												.toString();
	}

}



