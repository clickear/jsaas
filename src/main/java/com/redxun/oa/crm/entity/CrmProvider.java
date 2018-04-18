package com.redxun.oa.crm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：CrmProvider实体类定义
 * 供应商管理
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "CRM_PROVIDER")
@TableDefine(title = "供应商管理")
public class CrmProvider extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "PRO_ID_")
	protected String proId;
	/* 供应商名 */
	@FieldDefine(title = "供应商名")
	@Column(name = "NAME_")
	@Size(max = 80)
	@NotEmpty
	protected String name;
	/* 供应商简称 */
	@FieldDefine(title = "供应商简称")
	@Column(name = "SHORT_DESC_")
	@Size(max = 100)
	@NotEmpty
	protected String shortDesc;
	/* 单位级别 */
	@FieldDefine(title = "单位级别")
	@Column(name = "CMP_LEVEL_")
	@Size(max = 20)
	protected String cmpLevel;
	/* 单位类型 */
	@FieldDefine(title = "单位类型")
	@Column(name = "CMP_TYPE_")
	@Size(max = 20)
	protected String cmpType;
	/* 信用级别 */
	@FieldDefine(title = "信用级别")
	@Column(name = "CREDIT_TYPE_")
	@Size(max = 20)
	protected String creditType;
	/* 信用额度 */
	@FieldDefine(title = "信用额度")
	@Column(name = "CREDIT_LIMIT_")
	protected Integer creditLimit;
	/* 信用账期 */
	@FieldDefine(title = "信用账期")
	@Column(name = "CREDIT_PERIOD_")
	protected Integer creditPeriod;
	/* 网站 */
	@FieldDefine(title = "网站")
	@Column(name = "WEB_SITE_")
	@Size(max = 200)
	protected String webSite;
	/* 地址 */
	@FieldDefine(title = "地址")
	@Column(name = "ADDRESS_")
	@Size(max = 200)
	protected String address;
	/* 邮编 */
	@FieldDefine(title = "邮编")
	@Column(name = "ZIP_")
	@Size(max = 20)
	protected String zip;
	/* 联系人名 */
	@FieldDefine(title = "联系人名")
	@Column(name = "CONTACTOR_")
	@Size(max = 32)
	protected String contactor;
	/* 联系人手机 */
	@FieldDefine(title = "联系人手机")
	@Column(name = "MOBILE_")
	@Size(max = 20)
	protected String mobile;
	/* 固定电话 */
	@FieldDefine(title = "固定电话")
	@Column(name = "PHONE_")
	@Size(max = 20)
	protected String phone;
	/* 微信号 */
	@FieldDefine(title = "微信号")
	@Column(name = "WEIXIN_")
	@Size(max = 50)
	protected String weixin;
	/* 微博号 */
	@FieldDefine(title = "微博号")
	@Column(name = "WEIBO_")
	@Size(max = 80)
	protected String weibo;
	/* 备注 */
	@FieldDefine(title = "备注")
	@Column(name = "MEMO_")
	@Size(max = 65535)
	protected String memo;
	/* 附件IDS */
	@FieldDefine(title = "附件IDS")
	@Column(name = "ADDTION_FIDS_")
	@Size(max = 512)
	protected String addtionFids;
	/* 负责人ID */
	@FieldDefine(title = "负责人ID")
	@Column(name = "CHARGE_ID_")
	@Size(max = 64)
	protected String chargeId;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	/* 流程实例ID */
	@FieldDefine(title = "流程实例ID")
	@Column(name = "ACT_INST_ID_")
	@Size(max = 64)
	protected String actInstId;

	/**
	 * Default Empty Constructor for class CrmProvider
	 */
	public CrmProvider() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class CrmProvider
	 */
	public CrmProvider(String in_proId) {
		this.setProId(in_proId);
	}

	/**
	 * 供应商ID * @return String
	 */
	public String getProId() {
		return this.proId;
	}

	/**
	 * 设置 供应商ID
	 */
	public void setProId(String aValue) {
		this.proId = aValue;
	}

	/**
	 * 供应商名 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 供应商名
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 供应商简称 * @return String
	 */
	public String getShortDesc() {
		return this.shortDesc;
	}

	/**
	 * 设置 供应商简称
	 */
	public void setShortDesc(String aValue) {
		this.shortDesc = aValue;
	}

	/**
	 * 单位级别 * @return String
	 */
	public String getCmpLevel() {
		return this.cmpLevel;
	}

	/**
	 * 设置 单位级别
	 */
	public void setCmpLevel(String aValue) {
		this.cmpLevel = aValue;
	}

	/**
	 * 单位类型 * @return String
	 */
	public String getCmpType() {
		return this.cmpType;
	}

	/**
	 * 设置 单位类型
	 */
	public void setCmpType(String aValue) {
		this.cmpType = aValue;
	}

	/**
	 * 信用级别 * @return String
	 */
	public String getCreditType() {
		return this.creditType;
	}

	/**
	 * 设置 信用级别
	 */
	public void setCreditType(String aValue) {
		this.creditType = aValue;
	}

	/**
	 * 信用额度 * @return Integer
	 */
	public Integer getCreditLimit() {
		return this.creditLimit;
	}

	/**
	 * 设置 信用额度
	 */
	public void setCreditLimit(Integer aValue) {
		this.creditLimit = aValue;
	}

	/**
	 * 信用账期 * @return Integer
	 */
	public Integer getCreditPeriod() {
		return this.creditPeriod;
	}

	/**
	 * 设置 信用账期
	 */
	public void setCreditPeriod(Integer aValue) {
		this.creditPeriod = aValue;
	}

	/**
	 * 网站 * @return String
	 */
	public String getWebSite() {
		return this.webSite;
	}

	/**
	 * 设置 网站
	 */
	public void setWebSite(String aValue) {
		this.webSite = aValue;
	}

	/**
	 * 地址 * @return String
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * 设置 地址
	 */
	public void setAddress(String aValue) {
		this.address = aValue;
	}

	/**
	 * 邮编 * @return String
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * 设置 邮编
	 */
	public void setZip(String aValue) {
		this.zip = aValue;
	}

	/**
	 * 联系人名 * @return String
	 */
	public String getContactor() {
		return this.contactor;
	}

	/**
	 * 设置 联系人名
	 */
	public void setContactor(String aValue) {
		this.contactor = aValue;
	}

	/**
	 * 联系人手机 * @return String
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 设置 联系人手机
	 */
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}

	/**
	 * 固定电话 * @return String
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置 固定电话
	 */
	public void setPhone(String aValue) {
		this.phone = aValue;
	}

	/**
	 * 微信号 * @return String
	 */
	public String getWeixin() {
		return this.weixin;
	}

	/**
	 * 设置 微信号
	 */
	public void setWeixin(String aValue) {
		this.weixin = aValue;
	}

	/**
	 * 微博号 * @return String
	 */
	public String getWeibo() {
		return this.weibo;
	}

	/**
	 * 设置 微博号
	 */
	public void setWeibo(String aValue) {
		this.weibo = aValue;
	}

	/**
	 * 备注 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 备注
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	/**
	 * 附件IDS * @return String
	 */
	public String getAddtionFids() {
		return this.addtionFids;
	}

	/**
	 * 设置 附件IDS
	 */
	public void setAddtionFids(String aValue) {
		this.addtionFids = aValue;
	}

	/**
	 * 负责人ID * @return String
	 */
	public String getChargeId() {
		return this.chargeId;
	}

	/**
	 * 设置 负责人ID
	 */
	public void setChargeId(String aValue) {
		this.chargeId = aValue;
	}

	/**
	 * 状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 流程实例ID * @return String
	 */
	public String getActInstId() {
		return this.actInstId;
	}

	/**
	 * 设置 流程实例ID
	 */
	public void setActInstId(String aValue) {
		this.actInstId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.proId;
	}

	@Override
	public Serializable getPkId() {
		return this.proId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.proId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CrmProvider)) {
			return false;
		}
		CrmProvider rhs = (CrmProvider) object;
		return new EqualsBuilder().append(this.proId, rhs.proId).append(this.name, rhs.name).append(this.shortDesc, rhs.shortDesc).append(this.cmpLevel, rhs.cmpLevel)
				.append(this.cmpType, rhs.cmpType).append(this.creditType, rhs.creditType).append(this.creditLimit, rhs.creditLimit).append(this.creditPeriod, rhs.creditPeriod)
				.append(this.webSite, rhs.webSite).append(this.address, rhs.address).append(this.zip, rhs.zip).append(this.contactor, rhs.contactor)
				.append(this.mobile, rhs.mobile).append(this.phone, rhs.phone).append(this.weixin, rhs.weixin).append(this.weibo, rhs.weibo).append(this.memo, rhs.memo)
				.append(this.addtionFids, rhs.addtionFids).append(this.chargeId, rhs.chargeId).append(this.status, rhs.status).append(this.actInstId, rhs.actInstId)
				.append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.proId).append(this.name).append(this.shortDesc).append(this.cmpLevel).append(this.cmpType)
				.append(this.creditType).append(this.creditLimit).append(this.creditPeriod).append(this.webSite).append(this.address).append(this.zip).append(this.contactor)
				.append(this.mobile).append(this.phone).append(this.weixin).append(this.weibo).append(this.memo).append(this.addtionFids).append(this.chargeId).append(this.status)
				.append(this.actInstId).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("proId", this.proId).append("name", this.name).append("shortDesc", this.shortDesc).append("cmpLevel", this.cmpLevel)
				.append("cmpType", this.cmpType).append("creditType", this.creditType).append("creditLimit", this.creditLimit).append("creditPeriod", this.creditPeriod)
				.append("webSite", this.webSite).append("address", this.address).append("zip", this.zip).append("contactor", this.contactor).append("mobile", this.mobile)
				.append("phone", this.phone).append("weixin", this.weixin).append("weibo", this.weibo).append("memo", this.memo).append("addtionFids", this.addtionFids)
				.append("chargeId", this.chargeId).append("status", this.status).append("actInstId", this.actInstId).append("tenantId", this.tenantId)
				.append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
