<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问拒绝</title>
<style type="text/css">
h1,h2,h3,h4,h5{font-weight: normal;margin: 0}
.content_box{width:90%;margin: 0 auto;text-align: center;}
.content_box h1{font-size: 42px;font-weight: normal;color: #666;margin: 40px 0 80px 0;font-family: '宋体';color: #666;}
.content_box>div{float: left;width: 50%;}
.content_box>div>img{width: 50%;display: block;margin: 0 auto;}
.content{width: 70%;padding-top: 60px;}
.content dt{margin-bottom: 40px;}
.content dt h2{font-size: 36px;text-indent: -40px;font-family: '宋体';color: #666;}
.content dt h2 span{color: red;font-size: 40px;font-family: '宋体';text-align: left;word-wrap:break-word;}
.content dd{text-align: left;margin: 20px 0 20px 160px;}
.content dd h3{font-size: 24px;font-family: '宋体';color: #666;}
.content dd h4{font-size: 16px;font-family: '微软雅黑';color: #666;}
</style>
</head>
<body>
	<div class="content_box">	
		 <h1>服务器上文件或目录拒绝访问</h1>
		<div>
			<img src="${ctxPath}/styles/images/error.png" alt="出错"/>
		</div>
		<div>
			<dl class="content">
				<dt><h2>错误代码:<span>403</span></h2></dt>
				<dd><h3>可能原因:</h3></dd>
				<dd><h4>1、该目录不允许执行程序</h4></dd>
				<dd><h4>2、"读取"访问被禁止而造成</h4></dd>
				<dd><h4>3、"写入"访问被禁止而造成</h4></dd>
				<dd><h4>4、由于要求SSL而造成</h4></dd>
				<dd><h4>5、由于IP 地址被拒绝而造成</h4></dd>
			</dl>
		</div>
		<h1 style="clear: both;"></h1>
	</div>
</body>
</html>

