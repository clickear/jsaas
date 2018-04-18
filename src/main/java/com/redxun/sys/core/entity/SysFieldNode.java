package com.redxun.sys.core.entity;

import java.util.List;
/**
 * 
 * <pre> 
 * 描述：字段树节点，若当前字段为复合字段，则其存在子
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年9月25日-下午3:40:23
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class SysFieldNode extends SysField {
	// 为子节点
	protected boolean leaf = true;

	protected boolean expanded = false;

	protected List<SysFieldNode> children;
	/**
	 * Data Index值
	 */
	protected String dataIndex=null;
	/**
	 * 树节点上文字描述
	 */
	protected String text;
	/**
	 * 隐藏文字的描述
	 */
	protected String hiddenText;
	
	/**
	 * 是否选中
	 */
	protected Boolean checked;
	/**
	 * 字段类型
	 */
	protected String fieldCat;

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

	public List<SysFieldNode> getChildren() {
		return children;
	}

	public void setChildren(List<SysFieldNode> children) {
		this.children = children;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getHiddenText() {
		return hiddenText;
	}

	public void setHiddenText(String hiddenText) {
		this.hiddenText = hiddenText;
	}

	
}
