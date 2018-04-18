package com.redxun.saweb.tag.ui;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class MiniComboxTag extends AbstractMiniDataControl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//是否允许填写（对于combox生效），非必填
	private boolean allowInput=false;
	//是否允许空项，非必填
	private boolean showNullItem=false;
	//空项文件内容，非必填
	private String nullItemText="请选择...";
	//选择项更改时触发的事件

	@Override
	public String getControlType() {
		return "mini-combobox";
	}
	
	@Override
	public String getExtAttrs() {
		StringBuffer sb=new StringBuffer();
		sb.append("\" allowInput=\"");
		sb.append(allowInput);
		sb.append("\" showNullItem=\"");
		sb.append(showNullItem);
		sb.append("\"");
		if(StringUtils.isNotBlank(nullItemText)){
			sb.append(" nullItemText=\"").append(nullItemText).append("\" ");
		}
		return sb.toString();
	}

	public boolean isAllowInput() {
		return allowInput;
	}

	public void setAllowInput(boolean allowInput) {
		this.allowInput = allowInput;
	}

	public boolean isShowNullItem() {
		return showNullItem;
	}

	public void setShowNullItem(boolean showNullItem) {
		this.showNullItem = showNullItem;
	}

	public String getNullItemText() {
		return nullItemText;
	}

	public void setNullItemText(String nullItemText) {
		this.nullItemText = nullItemText;
	}
	
}
