



package com.redxun.wx.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;
import com.redxun.core.xstream.convert.DateConverter;

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
import com.thoughtworks.xstream.annotations.XStreamConverter;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 *  
 * 描述：微信关注者实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-06-30 08:51:08
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_SUBSCRIBE_")
@TableDefine(title = "微信关注者")
public class WxSubscribe extends BaseTenantEntity {

	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID_")
	protected String id;
	@FieldDefine(title = "SUBSCRIBE")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "SUBSCRIBE")
	@Column(name = "SUBSCRIBE_")
	protected String subscribe; 
	@FieldDefine(title = "OPENID")
	@Column(name = "OPEN_ID_")
	protected String openId; 
	@FieldDefine(title = "昵称")
	@Column(name = "NICK_NAME_")
	protected String nickName; 
	@FieldDefine(title = "语言")
	@Column(name = "LANGUAGE_")
	protected String language; 
	@FieldDefine(title = "城市")
	@Column(name = "CITY_")
	protected String city; 
	@FieldDefine(title = "省份")
	@Column(name = "PROVINCE_")
	protected String province; 
	@FieldDefine(title = "国家")
	@Column(name = "COUNTRY_")
	protected String country; 
	@FieldDefine(title = "绑定ID")
	@Column(name = "UNIONID_")
	protected String unionid; 
	@FieldDefine(title = "最后的关注时间")
	@Column(name = "SUBSCRIBE_TIME_")
	@XStreamConverter(value=DateConverter.class)
	protected java.util.Date subscribeTime; 
	@FieldDefine(title = "粉丝备注")
	@Column(name = "REMARK_")
	protected String remark; 
	@FieldDefine(title = "用户分组ID")
	@Column(name = "GROUPID_")
	protected String groupid; 
	@FieldDefine(title = "标签ID列表")
	@Column(name = "TAGID_LIST_")
	protected String tagidList; 
	
	
	
	
	
	public WxSubscribe() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxSubscribe(String in_id) {
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
	
	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	
	/**
	 * 返回 SUBSCRIBE
	 * @return
	 */
	public String getSubscribe() {
		return this.subscribe;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	/**
	 * 返回 OPENID
	 * @return
	 */
	public String getOpenId() {
		return this.openId;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * 返回 昵称
	 * @return
	 */
	public String getNickName() {
		return this.nickName;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * 返回 语言
	 * @return
	 */
	public String getLanguage() {
		return this.language;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 返回 城市
	 * @return
	 */
	public String getCity() {
		return this.city;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	/**
	 * 返回 省份
	 * @return
	 */
	public String getProvince() {
		return this.province;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * 返回 国家
	 * @return
	 */
	public String getCountry() {
		return this.country;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	/**
	 * 返回 绑定ID
	 * @return
	 */
	public String getUnionid() {
		return this.unionid;
	}
	public void setSubscribeTime(java.util.Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	/**
	 * 返回 最后的关注时间
	 * @return
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getSubscribeTime() {
		return this.subscribeTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 返回 粉丝备注
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	/**
	 * 返回 用户分组ID
	 * @return
	 */
	public String getGroupid() {
		return this.groupid;
	}
	public void setTagidList(String tagidList) {
		this.tagidList = tagidList;
	}
	
	/**
	 * 返回 标签ID列表
	 * @return
	 */
	public String getTagidList() {
		return this.tagidList;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxSubscribe)) {
			return false;
		}
		WxSubscribe rhs = (WxSubscribe) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.subscribe, rhs.subscribe) 
		.append(this.openId, rhs.openId) 
		.append(this.nickName, rhs.nickName) 
		.append(this.language, rhs.language) 
		.append(this.city, rhs.city) 
		.append(this.province, rhs.province) 
		.append(this.country, rhs.country) 
		.append(this.unionid, rhs.unionid) 
		.append(this.subscribeTime, rhs.subscribeTime) 
		.append(this.remark, rhs.remark) 
		.append(this.groupid, rhs.groupid) 
		.append(this.tagidList, rhs.tagidList) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.subscribe) 
		.append(this.openId) 
		.append(this.nickName) 
		.append(this.language) 
		.append(this.city) 
		.append(this.province) 
		.append(this.country) 
		.append(this.unionid) 
		.append(this.subscribeTime) 
		.append(this.remark) 
		.append(this.groupid) 
		.append(this.tagidList) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("subscribe", this.subscribe) 
				.append("openId", this.openId) 
				.append("nickName", this.nickName) 
				.append("language", this.language) 
				.append("city", this.city) 
				.append("province", this.province) 
				.append("country", this.country) 
				.append("unionid", this.unionid) 
				.append("subscribeTime", this.subscribeTime) 
				.append("remark", this.remark) 
				.append("groupid", this.groupid) 
				.append("tagidList", this.tagidList) 
												.toString();
	}

}



