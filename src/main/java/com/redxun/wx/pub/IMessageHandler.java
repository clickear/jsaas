package com.redxun.wx.pub;

import java.util.Map;

public interface IMessageHandler {
	
	/**
	 * 消息类型
	 * @return
	 */
	String getType();
	
	/**
	 * 处理消息。
	 * @param messageMap
	 */
	void handle(Map<String,String> messageMap);
}
