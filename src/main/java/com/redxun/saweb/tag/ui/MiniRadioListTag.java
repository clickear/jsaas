package com.redxun.saweb.tag.ui;

/**
 * 单选框按钮列表
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class MiniRadioListTag extends AbstractMiniDataControl{
	@Override
	public String getControlType() {
		return "mini-radiobuttonlist";
	}
	
	@Override
	public String getExtAttrs() {
		return null;
	}
}
