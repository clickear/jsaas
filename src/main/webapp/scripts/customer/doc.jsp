<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>预览office文档</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@include file="/commons/dynamic.jspf" %>
    <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
    <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
    <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
    <script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.js"	></script>
    <script src="${ctxPath}/scripts/common/baiduTemplate.js" type="text/javascript"></script>
    <script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
    <style type="text/css">
	    .mini-office .mini-panel-footer{
			word-break:break-all !important;
		}
		html,body{
			height: 100%;
			width: 100%;
		}
    </style>
</head>
<body>  
	<div id="officeControl" class="mini-office" style="height:100%;width:100%" readonly="true" name="office" rights="print,printSetting"></div>
</body>
</html>
<script type="text/javascript">
var fileId="${param.fileId}";
mini_useShims=true;
mini.parse();

$(function(){
	var officeCtl=mini.get("officeControl");
	officeCtl.loadFile(fileId);
});
</script>