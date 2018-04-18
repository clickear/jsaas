package com.redxun.offdoc.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
 * 描述：OdDocFlow实体类定义
 * 收发文流程方案
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:00:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OD_DOC_FLOW")
@TableDefine(title = "收发文流程方案")
public class OdDocFlow extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SCHEME_ID_")
	protected String schemeId;
	/* 部门ID */
	@FieldDefine(title = "部门ID")
	@Column(name = "DEP_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String depId;
	/* 发文流程方案ID */
	@FieldDefine(title = "发文流程方案ID")
	@Column(name = "SEND_SOL_ID_")
	@Size(max = 64)
	protected String sendSolId;
	/* 发文流程方案名称 */
	@FieldDefine(title = "发文流程方案名称")
	@Column(name = "SEND_SOL_NAME_")
	@Size(max = 128)
	protected String sendSolName;
	/* 收文流程方案ID */
	@FieldDefine(title = "收文流程方案ID")
	@Column(name = "REC_SOL_ID_")
	@Size(max = 64)
	protected String recSolId;
	/* 收文流程方案名称 */
	@FieldDefine(title = "收文流程方案名称")
	@Column(name = "REC_SOL_NAME_")
	@Size(max = 128)
	protected String recSolName;
	/*部门名*/
	@Transient
	protected String groupName;
	/*父部门*/
	@Transient
	protected String groupParent;

	/**
	 * Default Empty Constructor for class OdDocFlow
	 */
	public OdDocFlow() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OdDocFlow
	 */
	public OdDocFlow(String in_schemeId) {
		this.setSchemeId(in_schemeId);
	}

	/**
	 * 方案ID * @return String
	 */
	public String getSchemeId() {
		return this.schemeId;
	}

	/**
	 * 设置 方案ID
	 */
	public void setSchemeId(String aValue) {
		this.schemeId = aValue;
	}

	/**
	 * 部门ID * @return String
	 */
	public String getDepId() {
		return this.depId;
	}

	/**
	 * 设置 部门ID
	 */
	public void setDepId(String aValue) {
		this.depId = aValue;
	}

	/**
	 * 发文流程方案ID * @return String
	 */
	public String getSendSolId() {
		return this.sendSolId;
	}

	/**
	 * 设置 发文流程方案ID
	 */
	public void setSendSolId(String aValue) {
		this.sendSolId = aValue;
	}

	/**
	 * 发文流程方案名称 * @return String
	 */
	public String getSendSolName() {
		return this.sendSolName;
	}

	/**
	 * 设置 发文流程方案名称
	 */
	public void setSendSolName(String aValue) {
		this.sendSolName = aValue;
	}

	/**
	 * 收文流程方案ID * @return String
	 */
	public String getRecSolId() {
		return this.recSolId;
	}

	/**
	 * 设置 收文流程方案ID
	 */
	public void setRecSolId(String aValue) {
		this.recSolId = aValue;
	}

	/**
	 * 收文流程方案名称 * @return String
	 */
	public String getRecSolName() {
		return this.recSolName;
	}

	/**
	 * 设置 收文流程方案名称
	 */
	public void setRecSolName(String aValue) {
		this.recSolName = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.schemeId;
	}

	@Override
	public Serializable getPkId() {
		return this.schemeId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.schemeId = (String) pkId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupParent() {
		return groupParent;
	}

	public void setGroupParent(String groupParent) {
		this.groupParent = groupParent;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OdDocFlow)) {
			return false;
		}
		OdDocFlow rhs = (OdDocFlow) object;
		return new EqualsBuilder().append(this.schemeId, rhs.schemeId).append(this.depId, rhs.depId).append(this.sendSolId, rhs.sendSolId).append(this.sendSolName, rhs.sendSolName).append(this.recSolId, rhs.recSolId)
				.append(this.recSolName, rhs.recSolName).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.schemeId).append(this.depId).append(this.sendSolId).append(this.sendSolName).append(this.recSolId).append(this.recSolName).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("schemeId", this.schemeId).append("depId", this.depId).append("sendSolId", this.sendSolId).append("sendSolName", this.sendSolName).append("recSolId", this.recSolId)
				.append("recSolName", this.recSolName).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime)
				.toString();
	}

}
