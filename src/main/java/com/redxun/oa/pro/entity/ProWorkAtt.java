package com.redxun.oa.pro.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

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
 * 描述：动态关注实体类定义
 * 工作动态关注
 * 构建组：miweb
 * 作者：陈茂昌
 * 日期:2015-12-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_WORK_ATT")
@TableDefine(title = "工作动态关注")
public class ProWorkAtt extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ATT_ID_")
	protected String attId;
	/* 关注人ID */
	@FieldDefine(title = "关注人ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/* 关注时间 */
	@FieldDefine(title = "关注时间")
	@Column(name = "ATT_TIME_")
	protected java.util.Date attTime;
	/* 通知方式 */
	@FieldDefine(title = "通知方式")
	@Column(name = "NOTICE_TYPE_")
	@Size(max = 50)
	@NotEmpty
	protected String noticeType;
	/* 关注类型 */
	@FieldDefine(title = "关注类型")
	@Column(name = "TYPE_")
	@Size(max = 50)
	@NotEmpty
	protected String type;
	/* 类型主键ID */
	@FieldDefine(title = "类型主键ID")
	@Column(name = "TYPE_PK_")
	@Size(max = 64)
	@NotEmpty
	protected String typePk;

	/**
	 * Default Empty Constructor for class ProWorkAtt
	 */
	public ProWorkAtt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ProWorkAtt
	 */
	public ProWorkAtt(String in_attId) {
		this.setAttId(in_attId);
	}

	/**
	 * 主键 * @return String
	 */
	public String getAttId() {
		return this.attId;
	}

	/**
	 * 设置 主键
	 */
	public void setAttId(String aValue) {
		this.attId = aValue;
	}

	/**
	 * 关注人ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 关注人ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 关注时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getAttTime() {
		return this.attTime;
	}

	/**
	 * 设置 关注时间
	 */
	public void setAttTime(java.util.Date aValue) {
		this.attTime = aValue;
	}

	/**
	 * 通知方式 * @return String
	 */
	public String getNoticeType() {
		return this.noticeType;
	}

	/**
	 * 设置 通知方式
	 */
	public void setNoticeType(String aValue) {
		this.noticeType = aValue;
	}

	/**
	 * 关注类型 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 关注类型
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 类型主键ID * @return String
	 */
	public String getTypePk() {
		return this.typePk;
	}

	/**
	 * 设置 类型主键ID
	 */
	public void setTypePk(String aValue) {
		this.typePk = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.attId;
	}

	@Override
	public Serializable getPkId() {
		return this.attId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.attId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProWorkAtt)) {
			return false;
		}
		ProWorkAtt rhs = (ProWorkAtt) object;
		return new EqualsBuilder().append(this.attId, rhs.attId).append(this.userId, rhs.userId).append(this.attTime, rhs.attTime).append(this.noticeType, rhs.noticeType).append(this.type, rhs.type).append(this.typePk, rhs.typePk)
				.append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.attId).append(this.userId).append(this.attTime).append(this.noticeType).append(this.type).append(this.typePk).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("attId", this.attId).append("userId", this.userId).append("attTime", this.attTime).append("noticeType", this.noticeType).append("type", this.type).append("typePk", this.typePk)
				.append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
