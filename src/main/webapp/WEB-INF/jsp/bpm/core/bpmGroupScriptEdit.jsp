
<%-- 
    Document   : [人员脚本]编辑页
    Created on : 2017-06-01 11:33:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[人员脚本]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="bpmGroupScript.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
				<input id="pkId" name="id" class="mini-hidden" value="${bpmGroupScript.scriptId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[人员脚本]基本信息</caption>
					<tr>
						<th>类名：</th>
						<td>
							
								<input name="className" value="${bpmGroupScript.className}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>类实例名：</th>
						<td>
							
								<input name="classInsName" value="${bpmGroupScript.classInsName}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>方法名：</th>
						<td>
							
								<input name="methodName" value="${bpmGroupScript.methodName}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>方法描述：</th>
						<td>
							
								<input name="methodDesc" value="${bpmGroupScript.methodDesc}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>返回类型：</th>
						<td>
							
								<input name="returnType" value="${bpmGroupScript.returnType}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>参数：</th>
						<td>
							
								<input name="argument" value="${bpmGroupScript.argument}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
				</table>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmGroupScript"
		entityName="com.redxun.bpm.core.entity.BpmGroupScript" />
</body>
</html>