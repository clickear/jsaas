<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流程图</title>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" rel="stylesheet" type="text/css"  />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/customFormUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$(".thisPlayButton").remove();
		initUser({ solId:"${solId}"});
	})
</script>
<style type="text/css">
	body{
		background-color: white;
	}
</style>
</head>
<body>
	
	<img src="${ctxPath}/bpm/activiti/processImage.do?actDefId=${actDefId}" usemap="#imgHref" style="border:0px;"/>
	<imgArea:imgAreaScript actDefId="${actDefId}"></imgArea:imgAreaScript>
	
</body>
</html>