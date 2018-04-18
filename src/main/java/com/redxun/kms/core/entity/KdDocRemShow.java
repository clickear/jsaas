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
 * 描述：点评的页面显示类
 * 知识文档点评
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public class KdDocRemShow {

	/* 分数 */
	protected Integer score;
	/* 点评内容 */
	protected String content;
	/* 推荐对象*/
	protected String remTarget;
	/* 推荐人*/
	protected String remName;
	/* 评论时间*/
	protected String remTime;
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}
	public void setKdDoc(com.redxun.kms.core.entity.KdDoc kdDoc) {
		this.kdDoc = kdDoc;
	}
	public String getRemTarget() {
		return remTarget;
	}
	public void setRemTarget(String remTarget) {
		this.remTarget = remTarget;
	}
	public String getRemName() {
		return remName;
	}
	public void setRemName(String remName) {
		this.remName = remName;
	}
	public String getRemTime() {
		return remTime;
	}
	public void setRemTime(String remTime) {
		this.remTime = remTime;
	}
	

	

	
}
