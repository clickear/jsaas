package com.redxun.ui.view.model;

public class UrlColumn {

	/**
	 * 字段
	 */
	private String field="";
	/**
	 * URL
	 */
	private String url="";
	/**
	 * URL 连接类型
	 */
	private String urlType="";
	
	
	public UrlColumn() {
		
	}
	
	public UrlColumn( String field,String url,String urlType) {
		this.field=field;
		this.url=url;
		this.urlType=urlType;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrlType() {
		return urlType;
	}
	
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

}
