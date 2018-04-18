package com.redxun.sys.bo.manager.parse.impl;

import com.alibaba.fastjson.JSONObject;
import com.redxun.sys.org.entity.OsGroup;

public class AttrParseUtil {
	
	
	
	public static void addKey(JSONObject jsonObj,String val){
		jsonObj.put("key", val);
	}
	
	public static String getKey(JSONObject jsonObj){
		if(jsonObj==null) return "";
		if(jsonObj.containsKey("key")) {
			return jsonObj.getString("key");
		}
		return "";
		
	}
	
	public static String getName(JSONObject jsonObj){
		if(jsonObj==null) return "";
		if(jsonObj.containsKey("name")) {
			return jsonObj.getString("name");
		}
		return "";
		
	}

	public static void addName(JSONObject jsonObj,String val){
		jsonObj.put("name", val);
	}
	
	
	
	

}
