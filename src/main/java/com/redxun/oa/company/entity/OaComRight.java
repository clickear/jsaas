package com.redxun.oa.company.entity;

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
 * 描述：OaComRight实体类定义
 * TODO: add class/table comments
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_COM_RIGHT")
@TableDefine(title = "公司通讯录权限")
@JsonIgnoreProperties(value={"oaComBook"})
public class OaComRight extends BaseTenantEntity {
	
	public final static String IDENTITYTYPE_USER="USER";
	public final static String IDENTITYTYPE_GROUP="GROUP";	
	public final static String IDENTITYTYPE_ALL="ALL";
	
	public final static String IDENTITYID_ALL="ALL";

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "RIGHT_ID_")
	protected String rightId;
	/*
	 * 授权类型 USER=用户 GROUP=用户组
	 */
	@FieldDefine(title = "授权类型")
	@Column(name = "IDENTITY_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String identityType;
	/* 用户或组ID */
	@FieldDefine(title = "用户或组ID")
	@Column(name = "IDENTITY_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String identityId;
	@FieldDefine(title = "联系人")
	@ManyToOne
	@JoinColumn(name = "COMBOOK_ID_")
	protected com.redxun.oa.company.entity.OaComBook oaComBook;

	/**
	 * Default Empty Constructor for class OaComRight
	 */
	public OaComRight() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaComRight
	 */
	public OaComRight(String in_rightId) {
		this.setRightId(in_rightId);
	}

	public com.redxun.oa.company.entity.OaComBook getOaComBook() {
		return oaComBook;
	}

	public void setOaComBook(com.redxun.oa.company.entity.OaComBook in_oaComBook) {
		this.oaComBook = in_oaComBook;
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
	 * 文档ID * @return String
	 */
	public String getCombookId() {
		return this.getOaComBook() == null ? null : this.getOaComBook().getComId();
	}

	/**
	 * 设置 文档ID
	 */
	public void setCombookId(String aValue) {
		if (aValue == null) {
			oaComBook = null;
		} else if (oaComBook == null) {
			oaComBook = new com.redxun.oa.company.entity.OaComBook(aValue);
		} else {
			oaComBook.setComId(aValue);
		}
	}

	/**
	 * 授权类型 USER=用户 GROUP=用户组 * @return String
	 */
	public String getIdentityType() {
		return this.identityType;
	}

	/**
	 * 设置 授权类型 USER=用户 GROUP=用户组
	 */
	public void setIdentityType(String aValue) {
		this.identityType = aValue;
	}

	/**
	 * 用户或组ID * @return String
	 */
	public String getIdentityId() {
		return this.identityId;
	}

	/**
	 * 设置 用户或组ID
	 */
	public void setIdentityId(String aValue) {
		this.identityId = aValue;
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
		if (!(object instanceof OaComRight)) {
			return false;
		}
		OaComRight rhs = (OaComRight) object;
		return new EqualsBuilder().append(this.rightId, rhs.rightId).append(this.identityType, rhs.identityType).append(this.identityId, rhs.identityId).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rightId).append(this.identityType).append(this.identityId).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rightId", this.rightId).append("identityType", this.identityType).append("identityId", this.identityId).append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
