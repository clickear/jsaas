package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;

public class NumberAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-spinner";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		field.setDataType(Column.COLUMN_TYPE_NUMBER );
		field.setLength(14);
		field.setDecimalLength(4);
		
		JSONObject json=new JSONObject();
		
		String dataOptions=el.attr("data-options");
		if(StringUtil.isNotEmpty(dataOptions)){
			json.put("conf", dataOptions);
		}
		
		String required=el.attr("required");
		if(StringUtil.isNotEmpty(required)){
			json.put("required",required);
		}
		
		if(json.size()>1){
			String jsonStr=json.toJSONString();
			field.setExtJson(jsonStr);	
		}
	}

	@Override
	public String getDescription() {
		return "数字控件";
	}
	
	@Override
	public boolean isSingleAttr() {
		return true;
	}

}
