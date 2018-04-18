<%-- 
    Document   : 用户组维度明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<html>
<head>
<title>用户组维度明细</title>
<%@include file="/commons/list.jsp"%>
<style>
.form-title li{
	width: 25%;
}

.shadowBox table{
	border-collapse: collapse;
}
</style>

</head>
<body>
	<div class="form-title">
		<h1>更新信息</h1>
		<ul>
			<li>
				<h2>创  建  人：<rxc:userLabel userId="${osDimension.createBy}" /></h2>
			</li>
			<li>
				<h2>更  新  人：<rxc:userLabel userId="${osDimension.updateBy}" /></h2>
			</li>
			<li>
				<h2>创建时间：<fmt:formatDate value="${osDimension.createTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
			<li>
				<h2>更新时间：<fmt:formatDate value="${osDimension.updateTime}" pattern="yyyy-MM-dd HH:mm" /></h2>
			</li>
		</ul>
	</div>

	<div id="form1" class="form-outer">
		<div class="shadowBox">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="0">
				<caption>用户组维度基本信息</caption>
				<tr>
					<th>维度名称</th>
					<td>${osDimension.name}</td>
					<th>维度业务主键</th>
					<td>${osDimension.dimKey}</td>
				</tr>
				<tr>
					<th>是否组合维度</th>
					<td>${osDimension.isCompose}</td>
					<th>是否系统预设维度</th>
					<td>${osDimension.isSystem}</td>
				</tr>
				<tr>
					<th>状　　态 </th>
					<td>${osDimension.status}</td>
					<th>序　　号</th>
					<td>${osDimension.sn}</td>
				</tr>
				<tr>
					<th>数据展示类型</th>
					<td>${osDimension.showType}</td>
				
					<th>是否参与授权</th>
					<td>${osDimension.isGrant}</td>
				</tr>
				<tr>
					<th>描　　述</th>
					<td colspan="3">${osDimension.desc}</td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/org/osDimension" formId="form1" />
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>