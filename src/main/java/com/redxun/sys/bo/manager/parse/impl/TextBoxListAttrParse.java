package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;

public class TextBoxListAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-textboxlist";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field,el);

	}
	
	@Override
	public boolean isSingleAttr() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return "多选文本框";
	}

}
