



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
 * 描述：公众号关键字回复实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-08-30 11:39:20
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_KEY_WORD_REPLY")
@TableDefine(title = "公众号关键字回复")
public class WxKeyWordReply extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "公众号ID")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "关键字")
	@Column(name = "KEY_WORD_")
	protected String keyWord; 
	@FieldDefine(title = "回复方式")
	@Column(name = "REPLY_TYPE_")
	protected String replyType; 
	@FieldDefine(title = "回复内容")
	@Column(name = "REPLY_CONTENT_")
	protected String replyContent; 
	
	
	
	
	public WxKeyWordReply() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxKeyWordReply(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.id;
	}

	@Override
	public Serializable getPkId() {
		return this.id;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.id = (String) pkId;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setId(String aValue) {
		this.id = aValue;
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
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	/**
	 * 返回 关键字
	 * @return
	 */
	public String getKeyWord() {
		return this.keyWord;
	}
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
	
	/**
	 * 返回 回复方式
	 * @return
	 */
	public String getReplyType() {
		return this.replyType;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	
	/**
	 * 返回 回复内容
	 * @return
	 */
	public String getReplyContent() {
		return this.replyContent;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxKeyWordReply)) {
			return false;
		}
		WxKeyWordReply rhs = (WxKeyWordReply) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.pubId, rhs.pubId) 
		.append(this.keyWord, rhs.keyWord) 
		.append(this.replyType, rhs.replyType) 
		.append(this.replyContent, rhs.replyContent) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.pubId) 
		.append(this.keyWord) 
		.append(this.replyType) 
		.append(this.replyContent) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("pubId", this.pubId) 
				.append("keyWord", this.keyWord) 
				.append("replyType", this.replyType) 
				.append("replyContent", this.replyContent) 
												.toString();
	}

}



