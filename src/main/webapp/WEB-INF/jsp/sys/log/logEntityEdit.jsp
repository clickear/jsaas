
<%-- 
    Document   : [日志实体]编辑页
    Created on : 2017-09-25 14:27:06
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[日志实体]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="logEntity.id" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${logEntity.id}" />
				<table class="table-detail column_2" cellspacing="1" cellpadding="0">
					<caption>[日志实体]基本信息</caption>
					<tr>
						<th>所属模块</th>
						<td>
							
								<input name="module" value="${logEntity.module}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>功　　能</th>
						<td>
							
								<input name="subModule" value="${logEntity.subModule}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>操  作  名</th>
						<td>
							
								<input name="action" value="${logEntity.action}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>操作  IP</th>
						<td>
							
								<input name="ip" value="${logEntity.ip}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>操作目标</th>
						<td>
							
								<input name="target" value="${logEntity.target}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/log/logEntity"
		entityName="com.redxun.sys.log.entity.LogEntity" />
	
	<script type="text/javascript">
	mini.parse();
	addBody();
	
	

	</script>
</body>
</html>