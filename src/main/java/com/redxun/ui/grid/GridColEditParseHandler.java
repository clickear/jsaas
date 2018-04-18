package com.redxun.ui.grid;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;

/**
 * 表格列字段解析器
 * @author mansan
 *
 */
public interface GridColEditParseHandler {
	/**
	 * 插件名称
	 * @return
	 */
	String getPluginName();
	/**
	 * 解析
	 * @param el
	 */
	Element parse(Element columnEl,JSONObject elJson, Map<String,Object> params);
}
