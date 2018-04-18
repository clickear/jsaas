package com.redxun.oa.res.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

/**
 * <pre>
 * 描述：OaCarApp实体类定义
 * 车辆申请
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_CAR_APP")
@TableDefine(title = "车辆申请")
public class OaCarApp extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "APP_ID_")
	protected String appId;
	/* 汽车类别 */
	@FieldDefine(title = "汽车类别")
	@Column(name = "CAR_CAT_")
	@Size(max = 50)
	@NotEmpty
	protected String carCat;
	/* 车辆名称 */
	@FieldDefine(title = "车辆名称")
	@Column(name = "CAR_NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String carName;
	/* 起始时间 */
	@FieldDefine(title = "起始时间")
	@Column(name = "START_TIME_")
	protected Timestamp startTime;
	/* 终止时间 */
	@FieldDefine(title = "终止时间")
	@Column(name = "END_TIME_")
	protected Timestamp endTime;
	/* 驾驶员 */
	@FieldDefine(title = "驾驶员")
	@Column(name = "DRIVER_")
	@Size(max = 20)
	protected String driver;
	/* 行程类别 */
	@FieldDefine(title = "行程类别")
	@Column(name = "CATEGORY_")
	@Size(max = 64)
	protected String category;
	/* 车辆状态 */
	@FieldDefine(title = "车辆状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;
	/* 目的地点 */
	@FieldDefine(title = "目的地点")
	@Column(name = "DEST_LOC_")
	@Size(max = 100)
	protected String destLoc;
	/* 行驶里程 */
	@FieldDefine(title = "行驶里程")
	@Column(name = "TRAV_MILES_")
	protected Integer travMiles;
	/* 使用人员 */
	@FieldDefine(title = "使用人员")
	@Column(name = "USE_MANS_")
	@Size(max = 20)
	protected String useMans;
	/* 使用说明 */
	@FieldDefine(title = "使用说明")
	@Column(name = "MEMO_")
	@Size(max = 65535)
	protected String memo;
	/* 流程实例ID */
	@FieldDefine(title = "流程实例ID")
	@Column(name = "BPM_INST_")
	@Size(max = 64)
	protected String bpmInst;
	@FieldDefine(title = "车辆信息")
	@ManyToOne
	@JoinColumn(name = "CAR_ID_")
	protected com.redxun.oa.res.entity.OaCar oaCar;

	/**
	 * Default Empty Constructor for class OaCarApp
	 */
	public OaCarApp() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaCarApp
	 */
	public OaCarApp(String in_appId) {
		this.setAppId(in_appId);
	}

	public com.redxun.oa.res.entity.OaCar getOaCar() {
		return oaCar;
	}

	public void setOaCar(com.redxun.oa.res.entity.OaCar in_oaCar) {
		this.oaCar = in_oaCar;
	}

	/**
	 * * @return String
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * 设置
	 */
	public void setAppId(String aValue) {
		this.appId = aValue;
	}

	/**
	 * 汽车类别 * @return String
	 */
	public String getCarCat() {
		return this.carCat;
	}

	/**
	 * 设置 汽车类别
	 */
	public void setCarCat(String aValue) {
		this.carCat = aValue;
	}

	/**
	 * 车辆ID * @return String
	 */
	public String getCarId() {
		return this.getOaCar() == null ? null : this.getOaCar().getCarId();
	}

	/**
	 * 设置 车辆ID
	 */
	public void setCarId(String aValue) {
		if (aValue == null) {
			oaCar = null;
		} else if (oaCar == null) {
			oaCar = new com.redxun.oa.res.entity.OaCar(aValue);
		} else {
			oaCar.setCarId(aValue);
		}
	}

	/**
	 * 车辆名称 * @return String
	 */
	public String getCarName() {
		return this.carName;
	}

	/**
	 * 设置 车辆名称
	 */
	public void setCarName(String aValue) {
		this.carName = aValue;
	}

	/**
	 * 起始时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 起始时间
	 */
	public void setStartTime(Timestamp aValue) {
		this.startTime = aValue;
	}

	/**
	 * 终止时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 终止时间
	 */
	public void setEndTime(Timestamp aValue) {
		this.endTime = aValue;
	}

	/**
	 * 驾驶员 * @return String
	 */
	public String getDriver() {
		return this.driver;
	}

	/**
	 * 设置 驾驶员
	 */
	public void setDriver(String aValue) {
		this.driver = aValue;
	}

	/**
	 * 行程类别 * @return String
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * 设置 行程类别
	 */
	public void setCategory(String aValue) {
		this.category = aValue;
	}
	
	/**
	 * 车辆状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 车辆状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 目的地点 * @return String
	 */
	public String getDestLoc() {
		return this.destLoc;
	}

	/**
	 * 设置 目的地点
	 */
	public void setDestLoc(String aValue) {
		this.destLoc = aValue;
	}

	/**
	 * 行驶里程 * @return Integer
	 */
	public Integer getTravMiles() {
		return this.travMiles;
	}

	/**
	 * 设置 行驶里程
	 */
	public void setTravMiles(Integer aValue) {
		this.travMiles = aValue;
	}

	/**
	 * 使用人员 * @return String
	 */
	public String getUseMans() {
		return this.useMans;
	}

	/**
	 * 设置 使用人员
	 */
	public void setUseMans(String aValue) {
		this.useMans = aValue;
	}

	/**
	 * 使用说明 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 使用说明
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	/**
	 * 流程实例ID * @return String
	 */
	public String getBpmInst() {
		return this.bpmInst;
	}

	/**
	 * 设置 流程实例ID
	 */
	public void setBpmInst(String aValue) {
		this.bpmInst = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.appId;
	}

	@Override
	public Serializable getPkId() {
		return this.appId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.appId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaCarApp)) {
			return false;
		}
		OaCarApp rhs = (OaCarApp) object;
		return new EqualsBuilder().append(this.appId, rhs.appId)
				.append(this.carCat, rhs.carCat)
				.append(this.carName, rhs.carName)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.driver, rhs.driver)
				.append(this.category, rhs.category)
				.append(this.status, rhs.status)
				.append(this.destLoc, rhs.destLoc)
				.append(this.travMiles, rhs.travMiles)
				.append(this.useMans, rhs.useMans).append(this.memo, rhs.memo)
				.append(this.bpmInst, rhs.bpmInst)
				.append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.appId)
				.append(this.carCat).append(this.carName)
				.append(this.startTime).append(this.endTime).append(this.status)
				.append(this.driver).append(this.category).append(this.destLoc)
				.append(this.travMiles).append(this.useMans).append(this.memo)
				.append(this.bpmInst).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("appId", this.appId)
				.append("carCat", this.carCat).append("carName", this.carName)
				.append("startTime", this.startTime)
				.append("endTime", this.endTime).append("driver", this.driver)
				.append("status", this.status).append("category", this.category)
				.append("destLoc", this.destLoc)
				.append("travMiles", this.travMiles)
				.append("useMans", this.useMans).append("memo", this.memo)
				.append("bpmInst", this.bpmInst)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
