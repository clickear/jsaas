package com.redxun.wx.pub.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.redxun.core.util.HttpClientUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.wx.core.entity.WxMeterial;
import com.redxun.wx.core.manager.WxMeterialManager;
import com.redxun.wx.ent.util.ApiUrl;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;

public class PublicApi {
	
	/**
	 * 取得用户列表。
	 * @param appId
	 * @param secret
	 * @return
	 * 
	 * {
		  "total": 1,
		  "count": 1,
		  "data": {
		    "openid": [
		      "o1fn5ssVpGkEG2FvRueMSZYSEbh0"
		    ]
		  },
		  "next_openid": "o1fn5ssVpGkEG2FvRueMSZYSEbh0"
		}
	 * @throws Exception 
	 */
	public static String getUsers(String token,String next_openid) throws Exception{
		
		String url=PublicApiUrl.getUsersUrl(token,next_openid);
		String json= HttpClientUtil.getFromUrl(url, null);
		return json;
	}
	
	/**
	 * 获取用户列表。
	 * @param appId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static String getUsers(String token) throws Exception{
		return getUsers(token,"");
	}
	
	/**
	 * {
		   "subscribe": 1, 
		   "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
		   "nickname": "Band", 
		   "sex": 1, 
		   "language": "zh_CN", 
		   "city": "广州", 
		   "province": "广东", 
		   "country": "中国", 
		   "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
		eMsv84eavHiaiceqxibJxCfHe/0",
		  "subscribe_time": 1382694957,
		  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
		  "remark": "",
		  "groupid": 0,
		  "tagid_list":[128,2]
		}
	 * @param token
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public static String getUser(String token,String openId) throws Exception{
		String url=PublicApiUrl.getUserUrl(token,openId);
		String json= HttpClientUtil.getFromUrl(url, null);
		return json;
	}

	
	public static String uploadArticleImg(String folder,String fileName, String token) throws IOException{
		String url= PublicApiUrl.getUploadImgUrl(token);
		Map<String,String> fileMap=new HashMap<String,String>();
		fileMap.put("media", fileName);
		String rtn= HttpClientUtil.uploadFile(url, folder, fileMap);
		return rtn;
		
	}
	
	/**
	 * 上传其他类型永久素材
	 * @param folder
	 * @param fileName
	 * @param token
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static String uploadOtherMeterial(String folder,String fileName,String token,String type) throws IOException{
		String url=PublicApiUrl.getUploadMeterial(token, type);
		Map<String,String> fileMap=new HashMap<String,String>();
		
		String rtn;
		if("video".equals(type)){
			Map<String,String> txtMap=new HashMap<String, String>();
			txtMap.put("description", "{\"title\":\"暂无标题\",introduction:\"暂无视频\"}");
			fileMap.put("media", folder+File.separator+fileName);
			rtn=HttpClientUtil.uploadFile(url, txtMap, fileMap);
		}else{
			fileMap.put("media", fileName);
			rtn= HttpClientUtil.uploadFile(url, folder, fileMap);
		}
		return rtn;
	}
	
	/**
	 * 上传其他类型临时素材
	 * @param folder
	 * @param fileName
	 * @param token
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static String uploadOtherTempMeterial(String folder,String fileName,String token,String type) throws IOException{
		String url=PublicApiUrl.getUploadTemporaryMeterial(token, type);
		Map<String,String> fileMap=new HashMap<String,String>();
		fileMap.put("media", fileName);
		String rtn= HttpClientUtil.uploadFile(url, folder, fileMap);
		return rtn;
	}
	/**
	 * 通过tagId群发消息
	 * @param tagId
	 * @param accessToken
	 * @param isToAll
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	public static String sendMsgToTag(int tagId,String accessToken,Boolean isToAll,String mediaId,String msgType) throws Exception{
		String url=PublicApiUrl.getSendMsgByTag(accessToken);
		JSONObject jsonParams=new JSONObject();
		JSONObject filter=new JSONObject();
		filter.put("is_to_all", isToAll);
		filter.put("tag_id", tagId);
		jsonParams.put("filter",filter);
		JSONObject mpnews=new JSONObject();
		if(msgType.equals("article")||msgType.equals("multiArticle")){
			msgType="mpnews";
			jsonParams.put( "send_ignore_reprint", 0);
		}else if(msgType.equals("video")){
			String videoUrl=PublicApiUrl.getUploadVideoUrl(accessToken);
			mediaId=translateVideo(mediaId, accessToken, msgType);
			
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("media_id", mediaId);
			jsonObject.put("title", "暂缺标题");
			jsonObject.put("description", "暂无描述");
			String rtnCode=HttpClientUtil.postJson(videoUrl, jsonObject.toString());
			JSONObject videoRtnObject=JSONObject.fromObject(rtnCode);
			mediaId=videoRtnObject.getString("media_id");
			msgType="mpvideo";
		}
		mpnews.put("media_id", mediaId);
		jsonParams.put(msgType, mpnews);	
		jsonParams.put("msgtype",msgType);
		
		String rtnString=HttpClientUtil.postJson(url, jsonParams.toString());
		return rtnString;
	}
	
	/**
	 * 将素材中的video上传到临时素材(基础支持中的上传下载video)得到临时的mediaId
	 * @param mediaId
	 * @param accessToken
	 * @param msgType
	 * @return
	 * @throws Exception
	 */
	public static String translateVideo(String mediaId,String accessToken,String msgType) throws Exception{
	//	String translateUrl=PublicApiUrl.getUploadTemporaryMeterial(accessToken, msgType);//中转链接
		WxMeterialManager wxMeterialManager=WebAppUtil.getBean(WxMeterialManager.class);
		WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
		JSONObject jsonObject=JSONObject.fromObject(wxMeterial.getArtConfig());
		String fileId=jsonObject.getString("fileUpload");
		SysFileManager sysFileManager=WebAppUtil.getBean(SysFileManager.class);
		SysFile sysFile=sysFileManager.get(fileId);
		String folder=WebAppUtil.getUploadPath() + "/" + sysFile.getPath();
		String[] array=folder.split("/");
		String fileName=sysFile.getNewFname();
		int num=folder.indexOf(fileName);
		folder=folder.substring(0, num-1);
		
		String rtnCode=uploadOtherTempMeterial(folder, fileName,accessToken, msgType);
		JSONObject rtnObj=JSONObject.fromObject(rtnCode);
		return rtnObj.getString("media_id");
	}
	
