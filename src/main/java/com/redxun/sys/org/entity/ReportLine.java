package com.redxun.sys.org.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户的汇报线
 * @author csx
 *
 */
public class ReportLine {
	/**
	 * 实例Id
	 */
	protected String instId;
	
	/**
	 * 用户Id或组Id
	 */
	protected String id;
	/**
	 * 用户姓名
	 */
	protected String name;
	/**
	 * 部门及职位信息
	 */
	protected String title;
	/**
	 * 显示的样式风格
	 */
	protected String className="";
	
	protected List<ReportLine> children=new ArrayList<ReportLine>();
	
	
	public ReportLine() {
		
	}
	
	public ReportLine(String id,String name,String title){
		this.id=id;
		this.name=name;
		this.title=title;
	}
	
	public ReportLine(String id,String name,String title,List<ReportLine> children){
		this.id=id;
		this.name=name;
		this.title=title;
		this.children=children;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ReportLine> getChildren() {
		return children;
	}
	public void setChildren(List<ReportLine> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

}
