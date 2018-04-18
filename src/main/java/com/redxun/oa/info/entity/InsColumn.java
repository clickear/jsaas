package com.redxun.oa.info.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：InsColumn实体类定义
 * 信息栏目
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_COLUMN")
@TableDefine(title = "信息栏目")
public class InsColumn extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "COL_ID_")
	protected String colId;
	@FieldDefine(title = "分类ID")
	@ManyToOne
	@JoinColumn(name = "TYPE_ID_")
	protected com.redxun.oa.info.entity.InsColType insColType;
	/* 栏目名称 */
	@FieldDefine(title = "栏目名称")
	@Column(name = "NAME_")
	@Size(max = 80)
	@NotEmpty
	protected String name;
	/* 栏目Key */
	@FieldDefine(title = "栏目Key")
	@Column(name = "KEY_")
	@Size(max = 50)
	@NotEmpty
	protected String key;
	
	/* 是否启用 */
	@FieldDefine(title = "是否启用")
	@Column(name = "ENABLED_")
	@Size(max = 20)
	@NotEmpty
	protected String enabled;
	/* 每页记录数 */
	@FieldDefine(title = "每页记录数")
	@Column(name = "NUMS_OF_PAGE_")
	protected Integer numsOfPage;
	/* 是否允许关闭 */
	@FieldDefine(title = "是否允许关闭")
	@Column(name = "ALLOW_CLOSE_")
	@Size(max = 20)
	protected String allowClose;
	/* 信息栏目类型 */
	@FieldDefine(title = "信息栏目类型")
	@Column(name = "COL_TYPE_")
	@Size(max = 50)
	protected String colType;
	@FieldDefine(title="外部URL")
	@Column(name="URL_")
	@Size(max= 255)
	protected String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public com.redxun.oa.info.entity.InsColType getInsColType() {
		return insColType;
	}

	public void setInsColType(com.redxun.oa.info.entity.InsColType in_insColType) {
		this.insColType = in_insColType;
	}

	/**
	 * Default Empty Constructor for class InsColumn
	 */
	public InsColumn() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InsColumn
	 */
	public InsColumn(String in_colId) {
		this.setColId(in_colId);
	}
	
	/**
	 * 栏目ID * @return String
	 */
	public String getColId() {
		return this.colId;
	}

	/**
	 * 设置 栏目ID
	 */
	public void setColId(String aValue) {
		this.colId = aValue;
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

	
	/**
	 * 是否启用 * @return String
	 */
	public String getEnabled() {
		return this.enabled;
	}

	/**
	 * 设置 是否启用
	 */
	public void setEnabled(String aValue) {
		this.enabled = aValue;
	}

	/**
	 * 每页记录数 * @return Integer
	 */
	public Integer getNumsOfPage() {
		return this.numsOfPage;
	}

	/**
	 * 设置 每页记录数
	 */
	public void setNumsOfPage(Integer aValue) {
		this.numsOfPage = aValue;
	}

	/**
	 * 是否允许关闭 * @return String
	 */
	public String getAllowClose() {
		return this.allowClose;
	}

	/**
	 * 设置 是否允许关闭
	 */
	public void setAllowClose(String aValue) {
		this.allowClose = aValue;
	}

	/**
	 * 信息栏目类型 * @return String
	 */
	public String getColType() {
		return this.colType;
	}

	/**
	 * 设置 信息栏目类型
	 */
	public void setColType(String aValue) {
		this.colType = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.colId;
	}

	@Override
	public Serializable getPkId() {
		return this.colId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.colId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsColumn)) {
			return false;
		}
		InsColumn rhs = (InsColumn) object;
		return new EqualsBuilder().append(this.colId, rhs.colId).append(this.name, rhs.name).append(this.key, rhs.key).append(this.enabled, rhs.enabled).append(this.numsOfPage, rhs.numsOfPage).append(this.allowClose, rhs.allowClose)
				.append(this.colType, rhs.colType).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.colId).append(this.name).append(this.key).append(this.enabled).append(this.numsOfPage).append(this.allowClose).append(this.colType).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("colId", this.colId).append("name", this.name).append("key", this.key).append("enabled", this.enabled).append("numsOfPage", this.numsOfPage).append("allowClose", this.allowClose)
				.append("colType", this.colType).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
