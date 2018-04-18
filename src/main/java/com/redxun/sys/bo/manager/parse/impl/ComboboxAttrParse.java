package com.redxun.sys.bo.manager.parse.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;

import net.sf.json.JSONArray;



/**
 * 下拉框
 * @author ray
 *
 */
public class ComboboxAttrParse extends AbstractBoAttrParse {

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field, el);
		String defaultVal= el.attr("defaultvalue");
		JSONObject json=new JSONObject();
		if(StringUtil.isNotEmpty(defaultVal)){
			json.put("defaultvalue", defaultVal);
		}
		String from =el.attr("from");
		json.put("from", from);
		if("dic".equals(from)){
			String dickey=el.attr("dickey");
			json.put("dic",dickey);
		} else if("self".equals(from)){
			String data=el.attr("data").replaceAll("&quot;", "");
			JSONArray jsonArray=JSONArray.fromObject(data);
			json.put("data", jsonArray);

		} else if("sql".equals(from)||"url".equals(from)){
			String fromText=el.attr(from);
			String textfield=el.attr("textfield");
			String valuefield=el.attr("valuefield");
			String sql_params=el.attr("sql_params");
			String sql_parent=el.attr("sql_parent");
			json.put(from, fromText);
			json.put("textfield", textfield);
			json.put("valuefield", valuefield);
			json.put("sql_params", sql_params);
			if("sql".equals(from)){
				json.put("sql_params", sql_params);
				json.put("sql_parent", sql_parent);
			}
		}
		String required=el.attr("required");
		if(StringUtil.isEmpty(required)){
			required="false";
		}
		json.put("required",required);
		field.setExtJson(json.toJSONString());
	}

	@Override
	public JSONObject getInitData(SysBoAttr attr) {
		if(StringUtil.isEmpty(attr.getExtJson())) return null;
		
		JSONObject obj=JSONObject.parseObject(attr.getExtJson());
		
		JSONObject jsonObject=new JSONObject();
		
		AttrParseUtil.addKey(jsonObject, obj.getString("defaultvalue"));
		
		return jsonObject;
		
	}

	@Override
	public String getPluginName() {
		return "mini-combobox";
	}
	
	@Override
	public boolean isSingleAttr() {
		return false;
	}

	@Override
	public String getDescription() {
		return "下拉框";
	}

}
