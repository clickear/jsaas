package com.redxun.wx.ent.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：微信企业配置实体类定义
 * 作者：mansan
 * 邮箱: ray@redxun.com
 * 日期:2017-06-04 12:27:35
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "wx_ent_corp")
@TableDefine(title = "微信企业配置")
public class WxEntCorp extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "企业ID")
	@Column(name = "CORP_ID_")
	protected String corpId; 
	@FieldDefine(title = "通讯录密钥")
	@Column(name = "SECRET_")
	protected String secret; 
	@FieldDefine(title = "是否启用企业微信")
	@Column(name = "ENABLE_")
	protected Integer enable; 
	
	
	
	
	public WxEntCorp() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxEntCorp(String in_id) {
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
	
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	
	/**
	 * 返回 企业ID
	 * @return
	 */
	public String getCorpId() {
		return this.corpId;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	/**
	 * 返回 通讯录密钥
	 * @return
	 */
	public String getSecret() {
		return this.secret;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	/**
	 * 返回 是否启用企业微信
	 * @return
	 */
	public Integer getEnable() {
		return this.enable;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxEntCorp)) {
			return false;
		}
		WxEntCorp rhs = (WxEntCorp) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.corpId, rhs.corpId) 
		.append(this.secret, rhs.secret) 
		.append(this.enable, rhs.enable) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.corpId) 
		.append(this.secret) 
		.append(this.enable) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("corpId", this.corpId) 
				.append("secret", this.secret) 
				.append("enable", this.enable) 
												.toString();
	}

}



