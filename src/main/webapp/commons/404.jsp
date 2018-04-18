<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/commons/dynamic.jspf"%>
<title>您访问的页面不存在</title>
<style type="text/css">
h1,h2,h3,h4,h5{font-weight: normal;margin: 0}
.content_box{width:90%;margin: 0 auto;text-align: center;}
.content_box h1{font-size: 42px;font-weight: normal;color: #666;margin: 40px 0 80px 0;font-family: '宋体';}
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
		 <h1>您访问的页面不存在!</h1>
		<div>
			<img src="${ctxPath}/styles/images/error.png" alt="出错"/>
		</div>
		<div>
			<dl class="content">
				<dt><h2>错误代码:<span>404</span></h2></dt>
				<dd><h3>可能原因:</h3></dd>
				<dd><h4>1、网络信号弱</h4></dd>
				<dd><h4>2、网站不正确</h4></dd>
				<dd><h4>3、找不到请求页面</h4></dd>
			</dl>
		</div>
		<h1 style="clear: both;"></h1>
	</div>
</body>
</html>