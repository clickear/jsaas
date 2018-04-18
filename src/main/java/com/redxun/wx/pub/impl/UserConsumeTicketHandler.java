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
public class UserConsumeTicketHandler implements IEventHandler {

	@Override
	public String getType() {
		return "user_consume_card";
	}

	/**
		<xml> 
		  <ToUserName> <![CDATA[gh_fc0a06a20993]]> </ToUserName>  
		  <FromUserName> <![CDATA[oZI8Fj040-be6rlDohc6gkoPOQTQ]]> </FromUserName>  
		  <CreateTime>1472549042</CreateTime>  
		  <MsgType> <![CDATA[event]]> </MsgType>  
		  <Event> <![CDATA[user_consume_card]]> </Event>  
		  <CardId> <![CDATA[pZI8Fj8y-E8hpvho2d1ZvpGwQBvA]]> </CardId>  
		  <UserCardCode> <![CDATA[452998530302]]> </UserCardCode>  
		  <ConsumeSource> <![CDATA[FROM_API]]> </ConsumeSource>  
		  <LocationName> <![CDATA[]]> </LocationName>  
		  <StaffOpenId> <![CDATA[oZ********nJ3bPJu_Rtjkw4c]]> </StaffOpenId>  
		  <VerifyCode> <![CDATA[]]> </VerifyCode>  
		  <RemarkAmount> <![CDATA[]]> </RemarkAmount>  
		  <OuterStr> <![CDATA[xxxxx]]> </OuterStr> 
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
