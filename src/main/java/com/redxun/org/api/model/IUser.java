package com.redxun.org.api.model;

import java.util.Set;

/**
 * 
 * <pre> 
 * 描述：组织架构用户
 * 构建组：ent-base-user
 * 作者：csx
 * 邮箱:keith@mitom.cn
 * 日期:2014年4月28日-下午9:59:49
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public interface IUser extends IdentityInfo{
	/**
	 * 用户Id 
	 * @return 
	 * String
	 */
	String getUserId();
	
	void setUserId(String userId);
	/**
	 * 用户所属的租用机构
	 * @return 
	 * String
	 */
	ITenant getTenant();
	
	
	void setTenant(ITenant tenant);
	/**
	 * 用户帐号
	 * @return 
	 * String
	 */
	String getUsername();
	
	/**
	 * 获得用户所属的租户下的域名
	 * @return
	 */
	String getDomain();
	
	/**
	 * 设置用户帐号
	 * @param userName
	 */
	void setUsername(String userName);
	/**
	 * 用户名称
	 * @return 
	 * String
	 */
	String getFullname();
	
	/**
	 * 设置用户全名
	 */
	void setFullname(String name);
	
    /**
     * 获得密码
     * @return
     * String
     */
    String getPwd();
    
    /**
     * 设置密码。
     * @param pwd
     */
    void setPwd(String pwd);
        
    
    /**
     * 是否超级管理员。
     * @return
     */
    boolean isSuperAdmin();
    
    /**
     * 是否为SaaS管理员
     * @return
     */
    boolean isSaaSAdmin();
    
    /**
     * 获得主部门Id
     * @return
     */
    public String getMainGroupId();
    
    /**
     * 获得主部门名称
     * @return
     */
    public String getMainGroupName();
    
    /**
     * 获得当前用户的用户组Id集合
     * @return
     */
    public Set<String> getGroupIds();
    
    /**
     * 获得所属公司Id
     * @return
     */
    public String getCompanyId();
    
    /**
     * 获得所属公司名称
     * @return
     */
    public String getCompanyName();
    
    /**
     * 获取执行人的状态。
     * @return
     */
    String getStatus();
    
    /**
     * 获得当前用户所在上下级的人员路径，即当前用户的上级人员ID,如，0.1.3.1001.
     * @return
     */
    String getUserUpLowPath();

}
