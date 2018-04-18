package com.redxun.org.api.model;
/**
 * 用户标识接口
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public interface IdentityInfo {
	/**
	 * 具体的用户
	 */
	public final static String IDENTIFY_TYPE_USER="USER";
	/**
	 * 用户组
	 */
	public final static String IDENTIFY_TYPE_GROUP="GROUP";
	
	/**
	 * 用户分组。
	 */
	public final static String IDENTIFY_TYPE_USERGROUP="USERGROUP";
	/**
	 * 返回用户标识类型
	 * @return
	 */
	String getIdentityType();
	
	/**
	 * 获得用户或组的唯一标识
	 * @return
	 */
	String getIdentityId();
	/**
	 * 获得用户或组的唯一标识名称
	 * @return
	 */
	String getIdentityName();
	
	
	
	
	
	
	
	
}
