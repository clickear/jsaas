package com.redxun.sys.core.entity;

/**
 * SysBo的头部按钮
 * @author mansan
 */
public class SysBoTopButton {
	//按钮标签
	private String btnLabel;
	//按钮名称
	private String btnName;
	//按钮样式
	private String btnIconCls;
	//按钮点击
	private String btnClick;
	//按钮执行脚本
	private String serverHandleScript;
	
	public String getBtnLabel() {
		return btnLabel;
	}
	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public String getBtnIconCls() {
		return btnIconCls;
	}
	public void setBtnIconCls(String btnIconCls) {
		this.btnIconCls = btnIconCls;
	}
	public String getBtnClick() {
		return btnClick;
	}
	public void setBtnClick(String btnClick) {
		this.btnClick = btnClick;
	}
	public String getServerHandleScript() {
		return serverHandleScript;
	}
	public void setServerHandleScript(String serverHandleScript) {
		this.serverHandleScript = serverHandleScript;
	}
	
	
}
