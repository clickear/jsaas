package com.redxun.sys.log;

import java.util.List;

public class LogEntMetadata {
	
	private String className="";
	
	private String fullName="";
	
	private List<MethodMetadata> metadatas;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<MethodMetadata> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(List<MethodMetadata> metadatas) {
		this.metadatas = metadatas;
	}
	
	

}
