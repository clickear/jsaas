package com.redxun.wx.util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.cache.CacheUtil;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.wx.ent.util.ApiUrl;
import com.redxun.wx.pub.util.PublicApiUrl;

/**
 * token工具。
 * @author ray
 *
 */
public class TokenUtil {
	 
	
	private static String WEIXIN_TOKEN_MAP="wexin_token_map_";
	
	private static String WEIXIN_JSAPI_MAP="wexin_jsapi_map_";
	
	/**
	 * 返回tokenmodel。
	 * @param corpId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static TokenModel getToken(String corpId,String secret,boolean isEnt) throws Exception{
		String key=corpId  +"_" + secret;
		Map<String,TokenModel> tokenMap= (Map<String, TokenModel>) CacheUtil.getCache(WEIXIN_TOKEN_MAP);//获取微信的token缓存maps
		if(tokenMap==null){
			tokenMap=new HashMap<String, TokenModel>();
			TokenModel model=getTokenFromApi(corpId,secret,isEnt);
			if(model.getCode()!=0){
				throw new RuntimeException("获取token异常:" + model.getMsg());
			}
			tokenMap.put(key,model);
			CacheUtil.addCache(WEIXIN_TOKEN_MAP, tokenMap);
			return model;
		}else {
			TokenModel model=tokenMap.get(key);
			if(model==null||model.isExpire()){//如果没有缓存或者缓存过期
				model=getTokenFromApi(corpId,secret,isEnt);
				tokenMap.put(key, model);
				CacheUtil.addCache(WEIXIN_TOKEN_MAP, tokenMap);
			}
			if(model.getCode()!=0){
				throw new RuntimeException("获取token异常:" + model.getMsg());
			}
			return model;
		}
		
	
	}
	
	/**
	 * 获取企业token。
	 * @param corpId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static TokenModel getEntToken(String corpId,String secret) throws Exception{
		return getToken( corpId, secret, true);
	}
	
	/**
	 * 获取公众号token。
	 * @param corpId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static TokenModel getPublicToken(String corpId,String secret) throws Exception{
		return getToken( corpId, secret, false);
	}
	
	
	/**
	 * 正常格式
	 * {
	 *	   "errcode":0，
	 *	   "errmsg":""，
	 *	   "access_token": "accesstoken000001",
	 *	   "expires_in": 7200
	 *	}
	 *	
	 *	出错格式
	 *	{
	 *	   "errcode":40091,
	 *	   "errmsg":"provider_secret is invalid"
	 *	}
	 * @param corpId
	 * @param secret
	 * @return
	 * @throws Exception 
	 */
	private static TokenModel getTokenFromApi(String corpId,String secret,boolean isEnt ) throws Exception{
		TokenModel token=new TokenModel();
		token.setCorpId(corpId);
		token.setSecret(secret);
		
		String url="";
		if(isEnt){
			url=ApiUrl.getTokenUrl(corpId, secret);
		}
		else{
			url=PublicApiUrl.getAccessTokenUrl(corpId, secret);
		}
		
		
		String json= HttpClientUtil.getFromUrl(url, null);
		if(StringUtil.isEmpty(json)){
			token.setCode(-1);
			token.setMsg("获取token出错了,请检查企业ID和密钥是否有正确!");
			return token;
		}
		JSONObject jsonObj=JSONObject.parseObject(json);
		
		int code=jsonObj.getIntValue("errcode");
		String errmsg=jsonObj.getString("errmsg");
		
		if(code==0){
			String accessToken=jsonObj.getString("access_token");
			token.setCode(code);
			token.setMsg(errmsg);
			token.setLastUpdateTime(new Date());
			token.setToken(accessToken);
		}
		else{
			token.setCode(code);
			token.setMsg(errmsg);
		}
		return token;
		
	}
	
	
	
	
	private static TokenModel getJsTicketFromApi(String corpId,String secret,boolean isEnt ) throws Exception{
		TokenModel accessTokenModel=TokenUtil.getToken(corpId, secret, isEnt);
		if(accessTokenModel.getCode()!=0) return null;
			
		
		TokenModel token=new TokenModel();
		token.setCorpId(corpId);
		token.setSecret(secret);
		
		String url="";
		if(isEnt){
			url=ApiUrl.getJsApiUrl(accessTokenModel.getToken());
		}
		else{
			url=PublicApiUrl.getJsApiUrl(accessTokenModel.getToken());
		}
		
		String json= HttpClientUtil.getFromUrl(url, null);
		if(StringUtil.isEmpty(json)){
			token.setCode(-1);
			token.setMsg("获取token出错了,请检查APPID和密钥是否有正确!");
			return token;
		}
		JSONObject jsonObj=JSONObject.parseObject(json);
		
		int code=jsonObj.getIntValue("errcode");
		String errmsg=jsonObj.getString("errmsg");
		
		if(code==0){
			String accessToken=jsonObj.getString("ticket");
			token.setCode(code);
			token.setMsg(errmsg);
			token.setLastUpdateTime(new Date());
			token.setToken(accessToken);
		}
		else{
			token.setCode(code);
			token.setMsg(errmsg);
		}
		return token;
		
	}
	
	
	/**
	 * 获取微信 js ticket。
	 * @param corpId	应用ID
	 * @param secret	密钥
	 * @param isEnt		是否企业微信
	 * @return
	 * @throws Exception
	 */
	public static TokenModel getJsApiToken(String corpId,String secret,boolean isEnt) throws Exception{
		String key=corpId  +"_" + secret;
		Map<String,TokenModel> tokenMap= (Map<String, TokenModel>) CacheUtil.getCache(WEIXIN_JSAPI_MAP);//获取微信的token缓存maps
		if(tokenMap==null){
			tokenMap=new HashMap<String, TokenModel>();
			TokenModel model=getJsTicketFromApi(corpId,secret,isEnt);
			if(model.getCode()!=0){
				throw new RuntimeException("获取token异常:" + model.getMsg());
			}
			tokenMap.put(key,model);
			CacheUtil.addCache(WEIXIN_JSAPI_MAP, tokenMap);
			return model;
		}else {
			TokenModel model=tokenMap.get(key);
			if(model==null||model.isExpire()){//如果没有缓存或者缓存过期
				model=getJsTicketFromApi(corpId,secret,isEnt);
				tokenMap.put(key, model);
				CacheUtil.addCache(WEIXIN_JSAPI_MAP, tokenMap);
			}
			if(model.getCode()!=0){
				throw new RuntimeException("获取JS TICKET异常:" + model.getMsg());
			}
			return model;
		}
	}

	/**
	 * 获取企业号ticket
	 * @param corpId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static TokenModel getEntTicket(String corpId,String secret) throws Exception{
		return getJsApiToken( corpId, secret, true);
	}
	
	/**
	 * 获取公众号ticket
	 * @param corpId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static TokenModel getPuclicTicket(String corpId,String secret) throws Exception{
		return getJsApiToken( corpId, secret, false);
	}
	
}
