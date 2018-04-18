package com.redxun.wx.ent.util.model.extend;

import com.redxun.wx.ent.util.model.BaseMsg;

public class VideoMsg extends BaseMsg {

	private VideoModel video;
	
	
	@Override
	public String getMsgtype() {
		return "video";
	}

	public VideoModel getVideo() {
		return video;
	}

	public void setVideo(VideoModel video) {
		this.video = video;
	}

	public static void main(String[] args) {
		VideoMsg img=new VideoMsg();
		VideoModel model=new VideoModel();
		model.setDescription("ddd");
		model.setMediaId("333");
		model.setTitle("国庆阅兵");
		img.setVideo(model);
		System.out.println(img.toString());
	}
	

	
}
