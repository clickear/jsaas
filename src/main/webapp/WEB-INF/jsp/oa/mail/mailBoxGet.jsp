<%-- 
    Document   : 内部邮件收件箱明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>内部邮件收件箱明细</title>
        <%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
				<caption>内部邮件收件箱基本信息</caption>
				<tr>
					<th>邮件ID：</th>
					<td>${mailBox.mailId}</td>
				</tr>
				<tr>
					<th>文件夹ID：</th>
					<td>${mailBox.folderId}</td>
				</tr>
				<tr>
					<th>员工ID：</th>
					<td>${mailBox.userId}</td>
				</tr>
				<tr>
					<th>删除标识=YES：</th>
					<td>${mailBox.isDel}</td>
				</tr>
				<tr>
					<th>阅读标识：</th>
					<td>${mailBox.isRead}</td>
				</tr>
				<tr>
					<th>回复标识：</th>
					<td>${mailBox.reply}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${mailBox.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${mailBox.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${mailBox.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${mailBox.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/mail/mailBox" formId="form1" />
</body>
</html>