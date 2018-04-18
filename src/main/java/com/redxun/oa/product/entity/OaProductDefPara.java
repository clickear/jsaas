package com.redxun.oa.product.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OaProductDefPara实体类定义
 * 产品定义参数表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PRODUCT_DEF_PARA")
@TableDefine(title = "产品定义参数表")
@JsonIgnoreProperties(value={"oaProductDefParaKey","oaProductDefParaValue","oaProductDef"})
public class OaProductDefPara extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ID_")
	protected String id;
	@FieldDefine(title = "产品定义参数KEY(产品类型)")
	@ManyToOne
	@JoinColumn(name = "KEY_ID_")
	protected com.redxun.oa.product.entity.OaProductDefParaKey oaProductDefParaKey;
	@FieldDefine(title = "产品定义参数VALUE(产品属性)")
	@ManyToOne
	@JoinColumn(name = "VALUE_ID_")
	protected com.redxun.oa.product.entity.OaProductDefParaValue oaProductDefParaValue;
	@FieldDefine(title = "产品分类定义")
	@ManyToOne
	@JoinColumn(name = "PROD_DEF_ID_")
	protected com.redxun.oa.product.entity.OaProductDef oaProductDef;

	/**
	 * Default Empty Constructor for class OaProductDefPara
	 */
	public OaProductDefPara() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaProductDefPara
	 */
	public OaProductDefPara(String in_id) {
		this.setId(in_id);
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

	public com.redxun.oa.product.entity.OaProductDef getOaProductDef() {
		return oaProductDef;
	}

	public void setOaProductDef(
			com.redxun.oa.product.entity.OaProductDef in_oaProductDef) {
		this.oaProductDef = in_oaProductDef;
	}

	/**
	 * 主键 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 主键
	 */
	public void setId(String aValue) {
		this.id = aValue;
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaProductDefPara)) {
			return false;
		}
		OaProductDefPara rhs = (OaProductDefPara) object;
		return new EqualsBuilder().append(this.id, rhs.id)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
