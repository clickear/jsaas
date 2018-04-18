package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;


/**
 * 复选框。
 * @author ray
 */

public class CheckBoxAttrParse extends AbstractBoAttrParse {

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field, el);
		Attributes attributes=el.attributes();
		String checked =attributes.get("checked");
		String truevalue =attributes.get("truevalue");
		String falsevalue =attributes.get("falsevalue");
		JSONObject json=new JSONObject();
		json.put("checked", checked);
		json.put("truevalue", truevalue);
		json.put("falsevalue", falsevalue);
		field.setExtJson(json.toJSONString());
	}

	@Override
	public String getPluginName() {
		return "mini-checkbox";
	}
	
	@Override
	public String getDescription() {
		return "复选框";
	}

	@Override
	public boolean isSingleAttr() {
		return true;
	}

	

}
