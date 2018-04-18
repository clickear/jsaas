<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<title>UEDITOR</title>
	 	<%@include file="/commons/get.jsp"%>
		<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor-mini.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/ueditor.all.min.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<h2>富文本控件UEditor</h2>
	
       <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
	
	
	<p>描述：
		<br/>
			使用uEditor进行设计及开发
		<br/>
	</p>
	
	<script type="text/javascript">
	 mini.parse();
	 var ue = UE.getEditor('editor');
	</script>
</body>
</html>
