<%-- 
    Document   : LDAP配置明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>LDAP配置明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" />
	<div id="form1" class="form-outer2">
		<div>
			<table style="width: 100%" class="table-detail" cellpadding="0"
				cellspacing="1">
				<caption>LDAP配置基本信息</caption>
				
				<tr>
					<th>地址：</th>
					<td>${sysLdapConfig.url}</td>
				</tr>
				<tr>
					<th>账号名称：</th>
					<td>${sysLdapConfig.account}</td>
				</tr>
				<tr>
					<th>密码：</th>
					<td>${sysLdapConfig.password}</td>
				</tr>
				
				<tr>
					<th>基本DN：</th>
					<td>${sysLdapConfig.dnBase}</td>
				</tr>
				<tr>
					<th>基准DN：</th>
					<td>${sysLdapConfig.dnDatum}</td>
				</tr>
				
				<tr>
					<th>用户过滤搜索器：</th>
					<td>${sysLdapConfig.userFilter}</td>
				</tr>
				
				
				<tr>
					<th>用户编号属性：</th>
					<td>${sysLdapConfig.attUserNo}</td>
				</tr>
				<tr>
					<th>用户账户属性：</th>
					<td>${sysLdapConfig.attUserAcc}</td>
				</tr>
				<tr>
					<th>用户名称属性：</th>
					<td>${sysLdapConfig.attUserName}</td>
				</tr>
				<tr>
					<th>用户密码属性：</th>
					<td>${sysLdapConfig.attUserPwd}</td>
				</tr>
				<tr>
					<th>用户电话属性：</th>
					<td>${sysLdapConfig.attUserTel}</td>
				</tr>
				<tr>
					<th>用户邮件属性：</th>
					<td>${sysLdapConfig.attUserMail}</td>
				</tr>
				<tr>
					<th>部门名称属性：</th>
					<td>${sysLdapConfig.attDeptName}</td>
				</tr>
				
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
		<div>
			<table class="table-detail" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${sysLdapConfig.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${sysLdapConfig.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${sysLdapConfig.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${sysLdapConfig.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="sys/ldap/sysLdapConfig"
		entityName="com.redxun.sys.ldap.entity.SysLdapConfig" formId="form1" />
</body>
</html>