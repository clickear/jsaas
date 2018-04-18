package com.redxun.oa.personal.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

/**
 * <pre>
 * 描述：联系人实体类定义
 * 通讯录联系人
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_ADDR_BOOK")
@TableDefine(title = "通讯录联系人")
@JsonIgnoreProperties(value={"addrConts","addrGrps"})
public class AddrBook extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ADDR_ID_")
	protected String addrId;
	/* 姓名 */
	@FieldDefine(title = "姓名")
	@Column(name = "NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String name;
	/* 公司 */
	@FieldDefine(title = "公司")
	@Column(name = "COMPANY_")
	@Size(max = 120)
	protected String company;
	/* 部门 */
	@FieldDefine(title = "部门")
	@Column(name = "DEP_")
	@Size(max = 50)
	protected String dep;
	/* 职务 */
	@FieldDefine(title = "职务")
	@Column(name = "POS_")
	@Size(max = 50)
	protected String pos;
	/* 主邮箱 */
	@FieldDefine(title = "主邮箱")
	@Column(name = "MAIL_")
	@Size(max = 255)
	protected String mail;
	/* 国家 */
	@FieldDefine(title = "国家")
	@Column(name = "COUNTRY_")
	@Size(max = 32)
	protected String country;
	/* 省份 */
	@FieldDefine(title = "省份")
	@Column(name = "SATE_")
	@Size(max = 32)
	protected String sate;
	/* 城市 */
	@FieldDefine(title = "城市")
	@Column(name = "CITY_")
	@Size(max = 32)
	protected String city;
	/* 街道 */
	@FieldDefine(title = "街道")
	@Column(name = "STREET_")
	@Size(max = 80)
	protected String street;
	/* 邮编 */
	@FieldDefine(title = "邮编")
	@Column(name = "ZIP_")
	@Size(max = 20)
	protected String zip;
	/* 生日 */
	@FieldDefine(title = "生日")
	@Column(name = "BIRTHDAY_")
	protected java.util.Date birthday;
	/* 主手机 */
	@FieldDefine(title = "主手机")
	@Column(name = "MOBILE_")
	@Size(max = 16)
	protected String mobile;
	/* 主电话 */
	@FieldDefine(title = "主电话")
	@Column(name = "PHONE_")
	@Size(max = 16)
	protected String phone;
	/* 主微信号 */
	@FieldDefine(title = "主微信号")
	@Column(name = "WEIXIN_")
	@Size(max = 80)
	protected String weixin;
	/* QQ */
	@FieldDefine(title = "QQ")
	@Column(name = "QQ_")
	@Size(max = 32)
	protected String qq;
	/* 头像文件ID */
	@FieldDefine(title = "头像文件ID")
	@Column(name = "PHOTO_ID_")
	@Size(max = 64)
	protected String photoId;

	@FieldDefine(title = "通讯录联系信息")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "addrBook", fetch = FetchType.LAZY)
	protected java.util.Set<AddrCont> addrConts = new java.util.HashSet<AddrCont>();
	
	@ManyToMany(fetch=FetchType.EAGER) 
    @JoinTable(  
        name="OA_ADDR_GPB" , 
        		joinColumns=
        		@JoinColumn(name="ADDR_ID_", referencedColumnName="ADDR_ID_"),
        		inverseJoinColumns=
        		@JoinColumn(name="GROUP_ID_", referencedColumnName="GROUP_ID_")
        )  
    private Set<AddrGrp> addrGrps=new HashSet<AddrGrp>();  


	/**
	 * Default Empty Constructor for class AddrBook
	 */
	public AddrBook() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class AddrBook
	 */
	public AddrBook(String in_addrId) {
		this.setAddrId(in_addrId);
	}

	public java.util.Set<AddrCont> getAddrConts() {
		return addrConts;
	}

	public void setAddrConts(java.util.Set<AddrCont> in_addrConts) {
		this.addrConts = in_addrConts;
	}

	/**
	 * 联系人ID * @return String
	 */
	public String getAddrId() {
		return this.addrId;
	}

	/**
	 * 设置 联系人ID
	 */
	public void setAddrId(String aValue) {
		this.addrId = aValue;
	}

	/**
	 * 姓名 * @return String
	 */
	public String getName() {
		return this.name;
	}

	public Set<AddrGrp> getAddrGrps() {
		return addrGrps;
	}

	public void setAddrGrps(Set<AddrGrp> addrGrps) {
		this.addrGrps = addrGrps;
	}

	/**
	 * 设置 姓名
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 公司 * @return String
	 */
	public String getCompany() {
		return this.company;
	}

	/**
	 * 设置 公司
	 */
	public void setCompany(String aValue) {
		this.company = aValue;
	}

	/**
	 * 部门 * @return String
	 */
	public String getDep() {
		return this.dep;
	}

	/**
	 * 设置 部门
	 */
	public void setDep(String aValue) {
		this.dep = aValue;
	}

	/**
	 * 职务 * @return String
	 */
	public String getPos() {
		return this.pos;
	}

	/**
	 * 设置 职务
	 */
	public void setPos(String aValue) {
		this.pos = aValue;
	}

	/**
	 * 主邮箱 * @return String
	 */
	public String getMail() {
		return this.mail;
	}

	/**
	 * 设置 主邮箱
	 */
	public void setMail(String aValue) {
		this.mail = aValue;
	}

	/**
	 * 主手机 * @return String
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 设置 主手机
	 */
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}

	/**
	 * 主电话 * @return String
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置 主电话
	 */
	public void setPhone(String aValue) {
		this.phone = aValue;
	}
	
	/**
	 * 主微信号 * @return String
	 */
	public String getWeixin() {
		return this.weixin;
	}
	
	/**
	 * 设置 主微信号
	 */
	public void setWeixin(String aValue) {
		this.weixin = aValue;
	}
	
	
	/**
	 * 国家 * @return String
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * 设置 国家
	 */
	public void setCountry(String aValue) {
		this.country = aValue;
	}

	/**
	 * 省份 * @return String
	 */
	public String getSate() {
		return this.sate;
	}

	/**
	 * 设置 省份
	 */
	public void setSate(String aValue) {
		this.sate = aValue;
	}

	/**
	 * 城市 * @return String
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * 设置 城市
	 */
	public void setCity(String aValue) {
		this.city = aValue;
	}

	/**
	 * 街道 * @return String
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * 设置 街道
	 */
	public void setStreet(String aValue) {
		this.street = aValue;
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
	 * QQ * @return String
	 */
	public String getQq() {
		return this.qq;
	}

	/**
	 * 设置 QQ
	 */
	public void setQq(String aValue) {
		this.qq = aValue;
	}

	/**
	 * 生日 * @return java.util.Date
	 */
	 @JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getBirthday() {
		return this.birthday;
	}

		/**
		 * 设置 生日
		 */
	public void setBirthday(java.util.Date aValue) {
		this.birthday = aValue;
	}

	/**
	 * 头像文件ID * @return String
	 */
	public String getPhotoId() {
		return this.photoId;
	}

	/**
	 * 设置 头像文件ID
	 */
	public void setPhotoId(String aValue) {
		this.photoId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.addrId;
	}

	@Override
	public Serializable getPkId() {
		return this.addrId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.addrId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AddrBook)) {
			return false;
		}
		AddrBook rhs = (AddrBook) object;
		return new EqualsBuilder().append(this.addrId, rhs.addrId).append(this.name, rhs.name).append(this.company, rhs.company).append(this.dep, rhs.dep).append(this.pos, rhs.pos).append(this.mail, rhs.mail).append(this.mobile, rhs.mobile).append(this.phone, rhs.phone).append(this.photoId, rhs.photoId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.addrId).append(this.name).append(this.company).append(this.dep).append(this.pos).append(this.mail).append(this.mobile).append(this.phone).append(this.photoId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("addrId", this.addrId).append("name", this.name).append("company", this.company).append("dep", this.dep).append("pos", this.pos).append("mail", this.mail).append("mobile", this.mobile).append("phone", this.phone).append("photoId", this.photoId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
