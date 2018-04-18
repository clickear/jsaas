package com.redxun.wx.pub.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
public class UserGetTicketHandler implements IEventHandler {

	@Override
	public String getType() {
		return "user_get_card";
	}

	/**
<xml> 
  <ToUserName> <![CDATA[gh_fc0a06a20993]]> </ToUserName>  
  <FromUserName> <![CDATA[oZI8Fj040-be6rlDohc6gkoPOQTQ]]> </FromUserName>  
  <CreateTime>1472551036</CreateTime>  
  <MsgType> <![CDATA[event]]> </MsgType>  
  <Event> <![CDATA[user_get_card]]> </Event>  
  <CardId> <![CDATA[pZI8Fjwsy5fVPRBeD78J4RmqVvBc]]> </CardId>  
  <IsGiveByFriend>0</IsGiveByFriend>  
  <UserCardCode> <![CDATA[226009850808]]> </UserCardCode>  
  <FriendUserName> <![CDATA[]]> </FriendUserName>  
  <OuterId>0</OuterId>  
  <OldUserCardCode> <![CDATA[]]> </OldUserCardCode>  
  <OuterStr> <![CDATA[12b]]> </OuterStr>  
  <IsRestoreMemberCard>0</IsRestoreMemberCard>  
  <IsRecommendByFriend>0</IsRecommendByFriend> 
</xml>
	 * 
	 */
	@Override
	public void handEvent(Map<String, String> messageMap) {
		String pubId=messageMap.get("pubId");
		System.out.println(messageMap);
		String friendName=messageMap.get("FriendUserName");
		if(StringUtils.isBlank(friendName)){
			String cardId=messageMap.get("CardId");
			WxTicketManager wxTicketManager=WebAppUtil.getBean(WxTicketManager.class);
			WxTicket wxTicket=wxTicketManager.get(cardId);
			JSONObject sku=JSONObject.parseObject(wxTicket.getSku());
			int quantity=sku.getInteger("quantity");
			sku.put("quantity", --quantity);
			wxTicket.setSku(sku.toJSONString());
			wxTicketManager.update(wxTicket);	
		}
		
		
		//HttpServletResponse response=HttpServletContext.getResponse();
		//	response.getWriter().print();
		
	}
	
	
	

}
