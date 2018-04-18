package com.redxun.oa.mail.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class MailServerConfigSync implements IMailServerConfig {
	
    private JdbcTemplate jdbcTemplate;  
    
    public JdbcTemplate getJdbcTemplate() {  
        return jdbcTemplate;  
    }  
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    }  
	
	/**
	 * 配置邮件服务器
	 * @param flag 是否使用James服务器
	 */
	public void configJames(boolean flag){
		
	}
	
	/**
	 * 创建域名
	 * @param domain 域名
	 */
	public boolean createDomain(String domain){
		String sql="select count(*) from james_domain where DOMAIN_NAME=?";
		if(this.jdbcTemplate.queryForObject(sql, new Object[]{domain},Integer.class)!=0)  //域名已经存在的时候
			return false;
		else{
			sql="insert into james_domain(DOMAIN_NAME) values(?)";
			this.jdbcTemplate.update(sql,  new Object[]{domain});  
			return true;
		}	
	}

	/**
	 * 创建用户
	 * @param username 用户名
	 */
	public boolean createUser(String username) {
		String domain=username.substring(username.indexOf("@")+1);
		String sql="select count(*) from james_domain where DOMAIN_NAME=?";
		if(this.jdbcTemplate.queryForObject(sql, new Object[]{domain},Integer.class)!=0){  //域名已经存在的时候
			sql="insert into james_user(USER_NAME,PASSWORD_HASH_ALGORITHM,PASSWORD,version) values(?,?,?,?)";
			this.jdbcTemplate.update(sql,  new Object[]{username,"MD5","e10adc3949ba59abbe56e057f20f883e",1});   //默认密码123456
			return true;
		}
		else
			return false;
	}

}
