package com.redxun.saweb.config.ui;
/**
 * UI皮肤
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class UiSkin {
	/**
	 * 皮肤ID
	 */
	private String id;
	/**
	 * 皮肤Name
	 */
	private String name;
	
	public UiSkin() {
	
	}
	
	public UiSkin(String id,String name){
		this.id=id;
		this.name=name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
