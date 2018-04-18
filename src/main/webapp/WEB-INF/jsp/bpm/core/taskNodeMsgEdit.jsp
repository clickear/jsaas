<%-- 
    Document   :任务通知节点消息编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>任务节点编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${taskNodeMsg.msgId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="msgId" class="mini-hidden"
					value="${taskNodeMsg.msgId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>增加任务节点基本信息</caption>

					<tr>
						<th>主题 ：</th>
						<td style="width:80%"><input name="subject" value="${taskNodeMsg.subject}"
							class="mini-textbox" vtype="maxLength:512" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>内容 ：</th>
						<td><textarea name="content" class="mini-textarea"
								vtype="maxLength:65535" style="width: 90%">${taskNodeMsg.content}
														 </textarea></td>
					</tr>

					<tr>
						<th>链接 ：</th>
						<td><textarea name="linked" class="mini-textarea"
								vtype="maxLength:512" style="width: 90%">${taskNodeMsg.linked}
														 </textarea></td>
					</tr>

					<tr>
						<th>任务节点名称 ：</th>
						<td><input name="taskNodeName"
							value="${taskNodeMsg.taskNodeName}" class="mini-textbox"
							vtype="maxLength:128" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>部署ID ：</th>
						<td><input name="deployId" value="${taskNodeMsg.deployId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/taskNodeMsg"
		entityName="com.redxun.bpm.core.entity.TaskNodeMsg" />
</body>
</html>