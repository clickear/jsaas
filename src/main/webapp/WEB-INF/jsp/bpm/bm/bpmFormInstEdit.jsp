<%-- 
    Document   : [BpmFormInst]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmFormInst]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmFormInst.formInstId}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="formInstId" class="mini-hidden" value="${bpmFormInst.formInstId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[BpmFormInst]基本信息</caption>

					<tr>
						<th>实例标题 <span class="star">*</span> ：
						</th>
						<td><input name="subject" value="${bpmFormInst.subject}" class="mini-textbox" vtype="maxLength:127" style="width: 90%" required="true" emptyText="请输入实例标题" /></td>
					</tr>

					<tr>
						<th>流程实例ID <span class="star">*</span> ：
						</th>
						<td><input name="instId" value="${bpmFormInst.instId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入流程实例ID" /></td>
					</tr>

					<tr>
						<th>ACT实例ID <span class="star">*</span> ：
						</th>
						<td><input name="actInstId" value="${bpmFormInst.actInstId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入ACT实例ID" /></td>
					</tr>

					<tr>
						<th>ACT定义ID <span class="star">*</span> ：
						</th>
						<td><input name="actDefId" value="${bpmFormInst.actDefId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入ACT定义ID" /></td>
					</tr>

					<tr>
						<th>流程定义ID <span class="star">*</span> ：
						</th>
						<td><input name="defId" value="${bpmFormInst.defId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" required="true" emptyText="请输入流程定义ID" /></td>
					</tr>

					<tr>
						<th>解决方案ID ：</th>
						<td><input name="solId" value="${bpmFormInst.solId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>数据模型ID ：</th>
						<td><input name="fmId" value="${bpmFormInst.fmId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>表单视图ID ：</th>
						<td><input name="fmViewId" value="${bpmFormInst.fmViewId}" class="mini-textbox" vtype="maxLength:64" style="width: 90%" /></td>
					</tr>

					<tr>
						<th>状态 <span class="star">*</span> ：
						</th>
						<td><input name="status" value="${bpmFormInst.status}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" required="true" emptyText="请输入状态" /></td>
					</tr>

					<tr>
						<th>数据JSON ：</th>
						<td><textarea name="jsonData" class="mini-textarea" vtype="maxLength:2147483647" style="width: 90%">${bpmFormInst.jsonData}
														 </textarea></td>
					</tr>

					<tr>
						<th>是否持久化 ：</th>
						<td><input name="isPersist" value="${bpmFormInst.isPersist}" class="mini-textbox" vtype="maxLength:20" style="width: 90%" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/bm/bpmFormInst" />
</body>
</html>