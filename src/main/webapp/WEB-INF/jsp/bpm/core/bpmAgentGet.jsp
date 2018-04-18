<%-- 
    Document   : [BpmAgent]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[BpmAgent]明细</title>
<%@include file="/commons/get.jsp"%>
<style>
.form-title li{
	width: 25%;
}
</style>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>

	<div class="form-title">
		<h1>更新信息</h1>
		<ul>
			<li>
				<h2>创  建  人：<rxc:userLabel userId="${bpmAgent.createBy}" /></h2>
			</li>
			<li>
				<h2>更  新  人：<rxc:userLabel userId="${bpmAgent.updateBy}" /></h2>
			</li>
			<li>
				<h2>创建时间：<fmt:formatDate value="${bpmAgent.createTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
			<li>
				<h2>更新时间：<fmt:formatDate value="${bpmAgent.updateTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
		</ul>
	</div>
	<div class="shadowBox">
		<div id="form1" class="form-outer">
			<table style="width: 100%" class="table-detail column_1 " cellpadding="0" cellspacing="1">
				<caption>[BpmAgent]基本信息</caption>
				<tr>
					<th>代理简述</th>
					<td>${bpmAgent.subject}</td>
				</tr>
				<tr>
					<th>代理人ID</th>
					<td>${bpmAgent.toUserId}</td>
				</tr>
				<tr>
					<th>被代理人ID</th>
					<td>${bpmAgent.agentUserId}</td>
				</tr>
				<tr>
					<th>开始时间</th>
					<td>${bpmAgent.startTime}</td>
				</tr>
				<tr>
					<th>结束时间</th>
					<td>${bpmAgent.endTime}</td>
				</tr>
				<tr>
					<th>代理类型</th>
					<td>${bpmAgent.type}</td>
				</tr>
				<tr>
					<th>状　　态</th>
					<td>${bpmAgent.status}</td>
				</tr>
				<tr>
					<th>描　　述</th>
					<td>${bpmAgent.descp}</td>
				</tr>
			</table>
		
<%-- 			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${bpmAgent.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${bpmAgent.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${bpmAgent.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${bpmAgent.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table> --%>
			

			
			
			
			
		</div>
	
	
	</div>
	</div>
	<rx:detailScript baseUrl="bpm/core/bpmAgent"  entityName="com.redxun.bpm.core.entity.BpmAgent" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>