package com.redxun.saweb.tree;

import java.util.List;

/**
 * 
 * <pre> 
 * 描述：树节点
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年8月14日-下午8:22:26
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class TreeNode {
	protected String id;
	protected String text;
	protected String cls;
	protected String glyph;
	protected String key;
	protected Integer sn;
	//是否选中
	protected Boolean checked;
	//保留字段1
	protected String extField1;
	//保留字段1
	protected String extField2;
	//保留字段3
	protected String extField3;
	//保留字段4
	protected String extField4;
	
	// 为子节点
	protected boolean leaf = true;
	// 菜单点击事件处理函数
	protected String handler = "onMenuClick";

	protected List<TreeNode> children;

	private boolean expanded = true;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getGlyph() {
		return glyph;
	}

	public void setGlyph(String glyph) {
		this.glyph = glyph;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
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

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExtField1() {
		return extField1;
	}

	public void setExtField1(String extField1) {
		this.extField1 = extField1;
	}

	public String getExtField2() {
		return extField2;
	}

	public void setExtField2(String extField2) {
		this.extField2 = extField2;
	}

	public String getExtField3() {
		return extField3;
	}

	public void setExtField3(String extField3) {
		this.extField3 = extField3;
	}

	public String getExtField4() {
		return extField4;
	}

	public void setExtField4(String extField4) {
		this.extField4 = extField4;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}



}
