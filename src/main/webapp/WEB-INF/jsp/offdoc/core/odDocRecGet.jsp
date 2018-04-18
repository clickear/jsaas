<%-- 
    Document   : [OdDocRec]明细页
    Created on : 2016-3-8, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[OdDocRec]明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1"  excludeButtons="remove"/>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>接收公文基本信息</caption>
				<%-- <tr>
					<th>收发文ID：</th>
					<td>${odDocRec.docId}</td>
				</tr> --%>
				<tr>
					<th>收文部门ID：</th>
					<td><a class="mini-group"  groupId="${odDocRec.recDepId}"></a></td>
				</tr>
				<tr>
					<th>收文单位类型：</th>
					<td>${odDocRec.recDtype}</td>
				</tr>
				<tr>
					<th>是否阅读：</th>
					<td>${odDocRec.isRead}</td>
				</tr>
				<tr>
					<th>阅读时间：</th>
					<td>${odDocRec.readTime}</td>
				</tr>
				
				<tr>
					<th>签收人：</th>
					<td><a class="mini-user"  userId="${odDocRec.signUsrId}"></a></td>
				</tr>
				<tr>
					<th>签收状态：</th>
					<td>${odDocRec.signStatus}</td>
				</tr>
				<tr>
					<th>签收时间：</th>
					<td>${odDocRec.signTime}</td>
				</tr>
				<tr>
					<th>反馈意见：</th>
					<td>${odDocRec.feedBack}</td>
				</tr>
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${odDocRec.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${odDocRec.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${odDocRec.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${odDocRec.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
_LoadUserInfo();_LoadGroupInfo();
</script>
	<rx:detailScript baseUrl="offdoc/core/odDocRec"  entityName="com.redxun.offdoc.core.entity.OdDocRec" formId="form1" />
</body>
</html>