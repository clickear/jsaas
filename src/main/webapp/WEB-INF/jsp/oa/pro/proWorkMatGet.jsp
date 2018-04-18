<%-- 
    Document   : 动态明细页
    Created on : 2015-3-28, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>[ProWorkMat]明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
<rx:toolbar toolbarId="toolbar1" hideRecordNav="true" excludeButtons="remove">
       <div class="self-toolbar">
       <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
       </div>
       </rx:toolbar>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_1" cellpadding="0" cellspacing="1">
			<tr>
					<th width="50">评论人</th>
					<td><rxc:userLabel userId="${proWorkMat.createBy}" /></td>
			 </tr>
			<tr>	
				<th width="70">评论时间</th>
				<td><fmt:formatDate value="${proWorkMat.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		   </tr>
				
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_1" cellpadding="0" cellspacing="1">
		<tr>
				<th width="50">评论内容</th>
					<td>${proWorkMat.content}</td>
		</tr>
	     <tr>
				 <th>附件列表</th>
		        <td colspan="3">
			 <c:forEach items="${file}" var="file">
				<a href="${ctxPath}/sys/core/file/previewFile.do?fileId=${file.key}">${file.value}</a>&nbsp;&nbsp;
			</c:forEach>
		     </td>
	     </tr>
			</table>
		</div>
	</div>

	
	<rx:detailScript baseUrl="oa/pro/proWorkMat" formId="form1"  entityName="com.redxun.oa.pro.entity.ProWorkMat"/>
</body>
</html>