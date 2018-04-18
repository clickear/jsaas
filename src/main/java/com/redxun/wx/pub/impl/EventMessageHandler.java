package com.redxun.wx.pub.impl;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.redxun.core.util.AppBeanUtil;
import com.redxun.wx.pub.IEventHandler;
import com.redxun.wx.pub.IMessageHandler;

@Service
public class EventMessageHandler implements IMessageHandler {

	@Override
	public String getType() {
		return "event";
	}

	@Override
	public void handle(Map<String, String> msgMap) {
		Collection<IEventHandler> list=(Collection<IEventHandler>) AppBeanUtil.getBeanList(IEventHandler.class);
		for(IEventHandler handler:list){
			if(msgMap.get("Event").equals(handler.getType())){
				handler.handEvent(msgMap);
			}
		}
	}

}
