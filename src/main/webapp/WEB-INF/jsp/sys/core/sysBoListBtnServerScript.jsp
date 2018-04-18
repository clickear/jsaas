<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<html>
<head>
<title>自定义按钮服务端脚本定义</title>
<%@include file="/commons/list.jsp" %>
<!-- code codemirror start -->	
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
</head>
<body>
	<div class="vform-margin">
		<div class="mini-toolbar ">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
		</div>
		 <pre>&nbsp;说明：运行脚本配置,执行Groovy动态脚本。插入客户端的参数，在脚本直接使用，如传入参数var1,则在脚本中直接使用var1,也可以这样使用，String myvar="<c:out value="$"/>{var1}";</pre>
		<div class="mini-toolbar">
			 插入脚本字段
			 <input 
			 	id="fieldCombo" 
			 	class="mini-combobox" 
			 	style="width:250px;" 
			 	onvaluechanged="appendFeild"
				textField="header" 
				valueField="field"  
				required="true"  
				showNullItem="true" 
				nullItemText="请选择字段..."
			/>      
			
			 <a class="mini-button" iconCls="icon-script" onclick="showScriptLibary()">插入脚本</a>
			 <a class="mini-button" iconCls="icon-associationForm" onclick="selDataSource">选择数据源</a>
		</div>
		<form id="vform" class="form-outer2 shadowBox90" style="margin-top: 20px;">
			<table class="table-detail" cellpadding="0" cellspacing="0" style="width:100%;border-collapse:collapse;">
				<thead>
					<tr>
						<th>脚本配置</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="padding: 0;">
							<textarea rows="4" cols="60" id="scriptCode" name="scriptCode"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
	
		mini.parse();

		var editor = CodeMirror.fromTextArea(document.getElementById("scriptCode"), {
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-groovy"
	     });
		
		function setData(data,fields){
			if(data.serverHandleScript){
				editor.setValue(data.serverHandleScript);	
			}
			var fieldCombo=mini.get('fieldCombo');
			fieldCombo.setData(fields);
		}
		
		function ok(){
			CloseWindow('ok');
		}
		
		function getData(){
			return editor.getValue();
		}
		
		function appendFeild(e){
			var field=e.sender.getValue();
			appendScript('\${'+field+'}');
		}
		
		//显示脚本库
		function showScriptLibary(){
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
						 appendScript(row.example);
					}
				}
			});
		}
		function selDataSource(e){
			
			_OpenWindow({
				title:'数据源选择',
				height:480,
				width:700,
				url:__rootPath+'/sys/core/sysDatasource/dialog.do',
				ondestroy:function(action){
					if(action!='ok') return;
					var iframe=this.getIFrameEl();
					var data=iframe.contentWindow.GetData();
					appendScript('"'+data.alias+'"');
				}
			});
		}
		//在当前活动的tab下的加入脚本内容
		function appendScript(scriptText){
         	var doc = editor.getDoc();
         	var cursor = doc.getCursor(); // gets the line number in the cursor position
        	doc.replaceRange(scriptText, cursor); // adds a new line
        }	
           	
	</script>
</body>
</html>