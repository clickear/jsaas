<%-- 
    Document   : [OaComRight]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[OaComRight]编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${oaComRight.rightId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="rightId" class="mini-hidden" value="${oaComRight.rightId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[OaComRight]基本信息</caption>

					<tr>
						<th>文档ID <span class="star">*</span> ：
						</th>
						<td><input name="combookId" value="${oaComRight.combookId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入文档ID" /></td>
					</tr>

					<tr>
						<th>授权类型<span class="star">*</span> ：
						</th>
						<td><input name="identityType" value="${oaComRight.identityType}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入授权类型" /></td>
					</tr>

					<tr>
						<th>用户或组ID <span class="star">*</span> ：
						</th>
						<td><input name="identityId" value="${oaComRight.identityId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入用户或组ID" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/company/oaComRight" entityName="com.redxun.oa.company.entity.OaComRight" />
</body>
</html>