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

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：HrHoliday实体类定义
 * 放假记录
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "HR_HOLIDAY")
@TableDefine(title = "放假记录")
public class HrHoliday extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "HOLIDAY_ID_")
	protected String holidayId;
	/* 开始日期 */
	@FieldDefine(title = "开始日期")
	@Column(name = "START_DAY_")
	protected java.util.Date startDay;
	/* 结束日期 */
	@FieldDefine(title = "结束日期")
	@Column(name = "END_DAY_")
	protected java.util.Date endDay;
	/* 假期描述 */
	@FieldDefine(title = "假期描述")
	@Column(name = "DESC_")
	@Size(max = 512)
	protected String desc;
	/* 所属用户ID */
	@FieldDefine(title="所属用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 65535)
	protected String userId;
	
	/* 用户名 */
	@FieldDefine(title="用户名")
	@Column(name = "USER_NAME_")
	@Size(max = 65535)
	protected String userName;
	
	/* 假期名称 */
	@FieldDefine(title = "假期名称")
	@Column(name = "NAME_")
	@Size(max =20)
	@NotEmpty
	protected String name;
	
	/* 用户组ID */
	@FieldDefine(title = "用户组ID")
	@Column(name = "GROUP_ID_")
	@Size(max = 65535)
	protected String groupId;
	
	/* 用户组ID */
	@FieldDefine(title = "用户组名")
	@Column(name = "GROUP_NAME_")
	@Size(max = 65535)
	protected String groupName;
	
	/* 班次ID */
	@FieldDefine(title = "班次ID")
	@Column(name = "SYSTEM_ID_")
	@Size(max = 65535)
	protected String systemId;
	
	@FieldDefine(title = "排版实例")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hrHoliday", fetch = FetchType.LAZY)
	protected java.util.Set<HrDutyInst> hrDutyInsts = new java.util.HashSet<HrDutyInst>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Default Empty Constructor for class HrHoliday
	 */
	public HrHoliday() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class HrHoliday
	 */
	public HrHoliday(String in_holidayId) {
		this.setHolidayId(in_holidayId);
	}

	
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 假期ID * @return String
	 */
	public String getHolidayId() {
		return this.holidayId;
	}
	
	

	/**
	 * 设置 假期ID
	 */
	public void setHolidayId(String aValue) {
		this.holidayId = aValue;
	}

	/**
	 * 开始日期 * @return java.util.Date
	 */
	public java.util.Date getStartDay() {
		return this.startDay;
	}

	/**
	 * 设置 开始日期
	 */
	public void setStartDay(java.util.Date aValue) {
		this.startDay = aValue;
	}
	
	
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	

	public java.util.Set<HrDutyInst> getHrDutyInsts() {
		return hrDutyInsts;
	}

	public void setHrDutyInsts(java.util.Set<HrDutyInst> hrDutyInsts) {
		this.hrDutyInsts = hrDutyInsts;
	}

	/**
	 * 结束日期 * @return java.util.Date
	 */
	public java.util.Date getEndDay() {
		return this.endDay;
	}

	/**
	 * 设置 结束日期
	 */
	public void setEndDay(java.util.Date aValue) {
		this.endDay = aValue;
	}

	/**
	 * 假期描述 * @return String
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 设置 假期描述
	 */
	public void setDesc(String aValue) {
		this.desc = aValue;
	}

	/**
	 * 所属用户 若为某员工的假期，则为员工自己的id * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 所属用户 若为某员工的假期，则为员工自己的id
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.holidayId;
	}

	@Override
	public Serializable getPkId() {
		return this.holidayId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.holidayId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HrHoliday)) {
			return false;
		}
		HrHoliday rhs = (HrHoliday) object;
		return new EqualsBuilder().append(this.holidayId, rhs.holidayId).append(this.startDay, rhs.startDay)
				.append(this.endDay, rhs.endDay).append(this.desc, rhs.desc).append(this.userId, rhs.userId)
				.append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.holidayId).append(this.startDay)
				.append(this.endDay).append(this.desc).append(this.userId).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("holidayId", this.holidayId).append("startDay", this.startDay)
				.append("endDay", this.endDay).append("desc", this.desc).append("userId", this.userId)
				.append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
