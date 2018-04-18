package com.redxun.sys.core.entity;

import com.redxun.core.entity.BaseTreeNode;
/**
 * 菜单菜单树节点
 * @author mansan
 *
 */
public class SysMenuNode extends BaseTreeNode {

	protected String nodeUrl;

	protected String showType;

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

}