	/**
	 * 给 指定的openId们群发消息
	 * @param openIds(数组长度必须大于2)
	 * @param msgType
	 * @param mediaId 如果为text则存放content,如果为卡券则存cardId
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static String sendMsgToOpenId(List<String> openIds,String msgType,String mediaId,String accessToken) throws Exception{
		String url=PublicApiUrl.getSendMsgByOpenId(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("touser", openIds);
		JSONObject media=new JSONObject();
		if("text".equals(msgType)){//普通文本
			media.put("content", media);
		}else if("wxcard".equals(msgType)){//微信卡券
			media.put("card_id", media);
		}else if("article".equals(msgType)||"multiArticle".equals(msgType)){//图文消息
			media.put("media_id", mediaId);
			msgType="mpnews";
			jsonObject.put("send_ignore_reprint", 0); 
		}else if("video".equals(msgType)){//其他
			String videoUrl=PublicApiUrl.getUploadVideoUrl(accessToken);
			mediaId=translateVideo(mediaId, accessToken, msgType);
			
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("media_id", mediaId);
			jsonObj.put("title", "暂缺标题");
			jsonObj.put("description", "暂无描述");
			String rtnCode=HttpClientUtil.postJson(videoUrl, jsonObj.toString());
			JSONObject videoRtnObject=JSONObject.fromObject(rtnCode);
			mediaId=videoRtnObject.getString("media_id");
			msgType="mpvideo";
			media.put("media_id", mediaId);
			
		}else if("image".equals(msgType)){
			media.put("media_id", mediaId);
		}
		jsonObject.put("msgtype", msgType);
		jsonObject.put(msgType, media);
		return HttpClientUtil.postJson(url, jsonObject.toString());
	}
	
	/**
	 * 通过openId预览发送的消息
	 * {
   "touser":"OPENID", 
   "mpnews":{              
            "media_id":"123dsdajkasd231jhksad"               
             },
   "msgtype":"mpnews" 
		}
	 * @param openId
	 * @param accessToken
	 * @param mediaId
	 * @param msgType
	 * @return
	 * @throws Exception
	 */
	public static String previewMsg(String openId,String accessToken,String mediaId,String msgType) throws Exception{
		String url=PublicApiUrl.getPreviewMsgUrl(accessToken);
		JSONObject paramObj=new JSONObject();
		paramObj.put("touser", openId);
		JSONObject msgJson=new JSONObject();
		msgJson.put("media_id", mediaId);
		paramObj.put(msgType, msgJson);
		paramObj.put("msgtype", msgType);
		return HttpClientUtil.postJson(url, paramObj.toString());
	}
	
