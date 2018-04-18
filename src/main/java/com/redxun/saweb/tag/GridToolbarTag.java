package com.redxun.saweb.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysMenu;
import com.redxun.sys.core.manager.SysMenuManager;
/**
 * 表格的工具栏
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class GridToolbarTag extends BodyTagSupport {
	
	/**
	 * 显示查询按钮
	 */
	private static Map<String,String> allButtonMap=new HashMap<String,String>();
	
	static{
		for(ToolbarButtonType type:ToolbarButtonType.values()){
			allButtonMap.put(type.name(), "true");
		}
	}
	//排除的按钮，用,分割标识列表
	private String excludeButtons="";
	//包括的按钮，用,分割标识列表
	private String includeButtons="";
	//是否授权按钮进行访问，若授权，则其是否允许访问需要通过，默认情况下是不需要授权均可访问
	private String isGranted="false";
	//样式
	private String style="border-bottom:0;margin:0;padding:0;";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 实体的类名
	 */
	private String entityName;
	
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	@Override
	public void setBodyContent(BodyContent b) {
		this.bodyContent=b;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		 JspWriter out=pageContext.getOut();
		 HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		 try{
			 	FreemarkEngine freemarkEngine=WebAppUtil.getBean(FreemarkEngine.class);
			 	SysMenuManager sysMenuManager=(SysMenuManager)WebAppUtil.getBean(SysMenuManager.class);
			 	 
				Map<String,Object> model=new HashMap<String,Object>();
				model.put("rootPath", request.getContextPath());
				model.put("entityName", entityName);
				model.put("style", style);
				//若需要授权，则根据授权的按钮进行显示处理
				if("true".equals(isGranted)){
					List<SysMenu> listButtons=sysMenuManager.getModuleButtons(entityName);
					//若系统中没有配置任何菜单，则包含的按钮就会作为必须显示的按钮
					if(listButtons.size()==0){
						if(StringUtils.isNotEmpty(includeButtons)){
							String[] ibuttons=includeButtons.split(",");
							for(String btn:ibuttons){
								model.put(btn, "true");
							}
						}
					}else{
						for(SysMenu sysMenu:listButtons){
							model.put(sysMenu.getKey(),"true");
						}
					}
				}else{//否则显示所有
					model.putAll(allButtonMap);
				}
				//检查是否有排除的按钮列表
				if(StringUtils.isNotEmpty(excludeButtons)){
					String[]ebuttons=excludeButtons.split(",");
					for(String btn:ebuttons){
						model.put(btn, "false");
					}
				}
				
				BodyContent content=getBodyContent();
				if(content!=null){
					String bodyContent=content.getString();
					Document doc = Jsoup.parse(bodyContent);
					//查找有该样式的控件
					Elements selfToobar=doc.select(".self-toolbar");
					model.put("extToolbars",selfToobar.html());
					Elements selfSearch=doc.select(".self-search");
					String selfSearchHtml=selfSearch.html();
					//使用自定义搜索
					if(StringUtils.isNotEmpty(selfSearchHtml)){
						model.put("selfSearch",selfSearchHtml);
						//屏蔽按字段的查询
						model.put(ToolbarButtonType.fieldSearch.name(),"false");
					}
					
				}
				String html=freemarkEngine.mergeTemplateIntoString("gridToolbar.ftl", model);
				out.println(html);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return EVAL_PAGE;
	}

	public String getExcludeButtons() {
		return excludeButtons;
	}

	public void setExcludeButtons(String excludeButtons) {
		this.excludeButtons = excludeButtons;
	}

	public String getIncludeButtons() {
		return includeButtons;
	}

	public void setIncludeButtons(String includeButtons) {
		this.includeButtons = includeButtons;
	}

	public String getIsGranted() {
		return isGranted;
	}

	public void setIsGranted(String isGranted) {
		this.isGranted = isGranted;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
