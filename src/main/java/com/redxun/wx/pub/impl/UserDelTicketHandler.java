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
public class UserDelTicketHandler implements IEventHandler {

	@Override
	public String getType() {
		return "user_del_card";
	}

	/**
<xml> <ToUserName><![CDATA[toUser]]></ToUserName> 
<FromUserName><![CDATA[FromUser]]></FromUserName> 
<CreateTime>123456789</CreateTime> 
<MsgType><![CDATA[event]]></MsgType> 
<Event><![CDATA[user_del_card]]></Event> 
<CardId><![CDATA[cardid]]></CardId> 
<UserCardCode><![CDATA[12312312]]></UserCardCode>
</xml>
	 * 
	 */
	@Override
	public void handEvent(Map<String, String> messageMap) {
		String pubId=messageMap.get("pubId");
		
		//HttpServletResponse response=HttpServletContext.getResponse();
		//	response.getWriter().print();
		
	}
	
	
	

}
