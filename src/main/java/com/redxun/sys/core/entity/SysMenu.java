package com.redxun.sys.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseEntity;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.util.StringUtil;

/**
 * <pre>
 * 描述：SysMenu实体类定义
 * 菜单项目
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_MENU")
public class SysMenu extends BaseTenantEntity{
	/**
	 * 位图版本
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "MENU_ID_")
	protected String menuId;
	/* 菜单名称 */
	@Column(name = "NAME_")
	@Size(max = 60)
	@NotEmpty
	protected String name;
	/* 菜单Key */
	@Column(name = "KEY_")
	@Size(max = 80)
	@NotEmpty
	protected String key;
	
	/* 模块实体名 */
	@Column(name = "ENTITY_NAME_")
	@Size(max = 100)
	protected String entityName;
	
	/* 实体表单 */
	@Column(name = "FORM_")
	@Size(max = 80)
	protected String form;
	
	/* 图标样式 */
	@Column(name = "ICON_CLS_")
	@Size(max = 32)
	protected String iconCls;
	
	@Column(name = "IMG_")
	@Size(max = 50)
	protected String img;

	/* 上级父ID */
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String parentId;
	/* 层次 */
	@Column(name = "DEPTH_")
	protected Integer depth;
	/* 路径 */
	@Column(name = "PATH_")
	@Size(max = 256)
	protected String path;
	/* 序号 */
	@Column(name = "SN_")
	protected Integer sn;
	
	/* 子结节点数 */
	@Column(name = "CHILDS_")
	protected Integer childs=0;
	
	/* 访问地址URL */
	@Column(name = "URL_")
	@Size(max = 256)
	protected String url;
	/*
	 * 访问方式 缺省打开 在新窗口打开
	 */
	@Column(name = "SHOW_TYPE_")
	@Size(max = 20)
	protected String showType;
	

	
	@Column(name="IS_BTN_MENU_")
	@Size(max = 20)
	protected String isBtnMenu=MBoolean.NO.name();
	
	@ManyToOne
	@JoinColumn(name = "SYS_ID_")
	protected com.redxun.sys.core.entity.Subsystem subsystem;
	
	@Column(name="BO_LIST_ID_")
	protected String boListId="";
//	
	
	
	
	@Transient
	protected List<SysMenu> childList = new ArrayList<SysMenu>();
	

	/**
	 * Default Empty Constructor for class SysMenu
	 */
	public SysMenu() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysMenu
	 */
	public SysMenu(String in_menuId) {
		this.setMenuId(in_menuId);
	}

	public com.redxun.sys.core.entity.Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(com.redxun.sys.core.entity.Subsystem in_subsystem) {
		this.subsystem = in_subsystem;
	}



	public String getIsBtnMenu() {
		return isBtnMenu;
	}

	public void setIsBtnMenu(String isBtnMenu) {
		this.isBtnMenu = isBtnMenu;
	}

	/**
	 * * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 设置
	 */
	public void setMenuId(String aValue) {
		this.menuId = aValue;
	}

	/**
	 * 所属子系统 * @return String
	 */
	public String getSysId() {
		return this.getSubsystem() == null ? null : this.getSubsystem()
				.getSysId();
	}

	/**
	 * 设置 所属子系统
	 */
	public void setSysId(String aValue) {
		if (aValue == null) {
			subsystem = null;
		} else if (subsystem == null) {
			subsystem = new com.redxun.sys.core.entity.Subsystem(aValue);
			// subsystem.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			subsystem.setSysId(aValue);
		}
	}

	/**
	 * 菜单名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 菜单名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 图标 * @return String
	 */
	public String getIconCls() {
		if(StringUtils.isEmpty(this.iconCls) &&this.depth!=null && this.depth==1){
			return "icon-window";
		}
		return this.iconCls;
	}

	/**
	 * 设置 图标
	 */
	public void setIconCls(String aValue) {
		this.iconCls = aValue;
	}

	/**
	public String getGlyph() {
		return glyph;
	}

	public void setGlyph(String glyph) {
		this.glyph = glyph;
	}**/

	/**
	 * 上级父ID * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 上级父ID
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
	 * 访问地址URL * @return String
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 设置 访问地址URL
	 */
	public void setUrl(String aValue) {
		this.url = aValue;
	}

	/**
	 * 访问方式 缺省打开 在新窗口打开 * @return String
	 */
	public String getShowType() {
		return this.showType;
	}

	/**
	 * 设置 访问方式 缺省打开 在新窗口打开
	 */
	public void setShowType(String aValue) {
		this.showType = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.menuId;
	}

	@Override
	public Serializable getPkId() {
		return this.menuId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.menuId = (String) pkId;
	}

	public Integer getChilds() {
		return childs;
	}

	public void setChilds(Integer childs) {
		this.childs = childs;
	}

	public String getKey() {
		if(StringUtil.isEmpty(this.key)){
			return "";
		}
		return key.trim();
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	

	public List<SysMenu> getChildList() {
		return childList;
	}

	public void setChildList(List<SysMenu> childList) {
		this.childList = childList;
	}

	public String getBoListId() {
		return boListId;
	}

	public void setBoListId(String boListId) {
		this.boListId = boListId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysMenu)) {
			return false;
		}
		SysMenu rhs = (SysMenu) object;
		return new EqualsBuilder().append(this.menuId, rhs.menuId)
				.append(this.name, rhs.name).append(this.iconCls, rhs.iconCls)
				.append(this.parentId, rhs.parentId)
				.append(this.depth, rhs.depth).append(this.path, rhs.path)
				.append(this.sn, rhs.sn).append(this.url, rhs.url)
				.append(this.showType, rhs.showType)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.menuId)
				.append(this.name).append(this.iconCls).append(this.parentId)
				.append(this.depth).append(this.path).append(this.sn)
				.append(this.url).append(this.showType)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("menuId", this.menuId)
				.append("name", this.name).append("icon", this.iconCls)
				.append("parentId", this.parentId).append("depth", this.depth)
				.append("path", this.path).append("sn", this.sn)
				.append("url", this.url).append("showType", this.showType)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
