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
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/xml/xml.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
<script src="${ctxPath}/scripts/common/jquery.base64.js"></script>

<style>
	.CodeMirror{
		height: 600px;
	}
</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveData">保存</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	                
	            </td>
	        </tr>
	    </table>
    </div>
    
	<div id="p1" class="form-outer2">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="id" class="mini-hidden"
					value="${bpmFormView.viewId}" />
				<table class="table-detail table-detail2" cellspacing="1" cellpadding="0">
					<caption>表单模版编辑</caption>

					<tr>
						<th>模版名称 ：</th>
						<td><input name="name" value="${bpmFormView.name}"
							class="mini-textbox" vtype="maxLength:50" enabled="false"/>

						</td>
					</tr>

					<tr>
						<th>模版 ：</th>
						<td style="max-width: 1024px;width: 93%;heigth:80%">
 							<textarea id="template" name="template"></textarea> 
						</td>
					</tr>

				</table>
			</div>
		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="bpm/form/bpmFormView"
		entityName="com.redxun.bpm.form.entity.BpmFormView" />
	<script>
	var bpmFormView={};
	var editorMsg;
	$(function(){
		_SubmitJson({
			url:__rootPath+'/bpm/form/bpmFormView/getTemplate.do?viewId=${bpmFormView.viewId}',
			showMsg:false,
			success:function(result){
				var templateObj=$("#template")
				bpmFormView=result.data;
				templateObj.val(result.data.template);
				
				editorMsg= CodeMirror.fromTextArea(templateObj[0], {
				       lineNumbers: true,
				       matchBrackets: true,
				       lineWrapping:true,
				       mode: "text/xml"
			   	});
			}
		});	
	})
	
	function saveData(){
		var template=editorMsg.getValue();
		debugger;
        var config={
	        	url:__rootPath+"/bpm/form/bpmFormView/saveTemplate.do",
	        	method:'POST',
	        	data:{
	        		viewId:'${bpmFormView.viewId}',
	        		template:"<div>"+$.base64.btoa(template,true,'utf8')+"</div>"
	        	},
	        	success:function(result){
        			CloseWindow('ok');	
	        	}
	        }
	        _SubmitJson(config);
	}
		
	
	</script>	
		
</body>
</html>