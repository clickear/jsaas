<%-- 
    Document   : [KdQuestionAnswer]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[KdQuestionAnswer]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${kdQuestionAnswer.answerId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="answerId" class="mini-hidden" value="${kdQuestionAnswer.answerId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[KdQuestionAnswer]基本信息</caption>

					<tr>
						<th>问题ID <span class="star">*</span> ：
						</th>
						<td><input name="queId" value="${kdQuestionAnswer.queId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入问题ID" /></td>
					</tr>

					<tr>
						<th><span class="star">*</span> ：</th>
						<td><textarea name="replyContent" class="mini-textarea" vtype="maxLength:65535" style="width: 90%" required="true" emptyText="请输入">${kdQuestionAnswer.replyContent}
														 </textarea></td>
					</tr>

					<tr>
						<th>：</th>
						<td><input name="isBest" value="${kdQuestionAnswer.isBest}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>

					<tr>
						<th><span class="star">*</span> ：</th>
						<td><input name="authorId" value="${kdQuestionAnswer.authorId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="kms/core/kdQuestionAnswer" />
</body>
</html>