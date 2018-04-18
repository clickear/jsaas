package com.redxun.kms.core.entity;

import java.io.Serializable;

/**
 * <pre>
 * 描述：KdDocFav实体类定义
 * 文档收藏
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public class KdMapNote implements Serializable {
	String id;
	String left;
	String top;
	String width;
	String height;
	String note;
	String link;
	String pwidth;
	String pheight;
	String type;
	String docIds;
	String docNum;
	
	
	
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPwidth() {
		return pwidth;
	}
	public void setPwidth(String pwidth) {
		this.pwidth = pwidth;
	}
	public String getPheight() {
		return pheight;
	}
	public void setPheight(String pheight) {
		this.pheight = pheight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDocIds() {
		return docIds;
	}
	public void setDocIds(String docIds) {
		this.docIds = docIds;
	}
	
	
}

