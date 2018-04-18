package com.redxun.sys.org.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.WidgetType;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OsDimension实体类定义
 * 组织维度
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "OS_DIMENSION")
@TableDefine(title = "组织维度")
public class OsDimension extends BaseTenantEntity {
	/**
	 * 岗位维度=_POS
	 */
	public static final String DIM_POS="_POS";
	/**
	 * 职务维度=_JOB
	 */
	public static final String DIM_JOB="_JOB";
	
	/**
	 * 职务维度ID=3
	 */
	public static final String DIM_JOB_ID="3";
	/**
	 * 行政维度=_ADMIN
	 */
	public static final String DIM_ADMIN="_ADMIN";
	/**
	 * 行政维度ID=1
	 */
	public static final String DIM_ADMIN_ID="1";
	
	/**
	 * 角色维度=_ROLE
	 */
	public static final String DIM_ROLE="_ROLE";
	
	/**
	 * 角色维度ID=2
	 */
	public static final String DIM_ROLE_ID="2";
	
	/**
	 * 维度下的用户组数据为树型结构=TREE
	 */
	public static final String SHOW_TYPE_TREE="TREE";
	/**
	 * 维度下的用户组数据为平铺结构=FLAT
	 */
	public static final String SHOW_TYPE_FLAT="FLAT";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DIM_ID_")
	protected String dimId;
	/* 维度名称 */
	@FieldDefine(title = "维度名称", defaultCol = MBoolean.YES)
	@Column(name = "NAME_")
	@Size(max = 40)
	@NotEmpty
	protected String name;
	/* 维度业务主键 */
	@FieldDefine(title = "维度业务主键", defaultCol = MBoolean.YES)
	@Column(name = "DIM_KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String dimKey;
	/* 是否组合维度 */
	@FieldDefine(title = "是否组合维度", defaultCol = MBoolean.YES)
	@Column(name = "IS_COMPOSE_")
	@Size(max = 10)
	@NotEmpty
	protected String isCompose;
	/* 是否系统预设维度 */
	@FieldDefine(title = "是否系统预设维度", defaultCol = MBoolean.YES)
	@Column(name = "IS_SYSTEM_")
	@Size(max = 10)
	@NotEmpty
	protected String isSystem;
	/* 状态。ENABLED 已激活；DISABLED 锁定（禁用）；DELETED 已删除 */
	@FieldDefine(title = "状态", defaultCol = MBoolean.YES, widgetType=WidgetType.LOCAL_COMBO)
	@Column(name = "STATUS_")
	@Size(max = 40)
	@NotEmpty
	protected String status;
	/* 排序号 */
	@FieldDefine(title = "排序号", defaultCol = MBoolean.YES)
	@Column(name = "SN_")
	protected Integer sn;
	/* TREE=树型数据。FLAT=平铺数据 */
	@FieldDefine(title = "数据展示类型", defaultCol = MBoolean.YES)
	@Column(name = "SHOW_TYPE_")
	@Size(max = 40)
	@NotEmpty
	protected String showType;
	/**/
	@FieldDefine(title = "是否授权", defaultCol = MBoolean.YES)
	@Column(name = "IS_GRANT_")
	@Size(max = 10)
	protected String isGrant;
	/* 描述 */
	@FieldDefine(title = "描述", defaultCol = MBoolean.YES)
	@Column(name = "DESC_")
	@Size(max = 255)
	protected String desc;
	
	@FieldDefine(title = "等级分类", defaultCol = MBoolean.YES)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "osDimension", fetch = FetchType.LAZY)
	protected java.util.Set<OsRankType> osRankTypes = new java.util.HashSet<OsRankType>();

	@Transient
	protected String iconCls="icon-group";
	/**
	 * Default Empty Constructor for class OsDimension
	 */
	public OsDimension() {
		super();
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * Default Key Fields Constructor for class OsDimension
	 */
	public OsDimension(String in_dimId) {
		this.setDimId(in_dimId);
	}
	
	@JsonBackReference
	public java.util.Set<OsRankType> getOsRankTypes() {
		return osRankTypes;
	}
	@JsonBackReference
	public void setOsRankTypes(java.util.Set<OsRankType> in_osRankTypes) {
		this.osRankTypes = in_osRankTypes;
	}

	/**
	 * 维度ID * @return String
	 */
	public String getDimId() {
		return this.dimId;
	}

	/**
	 * 设置 维度ID
	 */
	public void setDimId(String aValue) {
		this.dimId = aValue;
	}

	/**
	 * 维度名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 维度名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 维度业务主键 * @return String
	 */
	public String getDimKey() {
		return this.dimKey;
	}

	/**
	 * 设置 维度业务主键
	 */
	public void setDimKey(String aValue) {
		this.dimKey = aValue;
	}

	/**
	 * 是否组合维度 * @return String
	 */
	public String getIsCompose() {
		return this.isCompose;
	}

	/**
	 * 设置 是否组合维度
	 */
	public void setIsCompose(String aValue) {
		this.isCompose = aValue;
	}

	/**
	 * 是否系统预设维度 * @return String
	 */
	public String getIsSystem() {
		return this.isSystem;
	}

	/**
	 * 设置 是否系统预设维度
	 */
	public void setIsSystem(String aValue) {
		this.isSystem = aValue;
	}

	/**
	 * 状态。actived 已激活；locked 锁定（禁用）；deleted 已删除 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态。actived 已激活；locked 锁定（禁用）；deleted 已删除
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 排序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 排序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

	/**
	 * tree=树型数据。flat=平铺数据 * @return String
	 */
	public String getShowType() {
		return this.showType;
	}

	/**
	 * 设置 tree=树型数据。flat=平铺数据
	 */
	public void setShowType(String aValue) {
		this.showType = aValue;
	}

	/**
	 * * @return String
	 */
	public String getIsGrant() {
		return this.isGrant;
	}

	/**
	 * 设置
	 */
	public void setIsGrant(String aValue) {
		this.isGrant = aValue;
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
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.dimId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.dimId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsDimension)) {
			return false;
		}
		OsDimension rhs = (OsDimension) object;
		return new EqualsBuilder().append(this.dimId, rhs.dimId)
				.append(this.name, rhs.name).append(this.dimKey, rhs.dimKey)
				.append(this.isCompose, rhs.isCompose)
				.append(this.isSystem, rhs.isSystem)
				.append(this.status, rhs.status).append(this.sn, rhs.sn)
				.append(this.showType, rhs.showType)
				.append(this.isGrant, rhs.isGrant).append(this.desc, rhs.desc)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.dimId)
				.append(this.name).append(this.dimKey).append(this.isCompose)
				.append(this.isSystem).append(this.status).append(this.sn)
				.append(this.showType).append(this.isGrant).append(this.desc)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("dimId", this.dimId)
				.append("name", this.name).append("dimKey", this.dimKey)
				.append("isCompose", this.isCompose)
				.append("isSystem", this.isSystem)
				.append("status", this.status).append("sn", this.sn)
				.append("showType", this.showType)
				.append("isGrant", this.isGrant).append("desc", this.desc)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
