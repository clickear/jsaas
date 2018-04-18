package com.redxun.saweb.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.saweb.util.WebAppUtil;

/**
 *  表格分页标签
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class GridPagerTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分页控件ID
	 */
	private String pagerId;
	/**
	 * 实体名称
	 */
	private String entityName;
	
	public String getPagerId() {
		return pagerId;
	}
	
	public void setPagerId(String pagerId) {
		this.pagerId = pagerId;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
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
					model.put("pagerId", pagerId);
					String html=freemarkEngine.mergeTemplateIntoString("gridPager.ftl", model);
					out.println(html);
					
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
			 return SKIP_BODY;
	}
}
