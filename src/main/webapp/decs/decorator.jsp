<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><sitemesh:write property='title' /></title>
	 	<%@include file="/commons/dynamic.jspf" %>
	 	<sitemesh:write property='head' />
    	<meta http-equiv="X-UA-Compatible" content="IE=8">
</head>
<body style="visibility:hidden;">
	<sitemesh:write property='body' />
	<script type="text/javascript">
	document.body.style.visibility = "visible";
	
	$(document).ajaxComplete(function (evt, request, settings) {
		var action=request.getResponseHeader('exceptionaction');
		if(!action) return;
		
		if(action=="timeout"){
			top.location = '${ctxPath}/login.jsp';
		}
		else if(action=="403"){
			top._ShowTips({
        		msg:"你没有权限访问该页面!"
        	});
		}
		
	
	});

	if(window==top){
		top['index']=window;
	}
	function resizeAfter(){
		if(mini){
			mini.layout();
		}
	}
	$(window).resize(function(){
    	setTimeout('',500);
    });
	
	</script>
</body>
</html>