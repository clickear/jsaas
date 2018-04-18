<%@page contentType="text/html" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>流程全局事件或节点接口调用配置--SQL调用配置</title>
	<%@include file="/commons/edit.jsp"%>
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/sql/sql.js"></script>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar" >
		<a class="mini-button" plain="true" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" plain="true" iconCls="icon-close" onclick="CloseWindow()">关闭</a>		
	</div>
	
	<table class="table-detail column_2_m"  cellspacing="1" cellpadding="0" style="width:100%;">
		<caption>调用SQL配置</caption>
		<tr>
			<td colspan="2">
				<ul id="popVarsMenu" style="display:none" class="mini-menu">
		 			<c:forEach items="${configVars}" var="var">
			 			<li iconCls="icon-var"  onclick="appendScript('${var.key}')">${var.name}[${var.key} (${var.type})] </li>
			 		</c:forEach>
			    </ul>
				<div class="mini-toolbar" style="margin-bottom:2px;padding:10px;">
			 		<a class="mini-menubutton "  iconCls="icon-var"  menu="#popVarsMenu" >插入流程变量</a>
			 		<a class="mini-button" iconCls="icon-formAdd" plain="true" onclick="showFormFieldDlg()">从表单中添加</a>
			 		
		 			数据源:<input class="mini-buttonedit" name="dbAlias" onbuttonclick="selDataSource" showClose="true" oncloseclick="_OnButtonEditClear"></div>
		 		</div>
			</td>
		</tr>
		<tr>
			<td>
				<textarea id="script" name="script" style="width:100%;height:380px"></textarea>
			</td>
		</tr>
	</table>
		
	<script type="text/javascript">
		mini.parse();
		var scriptEditor = CodeMirror.fromTextArea(document.getElementById('script'),{
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-sql"
		});
		
		//在当前活动的tab下的加入脚本内容
		function appendScript(scriptText){
       		var doc = scriptEditor.getDoc();
       		var cursor = doc.getCursor(); // gets the line number in the cursor position
      		doc.replaceRange(scriptText, cursor); // adds a new line
		}
		
		function showFormFieldDlg(){
			var nodeId='${param.nodeId}';
			var actDefId='${param.actDefId}';
			var solId='${param.solId}';
			_OpenWindow({
				title:'选择表单字段',
				height:450,
				width:680,
				url:__rootPath+'/bpm/core/bpmSolution/modelFieldsDlg.do?solId='+solId+'&nodeId='+nodeId+'&actDefId='+actDefId,
				ondestroy:function(action){
					if(action!='ok') return;
					var iframe=this.getIFrameEl();
					var fields=iframe.contentWindow.getSelectedFields();
					for(var i=0;i<fields.length;i++){
						if(i>0){
							appendScript(' ');
						}
						var f=fields[i].formField.replace('.','_');
						//var field= "\${"+  f + "}";
						appendScript(f);
					}
				}
			});
		}

		function selDataSource(e){
			var btn=e.sender;
			_OpenWindow({
				title:'数据源选择',
				height:480,
				width:700,
				url:__rootPath+'/sys/core/sysDatasource/dialog.do',
				ondestroy:function(action){
					if(action!='ok') return;
					var iframe=this.getIFrameEl();
					var data=iframe.contentWindow.GetData();
					btn.setText(data.alias);
					btn.setValue(data.alias);
				}
			});
		}
		
		//获得脚本的配置 
		function getData(){
			return scriptEditor.getValue();
		}
		
		function setData(script){
			scriptEditor.setValue(script);
		}
		
	</script>
</body>
</html>
