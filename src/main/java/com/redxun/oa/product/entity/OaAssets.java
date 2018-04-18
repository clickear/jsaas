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

/**
 * <pre>
 * 描述：OaAssets实体类定义
 * 资产信息
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_ASSETS")
@TableDefine(title = "资产信息")
@JsonIgnoreProperties(value={"oaProductDef","oaAssParameters","oaAssetsBids"})
public class OaAssets extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ASS_ID_")
	protected String assId;
	/* 资产编号 */
	@FieldDefine(title = "资产编号")
	@Column(name = "CODE_")
	@Size(max = 64)
	protected String code;
	/* 资产名称 */
	@FieldDefine(title = "资产名称")
	@Column(name = "NAME_")
	@Size(max = 64)
	protected String name;
	/* 参数JSON */
	@FieldDefine(title = "参数JSON")
	@Column(name = "JSON_")
	@Size(max = 65535)
	protected String json;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESC_")
	@Size(max = 256)
	protected String desc;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	@FieldDefine(title = "产品分类定义")
	@ManyToOne
	@JoinColumn(name = "PROD_DEF_ID_")
	protected com.redxun.oa.product.entity.OaProductDef oaProductDef;

	@FieldDefine(title = "资产参数")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaAssets", fetch = FetchType.LAZY)
	protected java.util.Set<OaAssParameter> oaAssParameters = new java.util.HashSet<OaAssParameter>();
	@FieldDefine(title = "【资产申请】")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaAssets", fetch = FetchType.LAZY)
	protected java.util.Set<OaAssetsBid> oaAssetsBids = new java.util.HashSet<OaAssetsBid>();

	/**
	 * Default Empty Constructor for class OaAssets
	 */
	public OaAssets() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaAssets
	 */
	public OaAssets(String in_assId) {
		this.setAssId(in_assId);
	}

	public com.redxun.oa.product.entity.OaProductDef getOaProductDef() {
		return oaProductDef;
	}

	public void setOaProductDef(
			com.redxun.oa.product.entity.OaProductDef in_oaProductDef) {
		this.oaProductDef = in_oaProductDef;
	}

	public java.util.Set<OaAssParameter> getOaAssParameters() {
		return oaAssParameters;
	}

	public void setOaAssParameters(
			java.util.Set<OaAssParameter> in_oaAssParameters) {
		this.oaAssParameters = in_oaAssParameters;
	}

	public java.util.Set<OaAssetsBid> getOaAssetsBids() {
		return oaAssetsBids;
	}

	public void setOaAssetsBids(java.util.Set<OaAssetsBid> in_oaAssetsBids) {
		this.oaAssetsBids = in_oaAssetsBids;
	}

	/**
	 * 资产ID * @return String
	 */
	public String getAssId() {
		return this.assId;
	}

	/**
	 * 设置 资产ID
	 */
	public void setAssId(String aValue) {
		this.assId = aValue;
	}

	/**
	 * 参数定义ID * @return String
	 */
	public String getProdDefId() {
		return this.getOaProductDef() == null ? null : this.getOaProductDef()
				.getProdDefId();
	}

	/**
	 * 设置 参数定义ID
	 */
	public void setProdDefId(String aValue) {
		if (aValue == null) {
			oaProductDef = null;
		} else if (oaProductDef == null) {
			oaProductDef = new com.redxun.oa.product.entity.OaProductDef(aValue);
		} else {
			oaProductDef.setProdDefId(aValue);
		}
	}

	/**
	 * 资产编号 * @return String
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 设置 资产编号
	 */
	public void setCode(String aValue) {
		this.code = aValue;
	}

	/**
	 * 资产名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 资产名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 参数JSON * @return String
	 */
	public String getJson() {
		return this.json;
	}

	/**
	 * 设置 参数JSON
	 */
	public void setJson(String aValue) {
		this.json = aValue;
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

	/**
	 * 状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.assId;
	}

	@Override
	public Serializable getPkId() {
		return this.assId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.assId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaAssets)) {
			return false;
		}
		OaAssets rhs = (OaAssets) object;
		return new EqualsBuilder().append(this.assId, rhs.assId)
				.append(this.code, rhs.code).append(this.name, rhs.name)
				.append(this.json, rhs.json).append(this.desc, rhs.desc)
				.append(this.status, rhs.status)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.assId)
				.append(this.code).append(this.name).append(this.json)
				.append(this.desc).append(this.status).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("assId", this.assId)
				.append("code", this.code).append("name", this.name)
				.append("json", this.json).append("desc", this.desc)
				.append("status", this.status)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
