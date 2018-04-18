<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件查看</title>
</head>
<body>
	<h3>文件查看</h3>
	<br/>
	<div style="width:100%;margin:auto;">
		<img id="img" src="${ctxPath}/sys/core/file/previewImage.do?fileId=${fileId}"/>
	</div>
</body>
</html>