package com.redxun.wx.pub;

import java.util.Map;

public interface IEventHandler {
	
	/**
	 * 事件类型
	 * @return
	 */
	String getType();
	
	/**
	 * 处理事件。
	 * @param msgMap
	 */
	void handEvent(Map<String,String> msgMap);

}
