<%-- 
    Document   : [BpmOpinionLib]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html >
<head>
<title>常用审批意见编辑</title>
<%@include file="/commons/edit.jsp"%>

</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmOpinionLib.opId}" />
	<div class="shadowBox">
		<div id="p1" class="form-outer">
			<form id="form1" method="post">
					<input id="pkId" name="opId" class="mini-hidden"
						value="${bpmOpinionLib.opId}" />
					<table class="table-detail column_1" cellspacing="1" cellpadding="0">
						<caption>常用审批意见基本信息</caption>
	
						<tr>
							<th>常用审批意见</th>
							<td><textarea name="opText" class="mini-textarea"
									vtype="maxLength:512" style="width: 100%">${bpmOpinionLib.opText}</textarea></td>
						</tr>
					</table>
			</form>
		</div>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmOpinionLib"
		entityName="com.redxun.bpm.core.entity.BpmOpinionLib" />
		
		
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>