package com.redxun.wx.ent.util.model;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.org.entity.OsGroup;

public class WxOrg {
	
	public WxOrg() {
	}

	public WxOrg(Integer id, String name, Integer parentid, String order) {
		this.id = id;
		this.name = name;
		this.parentid = parentid;
		this.order = order;
	}
	
	public WxOrg(OsGroup osGroup) {
		this.id = osGroup.getWxPid();
		this.name = osGroup.getName();
		this.parentid = osGroup.getWxParentPid();
		this.order = osGroup.getSn().toString();
		this.originId=osGroup.getGroupId();
	}



	private Integer id=0;
	private String name="";
	private Integer parentid=0;
	private String originId="";
	
	private String order="";
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public String getOrder() {
		return order;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	@Override
	public String toString() {
		JSONObject o=new JSONObject();
		o.put("id", this.id);
		o.put("name", this.name);
		o.put("parentid", this.parentid);
		if(StringUtil.isNotEmpty(order)){
			o.put("order", this.order);
		}
		
		return o.toJSONString();
	}
	
	
	
	public static void main(String[] args) {
		WxOrg org=new WxOrg();
		org.setId(33);
		org.setName("name");
		System.out.println(org);
	}
	
	
}
