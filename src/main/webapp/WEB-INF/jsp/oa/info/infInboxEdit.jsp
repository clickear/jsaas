<%-- 
    Document   : 内部短消息收件箱编辑页,暂时没用
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[InfInbox]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${infInbox.recId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="recId" class="mini-hidden" value="${infInbox.recId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>内部短消息基本信息</caption>

					<tr>
						<th>消息ID <span class="star">*</span> ：
						</th>
						<td><input name="msgId" value="${infInbox.msgId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入消息ID" /></td>
					</tr>

					<tr>
						<th>接收人ID ：</th>
						<td><input name="recUserId" value="${infInbox.recUserId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>接收人名称 ：</th>
						<td><input name="fullname" value="${infInbox.fullname}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>用户组ID：</th>
						<td><input name="groupId" value="${infInbox.groupId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>组名 ：</th>
						<td><input name="groupName" value="${infInbox.groupName}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>是否阅读 ：</th>
						<td><input name="isRead" value="${infInbox.isRead}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>是否删除 ：</th>
						<td><input name="isDel" value="${infInbox.isDel}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/infInbox" />
</body>
</html>