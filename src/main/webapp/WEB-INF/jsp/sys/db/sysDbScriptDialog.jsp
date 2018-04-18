<%
	//用户选择框
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>脚本输入页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@include file="/commons/list.jsp" %>
    <!-- code codemirror start -->	
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
	<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
	<!-- code minitor end -->
</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div region="south" showSplit="false" height="50" showHeader="false" showSplitIcon="false" cls="dialog-footer" style="text-align: center;" >
			<div class="mini-toolbar dialog-footer" style="border:none;">
			     <a class="mini-button" iconCls="icon-ok"   onclick="onOk()">保存</a>
			    <a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
			</div>	 
		 </div>
	  
	    <div region="center"  showHeader="false" showCollapseButton="false" style=" mini-toolbar-bottom">
	        	<div class="mini-toolbar" style="padding:2px;margin-top:2px; border:none;">
                	<a class="mini-button" iconCls="icon-help"  onclick="showScriptLibrary()">常用脚本</a>
				</div>
				<textarea id="valueDefTextArea"    style="width:100%;height:150px;" minWidth="200"></textarea>
	    </div>
	</div>
	
	<script type="text/javascript">
		mini.parse();
	

		function onCancel(){
			CloseWindow('cancel');
		}
		
		var editor;

		function initCodeMirror(){
			
				editor= CodeMirror.fromTextArea($("#valueDefTextArea")[0], {
			        matchBrackets: true,
			        mode: "text/x-groovy"
			      });
		}
		
		initCodeMirror();
		
		function onOk(){
			CloseWindow('ok');
		}
			
		
		function setScript(script){
			editor.setValue(script);
		}

		
		//返回信息
		function getScriptContent(){
			return editor.getValue();
		}
		
		function showScriptLibrary(){
			_OpenWindow({
				title:'脚本库',
				iconCls:'icon-script',
				url:__rootPath+'/bpm/core/bpmScript/libary.do',
				height:450,
				width:800,
				onload:function(){
					
				},
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					var win=this.getIFrameEl().contentWindow;
					var row=win.getSelectedRow();
					
					if(row){
				   		var doc = editor.getDoc();
				   		var cursor = doc.getCursor(); // gets the line number in the cursor position
				  		doc.replaceRange(row.example, cursor); // adds a new line
					}
				}
			});
		}
		
	</script>
</body>
</html>