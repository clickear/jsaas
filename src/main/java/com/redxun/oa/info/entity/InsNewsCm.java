package com.redxun.oa.info.entity;

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

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：InsNewsCm实体类定义
 * 公司或新闻评论
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "INS_NEWS_CM")
@TableDefine(title = "公司或新闻评论")
public class InsNewsCm extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "COMM_ID_")
	protected String commId;
	/* 评论人名 */
	@FieldDefine(title = "评论人名")
	@Column(name = "FULL_NAME_")
	@Size(max = 50)
	@NotEmpty
	protected String fullName;
	/* 评论内容 */
	@FieldDefine(title = "评论内容")
	@Column(name = "CONTENT_")
	@Size(max = 1024)
	@NotEmpty
	protected String content;
	/* 赞同与顶 */
	@FieldDefine(title = "赞同与顶")
	@Column(name = "AGREE_NUMS_")
	protected Integer agreeNums;
	/* 反对与鄙视次数 */
	@FieldDefine(title = "反对与鄙视次数")
	@Column(name = "REFUSE_NUMS_")
	protected Integer refuseNums;
	/* 是否为回复 */
	@FieldDefine(title = "是否为回复")
	@Column(name = "IS_REPLY_")
	@Size(max = 20)
	@NotEmpty
	protected String isReply;
	/* 回复评论ID */
	@FieldDefine(title = "回复评论ID")
	@Column(name = "REP_ID_")
	@Size(max = 64)
	protected String repId;
	
	/* 回复的新闻标题 */
	@Transient
	protected String newsTitle;
	
	@FieldDefine(title = "所属新闻")
	@ManyToOne
	@JoinColumn(name = "NEW_ID_")
	protected com.redxun.oa.info.entity.InsNews insNews;

	/**
	 * Default Empty Constructor for class InsNewsCm
	 */
	public InsNewsCm() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class InsNewsCm
	 */
	public InsNewsCm(String in_commId) {
		this.setCommId(in_commId);
	}

	public com.redxun.oa.info.entity.InsNews getInsNews() {
		return insNews;
	}

	public void setInsNews(com.redxun.oa.info.entity.InsNews in_insNews) {
		this.insNews = in_insNews;
	}

	/**
	 * 评论ID * @return String
	 */
	public String getCommId() {
		return this.commId;
	}

	/**
	 * 设置 评论ID
	 */
	public void setCommId(String aValue) {
		this.commId = aValue;
	}

	/**
	 * 信息ID * @return String
	 */
	public String getNewId() {
		return this.getInsNews() == null ? null : this.getInsNews().getNewId();
	}

	/**
	 * 设置 信息ID
	 */
	public void setNewId(String aValue) {
		if (aValue == null) {
			insNews = null;
		} else if (insNews == null) {
			insNews = new com.redxun.oa.info.entity.InsNews(aValue);
			// insNews.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			insNews.setNewId(aValue);
		}
	}

	/**
	 * 评论人名 * @return String
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * 设置 评论人名
	 */
	public void setFullName(String aValue) {
		this.fullName = aValue;
	}

	
	/**
	 * 回复的新闻的标题 * @return String
	 */
	public String getNewsTitle() {
		return newsTitle;
	}

	/**
	 * 设置回复的新闻的标题 
	 */
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	/**
	 * 评论内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 评论内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 赞同与顶 * @return Integer
	 */
	public Integer getAgreeNums() {
		return this.agreeNums;
	}

	/**
	 * 设置 赞同与顶
	 */
	public void setAgreeNums(Integer aValue) {
		this.agreeNums = aValue;
	}

	/**
	 * 反对与鄙视次数 * @return Integer
	 */
	public Integer getRefuseNums() {
		return this.refuseNums;
	}

	/**
	 * 设置 反对与鄙视次数
	 */
	public void setRefuseNums(Integer aValue) {
		this.refuseNums = aValue;
	}

	/**
	 * 是否为回复 * @return String
	 */
	public String getIsReply() {
		return this.isReply;
	}

	/**
	 * 设置 是否为回复
	 */
	public void setIsReply(String aValue) {
		this.isReply = aValue;
	}

	/**
	 * 回复评论ID * @return String
	 */
	public String getRepId() {
		return this.repId;
	}

	/**
	 * 设置 回复评论ID
	 */
	public void setRepId(String aValue) {
		this.repId = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.commId;
	}

	@Override
	public Serializable getPkId() {
		return this.commId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.commId = (String) pkId;
	}
	
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsNewsCm)) {
			return false;
		}
		InsNewsCm rhs = (InsNewsCm) object;
		return new EqualsBuilder().append(this.commId, rhs.commId).append(this.fullName, rhs.fullName).append(this.content, rhs.content).append(this.agreeNums, rhs.agreeNums).append(this.refuseNums, rhs.refuseNums).append(this.isReply, rhs.isReply).append(this.repId, rhs.repId).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.commId).append(this.fullName).append(this.content).append(this.agreeNums).append(this.refuseNums).append(this.isReply).append(this.repId).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("commId", this.commId).append("fullName", this.fullName).append("content", this.content).append("agreeNums", this.agreeNums).append("refuseNums", this.refuseNums).append("isReply", this.isReply).append("repId", this.repId).append("tenantId", this.tenantId).append("createBy", this.createBy).append("createTime", this.createTime)
				.append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
