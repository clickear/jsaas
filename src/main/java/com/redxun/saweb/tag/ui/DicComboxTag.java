package com.redxun.saweb.tag.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 *  数据字典下拉值
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class DicComboxTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//数据字典Key，必填
	private String dicKey;
	
	//数据字典Name,必填
	private String name;
	//控件ID，非必填
	private String id;
	//值，非必填
	private String value;
	//显示文本，非必填
	private String text="";
	//是否为必填，非必填
	private boolean required=false;
	//样式，非必填
	private String style="";
	//是否允许填写（对于combox生效），非必填
	private boolean allowInput=false;
	//是否允许空项，非必填
	private boolean showNullItem=false;
	//空项文件内容，非必填
	private String nullItemText="请选择...";
	//选择项更改时触发的事件
	private String onValueChanged;

	public int doStartTag() throws JspException{
		
		JspWriter writer=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		StringBuffer sb=new StringBuffer();
		try{
			
			SysTreeManager sysTreeManager=WebAppUtil.getBean(SysTreeManager.class);
			SysTree sysTree=sysTreeManager.getByKey(dicKey);
			
			if(sysTree==null){
				writer.write("<span class='control-error'>数据字典分类树中不存在此Key["+dicKey+"]</span>");
				return SKIP_BODY;
			}
			
			if(SysTree.SHOW_TYPE_FLAT.equals(sysTree.getDataShowType())){
				sb.append("<input ");
				if(StringUtils.isNotEmpty(id)){
					sb.append("id=\"").append(id).append("\"");
				}
				if (StringUtils.isNotEmpty(name)) {
					sb.append(" name=\"").append(name).append("\"");
				}
				sb.append(" class=\"mini-combobox\"  style=\"").append(style).append("\" emptyText=\"请选择...\"");
				if(StringUtils.isNotEmpty(onValueChanged)){
					sb.append(" onvaluechanged=\"").append(onValueChanged).append("\"");
				}
				
				sb.append(" textField=\"name\" valueField=\"value\"");
				sb.append(" url=\"").append(request.getContextPath()).append("/sys/core/sysDic/listByTreeId.do?treeId=").append(sysTree.getTreeId()).append("\"");

				sb.append("value=\"");
				sb.append(value);
				sb.append("\"  required=\"");
				sb.append(required);
				sb.append("\" allowInput=\"");
				sb.append(allowInput);
				sb.append("\" showNullItem=\"");
				sb.append(showNullItem);
				sb.append("\" nullItemText=\"");
				sb.append(nullItemText);
				sb.append("\"/>");
			}else{
				sb.append("<input ");
				if(StringUtils.isEmpty(id)){
					sb.append("id=\"").append(id).append("\"");
				}
				sb.append(" name=\"").append(name).append("\"");
				sb.append(" class=\"mini-treeselect\" url=\"");
				sb.append(request.getContextPath());
				sb.append("/sys/core/sysDic/listByTreeId.do?treeId=");
				sb.append(sysTree.getTreeId());
				sb.append(" valueField=\"value\" textField=\"name\" multiSelect=\"false\"");
				sb.append(" value=\"");
				sb.append(value).append("\" text=\"").append(text).append("\"").append(" style=\"").append(style).append("\"/>");
			}
			writer.write(sb.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SKIP_BODY;
	}
	public String getDicKey() {
		return dicKey;
	}
	
	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
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
	
	public boolean isAllowInput() {
		return allowInput;
	}
	
	public void setAllowInput(boolean allowInput) {
		this.allowInput = allowInput;
	}
	
	public boolean isShowNullItem() {
		return showNullItem;
	}
	
	public void setShowNullItem(boolean showNullItem) {
		this.showNullItem = showNullItem;
	}
	
	public String getNullItemText() {
		return nullItemText;
	}
	
	public void setNullItemText(String nullItemText) {
		this.nullItemText = nullItemText;
	}
	
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getOnValueChanged() {
		return onValueChanged;
	}
	public void setOnValueChanged(String onValueChanged) {
		this.onValueChanged = onValueChanged;
	}
	
	
}
