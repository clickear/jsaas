
<%-- 
    Document   : [系统列表方案]编辑页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[系统列表方案]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="sysListSol.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${sysListSol.solId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[系统列表方案]基本信息</caption>
					<tr>
						<th>标识健：</th>
						<td>
							
								<input name="key" value="${sysListSol.key}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>名称：</th>
						<td>
							
								<input name="name" value="${sysListSol.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>描述：</th>
						<td>
							
								<input name="descp" value="${sysListSol.descp}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>权限配置：</th>
						<td>
							
								<input name="rightConfigs" value="${sysListSol.rightConfigs}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysListSol"
		entityName="com.redxun.sys.core.entity.SysListSol" />
</body>
</html>