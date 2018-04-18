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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;
import com.redxun.sys.core.entity.SysTree;

/**
 * <pre>
 * 描述：OaProductDefParaValue实体类定义
 * 产品定义参数VALUE(产品属性)
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PRODUCT_DEF_PARA_VALUE")
@TableDefine(title = "产品定义参数VALUE(产品属性)")
@JsonIgnoreProperties(value={"oaProductDefParaKey","oaAssParameters","oaProductDefParas","sysTree"})
public class OaProductDefParaValue extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "VALUE_ID_")
	protected String valueId;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 64)
	protected String name;
	/* 数字类型 */
	@FieldDefine(title = "数字类型")
	@Column(name = "NUMBER_")
	protected Double number;
	/* 字符串类型 */
	@FieldDefine(title = "字符串类型")
	@Column(name = "STRING_")
	@Size(max = 20)
	protected String string;
	/* 文本类型 */
	@FieldDefine(title = "文本类型")
	@Column(name = "TEXT_")
	@Size(max = 4000)
	protected String text;
	/* 时间类型 */
	@FieldDefine(title = "时间类型")
	@Column(name = "DATETIME__")
	protected java.util.Date datetime;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESC_")
	@Size(max = 256)
	protected String desc;
	@FieldDefine(title = "产品定义参数KEY(产品类型)")
	@ManyToOne
	@JoinColumn(name = "KEY_ID_")
	protected com.redxun.oa.product.entity.OaProductDefParaKey oaProductDefParaKey;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;

	@FieldDefine(title = "资产参数")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDefParaValue", fetch = FetchType.LAZY)
	protected java.util.Set<OaAssParameter> oaAssParameters = new java.util.HashSet<OaAssParameter>();
	@FieldDefine(title = "产品定义参数表")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDefParaValue", fetch = FetchType.LAZY)
	protected java.util.Set<OaProductDefPara> oaProductDefParas = new java.util.HashSet<OaProductDefPara>();

	/**
	 * Default Empty Constructor for class OaProductDefParaValue
	 */
	public OaProductDefParaValue() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaProductDefParaValue
	 */
	public OaProductDefParaValue(String in_valueId) {
		this.setValueId(in_valueId);
	}

	public com.redxun.oa.product.entity.OaProductDefParaKey getOaProductDefParaKey() {
		return oaProductDefParaKey;
	}

	public void setOaProductDefParaKey(
			com.redxun.oa.product.entity.OaProductDefParaKey in_oaProductDefParaKey) {
		this.oaProductDefParaKey = in_oaProductDefParaKey;
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

	/**
	 * 参数VALUE主键 * @return String
	 */
	public String getValueId() {
		return this.valueId;
	}

	/**
	 * 设置 参数VALUE主键
	 */
	public void setValueId(String aValue) {
		this.valueId = aValue;
	}

	/**
	 * 参数KEY主键 * @return String
	 */
	public String getKeyId() {
		return this.getOaProductDefParaKey() == null ? null : this
				.getOaProductDefParaKey().getKeyId();
	}

	/**
	 * 设置 参数KEY主键
	 */
	public void setKeyId(String aValue) {
		if (aValue == null) {
			oaProductDefParaKey = null;
		} else if (oaProductDefParaKey == null) {
			oaProductDefParaKey = new com.redxun.oa.product.entity.OaProductDefParaKey(
					aValue);
		} else {
			oaProductDefParaKey.setKeyId(aValue);
		}
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
	 * 数字类型 * @return Double
	 */
	public Double getNumber() {
		return this.number;
	}

	/**
	 * 设置 数字类型
	 */
	public void setNumber(Double aValue) {
		this.number = aValue;
	}

	/**
	 * 字符串类型 * @return String
	 */
	public String getString() {
		return this.string;
	}

	/**
	 * 设置 字符串类型
	 */
	public void setString(String aValue) {
		this.string = aValue;
	}

	/**
	 * 文本类型 * @return String
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * 设置 文本类型
	 */
	public void setText(String aValue) {
		this.text = aValue;
	}

	/**
	 * 时间类型 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getDatetime() {
		return this.datetime;
	}

	/**
	 * 设置 时间类型
	 */
	public void setDatetime(java.util.Date aValue) {
		this.datetime = aValue;
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
		return this.valueId;
	}

	@Override
	public Serializable getPkId() {
		return this.valueId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.valueId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaProductDefParaValue)) {
			return false;
		}
		OaProductDefParaValue rhs = (OaProductDefParaValue) object;
		return new EqualsBuilder().append(this.valueId, rhs.valueId)
				.append(this.name, rhs.name).append(this.number, rhs.number)
				.append(this.string, rhs.string).append(this.text, rhs.text)
				.append(this.datetime, rhs.datetime)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.valueId)
				.append(this.name).append(this.number).append(this.string)
				.append(this.text).append(this.datetime).append(this.desc)
				.append(this.createBy).append(this.createTime).append(this.tenantId)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("valueId", this.valueId)
				.append("name", this.name).append("number", this.number)
				.append("string", this.string).append("text", this.text)
				.append("datetime", this.datetime).append("desc", this.desc)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
