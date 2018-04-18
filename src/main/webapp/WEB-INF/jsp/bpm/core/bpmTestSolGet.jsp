<%-- 
    Document   : 测试方案明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>测试方案明细</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer2">
		<div>
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>测试方案基本信息</caption>
				<tr>
					<th>方案编号：</th>
					<td>${bpmTestSol.testNo}</td>
				</tr>
				<tr>
					<th>解决方案ID：</th>
					<td>${bpmTestSol.solId}</td>
				</tr>
				<tr>
					<th>测试方案描述：</th>
					<td>${bpmTestSol.memo}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${bpmTestSol.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${bpmTestSol.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${bpmTestSol.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${bpmTestSol.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="bpm/core/bpmTestSol"
		entityName="com.redxun.bpm.core.entity.BpmTestSol" formId="form1" />
</body>
</html>