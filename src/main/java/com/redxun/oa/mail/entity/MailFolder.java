package com.redxun.oa.mail.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：MailFolder实体类定义
 * 邮件文件夹
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_MAIL_FOLDER")
@TableDefine(title = "邮件文件夹")
@JsonIgnoreProperties(value={"mails","mailFolders"})
public class MailFolder extends BaseTenantEntity {

	public final static String TYPE_ROOT_FOLDER="ROOT-FOLDER";
	public final static String TYPE_RECEIVE_FOLDER="RECEIVE-FOLDER";
	public final static String TYPE_SENDER_FOLDER="SENDER-FOLDER";
	public final static String TYPE_DRAFT_FOLDER="DRAFT-FOLDER";
	public final static String TYPE_DEL_FOLDER="DEL-FOLDER";
	public final static String TYPE_OTHER_FOLDER="OTHER-FOLDER";
	public final static String FOLDER_FLAG_IN="IN";
	public final static String FOLDER_FLAG_OUT="OUT";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "FOLDER_ID_")
	protected String folderId;
	/* 主键 */
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String userId;
	/* 文件夹名称 */
	@FieldDefine(title = "文件夹名称")
	@Column(name = "NAME_")
	@Size(max = 128)
	@NotEmpty
	protected String name;
	/* 父目录 */
	@FieldDefine(title = "父目录")
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	/* 目录层 */
	@FieldDefine(title = "目录层")
	@Column(name = "DEPTH_")
	protected Integer depth;
	/* 目录路径 */
	@FieldDefine(title = "目录路径")
	@Column(name = "PATH_")
	@Size(max = 256)
	protected String path;
	/*内部外部邮箱标识*/
	@FieldDefine(title = "内部外部邮箱标识")
	@Column(name = "IN_OUT_")
	@Size(max = 20)
	protected String inOut;
	
    /*文件夹别名*/
	@Transient
	private String alias;
	/*
	 * 文件夹类型 RECEIVE-FOLDER=收信箱 SENDER-FOLDEr=发信箱 DRAFT-FOLDER=草稿箱
	 * DEL-FOLDER=删除箱 OTHER-FOLDER=其他
	 */
	@FieldDefine(title = "文件夹类型")
	@Column(name = "TYPE_")
	@Size(max = 32)
	@NotEmpty
	protected String type;
	@FieldDefine(title = "外部邮箱设置")
	@ManyToOne
	@JoinColumn(name = "CONFIG_ID_")
	protected com.redxun.oa.mail.entity.MailConfig mailConfig;

	/**
	 * Default Empty Constructor for class MailFolder
	 */
	public MailFolder() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class MailFolder
	 */
	public MailFolder(String in_folderId) {
		this.setFolderId(in_folderId);
	}

	public com.redxun.oa.mail.entity.MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(
			com.redxun.oa.mail.entity.MailConfig in_mailConfig) {
		this.mailConfig = in_mailConfig;
	}
	

	/**
	 * 内外部邮箱标识 * @return String
	 */
	public String getInOut() {
		return this.inOut;
	}

	/**
	 * 设置 内外部邮箱标识
	 */
	public void setInOut(String aValue) {
		this.inOut = aValue;
	}
	
	
	/**
	 * 文件夹别名 * @return String
	 */
	public String getAlias() {
		return this.alias;
	}
	/**
	 * 设置 文件夹别名
	 */
	public void setAlias(String aValue) {
		this.alias = aValue;
	}

	/**
	 * 文件夹编号 * @return String
	 */
	public String getFolderId() {
		return this.folderId;
	}

	/**
	 * 设置 文件夹编号
	 */
	public void setFolderId(String aValue) {
		this.folderId = aValue;
	}

	/**
	 * 配置ID * @return String
	 */
	public String getConfigId() {
		return this.getMailConfig() == null ? null : this.getMailConfig()
				.getConfigId();
	}

	/**
	 * 设置 配置ID
	 */
	public void setConfigId(String aValue) {
		if (aValue == null) {
			mailConfig = null;
		} else if (mailConfig == null) {
			mailConfig = new com.redxun.oa.mail.entity.MailConfig(aValue);
		} else {
			mailConfig.setConfigId(aValue);
		}
	}

	/**
	 * 主键 * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 主键
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	/**
	 * 文件夹名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 文件夹名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 父目录 * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父目录
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
	}

	/**
	 * 目录层 * @return Integer
	 */
	public Integer getDepth() {
		return this.depth;
	}

	/**
	 * 设置 目录层
	 */
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}

	/**
	 * 目录路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 设置 目录路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}

	/**
	 * 文件夹类型 RECEIVE-FOLDER=收信箱 SENDER-FOLDEr=发信箱 DRAFT-FOLDER=草稿箱
	 * DEL-FOLDER=删除箱 OTHER-FOLDER=其他 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 文件夹类型 RECEIVE-FOLDER=收信箱 SENDER-FOLDEr=发信箱 DRAFT-FOLDER=草稿箱
	 * DEL-FOLDER=删除箱 OTHER-FOLDER=其他
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.folderId;
	}

	@Override
	public Serializable getPkId() {
		return this.folderId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.folderId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof MailFolder)) {
			return false;
		}
		MailFolder rhs = (MailFolder) object;
		return new EqualsBuilder().append(this.folderId, rhs.folderId)
				.append(this.userId, rhs.userId).append(this.name, rhs.name)
				.append(this.parentId, rhs.parentId)
				.append(this.depth, rhs.depth).append(this.path, rhs.path)
				.append(this.type, rhs.type)
				.append(this.updateTime, rhs.updateTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy)
				.append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.folderId)
				.append(this.userId).append(this.name).append(this.parentId)
				.append(this.depth).append(this.path).append(this.type)
				.append(this.updateTime).append(this.updateBy)
				.append(this.createTime).append(this.createBy)
				.append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("folderId", this.folderId)
				.append("userId", this.userId).append("name", this.name)
				.append("parentId", this.parentId).append("depth", this.depth)
				.append("path", this.path).append("type", this.type)
				.append("updateTime", this.updateTime)
				.append("updateBy", this.updateBy)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("tenantId", this.tenantId).toString();
	}

}
