package com.redxun.wx.ent.util.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class SignParamModel {

	/**
	 * {	
	 * 	"endtime":1522720800,
	 * 	"starttime":1522720800,
	 * 	"useridlist":["zyg","lsm"],
	 * 	"opencheckindatatype":3
	 * }
	 * @param args
	 */
	public static void main(String[] args) {
		SignParamModel model=new SignParamModel();
		model.setStartDate(new Date());
		model.setEndDate(new Date());
		List<String> users=new ArrayList<>();
		users.add("zyg");
		users.add("lsm");
		model.setUserIds(users);
		
		System.out.println(model);
		
	}

	
	private int type=3;
	private Date startDate;
	private Date endDate;
	private List<String> userIds=new ArrayList<String>();
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
	public void addUser(String userId) {
		this.userIds.add(userId);
	}
	
	
	@Override
	public String toString() {
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("opencheckindatatype",this.type);
		jsonObj.put("starttime", this.startDate.getTime() /1000);
		jsonObj.put("endtime", this.endDate.getTime() /1000);
		jsonObj.put("useridlist", this.userIds);
	
		return jsonObj.toJSONString();
		
	}
	
	
	
}
