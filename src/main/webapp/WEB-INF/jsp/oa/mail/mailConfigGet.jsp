<%-- 
    Document   : 外部邮箱账号明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>外部邮箱账号明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" >
		<div class="self-toolbar">
			<a class="mini-button"  iconCls="icon-edit" plain="true" onclick="edit">编辑</a>
		</div>
	</rx:toolbar>
	<div id="form1" class="form-outer mini-toolbar-icons">
		<div>
			<table style="width: 100%" class="table-detail column_1" cellpadding="0"
				cellspacing="1">
				<caption>邮箱账号配置信息</caption>
				<tr>
					<th style="width: 25%;">帐号名称</th>
					<td>${mailConfig.account}</td>
				</tr>
				<tr>
					<th>外部邮件地址</th>
					<td>${mailConfig.mailAccount}</td>
				</tr>
				<tr>
					<th>外部邮件密码</th>
					<td>${mailConfig.mailPwd}</td>
				</tr>
				<tr>
					<th>协议类型 IMAP POP3</th>
					<td>${mailConfig.protocol}</td>
				</tr>
				<tr>
					<th>邮件发送主机</th>
					<td>${mailConfig.smtpHost}</td>
				</tr>
				<tr>
					<th>邮件发送端口</th>
					<td>${mailConfig.smtpPort}</td>
				</tr>
				<tr>
					<th>接收主机</th>
					<td>${mailConfig.recpHost}</td>
				</tr>
				<tr>
					<th>接收端口</th>
					<td>${mailConfig.recpPort}</td>
				</tr>
				<tr>
					<th>是否默认 YES NO</th>
					<td>${mailConfig.isDefault}</td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/mail/mailConfig" formId="form1" entityName="com.redxun.oa.mail.entity.MailConfig" />
	<script type="text/javascript">
		var pkId='${mailConfig.configId}';
		/*编辑当前邮箱账号配置*/
		function edit(){
			 window.location.href=__rootPath+'/oa/mail/mailConfig/edit.do?pkId='+pkId;
		}
	</script>
</body>
</html>