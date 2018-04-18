package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 * 描述：SysGridField实体类定义
 * 列表视图字段
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_GRID_FIELD")
public class SysGridField extends BaseEntity {

	@Id
	@Column(name = "GD_FIELD_ID_")
	protected String gdFieldId;
	/* 序号 */
	@Column(name = "SN_")
	protected Integer sn;
	/**
	 * 父ID
	 */
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	/**
	 * 路径
	 */
	@Column(name = "PATH_")
	@Size(max = 255)
	protected String path;
	/**
	 * 项类型
	 * 组=GROUP
	 * 字段=FIELD
	 */
	@Column(name = "ITEM_TYPE_")
	@Size(max = 20)
	protected String itemType;
	
	/* 是否锁定 */
	@Column(name = "IS_LOCK_")
	@Size(max = 8)
	protected String isLock;
	/* 是否允许排序 */
	@Column(name = "ALLOW_SORT_")
	@Size(max = 8)
	protected String allowSort;
	/* 是否隐藏 */
	@Column(name = "IS_HIDDEN_")
	@Size(max = 8)
	protected String isHidden;
	/* 是否允许总计 */
	@Column(name = "ALLOW_SUM_")
	@Size(max = 8)
	protected String allowSum;
	/* 列宽 */
	@Column(name = "COL_WIDTH_")
	protected Integer colWidth;
	/* 是否允许导出 */
	@Column(name = "IS_EXPORT_")
	@Size(max = 8)
	protected String isExport;
	/* 格式化 */
	@Column(name = "FOMART_")
	@Size(max = 250)
	protected String fomart;
	/* 备注 */
	@Column(name = "REMARK_")
	@Size(max = 65535)
	protected String remark;
	
	@ManyToOne
	@JoinColumn(name = "GRID_VIEW_ID_")
	protected com.redxun.sys.core.entity.SysGridView sysGridView;

	@ManyToOne
	@JoinColumn(name = "FIELD_ID_")
	protected com.redxun.sys.core.entity.SysField sysField;
	
	@Column(name = "FIELD_NAME_")
	@Size(max = 50)
	protected String fieldName;
	
	@Column(name = "FIELD_TITLE_")
	@Size(max = 50)
	@NotEmpty
	protected String fieldTitle;

	/**
	 * Default Empty Constructor for class SysGridField
	 */
	public SysGridField() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysGridField
	 */
	public SysGridField(String in_gdFieldId) {
		this.setGdFieldId(in_gdFieldId);
	}
	@JsonBackReference
	public com.redxun.sys.core.entity.SysGridView getSysGridView() {
		return sysGridView;
	}

	public void setSysGridView(
			com.redxun.sys.core.entity.SysGridView in_sysGridView) {
		this.sysGridView = in_sysGridView;
	}

	@JsonBackReference
	public com.redxun.sys.core.entity.SysField getSysField() {
		return sysField;
	}

	public void setSysField(com.redxun.sys.core.entity.SysField in_sysField) {
		this.sysField = in_sysField;
	}

	/**
	 * * @return String
	 */
	public String getGdFieldId() {
		return this.gdFieldId;
	}

	/**
	 * 设置
	 */
	public void setGdFieldId(String aValue) {
		this.gdFieldId = aValue;
	}

	/**
	 * 字段ID * @return String
	 */
	public String getFieldId() {
		return this.getSysField() == null ? null : this.getSysField()
				.getFieldId();
	}

	/**
	 * 设置 字段ID
	 */
	public void setFieldId(String aValue) {
		if (aValue == null) {
			sysField = null;
		} else if (sysField == null) {
			sysField = new com.redxun.sys.core.entity.SysField(aValue);
			// sysField.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysField.setFieldId(aValue);
		}
	}

	/**
	 * 所属图视图ID 当不属于任何分组时，需要填写该值 * @return String
	 */
	public String getGridViewId() {
		return this.getSysGridView() == null ? null : this.getSysGridView()
				.getGridViewId();
	}

