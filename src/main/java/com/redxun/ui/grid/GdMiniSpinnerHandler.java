package com.redxun.ui.grid;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.FastjsonUtil;

public class GdMiniSpinnerHandler implements GridColEditParseHandler{

	@Override
	public String getPluginName() {
		return "mini-spinner";
	}

	@Override
	public Element parse(Element columnEl,JSONObject elJson, Map<String, Object> params) {
		Element el=new Element(Tag.valueOf("INPUT"),"");
		el.attr("class",getPluginName());
		el.attr("property","editor");
		el.attr("value",FastjsonUtil.getString(elJson,"value",""));
		el.attr("format",FastjsonUtil.getString(elJson,"format",""));
		el.attr("increment",FastjsonUtil.getString(elJson,"increment","1"));
		el.attr("minvalue",FastjsonUtil.getString(elJson,"minvalue","0"));
		el.attr("maxvalue",FastjsonUtil.getString(elJson,"maxvalue","10000"));
		el.attr("required",FastjsonUtil.getString(elJson,"required","false"));
		return el;
	}

}
