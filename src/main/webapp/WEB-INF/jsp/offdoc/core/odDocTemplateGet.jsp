<%-- 
    Document   : [OdDocTemplate]明细页
    Created on : 2016-3-8, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文模板明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"　type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<link href="${ctxPath}/scripts/ueditor/form-design/form-def.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1"  excludeButtons="remove"/>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>模板基本信息</caption>
				<tr>
					<th>模板名称：</th>
					<td>${odDocTemplate.name}</td>
					<th>模板描述：</th>
					<td>${odDocTemplate.descp}</td>
				</tr>
				<tr>
					<th>模板状态：</th>
					<td>${odDocTemplate.status}</td>
					<th>模板类型：</th>
					<td>${odDocTemplate.tempType}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${odDocTemplate.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${odDocTemplate.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${odDocTemplate.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${odDocTemplate.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
		<div style=" width:auto; height:530px;">
   
    </div>
	</div>
	<script type="text/javascript">
	mini.parse();

	
</script>
	<rx:detailScript baseUrl="offdoc/core/odDocTemplate" formId="form1" entityName="com.redxun.offdoc.core.entity.OdDocTemplate"  />
</body>
</html>