<%-- 
    Document   : [KdDocRem]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[KdDocRem]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${kdDocRem.remId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="remId" class="mini-hidden" value="${kdDocRem.remId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[KdDocRem]基本信息</caption>

					<tr>
						<th>文档名<span class="star">*</span> ：</th>
						<td><input name="docId" value="${kdDocRem.docId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入" /></td>
					</tr>

					<tr>
						<th>推荐部门ID：</th>
						<td><input name="depId" value="${kdDocRem.depId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>推荐个人ID：</th>
						<td><input name="userId" value="${kdDocRem.userId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>推荐级别：</th>
						<td><input name="level" value="${kdDocRem.level}" class="mini-textbox" vtype="maxLength:10" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>推荐理由：</th>
						<td><textarea name="memo" class="mini-textarea" vtype="maxLength:1024" style="width: 90%">${kdDocRem.memo}
														 </textarea></td>
					</tr>

					<tr>
						<th>推荐精华库分类ID ：</th>
						<td><input name="recTreeId" value="${kdDocRem.recTreeId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>是否通知创建人：</th>
						<td><input name="noticeCreator" value="${kdDocRem.noticeCreator}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>通知方式：</th>
						<td><input name="noticeType" value="${kdDocRem.noticeType}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdDocRem" entityName="com.redxun.kms.core.entity.KdDocRem" />
</body>
</html>