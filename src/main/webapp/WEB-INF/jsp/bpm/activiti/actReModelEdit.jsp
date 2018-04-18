<%-- 
    Document   : 流程定义模型编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程定义模型编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${actReModel.id}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="id" class="mini-hidden" value="${actReModel.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>流程定义模型基本信息</caption>
					<tr>
						<th>名称 ：</th>
						<td>
							<input name="name" value="${actReModel.name}" class="mini-textbox" vtype="maxLength:255" style="width: 90%"/>
						</td>
					</tr>
					<c:if test="${empty actReModel.id}">
					<tr>
						<th>描述 ：</th>
						<td><textarea name="description" class="mini-textarea" vtype="maxLength:255" style="width: 90%"></textarea></td>
					</tr>
					</c:if>
					<tr>
						<th>分类Tag ：</th>
						<td><textarea name="category" class="mini-textarea" vtype="maxLength:255" style="width: 90%">${actReModel.category}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/activiti/actReModel" />
</body>
</html>