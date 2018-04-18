
<%-- 
    Document   : [微信企业配置]编辑页
    Created on : 2017-06-04 12:27:36
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信企业配置]编辑</title>
<%@include file="/commons/edit.jsp"%>
<script  type="text/javascript">
	function getData(){
		return _GetFormJsonMini("form1");
	}
	
	function isValid(){
		return true;
	}
	
</script>
</head>
<body>
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<input id="pkId" name="id" class="mini-hidden" value="${wxEntCorp.id}" />
			<table class="table-detail column_1" cellspacing="1" cellpadding="0">
				<caption>[微信企业配置]基本信息</caption>
				<tr>
					<th>企业ID</th>
					<td>
						
							<input name="corpId" value="${wxEntCorp.corpId}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>通讯录密钥</th>
					<td>
						
							<input name="secret" value="${wxEntCorp.secret}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>是否启用企业微信</th>
					<td>
						
							<input name="enable" value="${wxEntCorp.enable}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>