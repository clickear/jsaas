package com.redxun.kms.core.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;

/**
 * <pre>
 * 描述：KdDoc实体类定义
 * 知识文档、地图、词条
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2016-3-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC")
@TableDefine(title = "知识文档、地图、词条")
@JsonIgnoreProperties(value={"kdDocCmmts","kdDocRights","kdDocDirs","kdDocFavs","kdDocHiss","kdDocRounds","kdDocContrs","kdDocReads","kdDocRems"})
public class KdDoc extends BaseTenantEntity {
	
	/**
	 * 草稿
	 */
	public final static String STATUS_DRAFT="DRAFT";
	/**
	 * 发布
	 */
	public final static String STATUS_ISSUED="ISSUED";
	/**
	 * 过期
	 */
	public final static String STATUS_OVERDUE="OVERDUE";
	/**
	 * 归档
	 */
	public final static String STATUS_ARCHIVED="ARCHIVED";
	/**
	 * 废弃
	 */
	public final static String STATUS_ABANDON="ABANDON";
	/**
	 * 驳回
	 */
	public final static String STATUS_BACK="BACK";
	/**
	 * 待审
	 */
	public final static String STATUS_PENDING="PENDING";
	

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DOC_ID_")
	protected String docId;
	/* 文档标题 */
	@FieldDefine(title = "文档标题")
	@Column(name = "SUBJECT_")
	@Size(max = 128)
	@NotEmpty
	protected String subject;
	/* 是否精华 */
	@FieldDefine(title = "是否精华")
	@Column(name = "IS_ESSENCE_")
	@Size(max = 20)
	protected String isEssence;
	/* 作者 */
	@FieldDefine(title = "作者")
	@Column(name = "AUTHOR_")
	@Size(max = 64)
	@NotEmpty
	protected String author;
	/*
	 * 作者类型
	 */
	@FieldDefine(title = "作者类型 ")
	@Column(name = "AUTHOR_TYPE_")
	@Size(max = 20)
	protected String authorType;
	/* 所属岗位 */
	@FieldDefine(title = "所属岗位")
	@Column(name = "AUTHOR_POS_")
	@Size(max = 64)
	protected String authorPos;
	/* 所属部门ID */
	@FieldDefine(title = "所属部门ID")
	@Column(name = "BELONG_DEPID_")
	@Size(max = 64)
	protected String belongDepid;
	/* 关键字 */
	@FieldDefine(title = "关键字")
	@Column(name = "KEYWORDS_")
	@Size(max = 128)
	protected String keywords;
	/* 审批人ID */
	@FieldDefine(title = "审批人ID")
	@Column(name = "APPROVAL_ID_")
	@Size(max = 64)
	protected String approvalId;
	/* 发布日期 */
	@FieldDefine(title = "发布日期")
	@Column(name = "ISSUED_TIME_")
	protected java.util.Date issuedTime;
	/* 浏览次数 */
	@FieldDefine(title = "浏览次数")
	@Column(name = "VIEW_TIMES_")
	protected Integer viewTimes;
	/* 摘要 */
	@FieldDefine(title = "摘要")
	@Column(name = "SUMMARY_")
	@Size(max = 512)
	protected String summary;
	/* 知识内容 */
	@FieldDefine(title = "知识内容")
	@Column(name = "CONTENT_")
	@Size(max = 2147483647)
	protected String content;
	/* 综合评分 */
	@FieldDefine(title = "综合评分")
	@Column(name = "COMP_SCORE_")
	protected double compScore;
	/* 标签 */
	@FieldDefine(title = "标签")
	@Column(name = "TAGS")
	@Size(max = 200)
	protected String tags;
	/*
	 * 存放年限
	 */
	@FieldDefine(title = "存放年限")
	@Column(name = "STORE_PEROID_")
	protected Integer storePeroid;
	/* 封面图 */
	@FieldDefine(title = "封面图")
	@Column(name = "COVER_IMG_ID_")
	@Size(max = 64)
	protected String coverImgId;
	/* 流程实例ID */
	@FieldDefine(title = "流程实例ID")
	@Column(name = "BPM_INST_ID_")
	@Size(max = 64)
	protected String bpmInstId;
	/* 文档附件 */
	@FieldDefine(title = "文档附件")
	@Column(name = "ATT_FILEIDS_")
	@Size(max = 512)
	protected String attFileids;
	/*
	 * 归档分类
	 */
	@FieldDefine(title = "归档分类")
	@Column(name = "ARCH_CLASS_")
	@Size(max = 20)
	protected String archClass;
	/*
	 * 文档状态
	 */
	@FieldDefine(title = "文档状态 ")
	@Column(name = "STATUS_")
	@Size(max = 20)
	@NotEmpty
	protected String status;
	/*
	 * 文档类型
	 */
	@FieldDefine(title = "文档类型")
	@Column(name = "DOC_TYPE_")
	@Size(max = 20)
	protected String docType;
	/*版本号*/
	@FieldDefine(title = "版本号")
	@Column(name = "VERSION_")
	protected Integer version;
	/* 知识地图锚点信息 */
	@FieldDefine(title = "知识地图锚点信息")
	@Column(name = "IMG_MAPS_")
	@Size(max = 65535)
	protected String imgMaps;
	@FieldDefine(title = "文档模板")
	@ManyToOne
	@JoinColumn(name = "TEMP_ID_")
	protected com.redxun.kms.core.entity.KdDocTemplate kdDocTemplate;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected com.redxun.sys.core.entity.SysTree sysTree;
	
	//用来判断是否设为首页推荐
	@Transient
	private String isPortalPic;

	@FieldDefine(title = "文档索引目录")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocDir> kdDocDirs = new java.util.HashSet<KdDocDir>();
	@FieldDefine(title = "文档收藏")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocFav> kdDocFavs = new java.util.HashSet<KdDocFav>();
	@FieldDefine(title = "知识文档历史版本")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocHis> kdDocHiss = new java.util.HashSet<KdDocHis>();
	@FieldDefine(title = "文档权限")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocRight> kdDocRights = new java.util.HashSet<KdDocRight>();
	@FieldDefine(title = "文档传阅")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocRound> kdDocRounds = new java.util.HashSet<KdDocRound>();
	@FieldDefine(title = "文档点评")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocCmmt> kdDocCmmts = new java.util.HashSet<KdDocCmmt>();
	@FieldDefine(title = "知识文档贡献者")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocContr> kdDocContrs = new java.util.HashSet<KdDocContr>();
	@FieldDefine(title = "文档阅读")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocRead> kdDocReads = new java.util.HashSet<KdDocRead>();
	@FieldDefine(title = "文档推荐")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdDoc", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocRem> kdDocRems = new java.util.HashSet<KdDocRem>();


	/**
	 * Default Empty Constructor for class KdDoc
	 */
	public KdDoc() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDoc
	 */
	public KdDoc(String in_docId) {
		this.setDocId(in_docId);
	}
	
	
	public String getIsPortalPic() {
		return isPortalPic;
	}

	public void setIsPortalPic(String isPortalPic) {
		this.isPortalPic = isPortalPic;
	}

	public com.redxun.kms.core.entity.KdDocTemplate getKdDocTemplate() {
		return kdDocTemplate;
	}

	public void setKdDocTemplate(com.redxun.kms.core.entity.KdDocTemplate in_kdDocTemplate) {
		this.kdDocTemplate = in_kdDocTemplate;
	}

	public com.redxun.sys.core.entity.SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(com.redxun.sys.core.entity.SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	public java.util.Set<KdDocDir> getKdDocDirs() {
		return kdDocDirs;
	}

	public void setKdDocDirs(java.util.Set<KdDocDir> in_kdDocDirs) {
		this.kdDocDirs = in_kdDocDirs;
	}

	public java.util.Set<KdDocFav> getKdDocFavs() {
		return kdDocFavs;
	}

	public void setKdDocFavs(java.util.Set<KdDocFav> in_kdDocFavs) {
		this.kdDocFavs = in_kdDocFavs;
	}

	public java.util.Set<KdDocHis> getKdDocHiss() {
		return kdDocHiss;
	}

	public void setKdDocHiss(java.util.Set<KdDocHis> in_kdDocHiss) {
		this.kdDocHiss = in_kdDocHiss;
	}

	public java.util.Set<KdDocRight> getKdDocRights() {
		return kdDocRights;
	}

	public void setKdDocRights(java.util.Set<KdDocRight> in_kdDocRights) {
		this.kdDocRights = in_kdDocRights;
	}

	public java.util.Set<KdDocRound> getKdDocRounds() {
		return kdDocRounds;
	}

	public void setKdDocRounds(java.util.Set<KdDocRound> in_kdDocRounds) {
		this.kdDocRounds = in_kdDocRounds;
	}
	public java.util.Set<KdDocCmmt> getKdDocCmmts() {
		return kdDocCmmts;
	}

	public void setKdDocCmmts(java.util.Set<KdDocCmmt> kdDocCmmts) {
		this.kdDocCmmts = kdDocCmmts;
	}

	public java.util.Set<KdDocContr> getKdDocContrs() {
		return kdDocContrs;
	}

	public void setKdDocContrs(java.util.Set<KdDocContr> kdDocContrs) {
		this.kdDocContrs = kdDocContrs;
	}

	public java.util.Set<KdDocRead> getKdDocReads() {
		return kdDocReads;
	}

	public void setKdDocReads(java.util.Set<KdDocRead> kdDocReads) {
		this.kdDocReads = kdDocReads;
	}

	public java.util.Set<KdDocRem> getKdDocRems() {
		return kdDocRems;
	}

	public void setKdDocRems(java.util.Set<KdDocRem> kdDocRems) {
		this.kdDocRems = kdDocRems;
	}
	
	

	public String getImgMaps() {
		return imgMaps;
	}

	public void setImgMaps(String imgMaps) {
		this.imgMaps = imgMaps;
	}

	/**
	 * * @return String
	 */
	public String getDocId() {
		return this.docId;
	}

	/**
	 * 设置
	 */
	public void setDocId(String aValue) {
		this.docId = aValue;
	}

	/**
	 * 所属分类 * @return String
	 */
	public String getTreeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 所属分类
	 */
	public void setTreeId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new com.redxun.sys.core.entity.SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 文档标题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 文档标题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 词条或知识模板ID * @return String
	 */
	public String getTempId() {
		return this.getKdDocTemplate() == null ? null : this.getKdDocTemplate().getTempId();
	}

	/**
	 * 设置 词条或知识模板ID
	 */
	public void setTempId(String aValue) {
		if (aValue == null) {
			kdDocTemplate = null;
		} else if (kdDocTemplate == null) {
			kdDocTemplate = new com.redxun.kms.core.entity.KdDocTemplate(aValue);
		} else {
			kdDocTemplate.setTempId(aValue);
		}
	}

	/**
	 * 是否精华 * @return String
	 */
	public String getIsEssence() {
		return this.isEssence;
	}

	/**
	 * 设置 是否精华
	 */
	public void setIsEssence(String aValue) {
		this.isEssence = aValue;
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
	 * 作者类型 内部=INNER 外部=OUTER * @return String
	 */
	public String getAuthorType() {
		return this.authorType;
	}

	/**
	 * 设置 作者类型 内部=INNER 外部=OUTER
	 */
	public void setAuthorType(String aValue) {
		this.authorType = aValue;
	}

	/**
	 * 所属岗位 * @return String
	 */
	public String getAuthorPos() {
		return this.authorPos;
	}

	/**
	 * 设置 所属岗位
	 */
	public void setAuthorPos(String aValue) {
		this.authorPos = aValue;
	}

	/**
	 * 所属部门ID * @return String
	 */
	public String getBelongDepid() {
		return this.belongDepid;
	}

	/**
	 * 设置 所属部门ID
	 */
	public void setBelongDepid(String aValue) {
		this.belongDepid = aValue;
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
	 * 审批人ID * @return String
	 */
	public String getApprovalId() {
		return this.approvalId;
	}

	/**
	 * 设置 审批人ID
	 */
	public void setApprovalId(String aValue) {
		this.approvalId = aValue;
	}

	/**
	 * 发布日期 * @return java.util.Date
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public java.util.Date getIssuedTime() {
		return this.issuedTime;
	}

	/**
	 * 设置 发布日期
	 */
	public void setIssuedTime(java.util.Date aValue) {
		this.issuedTime = aValue;
	}

	/**
	 * 浏览次数 * @return Integer
	 */
	public Integer getViewTimes() {
		return this.viewTimes;
	}

	/**
	 * 设置 浏览次数
	 */
	public void setViewTimes(Integer aValue) {
		this.viewTimes = aValue;
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
	 * 知识内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 知识内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 综合评分 * @return Integer
	 */
	public double getCompScore() {
		return this.compScore;
	}

	/**
	 * 设置 综合评分
	 */
	public void setCompScore(double aValue) {
		this.compScore = aValue;
	}

	/**
	 * 标签 * @return String
	 */
	public String getTags() {
		return this.tags;
	}

	/**
	 * 设置 标签
	 */
	public void setTags(String aValue) {
		this.tags = aValue;
	}

	/**
	 * 存放年限 单位为年 * @return Integer
	 */
	public Integer getStorePeroid() {
		return this.storePeroid;
	}

	/**
	 * 设置 存放年限 单位为年
	 */
	public void setStorePeroid(Integer aValue) {
		this.storePeroid = aValue;
	}

	/**
	 * 封面图 * @return String
	 */
	public String getCoverImgId() {
		return this.coverImgId;
	}

	/**
	 * 设置 封面图
	 */
	public void setCoverImgId(String aValue) {
		this.coverImgId = aValue;
	}

	/**
	 * 流程实例ID * @return String
	 */
	public String getBpmInstId() {
		return this.bpmInstId;
	}

	/**
	 * 设置 流程实例ID
	 */
	public void setBpmInstId(String aValue) {
		this.bpmInstId = aValue;
	}

	/**
	 * 文档附件 * @return String
	 */
	public String getAttFileids() {
		return this.attFileids;
	}

	/**
	 * 设置 文档附件
	 */
	public void setAttFileids(String aValue) {
		this.attFileids = aValue;
	}

	/**
	 * 归档分类 知识文档 知识地图 词条 * @return String
	 */
	public String getArchClass() {
		return this.archClass;
	}

	/**
	 * 设置 归档分类 知识文档 知识地图 词条
	 */
	public void setArchClass(String aValue) {
		this.archClass = aValue;
	}

	/**
	 * 文档状态 废弃=abandon 草稿=draft 驳回=back 待审=pending 发布=issued 过期=overdue
	 * 归档=archived * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 文档状态 废弃=abandon 草稿=draft 驳回=back 待审=pending 发布=issued 过期=overdue
	 * 归档=archived
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 知识文档=KD_DOC 词条=KD_WORD 知识地图=KD_MAP * @return String
	 */
	public String getDocType() {
		return this.docType;
	}

	/**
	 * 设置 知识文档=KD_DOC 词条=KD_WORD 知识地图=KD_MAP
	 */
	public void setDocType(String aValue) {
		this.docType = aValue;
	}

	/**
	 * * @return Integer
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * 设置
	 */
	public void setVersion(Integer aValue) {
		this.version = aValue;
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
		if (!(object instanceof KdDoc)) {
			return false;
		}
		KdDoc rhs = (KdDoc) object;
		return new EqualsBuilder().append(this.docId, rhs.docId).append(this.subject, rhs.subject).append(this.isEssence, rhs.isEssence).append(this.author, rhs.author).append(this.authorType, rhs.authorType).append(this.authorPos, rhs.authorPos).append(this.belongDepid, rhs.belongDepid).append(this.keywords, rhs.keywords).append(this.approvalId, rhs.approvalId).append(this.issuedTime, rhs.issuedTime).append(this.viewTimes, rhs.viewTimes).append(this.summary, rhs.summary).append(this.content, rhs.content).append(this.compScore, rhs.compScore).append(this.tags, rhs.tags).append(this.storePeroid, rhs.storePeroid).append(this.coverImgId, rhs.coverImgId).append(this.bpmInstId, rhs.bpmInstId).append(this.attFileids, rhs.attFileids).append(this.archClass, rhs.archClass)
				.append(this.status, rhs.status).append(this.docType, rhs.docType).append(this.version, rhs.version).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.docId).append(this.subject).append(this.isEssence).append(this.author).append(this.authorType).append(this.authorPos).append(this.belongDepid).append(this.keywords).append(this.approvalId).append(this.issuedTime).append(this.viewTimes).append(this.summary).append(this.content).append(this.compScore).append(this.tags).append(this.storePeroid).append(this.coverImgId).append(this.bpmInstId).append(this.attFileids).append(this.archClass).append(this.status).append(this.docType).append(this.version).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("docId", this.docId).append("subject", this.subject).append("isEssence", this.isEssence).append("author", this.author).append("authorType", this.authorType).append("authorPos", this.authorPos).append("belongDepid", this.belongDepid).append("keywords", this.keywords).append("approvalId", this.approvalId).append("issuedTime", this.issuedTime).append("viewTimes", this.viewTimes).append("summary", this.summary).append("content", this.content).append("compScore", this.compScore).append("tags", this.tags).append("storePeroid", this.storePeroid).append("coverImgId", this.coverImgId).append("bpmInstId", this.bpmInstId).append("attFileids", this.attFileids).append("archClass", this.archClass).append("status", this.status)
				.append("docType", this.docType).append("version", this.version).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).append("tenantId", this.tenantId).toString();
	}

}
