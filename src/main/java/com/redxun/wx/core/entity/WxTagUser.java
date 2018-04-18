



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
 * 描述：微信用户标签实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-06-29 17:55:30
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_TAG_USER")
@TableDefine(title = "微信用户标签")
public class WxTagUser extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;
	@FieldDefine(title = "标签名")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "标签名")
	@Column(name = "TAG_ID_")
	protected String tagId; 
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	protected String userId; 
	
	
	
	
	public WxTagUser() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxTagUser(String in_id) {
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

	
	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public void setId(String aValue) {
		this.id = aValue;
	}
	
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	/**
	 * 返回 标签名
	 * @return
	 */
	public String getTagId() {
		return this.tagId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxTagUser)) {
			return false;
		}
		WxTagUser rhs = (WxTagUser) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.tagId, rhs.tagId) 
		.append(this.userId, rhs.userId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.tagId) 
		.append(this.userId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("tagId", this.tagId) 
				.append("userId", this.userId) 
												.toString();
	}

}



