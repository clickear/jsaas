package com.redxun.offdoc.core.entity;

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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.json.JsonDateSerializer;
import com.redxun.core.xstream.convert.DateConverter;
import com.redxun.sys.core.entity.SysTree;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * <pre>
 * 描述：OdDocument实体类定义
 * TODO: add class/table comments
 * 作者：陈茂昌
 * 日期:2016-3-8-上午16:00:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OD_DOCUMENT")
@TableDefine(title = "文档")
public class OdDocument extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "DOC_ID_")
	protected String docId;
	/* 发文字号 */
	@FieldDefine(title = "发文字号")
	@Column(name = "ISSUE_NO_")
	@Size(max = 100)
	@NotEmpty
	protected String issueNo;
	/* 发文机关或部门 */
	@FieldDefine(title = "发文机关或部门")
	@Column(name = "ISSUE_DEP_ID_")
	@Size(max = 64)
	protected String issueDepId;
	/* 是否联合发文件 */
	@FieldDefine(title = "是否联合发文件")
	@Column(name = "IS_JOIN_ISSUE_")
	@Size(max = 20)
	protected String isJoinIssue;
	/* 联合发文单位或部门 */
	@FieldDefine(title = "联合发文单位或部门")
	@Column(name = "JOIN_DEP_IDS_")
	@Size(max = 512)
	protected String joinDepIds;
	/* 主送单位 */
	@FieldDefine(title = "主送单位")
	@Column(name = "MAIN_DEP_ID_")
	@Size(max = 64)
	protected String mainDepId;
	/* 抄送单位或部门 */
	@FieldDefine(title = "抄送单位或部门")
	@Column(name = "CC_DEP_ID_")
	@Size(max = 64)
	protected String ccDepId;
	/* 承办部门ID */
	@FieldDefine(title = "承办部门ID")
	@Column(name = "TAKE_DEP_ID_")
	@Size(max = 64)
	protected String takeDepId;
	/* 协办部门ID */
	@FieldDefine(title = "协办部门ID")
	@Column(name = "ASS_DEP_ID_")
	@Size(max = 64)
	protected String assDepId;
	/* 文件标题 */
	@FieldDefine(title = "文件标题")
	@Column(name = "SUBJECT_")
	@Size(max = 200)
	@NotEmpty
	protected String subject;
	/* 秘密等级 */
	@FieldDefine(title = "秘密等级")
	@Column(name = "PRIVACY_LEVEL_")
	@Size(max = 50)
	protected String privacyLevel;
	/* 保密期限(年) */
	@FieldDefine(title = "保密期限(年)")
	@Column(name = "SECRECY_TERM_")
	protected Integer secrecyTerm;
	/* 打印份数 */
	@FieldDefine(title = "打印份数")
	@Column(name = "PRINT_COUNT_")
	protected Integer printCount;
	/* 主题词 */
	@FieldDefine(title = "主题词")
	@Column(name = "KEYWORDS_")
	@Size(max = 256)
	protected String keywords;
	/* 紧急程度提 */
	@FieldDefine(title = "紧急程度提")
	@Column(name = "URGENT_LEVEL_")
	@Size(max = 50)
	protected String urgentLevel;
	/* 内容简介 */
	@FieldDefine(title = "内容简介")
	@Column(name = "SUMMARY_")
	@Size(max = 1024)
	protected String summary;
	/* 正文路径 */
	@FieldDefine(title = "正文路径")
	@Column(name = "BODY_FILE_PATH_")
	@Size(max = 255)
	protected String bodyFilePath;
	/* 附件IDs */
	@FieldDefine(title = "附件IDs")
	@Column(name = "FILE_IDS_")
	@Size(max = 512)
	protected String fileIds;
	/* 附件名称 */
	@FieldDefine(title = "附件名称")
	@Column(name = "FILE_NAMES_")
	@Size(max = 512)
	protected String fileNames;
	/* 发文人ID */
	@FieldDefine(title = "发文人ID")
	@Column(name = "ISSUE_USR_ID_")
	@Size(max = 64)
	protected String issueUsrId;
	/* 公文来源
	@FieldDefine(title = "公文来源")
	@Column(name = "FROM_")
	@Size(max = 50)
	protected String from; */
	/* 收发状态 */
	@FieldDefine(title = "收发状态")
	@Column(name = "ARCH_TYPE_")
	protected Short archType;
	/* 用于收文时使用，指向原公文ID */
	@FieldDefine(title = "原公文ID")
	@Column(name = "ORG_ARCH_ID_")
	@Size(max = 64)
	protected String orgArchId;
	/* 用于收文时，部门对自身的公文自编号 */
	@FieldDefine(title = "公文自编号")
	@Column(name = "SELF_NO_")
	@Size(max = 100)
	protected String selfNo;
	/* 公文状态 */
	@FieldDefine(title = "公文状态")
	@Column(name = "STATUS_")
	@Size(max = 256)
	@NotEmpty
	protected String status;
	/* 流程运行id */
	@FieldDefine(title = "流程运行id")
	@Column(name = "BPM_INST_ID_")
	@Size(max = 64)
	protected String bpmInstId;
	/* 流程方案ID */
	@FieldDefine(title = "流程方案ID")
	@Column(name = "BPM_SOL_ID_")
	@Size(max = 64)
	protected String bpmSolId;
	/* 公文的类型 */
	@FieldDefine(title = "公文的类型")
	@Column(name = "DOC_TYPE_")
	@Size(max = 20)
	protected String docType;
	/* 发布日期 */
	@FieldDefine(title = "发布日期")
	@Column(name = "ISSUED_DATE_")
	protected java.util.Date issuedDate;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected SysTree sysTree;
	/*公文分类中文*/
	@Transient
	protected String CNType;

	/**
	 * 获取中文公文分类
	 * @return
	 */
	public String getCNType() {
		return CNType;
	}
	/**
	 * 设置中文公文分类
	 * @return
	 */
	public void setCNType(String cNType) {
		CNType = cNType;
	}

	/**
	 * Default Empty Constructor for class OdDocument
	 */
	public OdDocument() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class OdDocument
	 */
	public OdDocument(String in_docId) {
		this.setDocId(in_docId);
	}

	public SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(SysTree in_sysTree) {
		this.sysTree = in_sysTree;
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
	 * 发文分类ID * @return String
	 */
	public String getTreeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 发文分类ID
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

	/**
	 * 发文字号 * @return String
	 */
	public String getIssueNo() {
		return this.issueNo;
	}

	/**
	 * 设置 发文字号
	 */
	public void setIssueNo(String aValue) {
		this.issueNo = aValue;
	}

	/**
	 * 发文机关或部门 * @return String
	 */
	public String getIssueDepId() {
		return this.issueDepId;
	}

	/**
	 * 设置 发文机关或部门
	 */
	public void setIssueDepId(String aValue) {
		this.issueDepId = aValue;
	}

	/**
	 * 是否联合发文件 * @return String
	 */
	public String getIsJoinIssue() {
		return this.isJoinIssue;
	}

	/**
	 * 设置 是否联合发文件
	 */
	public void setIsJoinIssue(String aValue) {
		this.isJoinIssue = aValue;
	}

	/**
	 * 联合发文单位或部门 * @return String
	 */
	public String getJoinDepIds() {
		return this.joinDepIds;
	}

	/**
	 * 设置 联合发文单位或部门
	 */
	public void setJoinDepIds(String aValue) {
		this.joinDepIds = aValue;
	}

	/**
	 * 主送单位 * @return String
	 */
	public String getMainDepId() {
		return this.mainDepId;
	}

	/**
	 * 设置 主送单位
	 */
	public void setMainDepId(String aValue) {
		this.mainDepId = aValue;
	}

	/**
	 * 抄送单位或部门 * @return String
	 */
	public String getCcDepId() {
		return this.ccDepId;
	}

	/**
	 * 设置 抄送单位或部门
	 */
	public void setCcDepId(String aValue) {
		this.ccDepId = aValue;
	}

	/**
	 * 承办部门ID * @return String
	 */
	public String getTakeDepId() {
		return this.takeDepId;
	}

	/**
	 * 设置 承办部门ID
	 */
	public void setTakeDepId(String aValue) {
		this.takeDepId = aValue;
	}

	/**
	 * 协办部门ID * @return String
	 */
	public String getAssDepId() {
		return this.assDepId;
	}

	/**
	 * 设置 协办部门ID
	 */
	public void setAssDepId(String aValue) {
		this.assDepId = aValue;
	}

	/**
	 * 文件标题 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 文件标题
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 秘密等级 * @return String
	 */
	public String getPrivacyLevel() {
		return this.privacyLevel;
	}

	/**
	 * 设置 秘密等级
	 */
	public void setPrivacyLevel(String aValue) {
		this.privacyLevel = aValue;
	}

	/**
	 * 保密期限(年) * @return Integer
	 */
	public Integer getSecrecyTerm() {
		return this.secrecyTerm;
	}

	/**
	 * 设置 保密期限(年)
	 */
	public void setSecrecyTerm(Integer aValue) {
		this.secrecyTerm = aValue;
	}

	/**
	 * 打印份数 * @return Integer
	 */
	public Integer getPrintCount() {
		return this.printCount;
	}

	/**
	 * 设置 打印份数
	 */
	public void setPrintCount(Integer aValue) {
		this.printCount = aValue;
	}

	/**
	 * 主题词 * @return String
	 */
	public String getKeywords() {
		return this.keywords;
	}

	/**
	 * 设置 主题词
	 */
	public void setKeywords(String aValue) {
		this.keywords = aValue;
	}

	/**
	 * 紧急程度提 * @return String
	 */
	public String getUrgentLevel() {
		return this.urgentLevel;
	}

	/**
	 * 设置 紧急程度提
	 */
	public void setUrgentLevel(String aValue) {
		this.urgentLevel = aValue;
	}

	/**
	 * 内容简介 * @return String
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * 设置 内容简介
	 */
	public void setSummary(String aValue) {
		this.summary = aValue;
	}

	/**
	 * 正文路径 * @return String
	 */
	public String getBodyFilePath() {
		return this.bodyFilePath;
	}

	/**
	 * 设置 正文路径
	 */
	public void setBodyFilePath(String aValue) {
		this.bodyFilePath = aValue;
	}

	/**
	 * 附件IDs * @return String
	 */
	public String getFileIds() {
		return this.fileIds;
	}

	/**
	 * 设置 附件IDs
	 */
	public void setFileIds(String aValue) {
		this.fileIds = aValue;
	}

	/**
	 * 附件名称 * @return String
	 */
	public String getFileNames() {
		return this.fileNames;
	}

	/**
	 * 设置 附件名称
	 */
	public void setFileNames(String aValue) {
		this.fileNames = aValue;
	}

	/**
	 * 发文人ID * @return String
	 */
	public String getIssueUsrId() {
		return this.issueUsrId;
	}

	/**
	 * 设置 发文人ID
	 */
	public void setIssueUsrId(String aValue) {
		this.issueUsrId = aValue;
	}

/*	*//**
	 * 公文来源 * @return String
	 *//*
	public String getFrom() {
		return this.from;
	}

	*//**
	 * 设置 公文来源
	 *//*
	public void setFrom(String aValue) {
		this.from = aValue;
	}*/

	/**
	 * 0=发文1=收文 * @return Short
	 */
	public Short getArchType() {
		return this.archType;
	}

	/**
	 * 设置 0=发文1=收文
	 */
	public void setArchType(Short aValue) {
		this.archType = aValue;
	}

	/**
	 * 用于收文时使用，指向原公文ID * @return String
	 */
	public String getOrgArchId() {
		return this.orgArchId;
	}

	/**
	 * 设置 用于收文时使用，指向原公文ID
	 */
	public void setOrgArchId(String aValue) {
		this.orgArchId = aValue;
	}

	/**
	 * 用于收文时，部门对自身的公文自编号 * @return String
	 */
	public String getSelfNo() {
		return this.selfNo;
	}

	/**
	 * 设置 用于收文时，部门对自身的公文自编号
	 */
	public void setSelfNo(String aValue) {
		this.selfNo = aValue;
	}

	/**
	 * 公文状态 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 公文状态
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 流程运行id * @return String
	 */
	public String getBpmInstId() {
		return this.bpmInstId;
	}

	/**
	 * 设置 流程运行id
	 */
	public void setBpmInstId(String aValue) {
		this.bpmInstId = aValue;
	}

	/**
	 * 流程方案ID * @return String
	 */
	public String getBpmSolId() {
		return this.bpmSolId;
	}

	/**
	 * 设置 流程方案ID
	 */
	public void setBpmSolId(String aValue) {
		this.bpmSolId = aValue;
	}

	/**
	 * 公文的类型 * @return String
	 */
	public String getDocType() {
		return this.docType;
	}

	/**
	 * 设置 公文的类型
	 */
	public void setDocType(String aValue) {
		this.docType = aValue;
	}

	/**
	 * 发布日期 * @return java.util.Date
	 */
	public java.util.Date getIssuedDate() {
		return this.issuedDate;
	}

	/**
	 * 设置 发布日期
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setIssuedDate(java.util.Date aValue) {
		this.issuedDate = aValue;
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
		if (!(object instanceof OdDocument)) {
			return false;
		}
		OdDocument rhs = (OdDocument) object;
		return new EqualsBuilder().append(this.docId, rhs.docId).append(this.issueNo, rhs.issueNo).append(this.issueDepId, rhs.issueDepId).append(this.isJoinIssue, rhs.isJoinIssue).append(this.joinDepIds, rhs.joinDepIds)
				.append(this.mainDepId, rhs.mainDepId).append(this.ccDepId, rhs.ccDepId).append(this.takeDepId, rhs.takeDepId).append(this.assDepId, rhs.assDepId).append(this.subject, rhs.subject)
				.append(this.privacyLevel, rhs.privacyLevel).append(this.secrecyTerm, rhs.secrecyTerm).append(this.printCount, rhs.printCount).append(this.keywords, rhs.keywords).append(this.urgentLevel, rhs.urgentLevel)
				.append(this.summary, rhs.summary).append(this.bodyFilePath, rhs.bodyFilePath).append(this.fileIds, rhs.fileIds).append(this.fileNames, rhs.fileNames).append(this.issueUsrId, rhs.issueUsrId)
				.append(this.archType, rhs.archType).append(this.orgArchId, rhs.orgArchId).append(this.selfNo, rhs.selfNo).append(this.status, rhs.status).append(this.bpmInstId, rhs.bpmInstId).append(this.bpmSolId, rhs.bpmSolId)
				.append(this.docType, rhs.docType).append(this.issuedDate, rhs.issuedDate).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.docId).append(this.issueNo).append(this.issueDepId).append(this.isJoinIssue).append(this.joinDepIds).append(this.mainDepId).append(this.ccDepId).append(this.takeDepId)
				.append(this.assDepId).append(this.subject).append(this.privacyLevel).append(this.secrecyTerm).append(this.printCount).append(this.keywords).append(this.urgentLevel).append(this.summary).append(this.bodyFilePath)
				.append(this.fileIds).append(this.fileNames).append(this.issueUsrId).append(this.archType).append(this.orgArchId).append(this.selfNo).append(this.status).append(this.bpmInstId).append(this.bpmSolId)
				.append(this.docType).append(this.issuedDate).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("docId", this.docId).append("issueNo", this.issueNo).append("issueDepId", this.issueDepId).append("isJoinIssue", this.isJoinIssue).append("joinDepIds", this.joinDepIds)
				.append("mainDepId", this.mainDepId).append("ccDepId", this.ccDepId).append("takeDepId", this.takeDepId).append("assDepId", this.assDepId).append("subject", this.subject).append("privacyLevel", this.privacyLevel)
				.append("secrecyTerm", this.secrecyTerm).append("printCount", this.printCount).append("keywords", this.keywords).append("urgentLevel", this.urgentLevel).append("summary", this.summary)
				.append("bodyFilePath", this.bodyFilePath).append("fileIds", this.fileIds).append("fileNames", this.fileNames).append("issueUsrId", this.issueUsrId).append("archType", this.archType)
				.append("orgArchId", this.orgArchId).append("selfNo", this.selfNo).append("status", this.status).append("bpmInstId", this.bpmInstId).append("bpmSolId", this.bpmSolId).append("docType", this.docType)
				.append("issuedDate", this.issuedDate).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime)
				.toString();
	}

}
