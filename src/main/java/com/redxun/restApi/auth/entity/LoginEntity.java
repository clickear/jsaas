package com.redxun.restApi.auth.entity;

import java.io.Serializable;

public class LoginEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accountName;
	
	private String passWord;
	
	private String reLogin;
	
	private String type;
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getReLogin() {
		return reLogin;
	}
	
	public void setReLogin(String reLogin) {
		this.reLogin = reLogin;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("accountName:").append(this.accountName)
		.append(",passWord:").append(this.passWord)
		.append(",reLogin:").append(this.reLogin)
		.append(",type").append(this.type);
		return sb.toString();
	}
}
