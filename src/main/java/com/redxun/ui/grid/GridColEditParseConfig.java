package com.redxun.ui.grid;

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
public class GridColEditParseConfig implements InitializingBean {
	/**
	 * 插件解析器映射
	 */
	private Map<String, GridColEditParseHandler> viewHandlersMap = new HashMap<String, GridColEditParseHandler>();
	/**
	 * 视图控件解析器
	 */
	private List<GridColEditParseHandler> viewHandlers = new ArrayList<GridColEditParseHandler>();

	/**
	 * 默认解析器 
	 */
	private GridColEditParseHandler defaulViewHandler = new GdMiniTextBoxHandler();
	
	public List<GridColEditParseHandler> getViewHandlers() {
		return viewHandlers;
	}

	public void setViewHandlers(List<GridColEditParseHandler> viewHandlers) {
		this.viewHandlers = viewHandlers;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for(GridColEditParseHandler handler:viewHandlers){
			viewHandlersMap.put(handler.getPluginName(), handler);
		}
	}
	
	public GridColEditParseHandler getControlParseHandler(String pluginName){
		GridColEditParseHandler handler=viewHandlersMap.get(pluginName);
		if(handler!=null){
			return handler;
		}
		return defaulViewHandler;
	}

}
