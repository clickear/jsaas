<%-- 
    Document   : [BpmFormTemplate]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单模板明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<div class="paddingTop">
		<div id="form1" class="form-outer shadowBox">
			<div>
				<table style="width: 100%" class="table-detail column_2" cellpadding="0"
					cellspacing="1">
					<caption>表单模版基本信息</caption>
					<tr>
						<th>模版名称</th>
						<td>${bpmFormTemplate.name}</td>
					</tr>
					<tr>
						<th>别　　名</th>
						<td>${bpmFormTemplate.alias}</td>
					</tr>
					<tr>
						<th>模　　版</th>
						<td><c:out value="${bpmFormTemplate.template}"></c:out> </td>
					</tr>
					
					<tr>
						<th>模版类型 </th>
						<td>${bpmFormTemplate.type}</td>
					</tr>
					<tr>
						<th>初始添加的(1是,0否)</th>
						<td>${bpmFormTemplate.init==1?"系统默认":"自定义添加"}</td>
					</tr>
				</table>
			</div>
			
		</div>
	</div>
	<rx:detailScript baseUrl="bpm/form/bpmFormTemplate"
		entityName="com.redxun.bpm.form.entity.BpmFormTemplate" formId="form1" />
		
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>