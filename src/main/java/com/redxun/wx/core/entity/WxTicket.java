



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
 * 描述：微信卡券实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-09-01 16:38:30
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "WX_TICKET")
@TableDefine(title = "微信卡券")
public class WxTicket extends BaseTenantEntity {

	@FieldDefine(title = "ID")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "公众号ID")
	@Column(name = "PUB_ID_")
	protected String pubId; 
	@FieldDefine(title = "卡券类型")
	@Column(name = "CARD_TYPE_")
	protected String cardType; 
	@FieldDefine(title = "卡券的商户logo")
	@Column(name = "LOGO_URL_")
	protected String logoUrl; 
	@FieldDefine(title = "码型")
	@Column(name = "CODE_TYPE_")
	protected String codeType; 
	@FieldDefine(title = "商户名字")
	@Column(name = "BRAND_NAME_")
	protected String brandName; 
	@FieldDefine(title = "卡券名")
	@Column(name = "TITLE_")
	protected String title; 
	@FieldDefine(title = "券颜色")
	@Column(name = "COLOR_")
	protected String color; 
	@FieldDefine(title = "卡券使用提醒")
	@Column(name = "NOTICE_")
	protected String notice; 
	@FieldDefine(title = "卡券使用说明")
	@Column(name = "DESCRIPTION_")
	protected String description; 
	@FieldDefine(title = "商品信息")
	@Column(name = "SKU_")
	protected String sku; 
	@FieldDefine(title = "使用日期")
	@Column(name = "DATE_INFO")
	protected String dateInfo; 
	@FieldDefine(title = "基础非必须信息")
	@Column(name = "BASE_INFO_")
	protected String baseInfo; 
	@FieldDefine(title = "高级非必填信息")
	@Column(name = "ADVANCED_INFO_")
	protected String advancedInfo; 
	@FieldDefine(title = "专用配置")
	@Column(name = "SPECIAL_CONFIG_")
	protected String specialConfig; 
	@FieldDefine(title = "审核是否通过")
	@Column(name = "CHECKED_")
	protected String checked; 
	
	
	
	
	public WxTicket() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public WxTicket(String in_id) {
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
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	/**
	 * 返回 卡券类型
	 * @return
	 */
	public String getCardType() {
		return this.cardType;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	/**
	 * 返回 卡券的商户logo
	 * @return
	 */
	public String getLogoUrl() {
		return this.logoUrl;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	
	/**
	 * 返回 码型
	 * @return
	 */
	public String getCodeType() {
		return this.codeType;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	/**
	 * 返回 商户名字
	 * @return
	 */
	public String getBrandName() {
		return this.brandName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 返回 卡券名
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * 返回 券颜色
	 * @return
	 */
	public String getColor() {
		return this.color;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	/**
	 * 返回 卡券使用提醒
	 * @return
	 */
	public String getNotice() {
		return this.notice;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 返回 卡券使用说明
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	/**
	 * 返回 商品信息
	 * @return
	 */
	public String getSku() {
		return this.sku;
	}
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	
	/**
	 * 返回 使用日期
	 * @return
	 */
	public String getDateInfo() {
		return this.dateInfo;
	}
	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}
	
	/**
	 * 返回 基础非必须信息
	 * @return
	 */
	public String getBaseInfo() {
		return this.baseInfo;
	}
	public void setAdvancedInfo(String advancedInfo) {
		this.advancedInfo = advancedInfo;
	}
	
	/**
	 * 返回 高级非必填信息
	 * @return
	 */
	public String getAdvancedInfo() {
		return this.advancedInfo;
	}
	public void setSpecialConfig(String specialConfig) {
		this.specialConfig = specialConfig;
	}
	
	/**
	 * 返回 专用配置
	 * @return
	 */
	public String getSpecialConfig() {
		return this.specialConfig;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	/**
	 * 返回 审核是否通过
	 * @return
	 */
	public String getChecked() {
		return this.checked;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WxTicket)) {
			return false;
		}
		WxTicket rhs = (WxTicket) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.pubId, rhs.pubId) 
		.append(this.cardType, rhs.cardType) 
		.append(this.logoUrl, rhs.logoUrl) 
		.append(this.codeType, rhs.codeType) 
		.append(this.brandName, rhs.brandName) 
		.append(this.title, rhs.title) 
		.append(this.color, rhs.color) 
		.append(this.notice, rhs.notice) 
		.append(this.description, rhs.description) 
		.append(this.sku, rhs.sku) 
		.append(this.dateInfo, rhs.dateInfo) 
		.append(this.baseInfo, rhs.baseInfo) 
		.append(this.advancedInfo, rhs.advancedInfo) 
		.append(this.specialConfig, rhs.specialConfig) 
		.append(this.checked, rhs.checked) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.pubId) 
		.append(this.cardType) 
		.append(this.logoUrl) 
		.append(this.codeType) 
		.append(this.brandName) 
		.append(this.title) 
		.append(this.color) 
		.append(this.notice) 
		.append(this.description) 
		.append(this.sku) 
		.append(this.dateInfo) 
		.append(this.baseInfo) 
		.append(this.advancedInfo) 
		.append(this.specialConfig) 
		.append(this.checked) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("pubId", this.pubId) 
				.append("cardType", this.cardType) 
				.append("logoUrl", this.logoUrl) 
				.append("codeType", this.codeType) 
				.append("brandName", this.brandName) 
				.append("title", this.title) 
				.append("color", this.color) 
				.append("notice", this.notice) 
				.append("description", this.description) 
				.append("sku", this.sku) 
				.append("dateInfo", this.dateInfo) 
				.append("baseInfo", this.baseInfo) 
				.append("advancedInfo", this.advancedInfo) 
				.append("specialConfig", this.specialConfig) 
				.append("checked", this.checked) 
												.toString();
	}

}



