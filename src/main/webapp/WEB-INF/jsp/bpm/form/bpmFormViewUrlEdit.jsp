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
	    <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveForm()">保存</a>
    </div>
	<div class="shadowBox90" style="padding-top: 8px;">
		<form id="form1" method="post" class="form-outer">
			<table class="table-detail column_2_m" cellspacing="1" cellpadding="0" border="2">
				<tr>
					<th>
						<span class="starBox">
							分　　类<span class="star">*</span>
						</span>
					</th>
					<td colspan="3">
						<input id="pkId" name="viewId" class="mini-hidden" value="${bpmFormView.viewId}" />
						<input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_VIEW" 
						 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${bpmFormView.treeId}"
					        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree" popupWidth="300" style="width:250px"/>
					</td>
				</tr>
				<tr>
					<th>
						<span class="starBox">
							名　　称 <span class="star">*</span>
						</span> 
					</th>
					<td>
						<input name="name" value="${bpmFormView.name}" class="mini-textbox" vtype="maxLength:255" style="width: 90%" required="true" emptyText="请输入名称" />
					</td>
				
					<th>
						<span class="starBox">
							标  识  键 <span class="star">*</span>
						</span> 
					</th>
					<td>
						<input name="key" value="${bpmFormView.key}" class="mini-textbox" vtype="key,maxLength:100" style="width: 90%" required="true" emptyText="请输入标识键" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="starBox">
							表单地址<span class="star">*</span>
						</span> 
					</th>
					<td colspan="3">
						<input name="renderUrl" value="${bpmFormView.renderUrl}" class="mini-textbox" vtype="key,maxLength:100" style="width: 90%" required="true" emptyText="请输入表单URL" />
					</td>
				</tr>
				<tr>
					<th>视图说明 </th>
					<td colspan="3">
						<input name="isMain" class="mini-hidden"   value="YES" />
						<input name="version" class="mini-hidden"   value="1" />
						<input name="type" class="mini-hidden"   value="SEL-DEV" />
						<input name="status" class="mini-hidden"   value="DEPLOYED" />
						<textarea name="descp" class="mini-textarea"  style="width:80%;" >${bpmFormView.descp}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
		
	
	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormView" entityName="com.redxun.bpm.form.entity.BpmFormView" />
	<script type="text/javascript">
		addBody();
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