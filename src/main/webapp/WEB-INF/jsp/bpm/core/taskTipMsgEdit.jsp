<%-- 
    Document   : 任务提示消息编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>任务提示消息编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${taskTipMsg.id}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden"
					value="${taskTipMsg.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>任务提示消息基本信息</caption>
					<!--  
					<tr>
						<td>发送者ID ：</td>
						<td><input name="senderId" value="${taskTipMsg.senderId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />
						</td>
					</tr>-->

					<tr>
						<td >发送时间 ：</td>
						<td style="width: 80%"><input name="senderTime" value="${taskTipMsg.senderTime}"
							class="mini-textbox" vtype="maxLength:19" style="width: 95%" />
						</td>
					</tr>

					<tr>
						<td>主题 ：</td>
						<td ><textarea name="subject" class="mini-textarea"
								vtype="maxLength:512" style="width: 95%;">${taskTipMsg.subject}
														 </textarea></td>
					</tr>

					<tr>
						<td>内容 ：</td>
						<td><textarea name="content" class="mini-textarea"
								vtype="maxLength:65535" style="width: 95%">${taskTipMsg.content}
														 </textarea></td>
					</tr>

					<tr>
						<td>链接 ：</td>
						<td><textarea name="linked" class="mini-textarea"
								vtype="maxLength:512" style="width: 95%">${taskTipMsg.linked}
														 </textarea></td>
					</tr>

					<tr>
						<td>撤销 ：</td>
						<td><input name="isinvalid" value="${taskTipMsg.isinvalid}"
							class="mini-textbox" vtype="maxLength:64" style="width: 95%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/taskTipMsg"
		entityName="com.redxun.bpm.core.entity.TaskTipMsg" />
</body>
</html>