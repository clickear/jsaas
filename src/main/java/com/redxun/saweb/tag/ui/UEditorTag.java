package com.redxun.saweb.tag.ui;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.saweb.util.WebAppUtil;
/**
 *  UEdito编辑器
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class UEditorTag extends BodyTagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Id
	private String id;
	//name
	private String name;
	//width,宽度px
	private String width;
	//height,高度px
	private Integer height;
	//是否为精简版
	private String isMini="false";

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException{
		 JspWriter out=pageContext.getOut();
		 HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		 String templateSource="<script id=\"${id}\" name=\"${name}\" rich-type=\"ueditor\" type=\"text/plain\" style=\"width:${width};height:${height}px;\">${value}</script>";
		 try{

			 if(request.getAttribute("_writeUEditorJs")==null){
				 String contextPath=request.getContextPath();
			    String script1="<script src=\""+contextPath+"/scripts/jquery/plugins/jquery.getscripts.min.js\" type=\"text/javascript\"></script>";    
			    out.println(script1);
			    if("true".equals(isMini)){
			    	String script2="<script type=\"text/javascript\" charset=\"utf-8\" src=\""+contextPath+"/scripts/ueditor/use-ueditor-mini.js\"></script>";
					out.println(script2);
			    }else{
			    	String script2="<script type=\"text/javascript\" charset=\"utf-8\" src=\""+contextPath+"/scripts/ueditor/use-ueditor.js\"></script>";
					out.println(script2);
			    }
				
				request.setAttribute("_writeUEditorJs",true);
			 }
			 
			 FreemarkEngine freemarkEngine=WebAppUtil.getBean(FreemarkEngine.class);
				Map<String,Object> model=new HashMap<String,Object>();
				model.put("ctxPath", request.getContextPath());
				model.put("id", id);
				model.put("name", name);
				model.put("height", height);
				if(width==null){
					model.put("width", "100%");
				}else{
					model.put("width", width);
				}
				BodyContent content=getBodyContent();
				
				if(content!=null){
					String bodyContent=content.getString();
					model.put("value",bodyContent);
				}
				
				String html=freemarkEngine.parseByStringTemplate(model, templateSource);
				out.println(html);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return EVAL_PAGE;
	}

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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getIsMini() {
		return isMini;
	}

	public void setIsMini(String isMini) {
		this.isMini = isMini;
	}

}
