<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>脚本配置</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
</head>
<body>
	
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<form id="miniForm">
		<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
			<caption>脚本</caption>
			<tr>
				<td>
						插入行值变量<input class="mini-combobox" name="paramFields" id="paramFields" textField="header" valueField="field" onvaluechanged="changeParamField"/>
						<ul id="popVarsMenu" style="display:none" class="mini-menu">
					 		<li iconCls="icon-var"  onclick="appUrlParam('ctxPath')">应用程序路径</li>
					    	<li iconCls="icon-var"  onclick="appUrlParam('curUserId')">当前用户ID</li>
					 		<li iconCls="icon-var"  onclick="appUrlParam('curDepId')">当前主部门</li>
					 		<li iconCls="icon-var"  onclick="appUrlParam('curTenantId')">当前机构Id</li>
					    </ul>
					<a class="mini-menubutton " iconCls="icon-var" menu="#popVarsMenu" >插入变量</a>
					<textarea id="script" name="script" style="width:90%"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<pre>
编写Groovy脚本示例：
//其中F_TOTAL为表单变量
						
if(!isExport){//导出Excel
    if(F_TOTAL>100){
    	return "超额";
    }else{
      	return "正常";
    }
}else{//HTML输出
    if(F_TOTAL>100){
    	return "<span color='red'>超额</span>";
    }else{
      	return "<span color='green'>正常</span>";
    }
}
					</pre>
				</td>
			</tr>
		</table>
		
	</form>
	<script type="text/javascript">
		
		mini.parse();
		
		var form=new mini.Form('miniForm');
		
		var gridInput=mini.get('gridInput');
		function onSave(){
			CloseWindow('ok');
		}
		
		function setData(formData){
			form.setData(formData);
			if(formData){
				
				if(formData.script){
					scriptEditor.setValue(formData.script);
				}
				
			}
		}
		
		function getData(){
			var data=form.getData();
			
			data.script=urlEditor.getValue();
			
			return data;
		}
		
		function setFields(fields){
			mini.get('paramFields').setData(fields);
		}
		
		function onClear(){
			CloseWindow('clear');
		}
		
		
		
		var scriptEditor = CodeMirror.fromTextArea(document.getElementById("script"), {
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-groovy"
	     });
		scriptEditor.setSize('auto',220);
		
       
       function changeParamField(e){
	       	var combo=e.sender;
	       	var text=combo.getValue();
	       	appUrlParam(text);
       }

		function appUrlParam(text){
			var doc = scriptEditor.getDoc();
	      	var cursor = doc.getCursor(); // gets the line number in the cursor position
	     	doc.replaceRange(text, cursor); // adds a new line
		}

	</script>				
</body>
</html>