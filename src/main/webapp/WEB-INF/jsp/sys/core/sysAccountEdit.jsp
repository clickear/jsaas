<%-- 
    Document   : 系统账号编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统账号编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${sysAccount.accountId}" hideRecordNav="true"/>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div style="padding: 5px;">
				<input id="pkId" name="accountId" class="mini-hidden" value="${sysAccount.accountId}" />
				<input  name="encType" class="mini-hidden" value="SHA-256" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>系统账号基本信息</caption>
					<tr>
						<th>
							<span class="starBox">
								账号名称 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input name="name" value="${sysAccount.name}" class="mini-textbox" 
							 <c:choose>
							  <c:when test="${not empty sysAccount.accountId}">								
							 	allowInput="false"
							 </c:when>
							 <c:otherwise>
							 	onvalidation="onEnglishAndNumberValidation"
								vtype="maxLength:50"
							 </c:otherwise>
							 </c:choose>
							 required="true" emptyText="请输入账号名称" style="width:60%"/><c:if test="${empty sysAccount.accountId}">@${domain}</c:if></td>
					</tr>
					<c:if test="${empty sysAccount.accountId}">
					<tr>
						<th>
							<span class="starBox">
								密　　码 <span class="star">*</span>
							</span>
						</th>
						<td><input id="password" name="pwd" value="${sysAccount.pwd}" class="mini-password" vtype="maxLength:64" required="true" emptyText="请输入密码" style="width:60%"/></td>
					</tr>
					<tr>
						<th>
							<span class="starBox"
								确认密码 <span class="star">*</span>
							</span>
						</th>
						<td><input id="repassword" name="repassword" class="mini-password" vtype="maxLength:64" required="true"
							onvalidation="onValidateRepassword"
						 emptyText="请输入确认密码" style="width:60%"/></td>
					</tr>
					
					</c:if>
					<tr>
						<th>绑定用户</th>
						<td>
							<input id="fullname" name="fullname" value="${sysAccount.fullname}" class="mini-hidden" />
							<input id="userIdEdit" name="userId" value="${sysAccount.userId}" text="${sysAccount.fullname}" required="true" 
							class="mini-buttonedit" emptyText="请输入..."  allowInput="false" onbuttonclick="onSelectUser()"/>
						</td>
					</tr>
					<tr>
						<th>备　　注 </th>
						<td><input name="remark" value="${sysAccount.remark}" class="mini-textarea" vtype="maxLength:200" style="width:80%"/></td>
					</tr>
					<tr>
						<th>
							<span class="starBox">
								状　　态 <span class="star">*</span>
							</span>
						</th>
						<td>
						<div name="status" class="mini-radiobuttonlist" value="${sysAccount.status}" required="true" repeatDirection="vertical" required="true" emptyText="请输入状态" 
								  repeatLayout="table" data="[{id:'ENABLED',text:'启用'},{id:'DISABLED',text:'禁用'}]" textField="text" valueField="id" required="true" vtype="maxLength:40"></div>
				
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysAccount" tenantId="${param['tenantId']}"/>
	<script type="text/javascript">
		addBody();
		function onSelectUser(){
			_TenantUserDlg(tenantId,true,function(user){
				var userIdEdit=mini.get('userIdEdit');
				if(user){
					userIdEdit.setValue(user.userId);
					userIdEdit.setText(user.fullname);
					$("#fullname").val(user.fullname);
				}
			});
		}
		 function onValidateRepassword(e) {
	    		if (e.isValid) {
	    			var pwd=mini.get('password').getValue();
	    			var rePassword=mini.get('repassword').getValue();
	    			if (pwd!=rePassword) {
	    				e.errorText = "两次密码不一致!";
	    				e.isValid = false;
	    			}
	    		}
	    	}
	</script>
</body>
</html>