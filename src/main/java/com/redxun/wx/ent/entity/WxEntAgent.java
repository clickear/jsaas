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
 * 描述：微信应用实体类定义
 * 作者：mansan
 * 邮箱: ray@redxun.com
 * 日期:2017-06-04 12:27:36
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "wx_ent_agent")
@TableDefine(title = "微信应用")
public class WxEntAgent extends BaseTenantEntity {
	
	

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "NAME_")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "DESCRIPTION_")
	@Column(name = "DESCRIPTION_")
	protected String description; 
	@FieldDefine(title = "信任域名")
	@Column(name = "DOMAIN_")
	protected String domain; 
	@FieldDefine(title = "HOME_URL_")
	@Column(name = "HOME_URL_")
	protected String homeUrl; 
	@FieldDefine(title = "企业主键")
	@Column(name = "ENT_ID_")
	protected String entId; 
	@FieldDefine(title = "企业ID")
	@Column(name = "CORP_ID_")
	protected String corpId; 
	@FieldDefine(title = "应用ID")
	@Column(name = "AGENT_ID_")
	protected String agentId; 
	@FieldDefine(title = "密钥")
	@Column(name = "SECRET_")
	protected String secret; 
	@FieldDefine(title = "是否默认")
	@Column(name = "DEFAULT_AGENT_")
	protected Integer defaultAgent; 
	
	/**
	 * 
	 */
	private WxEntCorp wxEntCorp;
	
	/**
	 * 保存，保存发布
	 */
	private String action="";
	
	
	
	
	public WxEntAgent() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxEntAgent(String in_id) {
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 NAME_
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 返回 DESCRIPTION_
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	/**
	 * 返回 信任域名
	 * @return
	 */
	public String getDomain() {
		return this.domain;
	}
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
	
	/**
	 * 返回 HOME_URL_
	 * @return
	 */
	public String getHomeUrl() {
		return this.homeUrl;
	}
	public void setEntId(String entId) {
		this.entId = entId;
	}
	
	/**
	 * 返回 企业主键
	 * @return
	 */
	public String getEntId() {
		return this.entId;
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
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	/**
	 * 返回 应用ID
	 * @return
	 */
	public String getAgentId() {
		return this.agentId;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	/**
	 * 返回 密钥
	 * @return
	 */
	public String getSecret() {
		return this.secret;
	}
	public void setDefaultAgent(Integer defaultAgent) {
		this.defaultAgent = defaultAgent;
	}
	
	/**
	 * 返回 是否默认
	 * @return
	 */
	public Integer getDefaultAgent() {
		return this.defaultAgent;
	}
	
	
	
		

	public WxEntCorp getWxEntCorp() {
		return wxEntCorp;
	}

	public String getAction() {
		return action;
	}

	public void setWxEntCorp(WxEntCorp wxEntCorp) {
		this.wxEntCorp = wxEntCorp;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxEntAgent)) {
			return false;
		}
		WxEntAgent rhs = (WxEntAgent) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.name, rhs.name) 
		.append(this.description, rhs.description) 
		.append(this.domain, rhs.domain) 
		.append(this.homeUrl, rhs.homeUrl) 
		.append(this.entId, rhs.entId) 
		.append(this.corpId, rhs.corpId) 
		.append(this.agentId, rhs.agentId) 
		.append(this.secret, rhs.secret) 
		.append(this.defaultAgent, rhs.defaultAgent) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.name) 
		.append(this.description) 
		.append(this.domain) 
		.append(this.homeUrl) 
		.append(this.entId) 
		.append(this.corpId) 
		.append(this.agentId) 
		.append(this.secret) 
		.append(this.defaultAgent) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("name", this.name) 
				.append("description", this.description) 
				.append("domain", this.domain) 
				.append("homeUrl", this.homeUrl) 
				.append("entId", this.entId) 
				.append("corpId", this.corpId) 
				.append("agentId", this.agentId) 
				.append("secret", this.secret) 
				.append("defaultAgent", this.defaultAgent) 
												.toString();
	}

}



