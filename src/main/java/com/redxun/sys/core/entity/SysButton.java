package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.entity.BaseEntity;

/**
 * <pre>
 * 描述：SysButton实体类定义
 * 系统功能按钮管理
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_BUTTON")
@TableDefine(title = "系统功能按钮管理")
public class SysButton extends BaseEntity {
	@FieldDefine(title="按钮ID",group="基本信息")
	@Id
	@Column(name = "BUTTON_ID_")
	protected String buttonId;
	/* 按钮名称 */
	@FieldDefine(title = "按钮名称", group = "基本信息")
	@Column(name = "NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String name;
	/* 按钮ICONCLS */
	@FieldDefine(title = "按钮ICONCLS", group = "基本信息")
	@Column(name = "ICON_CLS_")
	@Size(max = 50)
	protected String iconCls;
	/* GLYPH */
	@FieldDefine(title = "GLYPH", group = "基本信息")
	@Column(name = "GLYPH_")
	@Size(max = 50)
	protected String glyph;
	/* 序号 */
	@FieldDefine(title = "序号", group = "基本信息")
	@Column(name = "SN_")
	protected Integer sn;
	/**
	 * 处理器
	 */
	@Transient
	protected String handlerName;
	/*
	 * 按钮类型
	 * 
	 * 明细=DETAIL 新建=NEW 修改=EDIT 删除=DEL 高级查询=SEARCH_COMPOSE 新增附件=NEW_ATTACH
	 * 打印=PRINT 导出=EXPORT 按字段查询=SEARCH_FIELD 保存=SAVE 上一条=PREV 下一条=NEXT
	 * 自定义=CUSTOM
	 */
	@FieldDefine(title = "按钮类型", group = "基本信息")
	@Column(name = "BTN_TYPE_")
	@Size(max = 20)
	@NotEmpty
	protected String btnType;
	/* 按钮Key */
	@FieldDefine(title = "按钮Key", group = "基本信息")
	@Column(name = "KEY_")
	@Size(max = 50)
	@NotEmpty
	protected String key;
	/*
	 * 按钮位置 TOP=表头工具栏 MANAGE=管理列 FORM_BOTTOM=表单底部按钮栏 FORM_TOP=表单的头部
	 * DETAIL_TOP=明细的头部 DETAIL_BOTTOM=表单底部明细
	 */
	@FieldDefine(title = "按钮位置", group = "基本信息")
	@Column(name = "POS_")
	@Size(max = 50)
	@NotEmpty
	protected String pos;
	/* 自定义执行处理 */
	@FieldDefine(title = "自定义执行处理", group = "基本信息")
	@Column(name = "CUSTOM_HANDLER_")
	@Size(max = 65535)
	protected String customHandler;
	@FieldDefine(title = "系统模块", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "MODULE_ID_")
	protected com.redxun.sys.core.entity.SysModule sysModule;
	
	@FieldDefine(title = "关联系统模块", group = "基本信息")
	@ManyToOne
	@JoinColumn(name = "LINK_MODULE_ID_")
	protected com.redxun.sys.core.entity.SysModule linkSysModule;

	/**
	 * Default Empty Constructor for class SysButton
	 */
	public SysButton() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysButton
	 */
	public SysButton(String in_buttonId) {
		this.setButtonId(in_buttonId);
	}

	public com.redxun.sys.core.entity.SysModule getLinkSysModule() {
		return linkSysModule;
	}

	public void setLinkSysModule(com.redxun.sys.core.entity.SysModule linkSysModule) {
		this.linkSysModule = linkSysModule;
	}

	public com.redxun.sys.core.entity.SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(com.redxun.sys.core.entity.SysModule in_sysModule) {
		this.sysModule = in_sysModule;
	}

	/**
	 * 按钮ID * @return String
	 */
	public String getButtonId() {
		return this.buttonId;
	}

	/**
	 * 设置 按钮ID
	 */
	public void setButtonId(String aValue) {
		this.buttonId = aValue;
	}

	/**
	 * 模块ID * @return String
	 */
	public String getModuleId() {
		return this.getSysModule() == null ? null : this.getSysModule()
				.getModuleId();
	}

	/**
	 * 设置 模块ID
	 */
	public void setModuleId(String aValue) {
		if (aValue == null) {
			sysModule = null;
		} else if (sysModule == null) {
			sysModule = new com.redxun.sys.core.entity.SysModule(aValue);
			// sysModule.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysModule.setModuleId(aValue);
		}
	}

	/**
	 * 按钮名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 按钮名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 按钮ICONCLS * @return String
	 */
	public String getIconCls() {
		return this.iconCls;
	}

	/**
	 * 设置 按钮ICONCLS
	 */
	public void setIconCls(String aValue) {
		this.iconCls = aValue;
	}

	/**
	 * GLYPH * @return String
	 */
	public String getGlyph() {
		return this.glyph;
	}

	/**
	 * 设置 GLYPH
	 */
	public void setGlyph(String aValue) {
		this.glyph = aValue;
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
	 * 按钮类型
	 * 
	 * 明细=DETAIL 新建=NEW 修改=EDIT 删除=DEL 高级查询=SEARCH_COMPOSE 新增附件=NEW_ATTACH
	 * 打印=PRINT 导出=EXPORT 按字段查询=SEARCH_FIELD 保存=SAVE 上一条=PREV 下一条=NEXT
	 * 自定义=CUSTOM * @return String
	 */
	public String getBtnType() {
		return this.btnType;
	}

	/**
	 * 设置 按钮类型
	 * 
	 * 明细=DETAIL 新建=NEW 修改=EDIT 删除=DEL 高级查询=SEARCH_COMPOSE 新增附件=NEW_ATTACH
	 * 打印=PRINT 导出=EXPORT 按字段查询=SEARCH_FIELD 保存=SAVE 上一条=PREV 下一条=NEXT
	 * 自定义=CUSTOM
	 */
	public void setBtnType(String aValue) {
		this.btnType = aValue;
	}

	/**
	 * 按钮Key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 按钮Key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 按钮位置 TOP=表头工具栏 MANAGE=管理列 FORM_BOTTOM=表单底部按钮栏 FORM_TOP=表单的头部
	 * DETAIL_TOP=明细的头部 DETAIL_BOTTOM=表单底部明细
	 * 
	 * @return String
	 */
	public String getPos() {
		return this.pos;
	}

	/**
	 * 设置 按钮位置 TOP=表头工具栏 MANAGE=管理列 FORM_BOTTOM=表单底部按钮栏 FORM_TOP=表单的头部
	 * DETAIL_TOP=明细的头部 DETAIL_BOTTOM=表单底部明细
	 */
	public void setPos(String aValue) {
		this.pos = aValue;
	}

	/**
	 * 自定义执行处理 * @return String
	 */
	public String getCustomHandler() {
		return this.customHandler;
	}

	/**
	 * 设置 自定义执行处理
	 */
	public void setCustomHandler(String aValue) {
		this.customHandler = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.buttonId;
	}

	@Override
	public Serializable getPkId() {
		return this.buttonId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.buttonId = (String) pkId;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysButton)) {
			return false;
		}
		SysButton rhs = (SysButton) object;
		return new EqualsBuilder().append(this.buttonId, rhs.buttonId)
				.append(this.name, rhs.name).append(this.iconCls, rhs.iconCls)
				.append(this.glyph, rhs.glyph).append(this.sn, rhs.sn)
				.append(this.btnType, rhs.btnType).append(this.key, rhs.key)
				.append(this.pos, rhs.pos)
				.append(this.customHandler, rhs.customHandler).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.buttonId)
				.append(this.name).append(this.iconCls).append(this.glyph)
				.append(this.sn).append(this.btnType).append(this.key)
				.append(this.pos).append(this.customHandler).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("buttonId", this.buttonId)
				.append("name", this.name).append("iconCls", this.iconCls)
				.append("glyph", this.glyph).append("sn", this.sn)
				.append("btnType", this.btnType).append("key", this.key)
				.append("pos", this.pos)
				.append("customHandler", this.customHandler).toString();
	}

}
