package com.redxun.kms.core.entity;

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
 * 描述：KdUser实体类定义
 * 知识用户信息
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_USER")
@TableDefine(title = "知识用户信息")
public class KdUser extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "KUSER_ID")
	protected String kuserId;
	/* 问答积分 */
	@FieldDefine(title = "积分")
	@Column(name = "POINT_")
	protected Integer point;
	/* 文档积分 */
	@FieldDefine(title = "积分")
	@Column(name = "DOC_SCORE_")
	protected Integer docScore;
	/**/
	@FieldDefine(title = "")
	@Column(name = "GRADE_")
	@Size(max = 20)
	protected String grade;
	/* 专家个人=PERSON 领域专家=DOMAIN */
	@FieldDefine(title = "专家类型")
	@Column(name = "USER_TYPE_")
	@Size(max = 20)
	protected String userType;
	/**/
	@FieldDefine(title = "")
	@Column(name = "FULLNAME_")
	@Size(max = 32)
	protected String fullname;
	/* 序号 */
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	protected Integer sn;
/*	 知识领域 
	@FieldDefine(title = "知识领域")
	@Column(name = "KN_SYS_ID_")
	@Size(max = 64)
	protected String knSysId;
	 爱问领域 
	@FieldDefine(title = "爱问领域")
	@Column(name = "REQ_SYS_ID_")
	@Size(max = 64)
	protected String reqSysId;*/
	/* 个性签名 */
	@FieldDefine(title = "个性签名")
	@Column(name = "SIGN_")
	@Size(max = 80)
	protected String sign;
	/* 个人简介 */
	@FieldDefine(title = "个人简介")
	@Column(name = "PROFILE_")
	@Size(max = 100)
	protected String profile;
	/* 头像 */
	@FieldDefine(title = "头像")
	@Column(name = "HEAD_ID_")
	@Size(max = 64)
	protected String headId;
	/* 性别 */
	@FieldDefine(title = "性别")
	@Column(name = "SEX_")
	@Size(max = 64)
	protected String sex;
	/* 办公电话 */
	@FieldDefine(title = "办公电话")
	@Column(name = "OFFICE_PHONE_")
	@Size(max = 20)
	protected String officePhone;
	/* 手机号码 */
	@FieldDefine(title = "手机号码")
	@Column(name = "MOBILE_")
	@Size(max = 16)
	protected String mobile;
	/* 电子邮箱 */
	@FieldDefine(title = "电子邮箱")
	@Column(name = "EMAIL_")
	@Size(max = 80)
	protected String email;
	
	@ManyToOne
	@JoinColumn(name = "KN_SYS_ID_")
	protected com.redxun.sys.core.entity.SysTree knSysTree;
	
	@ManyToOne
	@JoinColumn(name = "REQ_SYS_ID_")
	protected com.redxun.sys.core.entity.SysTree reqSysTree;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID_")
	protected com.redxun.sys.org.entity.OsUser user;

	/**
	 * Default Empty Constructor for class KdUser
	 */
	public KdUser() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdUser
	 */
	public KdUser(String in_kuserId) {
		this.setKuserId(in_kuserId);
	}

	/**
	 * 用户ID * @return String
	 */
	public String getKuserId() {
		return this.kuserId;
	}

	/**
	 * 设置 用户ID
	 */
	public void setKuserId(String aValue) {
		this.kuserId = aValue;
	}

	/**
	 * 积分 * @return Integer
	 */
	public Integer getPoint() {
		return this.point;
	}

	/**
	 * 设置 积分
	 */
	public void setPoint(Integer aValue) {
		this.point = aValue;
	}

	/**
	 * * @return String
	 */
	public String getGrade() {
		return this.grade;
	}

	/**
	 * 设置
	 */
	public void setGrade(String aValue) {
		this.grade = aValue;
	}

	/**
	 * 专家个人=PERSON 领域专家=DOMAIN * @return String
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * 设置 专家个人=PERSON 领域专家=DOMAIN
	 */
	public void setUserType(String aValue) {
		this.userType = aValue;
	}

	/**
	 * * @return String
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * 设置
	 */
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}

	/**
	 * 序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

/*	*//**
	 * 知识领域 * @return String
	 *//*
	public String getKnSysId() {
		return this.knSysId;
	}

	*//**
	 * 设置 知识领域
	 *//*
	public void setKnSysId(String aValue) {
		this.knSysId = aValue;
	}

	*//**
	 * 爱问领域 * @return String
	 *//*
	public String getReqSysId() {
		return this.reqSysId;
	}

	*//**
	 * 设置 爱问领域
	 *//*
	public void setReqSysId(String aValue) {
		this.reqSysId = aValue;
	}
*/
	/**
	 * 个性签名 * @return String
	 */
	public String getSign() {
		return this.sign;
	}

	/**
	 * 设置 个性签名
	 */
	public void setSign(String aValue) {
		this.sign = aValue;
	}

	/**
	 * 个人简介 * @return String
	 */
	public String getProfile() {
		return this.profile;
	}

	/**
	 * 设置 个人简介
	 */
	public void setProfile(String aValue) {
		this.profile = aValue;
	}

	/**
	 * 头像 * @return String
	 */
	public String getHeadId() {
		return this.headId;
	}

	/**
	 * 设置 头像
	 */
	public void setHeadId(String aValue) {
		this.headId = aValue;
	}

	/**
	 * 性别 * @return String
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * 设置 性别
	 */
	public void setSex(String aValue) {
		this.sex = aValue;
	}	

	public Integer getDocScore() {
		return docScore;
	}

	public void setDocScore(Integer docScore) {
		this.docScore = docScore;
	}

	/**
	 * 办公电话 * @return String
	 */
	public String getOfficePhone() {
		return this.officePhone;
	}

	/**
	 * 设置 办公电话
	 */
	public void setOfficePhone(String aValue) {
		this.officePhone = aValue;
	}

	/**
	 * 手机号码 * @return String
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 设置 手机号码
	 */
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}

	/**
	 * 电子邮箱 * @return String
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * 设置 电子邮箱
	 */
	public void setEmail(String aValue) {
		this.email = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.kuserId;
	}

	@Override
	public Serializable getPkId() {
		return this.kuserId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.kuserId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdUser)) {
			return false;
		}
		KdUser rhs = (KdUser) object;
		return new EqualsBuilder().append(this.kuserId, rhs.kuserId).append(this.point, rhs.point).append(this.grade, rhs.grade).append(this.userType, rhs.userType).append(this.fullname, rhs.fullname).append(this.sn, rhs.sn).append(this.sign, rhs.sign).append(this.profile, rhs.profile).append(this.headId, rhs.headId).append(this.sex, rhs.sex).append(this.officePhone, rhs.officePhone).append(this.mobile, rhs.mobile).append(this.email, rhs.email).isEquals();
	}

	public com.redxun.sys.core.entity.SysTree getKnSysTree() {
		return knSysTree;
	}

	public void setKnSysTree(com.redxun.sys.core.entity.SysTree knSysTree) {
		this.knSysTree = knSysTree;
	}

	public com.redxun.sys.core.entity.SysTree getReqSysTree() {
		return reqSysTree;
	}

	public void setReqSysTree(com.redxun.sys.core.entity.SysTree reqSysTree) {
		this.reqSysTree = reqSysTree;
	}
	
	public com.redxun.sys.org.entity.OsUser getUser() {
		return user;
	}

	public void setUser(com.redxun.sys.org.entity.OsUser user) {
		this.user = user;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.kuserId).append(this.point).append(this.grade).append(this.userType).append(this.fullname).append(this.sn).append(this.sign).append(this.profile).append(this.headId).append(this.sex).append(this.officePhone).append(this.mobile).append(this.email).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("kuserId", this.kuserId).append("point", this.point).append("grade", this.grade).append("userType", this.userType).append("fullname", this.fullname).append("sn", this.sn).append("sign", this.sign).append("profile", this.profile).append("headId", this.headId).append("sex", this.sex).append("officePhone", this.officePhone).append("mobile", this.mobile).append("email", this.email).toString();
	}

}
