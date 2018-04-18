package com.redxun.sys.org.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OsRelType实体类定义
 * 关系类型定义
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "OS_REL_TYPE")
@TableDefine(title = "关系类型定义")
@JsonIgnoreProperties(value={"dim1","dim2"})
public class OsRelType extends BaseTenantEntity {
	/**
	 * 关系类型。用户关系=USER-USER；
	 */
	public final static String REL_TYPE_USER_USER="USER-USER";
	/**
	 * 关系类型，用户组关系=GROUP-GROUP；
	 */
	public final static String REL_TYPE_GROUP_GROUP="GROUP-GROUP";
	/**
	 * 关系类型，组与用户关系=GROUP-USER
	 */
	public final static String REL_TYPE_GROUP_USER="GROUP-USER";
	
	/**
	 * 用户与组的从属关系=GROUP-USER-BELONG
	 */
	public final static String REL_CAT_GROUP_USER_BELONG="GROUP-USER-BELONG";
	/**
	 * OS_REL_TYPE_表中的从属关系定义主键，由初始化数据初始化 ID=1
	 */
	public final static String REL_CAT_GROUP_USER_BELONG_ID="1";
	/**
	 * 用户与组的组负责人关系=GROUP-USER-LEADER
	 */
	public final static String REL_CAT_GROUP_USER_LEADER="GROUP-USER-LEADER";
	/**
	 * OS_REL_TYPE_表中的组内负责人定义主键，由初始化数据初始化 ID=2
	 */
	public final static String REL_CAT_GROUP_USER_LEADER_ID="2";
	/**
	 * 用户与组的上下级关系=USER-UP-LOWER
	 */
	public final static String REL_CAT_USER_UP_LOWER="USER-UP-LOWER";
	/**
	 * OS_REL_TYPE_表中的用户与组的上下级关系定义主键，由初始化数据初始化 ID=3
	 */
	public final static String REL_CAT_USER_UP_LOWER_ID="3";
	
	/**
	 * 部门下的职位关系，我们称之为岗位=GROUP-DEP-POS
	 */
	public final static String REL_CAT_DEP_POS="GROUP-DEP-POS";
	/**
	 * OS_REL_TYPE_部门下的职位关系（岗位）定义主键，由初始化数据初始化 ID=4
	 */
	public final static String REL_CAT_DEP_POS_ID="4";
	
