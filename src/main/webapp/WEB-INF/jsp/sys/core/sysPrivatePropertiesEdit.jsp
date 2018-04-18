
<%-- 
    Document   : [私有参数]编辑页
    Created on : 2017-06-21 10:30:22
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[私有参数]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="sysPrivateProperties.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${sysPrivateProperties.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[私有参数]基本信息</caption>
					<tr>
						<th>参数ID：</th>
						<td>
							<input id="category" class="mini-combobox" name="category" style="width: 90%" textField="key" valueField="key" 
    url="${ctxPath}/sys/core/sysProperties/getCommonProperties.do" value="${sysPrivateProperties.proId}" showNullItem="true" required="true" allowInput="true"/>
							
						</td>
					</tr>
					<tr>
						<th>私有值：</th>
						<td>
							
								<input name="priValue" value="${sysPrivateProperties.priValue}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysPrivateProperties"
		entityName="com.redxun.sys.core.entity.SysPrivateProperties" />
</body>
</html>