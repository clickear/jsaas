<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	 	<title>Rich-UEDITOR</title>
	  <%@include file="/commons/get.jsp"%>
	 	<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>    
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/use-mini-ueditor.js"></script>
</head>
<body>
	<h2>富文本控件UEditor封装后的使用方式,通过TextArea来替换</h2>
	<textarea name="content" id="content" class="mini-ueditor" style="width:1024px;height:250px;">xxxxx</textarea>
	<p>描述：
		<br/>
			UEditor封装后的使用方式设计及开发
		<br/>
	</p>
	<script type="text/javascript">
	 //mini.parse();
	 //var ue = UE.getEditor('editor');
	</script>
</body>
</html>
