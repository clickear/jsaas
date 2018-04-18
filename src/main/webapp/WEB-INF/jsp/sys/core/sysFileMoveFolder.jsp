<%-- 
    Document   : 附件移动归类
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
<title>附件明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom topBtn">
		<a class="mini-button" iconCls="icon-move-in" onclick="moveFolder()" plain="true">确 定</a>
<!-- 		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关 闭</a> -->
	</div>
	<div class="shadowBox90" style="padding-top: 8px;">
		<form id="fileForm">
			<div class="form-outer">
				<div>
					<table style="width: 100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<c:if test="${not empty sysFile }">
						<caption>附件信息</caption>
						<tr>
							<th>附件名称</th>
							<td colspan="3"><a href="${ctxPath}/sys/core/file/previewFile.do?fileId=${sysFile.fileId}">${sysFile.fileName}</a></td>
						</tr>
						<tr>
							<th>上 传 人 </th>
							<td><rxc:userLabel userId="${sysFile.createBy}" /></td>
							<th>上传时间 </th>
							<td><fmt:formatDate value="${sysFile.createTime}" /> </td>
						</tr>
						<tr>
							<th>文件大小</th>
							<td colspan="3">${sysFile.sizeFormat}</td>
						</tr>
						</c:if>
						<tr>
							<th>
								<span class="starBox">
									移动目录<span class="star">*</span>
								</span>
							</th>
							<td colspan="3">
								 <input class="mini-hidden" name="fileId" value="${sysFile.fileId}"/>
								 <input class="mini-hidden" name="fileIds" value="${fileIds}"/>
								 <input 
								 	id="folderTree" 
								 	name="treeId" 
								 	class="mini-treeselect" 
								 	url="${ctxPath}/sys/core/sysTree/myFileFolder.do" 
								 	multiSelect="false"  
								 	valueFromSelect="false"
							        textField="name"
							        valueField="treeId"
							        parentField="parentId"
							        onbeforenodeselect="disableSelectRoot" 
							        expandOnLoad="true"
							        required="true"
							        allowInput="false" 
							        showRadioButton="true" 
							        showFolderCheckBox="false" 
							        style="width:300px;" 
							        popupHeight="150"
						        />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function disableSelectRoot(e){
			if(e.node.treeId==0){
				e.cancel=true;
			}
		}
		
		//移动目录
		function moveFolder(){
			var form=new mini.Form('fileForm');
			form.validate();
			if(!form.isValid()) {
				return;
			}
			_SubmitJson({
				url:__rootPath+'/sys/core/sysFile/saveFolder.do',
				data:form.getData(),
				success:function(action){
					CloseWindow('ok');
				}
			});
		};
		addBody();
		
	</script>
	
</body>
</html>