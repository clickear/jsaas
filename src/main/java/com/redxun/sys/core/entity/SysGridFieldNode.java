package com.redxun.sys.core.entity;

import java.util.List;
/**
 * 系统表格子节点
 * @author mansan
 *
 */
public class SysGridFieldNode extends SysGridField {
	// 为子节点
	protected boolean leaf = true;

	protected boolean expanded = false;

	protected List<SysGridFieldNode> children;

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public List<SysGridFieldNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysGridFieldNode> children) {
		this.children = children;
	}

	public String getDataIndex() {
		return this.fieldName;
	}

	public String getText() {
		return this.fieldTitle;
	}
}
