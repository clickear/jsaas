package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 * 描述：SysElemRight实体类定义
 * 系统元素权限\r\n表单、组、字段、按钮权限
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_ELEM_RIGHT")
@TableDefine(title = "系统元素权限")
public class SysElemRight extends BaseEntity {

	@FieldDefine(title = "权限ID", group = "基本信息")
	@Id
	@Column(name = "RIGHT_ID_")
	protected String rightId;
	/*
	 * 组件ID 表单ID/组/字段ID/按钮ID
	 */
	@FieldDefine(title = "组件ID", group = "基本信息")
	@Column(name = "COMP_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String compId;
	/*
	 * 组件类型 Form=表单 Group=组 Field=字段 Button=按钮
	 */
	@FieldDefine(title = "组件类型", group = "基本信息")
	@Column(name = "COMP_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String compType;
	/*
	 * 权限类型 ReadOnly=只读 Edit=可用 Hidden=隐藏
	 */
	@FieldDefine(title = "权限类型", group = "基本信息")
	@Column(name = "RIGHT_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String rightType;
	/* 用户标识ID */
	@FieldDefine(title = "用户标识ID", group = "基本信息")
	@Column(name = "IDENTITY_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String identityId;
	/*
	 * 用户=User 用户组=Group
	 */
	@FieldDefine(title = "用户标识类型", group = "基本信息")
	@Column(name = "IDENTITY_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String identityType;

	/**
	 * Default Empty Constructor for class SysElemRight
	 */
	public SysElemRight() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysElemRight
	 */
	public SysElemRight(String in_rightId) {
		this.setRightId(in_rightId);
	}

	/**
	 * 权限ID * @return String
	 */
	public String getRightId() {
		return this.rightId;
	}

	/**
	 * 设置 权限ID
	 */
	public void setRightId(String aValue) {
		this.rightId = aValue;
	}

	/**
	 * 组件ID 表单ID/组/字段ID/按钮ID * @return String
	 */
	public String getCompId() {
		return this.compId;
	}

	/**
	 * 设置 组件ID 表单ID/组/字段ID/按钮ID
	 */
	public void setCompId(String aValue) {
		this.compId = aValue;
	}

	/**
	 * 组件类型 Form=表单 Group=组 Field=字段 Button=按钮
	 * 
	 * @return String
	 */
	public String getCompType() {
		return this.compType;
	}

	/**
	 * 设置 组件类型 Form=表单 Group=组 Field=字段 Button=按钮
	 */
	public void setCompType(String aValue) {
		this.compType = aValue;
	}

	/**
	 * 权限类型 ReadOnly=只读 Edit=可用 Hidden=隐藏 * @return String
	 */
	public String getRightType() {
		return this.rightType;
	}

	/**
	 * 设置 权限类型 ReadOnly=只读 Edit=可用 Hidden=隐藏
	 */
	public void setRightType(String aValue) {
		this.rightType = aValue;
	}

	/**
	 * 用户标识ID * @return String
	 */
	public String getIdentityId() {
		return this.identityId;
	}

	/**
	 * 设置 用户标识ID
	 */
	public void setIdentityId(String aValue) {
		this.identityId = aValue;
	}

	/**
	 * 用户=User 用户组=Group * @return String
	 */
	public String getIdentityType() {
		return this.identityType;
	}

	/**
	 * 设置 用户=User 用户组=Group
	 */
	public void setIdentityType(String aValue) {
		this.identityType = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.rightId;
	}

	@Override
	public Serializable getPkId() {
		return this.rightId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.rightId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysElemRight)) {
			return false;
		}
		SysElemRight rhs = (SysElemRight) object;
		return new EqualsBuilder().append(this.rightId, rhs.rightId)
				.append(this.compId, rhs.compId)
				.append(this.compType, rhs.compType)
				.append(this.rightType, rhs.rightType)
				.append(this.identityId, rhs.identityId)
				.append(this.identityType, rhs.identityType).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rightId)
				.append(this.compId).append(this.compType)
				.append(this.rightType).append(this.identityId)
				.append(this.identityType).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rightId", this.rightId)
				.append("compId", this.compId)
				.append("compType", this.compType)
				.append("rightType", this.rightType)
				.append("identityId", this.identityId)
				.append("identityType", this.identityType).toString();
	}

}
