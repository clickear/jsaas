package com.redxun.sys.core.entity;

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
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysTemplate实体类定义
 * 系统模板
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_TEMPLATE")
@TableDefine(title = "系统模板")
public class SysTemplate extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "TEMP_ID_")
	protected String tempId;
	/* 模板名称 */
	@FieldDefine(title = "模板名称")
	@Column(name = "NAME_")
	@Size(max = 80)
	@NotEmpty
	protected String name;
	/* 模板KEY */
	@FieldDefine(title = "模板KEY")
	@Column(name = "KEY_")
	@Size(max = 50)
	@NotEmpty
	protected String key;
	
	/* 模板KEY */
	@FieldDefine(title = "分类KEY")
	@Column(name = "CAT_KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String catKey;
	
	/* 是否系统缺省 */
	@FieldDefine(title = "是否系统缺省")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 20)
	@NotEmpty
	protected String isDefault;
	/* 模板内容 */
	@FieldDefine(title = "模板内容")
	@Column(name = "CONTENT_")
	@Size(max = 65535)
	@NotEmpty
	protected String content;
	/* 模板描述 */
	@FieldDefine(title = "模板描述")
	@Column(name = "DESCP")
	@Size(max = 500)
	protected String descp;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected com.redxun.sys.core.entity.SysTree sysTree;

	/**
	 * Default Empty Constructor for class SysTemplate
	 */
	public SysTemplate() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysTemplate
	 */
	public SysTemplate(String in_tempId) {
		this.setTempId(in_tempId);
	}

	public com.redxun.sys.core.entity.SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(com.redxun.sys.core.entity.SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	/**
	 * 模板ID * @return String
	 */
	public String getTempId() {
		return this.tempId;
	}

	/**
	 * 设置 模板ID
	 */
	public void setTempId(String aValue) {
		this.tempId = aValue;
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
			sysTree = new com.redxun.sys.core.entity.SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 模板名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 模板名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 模板KEY * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 模板KEY
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 是否系统缺省 * @return String
	 */
	public String getIsDefault() {
		return this.isDefault;
	}

	/**
	 * 设置 是否系统缺省
	 */
	public void setIsDefault(String aValue) {
		this.isDefault = aValue;
	}

	/**
	 * 模板内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	/**
	 * 设置 模板内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 模板描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 模板描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.tempId;
	}

	@Override
	public Serializable getPkId() {
		return this.tempId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.tempId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysTemplate)) {
			return false;
		}
		SysTemplate rhs = (SysTemplate) object;
		return new EqualsBuilder().append(this.tempId, rhs.tempId)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.isDefault, rhs.isDefault)
				.append(this.content, rhs.content)
				.append(this.descp, rhs.descp)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.tempId)
				.append(this.name).append(this.key).append(this.isDefault)
				.append(this.content).append(this.descp).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("tempId", this.tempId)
				.append("name", this.name).append("key", this.key)
				.append("isDefault", this.isDefault)
				.append("content", this.content).append("descp", this.descp)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
