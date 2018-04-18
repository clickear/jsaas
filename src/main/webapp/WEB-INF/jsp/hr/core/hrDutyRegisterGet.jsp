<%-- 
    Document   : [HrDutyRegister]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutyRegister]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>[HrDutyRegister]基本信息</caption>
				<tr>
					<th>登记时间：</th>
					<td>${hrDutyRegister.registerTime}</td>
				</tr>
				<tr>
					<th>登记标识 1=正常登记（上班，下班） 2＝迟到 3=早退 4＝休息 5＝旷工 6=放假：</th>
					<td>${hrDutyRegister.regFlag}</td>
				</tr>
				<tr>
					<th>迟到或早退分钟 正常上班时为0：</th>
					<td>${hrDutyRegister.regMins}</td>
				</tr>
				<tr>
					<th>迟到原因：</th>
					<td>${hrDutyRegister.reason}</td>
				</tr>
				<tr>
					<th>周几：</th>
					<td>${hrDutyRegister.dayofweek}</td>
				</tr>
				<tr>
					<th>上下班标识 1=签到 2=签退：</th>
					<td>${hrDutyRegister.inOffFlag}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrDutyRegister.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrDutyRegister.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrDutyRegister.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrDutyRegister.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrDutyRegister"
		entityName="com.redxun.hr.core.entity.HrDutyRegister" formId="form1" />
</body>
</html>