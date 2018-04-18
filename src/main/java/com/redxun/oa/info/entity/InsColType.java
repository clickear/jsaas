package com.redxun.oa.info.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.entity.BaseTenantEntity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：InsColType实体类定义
 * 栏目分类表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_COL_TYPE")
@TableDefine(title = "栏目分类表")
@JsonIgnoreProperties({"insColumns"})
public class InsColType extends BaseTenantEntity {

	/**
	 * 加载模式=URL
	 */
	public final static String LOAD_TYPE_URL="URL";
	/**
	 * 加载模式=模板
	 */
	public final static String LOAD_TYPE_TEMPLATE="TEMPLATE";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "TYPE_ID_")
	protected String typeId;
	/* 栏目名称 */
	@FieldDefine(title = "栏目名称")
	@Column(name = "NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String name;
	/* 栏目Key */
	@FieldDefine(title = "栏目Key")
	@Column(name = "KEY_")
	@Size(max = 50)
	@NotEmpty
	protected String key;
	/* 栏目映射URL */
	@FieldDefine(title = "栏目映射URL")
	@Column(name = "URL_")
	@Size(max = 100)
	protected String url;
	@FieldDefine(title = "栏目更多URL")
	@Column(name = "MORE_URL_")
	@Size(max = 100)
	protected String moreUrl;
	/* 栏目分类描述 */
	@FieldDefine(title = "栏目分类描述")
	@Column(name = "MEMO_")
	@Size(max = 512)
	protected String memo;
	
	@FieldDefine(title = "模板ID")
	@Column(name = "TEMP_ID_")
	@Size(max = 20)
	protected String tempId;
	@FieldDefine(title = "模板名称")
	@Column(name = "TEMP_NAME_")
	@Size(max = 20)
	protected String tempName;
	
	@FieldDefine(title = "加载类型")
	@Column(name = "LOAD_TYPE_")
	@Size(max = 20)
	protected String loadType;
	
	@FieldDefine(title = "图标")
	@Column(name = "ICON_CLS_")
	@Size(max = 20)
	protected String iconCls;
	

	@FieldDefine(title = "信息栏目")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "insColType", fetch = FetchType.LAZY)
	protected java.util.Set<InsColumn> insColumns = new java.util.HashSet<InsColumn>();

	/**
	 * Default Empty Constructor for class InsColType
	 */
	public InsColType() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InsColType
	 */
	public InsColType(String in_typeId) {
		this.setTypeId(in_typeId);
	}

	public java.util.Set<InsColumn> getInsColumns() {
		return insColumns;
	}

	public void setInsColumns(java.util.Set<InsColumn> in_insColumns) {
		this.insColumns = in_insColumns;
	}

	/**
	 * * @return String
	 */
	public String getTypeId() {
		return this.typeId;
	}

	/**
	 * 设置
	 */
	public void setTypeId(String aValue) {
		this.typeId = aValue;
	}

	/**
	 * 栏目名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 栏目名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 栏目Key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 栏目Key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}
	
	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	/**
	 * 栏目映射URL * @return String
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 设置 栏目映射URL
	 */
	public void setUrl(String aValue) {
		this.url = aValue;
	}

	/**
	 * 栏目分类描述 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 栏目分类描述
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.typeId;
	}

	@Override
	public Serializable getPkId() {
		return this.typeId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.typeId = (String) pkId;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsColType)) {
			return false;
		}
		InsColType rhs = (InsColType) object;
		return new EqualsBuilder().append(this.typeId, rhs.typeId)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.url, rhs.url).append(this.memo, rhs.memo)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.typeId)
				.append(this.name).append(this.key).append(this.url)
				.append(this.memo).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("typeId", this.typeId)
				.append("name", this.name).append("key", this.key)
				.append("url", this.url).append("memo", this.memo).toString();
	}

}
