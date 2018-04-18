package com.redxun.saweb.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.redxun.saweb.filter.SecurityUtil;

/**
 * 功能权限标签。
 * <br>
 * 功能：这个标签配合权限分配，可以实现页面上的按钮是否可以点击操作，将权限操作控制到按钮。
 * <ul>
 *  <li>标签中 有个别名属性，系统根据该别名控制当前用户是否有该操作的的权限。</li>
 *  <li>标签的使用需要在tld文件和web.xml中进行配置。</li>
 *  <li>权限标签的写法:&lt;f:a alias="user_add" iconCls="icon-add" href="addUser.do">添加&lt;/f:a&gt;</li>
 *  <li>权限标签的写法:&lt;f:a alias="user_add" iconCls="icon-add" tag="span" href="addUser.do">添加&lt;/f:a&gt;</li>
 * </ul>
 */
@SuppressWarnings("serial")
public class PermissionTag extends BodyTagSupport {
	
	/** 链接的样式class */
	private String iconCls;
	/** 链接的 别名*/
	private String alias;
	/** 链接的 name*/
	private String id;
	/** 链接的 href*/
	private String href;
	/** 链接的 onclick*/
	private String onclick;
	/** 链接的 目标*/
	private String target;
	/**
	 * 标题
	 */
	private String title="";
	
	/**
	 * 标签名称。
	 */
	private String tag="a";
	
	/**
	 * 当没有权限的时候超链接是否显示。
	 */
	private boolean showNoRight=true;

	

	public void setTarget(String target) {
		this.target = target;
	}
	
	public void setShowNoRight(boolean _isShowNoRight){
		this.showNoRight=_isShowNoRight;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}


	public void setId(String id) {
		this.id = id;
	}

	public void setHref(String href) {
		this.href = href;
	}

	
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
	
	

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}
	
	

	private String getOutput() throws Exception{
		boolean canAccess =SecurityUtil.hasPermission(this.alias);
		/**
		 * 当没有权限不显示返回空串。
		 */
		if(!showNoRight && !canAccess){
			return "";
		}
		String body="";
		BodyContent  bodyContent = this.getBodyContent();
		if(bodyContent!=null){
			body=bodyContent.getString();
		}
		StringBuffer content = null;
		if("a".equals(this.tag)){
			content=getLinkTag(canAccess, body);
		}
		else{
			content=getSpanTag(canAccess, body);
		}
		return content.toString();
	}
	
	private StringBuffer getLinkTag(boolean canAccess,String body){
		StringBuffer content = new StringBuffer("<a class=\"mini-button\" ");
		if(id != null) {
			content.append("id=\"" + id + "\" ");
		}
		if(target != null){
			content.append("target=\"" + target + "\" ");
		}
		if(iconCls != null) {
			content.append(" iconCls=\"" + iconCls + "\" ");
		}
		if(title != null) {
			content.append(" title=\"" + this.title + "\" ");
		}
		
		//可以访问的情况。
		if(canAccess) {
			if(href != null) {
				content.append(" href=\"" + href + "\" ");
			}
			if(onclick != null) {
				content.append("onclick=\"" + onclick + "\" ");
			}
		} 
		else {
			content.append(" enabled=\"false\" ");
		}
		
		content.append(">");
		content.append(body);
		content.append("</a>");
		
		return content;
	}
	
	private StringBuffer getSpanTag(boolean canAccess,String body){
		StringBuffer content = new StringBuffer("<span class=\""+this.iconCls+"\" ");
		if(id != null) {
			content.append("id=\"" + id + "\" ");
		}
		
		if(title != null) {
			content.append(" title=\"" + this.title + "\" ");
		}
		
		//可以访问的情况。
		if(canAccess) {
			if(onclick != null) {
				content.append("onclick=\"" + onclick + "\" ");
			}
		} 
		else {
			content.append(" enabled=\"false\" ");
		}
		
		content.append(">");
		content.append(body);
		content.append("</span>");
		
		return content;
	}
	
	
	

	public int doEndTag() throws JspTagException {

		try {
			String str = getOutput();
			pageContext.getOut().print(str);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

}
