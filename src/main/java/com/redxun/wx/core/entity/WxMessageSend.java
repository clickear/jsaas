



package com.redxun.wx.core.entity;

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
 *  
 * 描述：WX_MESSAGE_SEND实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-07-17 17:58:08
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_MESSAGE_SEND")
@TableDefine(title = "WX_MESSAGE_SEND")
public class WxMessageSend extends BaseTenantEntity {
	public static final 	String  EVERYONE="everyone";
	public static final String TAG="tag";
	public static final String OPENID="openId";
	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID")
	protected String ID;

	@FieldDefine(title = "公众号ID")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "消息类型")
	@Column(name = "MSG_TYPE_")
	protected String msgType; 
	@FieldDefine(title = "发送类型")
	@Column(name = "SEND_TYPE_")
	protected String sendType; 
	@FieldDefine(title = "接收者")
	@Column(name = "RECEIVER_")
	protected String receiver; 
	@FieldDefine(title = "消息内容")
	@Column(name = "CONTENT_")
	protected String content; 
	@FieldDefine(title = "发送状态")
	@Column(name = "SEND_STATE_")
	protected String sendState; 
	@FieldDefine(title = "备用配置")
	@Column(name = "CONFIG_")
	protected String config; 
	
	
	
	
	public WxMessageSend() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxMessageSend(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.ID;
	}

	@Override
	public Serializable getPkId() {
		return this.ID;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.ID = (String) pkId;
	}
	
	public String getID() {
		return this.ID;
	}

	
	public void setID(String aValue) {
		this.ID = aValue;
	}
	
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	
	/**
	 * 返回 公众号ID
	 * @return
	 */
	public String getPubId() {
		return this.pubId;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	/**
	 * 返回 消息类型
	 * @return
	 */
	public String getMsgType() {
		return this.msgType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
	/**
	 * 返回 发送类型
	 * @return
	 */
	public String getSendType() {
		return this.sendType;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * 返回 接收者
	 * @return
	 */
	public String getReceiver() {
		return this.receiver;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 返回 消息内容
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	
	/**
	 * 返回 发送状态
	 * @return
	 */
	public String getSendState() {
		return this.sendState;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	
	/**
	 * 返回 备用配置
	 * @return
	 */
	public String getConfig() {
		return this.config;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxMessageSend)) {
			return false;
		}
		WxMessageSend rhs = (WxMessageSend) object;
		return new EqualsBuilder()
		.append(this.ID, rhs.ID) 
		.append(this.pubId, rhs.pubId) 
		.append(this.msgType, rhs.msgType) 
		.append(this.sendType, rhs.sendType) 
		.append(this.receiver, rhs.receiver) 
		.append(this.content, rhs.content) 
		.append(this.sendState, rhs.sendState) 
		.append(this.config, rhs.config) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.ID) 
		.append(this.pubId) 
		.append(this.msgType) 
		.append(this.sendType) 
		.append(this.receiver) 
		.append(this.content) 
		.append(this.sendState) 
		.append(this.config) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("ID", this.ID) 
				.append("pubId", this.pubId) 
				.append("msgType", this.msgType) 
				.append("sendType", this.sendType) 
				.append("receiver", this.receiver) 
				.append("content", this.content) 
				.append("sendState", this.sendState) 
				.append("config", this.config) 
										.toString();
	}

}



