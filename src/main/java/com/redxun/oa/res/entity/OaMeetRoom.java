package com.redxun.oa.res.entity;

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
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OaMeetRoom实体类定义
 * 会议室
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_MEET_ROOM")
@TableDefine(title = "会议室")
@JsonIgnoreProperties(value={"oaMeetings"})
public class OaMeetRoom extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ROOM_ID_")
	protected String roomId;
	/* 会议室名称 */
	@FieldDefine(title = "会议室名称")
	@Column(name = "NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String name;
	/* 会议室地址 */
	@FieldDefine(title = "会议室地址")
	@Column(name = "LOCATION_")
	@Size(max = 255)
	protected String location;
	/* 会议室描述 */
	@FieldDefine(title = "会议室描述")
	@Column(name = "DESCP_")
	@Size(max = 512)
	protected String descp;

	@FieldDefine(title = "会议申请")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaMeetRoom", fetch = FetchType.LAZY)
	protected java.util.Set<OaMeeting> oaMeetings = new java.util.HashSet<OaMeeting>();

	/**
	 * Default Empty Constructor for class OaMeetRoom
	 */
	public OaMeetRoom() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaMeetRoom
	 */
	public OaMeetRoom(String in_roomId) {
		this.setRoomId(in_roomId);
	}

	public java.util.Set<OaMeeting> getOaMeetings() {
		return oaMeetings;
	}

	public void setOaMeetings(java.util.Set<OaMeeting> in_oaMeetings) {
		this.oaMeetings = in_oaMeetings;
	}

	/**
	 * 会议室ID * @return String
	 */
	public String getRoomId() {
		return this.roomId;
	}

	/**
	 * 设置 会议室ID
	 */
	public void setRoomId(String aValue) {
		this.roomId = aValue;
	}

	/**
	 * 会议室名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 会议室名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 会议室地址 * @return String
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * 设置 会议室地址
	 */
	public void setLocation(String aValue) {
		this.location = aValue;
	}

	/**
	 * 会议室描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 会议室描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.roomId;
	}

	@Override
	public Serializable getPkId() {
		return this.roomId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.roomId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaMeetRoom)) {
			return false;
		}
		OaMeetRoom rhs = (OaMeetRoom) object;
		return new EqualsBuilder().append(this.roomId, rhs.roomId)
				.append(this.name, rhs.name)
				.append(this.location, rhs.location)
				.append(this.descp, rhs.descp)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.roomId)
				.append(this.name).append(this.location).append(this.descp)
				.append(this.tenantId).append(this.createTime)
				.append(this.createBy).append(this.updateTime)
				.append(this.updateBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("roomId", this.roomId)
				.append("name", this.name).append("location", this.location)
				.append("descp", this.descp).append("tenantId", this.tenantId)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy).toString();
	}

}
