package com.redxun.wx.pub.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.redxun.core.util.StringUtil;

public class PublicApiUrl {

	/**
	 * 获取token地址。
	 * @param appId
	 * @param secret
	 * @return
	 */
	public static String getAccessTokenUrl(String appId,String secret){
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
	}
	
	/**
	 * 取得用户列表Url。
	 * @param token
	 * @return
	 */
	public static String getUsersUrl(String token,String next_openid){
		String url="https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token;
		if(StringUtil.isNotEmpty(next_openid)){
			url+="&next_openid=" + next_openid;
		}
		return url;
	}
	
	/**
	 * 取得用户信息。
	 * @param token
	 * @param openId
	 * @return
	 */
	public static String getUserUrl(String token,String openId){
		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openId+"&lang=zh_CN";
		return url;
	}
	
	
	/**
	 * 上传图文消息内图片
	 * @param token
	 * @return
	 */
	public static String getUploadImgUrl(String token){
		String url="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+token;
		return url;
	}
	
	/**
	 * 上传视频要求格式mp4,5mb以内
	 * @param accessToken
	 * @return
	 */
	public static String getUploadVideoUrl(String accessToken){
		String url="https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token="+accessToken;
		return url;
	}
	
	
	/**
	 * 获取其他永久类型的永久素材mediaId
	 * @param token
	 * @param type
	 * @return
	 */
	public static String getUploadMeterial(String token,String type){
		String url="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+token+"&type="+type;
		return url;
	}
	
	/**
	 * 取得新增图文素材的url
	 * @param accessToken
	 * @return
	 */
	public static String getForeverMaterial(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="+accessToken;
	}
	
	/**
	 * 取得新增临时素材的url
	 * @param accessToken
	 * @param type
	 * @return
	 */
	public static String getUploadTemporaryMeterial(String accessToken,String type){
		return "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type="+type;
	}
	
	/**
	 * 取得获取永久素材的url
	 * @param accessToken
	 * @return
	 */
	public static String getForeverMeterial(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="+accessToken;
	}
	
	/**
	 * 取得获取临时素材的url
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public static String getTempMeterial(String accessToken,String mediaId){
		return "https://api.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id="+mediaId;
	}
	
	/**
	 * 获取素材的修改url
	 * @param accessToken
	 * @return
	 */
	public static String getUpdateMeterial(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token="+accessToken;
	}
	
	/**
	 * 获取删除微信永久素材的url
	 * @param accessToken
	 * @return
	 */
	public static String getDeleteMeterial(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token="+accessToken;
	}
	
	/**
	 * 获取根据tag群发消息的url
	 * @param accessToken
	 * @return
	 */
	public static String getSendMsgByTag(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+accessToken;
	}
	
	/**
	 * 获取消息发送结果的url
	 * @param accessToken
	 * @return
	 */
	public static String getSendMsgReturnState(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token="+accessToken;
	}
	
	/**
	 * 获取根据openId发送消息的url
	 * @param accessToken
	 * @return
	 */
	public static String getSendMsgByOpenId(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+accessToken;
	}
	
	/**
	 * 获取删除消息的url
	 * @param accessToken
	 * @return
	 */
	public static String getDeleteMsgUrl(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token="+accessToken;
	}
	/**
	 * 获取预览消息的url
	 * @param accessToken
	 * @return
	 */
	public static String getPreviewMsgUrl(	String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="+accessToken;
	}
	
	/**
	 * 获取创建菜单的url
	 * @param accessToken
	 * @return
	 */
	public static String getCreateMenuUrl(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
	}
	
	/**
	 * 获取查询菜单的url
	 * @param accessToken
	 * @return
	 */
	public static String getFindWxMenus(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+accessToken;
	}
	
	/**
	 * 获取删除所有菜单的url
	 * @param accessToken
	 * @return
	 */
	public static String getDelWxMenusUrl(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken;	
	}
	
	/**
	 * 网页授权uri配置
	 * 会跳转到redirectUri返回code(只有5分钟使用时期)
	 * @param appId
	 * @param redirectUri
	 * @param scope     snsapi_userinfo:最大权限   snsapi_base:静默小权限
	 * @return url
	 * { "access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN","openid":"OPENID","scope":"SCOPE" } 
	 * @throws UnsupportedEncodingException
	 */
	public static String getWebCodeUrl(String appId,String redirectUri,String scope) throws UnsupportedEncodingException{
		redirectUri=URLEncoder.encode(redirectUri, "utf-8");
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+ redirectUri+"&response_type=code&scope="+scope+"#wechat_redirect";
	}
	
