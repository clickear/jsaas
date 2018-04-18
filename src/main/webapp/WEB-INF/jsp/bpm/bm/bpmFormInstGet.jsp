<%-- 
    Document   : [BpmFormInst]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmFormInst]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>[BpmFormInst]基本信息</caption>
				<tr>
					<th>实例标题：</th>
					<td>${bpmFormInst.subject}</td>
				</tr>
				<tr>
					<th>流程实例ID：</th>
					<td>${bpmFormInst.instId}</td>
				</tr>
				<tr>
					<th>ACT实例ID：</th>
					<td>${bpmFormInst.actInstId}</td>
				</tr>
				<tr>
					<th>ACT定义ID：</th>
					<td>${bpmFormInst.actDefId}</td>
				</tr>
				<tr>
					<th>流程定义ID：</th>
					<td>${bpmFormInst.defId}</td>
				</tr>
				<tr>
					<th>解决方案ID：</th>
					<td>${bpmFormInst.solId}</td>
				</tr>
				<tr>
					<th>数据模型ID：</th>
					<td>${bpmFormInst.fmId}</td>
				</tr>
				<tr>
					<th>表单视图ID：</th>
					<td>${bpmFormInst.fmViewId}</td>
				</tr>
				<tr>
					<th>状态：</th>
					<td>${bpmFormInst.status}</td>
				</tr>
				<tr>
					<th>数据JSON：</th>
					<td>${bpmFormInst.jsonData}</td>
				</tr>
				<tr>
					<th>是否持久化：</th>
					<td>${bpmFormInst.isPersist}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${bpmFormInst.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${bpmFormInst.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${bpmFormInst.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${bpmFormInst.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="bpm/bm/bpmFormInst" formId="form1" />
</body>
</html>