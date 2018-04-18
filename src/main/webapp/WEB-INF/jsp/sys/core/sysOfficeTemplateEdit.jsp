
<%-- 
    Document   : [office模板]编辑页
    Created on : 2018-01-28 11:11:47
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[office模板]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="sysOfficeTemplate.id" />
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden" value="${sysOfficeTemplate.id}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>[office模板]基本信息</caption>
					<tr>
						<th>名称：</th>
						<td>
							
								<input name="name" value="${sysOfficeTemplate.name}"
							class="mini-textbox"   style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>类型：</th>
						<td>
							<input class="mini-combobox" name="type" 
											 emptyText="请选择..."
											 data="[{id:'normal',text:'普通模板'},{id:'red',text:'套红模板'}]"
											 value="${sysOfficeTemplate.type}"/>
								
						</td>
					</tr>
					
					<tr>
						<th>文件名：</th>
						<td>
							<input name="docId" textName="docName" 
								value="${sysOfficeTemplate.docId}"
								text="${sysOfficeTemplate.docName}"
							class="mini-buttonedit"   showClose="true"
							
							oncloseclick="_OnButtonEditClear"
							 onbuttonclick="onButtonEdit" />
						</td>
					</tr>
					<tr>
						<th>描述：</th>
						<td>
							
								<input name="description" value="${sysOfficeTemplate.description}"
							class="mini-textarea"   style="width: 90%" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysOfficeTemplate"
		entityName="com.redxun.sys.core.entity.SysOfficeTemplate" />
	
	<script type="text/javascript">
	mini.parse();
	
	function onButtonEdit(e){
		var ctl=e.sender;
		_UploadDialogShowFile({
			from:'SELF',
			types:"Document",
			single:true,
			onlyOne:true,
			sizelimit:50,
			showMgr:false,
			callback:function(files){
				//that.setUploadFile(files);
				//console.info(files);
				if(files && files.length>0){
					var file=files[0];
					ctl.setValue(file.fileId);
					ctl.setText(file.fileName);
				}
				
			}
		});
	}
	
	
	
	

	</script>
</body>
</html>