<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	 	<title>MiniUI 的控件自定义</title>
	<%@include file="/commons/edit.jsp"%>
	
	 	<link href="${ctxPath}/scripts/ueditor/form-design/css/toolbars.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd-config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-fd.all.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
	    <!-- 引入表单控件 -->
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/design-plugin.js"></script>
</head>
<body>
		<h2>表单设计器示例</h2>
		
		<div class="mini-toolbar" style="padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="textfield" plain="true" onclick="designer.execCommand('mini-textbox')">文本控件</a>
                        <a class="mini-button" iconCls="textfield" plain="true" onclick="designer.execCommand('mini-textarea')">多行文本控件</a>
                    </td>
                </tr>
          </table>
         </div>
       <script id="designer" type="text/plain" style="width:100%;height:500px;"></script>

	<p>描述：
		<br/>
			表单设计器中的文本控件示例
		<br/>
	</p>
	
	<script type="text/javascript">
	 mini.parse();
	 var designer = UE.getEditor('designer');
	</script>
</body>
</html>
