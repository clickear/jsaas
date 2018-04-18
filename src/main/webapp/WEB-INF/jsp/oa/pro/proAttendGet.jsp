<%-- 
    Document   : 人员明细页
    Created on : 2015-3-28, 17:42:57
    Author     : cmc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>人员明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
        <rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">
       <div class="self-toolbar">
       <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
       </div>
       </rx:toolbar>
	<div id="form1" class="form-outer">
		<div>
			<table style="width: 100%" class="table-detail column_1" cellpadding="0"
				cellspacing="1">
				<caption>项目、产品人员参与信息</caption>
				<tr>
					<th>参 与 人</th>
					<td>${user}</td>
				</tr>
				<tr>
					<th>参 与 组</th>
					<td>${group}</td>
				</tr>
				<tr>
					<th>参与类型</th>
					<td>${proAttend.partType}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail column_2_m" style="width: 100%;" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创 建 人</th>
					<td><rxc:userLabel userId="${proAttend.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${proAttend.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更 新 人</th>
					<td><rxc:userLabel userId="${proAttend.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${proAttend.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/pro/proAttend" formId="form1"  entityName="com.redxun.oa.pro.entity.ProAttend"/>
</body>
</html>