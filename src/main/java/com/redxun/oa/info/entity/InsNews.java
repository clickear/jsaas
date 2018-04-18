package com.redxun.oa.info.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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

/**
 * <pre>
 * 描述：InsNews实体类定义
 * 信息公告
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_NEWS")
@TableDefine(title = "信息公告")
@JsonIgnoreProperties({"insColNews","insNewsCms"})
public class InsNews extends BaseTenantEntity {
	/**
	 * 草稿状态=Draft
	 */
	public final static String STATUS_DRAFT="Draft";
	/**
	 * 发布状态=Issued
	 */
	public final static String STATUS_ISSUED="Issued";
	/**
	 * 归档状态=Archived
	 */
	public final static String STATUS_ARCHIVED="Archived";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "NEW_ID_")
	protected String newId;
	/* 标题 */
	@FieldDefine(title = "标题")
	@Column(name = "SUBJECT_")
	@Size(max = 120)
	@NotEmpty
	protected String subject;
	/* 标签 */
	@FieldDefine(title = "标签")
	@Column(name = "TAG_")
	@Size(max = 120)
	protected String tag;
	/* 栏目id */
	@FieldDefine(title = "栏目id")
	@Column(name = "COLUMN_ID_")
	@Size(max = 120)
	protected String columnId;
	/* 关键字 */
	@FieldDefine(title = "关键字")
	@Column(name = "KEYWORDS_")
	@Size(max = 120)
	protected String keywords;
	/* 内容 */
	@FieldDefine(title = "内容")
	@Column(name = "CONTENT_")
	@Size(max = 2147483647)
	protected String content;
	/**/
	@FieldDefine(title = "图片文件ID")
	@Column(name = "IMG_FILE_ID_")
	@Size(max = 64)
	protected String imgFileId;
	/* 是否允许评论 */
	@FieldDefine(title = "是否允许评论")
	@Column(name = "ALLOW_CMT_")
	@Size(max = 20)
	protected String allowCmt;
	/* 阅读次数 */
	@FieldDefine(title = "阅读次数")
	@Column(name = "READ_TIMES_")
	protected Integer readTimes;
	/* 作者 */
	@FieldDefine(title = "作者")
	@Column(name = "AUTHOR_")
	@Size(max = 50)
	protected String author;
	/* 状态 */
	@FieldDefine(title = "状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	
	@FieldDefine(title = "是否为图片新闻")
	@Column(name = "IS_IMG_")
	@Size(max = 20)
	protected String isImg;
	
	@FieldDefine(title = "附件")
	@Column(name = "FILES_")
	@Size(max = 512)
	protected String files;
	
	
	
	

	/**
	 * Default Empty Constructor for class InsNews
	 */
	public InsNews() {
		super();
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * Default Key Fields Constructor for class InsNews
	 */
	public InsNews(String in_newId) {
		this.setNewId(in_newId);
	}

	

	/**
	 * * @return String
	 */
	public String getNewId() {
		return this.newId;
	}

	/**
	 * 设置
	 */
	public void setNewId(String aValue) {
		this.newId = aValue;
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
	 * * @return String
	 */
	public String getImgFileId() {
		return this.imgFileId;
	}

	/**
	 * 设置
	 */
	public void setImgFileId(String aValue) {
		this.imgFileId = aValue;
	}

	/**
	 * 阅读次数 * @return Integer
	 */
	public Integer getReadTimes() {
		return this.readTimes;
	}

	/**
	 * 设置 阅读次数
	 */
	public void setReadTimes(Integer aValue) {
		this.readTimes = aValue;
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
	
	public String getAllowCmt() {
		return allowCmt;
	}

	public void setAllowCmt(String allowCmt) {
		this.allowCmt = allowCmt;
	}

	/**
	 * 状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.newId;
	}

	@Override
	public Serializable getPkId() {
		return this.newId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.newId = (String) pkId;
	}

	public String getIsImg() {
		return isImg;
	}

	public void setIsImg(String isImg) {
		this.isImg = isImg;
	}	

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsNews)) {
			return false;
		}
		InsNews rhs = (InsNews) object;
		return new EqualsBuilder().append(this.newId, rhs.newId).append(this.columnId, rhs.columnId).append(this.subject, rhs.subject).append(this.content, rhs.content).append(this.imgFileId, rhs.imgFileId).append(this.readTimes, rhs.readTimes).append(this.author, rhs.author).append(this.status, rhs.status).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.newId).append(this.subject).append(this.content).append(this.imgFileId).append(this.readTimes).append(this.author).append(this.status).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("newId", this.newId).append("subject", this.subject).append("columnId", this.columnId).append("content", this.content).append("imgFileId", this.imgFileId).append("readTimes", this.readTimes).append("author", this.author).append("status", this.status).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
