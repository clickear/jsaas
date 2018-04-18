package com.redxun.sys.bo.manager.parse.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;

public class DeptAttrParse extends AbstractBoAttrParse{
	
	@Resource
	OsGroupManager osGroupManager;

	@Override
	public String getPluginName() {
		return "mini-dep";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field,el);
		parseExtJson(field,el);
	}
	
	private void parseExtJson(SysBoAttr field, Element el){
		String initlogindep=el.attr("initlogindep");
		String single=el.attr("single");
		
		JSONObject json=new JSONObject();
		if("true".equals(initlogindep)){
			json.put("init", "true");
			String level=el.attr("level");
			//等级
			if(StringUtil.isNotEmpty(level)){
				json.put("level", level);
			}
		}
		
		json.put("single", single);
		String required=el.attr("required");
		if(StringUtil.isEmpty(required)){
			required="false";
		}
		json.put("required",required);
		field.setExtJson(json.toJSONString());
	}
	
	@Override
	public JSONObject getInitData(SysBoAttr attr) {
		JSONObject jsonObj= getInitDep(attr);
		return jsonObj;
	}
	
	private static JSONObject getInitDep(SysBoAttr attr){
		String initlogindep=attr.getPropByName("init");
		Integer level=attr.getIntPropByName("level");
		
		if(!"true".equals(initlogindep)) return null;
		
		OsGroupManager osGroupManager=WebAppUtil.getBean(OsGroupManager.class);
		
		String groupId=ContextUtil.getCurrentUser().getMainGroupId();
		if(StringUtil.isEmpty(groupId)) return null;
		OsGroup group=osGroupManager.get(groupId);
		JSONObject obj=new JSONObject();
		if(level.equals(-1) || group.getRankLevel().equals(level)){
			AttrParseUtil.addKey(obj, groupId);
			AttrParseUtil.addName(obj, group.getName());
		}
		else{
			group=osGroupManager.getByLevel(group, level);
			if(group!=null){
				AttrParseUtil.addKey(obj, group.getGroupId());
				AttrParseUtil.addName(obj, group.getName());
			}
		}
		if(obj.keySet().size()==0) return null;
		return obj;
	}
	
	
	
	@Override
	public boolean isSingleAttr() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return "部门选择";
	}


}
