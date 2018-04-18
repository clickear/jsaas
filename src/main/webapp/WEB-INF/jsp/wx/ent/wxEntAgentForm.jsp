
<%-- 
    Document   : [微信应用]编辑页
    Created on : 2017-06-04 12:27:36
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[微信应用]编辑</title>
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
			<input id="pkId" name="id" class="mini-hidden" value="${wxEntAgent.id}" />
			<table class="table-detail column_2" cellspacing="1" cellpadding="0">
				<caption>[微信应用]基本信息</caption>
				<tr>
					<th>NAME_</th>
					<td>
						
							<input name="name" value="${wxEntAgent.name}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>DESCRIPTION_</th>
					<td>
						
							<input name="description" value="${wxEntAgent.description}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>信任域名</th>
					<td>
						
							<input name="domain" value="${wxEntAgent.domain}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>HOME_URL_</th>
					<td>
						
							<input name="homeUrl" value="${wxEntAgent.homeUrl}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>企业主键</th>
					<td>
						
							<input name="entId" value="${wxEntAgent.entId}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>企业ID</th>
					<td>
						
							<input name="corpId" value="${wxEntAgent.corpId}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>应用ID</th>
					<td>
						
							<input name="agentId" value="${wxEntAgent.agentId}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>密　钥</th>
					<td>
						
							<input name="secret" value="${wxEntAgent.secret}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>是否默认</th>
					<td>
						
							<input name="defaultAgent" value="${wxEntAgent.defaultAgent}"
						class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>