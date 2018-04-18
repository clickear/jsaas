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
 * 列表页面动态脚本
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class GridScriptTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 基础URL
	 */
	private String baseUrl;
	/**
	 * 实体名称
	 */
	private String entityName;
	/**
	 * 实体的描述
	 */
	private String entityTitle;
	
	/**
	 * 弹出编辑对话框的宽度
	 */
	private Integer winWidth=750;
	/**
	 * 弹出编辑对话框的高度
	 */
	private Integer winHeight=500;
	
	/**
	 * mini中的GridId
	 */
	private String gridId;
	
	/**
	 * 指定了租用ID
	 */
	private String tenantId="";


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getEntityTitle() {
		return entityTitle;
	}

	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}

	public Integer getWinWidth() {
		return winWidth;
	}

	public void setWinWidth(Integer winWidth) {
		this.winWidth = winWidth;
	}

	public Integer getWinHeight() {
		return winHeight;
	}

	public void setWinHeight(Integer winHeight) {
		this.winHeight = winHeight;
	}

	@Override
	public int doStartTag() throws JspException {
		 JspWriter out=pageContext.getOut();
		 HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			 try{
			 	FreemarkEngine freemarkEngine=WebAppUtil.getBean(FreemarkEngine.class);
				Map<String,Object> model=new HashMap<String,Object>();
				model.put("rootPath", request.getContextPath());
				model.put("entityName", entityName);
				model.put("entityTitle", entityTitle);
				model.put("baseUrl", baseUrl);
				model.put("gridId", gridId);
				model.put("winHeight",winHeight);
				model.put("winWidth", winWidth);
				model.put("tenantId", tenantId);
				String html=freemarkEngine.mergeTemplateIntoString("gridScript.ftl", model);
				out.println(html);
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
			 return SKIP_BODY;
	}
}
