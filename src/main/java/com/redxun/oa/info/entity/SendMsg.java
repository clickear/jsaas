package com.redxun.oa.info.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redxun.core.json.JsonDateSerializer;
/**
 * 这是已发信息infInboxSend页面的Get页面的显示类
 * @author Administrator
 *
 */
public class SendMsg {
	protected String recId;//接收人Id(可能组Id,可能人Id)
	protected String recName;//接受名字(可能组名,可能人名)
	protected String content;//内容
	protected Date createTime;
	
	public SendMsg(){
		
	}
	
	public SendMsg(String recId, String recName, String content, Date createTime) {
		super();
		this.recId = recId;
		this.recName = recName;
		this.content = content;
		this.createTime = createTime;
	}

	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public String getContent() {
		return content;
	}
	public void setContext(String content) {
		this.content = content;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}
	
	
}
