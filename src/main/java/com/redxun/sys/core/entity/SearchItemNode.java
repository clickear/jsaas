package com.redxun.sys.core.entity;

import java.util.List;
/**
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 * @author mansan
 *
 */
public class SearchItemNode extends SysSearchItem{
		// 为子节点
		protected boolean leaf = true;
		protected boolean expanded=true;
		// 菜单点击事件处理函数
		protected String handler = "onMenuClick";

		protected List<SearchItemNode> children;

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

		public String getHandler() {
			return handler;
		}

		public void setHandler(String handler) {
			this.handler = handler;
		}

		public List<SearchItemNode> getChildren() {
			return children;
		}

		public void setChildren(List<SearchItemNode> children) {
			this.children = children;
		}

}
