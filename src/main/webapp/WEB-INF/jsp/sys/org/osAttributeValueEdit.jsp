
<%-- 
    Document   : [人员属性值]编辑页
    Created on : 2017-12-14 14:09:43
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[人员属性值]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="osAttributeValue.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${osAttributeValue.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[人员属性值]基本信息</caption>
					<tr>
						<th>参数值：</th>
						<td>
							
								<input name="value" value="${osAttributeValue.value}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>目标ID：</th>
						<td>
							
								<input name="targetId" value="${osAttributeValue.targetId}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/org/osAttributeValue"
		entityName="com.redxun.sys.org.entity.OsAttributeValue" />
	
	<script type="text/javascript">
	mini.parse();
	
	
	

	</script>
</body>
</html>