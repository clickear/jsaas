package com.redxun.wx.ent.util.model.extend;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 视频消息。
 * @author ray
 *
 */
public class VideoModel {
	
	@JSONField(name="media_id")
	private String  mediaId="";
	private String  title="";
	private String  description="";
	
	public String getMediaId() {
		return mediaId;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
