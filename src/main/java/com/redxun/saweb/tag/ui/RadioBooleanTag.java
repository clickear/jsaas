package com.redxun.saweb.tag.ui;

/**
 * 是否Boolean选择控件
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class RadioBooleanTag extends AbstractMiniDataControl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final String booleanData="[{id:'YES',text:'是'},{id:'NO',text:'否'}]";
	
	@Override
	public String getControlType() {
		return "mini-radiobuttonlist";
	}
	
	@Override
	public String getExtAttrs() {
		return null;
	}
	
	@Override
	public String getData() {
		return booleanData;
	}
}
