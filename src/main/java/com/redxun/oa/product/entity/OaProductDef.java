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
 * 描述：OaProductDef实体类定义
 * 产品分类定义
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PRODUCT_DEF")
@TableDefine(title = "产品分类定义")
@JsonIgnoreProperties(value={"sysTree","oaAssetss","oaProductDefParas"})
public class OaProductDef extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "PROD_DEF_ID_")
	protected String prodDefId;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 64)
	protected String name;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESC_")
	@Size(max = 256)
	protected String desc;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;

	@FieldDefine(title = "资产信息")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDef", fetch = FetchType.LAZY)
	protected java.util.Set<OaAssets> oaAssetss = new java.util.HashSet<OaAssets>();
	@FieldDefine(title = "产品定义参数表")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaProductDef", fetch = FetchType.LAZY)
	protected java.util.Set<OaProductDefPara> oaProductDefParas = new java.util.HashSet<OaProductDefPara>();

	/**
	 * Default Empty Constructor for class OaProductDef
	 */
	public OaProductDef() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaProductDef
	 */
	public OaProductDef(String in_prodDefId) {
		this.setProdDefId(in_prodDefId);
	}

	public SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	public java.util.Set<OaAssets> getOaAssetss() {
		return oaAssetss;
	}

	public void setOaAssetss(java.util.Set<OaAssets> in_oaAssetss) {
		this.oaAssetss = in_oaAssetss;
	}

	public java.util.Set<OaProductDefPara> getOaProductDefParas() {
		return oaProductDefParas;
	}

	public void setOaProductDefParas(
			java.util.Set<OaProductDefPara> in_oaProductDefParas) {
		this.oaProductDefParas = in_oaProductDefParas;
	}

	/**
	 * 参数定义ID * @return String
	 */
	public String getProdDefId() {
		return this.prodDefId;
	}

	/**
	 * 设置 参数定义ID
	 */
	public void setProdDefId(String aValue) {
		this.prodDefId = aValue;
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
		return this.prodDefId;
	}

	@Override
	public Serializable getPkId() {
		return this.prodDefId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.prodDefId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaProductDef)) {
			return false;
		}
		OaProductDef rhs = (OaProductDef) object;
		return new EqualsBuilder().append(this.prodDefId, rhs.prodDefId)
				.append(this.name, rhs.name).append(this.desc, rhs.desc)
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
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.prodDefId).append(this.name).append(this.desc)
				.append(this.createBy).append(this.createTime).append(this.tenantId)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("prodDefId", this.prodDefId)
				.append("name", this.name).append("desc", this.desc)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
