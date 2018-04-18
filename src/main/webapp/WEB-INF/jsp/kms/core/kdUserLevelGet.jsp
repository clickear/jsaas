<%-- 
    Document   : [KdUserLevel]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdUserLevel]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
			<caption>等级基本信息</caption>
			<tr>
				<th>起  始  值</th>
				<td>${kdUserLevel.startVal}</td>
			</tr>
			<tr>
				<th>结  束  值</th>
				<td>${kdUserLevel.endVal}</td>
			</tr>
			<tr>
				<th>等级名称</th>
				<td>${kdUserLevel.levelName}</td>
			</tr>
			<tr>
				<th>头像Icon</th>
				<td>${kdUserLevel.headerIcon}</td>
			</tr>
			<tr>
				<th>备　　注</th>
				<td>${kdUserLevel.memo}</td>
			</tr>
		</table>
	</div>
	<rx:detailScript baseUrl="kms/core/kdUserLevel" entityName="com.redxun.kms.core.entity.KdUserLevel" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>