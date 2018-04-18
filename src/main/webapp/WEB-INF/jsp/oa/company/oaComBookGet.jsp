<%-- 
    Document   : [OaComBook]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通讯录明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
				<caption>通讯录信息</caption>
				<tr>
					<th width="20%">所属部门/组：</th>
					<td width="30%">${oaComBook.sysTree.name}</td>
					<th width="20%">是否内部员工：</th>
					<td width="30%">${oaComBook.isEmployee}</td>
				</tr>
				<tr>
					<th>姓名：</th>
					<td>${oaComBook.name}</td>
					<th >主部门：</th>
					<td >${oaComBook.depName}</td>
				<tr>
					<th>主手机号：</th>
					<td style="color: #b70000;">${oaComBook.mobile}</td>
					<th>副手机号：</th>
					<td>${oaComBook.mobile2}</td>
				</tr>
				<tr>
					<th>固话：</th>
					<td>${oaComBook.phone}</td>
					<th>邮件：</th>
					<td>${oaComBook.email}</td>
				</tr>
				<tr>
					<th>QQ：</th>
					<td colspan="3">${oaComBook.qq}</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td colspan="3">${oaCombook.remark}</td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/company/oaComBook" entityName="com.redxun.oa.company.entity.OaComBook" formId="form1" />
</body>
</html>