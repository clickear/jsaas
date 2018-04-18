package com.redxun.oa.info.entity;

public class PortalConfig {
	private String templateId;
	private String templateName;
	private String service;
	
	public PortalConfig() {
	
	}
	
	public PortalConfig(String templateId,String templateName,String service){
		this.templateId=templateId;
		this.templateName=templateName;
		this.service=service;
	}
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
}
