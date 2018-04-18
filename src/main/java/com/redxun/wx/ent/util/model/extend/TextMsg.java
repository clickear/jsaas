package com.redxun.wx.ent.util.model.extend;

import com.redxun.wx.ent.util.model.BaseMsg;

public class TextMsg extends BaseMsg {
	
	
	
	@Override
	public String getMsgtype() {
		return "text";
	}

	private String text="";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	public static void main(String[] args) {
		TextMsg t=new TextMsg();
		System.out.println(t);
	}
}
