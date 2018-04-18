package com.redxun.ui.grid;

import java.util.Map;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;

public class GdMiniCheckboxHandler implements GridColEditParseHandler{

	@Override
	public String getPluginName() {
		return "mini-checkbox";
	}

	@Override
	public Element parse(Element columnEl, JSONObject elJson, Map<String, Object> params) {
		//type="checkboxcolumn" field="married" trueValue="1" falseValue="0"
		String trueValue=elJson.getString("truevalue");
		String falseValue=elJson.getString("falsevalue");
		if(StringUtil.isEmpty(trueValue)){
			trueValue="true";
		}
		if(StringUtil.isEmpty(falseValue)){
			falseValue="false";
		}
		
		columnEl.attr("type","checkboxcolumn");
		columnEl.attr("trueValue",trueValue);
		columnEl.attr("falseValue",falseValue);
		return null;
	}
}
