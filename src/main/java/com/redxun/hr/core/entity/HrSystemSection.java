package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：HrSystemSection实体类定义
 * 班制班次表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_SYSTEM_SECTION")
@TableDefine(title = "班制班次表")
public class HrSystemSection extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SYSTEM_SECTION_ID_")
	protected String systemSectionId;
	/**/
	@FieldDefine(title = "")
	@Column(name = "WORKDAY_")
	protected Integer workday;
	@FieldDefine(title = "班制")
	@ManyToOne
	@JoinColumn(name = "SYSTEM_ID_")
	protected com.redxun.hr.core.entity.HrDutySystem hrDutySystem;
	@FieldDefine(title = "班次")
	@ManyToOne
	@JoinColumn(name = "SECTION_ID_")
	protected com.redxun.hr.core.entity.HrDutySection hrDutySection;

	/**
	 * Default Empty Constructor for class HrSystemSection
	 */
	public HrSystemSection() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrSystemSection
	 */
	public HrSystemSection(String in_systemSectionId) {
		this.setSystemSectionId(in_systemSectionId);
	}

	public com.redxun.hr.core.entity.HrDutySystem getHrDutySystem() {
		return hrDutySystem;
	}

	public void setHrDutySystem(com.redxun.hr.core.entity.HrDutySystem in_hrDutySystem) {
		this.hrDutySystem = in_hrDutySystem;
	}

	public com.redxun.hr.core.entity.HrDutySection getHrDutySection() {
		return hrDutySection;
	}

	public void setHrDutySection(com.redxun.hr.core.entity.HrDutySection in_hrDutySection) {
		this.hrDutySection = in_hrDutySection;
	}

	/**
	 * 主键 * @return String
	 */
	public String getSystemSectionId() {
		return this.systemSectionId;
	}

	/**
	 * 设置 主键
	 */
	public void setSystemSectionId(String aValue) {
		this.systemSectionId = aValue;
	}

	/**
	 * 班次ID * @return String
	 */
	public String getSectionId() {
		return this.getHrDutySection() == null ? null : this.getHrDutySection().getSectionId();
	}

	/**
	 * 设置 班次ID
	 */
	public void setSectionId(String aValue) {
		if (aValue == null) {
			hrDutySection = null;
		} else if (hrDutySection == null) {
			hrDutySection = new com.redxun.hr.core.entity.HrDutySection(aValue);
		} else {
			hrDutySection.setSectionId(aValue);
		}
	}

	/**
	 * 班制ID * @return String
	 */
	public String getSystemId() {
		return this.getHrDutySystem() == null ? null : this.getHrDutySystem().getSystemId();
	}

	/**
	 * 设置 班制ID
	 */
	public void setSystemId(String aValue) {
		if (aValue == null) {
			hrDutySystem = null;
		} else if (hrDutySystem == null) {
			hrDutySystem = new com.redxun.hr.core.entity.HrDutySystem(aValue);
		} else {
			hrDutySystem.setSystemId(aValue);
		}
	}

	/**
	 * * @return Integer
	 */
	public Integer getWorkday() {
		return this.workday;
	}

	/**
	 * 设置
	 */
	public void setWorkday(Integer aValue) {
		this.workday = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.systemSectionId;
	}

	@Override
	public Serializable getPkId() {
		return this.systemSectionId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.systemSectionId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrSystemSection)) {
			return false;
		}
		HrSystemSection rhs = (HrSystemSection) object;
		return new EqualsBuilder().append(this.systemSectionId, rhs.systemSectionId).append(this.workday, rhs.workday).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.systemSectionId).append(this.workday).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("systemSectionId", this.systemSectionId).append("workday", this.workday).toString();
	}

}
