package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
 *  
 * 描述：HrErrandsRegister实体类定义
 * 请假、加班、外出登记
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_ERRANDS_REGISTER")
@TableDefine(title = "请假、加班、外出登记")
public class HrErrandsRegister extends BaseTenantEntity {
	
	public final static String TYPE_VACATION="VACATION";
	public final static String TYPE_OVERTIME="OVERTIME";
	public final static String TYPE_TRANS_REST="TRANSREST";
	public final static String TYPE_OUT="OUT";

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ER_ID_")
	protected String erId;
	/* 原因 */
	@FieldDefine(title = "原因")
	@Column(name = "REASON_")
	@Size(max = 500)
	@NotEmpty
	protected String reason;
	/* 开始日期 */
	@FieldDefine(title = "开始日期")
	@Column(name = "START_TIME_")
	protected java.util.Date startTime;
	/* 结束日期 */
	@FieldDefine(title = "结束日期")
	@Column(name = "END_TIME_")
	protected java.util.Date endTime;
	/* 标识 0=加班 1=请假 2=外出 */
	@FieldDefine(title = "标识")
	@Column(name = "FLAG_")
	protected Short flag;
	/* 流程实例ID */
	@FieldDefine(title = "流程实例ID")
	@Column(name = "BPM_INST_ID_")
	@Size(max = 64)
	protected String bpmInstId;
	/* 类型 */
	@FieldDefine(title = "类型")
	@Column(name = "TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String type;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	
	@FieldDefine(title = "请假")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrErrandsRegister", fetch = FetchType.LAZY)
	protected java.util.Set<HrVacationExt> hrVacationExts = new java.util.HashSet<HrVacationExt>();
	
	@FieldDefine(title = "加班")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrErrandsRegister", fetch = FetchType.LAZY)
	protected java.util.Set<HrOvertimeExt>  hrOvertimeExts= new java.util.HashSet<HrOvertimeExt>();
	
	@FieldDefine(title = "调休")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrErrandsRegister", fetch = FetchType.LAZY)
	protected java.util.Set<HrTransRestExt>  hrTransRestExts= new java.util.HashSet<HrTransRestExt>();


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	/**
	 * Default Empty Constructor for class HrErrandsRegister
	 */
	public HrErrandsRegister() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrErrandsRegister
	 */
	public HrErrandsRegister(String in_erId) {
		this.setErId(in_erId);
	}

	/**
	 * erID * @return String
	 */
	public String getErId() {
		return this.erId;
	}
	
	/**
	 * 设置 erID
	 */
	public void setErId(String aValue) {
		this.erId = aValue;
	}


	
	
	

	public java.util.Set<HrVacationExt> getHrVacationExts() {
		return hrVacationExts;
	}

	public void setHrVacationExts(java.util.Set<HrVacationExt> hrVacationExts) {
		this.hrVacationExts = hrVacationExts;
	}

	public java.util.Set<HrOvertimeExt> getHrOvertimeExts() {
		return hrOvertimeExts;
	}

	public void setHrOvertimeExts(java.util.Set<HrOvertimeExt> hrOvertimeExts) {
		this.hrOvertimeExts = hrOvertimeExts;
	}

	public java.util.Set<HrTransRestExt> getHrTransRestExts() {
		return hrTransRestExts;
	}

	public void setHrTransRestExts(java.util.Set<HrTransRestExt> hrTransRestExts) {
		this.hrTransRestExts = hrTransRestExts;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 开始日期 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 开始日期
	 */
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}

	/**
	 * 结束日期 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 结束日期
	 */
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}

	/**
	 * 标识 0=加班 1=请假 2=外出 * @return Short
	 */
	public Short getFlag() {
		return this.flag;
	}

	/**
	 * 设置 标识 0=加班 1=请假 2=外出
	 */
	public void setFlag(Short aValue) {
		this.flag = aValue;
	}

	/**
	 * 流程实例ID * @return String
	 */
	public String getBpmInstId() {
		return this.bpmInstId;
	}

	/**
	 * 设置 流程实例ID
	 */
	public void setBpmInstId(String aValue) {
		this.bpmInstId = aValue;
	}

	/**
	 * 类型 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 类型
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.erId;
	}

	@Override
	public Serializable getPkId() {
		return this.erId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.erId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrErrandsRegister)) {
			return false;
		}
		HrErrandsRegister rhs = (HrErrandsRegister) object;
		return new EqualsBuilder().append(this.erId, rhs.erId)
				.append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).append(this.flag, rhs.flag)
				.append(this.bpmInstId, rhs.bpmInstId).append(this.type, rhs.type).append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.erId).append(this.startTime)
				.append(this.endTime).append(this.flag).append(this.bpmInstId).append(this.type).append(this.tenantId)
				.append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("erId", this.erId)
				.append("startTime", this.startTime).append("endTime", this.endTime).append("flag", this.flag)
				.append("bpmInstId", this.bpmInstId).append("type", this.type).append("tenantId", this.tenantId)
				.append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
