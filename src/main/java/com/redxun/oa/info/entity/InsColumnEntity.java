package com.redxun.oa.info.entity;

import java.util.List;

/**
 * 自定义栏目返回的数据结构。
 * @author ray
 */
public class InsColumnEntity<T> {
	private String title;
	private String url;
	private List<T> obj;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getObj() {
		return obj;
	}
	public void setObj(List<T> obj) {
		this.obj = obj;
	}
	
	public InsColumnEntity(){
		super();
	}
	
	public InsColumnEntity(String title, String url, List<T> obj) {
		super();
		this.title = title;
		this.url = url;
		this.obj = obj;
	}
	
	
}
