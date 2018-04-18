package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseEntity;
import com.redxun.org.api.model.ITenant;

/**
 * <pre>
 * 描述：SysInst实体类定义
 * 注册机构
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_INST")
@TableDefine(title = "注册机构")
public class SysInst extends BaseEntity implements ITenant {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "INST_ID_")
	protected String instId;
	/* 公司中文名 */
	@FieldDefine(title = "公司中文名", defaultCol = MBoolean.YES)
	@Column(name = "NAME_CN_")
	@Size(max = 256)
	@NotEmpty
	protected String nameCn;
	/* 公司英文名 */
	@FieldDefine(title = "公司英文名", defaultCol = MBoolean.YES)
	@Column(name = "NAME_EN_")
	@Size(max = 256)
	@NotEmpty
	protected String nameEn;
	
	@FieldDefine(title = "营业执照编号", defaultCol = MBoolean.YES)
	@Column(name = "BUS_LICE_NO_")
	@Size(max = 50)
	@NotEmpty
	protected String busLiceNo;
	
	/**/
	@FieldDefine(title = "组织机构编号", defaultCol = MBoolean.YES)
	@Column(name = "INST_NO_")
	@Size(max = 50)
	@NotEmpty
	protected String instNo;
	
	@FieldDefine(title = "公司域名", defaultCol = MBoolean.YES)
	@Column(name = "DOMAIN_")
	@Size(max = 100)
	@NotEmpty
	protected String domain;
	
	//@FieldDefine(title = "营业执照附件ID")
	@Column(name = "BUS_LICE_FILE_ID_")
	@Size(max = 64)
	protected String busLiceFileId;
	
	//@FieldDefine(title = "组织机构代码证附件ID")
	@Column(name = "REG_CODE_FILE_ID_")
	@Size(max = 64)
	protected String regCodeFileId;
	
	/* 公司简称(中文) */
	@FieldDefine(title = "公司简称(中文)", defaultCol = MBoolean.YES)
	@Column(name = "NAME_CN_S_")
	@Size(max = 80)
	protected String nameCnS;
	/* 公司简称(英文) */
	@FieldDefine(title = "公司简称(英文)", defaultCol = MBoolean.YES)
	@Column(name = "NAME_EN_S_")
	@Size(max = 80)
	protected String nameEnS;
	/* 公司法人 */
	@FieldDefine(title = "公司法人", defaultCol = MBoolean.YES)
	@Column(name = "LEGAL_MAN_")
	@Size(max = 64)
	protected String legalMan;
	/* 公司描述 */
	@FieldDefine(title = "公司描述")
	@Column(name = "DESCP_")
	@Size(max = 2147483647)
	protected String descp;
	/* 地址 */
	@FieldDefine(title = "地址", defaultCol = MBoolean.YES, group="联系信息")
	@Column(name = "ADDRESS_")
	@Size(max = 128)
	protected String address;
	/* 联系电话 */
	@FieldDefine(title = "联系电话", defaultCol = MBoolean.YES, group="联系信息")
	@Column(name = "PHONE_")
	@Size(max = 30)
	protected String phone;
	
	/* 联系电话 */
	@FieldDefine(title = "邮箱", defaultCol = MBoolean.YES, group="联系信息")
	@Column(name = "EMAIL_")
	@Size(max =255)
	protected String email;
	
	/* 传真 */
	@FieldDefine(title = "传真", defaultCol = MBoolean.YES, group="联系信息")
	@Column(name = "FAX_")
	@Size(max = 30)
	protected String fax;
	/* 联系人 */
	@FieldDefine(title = "联系人", defaultCol = MBoolean.YES, group="联系信息")
	@Column(name = "CONTRACTOR_")
	@Size(max = 30)
	protected String contractor;
	/* 状态 */
	@FieldDefine(title = "状态", defaultCol = MBoolean.YES)
	@Column(name = "STATUS_")
	@Size(max = 30)
	protected String status;
	
	@FieldDefine(title = "数据源名称", defaultCol = MBoolean.YES)
	@Column(name = "DS_NAME_")
	@Size(max = 30)
	protected String dsName="";
	
	@FieldDefine(title = "数据源别名", defaultCol = MBoolean.YES)
	@Column(name = "DS_ALIAS_")
	@Size(max = 30)
	protected String dsAlias="";
	
	@FieldDefine(title = "机构类型", defaultCol = MBoolean.YES)
	@Column(name = "INST_TYPE_")
	@Size(max = 50)
	protected String instType="";
	
	
	@FieldDefine(title = "后台访问首页", defaultCol = MBoolean.YES)
	@Column(name = "HOME_URL_")
	@Size(max = 50)
	protected String homeUrl;
	
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	
	@Column(name = "PATH_")
	@Size(max = 1024)
	protected String path;
	
	//注册码
	@Transient
	protected String validCode;

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	/**
	 * Default Empty Constructor for class SysInst
	 */
	public SysInst() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysInst
	 */
	public SysInst(String in_instId) {
		this.setInstId(in_instId);
	}

	/**
	 * * @return String
	 */
	public String getInstId() {
		return this.instId;
	}

	/**
	 * 设置
	 */
	public void setInstId(String aValue) {
		this.instId = aValue;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	/**
	 * 公司中文名 * @return String
	 */
	public String getNameCn() {
		return this.nameCn;
	}

	/**
	 * 设置 公司中文名
	 */
	public void setNameCn(String aValue) {
		this.nameCn = aValue;
	}

	/**
	 * 公司英文名 * @return String
	 */
	public String getNameEn() {
		return this.nameEn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 设置 公司英文名
	 */
	public void setNameEn(String aValue) {
		this.nameEn = aValue;
	}

	/**
	 *  返回 机构编号
	 * * @return String
	 */
	public String getInstNo() {
		return this.instNo;
	}

	/**
	 * 设置 机构编号
	 */
	public void setInstNo(String aValue) {
		this.instNo = aValue;
	}

	/**
	 * 公司简称(中文) * @return String
	 */
	public String getNameCnS() {
		return this.nameCnS;
	}

	/**
	 * 设置 公司简称(中文)
	 */
	public void setNameCnS(String aValue) {
		this.nameCnS = aValue;
	}

	/**
	 * 公司简称(英文) * @return String
	 */
	public String getNameEnS() {
		return this.nameEnS;
	}

	/**
	 * 设置 公司简称(英文)
	 */
	public void setNameEnS(String aValue) {
		this.nameEnS = aValue;
	}

	/**
	 * 公司法人 * @return String
	 */
	public String getLegalMan() {
		return this.legalMan;
	}

	/**
	 * 设置 公司法人
	 */
	public void setLegalMan(String aValue) {
		this.legalMan = aValue;
	}

	/**
	 * 公司描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 公司描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
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
	 * 联系电话 * @return String
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置 联系电话
	 */
	public void setPhone(String aValue) {
		this.phone = aValue;
	}

	/**
	 * 传真 * @return String
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * 设置 传真
	 */
	public void setFax(String aValue) {
		this.fax = aValue;
	}

	/**
	 * 联系人 * @return String
	 */
	public String getContractor() {
		return this.contractor;
	}

	/**
	 * 设置 联系人
	 */
	public void setContractor(String aValue) {
		this.contractor = aValue;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBusLiceNo() {
		return busLiceNo;
	}

	public void setBusLiceNo(String busLiceNo) {
		this.busLiceNo = busLiceNo;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBusLiceFileId() {
		return busLiceFileId;
	}

	public void setBusLiceFileId(String busLiceFileId) {
		this.busLiceFileId = busLiceFileId;
	}

	public String getRegCodeFileId() {
		return regCodeFileId;
	}

	public void setRegCodeFileId(String regCodeFileId) {
		this.regCodeFileId = regCodeFileId;
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

	@Override
	public String getIdentifyLabel() {
		return this.nameCnS;
	}

	@Override
	public Serializable getPkId() {
		return this.instId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.instId = (String) pkId;
	}
	
	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDsAlias() {
		return dsAlias;
	}

	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysInst)) {
			return false;
		}
		SysInst rhs = (SysInst) object;
		return new EqualsBuilder().append(this.instId, rhs.instId)
				.append(this.nameCn, rhs.nameCn)
				.append(this.nameEn, rhs.nameEn)
				.append(this.instNo, rhs.instNo)
				.append(this.nameCnS, rhs.nameCnS)
				.append(this.nameEnS, rhs.nameEnS)
				.append(this.legalMan, rhs.legalMan)
				.append(this.descp, rhs.descp)
				.append(this.address, rhs.address)
				.append(this.phone, rhs.phone).append(this.fax, rhs.fax)
				.append(this.contractor, rhs.contractor)
				.append(this.status, rhs.status)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.instId)
				.append(this.nameCn).append(this.nameEn).append(this.instNo)
				.append(this.nameCnS).append(this.nameEnS)
				.append(this.legalMan).append(this.descp).append(this.address)
				.append(this.phone).append(this.fax).append(this.contractor)
				.append(this.status).append(this.createBy)
				.append(this.createTime)
				.append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("instId", this.instId)
				.append("nameCn", this.nameCn).append("nameEn", this.nameEn)
				.append("instNo", this.instNo).append("nameCnS", this.nameCnS)
				.append("nameEnS", this.nameEnS)
				.append("legalMan", this.legalMan).append("descp", this.descp)
				.append("address", this.address).append("phone", this.phone)
				.append("fax", this.fax).append("contractor", this.contractor)
				.append("status", this.status)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

	@Override
	public String getTenantId() {
		return instId;
	}

	@Override
	public String getTenantName() {
		return nameCn;
	}

	@Override
	public String getShortName() {
		return this.nameCnS;
	}

	
	
}
