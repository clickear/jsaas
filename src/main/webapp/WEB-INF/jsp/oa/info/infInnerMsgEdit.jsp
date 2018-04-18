<%-- 
    Document   : 内部短消息编辑页,暂时没用
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[InfInnerMsg]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${infInnerMsg.msgId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="msgId" class="mini-hidden" value="${infInnerMsg.msgId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>内部短消息基本信息</caption>

					<tr>
						<th>消息内容 <span class="star">*</span> ：
						</th>
						<td><textarea name="content" class="mini-textarea" vtype="maxLength:512" style="width: 90%" required="true" emptyText="请输入消息内容">${infInnerMsg.content}</textarea></td>
					</tr>

					<tr>
						<th>消息分类 ：</th>
						<td><input name="category" value="${infInnerMsg.category}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>发送人名 ：</th>
						<td><input name="sender" value="${infInnerMsg.sender}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/infInnerMsg" />
</body>
</html>