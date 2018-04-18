package com.redxun.oa.info.entity;

import java.util.List;

/**
 *  回复评论类,回复新闻New的回复类,包含子回复ReplyComment类,回复这条Comment评论的评论为子评论
 * @author Administrator
 *
 */
public class Comment {
	protected String cmId;//这条评论的Id
	protected String name;//评论的人名
	protected String time;//评论时间
	protected String content;//评论内容
	protected List<ReplyComment> rpcomment;//子评论,回复这条Comment评论的评论为子评论
	
	public Comment(){
		
	}
	


	public Comment(String cmId, String name, String time, String content,
			List<ReplyComment> rpcomment) {
		super();
		this.cmId = cmId;
		this.name = name;
		this.time = time;
		this.content = content;
		this.rpcomment = rpcomment;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getCmId() {
		return cmId;
	}

	public void setCmId(String cmId) {
		this.cmId = cmId;
	}



	public List<ReplyComment> getRpcomment() {
		return rpcomment;
	}



	public void setRpcomment(List<ReplyComment> rpcomment) {
		this.rpcomment = rpcomment;
	}
	
	
}
