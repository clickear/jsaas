<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>待办事项不能处理</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body style="background: #fff;">

	<div style="width:100%;text-align: center;margin:100px auto;">
		<table style="border:solid 1px #ccc;width:580px" align="center">
			<tr>
				<td style="background-color: #eee;border-bottom: solid 1px #ccc;height:36px;font-weight: bold;font-size: 20px;">消息提示</td>
			</tr>
			<tr>
				
				<td width="*" height="200">
					<h2>${msg }</h2>
				</td>
			</tr>
		
		</table>
	</div>
</body>
</html>