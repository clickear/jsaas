<%-- 
    Document   : 产品属性明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品属性明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"　type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>产品属性基本信息</caption>
				<tr>
					<th>参数KEY主键：</th>
					<td>${oaProductDefParaValue.keyId}</td>
				</tr>
				<tr>
					<th>分类Id：</th>
					<td>${oaProductDefParaValue.treeId}</td>
				</tr>
				<tr>
					<th>名称：</th>
					<td>${oaProductDefParaValue.name}</td>
				</tr>
				<tr>
					<th>数字类型：</th>
					<td>${oaProductDefParaValue.number}</td>
				</tr>
				<tr>
					<th>字符串类型：</th>
					<td>${oaProductDefParaValue.string}</td>
				</tr>
				<tr>
					<th>文本类型：</th>
					<td>${oaProductDefParaValue.text}</td>
				</tr>
				<tr>
					<th>时间类型：</th>
					<td>${oaProductDefParaValue.datetime}</td>
				</tr>
				<tr>
					<th>描述：</th>
					<td>${oaProductDefParaValue.desc}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${oaProductDefParaValue.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate
							value="${oaProductDefParaValue.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${oaProductDefParaValue.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate
							value="${oaProductDefParaValue.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/product/oaProductDefParaValue"
		entityName="com.redxun.oa.product.entity.OaProductDefParaValue"
		formId="form1" />
</body>
</html>