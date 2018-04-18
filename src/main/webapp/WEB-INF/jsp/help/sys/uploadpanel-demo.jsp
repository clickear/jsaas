<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	 <title>上传附件</title>
	 <%@include file="/commons/dynamic.jspf" %>
	 <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
	 <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
	  <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
	 <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
	 
	 <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script> 
	 <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
	<!-- 加上扩展控件的支持 -->
	
	<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
</head>
<body>
	<h2>上传单个图片示例</h2>
	<form id="form1">
		<table align="center">
			<tr>
				<td>上传控件</td>
				<td>
					<input class="mini-hidden" name="xxx"/>
					<input name="axbc" class="upload-panel rxc" allowupload="true" label="xxx" fname="cc" allowlink="true" zipdown="true" />
				</td>
			</tr>
		</table>
		<a class="mini-button" onclick="getData()">submit</a>
	</form>

	<script type="text/javascript">
		mini.parse();
		//var form1=new mini.Form('form1');
		function getData(){
			var data=_GetFormJson('form1');
			alert(mini.encode(data));
		}
		$(function() {
			
			parseGridPlugins();
			mini.parse();
			parseExtendPlugins();
			mini.layout();
			
		});
	</script>
</body>
</html>
