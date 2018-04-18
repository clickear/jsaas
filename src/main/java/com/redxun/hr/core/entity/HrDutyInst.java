package com.redxun.hr.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * 描述：HrDutyInst实体类定义
 * 排班实例
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_DUTY_INST")
@TableDefine(title = "排班实例")
@JsonIgnoreProperties(value={"hrDutyInstExts","hrErrandsRegisters","hrHoliday"})
public class HrDutyInst extends BaseTenantEntity {
	
	public final static String TYPE_DUTY="DUTY";
	public final static String TYPE_REST="REST";
	public final static String TYPE_HOLIDAY="HOLIDAY";

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DUTY_INST_ID_")
	protected String dutyInstId;
	/*用户ID*/
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/*用户名称*/
	@FieldDefine(title = "用户名称")
	@Column(name = "USER_NAME_")
	@Size(max = 64)
	protected String userName;
	/*部门ID*/
	@FieldDefine(title = "部门ID")
	@Column(name = "DEP_ID_")
	@Size(max = 64)
	protected String depId;
	/*部门名称*/
	@FieldDefine(title = "部门名称")
	@Column(name = "DEP_NAME_")
	@Size(max = 64)
	protected String depName;
	/*班次ID*/
	@FieldDefine(title = "班次ID")
	@Column(name = "SECTION_ID_")
	@Size(max = 64)
	protected String sectionId;
	/* 班次名称 */
	@FieldDefine(title = "班次名称")
	@Column(name = "SECTION_NAME_")
	@Size(max = 16)
	protected String sectionName;
	/* 班次简称 */
	@FieldDefine(title = "班次简称")
	@Column(name = "SECTION_SHORT_NAME_")
	@Size(max = 4)
	protected String sectionShortName;
	/* 班制ID */
	@FieldDefine(title = "班制ID")
	@Column(name = "SYSTEM_ID_")
	@Size(max = 64)
	protected String systemId;
	/* 班制名字 */
	@FieldDefine(title = "班制名字")
	@Column(name = "SYSTEM_NAME_")
	@Size(max = 100)
	protected String systemName;
	/* 实例分类 */
	@FieldDefine(title = "实例分类")
	@Column(name = "TYPE_")
	@Size(max = 10)
	protected String type;
	/* 日期 */
	@FieldDefine(title = "日期")
	@Column(name = "DATE_")
	protected java.util.Date date;
	
	/* 请假申请 */
	@FieldDefine(title = "请假申请")
	@Column(name = "VAC_APP_")
	@Size(max = 65535)
	protected String vacApp;
	
	/* 加班申请 */
	@FieldDefine(title = "加班申请")
	@Column(name = "OT_APP_")
	@Size(max = 65535)
	protected String otApp;
	
	/* 调休申请 */
	@FieldDefine(title = "调休申请")
	@Column(name = "TR_APP_")
	@Size(max = 65535)
	protected String trApp;
	
	/* 出差申请 */
	@FieldDefine(title = "出差申请")
	@Column(name = "OUT_APP_")
	@Size(max = 65535)
	protected String outApp;
	
	@FieldDefine(title = "排班实例扩展")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrDutyInst", fetch = FetchType.LAZY)
	protected java.util.Set<HrDutyInstExt> hrDutyInstExts = new java.util.HashSet<HrDutyInstExt>();
	
	@FieldDefine(title = "放假ID")
	@ManyToOne
	@JoinColumn(name = "HOLIDAY_ID_")
	protected com.redxun.hr.core.entity.HrHoliday hrHoliday;
	
/*	@FieldDefine(title = "放假记录")
	@ManyToMany(cascade={CascadeType.PERSIST},fetch = FetchType.LAZY)     
	@JoinTable(name="HR_DUTY_HOLIDAY",
				joinColumns={ @JoinColumn(name="DUTY_INST_ID_",referencedColumnName="DUTY_INST_ID_")    },    
				inverseJoinColumns={ @JoinColumn(name="HOLIDAY_ID_",referencedColumnName="HOLIDAY_ID_") })  
    protected java.util.Set<HrHoliday>  hrHolidays= new java.util.HashSet<HrHoliday>();
*/
	/**
	 * Default Empty Constructor for class HrDutyInst
	 */
	public HrDutyInst() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrDutyInst
	 */
	public HrDutyInst(String in_dutyInstId) {
		this.setDutyInstId(in_dutyInstId);
	}

	/**
	 * * @return String
	 */
	public String getDutyInstId() {
		return this.dutyInstId;
	}
	
	
	

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getVacApp() {
		return vacApp;
	}

	public void setVacApp(String vacApp) {
		this.vacApp = vacApp;
	}

	public String getOtApp() {
		return otApp;
	}

	public void setOtApp(String otApp) {
		this.otApp = otApp;
	}

	public String getTrApp() {
		return trApp;
	}

	public void setTrApp(String trApp) {
		this.trApp = trApp;
	}

	public String getOutApp() {
		return outApp;
	}

	public void setOutApp(String outApp) {
		this.outApp = outApp;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public com.redxun.hr.core.entity.HrHoliday getHrHoliday() {
		return hrHoliday;
	}

	public void setHrHoliday(com.redxun.hr.core.entity.HrHoliday hrHoliday) {
		this.hrHoliday = hrHoliday;
	}

	public java.util.Set<HrDutyInstExt> getHrDutyInstExts() {
		return hrDutyInstExts;
	}

	public void setHrDutyInstExts(java.util.Set<HrDutyInstExt> hrDutyInstExts) {
		this.hrDutyInstExts = hrDutyInstExts;
	}

	/**
	 * 设置
	 */
	public void setDutyInstId(String aValue) {
		this.dutyInstId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getSectionId() {
		return this.sectionId;
	}

	/**
	 * 设置
	 */
	public void setSectionId(String aValue) {
		this.sectionId = aValue;
	}

	

	/**
	 * 日期 * @return java.util.Date
	 */
	public java.util.Date getDate() {
		return this.date;
	}

	/**
	 * 设置 日期
	 */
	public void setDate(java.util.Date aValue) {
		this.date = aValue;
	}
	
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionShortName() {
		return sectionShortName;
	}

	public void setSectionShortName(String sectionShortName) {
		this.sectionShortName = sectionShortName;
	}

	@Override
	public String getIdentifyLabel() {
		return this.dutyInstId;
	}

	@Override
	public Serializable getPkId() {
		return this.dutyInstId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.dutyInstId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrDutyInst)) {
			return false;
		}
		HrDutyInst rhs = (HrDutyInst) object;
		return new EqualsBuilder().append(this.dutyInstId, rhs.dutyInstId).append(this.userId, rhs.userId).append(this.sectionId, rhs.sectionId).append(this.date, rhs.date).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.dutyInstId).append(this.userId).append(this.sectionId).append(this.date).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("dutyInstId", this.dutyInstId).append("userId", this.userId).append("sectionId", this.sectionId).append("date", this.date).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
