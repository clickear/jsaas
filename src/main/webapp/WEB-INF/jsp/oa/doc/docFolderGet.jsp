<%-- 
    Document   : [DocFolder]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文件夹明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<div class="mini-toolbar">
 <a class="mini-button" iconCls="icon-close" plain="true" onclick="onClose()">关闭</a>
    </div>
	<div id="form1" class="form-outer">
		<div style="padding: 5px;">
			<table  class="table-detail column_2_m" cellpadding="0" cellspacing="1" align="center">
				<caption>文件夹信息</caption>
				<tr style="height: 3px;">
					<th ><div align="left" >目录名称</div></th>
					<td>${docFolder.name}</td>
					<th><div align="left">文档描述</div></th>
					<td><div align="left">${docFolder.descp}</div></td>
					
				</tr>
				
				<tr>
					<th><div align="left">层  次</div></th> 
					<td>${docFolder.depth}</td>
					<th><div align="left">共  享</div></th>
					<td>${docFolder.share}</td>
				</tr>
				
			</table>
		</div>
		<div style="padding: 5px">
			<table class="table-detail column_2_m" cellpadding="0" cellspacing="1"  align="center">
				<caption>更新信息</caption>
				<tr>
					<th><div align="left">创建人</div></th>
					<td><rxc:userLabel userId="${docFolder.createBy}" /></td>
					<th><div align="left">创建时间</th>
					<td><fmt:formatDate value="${docFolder.createTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr>
					<th><div align="left">更新人</div></th>
					<td><rxc:userLabel userId="${docFolder.updateBy}" /></td>
					<th><div align="left">更新时间</div></th>
					<td><fmt:formatDate value="${docFolder.updateTime}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</table>
		</div>
	</div>
	<rx:detailScript baseUrl="oa/doc/docFolder" formId="form1"  entityName="com.redxun.oa.doc.entity.DocFolder"/>
</body>
</html>