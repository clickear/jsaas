<%-- 
    Document   : [HrDutySystem]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySystem]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>[HrDutySystem]基本信息</caption>
				<tr>
					<th>名称：</th>
					<td>${hrDutySystem.name}</td>
				</tr>
				<tr>
					<th>是否缺省 1＝缺省 0＝非缺省：</th>
					<td>${hrDutySystem.isDefault}</td>
				</tr>
				<tr>
					<th>分类 一周=WEEKLY 周期性=PERIODIC：</th>
					<td>${hrDutySystem.type}</td>
				</tr>
				<tr>
					<th>作息班次：</th>
					<td>${hrDutySystem.workSection}</td>
				</tr>
				<tr>
					<th>休息日加班班次：</th>
					<td>${hrDutySystem.wkRestSection}</td>
				</tr>
				<tr>
					<th>休息日：</th>
					<td>${hrDutySystem.restSection}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrDutySystem.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrDutySystem.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrDutySystem.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrDutySystem.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrDutySystem"
		entityName="com.redxun.hr.core.entity.HrDutySystem" formId="form1" />
</body>
</html>