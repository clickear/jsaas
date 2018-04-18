package com.redxun.wx.ent.util.model.extend;

public class TextCard {
	/**
	 * 标题，不超过128个字节，超过会自动截断
	 */
	private String title="";
	/**
	 * 描述，不超过512个字节，超过会自动截断
	 */
	private String description="";
	/**
	 * 点击后跳转的链接
	 */
	private String url="";
	private String btntxt="详情";
	
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
	}
	public String getBtntxt() {
		return btntxt;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setBtntxt(String btntxt) {
		this.btntxt = btntxt;
	}
	
	
}
