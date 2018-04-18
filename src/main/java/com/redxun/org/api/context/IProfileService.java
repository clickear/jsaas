package com.redxun.org.api.context;

import java.util.Set;

/**
 * 获取用户配置信息。
 * @author ray
 *
 */
public interface IProfileService {
	
	/**
	 * 获取类型
	 * @return
	 */
	String getType();
	
	/**
	 * 获取类型名称。
	 * @return
	 */
	String getName();
	
	/**
	 * 获取策略配置数据。
	 * @return
	 */
	Set<String> getCurrentProfile();

}
