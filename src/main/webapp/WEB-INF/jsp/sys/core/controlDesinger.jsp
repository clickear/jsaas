<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/ueditor/form-design/css/toolbars.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd-config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 引入表单控件 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/design-plugin.js"></script>
<title>控件设计器</title>
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;">
	    <table style="width:100%;">
	        <tr>
	        <td style="width:100%;">
	            <a class="mini-button" iconCls="icon-ok" plain="true" onclick="CloseWindow('ok')">确定</a>
	           	<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
	        </td>
	        </tr>
	    </table>
	</div>	
	
	<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="4">
				<div class="mini-toolbar" style="padding:0px;border-top:none;">
		            <table style="width:100%;">
		                <tr>
		                    <td style="width:100%;" id="controlTd">
		                        <a class="mini-button" iconCls="icon-textbox" plain="true" onclick="exePlugin('mini-textbox')">文本控件</a>
		                        <a class="mini-button" iconCls="icon-textarea" plain="true" onclick="exePlugin('mini-textarea')">多行文本控件</a>
		                        <a class="mini-button" iconCls="icon-checkbox" plain="true" onclick="exePlugin('mini-checkbox')">复选框</a>
		                        <a class="mini-button" iconCls="icon-radiobuttonlist" plain="true" onclick="exePlugin('mini-radiobuttonlist')">单选按钮列表</a>
		                        <a class="mini-button" iconCls="icon-checkboxlist" plain="true" onclick="exePlugin('mini-checkboxlist')">复选按钮列表</a>
		                       	<a class="mini-button" iconCls="icon-combobox" plain="true" onclick="exePlugin('mini-combobox')">下拉框控件</a>
		                        <a class="mini-button" iconCls="icon-datepicker" plain="true" onclick="exePlugin('mini-datepicker')">日期控件</a>
		                        <a class="mini-button" iconCls="icon-monthpicker" plain="true" onclick="exePlugin('mini-monthpicker')">月份控件</a>
		                        <a class="mini-button" iconCls="icon-timespinner" plain="true" onclick="exePlugin('mini-timespinner')">时间输入框</a>
		                        <a class="mini-button" iconCls="icon-spinner" plain="true" onclick="exePlugin('mini-spinner')">数据输入框</a>
		                        <a class="mini-button" iconCls="icon-ueditor" plain="true" onclick="exePlugin('mini-ueditor')">富文本输入框</a>
		                        <a class="mini-button" iconCls="icon-user" plain="true" onclick="exePlugin('mini-user')">用户选择框</a>
		                        <a class="mini-button" iconCls="icon-group" plain="true" onclick="exePlugin('mini-group')">用户组选择框</a>
		                        <a class="mini-button" iconCls="icon-upload" plain="true" onclick="exePlugin('upload-panel')">上传控件</a>
		                        <a class="mini-button" iconCls="icon-hidden" plain="true" onclick="exePlugin('mini-hidden')">隐藏域控件</a>
		                        <a class="mini-button" iconCls="icon-buttonedit" plain="true" onclick="exePlugin('mini-buttonedit')">按钮输入框</a>
		                        <a class="mini-button" iconCls="icon-dep" plain="true" onclick="exePlugin('mini-dep')">部门选择控件</a>
		                        <a class="mini-button" iconCls="rx-grid" plain="true" onclick="exePlugin('rx-grid')">表格控件</a>
		                        <a class="mini-button" iconCls="icon-treeselect" plain="true" onclick="exePlugin('mini-treeselect')">下拉树选择控件</a>
		                        <a class="mini-button" iconCls="icon-btn" plain="true" onclick="exePlugin('mini-button')">自定义按钮</a>
		                        <a class="mini-button" iconCls="icon-textboxlist" plain="true" onclick="exePlugin('mini-textboxlist')">文本盒子</a>
							                        
		                    </td>
		                </tr>
		          </table>
		        </div>
				<script id="codeView" name="codeView" type="text/plain" style="width:100%;height:500px;"></script>
			</td>
		</tr>
	</table>
<script type="text/javascript">
	mini.parse();
	var codeView = UE.getEditor('codeView');
	
	function getModelId(){
		return '';	
	}
	function exePlugin(pluginName){
		codeView.execCommand(pluginName);
	}
	
	function getContent(){
		return codeView.getContent();
	}
	
	function setContent(text){
		codeView.setContent(text);
	}
</script>
</body>
</html>