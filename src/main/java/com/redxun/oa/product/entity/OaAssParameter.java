package com.redxun.oa.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * 描述：OaAssParameter实体类定义
 * 资产参数
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_ASS_PARAMETER")
@TableDefine(title = "资产参数")
@JsonIgnoreProperties(value={"oaAssets","oaProductDefParaKey","oaProductDefParaValue"})
public class OaAssParameter extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "PARA_ID_")
	protected String paraId;
	/* 自定义VALUE名 */
	@FieldDefine(title = "自定义VALUE名")
	@Column(name = "CUSTOM_VALUE_NAME_")
	@Size(max = 255)
	protected String customValueName;
	/* 自定义KEY名 */
	@FieldDefine(title = "自定义KEY名")
	@Column(name = "CUSTOM_KEY_NAME_")
	@Size(max = 255)
	protected String customKeyName;
	@FieldDefine(title = "资产信息")
	@ManyToOne
	@JoinColumn(name = "ASS_ID_")
	protected com.redxun.oa.product.entity.OaAssets oaAssets;
	@FieldDefine(title = "产品定义参数KEY(产品类型)")
	@ManyToOne
	@JoinColumn(name = "KEY_ID_")
	protected com.redxun.oa.product.entity.OaProductDefParaKey oaProductDefParaKey;
	@FieldDefine(title = "产品定义参数VALUE(产品属性)")
	@ManyToOne
	@JoinColumn(name = "VALUE_ID_")
	protected com.redxun.oa.product.entity.OaProductDefParaValue oaProductDefParaValue;

	/**
	 * Default Empty Constructor for class OaAssParameter
	 */
	public OaAssParameter() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaAssParameter
	 */
	public OaAssParameter(String in_paraId) {
		this.setParaId(in_paraId);
	}

	public com.redxun.oa.product.entity.OaAssets getOaAssets() {
		return oaAssets;
	}

	public void setOaAssets(com.redxun.oa.product.entity.OaAssets in_oaAssets) {
		this.oaAssets = in_oaAssets;
	}

	public com.redxun.oa.product.entity.OaProductDefParaKey getOaProductDefParaKey() {
		return oaProductDefParaKey;
	}

	public void setOaProductDefParaKey(
			com.redxun.oa.product.entity.OaProductDefParaKey in_oaProductDefParaKey) {
		this.oaProductDefParaKey = in_oaProductDefParaKey;
	}

	public com.redxun.oa.product.entity.OaProductDefParaValue getOaProductDefParaValue() {
		return oaProductDefParaValue;
	}

	public void setOaProductDefParaValue(
			com.redxun.oa.product.entity.OaProductDefParaValue in_oaProductDefParaValue) {
		this.oaProductDefParaValue = in_oaProductDefParaValue;
	}

	/**
	 * 参数ID * @return String
	 */
	public String getParaId() {
		return this.paraId;
	}

	/**
	 * 设置 参数ID
	 */
	public void setParaId(String aValue) {
		this.paraId = aValue;
	}

	/**
	 * 参数VALUE主键 * @return String
	 */
	public String getValueId() {
		return this.getOaProductDefParaValue() == null ? null : this
				.getOaProductDefParaValue().getValueId();
	}

	/**
	 * 设置 参数VALUE主键
	 */
	public void setValueId(String aValue) {
		if (aValue == null) {
			oaProductDefParaValue = null;
		} else if (oaProductDefParaValue == null) {
			oaProductDefParaValue = new com.redxun.oa.product.entity.OaProductDefParaValue(
					aValue);
		} else {
			oaProductDefParaValue.setValueId(aValue);
		}
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
	 * 资产ID * @return String
	 */
	public String getAssId() {
		return this.getOaAssets() == null ? null : this.getOaAssets()
				.getAssId();
	}

	/**
	 * 设置 资产ID
	 */
	public void setAssId(String aValue) {
		if (aValue == null) {
			oaAssets = null;
		} else if (oaAssets == null) {
			oaAssets = new com.redxun.oa.product.entity.OaAssets(aValue);
		} else {
			oaAssets.setAssId(aValue);
		}
	}

	/**
	 * 自定义VALUE名 * @return String
	 */
	public String getCustomValueName() {
		return this.customValueName;
	}

	/**
	 * 设置 自定义VALUE名
	 */
	public void setCustomValueName(String aValue) {
		this.customValueName = aValue;
	}

	/**
	 * 自定义KEY名 * @return String
	 */
	public String getCustomKeyName() {
		return this.customKeyName;
	}

	/**
	 * 设置 自定义KEY名
	 */
	public void setCustomKeyName(String aValue) {
		this.customKeyName = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.paraId;
	}

	@Override
	public Serializable getPkId() {
		return this.paraId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.paraId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaAssParameter)) {
			return false;
		}
		OaAssParameter rhs = (OaAssParameter) object;
		return new EqualsBuilder().append(this.paraId, rhs.paraId)
				.append(this.customValueName, rhs.customValueName)
				.append(this.customKeyName, rhs.customKeyName)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.paraId)
				.append(this.customValueName).append(this.customKeyName)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("paraId", this.paraId)
				.append("customValueName", this.customValueName)
				.append("customKeyName", this.customKeyName)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
