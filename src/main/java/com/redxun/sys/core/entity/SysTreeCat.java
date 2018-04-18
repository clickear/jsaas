package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysTreeCat实体类定义
 * 系统树分类类型
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_TREE_CAT")
@TableDefine(title="树分类类型")
public class SysTreeCat extends BaseTenantEntity {
	/**
	 * 系统分类
	 */
	public final static String CAT_SYS="CAT_SYS";
	/**
	 * 组织架构分类
	 */
	public final static String CAT_ORG="CAT_ORG";
	/**
	 * 组织架构分类
	 */
	public final static String CAT_DIM="CAT_DIM";
	
	@FieldDefine(group="基础信息",title="主键")
	@Id
	@Column(name = "cat_id_")
	protected String catId;
	
	@FieldDefine(group="基础信息",title="键值")
	@Column(name = "key_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	
	@FieldDefine(group="基础信息",title="名称")
	@Column(name = "name_")
	@Size(max = 64)
	@NotEmpty
	protected String name;
	
	@FieldDefine(group="基础信息",title="序号")
	@Column(name = "sn_")
	protected Integer sn;
	
	@FieldDefine(group="基础信息",title="描述")
	@Column(name = "descp_")
	protected String descp;

	/**
	 * Default Empty Constructor for class SysTreeCat
	 */
	public SysTreeCat() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysTreeCat
	 */
	public SysTreeCat(String in_catId) {
		this.setCatId(in_catId);
	}

	/**
	 * * @return String
	 */
	public String getCatId() {
		return this.catId;
	}

	/**
	 * 设置
	 */
	public void setCatId(String aValue) {
		this.catId = aValue;
	}

	/**
	 * 分类Key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 分类Key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 分类名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 分类名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
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
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.catId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.catId = (String) pkId;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysTreeCat)) {
			return false;
		}
		SysTreeCat rhs = (SysTreeCat) object;
		return new EqualsBuilder().append(this.catId, rhs.catId)
				.append(this.key, rhs.key).append(this.name, rhs.name)
				.append(this.sn, rhs.sn)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.catId)
				.append(this.key).append(this.name).append(this.sn).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("catId", this.catId)
				.append("key", this.key).append("name", this.name)
				.append("sn", this.sn)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
