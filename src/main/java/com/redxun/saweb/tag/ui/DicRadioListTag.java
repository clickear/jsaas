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
 * 数据字典RadioList方式
 * 
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class DicRadioListTag extends TagSupport {
	// 控件ID
	private String id;
	// 控件Name
	private String name;
	// 数据字典项的Key
	private String dicKey;
	// 数据项行的数量
	private Integer repeatItems = 3;
	// 值
	private String value;
	
	//是否必填
	private boolean required=false;
	//提示空值
	private String emptyText="请输入值";
	//选择项更改时触发的事件
	private String onValueChanged;
	
	//是否必填
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
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

	public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer sb = new StringBuffer();
		try {
			SysTreeManager sysTreeManager = WebAppUtil.getBean(SysTreeManager.class);
			SysTree sysTree = sysTreeManager.getByKey(dicKey);
			if (sysTree == null) {
				writer.write("<span class='control-error'>数据字典分类树中不存在此Key[" + dicKey + "]</span>");
				return SKIP_BODY;
			}
			sb.append("<div ");
			if (StringUtils.isNotEmpty(id)) {
				sb.append(" id=\"").append(id).append("\"");
			}
			if (StringUtils.isNotEmpty(name)) {
				sb.append(" name=\"").append(name).append("\"");
			}
			sb.append(" class=\"mini-radiobuttonlist\"");
			if(StringUtils.isNotEmpty(onValueChanged)){
				sb.append(" onvaluechanged=\"").append(onValueChanged).append("\"");
			}
			sb.append(" required=\"").append(required).append("\"");
			sb.append(" emptyText=\"").append(emptyText).append("\"");
			sb.append(" repeatLayout=\"table\" repeatDirection=\"horizontal\"");//vertical
			sb.append(" repeatItems=\"").append(repeatItems).append("\"");
			
			sb.append(" textField=\"name\" valueField=\"value\"");
			sb.append(" url=\"").append(request.getContextPath()).append("/sys/core/sysDic/listByTreeId.do?treeId=").append(sysTree.getTreeId()).append("\"");
			
			sb.append(" value=\"").append(value).append("\"/>");
			
			writer.write(sb.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SKIP_BODY;
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

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public Integer getRepeatItems() {
		return repeatItems;
	}

	public void setRepeatItems(Integer repeatItems) {
		this.repeatItems = repeatItems;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
