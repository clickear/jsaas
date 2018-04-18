package com.redxun.oa.res.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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
 * 描述：OaMeeting实体类定义
 * 会议申请
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_MEETING")
@TableDefine(title = "会议申请")
@JsonIgnoreProperties(value={"oaMeetAtts"})
public class OaMeeting extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "MEET_ID_")
	protected String meetId;
	/* 会议名称 */
	@FieldDefine(title = "会议名称")
	@Column(name = "NAME_")
	@Size(max = 255)
	@NotEmpty
	protected String name;
	/* 会议描述 */
	@FieldDefine(title = "会议描述")
	@Column(name = "DESCP_")
	@Size(max = 65535)
	protected String descp;
	/* 开始时间 */
	@FieldDefine(title = "开始时间")
	@Column(name = "START_")
	protected Timestamp start;
	/* 结束时间 */
	@FieldDefine(title = "结束时间")
	@Column(name = "END_")
	protected Timestamp end;
	/* 会议地点 */
	@FieldDefine(title = "会议地点")
	@Column(name = "LOCATION_")
	@Size(max = 255)
	@NotEmpty
	protected String location;
	/* 会议预算 */
	@FieldDefine(title = "会议预算")
	@Column(name = "BUDGET_")
	protected java.math.BigDecimal budget;
	/* 主持人 */
	@FieldDefine(title = "主持人")
	@Column(name = "HOST_UID_")
	@Size(max = 64)
	protected String hostUid;
	@Transient
	protected String hostName;
	/* 会议记录人 */
	@FieldDefine(title = "会议记录人")
	@Column(name = "RECORD_UID_")
	@Size(max = 64)
	protected String recordUid;
	@Transient
	protected String recordName;
	/* 重要程度 */
	@FieldDefine(title = "重要程度")
	@Column(name = "IMP_DEGREE_")
	@Size(max = 20)
	protected String impDegree;
	/* 会议状态 */
	@FieldDefine(title = "会议状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;
	/* 会议总结 */
	@FieldDefine(title = "会议总结")
	@Column(name = "SUMMARY_")
	@Size(max = 65535)
	protected String summary;
	/* 流程审批实例ID */
	@FieldDefine(title = "流程审批实例ID")
	@Column(name = "BPM_INST_ID_")
	@Size(max = 64)
	protected String bpmInstId;
	/* 附近ID */
	@FieldDefine(title = "附件ID")
	@Column(name = "FILE_IDS_")
	@Size(max = 512)
	protected String fileIds;
	@FieldDefine(title = "会议室")
	@ManyToOne
	@JoinColumn(name = "ROOM_ID_")
	protected com.redxun.oa.res.entity.OaMeetRoom oaMeetRoom;

	@FieldDefine(title = "会议参与人")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaMeeting", fetch = FetchType.LAZY)
	protected java.util.Set<OaMeetAtt> oaMeetAtts = new java.util.HashSet<OaMeetAtt>();

	/**
	 * Default Empty Constructor for class OaMeeting
	 */
	public OaMeeting() {
		super();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	/**
	 * Default Key Fields Constructor for class OaMeeting
	 */
	public OaMeeting(String in_meetId) {
		this.setMeetId(in_meetId);
	}

	public com.redxun.oa.res.entity.OaMeetRoom getOaMeetRoom() {
		return oaMeetRoom;
	}

	public void setOaMeetRoom(com.redxun.oa.res.entity.OaMeetRoom in_oaMeetRoom) {
		this.oaMeetRoom = in_oaMeetRoom;
	}

	public java.util.Set<OaMeetAtt> getOaMeetAtts() {
		return oaMeetAtts;
	}

	public void setOaMeetAtts(java.util.Set<OaMeetAtt> in_oaMeetAtts) {
		this.oaMeetAtts = in_oaMeetAtts;
	}

	/**
	 * 会议ID * @return String
	 */
	public String getMeetId() {
		return this.meetId;
	}

	/**
	 * 设置 会议ID
	 */
	public void setMeetId(String aValue) {
		this.meetId = aValue;
	}

	/**
	 * 会议名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 会议名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 会议描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 会议描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 开始时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getStart() {
		return this.start;
	}


	/**
	 * 结束时间 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getEnd() {
		return this.end;
	}


	public void setStart(Timestamp start) {
		this.start = start;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	/**
	 * 会议地点 * @return String
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * 设置 会议地点
	 */
	public void setLocation(String aValue) {
		this.location = aValue;
	}

	/**
	 * 会议室ID * @return String
	 */
	public String getRoomId() {
		return this.getOaMeetRoom() == null ? null : this.getOaMeetRoom()
				.getRoomId();
	}

	/**
	 * 设置 会议室ID
	 */
	public void setRoomId(String aValue) {
		if (aValue == null) {
			oaMeetRoom = null;
		} else if (oaMeetRoom == null) {
			oaMeetRoom = new com.redxun.oa.res.entity.OaMeetRoom(aValue);
		} else {
			oaMeetRoom.setRoomId(aValue);
		}
	}

	/**
	 * 会议预算 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal getBudget() {
		return this.budget;
	}

	/**
	 * 设置 会议预算
	 */
	public void setBudget(java.math.BigDecimal aValue) {
		this.budget = aValue;
	}

	/**
	 * 主持人 * @return String
	 */
	public String getHostUid() {
		return this.hostUid;
	}

	/**
	 * 设置 主持人
	 */
	public void setHostUid(String aValue) {
		this.hostUid = aValue;
	}

	/**
	 * 会议记录人 * @return String
	 */
	public String getRecordUid() {
		return this.recordUid;
	}

	/**
	 * 设置 会议记录人
	 */
	public void setRecordUid(String aValue) {
		this.recordUid = aValue;
	}

	/**
	 * 重要程度 * @return String
	 */
	public String getImpDegree() {
		return this.impDegree;
	}

	/**
	 * 设置 重要程度
	 */
	public void setImpDegree(String aValue) {
		this.impDegree = aValue;
	}

	/**
	 * 会议状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 会议状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 会议总结 * @return String
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * 设置 会议总结
	 */
	public void setSummary(String aValue) {
		this.summary = aValue;
	}

	/**
	 * 流程审批实例ID * @return String
	 */
	public String getBpmInstId() {
		return this.bpmInstId;
	}

	/**
	 * 设置 流程审批实例ID
	 */
	public void setBpmInstId(String aValue) {
		this.bpmInstId = aValue;
	}

	/**
	 * 附近ID * @return String
	 */
	public String getFileIds() {
		return this.fileIds;
	}

	/**
	 * 设置 附近ID
	 */
	public void setFileIds(String aValue) {
		this.fileIds = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.meetId;
	}

	@Override
	public Serializable getPkId() {
		return this.meetId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.meetId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaMeeting)) {
			return false;
		}
		OaMeeting rhs = (OaMeeting) object;
		return new EqualsBuilder().append(this.meetId, rhs.meetId)
				.append(this.name, rhs.name).append(this.descp, rhs.descp)
				.append(this.start, rhs.start).append(this.end, rhs.end)
				.append(this.location, rhs.location)
				.append(this.budget, rhs.budget)
				.append(this.hostUid, rhs.hostUid)
				.append(this.recordUid, rhs.recordUid)
				.append(this.impDegree, rhs.impDegree)
				.append(this.status, rhs.status)
				.append(this.summary, rhs.summary)
				.append(this.bpmInstId, rhs.bpmInstId)
				.append(this.fileIds, rhs.fileIds)
				.append(this.tenantId, rhs.tenantId)
				.append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy)
				.append(this.updateTime, rhs.updateTime)
				.append(this.updateBy, rhs.updateBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.meetId)
				.append(this.name).append(this.descp).append(this.start)
				.append(this.end).append(this.location).append(this.budget)
				.append(this.hostUid).append(this.recordUid)
				.append(this.impDegree).append(this.status)
				.append(this.summary).append(this.bpmInstId)
				.append(this.fileIds).append(this.tenantId)
				.append(this.createTime).append(this.createBy)
				.append(this.updateTime).append(this.updateBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("meetId", this.meetId)
				.append("name", this.name).append("descp", this.descp)
				.append("start", this.start).append("end", this.end)
				.append("location", this.location)
				.append("budget", this.budget).append("hostUid", this.hostUid)
				.append("recordUid", this.recordUid)
				.append("impDegree", this.impDegree)
				.append("status", this.status).append("summary", this.summary)
				.append("bpmInstId", this.bpmInstId)
				.append("fileIds", this.fileIds)
				.append("tenantId", this.tenantId)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy).toString();
	}

}