	/**
	 * 获取消息发送状态
	 * @param accessToken
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	public static String getMsgState(String accessToken,String msgId) throws Exception{
		String url=PublicApiUrl.getSendMsgReturnState(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("msg_id", msgId);
		return HttpClientUtil.postJson(url, jsonObject.toString());
	}
	
	/**
	 * 更新素材
	 * @param access_token
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String updateMeterial(String access_token,com.alibaba.fastjson.JSONObject obj) throws Exception{
		return HttpClientUtil.postJson(PublicApiUrl.getUpdateMeterial(access_token), obj.toString());
	}
	
	/**
	 * 创建菜单
	 * @param accessToken
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String createMenu(String accessToken,String menus) throws Exception{
		return HttpClientUtil.postJson(PublicApiUrl.getCreateMenuUrl(accessToken), menus);
	}
	
	/**
	 * 查询微信菜单
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static String getMenus(String accessToken) throws Exception{
		return HttpClientUtil.getFromUrl(PublicApiUrl.getFindWxMenus(accessToken), null);
	}
	
	/**
	 * 删除微信后端的永久素材
	 * @param mediaId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static  String deleteMeterial(String mediaId,String accessToken) throws Exception{
		String url=PublicApiUrl.getDeleteMeterial(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("media_id", mediaId);
		String rtnCode=HttpClientUtil.postJson(url, jsonObject.toString());
		return rtnCode;
	}
	
	/**
	 * 将mediaIds拆分成多个mediaId并挨个获取他们的article配置
	 * 将其整合成一个article(含多个)上传到后台获取唯一的mediaId返回
	 * @param mediaIds
	 * @param pubId
	 * @return
	 * @throws Exception 
	 */
	public static String transformMediaIdsIntoMediaId(String mediaIds,String accessToken,String pubId) throws Exception{
		WxMeterialManager wxMeterialManager=WebAppUtil.getBean(WxMeterialManager.class);
		String[] mediaIdArray=mediaIds.split(",");
		if(mediaIdArray.length==1){
			return mediaIdArray[0];
		}
		JSONObject jsonObject=new JSONObject();
		List<JSONObject> articles=new ArrayList<JSONObject>();
		for (int i = 0; i < mediaIdArray.length; i++) {
			String mediaId=mediaIdArray[i];
			WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
			JSONObject object=JSONObject.fromObject(wxMeterial.getArtConfig());//(JSONObject) JSONObject.parse(wxMeterial.getArtConfig());
			net.sf.json.JSONArray singleArray=object.getJSONArray("articles");
			JSONObject singleArticle=(JSONObject) singleArray.get(0);
			articles.add(singleArticle);
		}
		jsonObject.put("articles", articles);
		String rtnCode=HttpClientUtil.postJson(PublicApiUrl.getForeverMaterial(accessToken), jsonObject.toString());
		com.alibaba.fastjson.JSONObject rtnJson=(com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(rtnCode);
		Integer erroCode=rtnJson.getInteger("errcode");
		if(erroCode!=null){
			WxCode wxCode=new WxCode();
			String erroMsg=wxCode.getTheCode(erroCode);
			if(erroCode!=0){
				throw new RuntimeException(erroMsg);
			}
		}
		String rtnMediaId=rtnJson.getString("media_id");
		WxMeterial wxMeterial=new WxMeterial();
		wxMeterial.setMediaType("multiArticle");//多图文
		wxMeterial.setId(IdUtil.getId());
		wxMeterial.setTermType("1");
		wxMeterial.setPubId(pubId);
		wxMeterial.setMediaId(rtnMediaId);
		wxMeterial.setName("多素材");
		wxMeterial.setTenantId(ContextUtil.getCurrentTenantId());
		wxMeterial.setCreateTime(new Date());
		wxMeterial.setArtConfig(mediaIds);
		wxMeterialManager.create(wxMeterial);
		return rtnMediaId;
	}
	
	/**
	 * get the  wechat meterial massage
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	public static String getMeterial(String accessToken,String mediaId) throws Exception{
		String url=PublicApiUrl.getForeverMeterial(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("media_id", mediaId);
		String rtnCode=HttpClientUtil.postJson(url, jsonObject.toString());
		return rtnCode;
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * @param appId
	 * @param secret
	 * @param code
	 * @return {"access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN","openid":"OPENID","scope":"SCOPE"} 
	 * error {"errcode":40029,"errmsg":"invalid code"} 
	 * @throws Exception
	 */
	public static String getWebAccessToken(String appId,String secret,String code) throws Exception{
		String url=PublicApiUrl.getWebAccessTokenUrl(appId, secret, code);
		String rtnCode=HttpClientUtil.getFromUrl(url, null);
		return rtnCode;
	}
	
	/**
	 * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新
	 * refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
	 * @param appId
	 * @param refreshToken
	 * @return { "access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN","openid":"OPENID","scope":"SCOPE" } 
	 * @throws Exception
	 */
	public static String refreshWebAccessToken(String appId,String refreshToken) throws Exception{
		String url=PublicApiUrl.getRefreshAccessTokenUrl(appId, refreshToken);
		String rtnCode=HttpClientUtil.getFromUrl(url, null);
		return rtnCode;
	}
	
	/**
	 * 通过授权ACCESSTOKEN以及OpenId获取用户详细信息
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public static String getUserInfoByWebAccessToken(String accessToken,String openId) throws Exception{
		String url=PublicApiUrl.getUserMsgUrlByWebAccessToken(accessToken, openId);
		String rtnCode=HttpClientUtil.getFromUrl(url, null);
		return rtnCode;
	}
	
	/**
	 * 创建卡券接口
	 * @param accessToken
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String createCard(String accessToken,String json) throws Exception{
		String url=PublicApiUrl.getCreateCardUrl(accessToken);
		String rtnCode=HttpClientUtil.postJson(url, json);
		return rtnCode;
	}
	
	/**
	 * 创建投放卡券的二维码
	 * @param accessToken
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String putInWxTicket(String accessToken,String json) throws Exception{
		String url=PublicApiUrl.getCreateWxTicketQrCodeUrl(accessToken);
		String rtnCode=HttpClientUtil.postJson(url, json);
		return rtnCode;
	}
	
	/**
	 * 创建卡券货架
	 * @param accessToken
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String createLandingPage(String accessToken,String json) throws Exception{
		String url=PublicApiUrl.getCreateLandingPage(accessToken);
		String rtnCode=HttpClientUtil.postJson(url, json);
		return rtnCode;
	}
	/**
	 * 删除微信卡券
	 * @param accessToken
	 * @param cardId
	 * @return
	 * @throws Exception
	 */
	public static String deleteWxTiket(String accessToken,String cardId) throws Exception{
		String url=PublicApiUrl.getDeleteWxTicketUrl(accessToken);
		JSONObject jsonParams=new JSONObject();
		jsonParams.put("card_id", cardId);
		String rtnCode=HttpClientUtil.postJson(url, jsonParams.toString());
		return rtnCode;
	}
	/**
	 * 核销卡券
	 * @param accessToken
	 * @param jsonParams
	 * @return
	 * @throws Exception
	 */
	public static String consumeWxTicket(String accessToken,String code) throws Exception{
		String url=PublicApiUrl.getConsumeWxTicket(accessToken);
		JSONObject jsonParams=new JSONObject();
		jsonParams.put("code", code);
		String rtnCode=HttpClientUtil.postJson(url, jsonParams.toString());
		return rtnCode;
	}
	/**
	 * 修改卡券部分信息
	 * @param accessToken
	 * @param jsonParams
	 * @return
	 * @throws Exception
	 */
	public static String updateWxTicket(String accessToken,String jsonParams) throws Exception{
		String url=PublicApiUrl.getUpdateWxTicket(accessToken);
		String rtnCode=HttpClientUtil.postJson(url, jsonParams);
		return rtnCode;
	}
	/**
	 * 拉取商户卡券领取详情
	 * ｛"begin_date":"2015-06-15","end_date":"2015-06-30","cond_source":  0｝
	 * @param accessToken
	 * @param jsonParams
	 * @return
	 * {"list":[{"ref_date":"2015-06-23","view_cnt":1,"view_user":1,"receive_cnt":1,"receive_user":1,"verify_cnt":0,"verify_user":0,"given_cnt":0,"given_user":0,"expire_cnt":0,"expire_user":0}]}
	 * @throws Exception
	 */
	public static String getBrandTicketInfo(String accessToken,String jsonParams) throws Exception{
		String url=PublicApiUrl.getBrandTicketInfoUrl(accessToken);
		String rtnCode=HttpClientUtil.postJson(url, jsonParams);
		return rtnCode;
	}
	
	/**
	 * 获取免费券领取详情,优惠券、团购券、折扣券、礼品券
	 * @param accessToken
	 * @param jsonParams
	 * @return
	 * {"list":[{"ref_date":"2015-06-23","view_cnt":1,"view_user":1,"receive_cnt":1,"receive_user":1,"verify_cnt":0,"verify_user":0,"given_cnt":0,"given_user":0,"expire_cnt":0,"expire_user":0}]}
	 * @throws Exception
	 */
	public static String getFreeTicketInfo(String accessToken,String jsonParams) throws Exception{
		String url=PublicApiUrl.getFreeTicketInfoUrl(accessToken);
		String rtnCode=HttpClientUtil.postJson(url, jsonParams);
		return rtnCode;
		}
	
	/**
	 * 设置卡券失效
	 * @param accessToken
	 * @param cardId
	 * @return
	 * @throws Exception
	 */
	public static String cancelTicket(String accessToken,String code) throws Exception{
		String url=PublicApiUrl.getCancelTicketUrl(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code", code);
		String rtnCode=HttpClientUtil.postJson(url, jsonObject.toString());
		return rtnCode;
	}
	
	/**
	 * 查询卡券code核销情况
	 * @param accessToken
	 * @param code
	 * @return
	 * {
    "errcode": 0,
    "errmsg": "ok",
    "card": {
        "card_id": "pbLatjk4T4Hx-QFQGL4zGQy27_Qg",
        "begin_time": 1457452800,
        "end_time": 1463155199
    },
    "openid": "obLatjm43RA5C6QfMO5szKYnT3dM",
    "can_consume": true,
    "user_card_status": "NORMAL"
}
	 * @throws Exception
	 */
	public static String checkTicketCode(String accessToken,String code) throws Exception{
		String url=PublicApiUrl.getCheckTicketCodeUrl(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("check_consume", true);//检验核销状况
		String rtnCode=HttpClientUtil.postJson(url, jsonObject.toString());
		return rtnCode;
	}
	
	/**
	 * 修改库存
	 * @param accessToken
	 * @param cardId
	 * @param increase 可为0
	 * @param reduce  可为0
	 * @return
	 * @throws Exception
	 */
	public static String modifyTicketCount(String accessToken,String cardId,int change) throws Exception{
		String url=PublicApiUrl.getModifyCountUrl(accessToken);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("card_id", cardId);
		if(change>0){
			jsonObject.put("increase_stock_value", change);
		}else if(change<0){
			jsonObject.put("reduce_stock_value", change*(-1));
		}
		String rtnCode=HttpClientUtil.postJson(url, jsonObject.toString());
		return rtnCode;
	}
	
	public static void main(String[] args) throws Exception {
//		String users=getUsers("wx202fd79902565c28", "d1be868f6855a19bd1c9280d6b2df831");
//		System.out.println(users);
		TokenModel model=TokenUtil.getPublicToken("wxc0bea11e75d0eac4", "288f38e362355a4de7ecdd27b024b9fb");
		
		String json=sendMsgToTag(100, model.getToken(),false,"rs4T8TM_kEFRVXKi8amYuJd9y01DhU7cOfgdWv9ZU-w","mpnews");
		//String json=uploadArticleImg("C:\\Users\\Administrator\\Pictures","2.png",model.getToken());
		
		//String json=previewMsg("oUziL006_soL-5gfbKdISh0k9HYk", model.getToken(), "zRpglMKsQxCNfalnXkqD4nU-fJLBe8xs90uH70XUBhk", "mpnews");
		
//		String json=getUser(model.getToken(), "o1fn5ssVpGkEG2FvRueMSZYSEbh0");
		System.out.println(json);
	}

}
