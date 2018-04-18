<%-- 
    Document   : DocFolder编辑页
    Created on : 2015-11-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>文件夹编辑</title>
<%@include file="/commons/edit.jsp"%>
<!DOCTYPE html>
</head>	
<body>
	<rx:toolbar toolbarId="toolbar1"  hideRecordNav="true" ></rx:toolbar>
	<div id="p1" class="form-outer shadowBox" style="width: 90%">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="folderId" class="mini-hidden"
					value="${docFolder.folderId}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>文件夹基本信息</caption>

					<tr>
						<th>
							<span class="starBox">
								目录名称
								<span class="star">*</span>
							</span> 
						</th>
						<td colspan="3"><input name="name" value="${docFolder.name}"
							class="mini-textbox" vtype="maxLength:128" style="width: 90%"
							required="true" emptyText="请输入目录名称" />

						</td>
					</tr>

					<tr style="display:none;">
						<th>父  目  录 </th>
						<td><input name="parent" value="${docFolder.parent}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr> 
                    <tr style="display:none;">
						<th>文件夹类型 </th>
						<td><input name="type" value="${docFolder.type}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr> 
				 	

					<tr >
						<th>
							<span class="starBox">
								共　　享 
								<span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								name="Share" 
								value="${docFolder.share}"
								class="mini-radiobuttonlist" 
								required="true"  
								textField="text" 
								valueField="id"
								data="[{ 'id': 'YES', 'text': 'YES' },{ 'id': 'NO', 'text': 'NO' }]"
							/>
						</td>
						<th>次　　序</th><td><input name="sn" value="${docFolder.sn} }" class="mini-spinner"  minValue="0" maxValue="14"  /></td>
					</tr>

					<tr >
						<th >文档描述 </th>
						<td colspan="3"><textarea name="descp" class="mini-textarea" vtype="maxLength:256" style="width: 90%"  >${docFolder.descp}</textarea></td>
					</tr>

					 <tr style="display:none;">
						<th>用户ID <span class="star">*</span> 
						</th>
						<td><input name="userId" value="${docFolder.userId}"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%"
							required="true" emptyText="请输入用户ID" />

						</td> 
					</tr>
				</table>
			</div>
		</form>
	</div>
	
	<script type="text/javascript">
     mini.parse();
     addBody();
	
	</script>
	<rx:formScript formId="form1" baseUrl="oa/doc/docFolder"  entityName="com.redxun.oa.doc.entity.DocFolder"/>
</body>
</html>