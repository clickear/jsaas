package com.redxun.ui.query;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.view.consts.FormViewConsts;
import com.redxun.core.util.StringUtil;

public class MiniDialogHandler implements QueryControlParseHandler{

	@Override
	public String getPluginName() {
		return "mini-dialog";
	}

	/**
	 * "fc": "mini-dialog",
	"_id": 22,
	"_uid": 22,
	"_state": "added",
	"fieldLabel": "会议室",
	"dataType": "STRING",
	"queryDataType": "S",
	"fieldName": "f_hycyr_name",
	"fieldOpLabel": "%模糊匹配%",
	"fieldOp": "LK",
	"fcName": "自定义对话框",
	"dialogalias": "sysCustomFormSetting",
	"dialogname": "自定义表单方案",
	"valueField": "NAME_",
	"textField": "ALIAS_",
	"dialogName": "自定义表单方案"
	 */
	@Override
	public Element parse(JSONObject elJson,Map<String,Object> params) {
		String valueField=elJson.getString("valueField");
		String textField=elJson.getString("textField");
		String dialogalias=elJson.getString("dialogalias");
		String dialogname=elJson.getString("dialogname");
		Element el=new Element(Tag.valueOf("input"),"");
		el.attr("class","mini-buttonedit");
		JSONObject json=new JSONObject();
		json.put("valueField", valueField);
		json.put("textField",textField);
		json.put("dialogalias",dialogalias);
		json.put("dialogname", dialogname);
		el.attr("data-options",json.toJSONString());
		el.attr("onbuttonclick","_OnMiniDialogShow");//定义在share.js中
		
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
		String valueField=elJson.getString("valueField");
		String textField=elJson.getString("textField");
		String dialogalias=elJson.getString("dialogalias");
		String dialogname=elJson.getString("dialogname");
		Element el=new Element(Tag.valueOf("input"),"");
		el.attr("class","mini-buttonedit");
		JSONObject json=new JSONObject();
		json.put("valueField", valueField);
		json.put("textField",textField);
		json.put("dialogalias",dialogalias);
		json.put("dialogname", dialogname);
		el.attr("data-options",json.toJSONString());
		el.attr("onbuttonclick","_OnMiniDialogShow");//定义在share.js中
		
		String name=elJson.getString("fieldName");
		el.attr("name",name);
		
		String emptytext=elJson.getString("emptytext");
		if(StringUtil.isNotEmpty(emptytext)){
			el.attr("emptytext",emptytext);
		}
		
		return el;
	}

}
