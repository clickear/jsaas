<%-- 
    Document   : 用户组明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户组明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<div class="mini-toolbar" >
		<table style="width:100%">
			<tr>
				<td>
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow();" plain="true">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>用户组基本信息</caption>
				<tr>
					<th> 所属组维度: </th>
					<td colspan="3">${osDimension.name}</td>
				</tr>
				<tr>
					<th>用户组名 ：</th>
					<td>
						${osGroup.name}	
					</td>
					<th>用户组Key ：</th>
					<td>
						${osGroup.key}	
					</td>
				</tr>
			</table>
		</div>
		
		<div style="padding: 5px">
			<table class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>更新信息</caption>
				<tr>
					<th>创建人</th>
					<td><rxc:userLabel userId="${osGroup.createBy}" /></td>
					<th>创建时间</th>
					<td><fmt:formatDate value="${osGroup.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th>更新人</th>
					<td><rxc:userLabel userId="${osGroup.updateBy}" /></td>
					<th>更新时间</th>
					<td><fmt:formatDate value="${osGroup.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>