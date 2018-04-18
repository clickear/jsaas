package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：HrSectionConfig实体类定义
 * TODO: add class/table comments
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_SECTION_CONFIG")
@TableDefine(title = "TODO: add class/table comments")
public class HrSectionConfig extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CONFIG_ID_")
	protected String configId;
	/* 上班开始签到时间 */
	@FieldDefine(title = "上班开始签到时间")
	@Column(name = "IN_START_TIME_")
	protected Long inStartTime;
	/* 上班结束签到时间 */
	@FieldDefine(title = "上班结束签到时间")
	@Column(name = "IN_END_TIME_")
	protected Long inEndTime;
	/* 下班开始签到时间 */
	@FieldDefine(title = "下班开始签到时间")
	@Column(name = "OUT_START_TIME_")
	protected Long outStartTime;
	/* 下班结束签到时间 */
	@FieldDefine(title = "下班结束签到时间")
	@Column(name = "OUT_END_TIME_")
	protected Long outEndTime;

	/**
	 * Default Empty Constructor for class HrSectionConfig
	 */
	public HrSectionConfig() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrSectionConfig
	 */
	public HrSectionConfig(String in_configId) {
		this.setConfigId(in_configId);
	}

	/**
	 * 参数标号 * @return String
	 */
	public String getConfigId() {
		return this.configId;
	}

	/**
	 * 设置 参数标号
	 */
	public void setConfigId(String aValue) {
		this.configId = aValue;
	}

	/**
	 * 上班开始签到时间 * @return java.util.Date
	 */
	public Long getInStartTime() {
		return this.inStartTime;
	}

	/**
	 * 设置 上班开始签到时间
	 */
	public void setInStartTime(Long aValue) {
		this.inStartTime = aValue;
	}

	/**
	 * 上班结束签到时间 * @return java.util.Date
	 */
	public Long getInEndTime() {
		return this.inEndTime;
	}

	/**
	 * 设置 上班结束签到时间
	 */
	public void setInEndTime(Long aValue) {
		this.inEndTime = aValue;
	}

	/**
	 * 下班开始签到时间 * @return java.util.Date
	 */
	public Long getOutStartTime() {
		return this.outStartTime;
	}

	/**
	 * 设置 下班开始签到时间
	 */
	public void setOutStartTime(Long aValue) {
		this.outStartTime = aValue;
	}

	/**
	 * 下班结束签到时间 * @return java.util.Date
	 */
	public Long getOutEndTime() {
		return this.outEndTime;
	}

	/**
	 * 设置 下班结束签到时间
	 */
	public void setOutEndTime(Long aValue) {
		this.outEndTime = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.configId;
	}

	@Override
	public Serializable getPkId() {
		return this.configId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.configId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrSectionConfig)) {
			return false;
		}
		HrSectionConfig rhs = (HrSectionConfig) object;
		return new EqualsBuilder().append(this.configId, rhs.configId).append(this.inStartTime, rhs.inStartTime)
				.append(this.inEndTime, rhs.inEndTime).append(this.outStartTime, rhs.outStartTime)
				.append(this.outEndTime, rhs.outEndTime).append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.configId).append(this.inStartTime)
				.append(this.inEndTime).append(this.outStartTime).append(this.outEndTime).append(this.tenantId)
				.append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("configId", this.configId).append("inStartTime", this.inStartTime)
				.append("inEndTime", this.inEndTime).append("outStartTime", this.outStartTime)
				.append("outEndTime", this.outEndTime).append("tenantId", this.tenantId)
				.append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
