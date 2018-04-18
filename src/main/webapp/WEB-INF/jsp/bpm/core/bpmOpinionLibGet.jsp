<%-- 
    Document   : [BpmOpinionLib]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>常用审批意见明细</title>
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
				<h2>创  建  人：<rxc:userLabel userId="${bpmOpinionLib.createBy}" /></h2>
			</li>
			<li>
				<h2>更  新  人：<rxc:userLabel userId="${bpmOpinionLib.updateBy}" /></h2>
			</li>
			<li>
				<h2>创建时间：<fmt:formatDate value="${bpmOpinionLib.createTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
			<li>
				<h2>更新时间：<fmt:formatDate value="${bpmOpinionLib.updateTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
		</ul>
	</div>

	<div class="shadowBox">
		<div id="form1" class="form-outer ">
			<div style="padding: 5px;">
				<table style="width: 100%" class="table-detail column_1" cellpadding="0"
					cellspacing="1">
					<caption>常用审批意见基本信息</caption>
					<tr>
						<th>用户Id</th>
						<td>${bpmOpinionLib.userId}</td>
					</tr>
					<tr>
						<th>审批意见</th>
						<td>${bpmOpinionLib.opText}</td>
					</tr>
				</table>
			</div>
			<%-- <div style="padding: 5px">
				<table class="table-detail column_2" cellpadding="0" cellspacing="1">
					<caption>更新信息</caption>
					<tr>
						<th>创建人</th>
						<td><rxc:userLabel userId="${bpmOpinionLib.createBy}" /></td>
						<th>创建时间</th>
						<td><fmt:formatDate value="${bpmOpinionLib.createTime}"
								pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
					<tr>
						<th>更新人</th>
						<td><rxc:userLabel userId="${bpmOpinionLib.updateBy}" /></td>
						<th>更新时间</th>
						<td><fmt:formatDate value="${bpmOpinionLib.updateTime}"
								pattern="yyyy-MM-dd HH:mm" /></td>
					</tr>
				</table>
			</div> --%>
		</div>
	</div>

	
	<rx:detailScript baseUrl="bpm/core/bpmOpinionLib"
		entityName="com.redxun.bpm.core.entity.BpmOpinionLib" formId="form1" />
		
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>