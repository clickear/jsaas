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
 * 描述：HrVacationExt实体类定义
 * 请假扩展表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_VACATION_EXT")
@TableDefine(title = "请假扩展表")
@JsonIgnoreProperties(value="hrErrandsRegister")
public class HrVacationExt extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "VAC_ID_")
	protected String vacId;
	/* 请假类型 */
	@FieldDefine(title = "请假类型")
	@Column(name = "TYPE_")
	@Size(max = 20)
	protected String type;
	/* 工作安排 */
	@FieldDefine(title = "工作安排")
	@Column(name = "WORK_PLAN_")
	@Size(max = 500)
	protected String workPlan;
	
	@FieldDefine(title = "请假、加班、外出登记")
	@ManyToOne
	@JoinColumn(name = "ER_ID_")
	protected com.redxun.hr.core.entity.HrErrandsRegister hrErrandsRegister;

	/**
	 * Default Empty Constructor for class HrVacationExt
	 */
	public HrVacationExt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrVacationExt
	 */
	public HrVacationExt(String in_vacId) {
		this.setVacId(in_vacId);
	}

	
	
	public com.redxun.hr.core.entity.HrErrandsRegister getHrErrandsRegister() {
		return hrErrandsRegister;
	}

	public void setHrErrandsRegister(com.redxun.hr.core.entity.HrErrandsRegister hrErrandsRegister) {
		this.hrErrandsRegister = hrErrandsRegister;
	}

	/**
	 * 请假扩展ID * @return String
	 */
	public String getVacId() {
		return this.vacId;
	}

	/**
	 * 设置 请假扩展ID
	 */
	public void setVacId(String aValue) {
		this.vacId = aValue;
	}

	/**
	 * 请假类型 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 请假类型
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 工作安排 * @return String
	 */
	public String getWorkPlan() {
		return this.workPlan;
	}

	/**
	 * 设置 工作安排
	 */
	public void setWorkPlan(String aValue) {
		this.workPlan = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.vacId;
	}

	@Override
	public Serializable getPkId() {
		return this.vacId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.vacId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrVacationExt)) {
			return false;
		}
		HrVacationExt rhs = (HrVacationExt) object;
		return new EqualsBuilder().append(this.vacId, rhs.vacId).append(this.type, rhs.type).append(this.workPlan, rhs.workPlan).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.vacId).append(this.type).append(this.workPlan).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("vacId", this.vacId).append("type", this.type).append("workPlan", this.workPlan).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
