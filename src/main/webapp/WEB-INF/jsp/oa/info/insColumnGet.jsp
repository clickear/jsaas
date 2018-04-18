<%-- 
    Document   : 栏目明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>栏目明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer2">
		<div>
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>栏目基本信息</caption>
				<tr>
					<th>栏目名称：</th>
					<td>${insColumn.name}</td>

					<th>栏目Key：</th>
					<td>${insColumn.key}</td>
				</tr>
				<tr>
					<th>是否启用：</th>
					<td>${insColumn.enabled}</td>
					<th>是否允许关闭：</th>
					<td>${insColumn.allowClose}</td>
				</tr>
				<tr>
					<th>每页记录数：</th>
					<td>${insColumn.numsOfPage}</td>
					<th>信息栏目类型：</th>
					<td>${insColumn.colType}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${insColumn.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${insColumn.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${insColumn.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${insColumn.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/info/insColumn" formId="form1" entityName="com.redxun.oa.info.entity.InsColumn" />
</body>
</html>