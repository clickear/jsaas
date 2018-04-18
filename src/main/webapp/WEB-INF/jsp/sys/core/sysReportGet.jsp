<%-- 
    Document   : [SysReport]明细页
    Created on : 2015-3-28, 17:42:57
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[SysReport]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		<div>
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>报表基本信息</caption>
				<tr>
					<th style="width:100px;">报表分类</th>
					<td>${sysReport.sysTree.name}</td>
				</tr>
				<tr>
					<th>标　　题</th>
					<td>${sysReport.subject}</td>
				</tr>
				<tr>
					<th>标识key</th>
					<td>${sysReport.key}</td>
				</tr>
				<tr>
					<th>描　　述</th>
					<td>${sysReport.descp}</td>
				</tr>
				<tr>
					<th>jasper路径</th>
					<td>${sysReport.filePath}</td>
				</tr>
				<tr>
					<th>是否缺省</th>
					<td>${sysReport.defaults}</td>
				</tr>
				<tr>
					<th>报表解析引擎</th>
					<td>${sysReport.engine}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${sysReport.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${sysReport.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${sysReport.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${sysReport.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/core/sysReport" formId="form1" entityName="com.redxun.sys.core.entity.SysReport"/>
	<script type="text/javascript">
		addBody();
	</script>
	
</body>
</html>