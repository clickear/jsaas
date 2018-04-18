



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
 * 描述：微信素材实体类定义
 * 作者：ray
 * 邮箱: cmc@redxun.com
 * 日期:2017-07-11 16:03:13
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_METERIAL")
@TableDefine(title = "微信素材")
public class WxMeterial extends BaseTenantEntity {

	@FieldDefine(title = "素材ID")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "公众号ID")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "期限类型")
	@Column(name = "TERM_TYPE_")
	protected String termType; 
	@FieldDefine(title = "素材类型")
	@Column(name = "TMEDIA_TYPE_")
	protected String mediaType; 
	@FieldDefine(title = "素材名")
	@Column(name = "NAME_")
	protected String name; 
	@FieldDefine(title = "微信后台指定ID")
	@Column(name = "MEDIA_ID_")
	protected String mediaId; 
	@FieldDefine(title = "图文配置")
	@Column(name = "ART_CONFIG_")
	protected String artConfig; 
	
	
	
	
	public WxMeterial() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxMeterial(String in_id) {
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
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	/**
	 * 返回 期限类型
	 * @return
	 */
	public String getTermType() {
		return this.termType;
	}
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 素材名
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	/**
	 * 返回 微信后台指定ID
	 * @return
	 */
	public String getMediaId() {
		return this.mediaId;
	}
	public void setArtConfig(String artConfig) {
		this.artConfig = artConfig;
	}
	
	/**
	 * 返回 图文配置
	 * @return
	 */
	public String getArtConfig() {
		return this.artConfig;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxMeterial)) {
			return false;
		}
		WxMeterial rhs = (WxMeterial) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.pubId, rhs.pubId) 
		.append(this.termType, rhs.termType) 
		.append(this.name, rhs.name) 
		.append(this.mediaId, rhs.mediaId) 
		.append(this.artConfig, rhs.artConfig) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.pubId) 
		.append(this.termType) 
		.append(this.name) 
		.append(this.mediaId) 
		.append(this.artConfig) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("pubId", this.pubId) 
				.append("termType", this.termType) 
				.append("name", this.name) 
				.append("mediaId", this.mediaId) 
				.append("artConfig", this.artConfig) 
										.toString();
	}

}



