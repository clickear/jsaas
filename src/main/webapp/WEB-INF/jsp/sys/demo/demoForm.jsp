<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[Demo]编辑</title>
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
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<input id="pkId" name="id" class="mini-hidden" value="${demo.id}" />
			<table class="table-detail" cellspacing="1" cellpadding="0">
				<caption>[Demo]基本信息</caption>
				<tr>
					<th>名字：</th>
					<td> <input name="name" value="${demo.name}" class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>地址：</th>
					<td> <input name="address" value="${demo.address}" class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>