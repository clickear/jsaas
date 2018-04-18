package com.redxun.oa.personal.entity;

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
 * 描述：通讯录联系实体类定义
 * 通讯录联系信息
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_ADDR_CONT")
@TableDefine(title = "通讯录联系信息")
public class AddrCont extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CONT_ID_")
	protected String contId;
	/*
	 * 类型 手机号=MOBILE 家庭地址=HOME_ADDRESS 工作地址=WORK_ADDRESS QQ=QQ 微信=WEI_XIN
	 * GoogleTalk=GOOGLE-TALK 工作=WORK_INFO 备注=MEMO
	 */
	@FieldDefine(title = "类型")
	@Column(name = "TYPE_")
	@Size(max = 50)
	@NotEmpty
	protected String type;
	/* 联系主信息 */
	@FieldDefine(title = "联系主信息")
	@Column(name = "CONTACT_")
	@Size(max = 255)
	protected String contact;
	/* 联系扩展字段1 */
	@FieldDefine(title = "联系扩展字段1")
	@Column(name = "EXT1_")
	@Size(max = 100)
	protected String ext1;
	/* 联系扩展字段2 */
	@FieldDefine(title = "联系扩展字段2")
	@Column(name = "EXT2_")
	@Size(max = 100)
	protected String ext2;
	/* 联系扩展字段3 */
	@FieldDefine(title = "联系扩展字段3")
	@Column(name = "EXT3_")
	@Size(max = 100)
	protected String ext3;
	/* 联系扩展字段4 */
	@FieldDefine(title = "联系扩展字段4")
	@Column(name = "EXT4_")
	@Size(max = 100)
	protected String ext4;
	@FieldDefine(title = "通讯录联系人")
	@ManyToOne
	@JoinColumn(name = "ADDR_ID_")
	protected com.redxun.oa.personal.entity.AddrBook addrBook;

	/**
	 * Default Empty Constructor for class AddrCont
	 */
	public AddrCont() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class AddrCont
	 */
	public AddrCont(String in_contId) {
		this.setContId(in_contId);
	}

	public com.redxun.oa.personal.entity.AddrBook getAddrBook() {
		return addrBook;
	}

	public void setAddrBook(com.redxun.oa.personal.entity.AddrBook in_addrBook) {
		this.addrBook = in_addrBook;
	}

	/**
	 * 联系信息ID * @return String
	 */
	public String getContId() {
		return this.contId;
	}

	/**
	 * 设置 联系信息ID
	 */
	public void setContId(String aValue) {
		this.contId = aValue;
	}

	/**
	 * 联系人ID * @return String
	 */
	public String getAddrId() {
		return this.getAddrBook() == null ? null : this.getAddrBook().getAddrId();
	}

	/**
	 * 设置 联系人ID
	 */
	public void setAddrId(String aValue) {
		if (aValue == null) {
			addrBook = null;
		} else if (addrBook == null) {
			addrBook = new com.redxun.oa.personal.entity.AddrBook(aValue);
		} else {
			addrBook.setAddrId(aValue);
		}
	}

	/**
	 * 类型 手机号=MOBILE 家庭地址=HOME_ADDRESS 工作地址=WORK_ADDRESS QQ=QQ 微信=WEI_XIN
	 * GoogleTalk=GOOGLE-TALK 工作=WORK_INFO 备注=MEMO * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 类型 手机号=MOBILE 家庭地址=HOME_ADDRESS 工作地址=WORK_ADDRESS QQ=QQ 微信=WEI_XIN
	 * GoogleTalk=GOOGLE-TALK 工作=WORK_INFO 备注=MEMO
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 联系主信息 * @return String
	 */
	public String getContact() {
		return this.contact;
	}

	/**
	 * 设置 联系主信息
	 */
	public void setContact(String aValue) {
		this.contact = aValue;
	}

	/**
	 * 联系扩展字段1 * @return String
	 */
	public String getExt1() {
		return this.ext1;
	}

	/**
	 * 设置 联系扩展字段1
	 */
	public void setExt1(String aValue) {
		this.ext1 = aValue;
	}

	/**
	 * 联系扩展字段2 * @return String
	 */
	public String getExt2() {
		return this.ext2;
	}

	/**
	 * 设置 联系扩展字段2
	 */
	public void setExt2(String aValue) {
		this.ext2 = aValue;
	}

	/**
	 * 联系扩展字段3 * @return String
	 */
	public String getExt3() {
		return this.ext3;
	}

	/**
	 * 设置 联系扩展字段3
	 */
	public void setExt3(String aValue) {
		this.ext3 = aValue;
	}

	/**
	 * 联系扩展字段4 * @return String
	 */
	public String getExt4() {
		return this.ext4;
	}

	/**
	 * 设置 联系扩展字段4
	 */
	public void setExt4(String aValue) {
		this.ext4 = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.contId;
	}

	@Override
	public Serializable getPkId() {
		return this.contId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.contId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AddrCont)) {
			return false;
		}
		AddrCont rhs = (AddrCont) object;
		return new EqualsBuilder().append(this.contId, rhs.contId).append(this.type, rhs.type).append(this.contact, rhs.contact).append(this.ext1, rhs.ext1).append(this.ext2, rhs.ext2).append(this.ext3, rhs.ext3).append(this.ext4, rhs.ext4).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.contId).append(this.type).append(this.contact).append(this.ext1).append(this.ext2).append(this.ext3).append(this.ext4).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("contId", this.contId).append("type", this.type).append("contact", this.contact).append("ext1", this.ext1).append("ext2", this.ext2).append("ext3", this.ext3).append("ext4", this.ext4).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
