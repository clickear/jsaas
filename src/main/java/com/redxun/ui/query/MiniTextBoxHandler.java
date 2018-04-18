package com.redxun.ui.query;

import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;

public class MiniTextBoxHandler implements QueryControlParseHandler{

	@Override
	public String getPluginName() {
		return "mini-textbox";
	}

	@Override
	public Element parse(JSONObject elJson,Map<String,Object> params) {
		Element el=new Element(Tag.valueOf("INPUT"),"");
		el.attr("class",getPluginName());
		String name=elJson.getString("fieldName");
		String fieldOp=elJson.getString("fieldOp");
		String queryDataType=elJson.getString("queryDataType");
		String autoFilter=elJson.getString("autoFilter");
		
		if("NO".equals(autoFilter)){
			el.attr("name",name);
		}else{
			el.attr("name","Q_"+name+"_"+queryDataType+"_"+fieldOp);
		}
		String emptytext=elJson.getString("emptytext");
		el.attr("onenter","onSearch()");
		if(StringUtil.isNotEmpty(emptytext)){
			el.attr("emptytext",emptytext);
		}
		return el;
	}

	@Override
	public Element parseReportEl(JSONObject elJson) {
		Element el=new Element(Tag.valueOf("INPUT"),"");
		el.attr("class",getPluginName());
		String name=elJson.getString("fieldName");
		el.attr("name",name);
		String emptytext=elJson.getString("emptytext");
		if(StringUtil.isNotEmpty(emptytext)){
			el.attr("emptytext",emptytext);
		}
		return el;
	}

}
