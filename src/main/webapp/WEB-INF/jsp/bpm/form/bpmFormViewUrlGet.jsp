<%-- 
    Document   : 业务表单视图编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>业务表单视图编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	                 <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	            </td>
	        </tr>
	    </table>
    </div>
	<form id="form1" method="post" class="form-outer">
		<table class="table-detail column_1" cellspacing="1" cellpadding="0" border="2">
			<tr>
				<th>分类 </th>
				<td>
					<input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_VIEW" 
					 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${bpmFormView.treeId}"
				        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree" popupWidth="300" style="width:150px"/>
				</td>
			</tr>
			<tr>
				<th>名称 <span class="star">*</span> </th>
				<td>
					${bpmFormView.name}
				</td>
			</tr>
			<tr>
				<th>标识键 <span class="star">*</span> </th>
				<td>
					${bpmFormView.key}
				</td>
			</tr>
			<tr>
				<th>表单地址 <span class="star">*</span> </th>
				<td>
					${bpmFormView.renderUrl}
				</td>
			</tr>
			<tr>
				<th>视图说明 </th>
				<td>
					${bpmFormView.descp}
				</td>
			</tr>
		</table>
	</form>
		
	
	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormView" entityName="com.redxun.bpm.form.entity.BpmFormView" />
	<script type="text/javascript">
		function saveForm(){
			var url=__rootPath+'/bpm/form/bpmFormView/saveUrlForm.do';
			_SaveJson("form1",url,function(data){
				if(data.success){
					CloseWindow("ok") ;
				}
			}) 
		}
		
	</script>
</body>
</html>