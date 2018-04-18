<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/commons/get.jsp"%>
<title>请求包含非法字符</title>
<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	}
	table.table-form{
		border: 1px solid silver;
		text-align: center;
		margin: 80px auto;
		
	}
	
	table.table-form caption{
		border-top: 1px solid silver;
		border-left: 1px solid silver;
		border-right: 1px solid silver;
		background-color: #f8f8f8;
	}
</style>
</head>
<body>
	<table style="width: 80%"  cellpadding="0" class="table-form" cellspacing="1">
		<caption>检测到Xss攻击</caption>
		<tr>
			<td style="height:100px;">
				检测到Xss攻击，请检查提交的数据!
			</td>
		</tr>
	</table>
</body>
</html>