package com.redxun.crm.bm.entity;

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

import com.redxun.bpm.form.entity.BpmFormView;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：Bpmfvright实体类定义
 * 表单视图字段权限
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "BPM_FV_RIGHT")
@TableDefine(title = "表单视图字段权限")
public class Bpmfvright extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "RIGHT_ID_")
	protected String rightId;
	/* Activiti定义ID */
	@FieldDefine(title = "Activiti定义ID")
	@Column(name = "ACT_DEF_ID_")
	@Size(max = 64)
	protected String actDefId;
	/* 字段路径 */
	@FieldDefine(title = "字段路径")
	@Column(name = "FIELD_NAME_")
	@Size(max = 255)
	@NotEmpty
	protected String fieldName;
	/**/
	@FieldDefine(title = "")
	@Column(name = "FIELD_LABEL_")
	@Size(max = 255)
	@NotEmpty
	protected String fieldLabel;
	/* 编辑权限描述 */
	@FieldDefine(title = "编辑权限描述")
	@Column(name = "EDIT_")
	@Size(max = 65535)
	protected String edit;
	/*
	 * 编辑权限 格式： { all:false, userIds:'', unames:'', groupIds:'', gnames:'' }
	 */
	@FieldDefine(title="编辑权限格式：{all:false,userIds:'',unames:'',groupIds:'',gnames:''}")
	@Column(name = "EDIT_DF_")
	@Size(max = 65535)
	protected String editDf;
	/* 只读权限 */
	@FieldDefine(title = "只读权限")
	@Column(name = "READ_")
	@Size(max = 65535)
	protected String read;
	/* 只读权限描述 */
	@FieldDefine(title = "只读权限描述")
	@Column(name = "READ_DF_")
	@Size(max = 65535)
	protected String readDf;
	/* 隐藏权限 */
	@FieldDefine(title = "隐藏权限")
	@Column(name = "HIDE_")
	@Size(max = 65535)
	protected String hide;
	/* 隐藏权限描述 */
	@FieldDefine(title = "隐藏权限描述")
	@Column(name = "HIDE_DF_")
	@Size(max = 65535)
	protected String hideDf;
	/* 序号 */
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	protected Integer sn;
	/* 流程节点ID */
	@FieldDefine(title = "流程节点ID")
	@Column(name = "NODE_ID_")
	@Size(max = 64)
	protected String nodeId;
	/* 流程解决方案Id */
	@FieldDefine(title = "流程解决方案Id")
	@Column(name = "SOL_ID_")
	@Size(max = 64)
	protected String solId;
	/* 权限类型(field:字段,opinion:意见) */
	@FieldDefine(title = "权限类型(field:字段,opinion:意见)")
	@Column(name = "TYPE_")
	@Size(max = 64)
	protected String type;
	/* 子表添加数据处理 */
	@FieldDefine(title = "子表添加数据处理")
	@Column(name = "DEALWITH_")
	@Size(max = 20)
	protected String dealwith;
	/* 表单别名 */
	@FieldDefine(title = "表单别名")
	@Column(name = "FORM_ALIAS_")
	@Size(max = 64)
	protected String formAlias;
	@FieldDefine(title = "业务表单视图")
	//@ManyToOne
	@JoinColumn(name = "VIEW_ID_")
	protected BpmFormView bpmFormView;

	/**
	 * Default Empty Constructor for class Bpmfvright
	 */
	public Bpmfvright() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Bpmfvright
	 */
	public Bpmfvright(String in_rightId) {
		this.setRightId(in_rightId);
	}

	public BpmFormView getBpmFormView() {
		return bpmFormView;
	}

	public void setBpmFormView(BpmFormView in_bpmFormView) {
		this.bpmFormView = in_bpmFormView;
	}

	/**
	 * 权限ID * @return String
	 */
	public String getRightId() {
		return this.rightId;
	}

	/**
	 * 设置 权限ID
	 */
	public void setRightId(String aValue) {
		this.rightId = aValue;
	}

	/**
	 * 业务表单视图ID * @return String
	 */
	public String getViewId() {
		return this.getBpmFormView() == null ? null : this.getBpmFormView().getViewId();
	}

	/**
	 * 设置 业务表单视图ID
	 */
	public void setViewId(String aValue) {
		if (aValue == null) {
			bpmFormView = null;
		} else if (bpmFormView == null) {
			bpmFormView = new BpmFormView(aValue);
		} else {
			bpmFormView.setViewId(aValue);
		}
	}

	/**
	 * Activiti定义ID * @return String
	 */
	public String getActDefId() {
		return this.actDefId;
	}

	/**
	 * 设置 Activiti定义ID
	 */
	public void setActDefId(String aValue) {
		this.actDefId = aValue;
	}

	/**
	 * 字段路径 * @return String
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * 设置 字段路径
	 */
	public void setFieldName(String aValue) {
		this.fieldName = aValue;
	}

	/**
	 * * @return String
	 */
	public String getFieldLabel() {
		return this.fieldLabel;
	}

	/**
	 * 设置
	 * 
	 */
	public void setFieldLabel(String aValue) {
		this.fieldLabel = aValue;
	}

	/**
	 * 编辑权限描述 * @return String
	 */
	public String getEdit() {
		return this.edit;
	}

	/**
	 * 设置 编辑权限描述
	 */
	public void setEdit(String aValue) {
		this.edit = aValue;
	}

	/**
	 * 编辑权限 格式： { all:false, userIds:'', unames:'', groupIds:'', gnames:'' }
	 * * @return String
	 */
	public String getEditDf() {
		return this.editDf;
	}

	/**
	 * 设置 编辑权限 格式： { all:false, userIds:'', unames:'', groupIds:'', gnames:'' }
	 */
	public void setEditDf(String aValue) {
		this.editDf = aValue;
	}

	/**
	 * 只读权限 * @return String
	 */
	public String getRead() {
		return this.read;
	}

	/**
	 * 设置 只读权限
	 */
	public void setRead(String aValue) {
		this.read = aValue;
	}

	/**
	 * 只读权限描述 * @return String
	 */
	public String getReadDf() {
		return this.readDf;
	}

	/**
	 * 设置 只读权限描述
	 */
	public void setReadDf(String aValue) {
		this.readDf = aValue;
	}

	/**
	 * 隐藏权限 * @return String
	 */
	public String getHide() {
		return this.hide;
	}

	/**
	 * 设置 隐藏权限
	 */
	public void setHide(String aValue) {
		this.hide = aValue;
	}

	/**
	 * 隐藏权限描述 * @return String
	 */
	public String getHideDf() {
		return this.hideDf;
	}

	/**
	 * 设置 隐藏权限描述
	 */
	public void setHideDf(String aValue) {
		this.hideDf = aValue;
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
	 * 流程节点ID * @return String
	 */
	public String getNodeId() {
		return this.nodeId;
	}

	/**
	 * 设置 流程节点ID
	 */
	public void setNodeId(String aValue) {
		this.nodeId = aValue;
	}

	/**
	 * 流程解决方案Id * @return String
	 */
	public String getSolId() {
		return this.solId;
	}

	/**
	 * 设置 流程解决方案Id
	 */
	public void setSolId(String aValue) {
		this.solId = aValue;
	}

	/**
	 * 权限类型(field:字段,opinion:意见) * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 权限类型(field:字段,opinion:意见)
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 子表添加数据处理 * @return String
	 */
	public String getDealwith() {
		return this.dealwith;
	}

	/**
	 * 设置 子表添加数据处理
	 */
	public void setDealwith(String aValue) {
		this.dealwith = aValue;
	}

	/**
	 * 表单别名 * @return String
	 */
	public String getFormAlias() {
		return this.formAlias;
	}

	/**
	 * 设置 表单别名
	 */
	public void setFormAlias(String aValue) {
		this.formAlias = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.rightId;
	}

	@Override
	public Serializable getPkId() {
		return this.rightId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.rightId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Bpmfvright)) {
			return false;
		}
		Bpmfvright rhs = (Bpmfvright) object;
		return new EqualsBuilder().append(this.rightId, rhs.rightId).append(this.actDefId, rhs.actDefId)
				.append(this.fieldName, rhs.fieldName).append(this.fieldLabel, rhs.fieldLabel)
				.append(this.edit, rhs.edit).append(this.editDf, rhs.editDf).append(this.read, rhs.read)
				.append(this.readDf, rhs.readDf).append(this.hide, rhs.hide).append(this.hideDf, rhs.hideDf)
				.append(this.sn, rhs.sn).append(this.nodeId, rhs.nodeId).append(this.solId, rhs.solId)
				.append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).append(this.type, rhs.type).append(this.dealwith, rhs.dealwith)
				.append(this.formAlias, rhs.formAlias).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rightId).append(this.actDefId)
				.append(this.fieldName).append(this.fieldLabel).append(this.edit).append(this.editDf).append(this.read)
				.append(this.readDf).append(this.hide).append(this.hideDf).append(this.sn).append(this.nodeId)
				.append(this.solId).append(this.tenantId).append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).append(this.type).append(this.dealwith)
				.append(this.formAlias).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rightId", this.rightId).append("actDefId", this.actDefId)
				.append("fieldName", this.fieldName).append("fieldLabel", this.fieldLabel).append("edit", this.edit)
				.append("editDf", this.editDf).append("read", this.read).append("readDf", this.readDf)
				.append("hide", this.hide).append("hideDf", this.hideDf).append("sn", this.sn)
				.append("nodeId", this.nodeId).append("solId", this.solId).append("tenantId", this.tenantId)
				.append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).append("type", this.type)
				.append("dealwith", this.dealwith).append("formAlias", this.formAlias).toString();
	}

}
