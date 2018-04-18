<%-- 
    Document   : 外部邮箱账号配置编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>外部邮箱账号配置编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${mailConfig.configId}" hideRecordNav="true" hideRemove="true" excludeButtons="remove" >
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-mailconnect" plain="true" onclick="testConnect">连接</a>
			<c:choose>
				<c:when test="${mailConfig.configId!=null&&mailConfig.configId!=''}">
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="onRemove">删除</a>
				</c:when>
			   <c:otherwise>
			   		<a class="mini-button" iconCls="icon-remove" plain="true"  visible="false" onclick="onRemove">删除</a>
			   </c:otherwise>
			</c:choose>
		</div>
	</rx:toolbar>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input 
					id="pkId" 
					name="configId" 
					class="mini-hidden" 
					value="${mailConfig.configId}" 
				/> 
				<input 
					name="userId" 
					value="${mailConfig.userId}"
					class="mini-hidden" 
					vtype="maxLength:64" 
					style="width: 90%" 
					required="true" 
				/> 
				<input 
					name="userName" 
					value="${mailConfig.userName}"
					class="mini-hidden" 
					vtype="maxLength:128" 
					style="width: 90%" 
				/>
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>邮箱账号信息配置</caption>
					<tr class="table-detail-right">
						<th>
							<span class="starBox">
								帐号名称<span class="star">*</span>
							</span>
						</th>
						<td><input name="account" value="${mailConfig.account}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true"  /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								外部邮件地址<span class="star">*</span> 
							</span> 
						</th>
						<td><input name="mailAccount" value="${mailConfig.mailAccount}" class="mini-textbox" vtype="maxLength:128" style="width: 90%"
							required="true" emptyText="请输入外部邮件地址" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								外部邮件密码<span class="star">*</span> 
							</span> 
						</th>
						<td><input name="mailPwd" value="${mailConfig.mailPwd}" class="mini-password" vtype="maxLength:128" style="width: 90%" required="true"
							emptyText="请输入外部邮件密码" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								协议类型 <span class="star">*</span> 
							</span> 
						</th>
						<td><c:choose>
								<c:when test="${mailConfig.protocol=='pop3' || mailConfig.protocol=='imap'}">
									<input name="protocol" value="${mailConfig.protocol}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true"
										emptyText="请输入邮件发送主机" allowInput="false" />
								</c:when>
								<c:otherwise>
									<input name="protocol" value="${mailConfig.protocol}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical"
										data="[{id:'pop3',text:'POP3'},{id:'imap',text:'IMAP'}]" onvaluechanged="isProtocol" required="true" />
								</c:otherwise>
							</c:choose></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								是否使用SSL<span class="star">*</span> 
							</span> 
						</th>
						<td><input name="ssl" value="${mailConfig.ssl}" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical"
							data="[{id:'true',text:'是'},{id:'false',text:'否'}]" value="false" required="true" onvaluechanged="isSSL" required="true" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								邮件发送主机 <span class="star">*</span> 
							</span> 
						</th>
						<td><input name="smtpHost" value="${mailConfig.smtpHost}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true"
							emptyText="请输入邮件发送主机" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								邮件发送端口 <span class="star">*</span> 
							</span> 
						</th>
						<td><input id="smtpPort" name="smtpPort" value="${mailConfig.smtpPort}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入邮件发送端口" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								接收主机 <span class="star">*</span> 
							</span> 
						</th>
						<td><input name="recpHost" value="${mailConfig.recpHost}" class="mini-textbox" vtype="maxLength:128" style="width: 90%" required="true"
							emptyText="请输入接收主机" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								接收端口 <span class="star">*</span> 
							</span> 
						</th>
						<td><input id="recpPort" name="recpPort" value="${mailConfig.recpPort}" class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入接收端口" /></td>
					</tr>

					<tr>
						<th>
							<span class="starBox">
								是否默认<span class="star">*</span> 
							</span> 
						</th>
						<td> <input name="isDefault" value="${mailConfig.isDefault}" class="mini-radiobuttonlist"
							repeatItems="1" repeatDirection="vertical" data="[{id:'YES',text:'是'},{id:'NO',text:'否'}]" value="YES" required="true" />
						</td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	addBody();
	/*删除当前邮箱账号配置*/
	 function onRemove(){
		var from="${param['from']}";
         if (confirm("确定当前记录？")) {
                var id=$("#pkId").val();
                _SubmitJson({
                	url:__rootPath+"/oa/mail/mailConfig/del.do",
                	data:{
                		ids:id,
                		delTabs:'YES'
                	},
                	method:'POST',
                	success:function(text){
                		if(from=='tree'){
                			var data=text.data;
                			top['mail'].delTabs(data);
                		}
                		CloseWindow('ok');
                	}
                });
            }
    }
		/*测试外部邮箱账号配置连接情况*/
		function testConnect(){
			var content = _GetFormJson("form1");
			var data = JSON.stringify(content);
			_SubmitJson({
				url:__rootPath+"/oa/mail/mailConfig/validateMailConfig.do",
				showMsg:false,
				data:{
					data:data
				},
				submitTips:"正在测试账号连通性...",
				method:'POST',
				success:function(text){
					var msg=text.message;
					alert(msg);
				}
			});
		}

		
		/*SSL选项值改变事件处理*/
		function isSSL(e) {
			var smtpPort = mini.getByName("smtpPort");
			var recpPort = mini.getByName("recpPort");
			var protocol = mini.getByName("protocol");
			if (this.getValue() == "true") {
				if (protocol.getValue() == "pop3") {
					smtpPort.setValue("465");
					recpPort.setValue("995");
				} else {
					smtpPort.setValue("465");
					recpPort.setValue("993");
				}
			} else {
				if (protocol.getValue() == "pop3") {
					smtpPort.setValue("25");
					recpPort.setValue("110");
				} else {
					smtpPort.setValue("25");
					recpPort.setValue("143");
				}
			}
		}

		/*协议更改事件处理*/
		function isProtocol(e) {
			var isSSL = mini.getByName("ssl");
			isSSL.setValue("false");
			var smtpPort = mini.getByName("smtpPort");
			var recpPort = mini.getByName("recpPort");
			var smtpHost = mini.getByName("smtpHost");
			var recpHost = mini.getByName("recpHost");
			smtpHost.setValue("smtp.xxx.com");
			if (isSSL == "true") {
				if (this.getValue() == "pop3") {
					smtpPort.setValue("465");
					recpPort.setValue("995");
					recpHost.setValue("pop.xxx.com");
				} else {
					smtpPort.setValue("465");
					recpPort.setValue("993");
					recpHost.setValue("imap.xxx.com");
				}
			} else {
				if (this.getValue() == "pop3") {
					smtpPort.setValue("25");
					recpPort.setValue("110");
					recpHost.setValue("pop.xxx.com");
				} else {
					smtpPort.setValue("25");
					recpPort.setValue("143");
					recpHost.setValue("imap.xxx.com");
				}
			}
		}
	</script>
	<rx:formScript formId="form1" baseUrl="oa/mail/mailConfig" />
</body>
</html>