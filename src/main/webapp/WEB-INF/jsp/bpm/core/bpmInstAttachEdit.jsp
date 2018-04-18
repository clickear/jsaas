<%-- 
    Document   : [BpmInstAttach]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmInstAttach]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmInstAttach.id}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden"
					value="${bpmInstAttach.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[BpmInstAttach]基本信息</caption>

					<tr>
						<th>${column.remarks} <span class="star">*</span> ：
						</th>
						<td><input name="instId" value="${bpmInstAttach.instId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入$column.remarks" />

						</td>
					</tr>

					<tr>
						<th>${column.remarks} <span class="star">*</span> ：
						</th>
						<td><input name="fileId" value="${bpmInstAttach.fileId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入$column.remarks" />

						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmInstAttach"
		entityName="com.redxun.bpm.core.entity.BpmInstAttach" />
</body>
</html>