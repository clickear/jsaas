package com.redxun.oa.company.entity;

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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.org.entity.OsGroup;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：OaComBook实体类定义
 * TODO: add class/table comments
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_COM_BOOK")
@TableDefine(title = "公司通讯录")
@JsonIgnoreProperties(value={"osGroup","oaComRights","sysTree"})
public class OaComBook extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "COM_ID_")
	protected String comId;
	/**/
	@FieldDefine(title = "名字")
	@Column(name = "NAME_")
	@Size(max = 64)
	@NotEmpty
	protected String name;
	@FieldDefine(title = "名字首字母")
	@Column(name = "FIRST_LETTER_")
	@Size(max = 16)
	//@NotEmpty
	protected String firstLetter;
	/**/
	@FieldDefine(title = "主手机号")
	@Column(name = "MOBILE_")
	@Size(max = 64)
	protected String mobile;
	/**/
	@FieldDefine(title = "副手机号")
	@Column(name = "MOBILE2_")
	@Size(max = 64)
	protected String mobile2;
	/**/
	@FieldDefine(title = "固话")
	@Column(name = "PHONE_")
	@Size(max = 64)
	protected String phone;
	/**/
	@FieldDefine(title = "邮箱")
	@Column(name = "EMAIL_")
	@Size(max = 64)
	protected String email;
	/*QQ*/
	@FieldDefine(title = "QQ")
	@Column(name = "QQ_")
	@Size(max = 32)
	protected String qq;
	/*备注*/
	@FieldDefine(title = "备注")
	@Column(name = "REMARK_")
	@Size(max = 500)
	protected String remark;
	/**/
	@FieldDefine(title = "")
	@Column(name = "IS_EMPLOYEE_")
	@Size(max = 16)
	@NotEmpty
	protected String isEmployee;
	@FieldDefine(title = "系统用户组")
	@ManyToOne
	@JoinColumn(name = "MAINDEP_")
	protected OsGroup osGroup;
	@FieldDefine(title = "主部门名")
	@Column(name = "DEPNAME_")
	@Size(max = 64)
	protected String depName;
	
	@FieldDefine(title = "系统分类树\r\n用于显示树层次结构的分类\r\n可以允许任何层次结构")
	@ManyToOne
	@JoinColumn(name = "COMGROUP_ID_")
	protected SysTree sysTree;

	@FieldDefine(title = "权限")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oaComBook", fetch = FetchType.LAZY)
	protected java.util.Set<OaComRight> oaComRights = new java.util.HashSet<OaComRight>();

	/**
	 * Default Empty Constructor for class OaComBook
	 */
	public OaComBook() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OaComBook
	 */
	public OaComBook(String in_comId) {
		this.setComId(in_comId);
	}
		
	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public OsGroup getOsGroup() {
		return osGroup;
	}

	public void setOsGroup(OsGroup in_osGroup) {
		this.osGroup = in_osGroup;
	}

	public SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	public java.util.Set<OaComRight> getOaComRights() {
		return oaComRights;
	}

	public void setOaComRights(java.util.Set<OaComRight> in_oaComRights) {
		this.oaComRights = in_oaComRights;
	}

	/**
	 * * @return String
	 */
	public String getComId() {
		return this.comId;
	}

	/**
	 * 设置
	 */
	public void setComId(String aValue) {
		this.comId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getComgroupId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置
	 */
	public void setComgroupId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * * @return String
	 */
	public String getMaindep() {
		return this.getOsGroup() == null ? null : this.getOsGroup().getGroupId();
	}

	/**
	 * 设置
	 */
	public void setMaindep(String aValue) {
		if (aValue == null) {
			osGroup = null;
		} else if (osGroup == null) {
			osGroup = new OsGroup(aValue);
		} else {
			osGroup.setGroupId(aValue);
		}
	}

	/**
	 * * @return String
	 */
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 设置
	 */
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}

	/**
	 * * @return String
	 */
	public String getMobile2() {
		return this.mobile2;
	}

	/**
	 * 设置
	 */
	public void setMobile2(String aValue) {
		this.mobile2 = aValue;
	}

	/**
	 * * @return String
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置
	 */
	public void setPhone(String aValue) {
		this.phone = aValue;
	}

	/**
	 * * @return String
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * 设置
	 */
	public void setEmail(String aValue) {
		this.email = aValue;
	}

	/**
	 * * @return String
	 */
	public String getQq() {
		return this.qq;
	}

	/**
	 * 设置
	 */
	public void setQq(String aValue) {
		this.qq = aValue;
	}

	/**
	 * * @return String
	 */
	public String getIsEmployee() {
		return this.isEmployee;
	}

	/**
	 * 设置
	 */
	public void setIsEmployee(String aValue) {
		this.isEmployee = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.comId;
	}

	@Override
	public Serializable getPkId() {
		return this.comId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.comId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaComBook)) {
			return false;
		}
		OaComBook rhs = (OaComBook) object;
		return new EqualsBuilder().append(this.comId, rhs.comId).append(this.name, rhs.name).append(this.mobile, rhs.mobile).append(this.mobile2, rhs.mobile2).append(this.phone, rhs.phone).append(this.email, rhs.email)
				.append(this.qq, rhs.qq).append(this.isEmployee, rhs.isEmployee).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.comId).append(this.name).append(this.mobile).append(this.mobile2).append(this.phone).append(this.email).append(this.qq).append(this.isEmployee).append(this.tenantId)
				.append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("comId", this.comId).append("name", this.name).append("mobile", this.mobile).append("mobile2", this.mobile2).append("phone", this.phone).append("email", this.email).append("qq", this.qq)
				.append("isEmployee", this.isEmployee).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime)
				.toString();
	}

}
