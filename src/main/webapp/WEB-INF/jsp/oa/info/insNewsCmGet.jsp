<%-- 
    Document   : 新闻评论明细页,暂时没用
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>新闻评论明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		<div>
			<table style="width: 100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
				<caption>新闻评论基本信息</caption>
				<tr>
					<th>信息ID</th>
					<td>${insNewsCm.newId}</td>
				</tr>
				<tr>
					<th>评论人名</th>
					<td>${insNewsCm.fullName}</td>
				</tr>
				<tr>
					<th>评论内容</th>
					<td>${insNewsCm.content}</td>
				</tr>
				<tr>
					<th>赞同与顶</th>
					<td>${insNewsCm.agreeNums}</td>
				</tr>
				<tr>
					<th>反对与鄙视次数</th>
					<td>${insNewsCm.refuseNums}</td>
				</tr>
				<tr>
					<th>是否为回复</th>
					<td>${insNewsCm.isReply}</td>
				</tr>
				<tr>
					<th>回复评论ID</th>
					<td>${insNewsCm.repId}</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创  建  人</th>
					<td><rxc:userLabel userId="${insNewsCm.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${insNewsCm.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更  新  人</th>
					<td><rxc:userLabel userId="${insNewsCm.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${insNewsCm.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/info/insNewsCm" formId="form1" entityName="com.redxun.oa.info.entity.InsNewsCm"/>
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>