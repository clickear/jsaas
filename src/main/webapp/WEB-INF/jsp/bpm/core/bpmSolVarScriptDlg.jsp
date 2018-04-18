<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案的人员脚本运算</title>
<%@include file="/commons/list.jsp" %>
<!-- code codemirror start -->	
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
</head>
<body>
	<div class="vform-margin">
		<div class="mini-toolbar mini-toolbar-bottom topBtn">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
<!-- 						<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a> -->
		</div>
		<div>
			<pre>&nbsp;说明：运行脚本配置，可返回集合Collection&lt;OsUser&gt;或Collection&lt;OsGroup&gt;</pre>
			
			<div>&nbsp;&nbsp;&nbsp;<a href="#" onclick="showExpEditor()">脚本编写器</a></div>
		</div>
		<div class="shadowBox90">
			<form id="vform" class="form-outer2">
				<table class="table-detail" cellpadding="0" cellspacing="0" style="width:100%;border-collapse:collapse;">
					<thead>
						<tr>
							<th>脚本配置</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="padding: 0 !important">
								<textarea rows="4" cols="60" id="scriptCode" name="scriptCode"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
	
		mini.parse();
		
		var solId="${param['solId']}";
		var nodeId="${param['nodeId']}";
		var actDefId="${param['actDefId']}";
		
		var editor = CodeMirror.fromTextArea(document.getElementById("scriptCode"), {
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-groovy"
	     });
		
		function setData(config){
			if(!config) return;
			var conf=mini.decode(config);
			if(conf.script){
				editor.setValue(conf.script);	
			}
		}
		
		function ok(){
			CloseWindow('ok');
		}
		
		function getConfigJson(){
			var configDescp="用户或组值来自脚本运算的结果";
			return{
				config:{
					script:editor.getValue()
				},
				configDescp:configDescp
			};
		}
		
		//显示编辑器
		function showExpEditor(){
			_OpenWindow({
				title:'公式设计器',
				iconCls:'icon-formula',
				url:__rootPath+'/bpm/core/bpmSolution/expEditor.do?solId='+solId+'&nodeId='+nodeId+'&actDefId='+actDefId,
				width:780,
				height:450,
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var content=win.getFormula();
					editor.setValue(content);
				}
			});
		}
	</script>
</body>
</html>