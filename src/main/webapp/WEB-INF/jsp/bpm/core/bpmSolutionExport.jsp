<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案导出</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar" style="padding:6px;">
		<a class="mini-button" iconCls="icon-export" onclick="doExport">导出</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<form id="form">
		<input class="mini-hidden" id="ids" name="ids" value="${param['ids']}"/>
		<table cellpadding="0" cellspacing="0" class="table-detail" style="padding:6px;width:100%;">
			<tr>
				<td>导出选项配置</td>
			</tr>
			<tr>
				<td>
					<div id="expOptions" name="expOptions" class="mini-checkboxlist" repeatItems="4" repeatLayout="table"
				        data="[{id:'bpmSolution',text:'流程业务解决方案'},{id:'bpmDefExt',text:'流程定义'},{id:'bpmFormViews',text:'流程表单'},{id:'bpmSolFvs',text:'流程表单配置'},{id:'bpmNodeSets',text:'流程节点配置'},{id:'bpmSolVars',text:'流程节点变量配置'},{id:'bpmSolUsers',text:'流程审批人员配置'},{id:'formViewRights',text:'表单字段权限配置'}]" >
				    </div>
				</td>
			</tr>
			
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();
		var expOptions=mini.get('expOptions');
		expOptions.selectAll();
		
		function doExport(){
			var ids=mini.get('ids').getValue();
			var options=expOptions.getValue();
			jQuery.download(__rootPath+'/bpm/core/bpmSolution/doExport.do?ids='+ids,{expOptions:options},'post');
		}
	</script>
</body>
</html>