<%-- 
    Document   : [DocRight]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[DocRight]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"
	type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${docRight.rightId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="rightId" class="mini-hidden"
					value="${docRight.rightId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[DocRight]基本信息</caption>

					<tr>
						<th>文档ID ：</th>
						<td><input name="docId" value="${docRight.docId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>文件夹ID ：</th>
						<td><input name="folderId" value="${docRight.folderId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>权限 文档或目录的读写修改权限 <span class="star">*</span> ：
						</th>
						<td><input name="rights" value="${docRight.rights}"
							class="mini-textbox" vtype="maxLength:10" style="width: 90%"
							required="true"
							emptyText="请输入权限" />

						</td>
					</tr>

					<tr>
						<th>用户ID ：</th>
						<td><input name="userId" value="${docRight.userId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>用户组ID ：</th>
						<td><input name="groupId" value="${docRight.groupId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/doc/docRight"  entityName="com.redxun.oa.doc.entity.DocRight"/>
</body>
</html>