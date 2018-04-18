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
import javax.persistence.OrderBy;
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
 * 描述：KdQuestion实体类定义
 * 知识问答
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_QUESTION")
@TableDefine(title = "知识问答")
@JsonIgnoreProperties(value={"kdQuestionAnswers","kdDocFavs","sysTree"})
public class KdQuestion extends BaseTenantEntity {

	public final static String STATUS_UNSOLVED="UNSOLVED";
	public final static String STATUS_SOLVED="SOLVED";
	
	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "QUE_ID_")
	protected String queId;
	/* 问题内容 */
	@FieldDefine(title = "问题内容")
	@Column(name = "SUBJECT_")
	@Size(max = 80)
	@NotEmpty
	protected String subject;
	/* 详细问题 */
	@FieldDefine(title = "详细问题")
	@Column(name = "QUESTION_")
	@Size(max = 65535)
	protected String question;
	/* 附件 */
	@FieldDefine(title = "附件")
	@Column(name = "FILE_IDS_")
	@Size(max = 512)
	protected String fileIds;
	/* 标签 */
	@FieldDefine(title = "标签")
	@Column(name = "TAGS_")
	@Size(max = 128)
	protected String tags;
	/* 悬赏货币 */
	@FieldDefine(title = "悬赏货币")
	@Column(name = "REWARD_SCORE_")
	protected Integer rewardScore;
	/* 所有人 专家个人 领域专家 */
	@FieldDefine(title = "专家类型")
	@Column(name = "REPLY_TYPE_")
	@Size(max = 80)
	protected String replyType;
	/* 待解决=UNSOLVED 已解决=SOLVED */
	@FieldDefine(title = "解决状态")
	@Column(name = "STATUS_")
	@Size(max = 20)
	protected String status;
	/* 回复数 */
	@FieldDefine(title = "回复数")
	@Column(name = "REPLY_COUNTS_")
	protected Integer replyCounts;
	/* 回复数 */
	@FieldDefine(title = "浏览次数")
	@Column(name = "VIEW_TIMES_")
	protected Integer viewTimes;
	/* 回复者ID */
	@FieldDefine(title = "回复者ID")
	@Column(name = "REPLIER_ID_")
	@Size(max = 64)
	protected String replierId;
	 /*是否本身提问*/
	@Transient
	private String isSelf;
	/*最佳答案*/
	@Transient
	private com.redxun.kms.core.entity.KdQuestionAnswer bestAnswer;
	@FieldDefine(title = "系统分类树")
	@ManyToOne
	@JoinColumn(name = "TREE_ID_")
	protected com.redxun.sys.core.entity.SysTree sysTree;

	@FieldDefine(title = "问题答案")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdQuestion", fetch = FetchType.LAZY)
	 @OrderBy("createTime DESC")
	protected java.util.Set<KdQuestionAnswer> kdQuestionAnswers = new java.util.HashSet<KdQuestionAnswer>();
	
	@FieldDefine(title = "问题收藏")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kdQuestion", fetch = FetchType.LAZY)
	protected java.util.Set<KdDocFav> kdDocFavs = new java.util.HashSet<KdDocFav>();

	/**
	 * Default Empty Constructor for class KdQuestion
	 */
	public KdQuestion() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdQuestion
	 */
	public KdQuestion(String in_queId) {
		this.setQueId(in_queId);
	}
	
	

	public java.util.Set<KdDocFav> getKdDocFavs() {
		return kdDocFavs;
	}

	public void setKdDocFavs(java.util.Set<KdDocFav> kdDocFavs) {
		this.kdDocFavs = kdDocFavs;
	}

	public com.redxun.sys.core.entity.SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(com.redxun.sys.core.entity.SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	public java.util.Set<KdQuestionAnswer> getKdQuestionAnswers() {
		return kdQuestionAnswers;
	}

	public void setKdQuestionAnswers(java.util.Set<KdQuestionAnswer> in_kdQuestionAnswers) {
		this.kdQuestionAnswers = in_kdQuestionAnswers;
	}
	
	/**
	 * 最佳答案@return
	 */
	public com.redxun.kms.core.entity.KdQuestionAnswer getBestAnswer() {
		return bestAnswer;
	}

	/**
	 * 设置 最佳答案
	 */
	public void setBestAnswer(com.redxun.kms.core.entity.KdQuestionAnswer bestAnswer) {
		this.bestAnswer = bestAnswer;
	}

	/**
	 * 是否本身提问 * @return String
	 */
	public String getIsSelf() {
		return this.isSelf;
	}
	
	/**
	 * 设置 是否本身提问
	 */
	public void setIsSelf(String aValue) {
		this.isSelf = aValue;
	}

	/**
	 * 问题ID * @return String
	 */
	public String getQueId() {
		return this.queId;
	}

	/**
	 * 设置 问题ID
	 */
	public void setQueId(String aValue) {
		this.queId = aValue;
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
			sysTree = new com.redxun.sys.core.entity.SysTree(aValue);
		} else {
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 问题内容 * @return String
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置 问题内容
	 */
	public void setSubject(String aValue) {
		this.subject = aValue;
	}

	/**
	 * 详细问题 * @return String
	 */
	public String getQuestion() {
		return this.question;
	}

	/**
	 * 设置 详细问题
	 */
	public void setQuestion(String aValue) {
		this.question = aValue;
	}

	/**
	 * 附件 * @return String
	 */
	public String getFileIds() {
		return this.fileIds;
	}

	/**
	 * 设置 附件
	 */
	public void setFileIds(String aValue) {
		this.fileIds = aValue;
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
	 * 悬赏货币 * @return Integer
	 */
	public Integer getRewardScore() {
		return this.rewardScore;
	}

	/**
	 * 设置 悬赏货币
	 */
	public void setRewardScore(Integer aValue) {
		this.rewardScore = aValue;
	}

	/**
	 * 所有人 专家个人 领域专家 * @return String
	 */
	public String getReplyType() {
		return this.replyType;
	}

	/**
	 * 设置 所有人 专家个人 领域专家
	 */
	public void setReplyType(String aValue) {
		this.replyType = aValue;
	}

	/**
	 * 待解决=UNSOLVED 已解决=SOLVED * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 设置 待解决=UNSOLVED 已解决=SOLVED
	 */
	public void setStatus(String aValue) {
		this.status = aValue;
	}

	/**
	 * 回复数 * @return Integer
	 */
	public Integer getReplyCounts() {
		return this.replyCounts;
	}

	/**
	 * 设置 回复数
	 */
	public void setReplyCounts(Integer aValue) {
		this.replyCounts = aValue;
	}
	
	/**
	 * 回复者ID * @return String
	 */
	public String getReplierId() {
		return this.replierId;
	}

	/**
	 * 设置 回复者ID
	 */
	public void setReplierId(String aValue) {
		this.replierId = aValue;
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

	@Override
	public String getIdentifyLabel() {
		return this.queId;
	}

	@Override
	public Serializable getPkId() {
		return this.queId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.queId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdQuestion)) {
			return false;
		}
		KdQuestion rhs = (KdQuestion) object;
		return new EqualsBuilder().append(this.queId, rhs.queId).append(this.subject, rhs.subject).append(this.question, rhs.question).append(this.fileIds, rhs.fileIds).append(this.tags, rhs.tags).append(this.rewardScore, rhs.rewardScore).append(this.replyType, rhs.replyType).append(this.status, rhs.status).append(this.replyCounts, rhs.replyCounts).append(this.tenantId, rhs.tenantId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.queId).append(this.subject).append(this.question).append(this.fileIds).append(this.tags).append(this.rewardScore).append(this.replyType).append(this.status).append(this.replyCounts).append(this.tenantId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("queId", this.queId).append("subject", this.subject).append("question", this.question).append("fileIds", this.fileIds).append("tags", this.tags).append("rewardScore", this.rewardScore).append("replyType", this.replyType).append("status", this.status).append("replyCounts", this.replyCounts).append("tenantId", this.tenantId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
