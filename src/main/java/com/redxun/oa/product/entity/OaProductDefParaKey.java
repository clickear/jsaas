package com.redxun.oa.product.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.sys.core.entity.SysTree;

/**
 * <pre>
 * 描述：OaProductDefParaKey实体类定义
 * 产品定义参数KEY(产品类型)
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PRODUCT_DEF_PARA_KEY")
@TableDefine(title = "产品定义参数KEY(产品类型)")
@JsonIgnoreProperties(value={"oaAssParameters","oaProductDefParas","oaProductDefParaValues","sysTree"})
public class OaProductDefParaKey extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 字符串类型=String
	 */
	public final static String DATA_TYPE_STRING="String";
	/**
	 * 数据类型=Number
	 */
	public final static String DATA_TYPE_NUMBER="Number";
	/**
	 * 日期类型=Date
	 */
	public final static String DATA_TYPE_DATE="Date";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "KEY_ID_")
	protected String keyId;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 64)
	protected String name;
	/* 类型(radio-list checkbox-list textbox number date textarea) */
	@FieldDefine(title = "类型")
	@Column(name = "CONTROL_TYPE_")
	@Size(max = 64)
	protected String controlType;
	/* 数据类型(string number date 大文本) */
	@FieldDefine(title = "数据类型")
	@Column(name = "DATA_TYPE_")
	@Size(max = 20)
	protected String dataType;
	/* 是否唯一属性 */
	@FieldDefine(title = "是否唯一属性")
	@Column(name = "IS_UNIQUE_")
	protected Integer isUnique;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESC_")
	@Size(max = 256)
	protected String desc;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;

	@FieldDefine(title = "资产参数")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDefParaKey", fetch = FetchType.LAZY)
	protected java.util.Set<OaAssParameter> oaAssParameters = new java.util.HashSet<OaAssParameter>();
	@FieldDefine(title = "产品定义参数表")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDefParaKey", fetch = FetchType.LAZY)
	protected java.util.Set<OaProductDefPara> oaProductDefParas = new java.util.HashSet<OaProductDefPara>();
	@FieldDefine(title = "产品定义参数VALUE(产品属性)")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDefParaKey", fetch = FetchType.LAZY)
	protected java.util.Set<OaProductDefParaValue> oaProductDefParaValues = new java.util.HashSet<OaProductDefParaValue>();

	/**
	 * Default Empty Constructor for class OaProductDefParaKey
	 */
	public OaProductDefParaKey() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaProductDefParaKey
	 */
	public OaProductDefParaKey(String in_keyId) {
		this.setKeyId(in_keyId);
	}

	public SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	public java.util.Set<OaAssParameter> getOaAssParameters() {
		return oaAssParameters;
	}

	public void setOaAssParameters(
			java.util.Set<OaAssParameter> in_oaAssParameters) {
		this.oaAssParameters = in_oaAssParameters;
	}

	public java.util.Set<OaProductDefPara> getOaProductDefParas() {
		return oaProductDefParas;
	}

	public void setOaProductDefParas(
			java.util.Set<OaProductDefPara> in_oaProductDefParas) {
		this.oaProductDefParas = in_oaProductDefParas;
	}

	public java.util.Set<OaProductDefParaValue> getOaProductDefParaValues() {
		return oaProductDefParaValues;
	}

	public void setOaProductDefParaValues(
			java.util.Set<OaProductDefParaValue> in_oaProductDefParaValues) {
		this.oaProductDefParaValues = in_oaProductDefParaValues;
	}

	/**
	 * 参数KEY主键 * @return String
	 */
	public String getKeyId() {
		return this.keyId;
	}

	/**
	 * 设置 参数KEY主键
	 */
	public void setKeyId(String aValue) {
		this.keyId = aValue;
	}

	/**
	 * 分类Id * @return String
	 */
	public String getTreeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 分类Id
	 */
	public void setTreeId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 类型(radio-list checkbox-list textbox number date textarea) * @return
	 * String
	 */
	public String getControlType() {
		return this.controlType;
	}

	/**
	 * 设置 类型(radio-list checkbox-list textbox number date textarea)
	 */
	public void setControlType(String aValue) {
		this.controlType = aValue;
	}

	/**
	 * 数据类型(string number date 大文本) * @return String
	 */
	public String getDataType() {
		return this.dataType;
	}

	/**
	 * 设置 数据类型(string number date 大文本)
	 */
	public void setDataType(String aValue) {
		this.dataType = aValue;
	}

	/**
	 * 是否唯一属性 * @return Integer
	 */
	public Integer getIsUnique() {
		return this.isUnique;
	}

	/**
	 * 设置 是否唯一属性
	 */
	public void setIsUnique(Integer aValue) {
		this.isUnique = aValue;
	}

	/**
	 * 描述 * @return String
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 设置 描述
	 */
	public void setDesc(String aValue) {
		this.desc = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.keyId;
	}

	@Override
	public Serializable getPkId() {
		return this.keyId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.keyId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaProductDefParaKey)) {
			return false;
		}
		OaProductDefParaKey rhs = (OaProductDefParaKey) object;
		return new EqualsBuilder().append(this.keyId, rhs.keyId)
				.append(this.name, rhs.name)
				.append(this.controlType, rhs.controlType)
				.append(this.dataType, rhs.dataType)
				.append(this.isUnique, rhs.isUnique)
				.append(this.desc, rhs.desc)
				.append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.keyId)
				.append(this.name).append(this.controlType)
				.append(this.dataType).append(this.isUnique).append(this.desc)
				.append(this.createBy).append(this.createTime).append(this.tenantId)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("keyId", this.keyId)
				.append("name", this.name)
				.append("controlType", this.controlType)
				.append("dataType", this.dataType)
				.append("isUnique", this.isUnique).append("desc", this.desc)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
