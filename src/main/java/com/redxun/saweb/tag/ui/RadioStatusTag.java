package com.redxun.saweb.tag.ui;

/**
 * 重写，让其生成Boolean的数据
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class RadioStatusTag extends AbstractMiniDataControl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final String statusData="[{id:'ENABLED',text:'启用'},{id:'DISABLED',text:'禁用'}]";
	
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
		return statusData;
	}
}
