package com.redxun.wx.pub.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.redxun.core.util.AppBeanUtil;
import com.redxun.wx.core.entity.WxSubscribe;
import com.redxun.wx.core.manager.WxSubscribeManager;
import com.redxun.wx.pub.IEventHandler;

/**
 * <pre>
 * 	&lt;xml>
		&lt;ToUserName><![CDATA[toUser]]>&lt;/ToUserName>
		&lt;FromUserName><![CDATA[FromUser]]>&lt;/FromUserName>
		&lt;CreateTime>123456789&lt;/CreateTime>
		&lt;MsgType><![CDATA[event]]>&lt;/MsgType>
		&lt;Event><![CDATA[subscribe]]>&lt;/Event>
		&lt;/xml>
	</pre>
 * 
 * @author ray
 *
 */
@Service
public class UnsubscribeEventHandler implements IEventHandler {

	@Override
	public String getType() {
		return "unsubscribe";
	}

	
	@Override
	public void handEvent(Map<String, String> messageMap) {
		String subscriber=messageMap.get("FromUserName");//OpenId
		WxSubscribeManager wxSubscribeManager=AppBeanUtil.getBean(WxSubscribeManager.class);
		WxSubscribe wxSubscribe=wxSubscribeManager.getByOpenId(subscriber);
		wxSubscribe.setSubscribe("0");
		wxSubscribe.setUpdateTime(new Date());
		wxSubscribeManager.update(wxSubscribe);
		
	}

}
