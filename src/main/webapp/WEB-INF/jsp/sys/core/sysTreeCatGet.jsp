<%-- 
    Document   : 系统树分类明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>系统树分类明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">

		<table style="width: 100%" class="table-detail column_2"
			cellpadding="0" cellspacing="1">
			<caption>机构基本信息</caption>
			<tr>
				<th><span class="starBox"> 分类 Key<span class="star">*</span>
				</span></th>
				<td>${sysTreeCat.key}</td>
			</tr>
			<tr>
				<th><span class="starBox"> 分类名称 <span class="star">*</span>
				</span></th>
				<td>${sysTreeCat.name}</td>
			</tr>
			<tr>
				<th>序　　 号</th>
				<td>${sysTreeCat.sn}</td>
			</tr>
			<tr>
				<th>描　　述</th>
				<td>${sysTreeCat.descp}</td>
			</tr>
		</table>

		<table class="table-detail column_2" cellpadding="0" cellspacing="1">
			<caption>更新信息</caption>
			<tr style="width: 100%;">
				<th>创 建 人</th>
				<td><rxc:userLabel userId="${sysTreeCat.createBy}" /></td>
				<th>创建时间</th>
				<td><fmt:formatDate value="${sysTreeCat.createTime}"
						pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
			<tr>
				<th>更 新 人</th>
				<td><rxc:userLabel userId="${sysTreeCat.updateBy}" /></td>
				<th>更新时间</th>
				<td><fmt:formatDate value="${sysTreeCat.updateTime}"
						pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
		</table>
	</div>
	<rx:detailScript baseUrl="sys/core/sysTreeCat" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>


</body>
</html>