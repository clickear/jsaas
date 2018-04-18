<%-- 
    Document   : 业务流程解决方案明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<span class="toolbar_right"><rx:toolbar toolbarId="toolbar1" excludeButtons="remove"/></span > --%>
	<div style="height: 1px"></div>
	<div id="form1" class="form-outer shadowBox" style="margin-top: 40px">
		<div class="form-inner">
			<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
				<caption>业务流程解决方案基本信息</caption>
				<tr>
					<th>解决方案名称</th>
					<td>${bpmSolution.name}</td>

					<th>标  识  键</th>
					<td>${bpmSolution.key}</td>
				</tr>
				<tr>
					<th>绑定流程KEY</th>
					<td>${bpmSolution.defKey}</td>
					<th>状　　态</th>
					<td>${bpmSolution.status}</td>
				</tr>
				<tr>
					<th>解决方案描述</th>
					<td colspan="3">${bpmSolution.descp}</td>
				</tr>
			</table>
		
			<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${bpmSolution.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${bpmSolution.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${bpmSolution.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${bpmSolution.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript entityName="com.redxun.bpm.core.entity.BpmSolution"
	baseUrl="bpm/core/bpmSolution" formId="form1" />
	
	<script type="text/javascript">
		addBody();	
	</script>
</body>
</html>