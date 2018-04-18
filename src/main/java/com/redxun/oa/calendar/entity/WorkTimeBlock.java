package com.redxun.oa.calendar.entity;

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
 * 描述：WorkTimeBlock实体类定义
 * 工作时间段设定
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "CAL_TIME_BLOCK")
@TableDefine(title = "工作时间段设定")
public class WorkTimeBlock extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "SETTING_ID_")
	protected String settingId;
	/* 设定名称 */
	@FieldDefine(title = "设定名称")
	@Column(name = "SETTING_NAME_")
	@Size(max = 128)
	protected String settingName;
	/* 时间段组合json组合 */
	@FieldDefine(title = "时间段组合json组合")
	@Column(name = "TIME_INTERVALS_")
	@Size(max = 1024)
	protected String timeIntervals;

	/**
	 * Default Empty Constructor for class WorkTimeBlock
	 */
	public WorkTimeBlock() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class WorkTimeBlock
	 */
	public WorkTimeBlock(String in_settingId) {
		this.setSettingId(in_settingId);
	}

	/**
	 * 设定ID * @return String
	 */
	public String getSettingId() {
		return this.settingId;
	}

	/**
	 * 设置 设定ID
	 */
	public void setSettingId(String aValue) {
		this.settingId = aValue;
	}

	/**
	 * 设定名称 * @return String
	 */
	public String getSettingName() {
		return this.settingName;
	}

	/**
	 * 设置 设定名称
	 */
	public void setSettingName(String aValue) {
		this.settingName = aValue;
	}

	/**
	 * 时间段组合json组合 * @return String
	 */
	public String getTimeIntervals() {
		return this.timeIntervals;
	}

	/**
	 * 设置 时间段组合json组合
	 */
	public void setTimeIntervals(String aValue) {
		this.timeIntervals = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.settingId;
	}

	@Override
	public Serializable getPkId() {
		return this.settingId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.settingId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WorkTimeBlock)) {
			return false;
		}
		WorkTimeBlock rhs = (WorkTimeBlock) object;
		return new EqualsBuilder().append(this.settingId, rhs.settingId).append(this.settingName, rhs.settingName).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).append(this.timeIntervals, rhs.timeIntervals).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.settingId).append(this.settingName).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId)
				.append(this.timeIntervals).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("settingId", this.settingId).append("settingName", this.settingName).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime)
				.append("createBy", this.createBy).append("tenantId", this.tenantId).append("timeIntervals", this.timeIntervals).toString();
	}

}
