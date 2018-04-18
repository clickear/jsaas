<%-- 
    Document   : 没有外部邮箱账号配置处理页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>你还没有设置邮箱账号配置</title>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>
<body>
	<div>
        <div id="p1" class="form-outer" style="padding:10px;height:200px;width:800px;margin-left: auto;margin-right: auto;line-height: 32px;">
      		<div style="text-align:center">
	      		<h1 style="color:red">
	      			您还没有增加邮箱的账号设置！
	      		</h1>
	      		<p>
	      			请您配置您的外部邮箱的连接信息，<a href="#">配置邮箱</a>
	      		</p>
        	</div>
		</div>
	</div>
<script type="text/javascript">
	var __rootPath='${ctxPath}';
	/*点击事件处理*/
	$("a").on("click",function(){
		_OpenWindow({
			url:__rootPath+"/oa/mail/mailConfig/edit.do",
			title : '添加账号配置',
			width : 720,
			height : 600,
			ondestroy : function(action) {
					if(action=='ok')
						window.location=__rootPath+"/oa/mail/mailConfig/getAllConfig.do";
					}
			});
	});
</script>
</body>
</html>