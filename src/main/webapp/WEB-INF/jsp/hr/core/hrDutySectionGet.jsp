<%-- 
    Document   : [HrDutySection]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySection]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>[HrDutySection]基本信息</caption>
				<tr>
					<th>班次名称：</th>
					<td>${hrDutySection.sectionNmae}</td>
				</tr>
				<tr>
					<th>开始签到：</th>
					<td>${hrDutySection.startSignIn}</td>
				</tr>
				<tr>
					<th>上班时间：</th>
					<td>${hrDutySection.dutyStartTime}</td>
				</tr>
				<tr>
					<th>签到结束时间：</th>
					<td>${hrDutySection.endSignIn}</td>
				</tr>
				<tr>
					<th>早退计时：</th>
					<td>${hrDutySection.earlyOffTime}</td>
				</tr>
				<tr>
					<th>下班时间：</th>
					<td>${hrDutySection.dutyEndTime}</td>
				</tr>
				<tr>
					<th>签退结束：</th>
					<td>${hrDutySection.signOutTime}</td>
				</tr>
				<tr>
					<th>是否跨日：</th>
					<td>${hrDutySection.isTwoDay}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrDutySection.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrDutySection.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrDutySection.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrDutySection.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrDutySection"
		entityName="com.redxun.hr.core.entity.HrDutySection" formId="form1" />
</body>
</html>