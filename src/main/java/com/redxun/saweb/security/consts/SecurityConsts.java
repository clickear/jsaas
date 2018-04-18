package com.redxun.saweb.security.consts;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

/**
 * 安全参数常量
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class SecurityConsts {
	
    public static final ConfigAttribute ROLE_ANONYMOUS = new SecurityConfig("ROLE_ANONYMOUS");
    
    public static final ConfigAttribute ROLE_PUB=new SecurityConfig("ROLE_PUB");
    
	public static String S_ROLE_PUBLIC="ROLE_PUBLIC";
	
	public static String S_ROLE_ANONYMOUS="ROLE_ANONYMOUS";
}
