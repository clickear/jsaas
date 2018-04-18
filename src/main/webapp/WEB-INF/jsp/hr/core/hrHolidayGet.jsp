<%-- 
    Document   : [HrHoliday]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrHoliday]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>[HrHoliday]基本信息</caption>
				<tr>
					<th>开始日期：</th>
					<td>${hrHoliday.startDay}</td>
				</tr>
				<tr>
					<th>结束日期：</th>
					<td>${hrHoliday.endDay}</td>
				</tr>
				<tr>
					<th>假期描述：</th>
					<td>${hrHoliday.desc}</td>
				</tr>
				<tr>
					<th>所属用户 若为某员工的假期，则为员工自己的id：</th>
					<td>${hrHoliday.userId}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrHoliday.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrHoliday.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrHoliday.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrHoliday.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrHoliday"
		entityName="com.redxun.hr.core.entity.HrHoliday" formId="form1" />
</body>
</html>