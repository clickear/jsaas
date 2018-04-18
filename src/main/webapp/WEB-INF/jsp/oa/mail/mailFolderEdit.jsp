<%-- 
    Document   : 邮箱文件夹编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>邮箱文件夹编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${mailFolder.folderId}" hideRecordNav="true" excludeButtons="">
	</rx:toolbar>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="folderId" class="mini-hidden" value="${mailFolder.folderId}" /> <input name="userId" value="${mailFolder.userId}" class="mini-hidden" vtype="maxLength:64" style="width: 90%" required="true" />

				<!--  <input class="mini-textbox" name="configId" value="${param['configId']}"/>-->
				<c:if test="${mailFolder.inOut=='OUT'}">
					<input name="configId" value="${mailFolder.configId}" class="mini-hidden" vtype="maxLength:64" style="width: 90%" required="true" />
				</c:if>

				<input name="parentId" value="${mailFolder.parentId}" class="mini-hidden" vtype="maxLength:64" style="width: 90%" allowInput="false" /> <input name="type" value="${mailFolder.type}" class="mini-hidden" vtype="maxLength:32" style="width: 90%" required="true" allowInput="false" /> <input name="depth" value="${mailFolder.depth}" class="mini-hidden" vtype="maxLength:10" style="width: 90%" required="true" /><input name="inOut" value="${mailFolder.inOut}" class="mini-hidden"
					vtype="maxLength:20" style="width: 90%" required="true" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>目录信息</caption>
					<tr>
						<th>文件夹名称 <span class="star">*</span> 
						</th>
						<td><input name="name" value="${mailFolder.name}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true" emptyText="请输入文件夹名称" /></td>
					</tr>

				</table>
			</div>
		</form>
	</div><!-- end of form -->
	<rx:formScript formId="form1" baseUrl="oa/mail/mailFolder" />
</body>
</html>