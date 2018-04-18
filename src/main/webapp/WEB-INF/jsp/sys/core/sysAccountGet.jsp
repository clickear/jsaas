<%-- 
    Document   : 账号明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>账号明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox"">
		<div>
			<input type="hidden" id="pkId" name="accountId" value="${sysAccount.accountId}"/>
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>账号基本信息</caption>
				<tr>
					<th>账号名称</th>
					<td>${sysAccount.name}</td>

					<th>加密算法</th>
					<td>${sysAccount.encType}</td>
				</tr>
				<tr>
					<th>用  户  名</th>
					<td>${sysAccount.fullname}</td>
					<th>绑定用户ID</th>
					<td>${sysAccount.userId}</td>
				</tr>
				<tr>
					<th>备　　注</th>
					<td colspan="3">${sysAccount.remark}</td>
				</tr>
				<tr>
					<th>状　　态 </th>
					<td colspan="3">${sysAccount.status}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${sysAccount.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${sysAccount.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${sysAccount.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${sysAccount.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/core/sysAccount" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>