	/**
	 * 设置 所属图视图ID 当不属于任何分组时，需要填写该值
	 */
	public void setGridViewId(String aValue) {
		if (aValue == null) {
			sysGridView = null;
		} else if (sysGridView == null) {
			sysGridView = new com.redxun.sys.core.entity.SysGridView(aValue);
			// sysGridView.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysGridView.setGridViewId(aValue);
		}
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

	
	/**
	 * 是否锁定 * @return String
	 */
	public String getIsLock() {
		return this.isLock;
	}

	/**
	 * 设置 是否锁定
	 */
	public void setIsLock(String aValue) {
		this.isLock = aValue;
	}

	/**
	 * 是否允许排序 * @return String
	 */
	public String getAllowSort() {
		return this.allowSort;
	}

	/**
	 * 设置 是否允许排序
	 */
	public void setAllowSort(String aValue) {
		this.allowSort = aValue;
	}

	/**
	 * 是否隐藏 * @return String
	 */
	public String getIsHidden() {
		return this.isHidden;
	}

	/**
	 * 设置 是否隐藏
	 */
	public void setIsHidden(String aValue) {
		this.isHidden = aValue;
	}

	/**
	 * 是否允许总计 * @return String
	 */
	public String getAllowSum() {
		return this.allowSum;
	}

	/**
	 * 设置 是否允许总计
	 */
	public void setAllowSum(String aValue) {
		this.allowSum = aValue;
	}

	/**
	 * 列宽 * @return Integer
	 */
	public Integer getColWidth() {
		return this.colWidth;
	}

	/**
	 * 设置 列宽
	 */
	public void setColWidth(Integer aValue) {
		this.colWidth = aValue;
	}

	/**
	 * 是否允许导出 * @return String
	 */
	public String getIsExport() {
		return this.isExport;
	}

	/**
	 * 设置 是否允许导出
	 */
	public void setIsExport(String aValue) {
		this.isExport = aValue;
	}

	/**
	 * 格式化 * @return String
	 */
	public String getFomart() {
		return this.fomart;
	}

	/**
	 * 设置 格式化
	 */
	public void setFomart(String aValue) {
		this.fomart = aValue;
	}

	/**
	 * 备注 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置 备注
	 */
	public void setRemark(String aValue) {
		this.remark = aValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	@Override
	public String getIdentifyLabel() {
		return this.gdFieldId;
	}

	@Override
	public Serializable getPkId() {
		return this.gdFieldId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.gdFieldId = (String) pkId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysGridField)) {
			return false;
		}
		SysGridField rhs = (SysGridField) object;
		return new EqualsBuilder().append(this.gdFieldId, rhs.gdFieldId)
				.append(this.sn, rhs.sn)
				.append(this.isLock, rhs.isLock)
				.append(this.allowSort, rhs.allowSort)
				.append(this.isHidden, rhs.isHidden)
				.append(this.allowSum, rhs.allowSum)
				.append(this.colWidth, rhs.colWidth)
				.append(this.isExport, rhs.isExport)
				.append(this.fomart, rhs.fomart)
				.append(this.remark, rhs.remark).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.gdFieldId).append(this.sn)
				.append(this.isLock).append(this.allowSort)
				.append(this.isHidden).append(this.allowSum)
				.append(this.colWidth).append(this.isExport)
				.append(this.fomart).append(this.remark).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("gdFieldId", this.gdFieldId)
				.append("fieldName",this.fieldName)
				.append("fieldTitle",this.fieldTitle)
				.append("parentId",this.parentId)
				.append("path",this.path)
				.append("sn", this.sn)
				.append("isLock", this.isLock)
				.append("allowSort", this.allowSort)
				.append("isHidden", this.isHidden)
				.append("allowSum", this.allowSum)
				.append("colWidth", this.colWidth)
				.append("isExport", this.isExport)
				.append("fomart", this.fomart).append("remark", this.remark)
				.toString();
	}

}
