<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/list.jsp"%>
<title>流程实例已经被删除！</title>
</head>
<body>

	<div style="width:100%;text-align: center">
		<table style="border:solid 1px #ccc;width:580px" align="center" >
			<tr>
				<td colspan="2" style="background-color: #aaa;border-bottom: solid 1px #ccc;height:36px;color:white;font-weight: bold;">消息提示</td>
			</tr>
			<tr>
				<td valign="top" width="200"><img src="${ctxPath}/styles/images/alert.png" alt="警告"/></td>
				<td width="*">
					<h2>流程实例已经被删除！</h2>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="mini-toolbar dialog-footer" style="border: none;">
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" style="margin-left:200px; margin-bottom: 80px">关闭</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>