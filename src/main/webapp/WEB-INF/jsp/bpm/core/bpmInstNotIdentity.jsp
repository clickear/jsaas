<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/get.jsp"%>
<title>${title }</title>
</head>
<body>

	<div style="width:100%;text-align: center">
		<table style="border:solid 1px #ccc;width:580px" align="center">
			<tr>
				<td colspan="2" style="background-color: #aaa;border-bottom: solid 1px #ccc;height:36px;color:white;font-weight: bold;">消息提示</td>
			</tr>
			<tr>
				<td valign="top" width="200"><img src="${ctxPath}/styles/images/alert.png" alt="警告"/></td>
				<td width="*">
					<h2 style="color:red">${title }</h2>
					<br/>
					<span style="color:red">${message}</span>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>