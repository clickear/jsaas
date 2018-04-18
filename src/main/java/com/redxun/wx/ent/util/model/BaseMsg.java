package com.redxun.wx.ent.util.model;

import com.alibaba.fastjson.JSON;

/**
 * 基础消息。
 * @author ray
 *
 */
public abstract class BaseMsg {
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	private String touser="";
	private String toparty="";
	private String totag="";
	private String msgtype="";
	private String agentid="";
	
	public String getTouser() {
		return touser;
	}
	public String getToparty() {
		return toparty;
	}
	public String getTotag() {
		return totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	
	
	
	
}
