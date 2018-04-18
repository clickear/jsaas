package com.redxun.oa.product.entity;

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

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OaAssetsBid实体类定义
 * 【资产申请】
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_ASSETS_BID")
@TableDefine(title = "【资产申请】")
public class OaAssetsBid extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "BID_ID_")
	protected String bidId;
	/* 参数ID(不做关联) */
	@FieldDefine(title = "参数ID(不做关联)")
	@Column(name = "PARA_ID_")
	@Size(max = 64)
	protected String paraId;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_")
	protected java.util.Date start;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_")
	protected java.util.Date end;
	/* 申请说明 */
	@FieldDefine(title = "申请说明")
	@Column(name = "MEMO_")
	@Size(max = 65535)
	protected String memo;
	/* 申请人员 */
	@FieldDefine(title = "申请人员")
	@Column(name = "USE_MANS_")
	@Size(max = 20)
	protected String useMans;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	/* 流程实例ID */
	@FieldDefine(title = "流程实例ID")
	@Column(name = "BPM_INST_ID_")
	@Size(max = 64)
	protected String bpmInstId;
	@FieldDefine(title = "资产信息")
	@ManyToOne
	@JoinColumn(name = "ASS_ID_")
	protected com.redxun.oa.product.entity.OaAssets oaAssets;

	/**
	 * Default Empty Constructor for class OaAssetsBid
	 */
	public OaAssetsBid() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaAssetsBid
	 */
	public OaAssetsBid(String in_bidId) {
		this.setBidId(in_bidId);
	}

	public com.redxun.oa.product.entity.OaAssets getOaAssets() {
		return oaAssets;
	}

	public void setOaAssets(com.redxun.oa.product.entity.OaAssets in_oaAssets) {
		this.oaAssets = in_oaAssets;
	}

	/**
	 * 申请ID * @return String
	 */
	public String getBidId() {
		return this.bidId;
	}

	/**
	 * 设置 申请ID
	 */
	public void setBidId(String aValue) {
		this.bidId = aValue;
	}

	/**
	 * 资产ID * @return String
	 */
	public String getAssId() {
		return this.getOaAssets() == null ? null : this.getOaAssets()
				.getAssId();
	}

	/**
	 * 设置 资产ID
	 */
	public void setAssId(String aValue) {
		if (aValue == null) {
			oaAssets = null;
		} else if (oaAssets == null) {
			oaAssets = new com.redxun.oa.product.entity.OaAssets(aValue);
		} else {
			oaAssets.setAssId(aValue);
		}
	}

	/**
	 * 参数ID(不做关联) * @return String
	 */
	public String getParaId() {
		return this.paraId;
	}

	/**
	 * 设置 参数ID(不做关联)
	 */
	public void setParaId(String aValue) {
		this.paraId = aValue;
	}

	/**
	 * 开始时间 * @return java.util.Date
	 */
	public java.util.Date getStart() {
		return this.start;
	}

	/**
	 * 设置 开始时间
	 */
	public void setStart(java.util.Date aValue) {
		this.start = aValue;
	}

	/**
	 * 结束时间 * @return java.util.Date
	 */
	public java.util.Date getEnd() {
		return this.end;
	}

	/**
	 * 设置 结束时间
	 */
	public void setEnd(java.util.Date aValue) {
		this.end = aValue;
	}

	/**
	 * 申请说明 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 申请说明
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	/**
	 * 申请人员 * @return String
	 */
	public String getUseMans() {
		return this.useMans;
	}

	/**
	 * 设置 申请人员
	 */
	public void setUseMans(String aValue) {
		this.useMans = aValue;
	}

	/**
	 * 状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
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

	@Override
	public String getIdentifyLabel() {
		return this.bidId;
	}

	@Override
	public Serializable getPkId() {
		return this.bidId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.bidId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaAssetsBid)) {
			return false;
		}
		OaAssetsBid rhs = (OaAssetsBid) object;
		return new EqualsBuilder().append(this.bidId, rhs.bidId)
				.append(this.paraId, rhs.paraId).append(this.start, rhs.start)
				.append(this.end, rhs.end).append(this.memo, rhs.memo)
				.append(this.useMans, rhs.useMans)
				.append(this.status, rhs.status)
				.append(this.bpmInstId, rhs.bpmInstId)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.bidId)
				.append(this.paraId).append(this.start).append(this.end)
				.append(this.memo).append(this.useMans).append(this.status)
				.append(this.bpmInstId).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("bidId", this.bidId)
				.append("paraId", this.paraId).append("start", this.start)
				.append("end", this.end).append("memo", this.memo)
				.append("useMans", this.useMans).append("status", this.status)
				.append("bpmInstId", this.bpmInstId)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
