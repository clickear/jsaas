<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检测到外站连接</title>
<style type="text/css">
*{margin: 0;padding: 0;font-family: '宋体';font-weight: normal;color: #666;font-size: 16px;}
.content_box{width:90%;margin: 0 auto;text-align: center;}
.content_box h1{font-size: 42px;font-weight: normal;color: #666;margin: 40px 0 80px 0;}
.content_box>div{float: left;width: 50%;}
.content_box>div>img{width: 50%;display: block;margin: 0 auto;}
.content{width: 70%;padding-top: 60px;}
.content dt{margin-bottom: 40px;}
.content dt h2{font-size: 36px;text-indent: -40px;}
</style>
</head>
<body>	
	<div class="content_box">	
		 <h1>检测到外站连接</h1>
		<div>
			<img src="${ctxPath}/styles/images/error.png" alt="出错"/>
		</div>
		<div>
			<dl class="content">
				<dt><h2>检测到外站连接，请检查外链地址是否合法!</h2></dt>
			</dl>
		</div>
		<h1 style="clear: both;"></h1>
	</div>
</body>
</html>