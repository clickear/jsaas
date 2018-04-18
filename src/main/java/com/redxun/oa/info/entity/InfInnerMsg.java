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
import javax.persistence.Transient;
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
 * 描述：InfInnerMsg实体类定义
 * 内部短消息
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_INNER_MSG")
@TableDefine(title = "内部短消息")
@JsonIgnoreProperties("infInboxs")
public class InfInnerMsg extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "MSG_ID_")
	protected String msgId;
	/* 消息内容 */
	@FieldDefine(title = "消息内容")
	@Column(name = "CONTENT_")
	@Size(max = 512)
	@NotEmpty
	protected String content;
	/* 消息内容 */
	@FieldDefine(title = "消息携带链接")
	@Column(name = "LINK_MSG_")
	@Size(max = 1024)
	protected String linkMsg;
	/* 消息分类 */
	@FieldDefine(title = "消息分类")
	@Column(name = "CATEGORY_")
	@Size(max = 50)
	protected String category;
	/* 发送人名 */
	@FieldDefine(title = "发送人名")
	@Column(name = "SENDER_")
	@Size(max = 50)
	protected String sender;
	/* 发送人ID */
	@FieldDefine(title = "发送人ID")
	@Column(name = "SENDER_ID_")
	@Size(max = 64)
	protected String senderId;
	/* 删除标识*/
	@FieldDefine(title = "删除标识")
	@Column(name = "DEL_FLAG_")
	@Size(max = 20)
	protected String delFlag;
	/* 是否已读*/
	@Transient
	protected String isRead;

	@FieldDefine(title = "内部短消息收件箱")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "infInnerMsg", fetch = FetchType.LAZY)
	protected java.util.Set<InfInbox> infInboxs = new java.util.HashSet<InfInbox>();

	/**
	 * Default Empty Constructor for class InfInnerMsg
	 */
	public InfInnerMsg() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InfInnerMsg
	 */
	public InfInnerMsg(String in_msgId) {
		this.setMsgId(in_msgId);
	}

	public java.util.Set<InfInbox> getInfInboxs() {
		return infInboxs;
	}

	public void setInfInboxs(java.util.Set<InfInbox> in_infInboxs) {
		this.infInboxs = in_infInboxs;
	}
	
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * 消息ID * @return String
	 */
	public String getMsgId() {
		return this.msgId;
	}

	/**
	 * 设置 消息ID
	 */
	public void setMsgId(String aValue) {
		this.msgId = aValue;
	}

	/**
	 * 消息内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 消息内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	
	public String getLinkMsg() {
		return linkMsg;
	}

	public void setLinkMsg(String linkMsg) {
		this.linkMsg = linkMsg;
	}

	/**
	 * 消息分类 * @return String
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * 设置 消息分类
	 */
	public void setCategory(String aValue) {
		this.category = aValue;
	}

	/**
	 * 发送人名 * @return String
	 */
	public String getSender() {
		return this.sender;
	}

	/**
	 * 设置 发送人名
	 */
	public void setSender(String aValue) {
		this.sender = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.msgId;
	}

	@Override
	public Serializable getPkId() {
		return this.msgId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.msgId = (String) pkId;
	}

	
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InfInnerMsg)) {
			return false;
		}
		InfInnerMsg rhs = (InfInnerMsg) object;
		return new EqualsBuilder().append(this.msgId, rhs.msgId)
				.append(this.content, rhs.content)
				.append(this.category, rhs.category)
				.append(this.sender, rhs.sender)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.msgId)
				.append(this.content).append(this.category).append(this.sender)
				.append(this.createTime).append(this.createBy)
				.append(this.updateTime).append(this.updateBy)
				.append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("msgId", this.msgId)
				.append("content", this.content)
				.append("category", this.category)
				.append("sender", this.sender)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy)
				.append("tenantId", this.tenantId).toString();
	}

}
