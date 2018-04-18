<%-- 
    Document   : [BpmSolFm]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[BpmSolFm]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmSolFm.id}" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden"
					value="${bpmSolFm.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[BpmSolFm]基本信息</caption>

					<tr>
						<th>解决方案ID <span class="star">*</span> ：
						</th>
						<td><input name="solId" value="${bpmSolFm.solId}"
							class="mini-textbox" vtype="maxLength:64" required="true"
							emptyText="请输入解决方案ID" /></td>
					</tr>

					<tr>
						<th>业务模型标识键 <span class="star">*</span> ：
						</th>
						<td><input name="modKey" value="${bpmSolFm.modKey}"
							class="mini-textbox" vtype="maxLength:100" required="true"
							emptyText="请输入业务模型标识键" /></td>
					</tr>

					<tr>
						<th>是否为主模型 <span class="star">*</span> ：
						</th>
						<td><input name="isMain" value="${bpmSolFm.isMain}"
							class="mini-textbox" vtype="maxLength:20" required="true"
							emptyText="请输入是否为主模型" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmSolFm" />
</body>
</html>