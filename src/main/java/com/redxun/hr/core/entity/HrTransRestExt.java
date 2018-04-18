package com.redxun.hr.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 描述：HrTransRestExt实体类定义
 * 调休扩展表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_TRANS_REST_EXT")
@TableDefine(title = "调休扩展表")
@JsonIgnoreProperties(value="hrErrandsRegister")
public class HrTransRestExt extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "TR_ID_")
	protected String trId;
	/* 调休类型 */
	@FieldDefine(title = "调休类型")
	@Column(name = "TYPE_")
	@Size(max = 20)
	protected String type;
	/* 上班时间 */
	@FieldDefine(title = "上班时间")
	@Column(name = "WORK_")
	protected java.util.Date work;
	/* 休息时间 */
	@FieldDefine(title = "休息时间")
	@Column(name = "REST_")
	protected java.util.Date rest;
	
	@FieldDefine(title = "请假、加班、外出登记")
	@ManyToOne
	@JoinColumn(name = "ER_ID_")
	protected com.redxun.hr.core.entity.HrErrandsRegister hrErrandsRegister;

	/**
	 * Default Empty Constructor for class HrTransRestExt
	 */
	public HrTransRestExt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrTransRestExt
	 */
	public HrTransRestExt(String in_trId) {
		this.setTrId(in_trId);
	}

	/**
	 * 调休ID * @return String
	 */
	public String getTrId() {
		return this.trId;
	}

	/**
	 * 设置 调休ID
	 */
	public void setTrId(String aValue) {
		this.trId = aValue;
	}
	
	

	public com.redxun.hr.core.entity.HrErrandsRegister getHrErrandsRegister() {
		return hrErrandsRegister;
	}

	public void setHrErrandsRegister(com.redxun.hr.core.entity.HrErrandsRegister hrErrandsRegister) {
		this.hrErrandsRegister = hrErrandsRegister;
	}

	/**
	 * 调休类型 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 调休类型
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 上班时间 * @return java.util.Date
	 */
	public java.util.Date getWork() {
		return this.work;
	}

	/**
	 * 设置 上班时间
	 */
	public void setWork(java.util.Date aValue) {
		this.work = aValue;
	}

	/**
	 * 休息时间 * @return java.util.Date
	 */
	public java.util.Date getRest() {
		return this.rest;
	}

	/**
	 * 设置 休息时间
	 */
	public void setRest(java.util.Date aValue) {
		this.rest = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.trId;
	}

	@Override
	public Serializable getPkId() {
		return this.trId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.trId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrTransRestExt)) {
			return false;
		}
		HrTransRestExt rhs = (HrTransRestExt) object;
		return new EqualsBuilder().append(this.trId, rhs.trId).append(this.type, rhs.type).append(this.work, rhs.work).append(this.rest, rhs.rest).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.trId).append(this.type).append(this.work).append(this.rest).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("trId", this.trId).append("type", this.type).append("work", this.work).append("rest", this.rest).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
