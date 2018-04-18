package com.redxun.oa.info.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：InfInbox实体类定义
 * 内部短消息收件箱
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_INBOX")
@TableDefine(title = "内部短消息收件箱")
public class InfInbox extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "REC_ID_")
	protected String recId;
	/* 收发类型 */
	@FieldDefine(title = "收发类型")
	@Column(name = "REC_TYPE_")
	@Size(max = 20)
	protected String recType;
	/* 接收人ID */
	@FieldDefine(title = "收发人ID")
	@Column(name = "REC_USER_ID_")
	@Size(max = 512)
	protected String recUserId;
	/* 接收人名称 */
	@FieldDefine(title = "收发人名称")
	@Column(name = "FULLNAME_")
	@Size(max = 50)
	protected String fullname;
	/*
	 * 用户组ID 0代表全公司
	 */
	@FieldDefine(title = "用户组ID")
	@Column(name = "GROUP_ID_")
	@Size(max = 64)
	protected String groupId;
	/* 组名 */
	@FieldDefine(title = "组名")
	@Column(name = "GROUP_NAME_")
	@Size(max = 64)
	protected String groupName;
	/* 是否阅读 */
	@FieldDefine(title = "是否阅读")
	@Column(name = "IS_READ_")
	@Size(max = 20)
	protected String isRead;
	/* 是否删除 */
	@FieldDefine(title = "是否删除")
	@Column(name = "IS_DEL_")
	@Size(max = 20)
	protected String isDel;
	/* 收信内容*/
	@Transient
	protected String content;
	/* 收信名字*/
	@Transient
	protected String recName;
	
	@FieldDefine(title = "内部短消息")
	@ManyToOne
	@JoinColumn(name = "MSG_ID_")
	protected com.redxun.oa.info.entity.InfInnerMsg infInnerMsg;

	/**
	 * Default Empty Constructor for class InfInbox
	 */
	public InfInbox() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InfInbox
	 */
	public InfInbox(String in_recId) {
		this.setRecId(in_recId);
	}

	public com.redxun.oa.info.entity.InfInnerMsg getInfInnerMsg() {
		return infInnerMsg;
	}

	public void setInfInnerMsg(
			com.redxun.oa.info.entity.InfInnerMsg in_infInnerMsg) {
		this.infInnerMsg = in_infInnerMsg;
	}
	
	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}
	
	public String getRecType() {
		return recType;
	}

	public void setRecType(String recType) {
		this.recType = recType;
	}

	/**
	 * 接收ID * @return String
	 */
	
	public String getRecId() {
		return this.recId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 设置 接收ID
	 */
	public void setRecId(String aValue) {
		this.recId = aValue;
	}

	/**
	 * 消息ID * @return String
	 */
	public String getMsgId() {
		return this.getInfInnerMsg() == null ? null : this.getInfInnerMsg()
				.getMsgId();
	}

	/**
	 * 设置 消息ID
	 */
	public void setMsgId(String aValue) {
		if (aValue == null) {
			infInnerMsg = null;
		} else if (infInnerMsg == null) {
			infInnerMsg = new com.redxun.oa.info.entity.InfInnerMsg(aValue);
		} else {
			infInnerMsg.setMsgId(aValue);
		}
	}

	/**
	 * 收发人ID * @return String
	 */
	public String getRecUserId() {
		return this.recUserId;
	}

	/**
	 * 设置 收发人ID
	 */
	public void setRecUserId(String aValue) {
		this.recUserId = aValue;
	}

	/**
	 * 接收人名称 * @return String
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * 设置 接收人名称
	 */
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}

	/**
	 * 用户组ID 0代表全公司 * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置 用户组ID 0代表全公司
	 */
	public void setGroupId(String aValue) {
		this.groupId = aValue;
	}

	/**
	 * 组名 * @return String
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * 设置 组名
	 */
	public void setGroupName(String aValue) {
		this.groupName = aValue;
	}

	/**
	 * 是否阅读 * @return String
	 */
	public String getIsRead() {
		return this.isRead;
	}

	/**
	 * 设置 是否阅读
	 */
	public void setIsRead(String aValue) {
		this.isRead = aValue;
	}

	/**
	 * 是否删除 * @return String
	 */
	public String getIsDel() {
		return this.isDel;
	}

	/**
	 * 设置 是否删除
	 */
	public void setIsDel(String aValue) {
		this.isDel = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.recId;
	}

	@Override
	public Serializable getPkId() {
		return this.recId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.recId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InfInbox)) {
			return false;
		}
		InfInbox rhs = (InfInbox) object;
		return new EqualsBuilder().append(this.recId, rhs.recId)
				.append(this.recUserId, rhs.recUserId)
				.append(this.fullname, rhs.fullname)
				.append(this.groupId, rhs.groupId)
				.append(this.groupName, rhs.groupName)
				.append(this.isRead, rhs.isRead).append(this.isDel, rhs.isDel)
				.append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy)
				.append(this.updateTime, rhs.updateTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.recId)
				.append(this.recUserId).append(this.fullname)
				.append(this.groupId).append(this.groupName)
				.append(this.isRead).append(this.isDel).append(this.createTime)
				.append(this.createBy).append(this.updateTime)
				.append(this.updateBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("recId", this.recId)
				.append("recUserId", this.recUserId)
				.append("fullname", this.fullname)
				.append("groupId", this.groupId)
				.append("groupName", this.groupName)
				.append("isRead", this.isRead).append("isDel", this.isDel)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy)
				.append("tenantId", this.tenantId).toString();
	}

}
