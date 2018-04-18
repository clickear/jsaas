<%-- 
    Document   : 内部邮件收件箱编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>内部邮件收件箱编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${mailBox.boxId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="boxId" class="mini-hidden" value="${mailBox.boxId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>内部邮件收件箱基本信息</caption>

					<tr>
						<th>邮件ID ：</th>
						<td><input name="mailId" value="${mailBox.mailId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>文件夹ID ：</th>
						<td><input name="folderId" value="${mailBox.folderId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>员工ID ：</th>
						<td><input name="userId" value="${mailBox.userId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>删除标识=YES <span class="star">*</span> ：
						</th>
						<td><input name="isDel" value="${mailBox.isDel}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入删除标识=YES" /></td>
					</tr>

					<tr>
						<th>阅读标识 <span class="star">*</span> ：
						</th>
						<td><input name="isRead" value="${mailBox.isRead}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入阅读标识" /></td>
					</tr>

					<tr>
						<th>回复标识 <span class="star">*</span> ：
						</th>
						<td><input name="reply" value="${mailBox.reply}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入回复标识" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/mail/mailBox" />
</body>
</html>