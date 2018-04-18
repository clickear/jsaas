package com.redxun.kms.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * 描述：KdUserLevel实体类定义
 * 用户知识等级配置
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_USER_LEVEL")
@TableDefine(title = "用户知识等级配置")
public class KdUserLevel extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CONF_ID_")
	protected String confId;
	/* 起始值 */
	@FieldDefine(title = "起始值")
	@Column(name = "START_VAL_")
	protected Integer startVal;
	/* 结束值 */
	@FieldDefine(title = "结束值")
	@Column(name = "END_VAL_")
	protected Integer endVal;
	/* 等级名称 */
	@FieldDefine(title = "等级名称")
	@Column(name = "LEVEL_NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String levelName;
	/* 头像Icon */
	@FieldDefine(title = "头像Icon")
	@Column(name = "HEADER_ICON_")
	@Size(max = 128)
	protected String headerIcon;
	/* 备注 */
	@FieldDefine(title = "备注")
	@Column(name = "MEMO_")
	@Size(max = 500)
	protected String memo;

	/**
	 * Default Empty Constructor for class KdUserLevel
	 */
	public KdUserLevel() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdUserLevel
	 */
	public KdUserLevel(String in_confId) {
		this.setConfId(in_confId);
	}

	/**
	 * 配置ID * @return String
	 */
	public String getConfId() {
		return this.confId;
	}

	/**
	 * 设置 配置ID
	 */
	public void setConfId(String aValue) {
		this.confId = aValue;
	}

	/**
	 * 起始值 * @return Integer
	 */
	public Integer getStartVal() {
		return this.startVal;
	}

	/**
	 * 设置 起始值
	 */
	public void setStartVal(Integer aValue) {
		this.startVal = aValue;
	}

	/**
	 * 结束值 * @return Integer
	 */
	public Integer getEndVal() {
		return this.endVal;
	}

	/**
	 * 设置 结束值
	 */
	public void setEndVal(Integer aValue) {
		this.endVal = aValue;
	}

	/**
	 * 等级名称 * @return String
	 */
	public String getLevelName() {
		return this.levelName;
	}

	/**
	 * 设置 等级名称
	 */
	public void setLevelName(String aValue) {
		this.levelName = aValue;
	}

	/**
	 * 头像Icon * @return String
	 */
	public String getHeaderIcon() {
		return this.headerIcon;
	}

	/**
	 * 设置 头像Icon
	 */
	public void setHeaderIcon(String aValue) {
		this.headerIcon = aValue;
	}

	/**
	 * 备注 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 设置 备注
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.confId;
	}

	@Override
	public Serializable getPkId() {
		return this.confId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.confId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdUserLevel)) {
			return false;
		}
		KdUserLevel rhs = (KdUserLevel) object;
		return new EqualsBuilder().append(this.confId, rhs.confId).append(this.startVal, rhs.startVal).append(this.endVal, rhs.endVal).append(this.levelName, rhs.levelName).append(this.headerIcon, rhs.headerIcon).append(this.memo, rhs.memo).append(this.tenantId, rhs.tenantId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.confId).append(this.startVal).append(this.endVal).append(this.levelName).append(this.headerIcon).append(this.memo).append(this.tenantId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("confId", this.confId).append("startVal", this.startVal).append("endVal", this.endVal).append("levelName", this.levelName).append("headerIcon", this.headerIcon).append("memo", this.memo).append("tenantId", this.tenantId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
