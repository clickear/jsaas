package com.redxun.ui.query;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;

/**
 * 查询解析处理控件器
 * @author mansan
 *
 */
public interface QueryControlParseHandler {
	/**
	 * 插件名称
	 * @return
	 */
	String getPluginName();
	/**
	 * 解析
	 * @param el
	 */
	Element parse(JSONObject elJson, Map<String,Object> params);
	
	/**
	 * 解析成报表的元素
	 * @param elJson
	 * @return
	 */
	Element parseReportEl(JSONObject elJson);
}
