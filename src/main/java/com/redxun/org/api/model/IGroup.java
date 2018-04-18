package com.redxun.org.api.model;
/**
 * 
 * <pre> 
 * 描述：用户组
 * 构建组：ent-base-user
 * 作者：csx
 * 邮箱:keith@mitom.cn
 * 日期:2014年4月29日-上午9:02:49
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public interface IGroup extends IdentityInfo{
	
	
	/**
	 * 用户所属的租用ID，若不是租用者，则为0
	 * @return  String
	 */
	String getTenantId();
	
	
	
	/**
	 * 用户组Key
	 * @return  String
	 */
	String getKey();
	
	
	
	/**
	 * 用户组类型
	 * @return  String
	 */
	String getType();
	
	
	
	
}
