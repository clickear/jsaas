package com.redxun.oa.res.entity;

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
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OaMeetAtt实体类定义
 * 会议参与人
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_MEET_ATT")
@TableDefine(title = "会议参与人")
public class OaMeetAtt extends BaseTenantEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ATT_ID_")
	protected String attId;
	/* 用户ID */
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/* 用户名 */
	@FieldDefine(title = "用户名")
	@Column(name = "FULLNAME_")
	@Size(max = 20)
	protected String fullName;
	/* 会议总结 */
	@FieldDefine(title = "会议总结")
	@Column(name = "SUMMARY_")
	@Size(max = 65535)
	protected String summary;
	/* 总结状态 */
	@FieldDefine(title = "总结状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	@FieldDefine(title = "会议申请")
	@ManyToOne
	@JoinColumn(name = "MEET_ID_")
	protected com.redxun.oa.res.entity.OaMeeting oaMeeting;

	/**
	 * Default Empty Constructor for class OaMeetAtt
	 */
	public OaMeetAtt() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaMeetAtt
	 */
	public OaMeetAtt(String in_attId) {
		this.setAttId(in_attId);
	}

	public com.redxun.oa.res.entity.OaMeeting getOaMeeting() {
		return oaMeeting;
	}

	public void setOaMeeting(com.redxun.oa.res.entity.OaMeeting in_oaMeeting) {
		this.oaMeeting = in_oaMeeting;
	}

	/**
	 * 参与ID * @return String
	 */
	public String getAttId() {
		return this.attId;
	}

	/**
	 * 设置 参与ID
	 */
	public void setAttId(String aValue) {
		this.attId = aValue;
	}

	/**
	 * 会议ID * @return String
	 */
	public String getMeetId() {
		return this.getOaMeeting() == null ? null : this.getOaMeeting()
				.getMeetId();
	}

	/**
	 * 设置 会议ID
	 */
	public void setMeetId(String aValue) {
		if (aValue == null) {
			oaMeeting = null;
		} else if (oaMeeting == null) {
			oaMeeting = new com.redxun.oa.res.entity.OaMeeting(aValue);
		} else {
			oaMeeting.setMeetId(aValue);
		}
	}

	/**
	 * 用户ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 用户ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 用户名 * @return String
	 */

	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置 用户名
	 */

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * 总结状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 总结状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.attId;
	}

	@Override
	public Serializable getPkId() {
		return this.attId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.attId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaMeetAtt)) {
			return false;
		}
		OaMeetAtt rhs = (OaMeetAtt) object;
		return new EqualsBuilder().append(this.attId, rhs.attId)
				.append(this.userId, rhs.userId)
				.append(this.fullName, rhs.fullName)
				.append(this.summary, rhs.summary)
				.append(this.status, rhs.status)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.attId)
				.append(this.userId).append(this.fullName)
				.append(this.summary).append(this.status).append(this.tenantId)
				.append(this.createTime).append(this.createBy)
				.append(this.updateTime).append(this.updateBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("attId", this.attId)
				.append("userId", this.userId)
				.append("fullName", this.fullName)
				.append("summary", this.summary).append("status", this.status)
				.append("tenantId", this.tenantId)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy).toString();
	}

}
