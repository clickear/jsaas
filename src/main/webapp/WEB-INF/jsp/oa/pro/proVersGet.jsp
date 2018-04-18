<%-- 
    Document   : 版本明细页
    Created on : 2015-12-18, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>版本明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
		</div>
	</rx:toolbar>
	<div id="form1" class="form-outer shadowBox90">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_2_m" cellpadding="0"
				cellspacing="1">
				<caption>版本基本信息</caption>
				<tr>
					<th>版  本  号</th>
					<td>${proVers.version}</td>
					<th>状　　态</th>
					<td>${proVers.status}</td>
				</tr>
				<tr>
					<th>描　　述</th>
					<td colspan="5">${proVers.descp}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${proVers.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${proVers.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${proVers.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${proVers.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/pro/proVers" formId="form1"  entityName="com.redxun.oa.pro.entity.ProVers"/>
</body>
</html>