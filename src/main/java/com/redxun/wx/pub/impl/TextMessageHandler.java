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
import com.redxun.core.util.Dom4jUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.wx.core.entity.WxKeyWordReply;
import com.redxun.wx.core.entity.WxMeterial;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.manager.WxKeyWordReplyManager;
import com.redxun.wx.core.manager.WxMeterialManager;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.pub.IMessageHandler;
import com.redxun.wx.pub.util.PublicApi;

@Service
public class TextMessageHandler implements IMessageHandler {

	@Override
	public String getType() {
		return "text";
	}

	/**
	 * <pre>
	 *  &lt;xml>
		 &lt;ToUserName><![CDATA[toUser]]>&lt;/ToUserName>
		 &lt;FromUserName><![CDATA[fromUser]]>&lt;/FromUserName>
		 &lt;CreateTime>1348831860&lt;/CreateTime>
		 &lt;MsgType><![CDATA[text]]>&lt;/MsgType>
		 &lt;Content>&lt;![CDATA[this is a test]]>&lt;/Content>
		 &lt;MsgId>1234567890123456&lt;/MsgId>
		 &lt;/xml>
		 
		 
		 <xml>
			<ToUserName><![CDATA[toUser]]></ToUserName>
			<FromUserName><![CDATA[fromUser]]></FromUserName>
			<CreateTime>12345678</CreateTime>
			<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[你好]]></Content>
		</xml>
		</pre>
	 */
	@Override
	public void handle(Map<String, String> messageMap) {
		String pubId=messageMap.get("pubId");
		String receiveText=messageMap.get("Content");
		WxKeyWordReplyManager wxKeyWordReplyManager=WebAppUtil.getBean(WxKeyWordReplyManager.class);
		WxKeyWordReply wxKeyWordReply=wxKeyWordReplyManager.getWxKeyWordReplyByKeyWord(receiveText, pubId);
		
		
		HttpServletResponse response=HttpServletContext.getResponse();
		String xml;
		try {
			xml = handleElementWithType(wxKeyWordReply, messageMap);
			response.getWriter().print(xml);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		

	}
	
	private void add(Element el,String name,String value){
		el.addElement(name).setText(value);
	}
	
	/**
	 * 处理消息内部
	 * @param wxKeyWordReply
	 * @param root
	 * @throws Exception 
	 */
	private String  handleElementWithType(WxKeyWordReply wxKeyWordReply,Map<String, String> messageMap) throws Exception{
		Document doc=Dom4jUtil.stringToDocument("<xml></xml>");
		Element root= doc.getRootElement();
		add(root,"ToUserName", messageMap.get("FromUserName"));
		add(root,"FromUserName", messageMap.get("ToUserName"));
		add(root,"CreateTime", String.valueOf(System.currentTimeMillis()));
		if(wxKeyWordReply==null){
			add(root,"MsgType","text");
			add(root,"Content","公众号暂无配置回复");
		}else{
			String replyType=wxKeyWordReply.getReplyType();
			JSONObject jsonObject=JSONObject.parseObject(wxKeyWordReply.getReplyContent());
			if("text".equals(replyType)){
				add(root,"MsgType","text");
				add(root,"Content",jsonObject.getString("replyText"));
			}else if("image".equals(replyType)){
				add(root,"MsgType","image");
				Element imageElement=root.addElement("Image");
				add(imageElement,"MediaId",jsonObject.getString("meterial"));
			}else if("voice".equals(replyType)){
				add(root,"MsgType", "voice");
				Element videoElement=root.addElement("Voice");
				add(videoElement,"MediaId", jsonObject.getString("meterial"));
			}else if("video".equals(replyType)){
				add(root,"MsgType","video");
				Element voiceElement=root.addElement("Video");
				add(voiceElement,"MediaId",jsonObject.getString("meterial"));
			}else if("music".equals(replyType)){
				add(root,"MsgType","music");
				Element musicElement=root.addElement("Music");
				add(musicElement,"MusicUrl", jsonObject.getString("replyMusic"));
				add(musicElement,"HQMusicUrl", jsonObject.getString("replyMusic"));
				add(musicElement,"ThumbMediaId", jsonObject.getString("meterial"));
			}else if("article".equals(replyType)){
				String pubId=messageMap.get("pubId");
				WxMeterialManager wxMeterialManager=WebAppUtil.getBean(WxMeterialManager.class);
				WxPubAppManager wxPubAppManager=WebAppUtil.getBean(WxPubAppManager.class);
				String accessToken=wxPubAppManager.getAccessToken(pubId);
				String meterial=jsonObject.getString("meterial");
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
				    	meterialJson=PublicApi.getMeterial(accessToken, mediaId);
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
		}
			String xml=root.asXML();
		return xml;
		
	}

}
