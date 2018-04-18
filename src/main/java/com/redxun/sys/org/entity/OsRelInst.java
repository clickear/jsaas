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

import org.apache.commons.lang.StringUtils;
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
 * 描述：OsRelInst实体类定义
 * 关系实例
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "OS_REL_INST")
@TableDefine(title = "关系实例")
@JsonIgnoreProperties("osRelType")
public class OsRelInst extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "INST_ID_")
	protected String instId;
	
	@FieldDefine(title = "关系类型")
	@Column(name = "REL_TYPE_")
	@Size(max = 40)
	protected String relType;
	
	/* 关系类型 */
	@FieldDefine(title = "关系类型")
	@Column(name = "REL_TYPE_KEY_")
	@Size(max = 40)
	protected String relTypeKey;
	
	@FieldDefine(title = "是否为主关系")
	@Column(name = "IS_MAIN_")
	@Size(max = 20)
	@NotEmpty
	protected String isMain;
	
	/* 当前方ID */
	@FieldDefine(title = "当前方ID")
	@Column(name = "PARTY1_")
	@Size(max = 64)
	@NotEmpty
	protected String party1;
	/* 关联方ID */
	@FieldDefine(title = "关联方ID")
	@Column(name = "PARTY2_")
	@Size(max = 64)
	@NotEmpty
	protected String party2;
	/* 当前方维度ID */
	@FieldDefine(title = "当前方维度ID")
	@Column(name = "DIM1_")
	@Size(max = 64)
	protected String dim1;
	/* 关联方维度ID */
	@FieldDefine(title = "关联方维度ID")
	@Column(name = "DIM2_")
	@Size(max = 64)
	protected String dim2;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 40)
	@NotEmpty
	protected String status;
	
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "ALIAS_")
	@Size(max = 80)
	protected String alias;
	
	/* 状态 */
	@FieldDefine(title = "路径")
	@Column(name = "PATH_")
	@Size(max = 80)
	protected String path;
	
	@FieldDefine(title = "关系定义类型")
	@ManyToOne
	@JoinColumn(name = "REL_TYPE_ID_")
	protected com.redxun.sys.org.entity.OsRelType osRelType;
	
	//用于显示关系分类名称
	@Transient
	protected String relTypeName;
	//用于显示关系分类ID
	@Transient
	protected String osRelTypeId;
	//显示关系分类Key
	@Transient
	protected String relTypeCat;

	@Transient
	protected String groupName;
	//关系方二的名称
	@Transient
	protected String partyName2;
	//关系方一的名称
	@Transient
	protected String partyName1;
	
	@Transient
	protected String groupKey;

	/**
	 * Default Empty Constructor for class OsRelInst
	 */
	public OsRelInst() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OsRelInst
	 */
	public OsRelInst(String in_instId) {
		this.setInstId(in_instId);
	}

	public com.redxun.sys.org.entity.OsRelType getOsRelType() {
		return osRelType;
	}

	public void setOsRelType(com.redxun.sys.org.entity.OsRelType in_osRelType) {
		this.osRelType = in_osRelType;
	}

	public String getOsRelTypeId() {
		if(StringUtils.isNotEmpty(osRelTypeId)){
			return osRelTypeId;
		}
		if(this.osRelType!=null){
			return this.osRelType.getId();
		}
		return null;
	}

	public void setOsRelTypeId(String osRelTypeId) {
		this.osRelTypeId = osRelTypeId;
	}

	public String getPartyName2() {
		return partyName2;
	}

	public void setPartyName2(String partyName2) {
		this.partyName2 = partyName2;
	}

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	/**
	 * 当用户与组存在关系时，并且允许多个关系并存，这时即存在一个叫主关系，
	 * 如部门与用户 的从属关系，只允许用户有一个主部门
	 * @return
	 */
	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	/**
	 * 用户组关系ID * @return String
	 */
	public String getInstId() {
		return this.instId;
	}

	public String getRelTypeName() {
		if(StringUtils.isNotEmpty(relTypeName)){
			return relTypeName;
		}
		if(osRelType!=null){
			return osRelType.getName();
		}
		return null;
	}

	public void setRelTypeName(String relTypeName) {
		this.relTypeName = relTypeName;
	}

	/**
	 * 设置 用户组关系ID
	 */
	public void setInstId(String aValue) {
		this.instId = aValue;
	}

	/**
	 * 关系类型ID * @return String
	 */
	public String getRelTypeId() {
		return this.getOsRelType() == null ? null : this.getOsRelType().getId();
	}

	/**
	 * 设置 关系类型ID
	 */
	public void setRelTypeId(String aValue) {
		if (aValue == null) {
			osRelType = null;
		} else if (osRelType == null) {
			osRelType = new com.redxun.sys.org.entity.OsRelType(aValue);
		} else {
			//
			osRelType.setId(aValue);
		}
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 关系类型Key * @return String
	 */
	public String getRelTypeKey() {
		return this.relTypeKey;
	}

	/**
	 * 设置 关系类型
	 */
	public void setRelTypeKey(String aValue) {
		this.relTypeKey = aValue;
	}

	/**
	 * 当前方ID * @return String
	 */
	public String getParty1() {
		return this.party1;
	}

	/**
	 * 设置 当前方ID
	 */
	public void setParty1(String aValue) {
		this.party1 = aValue;
	}

	/**
	 * 关联方ID * @return String
	 */
	public String getParty2() {
		return this.party2;
	}

	/**
	 * 设置 关联方ID
	 */
	public void setParty2(String aValue) {
		this.party2 = aValue;
	}

	/**
	 * 当前方维度ID * @return String
	 */
	public String getDim1() {
		return this.dim1;
	}

	/**
	 * 设置 当前方维度ID
	 */
	public void setDim1(String aValue) {
		this.dim1 = aValue;
	}

	/**
	 * 关联方维度ID * @return String
	 */
	public String getDim2() {
		return this.dim2;
	}

	/**
	 * 设置 关联方维度ID
	 */
	public void setDim2(String aValue) {
		this.dim2 = aValue;
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

	public String getPartyName1() {
		return partyName1;
	}

	public void setPartyName1(String partyName1) {
		this.partyName1 = partyName1;
	}

	@Override
	public String getIdentifyLabel() {
		return this.instId;
	}

	@Override
	public Serializable getPkId() {
		return this.instId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.instId = (String) pkId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public String getRelTypeCat() {
		if(StringUtils.isNotEmpty(relTypeCat)){
			return relTypeCat;
		}
		if(osRelType!=null){
			return osRelType.getRelType();
		}
		return null;
	}

	public void setRelTypeCat(String relTypeCat) {
		this.relTypeCat = relTypeCat;
	}
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsRelInst)) {
			return false;
		}
		OsRelInst rhs = (OsRelInst) object;
		return new EqualsBuilder().append(this.instId, rhs.instId).append(this.relTypeKey, rhs.relTypeKey).append(this.party1, rhs.party1).append(this.party2, rhs.party2).append(this.dim1, rhs.dim1).append(this.dim2, rhs.dim2).append(this.status, rhs.status).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.instId).append(this.relTypeKey).append(this.party1).append(this.party2).append(this.dim1).append(this.dim2).append(this.status).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("instId", this.instId).append("relType", this.relTypeKey).append("party1", this.party1).append("party2", this.party2).append("dim1", this.dim1).append("dim2", this.dim2).append("status", this.status).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).append("tenantId", this.tenantId).toString();
	}

}
