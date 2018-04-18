<%-- 
    Document   : 附件明细
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>附件明细</title>
<%@include file="/commons/get.jsp"%>
<!-- <style>body{height:100%;}</style> -->
</head>
<body>
	<!-- <div class="mini-toolbar mini-toolbar-bottom">
		<table style="width:100%">
			<tr>
				<td style="width:100%;text-align:right;">
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a>					
				</td>
			</tr>
		</table>
	</div> -->
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		<div>
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>附件信息</caption>
				<tr>
					<th style="width:15% !important;">附件名称</th>
					<td colspan="3"><a href="${ctxPath}/sys/core/file/previewFile.do?fileId=${sysFile.fileId}">${sysFile.fileName}</a></td>
				</tr>
				<tr>
					<th>上 传 人</th>
					<td><rxc:userLabel userId="${sysFile.createBy}" /></td>
					<th style="width:15% !important;">上传时间</th>
					<td><fmt:formatDate value="${sysFile.createTime}" /> </td>
				</tr>
				<tr>
					<th>文件大小</th>
					<td colspan="3">${sysFile.sizeFormat}</td>
				</tr>
			</table>
		</div>
	</div>
	
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>