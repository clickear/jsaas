package com.redxun.wx.pub.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.redxun.core.cache.CacheUtil;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.wx.core.entity.WxTag;
import com.redxun.wx.core.manager.WxPubAppManager;

public class TagUtil {
	
	
	private static String WEIXIN_TAG_MAP="wexin_tag_map_";
	

	/**
	 * 通过pubId在缓存中取tags
	 * @param pubId
	 * @return
	 * @throws Exception
	 */
	public static List<WxTag> getCacheTags(String pubId) throws Exception{
		Map<String, List<WxTag>> tagMap=(Map<String, List<WxTag>>) CacheUtil.getCache(WEIXIN_TAG_MAP);
		WxPubAppManager wxPubAppManager=WebAppUtil.getBean(WxPubAppManager.class);
		String accessToken=wxPubAppManager.getAccessToken(pubId);
		List<WxTag> wxTags;
		if(tagMap==null){
			tagMap=new HashMap<String, List<WxTag>>();
			String json=getTag(accessToken);
			JSONObject jsonObject=JSONObject.fromObject(json);
			net.sf.json.JSONArray tags=(net.sf.json.JSONArray) jsonObject.get("tags");
			wxTags=(List<WxTag>) net.sf.json.JSONArray.toCollection(tags, WxTag.class);
			tagMap.put(pubId, wxTags);
			CacheUtil.addCache(WEIXIN_TAG_MAP,tagMap);
		}else{
			List<WxTag> wxUserTags=tagMap.get(pubId);
			if(wxUserTags==null){
				String json=getTag(accessToken);
				JSONObject jsonObject=JSONObject.fromObject(json);
				net.sf.json.JSONArray tags=(net.sf.json.JSONArray) jsonObject.get("tags");
				wxTags=(List<WxTag>) net.sf.json.JSONArray.toCollection(tags, WxTag.class);
				tagMap.put(pubId, wxTags);
				CacheUtil.addCache(WEIXIN_TAG_MAP,tagMap);
			}else{
				wxTags=wxUserTags;
			}
		}
		return wxTags;
	}
	
	/**
	 * 修改缓存
	 * @param pubId
	 * @param editWxTag
	 */
	public static void editCacheTag(String pubId,WxTag editWxTag){
		Map<String, List<WxTag>> tagMap=(Map<String, List<WxTag>>) CacheUtil.getCache(WEIXIN_TAG_MAP);
		List<WxTag> wxUserTags=tagMap.get(pubId);//肯定存在
		for (int i = 0; i < wxUserTags.size(); i++) {
			WxTag tag=wxUserTags.get(i);
			if(tag.getId().equals(editWxTag.getId())){//替换缓存中的wxTag
				//wxUserTags.remove(i);
				wxUserTags.set(i, editWxTag);
			}
		}
			tagMap.put(pubId, wxUserTags);
			CacheUtil.addCache(WEIXIN_TAG_MAP,tagMap);
	}
	
	/**
	 * 添加tag进缓存
	 * @param pubId
	 * @param addWxTag
	 */
	public static void addCacheTag(String pubId,WxTag addWxTag){
		Map<String, List<WxTag>> tagMap=(Map<String, List<WxTag>>) CacheUtil.getCache(WEIXIN_TAG_MAP);
		List<WxTag> wxUserTags=tagMap.get(pubId);//肯定存在
		wxUserTags.add(addWxTag);
		tagMap.put(pubId, wxUserTags);
		CacheUtil.addCache(WEIXIN_TAG_MAP,tagMap);
	}
	
	/**
	 * 删除缓存中的tag
	 * @param pubId
	 * @param deleteWxTag
	 */
	public static void deleteCacheTag(String pubId,String tagId){
		Map<String, List<WxTag>> tagMap=(Map<String, List<WxTag>>) CacheUtil.getCache(WEIXIN_TAG_MAP);
		List<WxTag> wxUserTags=tagMap.get(pubId);//肯定存在
		for (int i = 0; i <wxUserTags.size(); i++) {
			WxTag delTag=wxUserTags.get(i);
			if(delTag.getId().equals(tagId)){
				wxUserTags.remove(i);
				i--;
			}
		}
		tagMap.put(pubId, wxUserTags);
		CacheUtil.addCache(WEIXIN_TAG_MAP,tagMap);
	}
	
	
	/**
	 * 获取通过微信的api获取所有的tag
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public static String getTag(String access_token) throws Exception{
		return HttpClientUtil.getFromUrl("https://api.weixin.qq.com/cgi-bin/tags/get?access_token="+access_token, null);
	}
	
	/**
	 * 通过openId获取用户身上的tag
	 * @param openId
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	public static String getUserTag(String openId,String access_token) throws Exception{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("openid", openId);
		return HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token="+access_token, jsonObject.toString());
	}
	
	
	/**
	 * 获取通过微信的api删除指定的tag,
	 * {"tag":{"id" : 134}}
	 * @param access_token
	 * @param id 传入数字int
	 * @return
	 * @throws Exception
	 */
	public static String deleteTag(String access_token,int id) throws Exception{
		JSONObject obj=new JSONObject();
		JSONObject jsonId=new JSONObject();
		jsonId.put("id", id);
		obj.put("tag", jsonId);
		String tag=obj.toString();
		return HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/tags/delete?access_token="+access_token, tag);
	}
	
	/**
	 * 获取通过微信的api修改指定的tag
	 * {"tag" : {"id" : 134,"name" : "广东人"}}
	 * @param access_token
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public static String editTag(String access_token,int id,String name) throws Exception{
		JSONObject obj=new JSONObject();
		JSONObject jsonId=new JSONObject();
		jsonId.put("id", id);
		jsonId.put("name", name);
		obj.put("tag", jsonId);
		String tag=obj.toString();
		return HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/tags/update?access_token="+access_token, tag);
	}
	
	/**
	 * {"tag" : {"name" : "广东"}}
	 * @param access_token
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public static String addTag(String access_token,String name) throws Exception{
		JSONObject obj=new JSONObject();
		JSONObject jsonId=new JSONObject();
		jsonId.put("name", name);
		obj.put("tag", jsonId);
		String tag=obj.toString();
		return HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/tags/create?access_token="+access_token, tag);
	}
	
	/**
	 * {"openid_list" : ["ocYxcuAEy30bX0NXmGn4ypqx3tI0","ocYxcuBt0mRugKZ7tGAHPnUaOW7Y"],"tagid" : 134}
	 * 将用户批量加入tag
	 * @param access_token
	 * @param tagId
	 * @param openid_list    (逗号分隔)
	 * @return
	 * @throws Exception 
	 */
	public static String addUserIntoTag(String access_token,int tagId,String openid_list) throws Exception{
		String[] userIds=openid_list.split(",");
		
		JSONObject obj=new JSONObject();
		obj.put("openid_list", userIds);
		obj.put("tagid", tagId);
		String data=obj.toString();
		return HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token="+access_token, data);
	}
	
	/**
	 * 批量为用户取消标签
	 * @param access_token
	 * @param openIds
	 * @param tagId
	 * @return
	 * @throws Exception
	 */
	public static String cancelUsersTag(String access_token,String openIds,String tagId) throws Exception{
		String[] userIds=openIds.split(",");
		JSONObject obj=new JSONObject();
		obj.put("openid_list", userIds);
		obj.put("tagid", tagId);
		String data=obj.toString();
		return HttpClientUtil.postJson("https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token="+access_token, data);
		
	}
}
