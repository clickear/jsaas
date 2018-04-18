package com.redxun.saweb.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.saweb.util.WebAppUtil;

/**
 *  表单脚本标签
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class FormScriptTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 表单Id
	 */
	private String formId;
	/**
	 * 租用ID
	 */
	private String tenantId="";
	
	/**
	 * 实体名称
	 */
	private String entityName;
	
	private String baseUrl;
	
	
	public String getFormId() {
		return formId;
	}
	
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public int doStartTag() throws JspException {
		 JspWriter out=pageContext.getOut();
		 HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			 try{
				 	FreemarkEngine freemarkEngine=WebAppUtil.getBean(FreemarkEngine.class);
					Map<String,Object> model=new HashMap<String,Object>();
					model.put("rootPath", request.getContextPath());
					if(StringUtils.isNotEmpty(entityName)){
						model.put("entityName", entityName);
					}else{
						model.put("entityName","main");
					}
					model.put("baseUrl", baseUrl);
					model.put("formId", formId);
					model.put("tenantId", tenantId);
					String html=freemarkEngine.mergeTemplateIntoString("formScript.ftl", model);
					out.println(html);
					
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
			 return SKIP_BODY;
	}
}
