
<%-- 
    Document   : 系统自定义业务PDF导出模板
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单单行导出PDF</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<style>
.CodeMirror {
    font-family: monospace;
    height: 500px;
    color: black;
}
</style>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	            	<a class="mini-button" iconCls="icon-export" plain="true" onclick="onSave">保存</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	                
	            </td>
	        </tr>
	    </table>
    </div>
	<div><textarea  style="width:90%;height:1500px" name="dataHtml" id="dataHtml">${dataHtml}</textarea></div>
<%-- 	<form name="hiform" id="hiform" action="${ctxPath}/sys/core/sysBoList/exportRow.do" method="post">
		<input id="html" type="hidden" name="html">
		<input id="boKey" type="hidden" name="boKey">
	</form> --%>

	<script type="text/javascript">
	    mini.parse(); 	
	    var editor=null;
		$(function(){
			initCodeMirror();//加载完所有textArea后将数据加载进去
		});
		function initCodeMirror(){
			var el=document.getElementById('dataHtml');
			editor= CodeMirror.fromTextArea(el, {
		        matchBrackets: true,
		        mode: "text/x-groovy"
		      });
		 }
	    	
        function onSave(){
        	debugger;
        	var html = editor.getValue();
        	var key = "${key}";
        	//var html = UE.getEditor('dataHtml').getContent();
        	_SubmitJson({
				url:__rootPath+'/bpm/form/bpmFormView/savePDFTemp.do',
				method:'POST',
				data:{
					pdfTemp:html,
					key:key
				},
				success:function(text){
					CloseWindow();
				}
			});
        }
        
       
	</script>
</body>
</html>