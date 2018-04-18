package com.redxun.sys.core.entity;

import java.util.List;

/**
 * 菜单节点
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 *
 */
public class MenuNode extends SysMenu{

	private static final long serialVersionUID = 1L;

	// 为子节点
	protected boolean leaf = true;
	protected boolean expanded=false;
	// 菜单点击事件处理函数
	protected String handler = "onMenuClick";

	protected List<MenuNode> children;

	public String getText() {
		return name;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public List<MenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
