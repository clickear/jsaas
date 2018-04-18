package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;


public class TextAreaAttrParse extends AbstractBoAttrParse {

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		String datatype = el.attr("datatype");
		
		if(StringUtil.isEmpty(datatype)){
			field.setDataType(Column.COLUMN_TYPE_VARCHAR);
			field.setLength(400);
			return ;
		}
		
		if(Column.COLUMN_TYPE_VARCHAR .equals(datatype)){
			field.setDataType(Column.COLUMN_TYPE_VARCHAR);
			int len=getLength(el.attr("length").trim(), 400);
			field.setLength(len); 
		}else{
			field.setDataType(datatype);
		}
		
		JSONObject json=new JSONObject();
		String required=el.attr("required");
		json.put("required",required);
		field.setExtJson(json.toJSONString());

	}

	@Override
	public String getPluginName() {
		return "mini-textarea";
	}
	
	@Override
	public String getDescription() {
		return "多行文本";
	}
	
	@Override
	public boolean isSingleAttr() {
		return true;
	}


}
