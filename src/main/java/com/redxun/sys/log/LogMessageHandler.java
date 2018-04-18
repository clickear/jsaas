package com.redxun.sys.log;

import com.redxun.core.jms.IMessageHandler;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.log.entity.LogEntity;
import com.redxun.sys.log.manager.LogEntityManager;

public class LogMessageHandler implements IMessageHandler {

	@Override
	public void handMessage(Object messageObj) {
		LogEntity logEntity=(LogEntity) messageObj;
		LogEntityManager logEntityManager=WebAppUtil.getBean(LogEntityManager.class);
		logEntityManager.create(logEntity);
	}

}