	/**
	 * 关系约束-一对一
	 */
	public final static String CONST_TYPE_ONE_ONE="ONE-ONE";
	/**
	 * 关系约束-一对多
	 */
	public final static String CONST_TYPE_ONE_MANY="ONE-MANY";
	/**
	 * 关系约束-多对一
	 */
	public final static String CONST_TYPE_MANY_ONE="MANY-ONE";
	/**
	 * 关系约束-多对多
	 */
	public final static String CONST_TYPE_MANY_MANY="MANY-MANY";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ID_")
	protected String id;
	/* 关系名 */
	@FieldDefine(title = "关系名", defaultCol = MBoolean.YES)
	@Column(name = "NAME_")
	@Size(max = 64)
	@NotEmpty
	protected String name;
	/* 关系业务主键 */
	@FieldDefine(title = "关系业务主键", defaultCol = MBoolean.YES)
	@Column(name = "KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	/* 关系归属 */
	@FieldDefine(title = "关系归属", defaultCol = MBoolean.YES)
	@Column(name = "REL_TYPE_")
	@Size(max = 40)
	@NotEmpty
	protected String relType;
	/* 关系约束类型 */
	@FieldDefine(title = "关系约束类型", defaultCol = MBoolean.YES)
	@Column(name = "CONST_TYPE_")
	@Size(max = 40)
	@NotEmpty
	protected String constType;
	
	/* 关系当前方名称 */
	@FieldDefine(title = "关系当前方名称", defaultCol = MBoolean.YES)
	@Column(name = "PARTY1_")
	@Size(max = 128)
	@NotEmpty
	protected String party1;
	/* 关系关联方名称 */
	@FieldDefine(title = "关系关联方名称", defaultCol = MBoolean.YES)
	@Column(name = "PARTY2_")
	@Size(max = 128)
	@NotEmpty
	protected String party2;
	/*关系当前方维度*/
	@FieldDefine(title = "关系当前方维度", defaultCol = MBoolean.YES)
	@ManyToOne
	@JoinColumn(name = "DIM_ID1_")
	protected com.redxun.sys.org.entity.OsDimension dim1;
	
	/* 关联方维度ID */
	@FieldDefine(title = "关系关联方维度", defaultCol = MBoolean.YES)
	@ManyToOne
	@JoinColumn(name = "DIM_ID2_")
	protected com.redxun.sys.org.entity.OsDimension dim2;
	
	/* 状态 */
	@FieldDefine(title = "状态", defaultCol = MBoolean.YES)
	@Column(name = "STATUS_")
	@Size(max = 40)
	@NotEmpty
	protected String status;
	/* 是否系统预设 */
	@FieldDefine(title = "是否系统预设", defaultCol = MBoolean.YES)
	@Column(name = "IS_SYSTEM_")
	@Size(max = 10)
	@NotEmpty
	protected String isSystem;
	/* 是否默认 */
	@FieldDefine(title = "是否默认", defaultCol = MBoolean.YES)
	@Column(name = "IS_DEFAULT_")
	@Size(max = 10)
	@NotEmpty
	protected String isDefault;
	/* 是否是双向 */
	@FieldDefine(title = "是否是双向", defaultCol = MBoolean.YES)
	@Column(name = "IS_TWO_WAY_")
	@Size(max = 10)
	@NotEmpty
	protected String isTwoWay;
	/* 关系备注 */
	@FieldDefine(title = "关系备注", defaultCol = MBoolean.YES)
	@Column(name = "MEMO_")
	@Size(max = 255)
	protected String memo;
	
	/**
	 * Default Empty Constructor for class OsRelType
	 */
	public OsRelType() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OsRelType
	 */
	public OsRelType(String in_id) {
		this.setId(in_id);
	}

	public com.redxun.sys.org.entity.OsDimension getDim1() {
		return dim1;
	}

	public void setDim1(com.redxun.sys.org.entity.OsDimension dim1) {
		this.dim1 = dim1;
	}
	
	public String getDimId1(){
		return this.getDim1() == null ? null : this.getDim1()
				.getDimId();
	}
	
	/**
	 * 返回关系类型名称
	 * @return
	 */
	public String getRelTypeName(){
		if(REL_TYPE_USER_USER.equals(relType)){
			return "用户与用户关系";
		}else if(REL_TYPE_GROUP_GROUP.equals(relType)){
			return "用户组与用户组关系";
		}else if(REL_TYPE_GROUP_USER.equals(relType)){
			return "用户组与用户关系";
		}else{
			return "";
		}
	}
	
	public void setDimId1(String aValue){
		if (StringUtils.isEmpty(aValue)) {
			dim1 = null;
		} else if (dim1 == null) {
			dim1 = new com.redxun.sys.org.entity.OsDimension(aValue);
		} else {
			dim1.setDimId(aValue);
		}
	}
	
	public String getDimId2(){
		return this.getDim2() == null ? null : this.getDim2()
				.getDimId();
	}
	
	public void setDimId2(String aValue){
		if (StringUtils.isEmpty(aValue)) {
			dim2 = null;
		} else if (dim2 == null) {
			dim2 = new com.redxun.sys.org.entity.OsDimension(aValue);
		} else {
			dim2.setDimId(aValue);
		}
	}

	public com.redxun.sys.org.entity.OsDimension getDim2() {
		return dim2;
	}

	public void setDim2(com.redxun.sys.org.entity.OsDimension dim2) {
		this.dim2 = dim2;
	}

	/**
	 * 关系类型ID * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 关系类型ID
	 */
	public void setId(String aValue) {
		this.id = aValue;
	}

	/**
	 * 关系名 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 关系名
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 关系业务主键 * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 关系业务主键
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 关系归属 * @return String
	 */
	public String getRelType() {
		return this.relType;
	}

	/**
	 * 设置 关系归属
	 */
	public void setRelType(String aValue) {
		this.relType = aValue;
	}

	/**
	 * 关系约束类型 * @return String
	 */
	public String getConstType() {
		return this.constType;
	}

	/**
	 * 设置 关系约束类型
	 */
	public void setConstType(String aValue) {
		this.constType = aValue;
	}

	/**
	 * 关系当前方名称 * @return String
	 */
	public String getParty1() {
		return this.party1;
	}

	/**
	 * 设置 关系当前方名称
	 */
	public void setParty1(String aValue) {
		this.party1 = aValue;
	}

	/**
	 * 关系关联方名称 * @return String
	 */
	public String getParty2() {
		return this.party2;
	}

	/**
	 * 设置 关系关联方名称
	 */
	public void setParty2(String aValue) {
		this.party2 = aValue;
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
	 * 是否系统预设 * @return String
	 */
	public String getIsSystem() {
		return this.isSystem;
	}

	/**
	 * 设置 是否系统预设
	 */
	public void setIsSystem(String aValue) {
		this.isSystem = aValue;
	}

	/**
	 * 是否默认 * @return String
	 */
	public String getIsDefault() {
		return this.isDefault;
	}

	/**
	 * 设置 是否默认
	 */
	public void setIsDefault(String aValue) {
		this.isDefault = aValue;
	}

	/**
	 * 是否是双向 * @return String
	 */
	public String getIsTwoWay() {
		return this.isTwoWay;
	}

	/**
	 * 设置 是否是双向
	 */
	public void setIsTwoWay(String aValue) {
		this.isTwoWay = aValue;
	}

	/**
	 * 关系备注 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 关系备注
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.id;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.id = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsRelType)) {
			return false;
		}
		OsRelType rhs = (OsRelType) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.relType, rhs.relType)
				.append(this.constType, rhs.constType)
				.append(this.party1, rhs.party1)
				.append(this.party2, rhs.party2)
				.append(this.status, rhs.status)
				.append(this.isSystem, rhs.isSystem)
				.append(this.isDefault, rhs.isDefault)
				.append(this.isTwoWay, rhs.isTwoWay)
				.append(this.memo, rhs.memo)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.tenantId, rhs.tenantId)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.name).append(this.key).append(this.relType)
				.append(this.constType).append(this.party1).append(this.party2).append(this.status)
				.append(this.isSystem).append(this.isDefault)
				.append(this.isTwoWay).append(this.memo).append(this.createBy)
				.append(this.createTime).append(this.tenantId)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("key", this.key)
				.append("relType", this.relType)
				.append("constType", this.constType)
				.append("party1", this.party1).append("party2", this.party2)
				.append("status", this.status)
				.append("isSystem", this.isSystem)
				.append("isDefault", this.isDefault)
				.append("isTwoWay", this.isTwoWay).append("memo", this.memo)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("tenantId", this.tenantId)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
