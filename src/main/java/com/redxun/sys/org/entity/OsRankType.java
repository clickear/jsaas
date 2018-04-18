package com.redxun.sys.org.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：OsRankType实体类定义
 * 用户组等级分类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "OS_RANK_TYPE")
@TableDefine(title = "用户组等级分类")
public class OsRankType extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ID_")
	protected String id;
	/* 名称 */
	@FieldDefine(title = "名称", defaultCol = MBoolean.YES)
	@Column(name = "NAME_")
	@Size(max = 255)
	@NotEmpty
	protected String name;
	/* 分类业务键 */
	@FieldDefine(title = "分类业务键", defaultCol = MBoolean.YES)
	@Column(name = "KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	/* 级别数值 */
	@FieldDefine(title = "级别数值", defaultCol = MBoolean.YES)
	@Column(name = "LEVEL_")
	protected Integer level;
	
	@FieldDefine(title = "所属维度", defaultCol = MBoolean.FALSE)
	@ManyToOne
	@JoinColumn(name = "DIM_ID_")
	protected com.redxun.sys.org.entity.OsDimension osDimension;

	/**
	 * Default Empty Constructor for class OsRankType
	 */
	public OsRankType() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OsRankType
	 */
	public OsRankType(String in_id) {
		this.setId(in_id);
	}

	@JsonBackReference
	public com.redxun.sys.org.entity.OsDimension getOsDimension() {
		return osDimension;
	}
	
	@JsonBackReference
	public void setOsDimension(
			com.redxun.sys.org.entity.OsDimension in_osDimension) {
		this.osDimension = in_osDimension;
	}

	/**
	 * 主键 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 主键
	 */
	public void setId(String aValue) {
		this.id = aValue;
	}

	/**
	 * 维度ID * @return String
	 */
	public String getDimId() {
		return this.getOsDimension() == null ? null : this.getOsDimension()
				.getDimId();
	}

	/**
	 * 设置 维度ID
	 */
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

	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 分类业务键 * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 分类业务键
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 级别数值 * @return Integer
	 */
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 设置 级别数值
	 */
	public void setLevel(Integer aValue) {
		this.level = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.id;
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
		if (!(object instanceof OsRankType)) {
			return false;
		}
		OsRankType rhs = (OsRankType) object;
		return new EqualsBuilder().append(this.id, rhs.id)
				.append(this.name, rhs.name).append(this.key, rhs.key)
				.append(this.level, rhs.level)
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
		return new HashCodeBuilder(-82280557, -700257973).append(this.id)
				.append(this.name).append(this.key).append(this.level)
				.append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("name", this.name).append("key", this.key)
				.append("level", this.level).append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
