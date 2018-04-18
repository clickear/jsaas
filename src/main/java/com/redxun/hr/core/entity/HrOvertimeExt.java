package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：HrOvertimeExt实体类定义
 * 加班
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_OVERTIME_EXT")
@TableDefine(title = "加班")
@JsonIgnoreProperties(value="hrErrandsRegister")
public class HrOvertimeExt extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "OT_ID_")
	protected String otId;
	/* 加班类型 */
	@FieldDefine(title = "加班类型")
	@Column(name = "TYPE_")
	@Size(max = 20)
	protected String type;
	/* 标题 */
	@FieldDefine(title = "标题")
	@Column(name = "TITLE_")
	@Size(max = 50)
	protected String title;
	/* 备注 */
	@FieldDefine(title = "备注")
	@Column(name = "DESC_")
	@Size(max = 500)
	protected String desc;
	/* 结算 */
	@FieldDefine(title = "结算")
	@Column(name = "PAY_")
	@Size(max = 20)
	protected String pay;
	
	@FieldDefine(title = "请假、加班、外出登记")
	@ManyToOne
	@JoinColumn(name = "ER_ID_")
	protected com.redxun.hr.core.entity.HrErrandsRegister hrErrandsRegister;

	/**
	 * Default Empty Constructor for class HrOvertimeExt
	 */
	public HrOvertimeExt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrOvertimeExt
	 */
	public HrOvertimeExt(String in_otId) {
		this.setOtId(in_otId);
	}

	/**
	 * 加班ID * @return String
	 */
	public String getOtId() {
		return this.otId;
	}

	/**
	 * 设置 加班ID
	 */
	public void setOtId(String aValue) {
		this.otId = aValue;
	}
	
	

	public com.redxun.hr.core.entity.HrErrandsRegister getHrErrandsRegister() {
		return hrErrandsRegister;
	}

	public void setHrErrandsRegister(com.redxun.hr.core.entity.HrErrandsRegister hrErrandsRegister) {
		this.hrErrandsRegister = hrErrandsRegister;
	}

	/**
	 * 加班类型 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 加班类型
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}
	
	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 标题 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置 标题
	 */
	public void setTitle(String aValue) {
		this.title = aValue;
	}

	/**
	 * 结算 * @return String
	 */
	public String getPay() {
		return this.pay;
	}

	/**
	 * 设置 结算
	 */
	public void setPay(String aValue) {
		this.pay = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.otId;
	}

	@Override
	public Serializable getPkId() {
		return this.otId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.otId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrOvertimeExt)) {
			return false;
		}
		HrOvertimeExt rhs = (HrOvertimeExt) object;
		return new EqualsBuilder().append(this.otId, rhs.otId).append(this.type, rhs.type).append(this.title, rhs.title).append(this.pay, rhs.pay).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.otId).append(this.type).append(this.title).append(this.pay).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("otId", this.otId).append("type", this.type).append("title", this.title).append("pay", this.pay).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
