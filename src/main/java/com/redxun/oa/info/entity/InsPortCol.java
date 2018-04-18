package com.redxun.oa.info.entity;

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
import javax.validation.constraints.NotNull;
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
 * 描述：InsPortCol实体类定义
 * 门户栏目配置
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_PORT_COL")
@TableDefine(title = "门户栏目配置")
public class InsPortCol extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CONF_ID_")
	protected String confId;
	/* 宽度 */
	@FieldDefine(title = "宽度")
	@Column(name = "WIDTH_")
	protected Integer width;
	/* 高度 */
	@FieldDefine(title = "高度")
	@Column(name = "HEIGHT_")
	protected Integer height;

	/* 宽度单位*/
	@FieldDefine(title = "宽度单位")
	@Column(name = "WIDTH_UNIT_")
	@Size(max = 8)
	protected String widthUnit;
	/* 高度单位*/
	@FieldDefine(title = "高度单位 ")
	@Column(name = "HEIGHT_UNIT_")
	@Size(max = 8)
	@NotEmpty
	protected String heightUnit;
	@FieldDefine(title = "列号")
	@Column(name = "COL_NUM_")
	protected Integer colNum;
	/* 序号 */
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	@NotNull
	protected Integer sn;
	@FieldDefine(title = "信息栏目")
	@ManyToOne
	@JoinColumn(name = "COL_ID_")
	protected com.redxun.oa.info.entity.InsColumn insColumn;
	@FieldDefine(title = "PORTAL门户定义")
	@ManyToOne
	@JoinColumn(name = "PORT_ID_")
	protected com.redxun.oa.info.entity.InsPortal insPortal;

	/**
	 * Default Empty Constructor for class InsPortCol
	 */
	public InsPortCol() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InsPortCol
	 */
	public InsPortCol(String in_confId) {
		this.setConfId(in_confId);
	}

	public com.redxun.oa.info.entity.InsColumn getInsColumn() {
		return insColumn;
	}

	public void setInsColumn(com.redxun.oa.info.entity.InsColumn in_insColumn) {
		this.insColumn = in_insColumn;
	}

	public com.redxun.oa.info.entity.InsPortal getInsPortal() {
		return insPortal;
	}

	public void setInsPortal(com.redxun.oa.info.entity.InsPortal in_insPortal) {
		this.insPortal = in_insPortal;
	}

	/**
	 * * @return String
	 */
	public String getConfId() {
		return this.confId;
	}

	/**
	 * 设置
	 */
	public void setConfId(String aValue) {
		this.confId = aValue;
	}

	/**
	 * 门户ID * @return String
	 */
	public String getPortId() {
		return this.getInsPortal() == null ? null : this.getInsPortal()
				.getPortId();
	}

	/**
	 * 设置 门户ID
	 */
	public void setPortId(String aValue) {
		if (aValue == null) {
			insPortal = null;
		} else if (insPortal == null) {
			insPortal = new com.redxun.oa.info.entity.InsPortal(aValue);
		} else {
			insPortal.setPortId(aValue);
		}
	}

	/**
	 * 栏目ID * @return String
	 */
	public String getColId() {
		return this.getInsColumn() == null ? null : this.getInsColumn()
				.getColId();
	}

	/**
	 * 设置 栏目ID
	 */
	public void setColId(String aValue) {
		if (aValue == null) {
			insColumn = null;
		} else if (insColumn == null) {
			insColumn = new com.redxun.oa.info.entity.InsColumn(aValue);
		} else {
			insColumn.setColId(aValue);
		}
	}

	/**
	 * 宽度 * @return Integer
	 */
	public Integer getWidth() {
		return this.width;
	}

	/**
	 * 设置 宽度
	 */
	public void setWidth(Integer aValue) {
		this.width = aValue;
	}

	/**
	 * 高度 * @return Integer
	 */
	public Integer getHeight() {
		return this.height;
	}

	/**
	 * 设置 高度
	 */
	public void setHeight(Integer aValue) {
		this.height = aValue;
	}

	/**
	 * 宽度单位 百份比=% 像数=px * @return String
	 */
	public String getWidthUnit() {
		return this.widthUnit;
	}

	/**
	 * 设置 宽度单位 百份比=% 像数=px
	 */
	public void setWidthUnit(String aValue) {
		this.widthUnit = aValue;
	}

	/**
	 * 高度单位 百份比=% 像数=px * @return String
	 */
	public String getHeightUnit() {
		return this.heightUnit;
	}

	/**
	 * 设置 高度单位 百份比=% 像数=px
	 */
	public void setHeightUnit(String aValue) {
		this.heightUnit = aValue;
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

	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsPortCol)) {
			return false;
		}
		InsPortCol rhs = (InsPortCol) object;
		return new EqualsBuilder().append(this.confId, rhs.confId)
				.append(this.width, rhs.width).append(this.height, rhs.height)
				.append(this.widthUnit, rhs.widthUnit)
				.append(this.heightUnit, rhs.heightUnit)
				.append(this.sn, rhs.sn).append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.confId)
				.append(this.width).append(this.height).append(this.widthUnit)
				.append(this.heightUnit).append(this.sn).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("confId", this.confId)
				.append("width", this.width).append("height", this.height)
				.append("widthUnit", this.widthUnit)
				.append("heightUnit", this.heightUnit).append("sn", this.sn)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
