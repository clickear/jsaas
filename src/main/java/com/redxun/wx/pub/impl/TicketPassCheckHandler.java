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
import com.redxun.wx.core.entity.WxTicket;
import com.redxun.wx.core.manager.WxKeyWordReplyManager;
import com.redxun.wx.core.manager.WxMeterialManager;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxTicketManager;
import com.redxun.wx.pub.IEventHandler;
import com.redxun.wx.pub.IMessageHandler;
import com.redxun.wx.pub.util.PublicApi;

@Service
public class TicketPassCheckHandler implements IEventHandler {

	@Override
	public String getType() {
		return "card_pass_check";
	}

	/**
		<xml> 
		  <ToUserName><![CDATA[toUser]]></ToUserName>  
		  <FromUserName><![CDATA[FromUser]]></FromUserName>  
		  <CreateTime>123456789</CreateTime>  
		  <MsgType><![CDATA[event]]></MsgType>  
		  <Event><![CDATA[card_pass_check]]></Event> //不通过为card_not_pass_check 
		  <CardId><![CDATA[cardid]]></CardId>  
		  <RefuseReason><![CDATA[非法代制]]></RefuseReason> 
		</xml>
	 * 
	 */
	@Override
	public void handEvent(Map<String, String> messageMap) {
		String pubId=messageMap.get("pubId");
		String cardId=messageMap.get("CardId");
		WxTicketManager wxTicketManager=WebAppUtil.getBean(WxTicketManager.class);
		WxTicket wxTicket=wxTicketManager.get(cardId);
		wxTicket.setChecked("YES");
		wxTicketManager.update(wxTicket);
		//	response.getWriter().print();
		
	}
	
	
	

}
