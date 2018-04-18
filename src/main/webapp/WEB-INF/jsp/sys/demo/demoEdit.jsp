
<%-- 
    Document   : [Demo]编辑页
    Created on : 2017-04-26 16:24:45
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[Demo]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="demo.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${demo.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[Demo]基本信息</caption>
					<tr>
						<th>名字：</th>
						<td>
							
								<input name="name" value="${demo.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>地址：</th>
						<td>
							
								<input name="address" value="${demo.address}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/demo/demo"
		entityName="com.redxun.sys.demo.entity.Demo" />
</body>
</html>