<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片预览</title>
<%@include file="/commons/dynamic.jspf"%>
</head>
<body>
	<div style="width:100%;text-align: center;padding:5px 5px 5px 5px;">
	<c:choose>
		<c:when test="${sysFile.from=='APPLICATION'}">
			<img src="${ctxPath}/${sysFile.path}"/>	
		</c:when>
		<c:otherwise>
			<img class="file-image" src="${ctxPath}/sys/core/file/imageView.do?fileId=${sysFile.fileId}" />
		</c:otherwise>
	</c:choose>
	</div>
</body>
</html>