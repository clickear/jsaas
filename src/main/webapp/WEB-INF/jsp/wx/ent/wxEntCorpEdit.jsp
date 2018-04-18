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
		<%@include file="/commons/edit.jsp" %>   
	</head>
	<body>
		<div id="toolbar1" class="mini-toolbar topBtn" >
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave('form1');">保存</a>
<!-- 			<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
		</div>
		<div id="p1" class="form-outer shadowBox90">
			<form id="form1" method="post" action="/wx/ent/wxEntCorp/save.do">
					<input id="pkId" name="id" class="mini-hidden" value="${wxEntCorp.id}" />
					<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
						<caption>[微信企业配置]基本信息</caption>
						<tr>
							<th>企业ID</th>
							<td>
								<input name="corpId" value="${wxEntCorp.corpId}" class="mini-textbox"   style="width: 90%" />
							</td>
						</tr>
						<tr>
							<th>通讯录密钥</th>
							<td>
								<input name="secret" value="${wxEntCorp.secret}" class="mini-textbox"   style="width: 90%" />
							</td>
						</tr>
						<tr>
							<th>是否启用企业微信</th>
							<td>
								<div  name="enable" class="mini-checkbox" readOnly="false" text="启用" trueValue="1" falseValue="0" value="${wxEntCorp.enable}"></div>
							</td>
						</tr>
					</table>
			</form>
		</div>
		<rx:formScript formId="form1" baseUrl="wx/ent/wxEntCorp"
			entityName="com.redxun.wx.ent.entity.WxEntCorp" />
		<script type="text/javascript">
			addBody();
			indentBody();
			function successCallback(){
				location.href=location.href;
			}
		</script>
	</body>
</html>