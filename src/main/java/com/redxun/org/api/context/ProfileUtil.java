package com.redxun.org.api.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.BeanUtil;

/**
 * Profile 工具类
 * @author ray
 *
 */
public class ProfileUtil {
	
	/**
	 * 获取当前人的身份信息。
	 * @return
	 */
	public static Map<String,Set<String>> getCurrentProfile(){
		Map<String,Set<String>> map=new HashMap<String, Set<String>>();
		Collection<IProfileService> list=AppBeanUtil.getBeanList(IProfileService.class);
		for(IProfileService service:list){
			Set<String> set=service.getCurrentProfile( );
			if(BeanUtil.isEmpty(set)) continue;
			map.put(service.getType(), set);
		}
		return map;
	}
	
	/**
	 * 获取profile类型列表。
	 * @return
	 */
	public static Map<String,String> getProileTypes(){
		Collection<IProfileService> list=AppBeanUtil.getBeanList(IProfileService.class);
		Map<String,String> map=new HashMap<String, String>();
		for(IProfileService service:list){
			map.put(service.getType(), service.getName());
		}
		return map;
		
	}
	
	/**
	 * 获取类型JSON.
	 * @return
	 */
	public static JSONObject getProfileTypeJson(){
		Collection<IProfileService> list=AppBeanUtil.getBeanList(IProfileService.class);
		JSONObject json=new JSONObject();
		for(IProfileService service:list){
			json.put(service.getType(), service.getName());
		}
		
		return json;
	}

}
