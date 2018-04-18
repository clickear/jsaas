<%-- 
    Document   : 门户明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>门户明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>门户基本信息</caption>
				<tr>
					<th>门户名称：</th>
					<td>${insPortal.name}</td>
				</tr>
				<tr>
					<th>门户KEY：</th>
					<td>${insPortal.key}</td>
				</tr>
				<tr>
					<th>是否为系统缺省：</th>
					<td>${insPortal.isDefault}</td>
				</tr>
				<tr>
					<th>列数：</th>
					<td>${insPortal.colNums}</td>
				</tr>
				<tr>
					<th>栏目宽：</th>
					<td>${insPortal.colWidths}</td>
				</tr>
				<tr>
					<th>门户栏目：</th>
					<td>${colName}</td>
				</tr>
				<tr>
					<th>描述：</th>
					<td>${insPortal.desc}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${insPortal.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${insPortal.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${insPortal.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${insPortal.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/info/insPortal" formId="form1" />
</body>
</html>