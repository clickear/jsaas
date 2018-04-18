<%-- 
    Document   : 测试方案编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<html>
<head>
<title>测试方案编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmTestSol.testSolId}" />
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="testSolId" class="mini-hidden"
					value="${bpmTestSol.testSolId}" />
					<input name="solId" value="${bpmTestSol.solId}" class="mini-hidden" />
					<input name="actDefId" value="${bpmTestSol.actDefId}" class="mini-hidden" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>测试方案基本信息</caption>
					<tr>
						<th>方案编号 <span class="star">*</span> ：
						</th>
						<td><input name="testNo" value="${bpmTestSol.testNo}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入方案编号" />
						</td>
					</tr>
					<tr>
						<th>描述 ：</th>
						<td><textarea name="memo" class="mini-textarea" vtype="maxLength:1024" style="width: 90%">${bpmTestSol.memo}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmTestSol"
		entityName="com.redxun.bpm.core.entity.BpmTestSol" />
</body>
</html>