<%-- 
    Document   : 任务提示消息明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>任务提示消息明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>任务提示消息基本信息</caption>
				 
				<tr>
					<th>发送者名称：</th>
					<td><rxc:userLabel userId="${taskTipMsg.senderId}" /></td>
				</tr>
				<tr>
					<th>发送时间：</th>
					<td>${taskTipMsg.senderTime}</td>
				</tr>
				<tr>
					<th>接收者名称：</th>
					<td><rxc:userLabel userId="${taskTipMsg.receiverId}" /></td>
				</tr>
				<tr>
					<th>主题：</th>
					<td>${taskTipMsg.subject}</td>
				</tr>
				<tr>
					<th>内容：</th>
					<td>${taskTipMsg.content}</td>
				</tr>
				<tr>
					<th>链接：</th>
					<td>${taskTipMsg.linked}</td>
				</tr>
				<tr>
					<th>撤销：</th>
					<td>${taskTipMsg.isinvalid}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${taskTipMsg.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${taskTipMsg.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${taskTipMsg.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${taskTipMsg.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="bpm/core/taskTipMsg"
		entityName="com.redxun.bpm.core.entity.TaskTipMsg" formId="form1" />
</body>
</html>