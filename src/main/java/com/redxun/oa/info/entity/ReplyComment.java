package com.redxun.oa.info.entity;

/**
 * 这是子回复类,是回复Comment类的子类
 * @author Administrator
 *
 */
public class ReplyComment {
	protected String cmId;//这条子回复的评论Id
	protected String name;//评论人名
	protected String time;//评论时间
	protected String content;//评论内容
	protected String replyName;//回复人名
	protected String replyId;//回复的评论的Id
	
	public ReplyComment(){
		
	}
	public ReplyComment(String cmId, String name, String time, String content,
			String replyName, String replyId) {
		super();
		this.cmId = cmId;
		this.name = name;
		this.time = time;
		this.content = content;
		this.replyName = replyName;
		this.replyId = replyId;
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
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	
	
}