	/**
	 * 通过code来获取网页授权accessToken
	 * @param appId
	 * @param secret
	 * @param webCode
	 * @return
	 */
	public static String getWebAccessTokenUrl(String appId,String secret,String webCode){
		return "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+webCode+"&grant_type=authorization_code";
	}
	
	/**
	 * 获取通过refreshToken来刷新accessToken的url
	 * @param appId
	 * @param refreshToken
	 * @return {"access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN","openid":"OPENID","scope":"SCOPE"}
	 */
	public static String getRefreshAccessTokenUrl(String appId,String refreshToken){
		return "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appId+"&grant_type=refresh_token&refresh_token="+refreshToken;
	}
	
	/**
	 * 通过accessToken获取user信息
	 * @param webAccessToken
	 * @param openId
	 * @return
	 */
	public static String getUserMsgUrlByWebAccessToken(String webAccessToken,String openId){
		return "https://api.weixin.qq.com/sns/userinfo?access_token="+webAccessToken+"&openid="+openId+"&lang=zh_CN";
	}
	
	/**
	 * 获取创建微信卡券的url
	 * @param accessToken
	 * @return
	 */
	public static String getCreateCardUrl(String accessToken){
		return "https://api.weixin.qq.com/card/create?access_token="+accessToken;
	}
	
	/**
	 * 获取投放微信卡券二维码的url
	 * @param accessToken
	 * @return
	 */
	public static String getCreateWxTicketQrCodeUrl(String accessToken){
		return "https://api.weixin.qq.com/card/qrcode/create?access_token="+accessToken;
	}
	/**
	 * 获取删除微信卡券的url
	 * @param accessToken
	 * @return
	 */
	public static String getDeleteWxTicketUrl(String accessToken){
		return "https://api.weixin.qq.com/card/delete?access_token="+accessToken;
	}
	
	/**
	 * 获取核销微信卡券的url
	 * @param accessToken
	 * @return
	 */
	public static String getConsumeWxTicket(String accessToken){
		return "https://api.weixin.qq.com/card/code/consume?access_token="+accessToken;
	}
	
	/**
	 * 获取修改微信卡券部分信息的url
	 * @param accessToken
	 * @return
	 */
	public static String  getUpdateWxTicket(String accessToken){
		return "https://api.weixin.qq.com/card/update?access_token="+accessToken;
	}
	
	/**
	 * 拉取商户卡券领取详情
	 * @param accessToken
	 * @return
	 */
	public static String getBrandTicketInfoUrl(String accessToken){
		return "https://api.weixin.qq.com/datacube/getcardbizuininfo?access_token="+accessToken;
	}
	
	/**
	 * 获取普通免费卡券领取详情Url
	 * @param accessToken
	 * @return
	 */
	public static String getFreeTicketInfoUrl(String accessToken){
		return "https://api.weixin.qq.com/datacube/getcardcardinfo?access_token="+accessToken;
	}
	
	/**
	 * 获取设置卡券失效链接
	 * @param accessToken
	 * @return
	 */
	public static String getCancelTicketUrl(String accessToken){
		return "https://api.weixin.qq.com/card/code/unavailable?access_token="+accessToken;
	}
	
	/**
	 * 获取创建货架的url
	 * @param accessToken
	 * @return
	 */
	public static String getCreateLandingPage(String accessToken){
		return "https://api.weixin.qq.com/card/landingpage/create?access_token="+accessToken;
	}
	
	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getCheckTicketCodeUrl(String accessToken){
		return "https://api.weixin.qq.com/card/code/get?access_token="+accessToken;
	}
	
	/**
	 * 获取修改库存的url
	 * @param accessToken
	 * @return
	 */
	public static String getModifyCountUrl(String accessToken){
		return "https://api.weixin.qq.com/card/modifystock?access_token="+accessToken;
	}

	/**
	 * 获取jsapi 调用URL。
	 * @param accessToken
	 * @return
	 */
	public static String getJsApiUrl(String accessToken){
		return "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ accessToken + "&type=jsapi";
	}
	
	
}
