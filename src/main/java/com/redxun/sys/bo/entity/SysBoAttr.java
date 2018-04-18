



package com.redxun.sys.bo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.database.util.DbUtil;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.util.StringUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * <pre>
 *  
 * 描述：BO属性表实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2017-02-15 15:02:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_BO_ATTR")
@TableDefine(title = "BO属性表")
@XStreamAlias("sysBoAttr")
public class SysBoAttr extends BaseTenantEntity implements Cloneable{

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "字段名")
	@Column(name = "FIELD_NAME_")
	protected String fieldName; 
	@FieldDefine(title = "备注")
	@Column(name = "COMMENT_")
	protected String comment; 
	@FieldDefine(title = "字段类型")
	@Column(name = "DATA_TYPE_")
	protected String dataType; 
	@FieldDefine(title = "长度")
	@Column(name = "LENGTH_")
	protected Integer length; 
	@FieldDefine(title = "精度")
	@Column(name = "DECIMAL_LENGTH_")
	protected Integer decimalLength; 
	@FieldDefine(title = "控件类型")
	@Column(name = "CONTROL_")
	protected String control; 
	@FieldDefine(title = "扩展信息")
	@Column(name = "EXT_JSON_")
	protected String extJson; 
	
	/**
	 * 是否为单值属性,只存在一个字段。
	 */
	protected int isSingle=1;
	
	
	@FieldDefine(title = "表单实体对象")
	@ManyToOne
	@JoinColumn(name = "ENT_ID_")
	@XStreamOmitField
	protected  com.redxun.sys.bo.entity.SysBoEnt sysBoEnt;	
	
	
	@Transient
	protected String status=SysBoDef.VERSION_NEW;
	//不同的内容
	@Transient
	protected String diffConent="";
	@Transient
	protected JSONObject extJsonObj;
	
	/**
	 * 是否包含，这个只在解析HTML的时候使用。
	 */
	@Transient
	protected boolean isContain=false;
	
	
	
	public SysBoAttr() {
		
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysBoAttr(String in_id) {
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
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**
	 * 返回 字段名
	 * @return
	 */
	public String getFieldName() {
		if(StringUtil.isEmpty(fieldName)){
			
			String columnPre=DbUtil.getColumnPre();
			return columnPre + this.getName();
		}
		return this.fieldName;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * 返回 备注
	 * @return
	 */
	public String getComment() {
		if(StringUtil.isEmpty(this.comment)){
			return this.name;
		}
		return this.comment;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * 返回 字段类型
	 * @return
	 */
	public String getDataType() {
		return this.dataType;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	/**
	 * 返回 长度
	 * @return
	 */
	public Integer getLength() {
		return this.length;
	}
	public void setDecimalLength(Integer decimalLength) {
		this.decimalLength = decimalLength;
	}
	
	/**
	 * 返回 精度
	 * @return
	 */
	public Integer getDecimalLength() {
		return this.decimalLength;
	}
	public void setControl(String control) {
		this.control = control;
	}
	
	/**
	 * 返回 控件类型
	 * @return
	 */
	public String getControl() {
		return this.control;
	}
	public void setExtJson(String extJson) {
		if(StringUtil.isNotEmpty(extJson)){
			extJsonObj=JSONObject.parseObject(extJson);
		}
		
		this.extJson = extJson;
	}
	
	/**
	 * 返回 扩展信息
	 * @return
	 */
	public String getExtJson() {
		if(StringUtil.isEmpty(this.extJson)){
			return "{}";
		}
		return this.extJson;
	}
	
	
	public boolean getRequired(){
		JSONObject json=JSONObject.parseObject(getExtJson());
		String required=json.getString("required");
		Boolean flag=false;
		if("true".equals(required)){
			flag=true;
		}
		return flag;
	}
	
	public String getPropByName(String name){
		if(extJsonObj==null) return "";
		
		if(extJsonObj.containsKey(name)){
			return  extJsonObj.getString(name);
		}
		
		return "";
	}
	
	public Integer getIntPropByName(String name){
		if(extJsonObj==null) return -1;
		
		if(extJsonObj.containsKey(name)){
			return  extJsonObj.getInteger(name);
		}
		
		return -1;
	}
	
	
	public com.redxun.sys.bo.entity.SysBoEnt getSysBoEnt() {
		return sysBoEnt;
	}

	public void setSysBoEnt(com.redxun.sys.bo.entity.SysBoEnt in_sysBoEnt) {
		this.sysBoEnt = in_sysBoEnt;
	}
	
	/**
	 * 外键 
	 * @return String
	 */
	public String getEntId() {
		return this.getSysBoEnt() == null ? null : this.getSysBoEnt().getId();
	}

	/**
	 * 设置 外键
	 */
	public void setEntId(String aValue) {
		if (aValue == null) {
			sysBoEnt = null;
		} else if (sysBoEnt == null) {
			sysBoEnt = new com.redxun.sys.bo.entity.SysBoEnt(aValue);
		} else {
			sysBoEnt.setId(aValue);
		}
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDiffConent() {
		return diffConent;
	}

	public void setDiffConent(String diffConent) {
		this.diffConent = diffConent;
	}

	

	public int getIsSingle() {
		return isSingle;
	}
	
	public boolean single() {
		return isSingle==1;
	}

	public void setIsSingle(int isSingle) {
		this.isSingle = isSingle;
	}

	public boolean isContain() {
		return isContain;
	}

	public void setContain(boolean isContain) {
		this.isContain = isContain;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysBoAttr)) {
			return false;
		}
		SysBoAttr rhs = (SysBoAttr) object;
		boolean rtn= new EqualsBuilder()
		.append(this.name.toLowerCase(), rhs.name.toLowerCase()) 
		.append(this.dataType, rhs.dataType)
		.append(this.control, rhs.control)
		.isEquals();
		if(!rtn) return rtn;
		
		if(com.redxun.core.database.api.model.Column.COLUMN_TYPE_VARCHAR.equals(this.dataType)){
			return this.length.equals( rhs.length);
		}
		
		if(com.redxun.core.database.api.model.Column.COLUMN_TYPE_NUMBER.equals(this.dataType)){
			return this.length.equals(rhs.length) && this.decimalLength.equals(rhs.decimalLength);
		}
		
		return true;
		
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.fieldName) 
		.append(this.comment) 
		.append(this.dataType) 
		.append(this.length) 
		.append(this.decimalLength) 
		.append(this.control) 
		.append(this.extJson) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
						.append("name", this.name) 
				.append("fieldName", this.fieldName) 
				.append("comment", this.comment) 
				.append("dataType", this.dataType) 
				.append("length", this.length) 
				.append("decimalLength", this.decimalLength) 
				.append("control", this.control) 
				.append("extJson", this.extJson) 
												.toString();
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}



