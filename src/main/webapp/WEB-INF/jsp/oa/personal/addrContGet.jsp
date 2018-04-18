<%-- 
    Document   : 通讯录联系人信息明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通讯录联系人信息明细</title>
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
				<caption>[AddrCont]基本信息</caption>
				<tr>
					<th>联系人ID：</th>
					<td>${addrCont.addrId}</td>
				</tr>
				<tr>
					<th>类型 手机号=MOBILE 家庭地址=HOME_ADDRESS 工作地址=WORK_ADDRESS QQ=QQ 微信=WEI_XIN GoogleTalk=GOOGLE-TALK 工作=WORK_INFO 备注=MEMO：</th>
					<td>${addrCont.type}</td>
				</tr>
				<tr>
					<th>联系主信息：</th>
					<td>${addrCont.contact}</td>
				</tr>
				<tr>
					<th>联系扩展字段1：</th>
					<td>${addrCont.ext1}</td>
				</tr>
				<tr>
					<th>联系扩展字段2：</th>
					<td>${addrCont.ext2}</td>
				</tr>
				<tr>
					<th>联系扩展字段3：</th>
					<td>${addrCont.ext3}</td>
				</tr>
				<tr>
					<th>联系扩展字段4：</th>
					<td>${addrCont.ext4}</td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/personal/addrCont" formId="form1" />
</body>
</html>