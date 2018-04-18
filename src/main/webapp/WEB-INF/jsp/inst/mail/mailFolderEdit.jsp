<%-- 
    Document   : [MailFolder]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[MailFolder]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${mailFolder.folderId}"
		hideRecordNav="true" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="folderId" class="mini-hidden"
					value="${mailFolder.folderId}" /> <input name="userId"
					value="${mailFolder.userId}" class="mini-hidden"
					vtype="maxLength:64" style="width: 90%" required="true" />

				<!--  <input class="mini-textbox" name="configId" value="${param['configId']}"/>-->
				<input name="configId" value="${mailFolder.configId}"
					class="mini-hidden" vtype="maxLength:64" style="width: 90%"
					required="true" /> <input name="parentId"
					value="${mailFolder.parentId}" class="mini-hidden"
					vtype="maxLength:64" style="width: 90%" allowInput="false" /> <input
					name="type" value="${mailFolder.type}" class="mini-hidden"
					vtype="maxLength:32" style="width: 90%" required="true"
					allowInput="false" /> <input name="depth"
					value="${mailFolder.depth}" class="mini-hidden"
					vtype="maxLength:10" style="width: 90%" required="true" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>创建目录信息</caption>
					<tr>
						<th>文件夹名称 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${mailFolder.name}"
							class="mini-textbox" vtype="maxLength:128" style="width: 90%"
							required="true" emptyText="请输入文件夹名称" /></td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="inst/mail/mailFolder" />
</body>
</html>