package com.redxun.sys.util;

import com.redxun.core.jms.IMessageProducer;
import com.redxun.core.jms.MessageModel;
import com.redxun.core.jms.MessageUtil;
import com.redxun.core.util.StringUtil;

public class SysUtil {
	
	/**
	 * 发送消息。
	 * @param model
	 */
	public static void sendMessage(MessageModel model){
		String type=model.getType();
		if(StringUtil.isEmpty(type)) return;
		IMessageProducer producer= MessageUtil.getProducer();
		producer.send(model);
	}

}
