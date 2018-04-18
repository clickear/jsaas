package com.redxun.kms.core.entity;

import java.util.List;

public class KdMenu {
	private String name;
	private String id;
	private List<KdMenu> lowerMenu;
	
	
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
	public List<KdMenu> getLowerMenu() {
		return lowerMenu;
	}
	public void setLowerMenu(List<KdMenu> lowerMenu) {
		this.lowerMenu = lowerMenu;
	}
	
	
}
