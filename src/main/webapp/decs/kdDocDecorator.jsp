<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/commons/dynamic.jspf" %>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />


<style type="text/css">
.topMenuItemLink{
	text-decoration: none;
	color: #fff;
}
</style>
<sitemesh:write property='head' />
</head>

<body >
	<div class="nav maincolor">
        <ul>
            <li class="active"><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/home.do?i=0">首页</a></li>
            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/index.do?i=1">知识仓库</a></li>
            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/mapIndex.do?i=2">知识地图</a></li>
            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdQuestion/index.do?i=3">知识问答</a></li>
            <li><a class="topMenuItemLink" href="${ctxPath}/kms/core/kdDoc/personal.do?i=4">个人中心</a></li>
        </ul>
    </div>
    <script type="text/javascript">
    	$(function(){
    		var index="${param['i']}";
    		$(".nav").find("li").each(function(i){
    			   $(this).removeClass("active");
    		 });
    		$(".nav").find("li").eq(index).addClass("active");
    	});
    </script>
    
	<sitemesh:write property='body' />
</body>
</html>