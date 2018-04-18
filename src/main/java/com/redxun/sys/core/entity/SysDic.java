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

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysDic实体类定义
 * TODO: add class/table comments
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_DIC")
@TableDefine(title = "TODO: add class/table comments")
public class SysDic extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DIC_ID_")
	protected String dicId;
	/* 项Key */
	@FieldDefine(title = "项Key", defaultCol = MBoolean.YES)
	@Column(name = "KEY_")
	@Size(max = 64)
	protected String key;
	/* 项名 */
	@FieldDefine(title = "项名", defaultCol = MBoolean.YES)
	@Column(name = "NAME_")
	@Size(max = 64)
	@NotEmpty
	protected String name;
	/* 项值 */
	@FieldDefine(title = "项值", defaultCol = MBoolean.YES)
	@Column(name = "VALUE_")
	@Size(max = 100)
	@NotEmpty
	protected String value;
	/* 描述 */
	@FieldDefine(title = "描述", defaultCol = MBoolean.YES)
	@Column(name = "DESCP_")
	@Size(max = 256)
	protected String descp;
	/* 序号 */
	@FieldDefine(title = "序号", defaultCol = MBoolean.YES)
	@Column(name = "SN_")
	protected Integer sn;
	/* 路径 */
	@FieldDefine(title = "路径", defaultCol = MBoolean.YES)
	@Column(name = "PATH_")
	@Size(max = 256)
	protected String path;
	/* 父ID */
	@FieldDefine(title = "父ID", defaultCol = MBoolean.YES)
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TYPE_ID_")
	protected com.redxun.sys.core.entity.SysTree sysTree;

	/**
	 * Default Empty Constructor for class SysDic
	 */
	public SysDic() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysDic
	 */
	public SysDic(String in_dicId) {
		this.setDicId(in_dicId);
	}

	public com.redxun.sys.core.entity.SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(com.redxun.sys.core.entity.SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	/**
	 * 主键 * @return String
	 */
	public String getDicId() {
		return this.dicId;
	}

	/**
	 * 设置 主键
	 */
	public void setDicId(String aValue) {
		this.dicId = aValue;
	}

	/**
	 * 分类Id * @return String
	 */
	public String getTypeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 分类Id
	 */
	public void setTypeId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new com.redxun.sys.core.entity.SysTree(aValue);
			// sysTree.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 项Key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 项Key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 项名 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 项名
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 项值 * @return String
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 设置 项值
	 */
	public void setValue(String aValue) {
		this.value = aValue;
	}

	/**
	 * 描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

	/**
	 * 路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 设置 路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}

	/**
	 * 父ID * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父ID
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.dicId;
	}

	@Override
	public Serializable getPkId() {
		return this.dicId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.dicId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysDic)) {
			return false;
		}
		SysDic rhs = (SysDic) object;
		return new EqualsBuilder().append(this.dicId, rhs.dicId).append(this.key, rhs.key).append(this.name, rhs.name).append(this.value, rhs.value).append(this.descp, rhs.descp).append(this.sn, rhs.sn).append(this.path, rhs.path).append(this.parentId, rhs.parentId).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.dicId).append(this.key).append(this.name).append(this.value).append(this.descp).append(this.sn).append(this.path).append(this.parentId).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("dicId", this.dicId).append("key", this.key).append("name", this.name).append("value", this.value).append("descp", this.descp).append("sn", this.sn).append("path", this.path).append("parentId", this.parentId).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
