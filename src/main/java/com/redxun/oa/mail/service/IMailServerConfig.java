package com.redxun.oa.mail.service;

/**
 * <pre> 
 * 描述：邮箱服务器接口
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public interface IMailServerConfig {
	void configJames(boolean flag);//配置James服务器
	boolean createDomain(String domain); //创建域名
	boolean createUser(String username);//创建用户
}
