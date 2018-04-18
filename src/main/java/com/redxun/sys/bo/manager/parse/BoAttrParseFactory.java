package com.redxun.sys.bo.manager.parse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * 插件解析器工厂类。
 * @author ray
 *
 */
public class BoAttrParseFactory {
	
	private Map<String,IBoAttrParse> parseMap=new HashMap<String, IBoAttrParse>();
	
	private JSONObject pluginDescJson=new JSONObject();
	
	private Set<String> excludePlugins=new HashSet<String>();
	
	/**
	 * 设置属性列表。
	 * @param list
	 */
	public void setAttrParseList(List<IBoAttrParse>  list){
		for(IBoAttrParse parse:list){
			parseMap.put(parse.getPluginName(), parse);
			pluginDescJson.put(parse.getPluginName(), parse.getDescription());
		}
	}
	
	public void setExculdePlugins(Set<String> set){
		this.excludePlugins=set;
	}
	
	public JSONObject getPluginDesc(){
		return pluginDescJson;
	}
	
	
	public  IBoAttrParse getByPluginName(String pluginName){
		if(excludePlugins.contains(pluginName)) return null;
		if(parseMap.containsKey(pluginName)){
			return parseMap.get(pluginName);
		}
		
		return  parseMap.get("baseBo");
	}

}
