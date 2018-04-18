<%-- 
    Document   : 通讯录联系人信息编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>通讯录联系人信息编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${addrCont.contId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="contId" class="mini-hidden" value="${addrCont.contId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[AddrCont]基本信息</caption>

					<tr>
						<th>联系人ID ：</th>
						<td><input name="addrId" value="${addrCont.addrId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>类型 手机号=MOBILE 家庭地址=HOME_ADDRESS 工作地址=WORK_ADDRESS QQ=QQ 微信=WEI_XIN GoogleTalk=GOOGLE-TALK 工作=WORK_INFO 备注=MEMO <span class="star">*</span> ：
						</th>
						<td><input name="type" value="${addrCont.type}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" required="true" emptyText="请输入类型 手机号=MOBILE  家庭地址=HOME_ADDRESS   工作地址=WORK_ADDRESS  QQ=QQ  微信=WEI_XIN  GoogleTalk=GOOGLE-TALK  工作=WORK_INFO 备注=MEMO" /></td>
					</tr>

					<tr>
						<th>联系主信息 ：</th>
						<td><input name="contact" value="${addrCont.contact}" class="mini-textbox" vtype="maxLength:255" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>联系扩展字段1 ：</th>
						<td><input name="ext1" value="${addrCont.ext1}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>联系扩展字段2 ：</th>
						<td><input name="ext2" value="${addrCont.ext2}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>联系扩展字段3 ：</th>
						<td><input name="ext3" value="${addrCont.ext3}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>联系扩展字段4 ：</th>
						<td><input name="ext4" value="${addrCont.ext4}" class="mini-textbox" vtype="maxLength:100" style="width: 90%" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/personal/addrCont" />
</body>
</html>