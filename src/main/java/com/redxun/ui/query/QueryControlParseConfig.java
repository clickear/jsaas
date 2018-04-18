package com.redxun.ui.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

/**
 * 用于解析在线定义的查询条件的控件
 * @author mansan
 *
 */
public class QueryControlParseConfig implements InitializingBean {
	/**
	 * 插件解析器映射
	 */
	private Map<String, QueryControlParseHandler> viewHandlersMap = new HashMap<String, QueryControlParseHandler>();
	/**
	 * 视图控件解析器
	 */
	private List<QueryControlParseHandler> viewHandlers = new ArrayList<QueryControlParseHandler>();

	/**
	 * 默认解析器 
	 */
	private QueryControlParseHandler defaulViewHandler = new MiniTextBoxHandler();
	
	public List<QueryControlParseHandler> getViewHandlers() {
		return viewHandlers;
	}

	public void setViewHandlers(List<QueryControlParseHandler> viewHandlers) {
		this.viewHandlers = viewHandlers;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for(QueryControlParseHandler handler:viewHandlers){
			viewHandlersMap.put(handler.getPluginName(), handler);
		}
	}
	
	public QueryControlParseHandler getControlParseHandler(String pluginName){
		QueryControlParseHandler handler=viewHandlersMap.get(pluginName);
		if(handler!=null){
			return handler;
		}
		return defaulViewHandler;
	}

}
