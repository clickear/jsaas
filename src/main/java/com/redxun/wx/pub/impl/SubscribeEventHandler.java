package com.redxun.wx.pub.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;







import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.context.HttpServletContext;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.Dom4jUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.wx.core.entity.WxMeterial;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.entity.WxSubscribe;
import com.redxun.wx.core.manager.WxMeterialManager;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxSubscribeManager;
import com.redxun.wx.pub.IEventHandler;
import com.redxun.wx.pub.util.PublicApi;
import com.redxun.wx.pub.util.PublicApiUrl;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;

@Service
public class SubscribeEventHandler implements IEventHandler {

	@Override
	public String getType() {
		
		return "subscribe";
	}


	/**
	 * 
	 * 未关注时
		<xml><ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
		<Event><![CDATA[subscribe]]></Event>
		<EventKey><![CDATA[qrscene_123123]]></EventKey>
		<Ticket><![CDATA[TICKET]]></Ticket>
		</xml>
			 *
			 *已经关注时
		<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
		<Event><![CDATA[SCAN]]></Event>
		<EventKey><![CDATA[SCENE_VALUE]]></EventKey>
		<Ticket><![CDATA[TICKET]]></Ticket>
		</xml>
	 */
	@Override
	public void handEvent(Map<String, String> messageMap) {
		String pubId=messageMap.get("pubId");
		String official=messageMap.get("appId");
		String subscriber=messageMap.get("FromUserName");//OpenId
		WxPubAppManager wxPubAppManager=AppBeanUtil.getBean(WxPubAppManager.class);
		
		WxMeterialManager wxMeterialManager=AppBeanUtil.getBean(WxMeterialManager.class);
		WxPubApp wxPubApp=wxPubAppManager.getByAppId(official);

		String access_token="";
		String userJson="";
		try {
			access_token=wxPubAppManager.getAccessToken(pubId);//获取access_token
			userJson=PublicApi.getUser(access_token, subscriber);//获取关注者信息
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
		
		/**
		 * 创建/更新订阅者的实体
		 */
		WxSubscribeManager wxSubscribeManager=AppBeanUtil.getBean(WxSubscribeManager.class);
		JSONObject jsonObject=JSONObject.parseObject(userJson);
		WxSubscribe wxSubscribe=jsonObject.toJavaObject(WxSubscribe.class);
		WxSubscribe originSubscribe=wxSubscribeManager.getByOpenId(subscriber);//原本的关注者(之前有关注)
		if(originSubscribe==null){
			 
			wxSubscribe.setId(IdUtil.getId());
			wxSubscribe.setCreateTime(new Date());
			wxSubscribe.setTenantId(wxPubApp.getTenantId());
			wxSubscribe.setPubId(pubId);
			wxSubscribeManager.create(wxSubscribe);
		}else{
			originSubscribe.setSubscribe("1");
			originSubscribe.setNickName(wxSubscribe.getNickName());
			originSubscribe.setSubscribeTime(new Date());
			originSubscribe.setUpdateTime(new Date());
			wxSubscribeManager.update(originSubscribe);
		}
		
		
		JSONObject otherConfig=JSONObject.parseObject(wxPubApp.getOtherConfig());
		String welcomeConfig=otherConfig==null?null:otherConfig.getString("replyType");
		
		/**
		 * 发送消息给订阅者
		 */
		Document doc=Dom4jUtil.stringToDocument("<xml></xml>");
		Element root= doc.getRootElement();
		add(root,"ToUserName", messageMap.get("FromUserName"));
		add(root,"FromUserName", messageMap.get("ToUserName"));
		if(welcomeConfig==null){//如果没有配置则发送感谢关注
			add(root,"MsgType", "text");
			add(root,"Content","感谢关注");
		}else if("image".equals(welcomeConfig)){
			add(root,"MsgType", "image");
			Element imageElement=root.addElement("Image");
			add(imageElement,"MediaId", otherConfig.getString("meterial"));
		}else if("voice".equals(welcomeConfig)){
			add(root,"MsgType", "voice");
			Element voiceElement=root.addElement("Voice");
			add(voiceElement,"MediaId", otherConfig.getString("meterial"));
		}else if("video".equals(welcomeConfig)){
			add(root,"MsgType", "video");
			Element videoElement=root.addElement("Video");
			add(videoElement,"MediaId", otherConfig.getString("meterial"));
		}else if("text".equals(welcomeConfig)){
			add(root,"MsgType", "text");
			add(root,"Content",otherConfig.getString("replyText"));
		}else if("music".equals(welcomeConfig)){
			add(root,"MsgType","music");
			Element musicElement=root.addElement("Music");
			add(musicElement,"MusicUrl", otherConfig.getString("replyMusic"));
			add(musicElement,"ThumbMediaId", otherConfig.getString("meterial"));
		}else if("article".equals(welcomeConfig)){
			String meterial=otherConfig.getString("meterial");
			String[] meterialArray=meterial.split(",");
			int meterialNum=meterialArray.length;
			add(root,"MsgType", "news");
			add(root, "ArticleCount", ""+meterialNum);
			Element articlesElement=root.addElement("Articles");
			for (int i = 0; i < meterialArray.length; i++) {
				String mediaId=meterialArray[i];
				WxMeterial wxMeterial=wxMeterialManager.getByMediaId(mediaId);
				JSONObject meterialConfig=JSONObject.parseObject(wxMeterial.getArtConfig());
			    String thumbUrl=meterialConfig.getString("thumb_url");
			    
			    
			    String meterialJson="";
			    try {
			    	meterialJson=PublicApi.getMeterial(access_token, mediaId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			    JSONObject meterialObj=JSONObject.parseObject(meterialJson);
			    JSONArray itemArray=meterialObj.getJSONArray("news_item");
			    JSONObject itemObj=itemArray.getJSONObject(0);
				Element item=articlesElement.addElement("item");
				add(item, "Title", itemObj.getString("title"));
				add(item, "Description", wxMeterial.getName());
				add(item, "PicUrl", thumbUrl);
				add(item, "Url", itemObj.getString("url"));
			}
		}
		
		
		
		add(root,"CreateTime", String.valueOf(System.currentTimeMillis()));
		
		HttpServletResponse response=HttpServletContext.getResponse();
		try {
			String xml=root.asXML();
			response.getWriter().print(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void add(Element el,String name,String value){
		el.addElement(name).setText(value);
	}
	
}
