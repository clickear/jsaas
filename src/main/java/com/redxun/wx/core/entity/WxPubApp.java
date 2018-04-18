



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
 * 描述：公众号管理实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-06-29 16:57:29
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_PUB_APP")
@TableDefine(title = "公众号管理")
public class WxPubApp extends BaseTenantEntity {

	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID_")
	protected String id;
	
	@FieldDefine(title = "微信号")
	@Column(name = "WX_NO_")
	protected String wxNo; 
	@FieldDefine(title = "APP_ID_")
	@Column(name = "APP_ID_")
	protected String appId; 
	@FieldDefine(title = "密钥")
	@Column(name = "SECRET_")
	protected String secret; 
	@FieldDefine(title = "类型")
	@Column(name = "TYPE_")
	protected String type; 
	@FieldDefine(title = "是否认证")
	@Column(name = "AUTHED_")
	protected String authed; 
	@FieldDefine(title = "接口消息地址")
	@Column(name = "INTERFACE_URL_")
	protected String interfaceUrl; 
	@FieldDefine(title = "token")
	@Column(name = "TOKEN")
	protected String TOKEN; 
	@FieldDefine(title = "js安全域名")
	@Column(name = "JS_DOMAIN_")
	protected String jsDomain; 
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "别名")
	@Column(name = "ALIAS_")
	protected String alias; 
	@FieldDefine(title = "描述")
	@Column(name = "DESCRIPTION_")
	protected String description; 
	@FieldDefine(title = "菜单配置")
	@Column(name = "MENU_CONFIG_")
	protected String menuConfig; 
	@FieldDefine(title = "其他配置")
	@Column(name = "OTHER_CONFIG_")
	protected String otherConfig; 
	
	
	public WxPubApp() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxPubApp(String in_id) {
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
	
	public void setWxNo(String wxNo) {
		this.wxNo = wxNo;
	}
	
	/**
	 * 返回 微信号
	 * @return
	 */
	public String getWxNo() {
		return this.wxNo;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	/**
	 * 返回 APP_ID_
	 * @return
	 */
	public String getAppId() {
		return this.appId;
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
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	public void setAuthed(String authed) {
		this.authed = authed;
	}
	
	/**
	 * 返回 是否认证
	 * @return
	 */
	public String getAuthed() {
		return this.authed;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	
	/**
	 * 返回 接口消息地址
	 * @return
	 */
	public String getInterfaceUrl() {
		return this.interfaceUrl;
	}
	public void setTOKEN(String TOKEN) {
		this.TOKEN = TOKEN;
	}
	
	/**
	 * 返回 token
	 * @return
	 */
	public String getTOKEN() {
		return this.TOKEN;
	}
	public void setJsDomain(String jsDomain) {
		this.jsDomain = jsDomain;
	}
	
	/**
	 * 返回 js安全域名
	 * @return
	 */
	public String getJsDomain() {
		return this.jsDomain;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	
		

	public String getMenuConfig() {
		return menuConfig;
	}

	public void setMenuConfig(String menuConfig) {
		this.menuConfig = menuConfig;
	}

	public String getOtherConfig() {
		return otherConfig;
	}

	public void setOtherConfig(String otherConfig) {
		this.otherConfig = otherConfig;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxPubApp)) {
			return false;
		}
		WxPubApp rhs = (WxPubApp) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.wxNo, rhs.wxNo) 
		.append(this.appId, rhs.appId) 
		.append(this.secret, rhs.secret) 
		.append(this.type, rhs.type) 
		.append(this.authed, rhs.authed) 
		.append(this.interfaceUrl, rhs.interfaceUrl) 
		.append(this.TOKEN, rhs.TOKEN) 
		.append(this.jsDomain, rhs.jsDomain) 
		.append(this.name, rhs.name) 
		.append(this.alias, rhs.alias) 
		.append(this.description, rhs.description) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.wxNo) 
		.append(this.appId) 
		.append(this.secret) 
		.append(this.type) 
		.append(this.authed) 
		.append(this.interfaceUrl) 
		.append(this.TOKEN) 
		.append(this.jsDomain) 
		.append(this.name) 
		.append(this.alias) 
		.append(this.description) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("wxNo", this.wxNo) 
				.append("appId", this.appId) 
				.append("secret", this.secret) 
				.append("type", this.type) 
				.append("authed", this.authed) 
				.append("interfaceUrl", this.interfaceUrl) 
				.append("TOKEN", this.TOKEN) 
				.append("jsDomain", this.jsDomain) 
				.append("name", this.name) 
				.append("alias", this.alias) 
				.append("description", this.description) 
												.toString();
	}

}



