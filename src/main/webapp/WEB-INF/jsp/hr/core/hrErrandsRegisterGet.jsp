<%-- 
    Document   : [HrErrandsRegister]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrErrandsRegister]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>[HrErrandsRegister]基本信息</caption>
				<%-- <tr>
					<th>描述：</th>
					<td>${hrErrandsRegister.desc}</td>
				</tr> --%>
				<tr>
					<th>开始日期：</th>
					<td>${hrErrandsRegister.startTime}</td>
				</tr>
				<tr>
					<th>结束日期：</th>
					<td>${hrErrandsRegister.endTime}</td>
				</tr>
				<tr>
					<th>标识：</th>
					<td>${hrErrandsRegister.flag}</td>
				</tr>
				<tr>
					<th>流程实例ID：</th>
					<td>${hrErrandsRegister.bpmInstId}</td>
				</tr>
				<tr>
					<th>类型：</th>
					<td>${hrErrandsRegister.type}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${hrErrandsRegister.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${hrErrandsRegister.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${hrErrandsRegister.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${hrErrandsRegister.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="hr/core/hrErrandsRegister"
		entityName="com.redxun.hr.core.entity.HrErrandsRegister"
		formId="form1" />
</body>
</html>