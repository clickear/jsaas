package com.redxun.oa.doc.entity;

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
 * 描述：文档权限实体类定义
 * 文档或目录的权限，只要是针对公共目录下的文档，或个人的文档的共享\r\n\r\n某个目录或文档若没有指定某部
 * 作者：陈茂昌
 * 日期:2015-10-25-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_DOC_RIGHT")
@TableDefine(title = "文档或目录的权限")
public class DocRight extends BaseTenantEntity {
	
	public final static int RIGHTS_READ = 1;
	public final static int RIGHTS_EDIT = 2;
	public final static int RIGHTS_DEL = 3;
	
	public final static String IDENTITYTYPE_USER="USER";
	public final static String IDENTITYTYPE_GROUP="GROUP";	
	public final static String IDENTITYTYPE_ALL="ALL";
	
	public final static String IDENTITYID_ALL="ALL";

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "RIGHT_ID_")
	protected String rightId;
	/*
	 * 权限
	 */
	@FieldDefine(title = "权限")
	@Column(name = "RIGHTS_")
	protected Integer rights;
	/* 授权类型 */
	@FieldDefine(title = "授权类型")
	@Column(name = "IDENTITY_TYPE_")
	@Size(max = 64)
	@NotEmpty
	protected String identityType;
	/* 用户和组ID*/
	@FieldDefine(title = "用户和组ID")
	@Column(name = "IDENTITY_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String identityId;
	/*文档文件夹*/
	@FieldDefine(title = "文档文件夹")
	@ManyToOne
	@JoinColumn(name = "FOLDER_ID_")
	protected DocFolder docFolder;
	/*文档文件*/
	@FieldDefine(title = "文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected Doc doc;

	/**
	 * Default Empty Constructor for class DocRight
	 */
	public DocRight() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class DocRight
	 */
	public DocRight(String in_rightId) {
		this.setRightId(in_rightId);
	}

	public DocFolder getDocFolder() {
		return docFolder;
	}

	public void setDocFolder(DocFolder in_docFolder) {
		this.docFolder = in_docFolder;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc in_doc) {
		this.doc = in_doc;
	}

	
	
	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
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
	 * 文档ID * @return String
	 */
	public String getDocId() {
		return this.getDoc() == null ? null : this.getDoc().getDocId();
	}

	/**
	 * 设置 文档ID
	 */
	public void setDocId(String aValue) {
		if (aValue == null) {
			doc = null;
		} else if (doc == null) {
			doc = new Doc(aValue);
		} else {
			doc.setDocId(aValue);
		}
	}

	/**
	 * 文件夹ID * @return String
	 */
	public String getFolderId() {
		return this.getDocFolder() == null ? null : this.getDocFolder()
				.getFolderId();
	}

	/**
	 * 设置 文件夹ID
	 */
	public void setFolderId(String aValue) {
		if (aValue == null) {
			docFolder = null;
		} else if (docFolder == null) {
			docFolder = new DocFolder(aValue);
		} else {
			docFolder.setFolderId(aValue);
		}
	}

	/**
	 * 权限 文档或目录的读写修改权限 1=读 2=修改 4=删除
	 * 
	 * 权限值可以为上面的值之和 如：3则代表进行读，修改的操作
	 * 
	 * 
	 * @return Integer
	 */
	public Integer getRights() {
		return this.rights;
	}

	/**
	 * 设置 权限 文档或目录的读写修改权限 1=读 2=修改 4=删除
	 * 
	 * 权限值可以为上面的值之和 如：3则代表进行读，修改的操作
	 */
	public void setRights(Integer aValue) {
		this.rights = aValue;
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
		if (!(object instanceof DocRight)) {
			return false;
		}
		DocRight rhs = (DocRight) object;
		return new EqualsBuilder().append(this.rightId, rhs.rightId)
				.append(this.rights, rhs.rights)
				.append(this.identityType, rhs.identityType)
				.append(this.identityId, rhs.identityId)
				.append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy)
				.append(this.tenantId, rhs.tenantId)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rightId)
				.append(this.rights).append(this.identityType).append(this.identityId)
				.append(this.createTime).append(this.createBy)
				.append(this.tenantId).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rightId", this.rightId)
				.append("rights", this.rights).append("identityType", this.identityType)
				.append("identityId", this.identityId)
				.append("createTime", this.createTime)
				.append("createBy", this.createBy)
				.append("tenantId", this.tenantId)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
