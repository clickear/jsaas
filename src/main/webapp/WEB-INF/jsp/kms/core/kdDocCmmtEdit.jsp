<%-- 
    Document   : [KdDocCmmt]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[KdDocCmmt]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${kdDocCmmt.commentId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="commentId" class="mini-hidden" value="${kdDocCmmt.commentId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[KdDocCmmt]基本信息</caption>

					<tr>
						<th>知识ID ：</th>
						<td><input name="docId" value="${kdDocCmmt.docId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>分数 <span class="star">*</span> ：
						</th>
						<td><input name="score" value="${kdDocCmmt.score}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" required="true" emptyText="请输入分数" /></td>
					</tr>

					<tr>
						<th>点评内容 ：</th>
						<td><textarea name="content" class="mini-textarea" vtype="maxLength:1024" style="width: 90%">${kdDocCmmt.content}</textarea></td>
					</tr>

					<tr>
						<th>点评：</th>
						<td><input name="level" value="${kdDocCmmt.level}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdDocCmmt" entityName="com.redxun.kms.core.entity.KdDocCmmt" />
</body>
</html>