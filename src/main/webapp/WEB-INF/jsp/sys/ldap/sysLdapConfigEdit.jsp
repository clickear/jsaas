<%-- 
    Document   : LDAP配置编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>LDAP配置编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1"
		pkId="${sysLdapConfig.sysLdapConfigId}" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="sysLdapConfigId" class="mini-hidden"
					value="${sysLdapConfig.sysLdapConfigId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>LDAP配置基本信息</caption>

					<tr>
						<th>地址 ：</th>
						<td><input name="url" class="mini-textbox"
							vtype="maxLength:1024" style="width: 90%"
							value="${sysLdapConfig.url}"> </input></td>
					</tr>

					<tr>
						<th>账号名称 ：</th>
						<td><input name="account" value="${sysLdapConfig.account}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>


					<tr>
						<th>密码 ：</th>
						<td><input name="password" value="${sysLdapConfig.password}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>基本DN ：</th>
						<td><input name="dnBase" class="mini-textbox"
							vtype="maxLength:1024" style="width: 90%"
							value="${sysLdapConfig.dnBase}"> </input></td>
					</tr>

					<tr>
						<th>基准DN ：</th>
						<td><input name="dnDatum" class="mini-textbox"
							vtype="maxLength:1024" style="width: 90%"
							value="${sysLdapConfig.dnDatum}"> </input></td>
					</tr>



					<tr>
						<th>用户过滤搜索器：</th>
						<td><input name="userFilter" value="${sysLdapConfig.userFilter}"
							class="mini-textbox" vtype="maxLength:1024" style="width: 90%" /></td>
					</tr>



					<tr>
						<th>用户编号属性 ：</th>
						<td><input name="attUserNo"
							value="${sysLdapConfig.attUserNo}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>用户账户属性 ：</th>
						<td><input name="attUserAcc"
							value="${sysLdapConfig.attUserAcc}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>用户名称属性 ：</th>
						<td><input name="attUserName"
							value="${sysLdapConfig.attUserName}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>用户密码属性 ：</th>
						<td><input name="attUserPwd" class="mini-textbox"
							vtype="maxLength:1024" style="width: 90%"
							value="${sysLdapConfig.attUserPwd}"> </input></td>
					</tr>

					<tr>
						<th>用户电话属性 ：</th>
						<td><input name="attUserTel"
							value="${sysLdapConfig.attUserTel}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>用户邮件属性 ：</th>
						<td><input name="attUserMail"
							value="${sysLdapConfig.attUserMail}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>部门名称属性 ：</th>
						<td><input name="attDeptName"
							value="${sysLdapConfig.attDeptName}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>
<!--
					<tr>
						<th>状态码 ：</th>
						<td><input allowInput="false" name="status"
							value="${sysLdapConfig.status}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>状态信息 ：</th>
						<td><input allowInput="false" name="statusCn"
							value="${sysLdapConfig.statusCn}" class="mini-textbox"
							vtype="maxLength:64" style="width: 90%" emptyText="请输入状态" /></td>
					</tr>
					  -->
<tr>

  
					<th>状态码：</th>
					<td>${sysLdapConfig.status}</td>
				</tr>
				<tr>
					<th>状态信息：</th>
					<td>${sysLdapConfig.statusCn}</td>
				</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/ldap/sysLdapConfig"
		entityName="com.redxun.sys.ldap.entity.SysLdapConfig" />
</body>
</html>