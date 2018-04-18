package com.redxun.sys.org.entity;

import com.redxun.core.entity.BaseTreeNode;
/**
 * 系统树节点
 * @author mansan
 *
 */
public class SysTreeNode extends BaseTreeNode{
	
	public SysTreeNode() {
	
	}
	
	/**
	 * 分类Key
	 */
	private String catKey;

	public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}
	
}
