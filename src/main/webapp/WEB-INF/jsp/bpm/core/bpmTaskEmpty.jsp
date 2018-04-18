<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>待办事项已经被处理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div style="width:100%;text-align: center;margin:20px">
		<table style="border:solid 1px #ccc;width:580px" align="center">
			<tr>
				<td colspan="2" style="background-color: #aaa;border-bottom: solid 1px #ccc;height:36px;color:white;font-weight: bold;">消息提示</td>
			</tr>
			<tr>
				<td valign="top" width="200"><img src="${ctxPath}/styles/images/alert.png" alt="警告"/></td>
				<td width="*">
					<h2>该任务已经被执行完成！</h2>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding:20px;">
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow('ok')">关闭</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>