package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.redxun.core.database.api.model.Column;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;

public class OfficeAttrParse extends AbstractBoAttrParse {


	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		field.setDataType(Column.COLUMN_TYPE_VARCHAR);
		field.setLength(100);
	}


	@Override
	public String getPluginName() {
		return "mini-office";
	}

	@Override
	public String getDescription() {
		return "Office控件";
	}

	@Override
	public boolean isSingleAttr() {
		return true;
	}

}
