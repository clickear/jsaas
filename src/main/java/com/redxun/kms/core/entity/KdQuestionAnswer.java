package com.redxun.kms.core.entity;

import com.redxun.core.entity.BaseTenantEntity;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：KdQuestionAnswer实体类定义
 * 问题答案
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_QUES_ANSWER")
@TableDefine(title = "问题答案")
public class KdQuestionAnswer extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ANSWER_ID_")
	protected String answerId;
	/**/
	@FieldDefine(title = "")
	@Column(name = "REPLY_CONTENT_")
	@Size(max = 65535)
	@NotEmpty
	protected String replyContent;
	/**/
	@FieldDefine(title = "")
	@Column(name = "IS_BEST_")
	@Size(max = 20)
	protected String isBest;
	/**/
	@FieldDefine(title = "")
	@Column(name = "AUTHOR_ID_")
	@Size(max = 64)
	@NotEmpty
	protected String authorId;
	@FieldDefine(title = "知识问答")
	@ManyToOne
	@JoinColumn(name = "QUE_ID_")
	protected com.redxun.kms.core.entity.KdQuestion kdQuestion;

	/**
	 * Default Empty Constructor for class KdQuestionAnswer
	 */
	public KdQuestionAnswer() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdQuestionAnswer
	 */
	public KdQuestionAnswer(String in_answerId) {
		this.setAnswerId(in_answerId);
	}

	public com.redxun.kms.core.entity.KdQuestion getKdQuestion() {
		return kdQuestion;
	}

	public void setKdQuestion(com.redxun.kms.core.entity.KdQuestion in_kdQuestion) {
		this.kdQuestion = in_kdQuestion;
	}

	/**
	 * * @return String
	 */
	public String getAnswerId() {
		return this.answerId;
	}

	/**
	 * 设置
	 */
	public void setAnswerId(String aValue) {
		this.answerId = aValue;
	}

	/**
	 * 问题ID * @return String
	 */
	public String getQueId() {
		return this.getKdQuestion() == null ? null : this.getKdQuestion().getQueId();
	}

	/**
	 * 设置 问题ID
	 */
	public void setQueId(String aValue) {
		if (aValue == null) {
			kdQuestion = null;
		} else if (kdQuestion == null) {
			kdQuestion = new com.redxun.kms.core.entity.KdQuestion(aValue);
		} else {
			kdQuestion.setQueId(aValue);
		}
	}

	/**
	 * * @return String
	 */
	public String getReplyContent() {
		return this.replyContent;
	}

	/**
	 * 设置
	 */
	public void setReplyContent(String aValue) {
		this.replyContent = aValue;
	}

	/**
	 * * @return String
	 */
	public String getIsBest() {
		return this.isBest;
	}

	/**
	 * 设置
	 */
	public void setIsBest(String aValue) {
		this.isBest = aValue;
	}

	/**
	 * * @return String
	 */
	public String getAuthorId() {
		return this.authorId;
	}

	/**
	 * 设置
	 */
	public void setAuthorId(String aValue) {
		this.authorId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.answerId;
	}

	@Override
	public Serializable getPkId() {
		return this.answerId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.answerId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdQuestionAnswer)) {
			return false;
		}
		KdQuestionAnswer rhs = (KdQuestionAnswer) object;
		return new EqualsBuilder().append(this.answerId, rhs.answerId).append(this.replyContent, rhs.replyContent).append(this.isBest, rhs.isBest).append(this.authorId, rhs.authorId).append(this.tenantId, rhs.tenantId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.answerId).append(this.replyContent).append(this.isBest).append(this.authorId).append(this.tenantId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("answerId", this.answerId).append("replyContent", this.replyContent).append("isBest", this.isBest).append("authorId", this.authorId).append("tenantId", this.tenantId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
