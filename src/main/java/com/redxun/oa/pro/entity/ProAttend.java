package com.redxun.oa.pro.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.entity.BaseTenantEntity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：Plan实体类定义
 * 项目参与人
 * 作者：cmc
 * 日期:2015-12-16-上午13:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_PRO_ATTEND")
@TableDefine(title = "项目或产品参与人、负责人、关注人")
@JsonIgnoreProperties(value={"project"})
public class ProAttend extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ATT_ID_")
	protected String attId;
	/**
	 * 用来处理更新时的对比字段，不存数据库
	 */
	@Transient
	protected String alexist;
	/* 参与人ID */
	@FieldDefine(title = "参与人ID")
	@Column(name = "USER_ID_")
	@Size(max = 4000)
	protected String userId;
	/**
	 * 后来添加的两个字段用来显示在grid里
	 */
	/*
	 * 把用户名和组名显示出来
	 * */
	@Transient
	protected String usernames;
	/*
	 * 把用户名和组名显示出来
	 * */
	@Transient
	protected String groupnames;
	/* 参与组ID */
	@FieldDefine(title = "参与组ID")
	@Column(name = "GROUP_ID_")
	@Size(max = 4000)
	protected String groupId;
	/*
	 * 参与类型 Participate
	 * 
	 * JOIN=参与 RESPONSE=负责 APPROVE=审批 PAY_ATT=关注
	 */
	@FieldDefine(title = "参与类型")
	@Column(name = "PART_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String partType;
	@FieldDefine(title = "项目或产品")
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID_")
	protected com.redxun.oa.pro.entity.Project project;

	/**
	 * Default Empty Constructor for class ProAttend
	 */
	public ProAttend() {
		super();
	}

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}

	public String getGroupnames() {
		return groupnames;
	}

	public void setGroupnames(String groupnames) {
		this.groupnames = groupnames;
	}

	public String getAlexist() {
		return alexist;
	}

	public void setAlexist(String alexist) {
		this.alexist = alexist;
	}

	/**
	 * Default Key Fields Constructor for class ProAttend
	 */
	public ProAttend(String in_attId) {
		this.setAttId(in_attId);
	}

	public com.redxun.oa.pro.entity.Project getProject() {
		return project;
	}

	public void setProject(com.redxun.oa.pro.entity.Project in_project) {
		this.project = in_project;
	}

	/**
	 * 参与人ID * @return String
	 */
	public String getAttId() {
		return this.attId;
	}

	/**
	 * 设置 参与人ID
	 */
	public void setAttId(String aValue) {
		this.attId = aValue;
	}

	/**
	 * 项目ID * @return String
	 */
	public String getProjectId() {
		return this.getProject() == null ? null : this.getProject()
				.getProjectId();
	}

	/**
	 * 设置 项目ID
	 */
	public void setProjectId(String aValue) {
		if (aValue == null) {
			project = null;
		} else if (project == null) {
			project = new com.redxun.oa.pro.entity.Project(aValue);
		} else {
			project.setProjectId(aValue);
		}
	}

	/**
	 * 参与人ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 参与人ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 参与组ID * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置 参与组ID
	 */
	public void setGroupId(String aValue) {
		this.groupId = aValue;
	}

	/**
	 * 参与类型 Participate
	 * 
	 * JOIN=参与 RESPONSE=负责 APPROVE=审批 PAY_ATT=关注 * @return String
	 */
	public String getPartType() {
		return this.partType;
	}

	/**
	 * 设置 参与类型 Participate
	 * 
	 * JOIN=参与 RESPONSE=负责 APPROVE=审批 PAY_ATT=关注
	 */
	public void setPartType(String aValue) {
		this.partType = aValue;
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
		if (!(object instanceof ProAttend)) {
			return false;
		}
		ProAttend rhs = (ProAttend) object;
		return new EqualsBuilder().append(this.attId, rhs.attId)
				.append(this.userId, rhs.userId)
				.append(this.groupId, rhs.groupId)
				.append(this.partType, rhs.partType)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.attId)
				.append(this.userId).append(this.groupId).append(this.partType)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("attId", this.attId)
				.append("userId", this.userId).append("groupId", this.groupId)
				.append("partType", this.partType)
				.append("createBy", this.createBy)
				.append("usernames", this.usernames)
				.append("groupnames", this.groupnames)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
