
<%-- 
    Document   : [公告栏目管理]编辑页
    Created on : 2018-04-16 17:38:10
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[公告栏目管理]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insNewsColumn.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${insNewsColumn.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[公告栏目管理]基本信息</caption>
					<tr>
						<th>栏目名称：</th>
						<td>
							<input name="name" value="${insNewsColumn.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>描述：</th>
						<td>
							
								<input name="description" value="${insNewsColumn.description}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/insNewsColumn"
		entityName="com.redxun.oa.info.entity.InsNewsColumn" />
	
	<script type="text/javascript">
	mini.parse();
	
	
	

	</script>
</body>
</html>