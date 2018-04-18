<%-- 
    Document   : [SysDic]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[SysDic]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysDic.dicId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="pkId" class="mini-hidden" value="${sysDic.dicId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[SysDic]基本信息</caption>

					<tr>
						<th>分类Id ：</th>
						<td><input name="typeId" value="${sysDic.typeId}" class="mini-textbox" vtype="maxLength:64" /></td>
					</tr>

					<tr>
						<th>项Key ：</th>
						<td><input name="key" value="${sysDic.key}" class="mini-textbox" vtype="maxLength:64" /></td>
					</tr>

					<tr>
						<th>项名 <span class="star">*</span> ：
						</th>
						<td><input name="name" value="${sysDic.name}" class="mini-textbox" vtype="maxLength:64" required="true" emptyText="请输入项名" /></td>
					</tr>

					<tr>
						<th>项值 <span class="star">*</span> ：
						</th>
						<td><input name="value" value="${sysDic.value}" class="mini-textbox" vtype="maxLength:100" required="true" emptyText="请输入项值" /></td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td><textarea name="descp" class="mini-textarea" vtype="maxLength:256" style="width: 90%">${sysDic.descp}
														 </textarea></td>
					</tr>

					<tr>
						<th>序号 ：</th>
						<td><input name="sn" value="${sysDic.sn}" class="mini-textbox" vtype="maxLength:10" /></td>
					</tr>

					<tr>
						<th>路径 ：</th>
						<td><textarea name="path" class="mini-textarea" vtype="maxLength:256" style="width: 90%">${sysDic.path}
														 </textarea></td>
					</tr>

					<tr>
						<th>父ID ：</th>
						<td><input name="parentId" value="${sysDic.parentId}" class="mini-textbox" vtype="maxLength:64" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysDic" />
</body>
</html>