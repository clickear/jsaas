package com.redxun.oa.doc.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.sys.core.entity.SysFile;

/**
 * <pre>
 * 描述：文档实体类定义
 * 文档
 * 作者：陈茂昌
 * 日期:2015-11-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INF_DOC")
@TableDefine(title = "文档")
@JsonIgnoreProperties(value={"infDocFiles","docRights"})
public class Doc extends BaseTenantEntity {
	/**
	 * 文档类型-HTML文档=html
	 */
	public final static String DOC_HTML="html";
	/**
	 * 文档类型-Word2003文档=doc
	 */
	public final static String DOC_WORD2003="doc";
	/**
	 * 文档类型-Word2007文档=docx
	 */
	public final static String DOC_WORD2007="docx";
	/**
	 * 文档类型-PPT2003文档=ppt
	 */
	public final static String DOC_PPT2003="ppt";
	/**
	 * 文档类型-PPT2007文档=pptx
	 */
	public final static String DOC_PPT2007="pptx";
	/**
	 * 文档类型-EXCEL2003文档=xls
	 */
	public final static String DOC_EXCEL2003="xls";
	/**
	 * 文档类型-EXCEL2003文档=xls
	 */
	public final static String DOC_EXCEL2007="xlsx";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DOC_ID_")
	protected String docId;
	/* 文档名称 */
	@FieldDefine(title = "文档名称")
	@Column(name = "NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String name;
	/* 内容 */
	@FieldDefine(title = "内容")
	@Column(name = "CONTENT_")
	@Size(max = 2147483647)
	protected String content;
	/* 摘要 */
	@FieldDefine(title = "摘要")
	@Column(name = "SUMMARY_")
	@Size(max = 512)
	protected String summary;
	/* 是否包括附件 */
	@FieldDefine(title = "是否包括附件")
	@Column(name = "HAS_ATTACH_")
	@Size(max = 8)
	protected String hasAttach;
	/* 是否共享 */
	@FieldDefine(title = "是否共享")
	@Column(name = "IS_SHARE_")
	@Size(max = 8)
	@NotEmpty
	protected String isShare;
	/* 作者 */
	@FieldDefine(title = "作者")
	@Column(name = "AUTHOR_")
	@Size(max = 64)
	protected String author;
	/* 关键字 */
	@FieldDefine(title = "关键字")
	@Column(name = "KEYWORDS_")
	@Size(max = 256)
	protected String keywords;
	/* 文档类型 */
	@FieldDefine(title = "文档类型")
	@Column(name = "DOC_TYPE_")
	@Size(max = 64)
	protected String docType;
	/* SWF文件f路径 */
	@FieldDefine(title = "SWF文件路径")
	@Column(name = "SWF_PATH_")
	@Size(max = 256)
	protected String swfPath;
	
	/* SWF文件f路径 */
	@FieldDefine(title = "文档文件路径")
	@Column(name = "DOC_PATH_")
	@Size(max = 256)
	protected String docPath;
	
	/* 用户ID */
	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	protected String userId;
	@FieldDefine(title = "文档文件夹")
	@ManyToOne
	@JoinColumn(name = "FOLDER_ID_")
	protected DocFolder docFolder;

	
	@FieldDefine(title = "附件列表")
	@ManyToMany(cascade={CascadeType.PERSIST},fetch = FetchType.LAZY)     
	@JoinTable(name="INF_DOC_FILE",
				joinColumns={ @JoinColumn(name="DOC_ID_",referencedColumnName="DOC_ID_")    },    
				inverseJoinColumns={ @JoinColumn(name="FILE_ID_",referencedColumnName="FILE_ID_") })  
    protected java.util.Set<SysFile> infDocFiles = new java.util.HashSet<SysFile>();
	
	@FieldDefine(title = "文档或目录的权限")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "doc", fetch = FetchType.LAZY)
	protected java.util.Set<DocRight> docRights = new java.util.HashSet<DocRight>();

	/**
	 * Default Empty Constructor for class Doc
	 */
	public Doc() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Doc
	 */
	public Doc(String in_docId) {
		this.setDocId(in_docId);
	}

	public DocFolder getDocFolder() {
		return docFolder;
	}

	public void setDocFolder(DocFolder in_docFolder) {
		this.docFolder = in_docFolder;
	}

	public java.util.Set<DocRight> getDocRights() {
		return docRights;
	}

	public void setDocRights(java.util.Set<DocRight> in_docRights) {
		this.docRights = in_docRights;
	}

	/**
	 * 文档ID * @return String
	 */
	public String getDocId() {
		return this.docId;
	}

	public java.util.Set<SysFile> getInfDocFiles() {
		return infDocFiles;
	}

	public void setInfDocFiles(java.util.Set<SysFile> infDocFiles) {
		this.infDocFiles = infDocFiles;
	}

	/**
	 * 设置 文档ID
	 */
	public void setDocId(String aValue) {
		this.docId = aValue;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
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
	 * 文档名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 文档名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 摘要 * @return String
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * 设置 摘要
	 */
	public void setSummary(String aValue) {
		this.summary = aValue;
	}

	/**
	 * 是否包括附件 * @return String
	 */
	public String getHasAttach() {
		return this.hasAttach;
	}

	/**
	 * 设置 是否包括附件
	 */
	public void setHasAttach(String aValue) {
		this.hasAttach = aValue;
	}

	/**
	 * 是否共享 * @return String
	 */
	public String getIsShare() {
		return this.isShare;
	}

	/**
	 * 设置 是否共享
	 */
	public void setIsShare(String aValue) {
		this.isShare = aValue;
	}

	/**
	 * 作者 * @return String
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * 设置 作者
	 */
	public void setAuthor(String aValue) {
		this.author = aValue;
	}

	/**
	 * 关键字 * @return String
	 */
	public String getKeywords() {
		return this.keywords;
	}

	/**
	 * 设置 关键字
	 */
	public void setKeywords(String aValue) {
		this.keywords = aValue;
	}

	/**
	 * 文档类型 * @return String
	 */
	public String getDocType() {
		return this.docType;
	}

	/**
	 * 设置 文档类型
	 */
	public void setDocType(String aValue) {
		this.docType = aValue;
	}

	/**
	 * SWF文件f路径 * @return String
	 */
	public String getSwfPath() {
		return this.swfPath;
	}

	/**
	 * 设置 SWF文件f路径
	 */
	public void setSwfPath(String aValue) {
		this.swfPath = aValue;
	}

	/**
	 * 用户ID * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 用户ID
	 */
	public void setUserId(String aValue) {
		this.userId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.docId;
	}

	@Override
	public Serializable getPkId() {
		return this.docId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.docId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Doc)) {
			return false;
		}
		Doc rhs = (Doc) object;
		return new EqualsBuilder().append(this.docId, rhs.docId)
				.append(this.name, rhs.name).append(this.content, rhs.content)
				.append(this.summary, rhs.summary)
				.append(this.hasAttach, rhs.hasAttach)
				.append(this.isShare, rhs.isShare)
				.append(this.author, rhs.author)
				.append(this.keywords, rhs.keywords)
				.append(this.docType, rhs.docType)
				.append(this.swfPath, rhs.swfPath)
				.append(this.userId, rhs.userId)
				.append(this.tenantId, rhs.tenantId)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.docId)
				.append(this.name).append(this.content).append(this.summary)
				.append(this.hasAttach).append(this.isShare)
				.append(this.author).append(this.keywords).append(this.docType)
				.append(this.swfPath).append(this.userId).append(this.tenantId)
				.append(this.createBy).append(this.createTime)
				.append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("docId", this.docId)
				.append("name", this.name).append("content", this.content)
				.append("summary", this.summary)
				.append("hasAttach", this.hasAttach)
				.append("isShare", this.isShare).append("author", this.author)
				.append("keywords", this.keywords)
				.append("docType", this.docType)
				.append("swfPath", this.swfPath).append("userId", this.userId)
				.append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

}
