
<%-- 
    Document   : [ins_portal_def]编辑页
    Created on : 2017-08-15 16:07:14
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>自定义门户编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insPortalDef.portId" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${insPortalDef.portId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>自定义门户基本信息</caption>
					<tr>
						<th>门户名字</th>
						<td>						
								<input name="name" value="${insPortalDef.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>门户 Key</th>
						<td>							
								<input name="key" value="${insPortalDef.key}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>是否缺省</th>
						<td>
							<div class="mini-radiobuttonlist" value="${insPortalDef.isDefault}" 
    						textField="text" valueField="id"  id="isDefault" name="isDefault"  data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" ></div>							
						</td>
					</tr>
					<tr>
						<th>优  先  级</th>
						<td>							
								<input name="priority" value="${insPortalDef.priority}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/info/insPortalDef"
		entityName="com.redxun.oa.info.entity.InsPortalDef" />
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>