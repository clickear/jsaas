<%-- 
    Document   : [OaMeetRoom]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>[OaMeetRoom]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div>
			<table style="width: 100%" class="table-detail column_1" cellpadding="0"
				cellspacing="1">
				<caption>会议室基本信息</caption>
				<tr>
					<th>会议室名称</th>
					<td>${oaMeetRoom.name}</td>
				</tr>
				<tr>
					<th>会议室地址</th>
					<td>${oaMeetRoom.location}</td>
				</tr>
				<tr>
					<th>会议室描述</th>
					<td>${oaMeetRoom.descp}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${oaMeetRoom.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${oaMeetRoom.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${oaMeetRoom.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${oaMeetRoom.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/res/oaMeetRoom"
		entityName="com.redxun.oa.res.entity.OaMeetRoom" formId="form1" />
</body>
</html>