<%-- 
    Document   : 流程定义管理列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程定义BPMN文件上传处理</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div>
		<div class="mini-toolbar mini-toolbar-bottom topBtn">
	        <a class="mini-button" iconCls="icon-upload" onclick="UploadFile()" plain="true">上传</a>
           	<c:if test="${not empty bpmDef.defId}">
           		<a class="mini-button" iconCls="icon-uploadPublic" onclick="UploadFile(true)" plain="true">上传并发布</a>
           	</c:if>
	    </div>
	    <div class="shadowBox90" style="padding-top: 8px;">
	    	<form id="uploadForm" method="post" action="${ctxPath}/bpm/core/bpmDef/uploadBpmnFile.do" enctype="multipart/form-data" class=" form-outer">
		    	<table class="table-detail column_2" style="width:100%;" >
		    		<c:if test="${not empty msg }">
		    		<tr>
		    			<td colspan="2">
		    				<span style="color:red">${msg}</span>
		    			</td>
		    		</tr>
		    		</c:if>
		    		<tr>
						<th>分　类 </th>
						<td>
								<input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_DEF" 
							 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${bpmDef.treeId}"
						        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree"
						        popupWidth="300" style="width:300px" popupHeight="180"/>
						     <input class="mini-hidden" name="defId" value="${bpmDef.defId}"/>
						     <input class="mini-hidden" id="isDeployNew" name="isDeployNew" />
						</td>
					</tr>
		    		<tr>
		    			<th>流程标题</th>
		    			<td>
		    				<input class="mini-textbox" name="subject"  required="true" style="width:80%" value="${bpmDef.subject}"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<th>流程Key</th>
		    			<td>
		    				<input class="mini-textbox" name="key" vtype="isEnglishAndNumber" required="true" style="width:80%" <c:if test="${not empty bpmDef.defId}">allowInput="false"</c:if> value="${bpmDef.key}"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<th>
		    				流程描述
		    			</th>
		    			<td>
		    				<textarea class="mini-textarea" name="descp" style="width:80%">${bpmDef.descp}</textarea>
		    			</td>
		    		</tr>
		    		<!-- tr> 
		    			<th>
		    				转为化设计器格式
		    			</th>
		    			<td>
		    				<input class="mini-checkbox" name="convertToDesign" value="true"/>
		    			</td>
		    		</tr>
					-->
		    		<tr>
		    			<th>上传BPMN文件</th>
		    			<td>
		    				<input id="file" type="file" name="bpmnFile"/>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
	    
	    </div>
    </div>
    <script type="text/javascript">
    	addBody();
   		mini.parse();
    	var success='${success}';
    	if(success=='true'){
    		mini.confirm("成功上传流程定义！", "确定关闭？",
    	            function (action) {
    	                if (action == "ok") {
    	                   CloseWindow('ok');
    	                } 
    	            }
    	      );
    	}
    	
    	function onClearTree(e){
			var obj = e.sender;
            obj.setText("");
            obj.setValue("");
		}
    	
    	var form=new mini.Form('uploadForm');
    	
    	function UploadFile(isDeployNew){
    		form.validate();
    		if(!form.isValid()){
    			return;
    		}
    		if(isDeployNew){//发布新版本
    			mini.get('isDeployNew').setValue(true);
    		}else{
    			mini.get('isDeployNew').setValue(false);
    		}
    		var file=document.getElementById('file');
    		if(file.value==''){
    			alert('请选择BPMN文件！');
    			return;
    		}
    		//上传文件
    		$("#uploadForm").submit();
    	}
    </script>
</body>
</html>