package com.redxun.sys.core.entity;

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

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysReport实体类定义
 * 系统报表
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "SYS_REPORT")
@TableDefine(title = "系统报表")
public class SysReport extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "REP_ID_")
	protected String repId;
	/* 标题 */
	@FieldDefine(title = "标题")
	@Column(name = "SUBJECT_")
	@Size(max = 128)
	@NotEmpty
	protected String subject;
	/* 标识key */
	@FieldDefine(title = "标识key")
	@Column(name = "KEY_")
	@Size(max = 128)
	protected String key;
	/* 描述 */
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	@Size(max = 500)
	protected String descp;
	/* 报表模块的jasper文件的路径 */
	@FieldDefine(title = "报表模板路径")
	@Column(name = "FILE_PATH_")
	@Size(max = 128)
	@NotEmpty
	protected String filePath;
	/**/
	@FieldDefine(title = "文件ID")
	@Column(name = "FILE_ID_")
	@Size(max = 64)
	protected String fileId;
	/*
	 * 是否缺省 1=缺省 0=非缺省
	 */
	@FieldDefine(title = "是否缺省")
	@Column(name = "IS_DEFAULT_")
	@Size(max = 20)
	protected String defaults;

	/*
	 * 自定义处理参数
	 */
	@FieldDefine(title = "自定义处理参数")
	@Column(name = "SELF_HANDLE_BEAN_")
	@Size(max = 100)
	protected String selfHandleBean;
	/*
	 * 报表解析引擎，可同时支持多种报表引擎类型，如 JasperReport FineReport
	 */
	@FieldDefine(title = "报表解析引擎")
	@Column(name = "ENGINE_")
	@Size(max = 30)
	protected String engine;
	/* 参数配置 */
	@FieldDefine(title = "参数配置")
	@Column(name = "PARAM_CONFIG_")
	@Size(max = 65535)
	protected String paramConfig;
	/*
	 * 项目属于什么分类
	 */
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;


	@FieldDefine(title = "数据源")
	@Column(name = "DS_ALIAS_")
	protected String dsAlias;
	/**
	 * Default Empty Constructor for class SysReport
	 */
	public SysReport() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysReport
	 */
	public SysReport(String in_repId) {
		this.setRepId(in_repId);
	}

	/**
	 * 报表ID * @return String
	 */
	public String getRepId() {
		return this.repId;
	}

	public String getSelfHandleBean() {
		return selfHandleBean;
	}

	public void setSelfHandleBean(String selfHandlerBean) {
		this.selfHandleBean = selfHandlerBean;
	}

	/**
	 * 设置 报表ID
	 */
	public void setRepId(String aValue) {
		this.repId = aValue;
	}

	public SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(SysTree sysTree) {
		this.sysTree = sysTree;
	}
	/**
	 * 分类Id * @return String
	 */
	public String getTreeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 分类Id
	 */
	public void setTreeId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	

	public String getDsAlias() {
		return this.dsAlias;
	}

	/**
	 * 设置 分类Id
	 */
	public void setDsAlias(String aValue) {
		this.dsAlias=aValue;
	}

	/**
	 * 标题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 标题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 标识key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 标识key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 描述 * @return String
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * 设置 描述
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 报表模块的jasper文件的路径 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 设置 报表模块的jasper文件的路径
	 */
	public void setFilePath(String aValue) {
		this.filePath = aValue;
	}

	/**
	 * * @return String
	 */
	public String getFileId() {
		return this.fileId;
	}

	/**
	 * 设置
	 */
	public void setFileId(String aValue) {
		this.fileId = aValue;
	}

	/**
	 * 是否缺省 1=缺省 0=非缺省 * @return String
	 */
	public String getDefaults() {
		return this.defaults;
	}

	/**
	 * 设置 是否缺省 1=缺省 0=非缺省
	 */
	public void setDefaults(String aValue) {
		this.defaults = aValue;
	}

	/**
	 * 报表解析引擎，可同时支持多种报表引擎类型，如 JasperReport FineReport * @return String
	 */
	public String getEngine() {
		return this.engine;
	}

	/**
	 * 设置 报表解析引擎，可同时支持多种报表引擎类型，如 JasperReport FineReport
	 */
	public void setEngine(String aValue) {
		this.engine = aValue;
	}

	/**
	 * 参数配置 * @return String
	 */
	public String getParamConfig() {
		return this.paramConfig;
	}

	/**
	 * 设置 参数配置
	 */
	public void setParamConfig(String aValue) {
		this.paramConfig = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.repId;
	}

	@Override
	public Serializable getPkId() {
		return this.repId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.repId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysReport)) {
			return false;
		}
		SysReport rhs = (SysReport) object;
		return new EqualsBuilder().append(this.repId, rhs.repId).append(this.subject, rhs.subject)
				.append(this.key, rhs.key).append(this.descp, rhs.descp).append(this.filePath, rhs.filePath)
				.append(this.fileId, rhs.fileId).append(this.defaults, rhs.defaults).append(this.engine, rhs.engine)
				.append(this.tenantId, rhs.tenantId).append(this.paramConfig, rhs.paramConfig)
				.append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.repId).append(this.subject).append(this.key)
				.append(this.descp).append(this.filePath).append(this.fileId).append(this.defaults).append(this.engine)
				.append(this.tenantId).append(this.paramConfig).append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("repId", this.repId).append("subject", this.subject)
				.append("key", this.key).append("descp", this.descp).append("filePath", this.filePath)
				.append("fileId", this.fileId).append("defaults", this.defaults).append("engine", this.engine)
				.append("tenantId", this.tenantId).append("paramConfig", this.paramConfig)
				.append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
