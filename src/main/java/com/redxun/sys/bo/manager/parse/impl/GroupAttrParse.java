package com.redxun.sys.bo.manager.parse.impl;

import java.util.List;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.bo.entity.SysBoAttr;
import com.redxun.sys.bo.manager.parse.AbstractBoAttrParse;
import com.redxun.sys.bo.manager.parse.ParseUtil;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRankTypeManager;

public class GroupAttrParse extends AbstractBoAttrParse {

	@Override
	public String getPluginName() {
		return "mini-group";
	}

	@Override
	protected void parseExt(SysBoAttr field, Element el) {
		ParseUtil.setStringLen(field,el);
		
		parseExtJson( field,  el);
	

	}
	
	
	private void parseExtJson(SysBoAttr field, Element el){
		String initlogindep=el.attr("initlogingroup");
		String single=el.attr("single");
		String dimId=el.attr("dimId");
		String showDim=el.attr("showdim");
		JSONObject json=new JSONObject();
		json.put("single", single);
		if("true".equals(initlogindep)){
			json.put("init", "true");
			json.put("showdim", showDim);
			json.put("dimId", dimId);
			
			String level=el.attr("level");
			//等级
			if(StringUtil.isNotEmpty(level)){
				json.put("level", level);
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
	public boolean isSingleAttr() {
		return false;
	}

	@Override
	public String getDescription() {
		return "用户组选择";
	}
	
	@Override
	public JSONObject getInitData(SysBoAttr attr) {
		JSONObject jsonObj= getInitDep(attr);
		return jsonObj;
		
	}
	
	private  JSONObject getInitDep(SysBoAttr attr){
		OsGroupManager osGroupManager=WebAppUtil.getBean(OsGroupManager.class);
		//是否单独
		String single=attr.getPropByName("single");
		//显示维度
		String showDim=attr.getPropByName("showdim");
		//维度ID
		String dimId=attr.getPropByName("dimId");
		//等级。
		Integer level=attr.getIntPropByName("level");
		
		IUser  user=ContextUtil.getCurrentUser();
		
		JSONObject obj=new JSONObject();
		//显示
		if("true".equals(showDim)){
			String groupId=user.getMainGroupId();
			if(StringUtil.isEmpty(groupId)) return null;
			OsGroup group=osGroupManager.get(groupId);
			if(group!=null){
				if(level.equals(-1)){
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
			}

		}
		else{
			//根据维度查询。
			List<OsGroup> list=osGroupManager.getByDimIdAndUserId(dimId, user.getUserId());
			if(BeanUtil.isEmpty(list)) return null;
			//没有选择级别
			if(level.equals(-1)){
				//单选
				if("true".equals(single)){
					OsGroup group=list.get(0);
					AttrParseUtil.addKey(obj, group.getGroupId());
					AttrParseUtil.addName(obj, group.getName());
				}
				else{
					setJson(obj,list);
				}
			}
			//选择级别
			else{
				OsGroup group =osGroupManager.getByLevel(list.get(0), level);
				if(group!=null){
					AttrParseUtil.addKey(obj, group.getGroupId());
					AttrParseUtil.addName(obj, group.getName());
				}
			}
		}
		if(obj.keySet().size()==0) return null;
		return obj;
		

	}
	
	private void setJson(JSONObject json,List<OsGroup> groups){
		StringBuffer gIds=new StringBuffer();
		StringBuffer gNames=new StringBuffer();
		
		for(int i=0;i<groups.size();i++){
			OsGroup group=groups.get(i);
			if(i==0){
				gIds.append(group.getGroupId());
				gNames.append(group.getName());
			}
			else{
				gIds.append("," +group.getGroupId());
				gNames.append("," +group.getName());
			}
		}
		AttrParseUtil.addKey(json, gIds.toString());
		AttrParseUtil.addName(json, gNames.toString());
	}
	
}
