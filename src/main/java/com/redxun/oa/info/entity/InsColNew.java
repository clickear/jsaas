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
 * 描述：InsColNew实体类定义
 * 信息所属栏目
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_COL_NEW")
@TableDefine(title = "信息所属栏目")
public class InsColNew extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ID_")
	protected String id;
	/* 序号 */
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	protected Integer sn;
	/* 有效开始时间 */
	@FieldDefine(title = "有效开始时间")
	@Column(name = "START_TIME_")
	protected java.util.Date startTime;
	/* 有效结束时间 */
	@FieldDefine(title = "有效结束时间")
	@Column(name = "END_TIME_")
	protected java.util.Date endTime;
	/* 是否长期有效 */
	@FieldDefine(title = "是否长期有效")
	@Column(name = "IS_LONG_VALID_")
	@Size(max = 20)
	protected String isLongValid;
	@FieldDefine(title = "栏目")
	@ManyToOne
	@JoinColumn(name = "COL_ID_")
	protected com.redxun.oa.info.entity.InsColumn insColumn;
	@FieldDefine(title = "新闻")
	@ManyToOne
	@JoinColumn(name = "NEW_ID_")
	protected com.redxun.oa.info.entity.InsNews insNews;

	/**
	 * Default Empty Constructor for class InsColNew
	 */
	public InsColNew() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InsColNew
	 */
	public InsColNew(String in_id) {
		this.setId(in_id);
	}

	public com.redxun.oa.info.entity.InsColumn getInsColumn() {
		return insColumn;
	}

	public void setInsColumn(com.redxun.oa.info.entity.InsColumn in_insColumn) {
		this.insColumn = in_insColumn;
	}

	public com.redxun.oa.info.entity.InsNews getInsNews() {
		return insNews;
	}

	public void setInsNews(com.redxun.oa.info.entity.InsNews in_insNews) {
		this.insNews = in_insNews;
	}

	/**
	 * ID_ * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置 ID_
	 */
	public void setId(String aValue) {
		this.id = aValue;
	}

		
	public String getIsLongValid() {
		return isLongValid;
	}

	public void setIsLongValid(String isLongValid) {
		this.isLongValid = isLongValid;
	}

	/**
	 * 栏目ID * @return String
	 */
	public String getColId() {
		return this.getInsColumn() == null ? null : this.getInsColumn().getColId();
	}

	/**
	 * 设置 栏目ID
	 */
	public void setColId(String aValue) {
		if (aValue == null) {
			insColumn = null;
		} else if (insColumn == null) {
			insColumn = new com.redxun.oa.info.entity.InsColumn(aValue);
			// insColumn.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			insColumn.setColId(aValue);
		}
	}

	/**
	 * 新闻ID * @return String
	 */
	public String getNewId() {
		return this.getInsNews() == null ? null : this.getInsNews().getNewId();
	}

	/**
	 * 设置 新闻ID
	 */
	public void setNewId(String aValue) {
		if (aValue == null) {
			insNews = null;
		} else if (insNews == null) {
			insNews = new com.redxun.oa.info.entity.InsNews(aValue);
			// insNews.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			insNews.setNewId(aValue);
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
	 * 有效开始时间 * @return java.util.Date
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置 有效开始时间
	 */
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}

	/**
	 * 有效结束时间 * @return java.util.Date
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置 有效结束时间
	 */
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
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
		if (!(object instanceof InsColNew)) {
			return false;
		}
		InsColNew rhs = (InsColNew) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.sn, rhs.sn).append(this.startTime, rhs.startTime).append(this.endTime, rhs.endTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.id).append(this.sn).append(this.startTime).append(this.endTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("sn", this.sn).append("startTime", this.startTime).append("endTime", this.endTime).toString();
	}

}
