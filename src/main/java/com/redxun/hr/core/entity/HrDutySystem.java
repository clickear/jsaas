package com.redxun.hr.core.entity;

import java.io.Serializable;

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
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：HrDutySystem实体类定义
 * 班制\r\n
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_DUTY_SYSTEM")
@TableDefine(title = "班制")
@JsonIgnoreProperties(value={"hrDutys","hrDutySections","hrSystemSections"})
public class HrDutySystem extends BaseTenantEntity {

	public final static String TYPE_WEEKLY="WEEKLY";
	public final static String TYPE_PERIODIC="PERIODIC";
	
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SYSTEM_ID_")
	protected String systemId;
	/* 名称 */
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String name;
	/* 是否缺省 1＝缺省 0＝非缺省 */
	@FieldDefine(title = "是否缺省  1＝缺省 0＝非缺省")
	@Column(name = "IS_DEFAULT")
	@Size(max = 8)
	@NotEmpty
	protected String isDefault;
	/* 分类 一周=WEEKLY 周期性=PERIODIC */
	@FieldDefine(title = "分类  一周=WEEKLY   周期性=PERIODIC")
	@Column(name = "TYPE_")
	@Size(max = 20)
	protected String type;
	/* 作息班次 */
	@FieldDefine(title = "作息班次")
	@Column(name = "WORK_SECTION_")
	@Size(max = 64)
	protected String workSection;
	/* 休息日加班班次 */
	@FieldDefine(title = "休息日加班班次")
	@Column(name = "WK_REST_SECTION_")
	@Size(max = 64)
	protected String wkRestSection;
	/* 休息日 */
	@FieldDefine(title = "休息日")
	@Column(name = "REST_SECTION_")
	@Size(max = 100)
	protected String restSection;

	@FieldDefine(title = "排班")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrDutySystem", fetch = FetchType.LAZY)
	protected java.util.Set<HrDuty> hrDutys = new java.util.HashSet<HrDuty>();
/*	@FieldDefine(title = "班制班次表")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrDutySystem", fetch = FetchType.LAZY)
	protected java.util.Set<HrSystemSection> hrSystemSections = new java.util.HashSet<HrSystemSection>();*/
	
	@FieldDefine(title = "班次ID")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrDutySystem", fetch = FetchType.LAZY)
	protected java.util.Set<HrSystemSection> hrSystemSections = new java.util.HashSet<HrSystemSection>();
	
	/*@FieldDefine(title = "班次列表")
	@ManyToMany(cascade={CascadeType.PERSIST},fetch = FetchType.LAZY)     
	@JoinTable(name="HR_SYSTEM_SECTION",
				joinColumns={ @JoinColumn(name="SYSTEM_ID_",referencedColumnName="SYSTEM_ID_")    },    
				inverseJoinColumns={ @JoinColumn(name="SECTION_ID_",referencedColumnName="SECTION_ID_") })  
    protected java.util.Set<HrDutySection> hrDutySections = new java.util.HashSet<HrDutySection>();*/

	/**
	 * Default Empty Constructor for class HrDutySystem
	 */
	public HrDutySystem() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrDutySystem
	 */
	public HrDutySystem(String in_systemId) {
		this.setSystemId(in_systemId);
	}

	public java.util.Set<HrDuty> getHrDutys() {
		return hrDutys;
	}

	public void setHrDutys(java.util.Set<HrDuty> in_hrDutys) {
		this.hrDutys = in_hrDutys;
	}
	
	
	
	

	/*public java.util.Set<HrSystemSection> getHrSystemSections() {
		return hrSystemSections;
	}

	public void setHrSystemSections(java.util.Set<HrSystemSection> in_hrSystemSections) {
		this.hrSystemSections = in_hrSystemSections;
	}*/

	public java.util.Set<HrSystemSection> getHrSystemSections() {
		return hrSystemSections;
	}

	public void setHrSystemSections(java.util.Set<HrSystemSection> hrSystemSections) {
		this.hrSystemSections = hrSystemSections;
	}

	/*public java.util.Set<HrDutySection> getHrDutySections() {
		return hrDutySections;
	}

	public void setHrDutySections(java.util.Set<HrDutySection> hrDutySections) {
		this.hrDutySections = hrDutySections;
	}*/

	/**
	 * 班制编号 * @return String
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * 设置 班制编号
	 */
	public void setSystemId(String aValue) {
		this.systemId = aValue;
	}

	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 是否缺省 1＝缺省 0＝非缺省 * @return String
	 */
	public String getIsDefault() {
		return this.isDefault;
	}

	/**
	 * 设置 是否缺省 1＝缺省 0＝非缺省
	 */
	public void setIsDefault(String aValue) {
		this.isDefault = aValue;
	}

	/**
	 * 分类 一周=WEEKLY 周期性=PERIODIC * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 分类 一周=WEEKLY 周期性=PERIODIC
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 作息班次 * @return String
	 */
	public String getWorkSection() {
		return this.workSection;
	}

	/**
	 * 设置 作息班次
	 */
	public void setWorkSection(String aValue) {
		this.workSection = aValue;
	}

	/**
	 * 休息日加班班次 * @return String
	 */
	public String getWkRestSection() {
		return this.wkRestSection;
	}

	/**
	 * 设置 休息日加班班次
	 */
	public void setWkRestSection(String aValue) {
		this.wkRestSection = aValue;
	}

	/**
	 * 休息日 * @return String
	 */
	public String getRestSection() {
		return this.restSection;
	}

	/**
	 * 设置 休息日
	 */
	public void setRestSection(String aValue) {
		this.restSection = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.systemId;
	}

	@Override
	public Serializable getPkId() {
		return this.systemId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.systemId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrDutySystem)) {
			return false;
		}
		HrDutySystem rhs = (HrDutySystem) object;
		return new EqualsBuilder().append(this.systemId, rhs.systemId).append(this.name, rhs.name)
				.append(this.isDefault, rhs.isDefault).append(this.type, rhs.type)
				.append(this.workSection, rhs.workSection).append(this.wkRestSection, rhs.wkRestSection)
				.append(this.restSection, rhs.restSection).append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.systemId).append(this.name).append(this.isDefault)
				.append(this.type).append(this.workSection).append(this.wkRestSection).append(this.restSection)
				.append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("systemId", this.systemId).append("name", this.name)
				.append("isDefault", this.isDefault).append("type", this.type).append("workSection", this.workSection)
				.append("wkRestSection", this.wkRestSection).append("restSection", this.restSection)
				.append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
