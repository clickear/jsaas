package com.redxun.sys.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.entity.KeyValEnt;

/**
 * 上下文常量。
 * @author ray
 *
 */
public class ContextHandlerFactory {
	
	private  Map<String,ContextHandler> handlerMap=new HashMap<String, ContextHandler>();
	
	private  List<ContextHandler> handlers=new ArrayList<ContextHandler>();

	public void setHandlers(List<ContextHandler> handlers){
		for(ContextHandler handler:handlers){
			handlerMap.put(handler.getKey(), handler);
		}
		this.handlers=handlers;
	}
	
	/**
	 * 返回值。
	 * @param key
	 * @return
	 */
	public Object getValByKey(String key,Map<String,Object> vars){
		if(handlerMap.containsKey(key)){
			return handlerMap.get(key).getValue(vars);
		}
		return null;
	}
	
	/**
	 * 返回处理器。
	 * @return
	 */
	public List<KeyValEnt> getHandlers(){
		List<KeyValEnt> list=new ArrayList<KeyValEnt>();
		for(ContextHandler handler: handlers){
			list.add(new KeyValEnt(handler.getKey(), handler.getName()));
		}
		return list;
	}
	
	
	/**
	 * 获取常量定义。
	 * <pre>
	 * 	[{key:"[USERID]",val:"当前登录用户"}]
	 * </pre>
	 * @return
	 */
	public JSONArray getJsonHandlers(){
		JSONArray ary=new  JSONArray();
		for(ContextHandler handler: handlers){
			JSONObject obj=new JSONObject();
			obj.put("key", handler.getKey());
			obj.put("val", handler.getName());
			ary.add(obj);
		}
		return ary;
	}
}
