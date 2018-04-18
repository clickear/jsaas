<%-- 
    Document   : [HrDuty]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDuty]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>[HrDuty]基本信息</caption>
				<tr>
					<th>班制编号：</th>
					<td>${hrDuty.systemId}</td>
				</tr>
				<tr>
					<th>开始时间：</th>
					<td>${hrDuty.startTime}</td>
				</tr>
				<tr>
					<th>结束时间：</th>
					<td>${hrDuty.endTime}</td>
				</tr>
				<tr>
					<th>使用者ID：</th>
					<td>${hrDuty.userId}</td>
				</tr>
				<tr>
					<th>用户组ID：</th>
					<td>${hrDuty.groupId}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrDuty.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrDuty.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrDuty.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrDuty.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrDuty" entityName="com.redxun.hr.core.entity.HrDuty" formId="form1" />
</body>
</html>