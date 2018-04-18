package com.redxun.sys.log;

import java.io.Serializable;

public class LogModel implements Serializable {

	private String ip="";
	
	private String logModule="";
	
	private String logSubModule="";
	
	private String action="";

	public String getLogModule() {
		return logModule;
	}

	public void setLogModule(String logModule) {
		this.logModule = logModule;
	}
	
	public String getLogSubModule() {
		return logSubModule;
	}

	public void setLogSubModule(String logSubModule) {
		this.logSubModule = logSubModule;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "LogModel [ip=" + ip + ", logModule=" + logModule  + ", logSubModule=" + logSubModule +", action=" + action + "]";
	}
	
	
}
