package com.redxun.saweb.tag.ui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * mini ui 的基础数据
 * 
 * @author mansan
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 * 
 */
public abstract class AbstractMiniDataControl extends TagSupport {
	
	protected Log logger=LogFactory.getLog(AbstractMiniDataControl.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 控件名,非必填
	protected String name;
	// 控件ID，非必填
	protected String id;
	// 值，非必填
	protected String value;
	// 显示文本，非必填
	protected String text = "";
	// 是否为必填，非必填
	protected boolean required = false;
	// 样式，非必填
	protected String style = "";
	// 若没选择时，提示的信息
	protected String emptyText = "请选择...";
	// 选择项更改时触发的事件
	protected String onValueChanged;
	// 初始化数据
	protected String data="";
	
	/**
	 * 返回mini控件名,目前支持mini-combo,mini-radiolist,mini-checkboxlist
	 * 
	 * @return
	 */
	public abstract String getControlType();

	/***
	 * 返回扩展的属性,拼接的属性字符串，如 showNullItem='true' showEmpty='true'
	 * 
	 * @return
	 */
	public abstract String getExtAttrs();
	/**
	 * 生成标签
	 */
	public int doStartTag() throws JspException{
		JspWriter writer=pageContext.getOut();
		StringBuffer sb=new StringBuffer();
		try{
			sb.append("<input ").append(" class=\"").append(getControlType()).append("\"");
			if(StringUtils.isNotEmpty(id)){
				sb.append("id=\"").append(id).append("\"");
			}
			if (StringUtils.isNotEmpty(name)) {
				sb.append(" name=\"").append(name).append("\"");
			}
			sb.append(" emptyText=\"").append(emptyText).append("\"");
			if(StringUtils.isNotEmpty(onValueChanged)){
				sb.append(" onvaluechanged=\"").append(onValueChanged).append("\"");
			}
			sb.append(" textField=\"text\" valueField=\"id\"");
			sb.append(" data=\"").append(getData()).append("\"");
			if(StringUtils.isNotEmpty(value)){
				sb.append(" value=\"").append(value).append("\"");
			}
			sb.append(" required=\"");
			sb.append(required);
			if(StringUtils.isNotEmpty(style)){
				sb.append(" style=\"").append("\"");
			}
			//加下子类扩展的属性
			String extAtts=getExtAttrs();
			if(StringUtils.isNotEmpty(extAtts)){
				sb.append(" ").append(extAtts);
			}
			sb.append("\"/>");
			writer.write(sb.toString());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	public String getOnValueChanged() {
		return onValueChanged;
	}

	public void setOnValueChanged(String onValueChanged) {
		this.onValueChanged = onValueChanged;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
