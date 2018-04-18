package com.redxun.wx.util;

import java.util.Date;

/**
 * 企业token对象。
 * @author ray
 *
 */
public class TokenModel {
	/**
	 * 企业ID
	 */
	private String corpId="";
	
	/**
	 * 密钥
	 */
	private String secret="";
	
	/**
	 * 令牌
	 */
	private String token="";
	
	/**
	 * 最后更新时间。
	 */
	private Date  lastUpdateTime;
	/**
	 * 错误代码
	 */
	private int code;
	
	/**
	 * 消息
	 */
	private String msg="";
	
	/**
	 * 键 由corpID 和 secret 组成
	 * @return
	 */
	public String getKey(){
		return corpId +"_" + secret;
	}

	public String getCorpId() {
		return corpId;
	}

	public String getSecret() {
		return secret;
	}

	public String getToken() {
		return token;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	/**
	 * 判断token是否过期。
	 * @return
	 */
	public boolean isExpire(){
		long time=System.currentTimeMillis();
		long lastTime= lastUpdateTime.getTime() + 7000* 1000;
		return lastTime<time;
	}
	
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(System.currentTimeMillis());
		Thread.sleep(1000);
		System.out.println(System.currentTimeMillis());
		
	}
	
	
}
