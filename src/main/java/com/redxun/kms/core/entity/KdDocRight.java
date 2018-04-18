package com.redxun.kms.core.entity;

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
 * 描述：KdDocRight实体类定义
 * 文档权限
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_RIGHT")
@TableDefine(title = "文档权限")
public class KdDocRight extends BaseTenantEntity {
	
	public final static String RIGHT_READ="READ";
	public final static String RIGHT_EDIT="EDIT";
	public final static String RIGHT_DOWNLOAD="DOWNLOAD";
	public final static String RIGHT_PRINT="PRINT";
	
	public final static String IDENTITYTYPE_USER="USER";
	public final static String IDENTITYTYPE_GROUP="GROUP";	
	public final static String IDENTITYTYPE_ALL="ALL";
	
	public final static String IDENTITYID_ALL="ALL";

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "RIGHT_ID_")
	protected String rightId;
	/* 授权类型 */
	@FieldDefine(title = "授权类型")
	@Column(name = "IDENTITY_TYPE_")
	@Size(max = 64)
	@NotEmpty
	protected String identityType;
	/* 用户和组ID*/
	@FieldDefine(title = "用户和组ID")
	@Column(name = "IDENTITY_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String identityId;
	/*
	 * READ=可读 EDIT=可编辑 PRINT=打印 DOWN_FILE=可下载附件
	 */
	@FieldDefine(title = "权限")
	@Column(name = "RIGHT_")
	@Size(max = 60)
	protected String right;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocRight
	 */
	public KdDocRight() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocRight
	 */
	public KdDocRight(String in_rightId) {
		this.setRightId(in_rightId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
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
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置 文档ID
	 */
	public void setDocId(String aValue) {
		if (aValue == null) {
			kdDoc = null;
		} else if (kdDoc == null) {
			kdDoc = new com.redxun.kms.core.entity.KdDoc(aValue);
		} else {
			kdDoc.setDocId(aValue);
		}
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	/**
	 * READ=可读 EDIT=可编辑 PRINT=打印 DOWN_FILE=可下载附件 * @return String
	 */
	public String getRight() {
		return this.right;
	}

	/**
	 * 设置 READ=可读 EDIT=可编辑 PRINT=打印 DOWN_FILE=可下载附件
	 */
	public void setRight(String aValue) {
		this.right = aValue;
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
		if (!(object instanceof KdDocRight)) {
			return false;
		}
		KdDocRight rhs = (KdDocRight) object;
		return new EqualsBuilder().append(this.rightId, rhs.rightId).append(this.identityId, rhs.identityId).append(this.identityType, rhs.identityType).append(this.right, rhs.right).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rightId).append(this.identityId).append(this.identityType).append(this.right).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rightId", this.rightId).append("identityId",this.identityId).append("identityType",this.identityType).append("right", this.right).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
