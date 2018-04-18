package com.redxun.wx.ent.util.model.extend;

public class News extends TextCard {
	
	/**
	 * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080。
	 */
	private String picurl="";

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

}
