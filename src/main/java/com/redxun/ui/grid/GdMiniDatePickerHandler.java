package com.redxun.ui.grid;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.FastjsonUtil;

public class GdMiniDatePickerHandler implements GridColEditParseHandler{

	@Override
	public String getPluginName() {
		return "mini-datepicker";
	}

	@Override
	public Element parse(Element columnEl,JSONObject elJson, Map<String, Object> params) {
		Element el=new Element(Tag.valueOf("INPUT"),"");
		el.attr("class",getPluginName());
		el.attr("property","editor");
		el.attr("value",FastjsonUtil.getString(elJson,"value",""));
		el.attr("format",FastjsonUtil.getString(elJson,"format","yyyy-MM-dd"));
		el.attr("required",FastjsonUtil.getString(elJson,"required",""));
		return el;
	}
	
}
