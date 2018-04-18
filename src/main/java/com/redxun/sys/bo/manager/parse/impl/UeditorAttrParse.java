package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.redxun.core.database.api.model.Column;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;

public class UeditorAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-ueditor";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		String datatype = el.attr("datatype");
		if(StringUtil.isEmpty(datatype)){
			field.setDataType(Column.COLUMN_TYPE_VARCHAR);
			field.setLength(4000);
			return ;
		}
		if(Column.COLUMN_TYPE_VARCHAR.equals(datatype)){
			field.setDataType(Column.COLUMN_TYPE_VARCHAR);
			field.setLength(Integer.parseInt(el.attr("length").trim())); 
		}
		else{
			field.setDataType(Column.COLUMN_TYPE_CLOB);
		}

	}
	
	@Override
	public String getDescription() {
		return "富文本框";
	}

	@Override
	public boolean isSingleAttr() {
		return true;
	}

}
