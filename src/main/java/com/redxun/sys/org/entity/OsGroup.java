package com.redxun.sys.org.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.org.api.model.IGroup;

/**
 * <pre>
 * 描述：OsGroup实体类定义
 * 系统用户组
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "OS_GROUP")
@TableDefine(title = "系统用户组")
@JsonIgnoreProperties("osDimension")
public class OsGroup extends BaseTenantEntity implements IGroup{

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "GROUP_ID_")
	protected String groupId;
	/* 用户组名称 */
	@FieldDefine(title = "用户组名称", defaultCol = MBoolean.YES)
	@Column(name = "NAME_")
	@Size(max = 128)
	@NotEmpty
	protected String name;
	/* 用户组业务主键 */
	@FieldDefine(title = "用户组业务主键", defaultCol = MBoolean.YES)
	@Column(name = "KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	/* 所属用户组等级值 */
	@FieldDefine(title = "所属用户组等级值", defaultCol = MBoolean.YES)
	@Column(name = "RANK_LEVEL_")
	protected Integer rankLevel;
	/* 状态 */
	@FieldDefine(title = "状态", defaultCol = MBoolean.YES)
	@Column(name = "STATUS_")
	@Size(max = 40)
	@NotEmpty
	protected String status;
	/* 描述 */
	@FieldDefine(title = "描述", defaultCol = MBoolean.YES)
	@Column(name = "DESCP_")
	@Size(max = 255)
	protected String descp;
	/* 排序号 */
	@FieldDefine(title = "排序号", defaultCol = MBoolean.YES)
	@Column(name = "SN_")
	protected Integer sn;
	/* 父用户组ID */
	@FieldDefine(title = "父用户组ID", defaultCol = MBoolean.YES)
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	/* 层次 */
	@FieldDefine(title = "层次", defaultCol = MBoolean.YES)
	@Column(name = "DEPTH_")
	protected Integer depth;
	/* 路径 */
	@FieldDefine(title = "路径", defaultCol = MBoolean.YES)
	@Column(name = "PATH_")
	@Size(max = 1024)
	protected String path;
	
	@Transient
	protected String iconCls="icon-group";
	
	/* 下级数量 */
	@FieldDefine(title = "下级数量", defaultCol = MBoolean.YES)
	@Column(name = "CHILDS_")
	protected Integer childs;
	
	/* 来源 */
	@FieldDefine(title = "来源", defaultCol = MBoolean.YES)
	@Column(name = "FORM_")
	@Size(max = 20)
	protected String form;
	/* 是否默认 */
	@FieldDefine(title = "是否默认", defaultCol = MBoolean.YES)
	@Column(name = "IS_DEFAULT_")
	@Size(max = 40)
	protected String isDefault;
	
	@FieldDefine(title = "所属维度", defaultCol = MBoolean.YES)
	@ManyToOne
	@JoinColumn(name = "DIM_ID_")
	protected com.redxun.sys.org.entity.OsDimension osDimension;
	
	@FieldDefine(title = "同步微信", defaultCol = MBoolean.YES)
	@Column(name = "SYNC_WX_")
	protected int syncWx=0;
	
	//内部维护父ID
	@FieldDefine(title = "微信内部维护父部门ID", defaultCol = MBoolean.YES)
	@Column(name = "WX_PARENT_PID_")
	protected Integer wxParentPid;

	//微信平台唯一ID
	@FieldDefine(title = "微信平台部门唯一ID", defaultCol = MBoolean.YES)
	@Column(name = "WX_PID_")
	protected Integer wxPid;
	
	@FieldDefine(title = "区域编码", defaultCol = MBoolean.YES)
	@Column(name = "AREA_CODE_")
	protected String areaCode;

	@Transient
	protected String dimId;
	
	@Transient
	protected OsGroup parentGroup;
	
	@Transient
	protected String isMain;

	public Integer getWxParentPid() {
		return wxParentPid;
	}
	
	public void setWxParentPid(Integer wxParentPid) {
		this.wxParentPid = wxParentPid;
	}
	
	public Integer getWxPid() {
		return wxPid;
	}
	
	public void setWxPid(Integer wxPid) {
		this.wxPid = wxPid;
	}

	/**
	 * 原始ID
	 */
	@Transient
	protected String orginId;

	public String getIsLeaf() {
		if(childs==null||childs == 0){
			return "true";
		}else{	
			return "false";
		}
	}
	public String getExpanded(){
		return "false";
	}

	/**
	 * Default Empty Constructor for class OsGroup
	 */
	public OsGroup() {
		super();
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * Default Key Fields Constructor for class OsGroup
	 */
	public OsGroup(String in_groupId) {
		this.setGroupId(in_groupId);
	}

	public com.redxun.sys.org.entity.OsDimension getOsDimension() {
		return osDimension;
	}

	public void setOsDimension(
			com.redxun.sys.org.entity.OsDimension in_osDimension) {
		this.osDimension = in_osDimension;
	}

	public OsGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(OsGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	/**
	 * 用户组ID * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 设置 用户组ID
	 */
	public void setGroupId(String aValue) {
		this.groupId = aValue;
	}

	
	
	public String getDimId() {
		return dimId;
	}

	public void setDimId(String dimId) {
		this.dimId = dimId;
	}

/*	*//**
	 * 维度ID * @return String
	 *//*
	public String getDimId() {
		return this.getOsDimension() == null ? null : this.getOsDimension()
				.getDimId();
	}
*/
	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

/*	*//**
	 * 设置 维度ID
	 *//*
	public void setDimId(String aValue) {
		if (aValue == null) {
			osDimension = null;
		} else if (osDimension == null) {
			osDimension = new com.redxun.sys.org.entity.OsDimension(aValue);
			// osDimension.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			osDimension.setDimId(aValue);
		}
	}
*/
	/**
	 * 用户组名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 用户组名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 用户组业务主键 * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 用户组业务主键
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 所属用户组等级值 * @return Integer
	 */
	public Integer getRankLevel() {
		return this.rankLevel;
	}

	/**
	 * 设置 所属用户组等级值
	 */
	public void setRankLevel(Integer aValue) {
		this.rankLevel = aValue;
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
	 * 描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 排序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 排序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

	/**
	 * 父用户组ID * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父用户组ID
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
	}

	/**
	 * 层次 * @return Integer
	 */
	public Integer getDepth() {
		return this.depth;
	}

	/**
	 * 设置 层次
	 */
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}

	/**
	 * 路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 设置 路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}

	public Integer getChilds() {
		return childs;
	}

	public void setChilds(Integer childs) {
		this.childs = childs;
	}

	/**
	 * 来源 * @return String
	 */
	public String getForm() {
		return this.form;
	}

	/**
	 * 设置 来源
	 */
	public void setForm(String aValue) {
		this.form = aValue;
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

	@Override
	public String getIdentifyLabel() {
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.groupId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.groupId = (String) pkId;
	}

	public String getAreaCode() {
		return areaCode;
	}
	
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsGroup)) {
			return false;
		}
		OsGroup rhs = (OsGroup) object;
		return new EqualsBuilder().append(this.groupId, rhs.groupId)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.rankLevel, rhs.rankLevel)
				.append(this.status, rhs.status).append(this.descp, rhs.descp)
				.append(this.sn, rhs.sn).append(this.parentId, rhs.parentId)
				.append(this.depth, rhs.depth).append(this.path, rhs.path)
				.append(this.form, rhs.form)
				.append(this.isDefault, rhs.isDefault)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.groupId)
				.append(this.name).append(this.key).append(this.rankLevel)
				.append(this.status).append(this.descp).append(this.sn)
				.append(this.parentId).append(this.depth).append(this.path)
				.append(this.form).append(this.isDefault).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("groupId", this.groupId)
				.append("name", this.name).append("key", this.key)
				.append("rankLevel", this.rankLevel)
				.append("status", this.status).append("descp", this.descp)
				.append("sn", this.sn).append("parentId", this.parentId)
				.append("depth", this.depth).append("path", this.path)
				.append("form", this.form).append("isDefault", this.isDefault)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

	@Override
	public String getIdentityType() {
		return IDENTIFY_TYPE_GROUP;
	}

	@Override
	public String getType() {
		if(osDimension!=null){
			return osDimension.dimKey;
		}
		return null;
	}



	@Override
	public String getIdentityName() {
		return name;
	}

	@Override
	public String getIdentityId() {
		return this.groupId;
	}

	public int getSyncWx() {
		return syncWx;
	}

	public void setSyncWx(int syncWx) {
		this.syncWx = syncWx;
	}

	public String getOrginId() {
		return orginId;
	}

	public void setOrginId(String orginId) {
		this.orginId = orginId;
	}

}
