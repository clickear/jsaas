package com.redxun.oa.res.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * 描述：OaCar实体类定义
 * 车辆信息
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_CAR")
@TableDefine(title = "车辆信息")
@JsonIgnoreProperties(value="oaCarApps")
public class OaCar extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CAR_ID_")
	protected String carId;
	/**/
	@FieldDefine(title = "汽车类型")
	@Column(name = "SYS_DIC_ID_")
	@Size(max = 64)
	protected String sysDicId;
	/* 车辆名称 */
	@FieldDefine(title = "车辆名称")
	@Column(name = "NAME_")
	@Size(max = 60)
	@NotEmpty
	protected String name;
	/* 车牌号 */
	@FieldDefine(title = "车牌号")
	@Column(name = "CAR_NO_")
	@Size(max = 20)
	@NotEmpty
	protected String carNo;
	/* 行驶里程 */
	@FieldDefine(title = "行驶里程")
	@Column(name = "TRAVEL_MILES_")
	protected Integer travelMiles;
	/* 发动机号 */
	@FieldDefine(title = "发动机号")
	@Column(name = "ENGINE_NUM_")
	@Size(max = 20)
	protected String engineNum;
	/* 车辆识别代号 */
	@FieldDefine(title = "车辆识别代号")
	@Column(name = "FRAME_NO_")
	@Size(max = 20)
	protected String frameNo;
	/* 品牌 */
	@FieldDefine(title = "品牌")
	@Column(name = "BRAND_")
	@Size(max = 64)
	protected String brand;
	/* 型号 */
	@FieldDefine(title = "型号")
	@Column(name = "MODEL_")
	@Size(max = 64)
	protected String model;
	/* 车辆载重 */
	@FieldDefine(title = "车辆载重")
	@Column(name = "WEIGHT_")
	protected Integer weight;
	/* 车辆座位 */
	@FieldDefine(title = "车辆座位")
	@Column(name = "SEATS_")
	protected Integer seats;
	/* 购买日期 */
	@FieldDefine(title = "购买日期")
	@Column(name = "BUY_DATE_")
	protected Timestamp buyDate;
	/* 购买价格 */
	@FieldDefine(title = "购买价格")
	@Column(name = "PRICE_")
	protected java.math.BigDecimal price;
	/* 年检情况 */
	@FieldDefine(title = "年检情况")
	@Column(name = "ANNUAL_INSP_")
	@Size(max = 65535)
	protected String annualInsp;
	/* 保险情况 */
	@FieldDefine(title = "保险情况")
	@Column(name = "INSURANCE_")
	@Size(max = 65535)
	protected String insurance;
	/* 保养维修情况 */
	@FieldDefine(title = "保养维修情况")
	@Column(name = "MAINTENS_")
	@Size(max = 65535)
	protected String maintens;
	/* 备注信息 */
	@FieldDefine(title = "备注信息")
	@Column(name = "MEMO_")
	@Size(max = 65535)
	protected String memo;
	/* 车辆照片 */
	@FieldDefine(title = "车辆照片")
	@Column(name = "PHOTO_IDS_")
	@Size(max = 512)
	protected String photoIds;
	/*
	 * 车辆状态
	 */
	@FieldDefine(title = "车辆状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;

	@FieldDefine(title = "车辆申请")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaCar", fetch = FetchType.LAZY)
	protected java.util.Set<OaCarApp> oaCarApps = new java.util.HashSet<OaCarApp>();

	/**
	 * Default Empty Constructor for class OaCar
	 */
	public OaCar() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaCar
	 */
	public OaCar(String in_carId) {
		this.setCarId(in_carId);
	}

	public java.util.Set<OaCarApp> getOaCarApps() {
		return oaCarApps;
	}

	public void setOaCarApps(java.util.Set<OaCarApp> in_oaCarApps) {
		this.oaCarApps = in_oaCarApps;
	}

	/**
	 * 车辆ID * @return String
	 */
	public String getCarId() {
		return this.carId;
	}

	/**
	 * 设置 车辆ID
	 */
	public void setCarId(String aValue) {
		this.carId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getSysDicId() {
		return this.sysDicId;
	}

	/**
	 * 设置
	 */
	public void setSysDicId(String aValue) {
		this.sysDicId = aValue;
	}

	/**
	 * 车辆名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 车辆名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 车牌号 * @return String
	 */
	public String getCarNo() {
		return this.carNo;
	}

	/**
	 * 设置 车牌号
	 */
	public void setCarNo(String aValue) {
		this.carNo = aValue;
	}

	/**
	 * 行驶里程 * @return Integer
	 */
	public Integer getTravelMiles() {
		return this.travelMiles;
	}

	/**
	 * 设置 行驶里程
	 */
	public void setTravelMiles(Integer aValue) {
		this.travelMiles = aValue;
	}

	/**
	 * 发动机号 * @return String
	 */
	public String getEngineNum() {
		return this.engineNum;
	}

	/**
	 * 设置 发动机号
	 */
	public void setEngineNum(String aValue) {
		this.engineNum = aValue;
	}

	/**
	 * 车辆识别代号 * @return String
	 */
	public String getFrameNo() {
		return this.frameNo;
	}

	/**
	 * 设置 车辆识别代号
	 */
	public void setFrameNo(String aValue) {
		this.frameNo = aValue;
	}

	/**
	 * 品牌 * @return String
	 */
	public String getBrand() {
		return this.brand;
	}

	/**
	 * 设置 品牌
	 */
	public void setBrand(String aValue) {
		this.brand = aValue;
	}

	/**
	 * 型号 * @return String
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * 设置 型号
	 */
	public void setModel(String aValue) {
		this.model = aValue;
	}

	/**
	 * 车辆载重 * @return Integer
	 */
	public Integer getWeight() {
		return this.weight;
	}

	/**
	 * 设置 车辆载重
	 */
	public void setWeight(Integer aValue) {
		this.weight = aValue;
	}

	/**
	 * 车辆座位 * @return Integer
	 */
	public Integer getSeats() {
		return this.seats;
	}

	/**
	 * 设置 车辆座位
	 */
	public void setSeats(Integer aValue) {
		this.seats = aValue;
	}

	/**
	 * 购买日期 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getBuyDate() {
		return this.buyDate;
	}

	/**
	 * 设置 购买日期
	 */
	public void setBuyDate(Timestamp aValue) {
		this.buyDate = aValue;
	}

	/**
	 * 购买价格 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getPrice() {
		return this.price;
	}

	/**
	 * 设置 购买价格
	 */
	public void setPrice(java.math.BigDecimal aValue) {
		this.price = aValue;
	}

	/**
	 * 年检情况 * @return String
	 */
	public String getAnnualInsp() {
		return this.annualInsp;
	}

	/**
	 * 设置 年检情况
	 */
	public void setAnnualInsp(String aValue) {
		this.annualInsp = aValue;
	}

	/**
	 * 保险情况 * @return String
	 */
	public String getInsurance() {
		return this.insurance;
	}

	/**
	 * 设置 保险情况
	 */
	public void setInsurance(String aValue) {
		this.insurance = aValue;
	}

	/**
	 * 保养维修情况 * @return String
	 */
	public String getMaintens() {
		return this.maintens;
	}

	/**
	 * 设置 保养维修情况
	 */
	public void setMaintens(String aValue) {
		this.maintens = aValue;
	}

	/**
	 * 备注信息 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 备注信息
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	/**
	 * 车辆照片 * @return String
	 */
	public String getPhotoIds() {
		return this.photoIds;
	}

	/**
	 * 设置 车辆照片
	 */
	public void setPhotoIds(String aValue) {
		this.photoIds = aValue;
	}

	/**
	 * 车辆状态 IN_USED=在使用 IN_FREE=空闲 SCRAP=报废 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 车辆状态 IN_USED=在使用 IN_FREE=空闲 SCRAP=报废
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.carId;
	}

	@Override
	public Serializable getPkId() {
		return this.carId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.carId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaCar)) {
			return false;
		}
		OaCar rhs = (OaCar) object;
		return new EqualsBuilder().append(this.carId, rhs.carId)
				.append(this.sysDicId, rhs.sysDicId)
				.append(this.name, rhs.name).append(this.carNo, rhs.carNo)
				.append(this.travelMiles, rhs.travelMiles)
				.append(this.engineNum, rhs.engineNum)
				.append(this.frameNo, rhs.frameNo)
				.append(this.brand, rhs.brand).append(this.model, rhs.model)
				.append(this.weight, rhs.weight).append(this.seats, rhs.seats)
				.append(this.buyDate, rhs.buyDate)
				.append(this.price, rhs.price)
				.append(this.annualInsp, rhs.annualInsp)
				.append(this.insurance, rhs.insurance)
				.append(this.maintens, rhs.maintens)
				.append(this.memo, rhs.memo)
				.append(this.photoIds, rhs.photoIds)
				.append(this.status, rhs.status)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.carId)
				.append(this.sysDicId).append(this.name).append(this.carNo)
				.append(this.travelMiles).append(this.engineNum)
				.append(this.frameNo).append(this.brand).append(this.model)
				.append(this.weight).append(this.seats).append(this.buyDate)
				.append(this.price).append(this.annualInsp)
				.append(this.insurance).append(this.maintens).append(this.memo)
				.append(this.photoIds).append(this.status)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("carId", this.carId)
				.append("sysDicId", this.sysDicId).append("name", this.name)
				.append("carNo", this.carNo)
				.append("travelMiles", this.travelMiles)
				.append("engineNum", this.engineNum)
				.append("frameNo", this.frameNo).append("brand", this.brand)
				.append("model", this.model).append("weight", this.weight)
				.append("seats", this.seats).append("buyDate", this.buyDate)
				.append("price", this.price)
				.append("annualInsp", this.annualInsp)
				.append("insurance", this.insurance)
				.append("maintens", this.maintens).append("memo", this.memo)
				.append("photoIds", this.photoIds)
				.append("status", this.status)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
