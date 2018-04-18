



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
 * 描述：微信网页授权实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-08-18 17:05:42
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_WEB_GRANT")
@TableDefine(title = "微信网页授权")
public class WxWebGrant extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "公众号ID")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "链接")
	@Column(name = "URL_")
	protected String url; 
	@FieldDefine(title = "转换后的URL")
	@Column(name = "TRANSFORM_URL_")
	protected String transformUrl; 
	@FieldDefine(title = "配置信息")
	@Column(name = "CONFIG_")
	protected String config; 
	
	
	
	
	public WxWebGrant() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxWebGrant(String in_id) {
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

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 返回 链接
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	public void setTransformUrl(String transformUrl) {
		this.transformUrl = transformUrl;
	}
	
	/**
	 * 返回 转换后的URL
	 * @return
	 */
	public String getTransformUrl() {
		return this.transformUrl;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	
	/**
	 * 返回 配置信息
	 * @return
	 */
	public String getConfig() {
		return this.config;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxWebGrant)) {
			return false;
		}
		WxWebGrant rhs = (WxWebGrant) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.pubId, rhs.pubId) 
		.append(this.url, rhs.url) 
		.append(this.transformUrl, rhs.transformUrl) 
		.append(this.config, rhs.config) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.pubId) 
		.append(this.url) 
		.append(this.transformUrl) 
		.append(this.config) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("pubId", this.pubId) 
				.append("url", this.url) 
				.append("transformUrl", this.transformUrl) 
				.append("config", this.config) 
												.toString();
	}

}



