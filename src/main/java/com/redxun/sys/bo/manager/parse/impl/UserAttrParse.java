package com.redxun.sys.bo.manager.parse.impl;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;

public class UserAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-user";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field,el);
		
		parseExtJson(field,el);
		
	}
	
	private void parseExtJson(SysBoAttr field, Element el){
		String initloginuser=el.attr("initloginuser");
		String single=el.attr("single");//单选多选
		JSONObject json=new JSONObject();
		json.put("single", single);
		if("true".equals(initloginuser)){
			json.put("initloginuser", "true");
		}
		String required=el.attr("required");
		if(StringUtil.isEmpty(required)){
			required="false";
		}
		json.put("required",required);
		field.setExtJson(json.toJSONString());
	}

	
	@Override
	public boolean isSingleAttr() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return "用户选择";
	}
	
	@Override
	public JSONObject getInitData(SysBoAttr attr) {
		String initloginuser=attr.getPropByName("initloginuser");
		
		if("true".equals(initloginuser)){
			JSONObject obj=new JSONObject();
			IUser curUser=ContextUtil.getCurrentUser();
			
			AttrParseUtil.addKey(obj, curUser.getUserId());
			AttrParseUtil.addName(obj, curUser.getFullname());
		
			return obj;
		}
		return null;
	}
}
