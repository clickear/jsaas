package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;

public class ButtonEditAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-buttonedit";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field,el);
	
		JSONObject jo=new JSONObject();
		String json=el.attr("data-options");
		if(StringUtil.isNotEmpty(json)){
			jo=JSONObject.parseObject(json);
		}
		String required=el.attr("required");
		if(StringUtil.isEmpty(required)){
			required="false";
		}
		jo.put("required",required);
		field.setExtJson(jo.toString());
	}

	@Override
	public boolean isSingleAttr() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return "按钮选择框";
	}
	

}
