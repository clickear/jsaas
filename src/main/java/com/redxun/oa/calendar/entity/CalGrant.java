package com.redxun.oa.calendar.entity;

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

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：CalGrant实体类定义
 * 日历分配
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "CAL_GRANT")
@TableDefine(title = "日历分配")
@JsonIgnoreProperties("calSetting")
public class CalGrant extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "GRANT_ID_")
	protected String grantId;
	/* 分配类型 USER/GROUP */
	@FieldDefine(title = "分配类型 USER/GROUP")
	@Column(name = "GRANT_TYPE_")
	@Size(max = 64)
	protected String grantType;
	/* 被分配者 GROUPID/USERID */
	@FieldDefine(title = "被分配者   GROUPID/USERID")
	@Column(name = "BELONG_WHO_")
	protected String belongWho;
	@FieldDefine(title = "日历设定")
	@ManyToOne
	@JoinColumn(name = "SETTING_ID_")
	protected com.redxun.oa.calendar.entity.CalSetting calSetting;

	/**
	 * Default Empty Constructor for class CalGrant
	 */
	public CalGrant() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class CalGrant
	 */
	public CalGrant(String in_grantId) {
		this.setGrantId(in_grantId);
	}

	public com.redxun.oa.calendar.entity.CalSetting getCalSetting() {
		return calSetting;
	}

	public void setCalSetting(com.redxun.oa.calendar.entity.CalSetting in_calSetting) {
		this.calSetting = in_calSetting;
	}

	/**
	 * 主键 * @return String
	 */
	public String getGrantId() {
		return this.grantId;
	}

	/**
	 * 设置 主键
	 */
	public void setGrantId(String aValue) {
		this.grantId = aValue;
	}

	/**
	 * 设定ID * @return String
	 */
	public String getSettingId() {
		return this.getCalSetting() == null ? null : this.getCalSetting().getSettingId();
	}

	/**
	 * 设置 设定ID
	 */
	public void setSettingId(String aValue) {
		if (aValue == null) {
			calSetting = null;
		} else if (calSetting == null) {
			calSetting = new com.redxun.oa.calendar.entity.CalSetting(aValue);
		} else {
			calSetting.setSettingId(aValue);
		}
	}

	/**
	 * 分配类型 USER/GROUP * @return String
	 */
	public String getGrantType() {
		return this.grantType;
	}

	/**
	 * 设置 分配类型 USER/GROUP
	 */
	public void setGrantType(String aValue) {
		this.grantType = aValue;
	}

	/**
	 * 被分配者 GROUPID/USERID * @return String
	 */
	public String getBelongWho() {
		return this.belongWho;
	}

	/**
	 * 设置 被分配者 GROUPID/USERID
	 */
	public void setBelongWho(String aValue) {
		this.belongWho = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.grantId;
	}

	@Override
	public Serializable getPkId() {
		return this.grantId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.grantId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CalGrant)) {
			return false;
		}
		CalGrant rhs = (CalGrant) object;
		return new EqualsBuilder().append(this.grantId, rhs.grantId).append(this.grantType, rhs.grantType).append(this.belongWho, rhs.belongWho).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.grantId).append(this.grantType).append(this.belongWho).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("grantId", this.grantId).append("grantType", this.grantType).append("belongWho", this.belongWho).append("updateTime", this.updateTime).append("updateBy", this.updateBy)
				.append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
