<%-- 
    Document   : [BpmFormTemplate]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单模板编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="${bpmFormTemplate.id}" hideRecordNav="true" hideRemove="true" />
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden"
					value="${bpmFormTemplate.id}" />
				<table class="table-detail table-detail2 column_2" cellspacing="1" cellpadding="0">
					<caption>表单模版基本信息</caption>

					<tr style="width: 100%;">
						<th>模版名称 </th>
						<td><input name="name" value="${bpmFormTemplate.name}"
							class="mini-textbox" vtype="maxLength:50" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>别　　 名 </th>
						<td><input name="alias" value="${bpmFormTemplate.alias}"
							class="mini-textbox" vtype="maxLength:50" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>模　　版 </th>
						<td><textarea name="template" class="mini-textarea" 
								vtype="maxLength:65535" style="width: 90%;height: 200px;">${bpmFormTemplate.template}</textarea></td>
					</tr>

					<tr>
						<th>模版类型 (pc,mobile)</th>
						<td>
							<input name="type"  class="mini-combobox" style="width:150px;" textField="text" valueField="id"  emptyText="请选择..."
							data='[{ "id": "pc", "text": "PC模版" },{ "id": "mobile", "text": "手机" }]' value="${bpmFormTemplate.type}"  showNullItem="true" allowInput="false"/>         
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormTemplate"
		entityName="com.redxun.bpm.form.entity.BpmFormTemplate" />
		
	<script type="text/javascript">
		addBody();s
	</script>
</body>
</html>