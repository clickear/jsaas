<%-- 
    Document   : 栏目类型明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>栏目类型明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer2">
		<div>
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>栏目类型基本信息</caption>
				<tr>
					<th>栏目名称：</th>
					<td>${insColType.name}</td>
				</tr>
				<tr>
					<th>栏目Key：</th>
					<td>${insColType.key}</td>
				</tr>
				<tr>
					<th>栏目映射URL：</th>
					<td>${insColType.url}</td>
				</tr>
				<tr>
					<th>栏目更多URL：</th>
					<td>${insColType.moreUrl}</td>
				</tr>
				<tr>
					<th>栏目分类描述：</th>
					<td>${insColType.memo}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${insColType.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${insColType.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${insColType.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${insColType.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/info/insColType" formId="form1" entityName="com.redxun.oa.info.entity.InsColType" />
</body>
</html>