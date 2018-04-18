<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="${ctxPath}/scripts/cut/jquery-1.4.2.min.js"></script>


<link rel="stylesheet" href="${ctxPath}/scripts/cut/map/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-ui.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-notes_1.0.8.js"></script>



</head>
<body>

  <a href="" title="">
	<img src="${ctxPath}/scripts/cut/flower2.jpg?add=true" alt="" class="jquery-note"
		width="300" height="300" />
	</a>
  <script type="text/javascript">
$(function() {
	
 	$('.jquery-note').jQueryNotes({
		operator:"${ctxPath}/kms/core/kdDoc/getPoint.do"
	});
});
</script>
  
 
  
	
</body>
</html>