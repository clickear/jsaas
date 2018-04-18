package com.redxun.sys.bo.manager.parse;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.sys.bo.entity.SysBoAttr;


public interface IBoAttrParse {
	


	/**
	 * 字段解析
	 * @param pluginName
	 * @param el
	 * @return
	 */
	SysBoAttr parse(String pluginName, Element el);
	
	/**
	 * 返回插件名称。
	 * @return
	 */
	String getPluginName();
	
	/**
	 * 描述
	 * @return
	 */
	String getDescription();
	
	/**
	 * 是否为单字段属性。
	 * @return
	 */
	boolean isSingleAttr();
	
	/**
	 * 返回数据格式如下{key:"",name:""}
	 * 如果是单字段属性则返回{key:""}
	 * 复合字段则返回{key:"",name:""}
	 * @param attr
	 * @return
	 */
	JSONObject getInitData(SysBoAttr attr);
}
