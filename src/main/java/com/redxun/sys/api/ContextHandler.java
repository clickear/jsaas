package com.redxun.sys.api;

import java.util.Map;

/**
 * 上下文常量。
 * @author ray
 *
 */
public interface ContextHandler {
	
	/**
	 * key名称
	 * [USERID]
	 * @return
	 */
	String getKey();
	
	/**
	 * 名称。
	 * @return
	 */
	String getName();
	
	/**
	 * 获取值
	 * @return
	 */
	Object getValue(Map<String,Object> vars);

}
