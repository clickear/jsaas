package com.redxun.kms.core.entity;
/**
 * 用于显示阅读记录的类
 * @author Administrator
 *
 */
public class KdReader {
	
	protected String id;
	//人名
	protected String name;
	//阅读时间
	protected String readTime;
	//部门
	protected String depName;
	//状态
	protected String status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
