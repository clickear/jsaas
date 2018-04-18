package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.database.api.model.Column;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;

public class RelatedSolutionAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-relatedsolution";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		field.setDataType(Column.COLUMN_TYPE_CLOB);
	}
	

	
	@Override
	public boolean isSingleAttr() {
		return true;
	}
	
	@Override
	public String getDescription() {
		return "关联流程";
	}
	
	
}
