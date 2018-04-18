<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,user-scalable=no" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/jquery_mobile/jquery.mobile-1.4.5.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery-1.11.3.js">
</script>
<script
	src="${pageContext.request.contextPath}/scripts/jquery_mobile/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
	/**
	 * 
	 */
	function listtalk() {
		var url = "${pageContext.request.contextPath}/pub/wxpub/test.do?pwd=" + 1;
		window.location.href = url;
	}
	/**
	 * 
	 */
	function listlive() {
		var url = "${pageContext.request.contextPath}/pub/wxpub/test.do?pwd=" + 1;
		window.location.href = url;
	}
	/**
	 * 
	 */
	function list() {
		var url = "${pageContext.request.contextPath}/pub/wxpub/test.do?pwd=1&&page=" + 1;
		window.location.href = url;
	}
</script>
</head>