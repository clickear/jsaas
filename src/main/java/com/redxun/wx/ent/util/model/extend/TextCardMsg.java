package com.redxun.wx.ent.util.model.extend;

import com.redxun.wx.ent.util.model.BaseMsg;

public class TextCardMsg extends BaseMsg {
	
	
	private TextCard textcard;
	

	@Override
	public String getMsgtype() {
		return "textcard";
	}


	public TextCard getTextcard() {
		return textcard;
	}


	public void setTextcard(TextCard textcard) {
		this.textcard = textcard;
	}
	
	
	public static void main(String[] args) {
		TextCardMsg img=new TextCardMsg();
		TextCard model=new TextCard();
		model.setDescription("ddd");
		model.setTitle("国庆阅兵");
		img.setTextcard(model);
		System.out.println(img.toString());
	}
	
}
