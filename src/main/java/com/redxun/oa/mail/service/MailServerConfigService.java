package com.redxun.oa.mail.service;

import com.redxun.sys.core.util.SysPropertiesUtil;

/**
 * 邮件服务器配置相关服务
 * 
 * @author zwj
 *  
 */


public class MailServerConfigService {
	
	private boolean useDefaultConfig;
	
	MailServerConfigSync mailServerConfigSync;
	

	public boolean getUseDefaultConfig() {
		return useDefaultConfig;
	}

	public void setUseDefaultConfig(boolean useDefaultConfig) {
		this.useDefaultConfig = useDefaultConfig;
	}

	public MailServerConfigSync getMailServerConfigSync() {
		return mailServerConfigSync;
	}

	public void setMailServerConfigSync(MailServerConfigSync mailServerConfigSync) {
		this.mailServerConfigSync = mailServerConfigSync;
	}

	/**
	 * 配置服务器 ，创建域名和默认账号admin
	 * @param domain 域名
	 * @return
	 */
	public boolean configServer(String domain){   
		if(!useDefaultConfig)
			return useDefaultConfig;
		else if(mailServerConfigSync.createDomain(domain)){   //如果创建域名成功
			String account=SysPropertiesUtil.getAdminAccount();
			boolean flag=mailServerConfigSync.createUser(account+ "@"+domain); //创建默认账户admin 
			return flag;
		}
		else
			return false;
	}
	
	/**
	 * 创建用户
	 * @param username 用户名
	 */
	public void createUser(String username){    //创建用户
		mailServerConfigSync.createUser(username);
	}
}
