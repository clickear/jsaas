package com.redxun.oa.article.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.util.Tree;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 *  
 * 描述：文章实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-10-11 15:46:34
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "PRO_ARTICLE")
@TableDefine(title = "文章")
@XStreamAlias("proArticle")
public class ProArticle extends BaseTenantEntity  implements Tree {
	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "所属项目ID")
	@Column(name = "BELONG_PRO_ID_")
	protected String belongProId; 
	@FieldDefine(title = "标题")
	@Column(name = "TITLE_")
	protected String title; 
	@FieldDefine(title = "AUTHOR_")
	@Column(name = "AUTHOR_")
	protected String author; 
	@FieldDefine(title = "概要")
	@Column(name = "OUT_LINE_")
	protected String outLine; 
	@FieldDefine(title = "类型")
	@Column(name = "TYPE_")
	protected String type; 
	@FieldDefine(title = "父ID")
	@Column(name = "PARENT_ID_")
	protected String parentId; 
	@FieldDefine(title = "序号")
	@Column(name = "SN_")
	protected String sn; 
	@FieldDefine(title = "内容")
	@Column(name = "CONTENT_")
	protected String content; 
	
	@Transient
	private List< Tree> childrens;
	@Transient
	private int titleLevel;
	@Transient
	private String prefixTitle;
	
	
	
	public ProArticle() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public ProArticle(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.id;
	}

	@Override
	public Serializable getPkId() {
		return this.id;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.id = (String) pkId;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setId(String aValue) {
		this.id = aValue;
	}
	
	public void setBelongProId(String belongProId) {
		this.belongProId = belongProId;
	}
	
	/**
	 * 返回 所属项目ID
	 * @return
	 */
	public String getBelongProId() {
		return this.belongProId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 返回 标题
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 返回 AUTHOR_
	 * @return
	 */
	public String getAuthor() {
		return this.author;
	}
	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}
	
	/**
	 * 返回 概要
	 * @return
	 */
	public String getOutLine() {
		return this.outLine;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 返回 父ID
	 * @return
	 */
	public String getParentId() {
		return this.parentId;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/**
	 * 返回 序号
	 * @return
	 */
	public String getSn() {
		return this.sn;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 返回 内容
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	
	
		

	public String getPrefixTitle() {
		return prefixTitle;
	}

	public void setPrefixTitle(String prefixTitle) {
		this.prefixTitle = prefixTitle;
	}

	public int getTitleLevel() {
		return titleLevel;
	}

	public void setTitleLevel(int titleLevel) {
		this.titleLevel = titleLevel;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProArticle)) {
			return false;
		}
		ProArticle rhs = (ProArticle) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.belongProId, rhs.belongProId) 
		.append(this.title, rhs.title) 
		.append(this.author, rhs.author) 
		.append(this.outLine, rhs.outLine) 
		.append(this.type, rhs.type) 
		.append(this.parentId, rhs.parentId) 
		.append(this.sn, rhs.sn) 
		.append(this.content, rhs.content) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.belongProId) 
		.append(this.title) 
		.append(this.author) 
		.append(this.outLine) 
		.append(this.type) 
		.append(this.parentId) 
		.append(this.sn) 
		.append(this.content) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("belongProId", this.belongProId) 
				.append("title", this.title) 
				.append("author", this.author) 
				.append("outLine", this.outLine) 
				.append("type", this.type) 
				.append("parentId", this.parentId) 
				.append("sn", this.sn) 
				.append("content", this.content) 
												.toString();
	}

	@Override
	public String getText() {
		
		return this.title;
	}

	@Override
	public List< Tree> getChildren() {
		return this.childrens;
	}

	@Override
	public void setChildren(List<Tree> list) {
		this.childrens=list;
		
	}

}



