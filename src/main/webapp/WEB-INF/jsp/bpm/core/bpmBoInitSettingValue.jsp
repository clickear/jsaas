<%-- 
    Document   : [OdDocument]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>业务模型设置</title>
<%@include file="/commons/edit.jsp"%>
<!-- code codemirror start -->	
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
<!-- code minitor end -->
<style type="text/css">
.CodeMirror{
	border: 1px dashed silver;
	height: 150px !important;
}
</style>
</head>
<body>
	
	<div class="mini-toolbar">
			<a class="mini-button" iconCls="icon-ok" plain="true" onclick="CloseWindow('ok')">确定</a> 
			<a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow('cancel')">关闭</a>
	</div>
	<div class="mini-fit" >
	
		<form id="form1" method="post">
			<div  name="boSetting">
				<div class="form-inner">
				
					<table class="table-detail column_1" cellspacing="1" cellpadding="0">
						<tr>
							<th >字段名称</th>
							<td><input id="field" name="field" class="mini-textbox asLabel" readOnly="true" /></td>
						</tr>
						<tr>
							<th>取值类型</th>
							<td><input id="valType" name="valType" class="mini-combobox" style="width: 150px;" textField="text" valueField="id" data="data"
								allowInput="false" showNullItem="false" value="manual" valueFromSelect="true" required="true" onvaluechanged="changeType" /></td>
						</tr>
						<tr id="constantTr" style="display:none">
							<th>值设置</th>
							<td>
								<div>值：</div>
								<input class="mini-combobox" id="valueIdInput" required="true" style="width: 150px;" name="val" textField="val" 
									valueField="key" url="${ctxPath}/bpm/core/bpmSolFv/getContextConstant.do" showNullItem="false" />
									
								<c:if test="${param.isSingle==0 }">
									<span>显示值：</span>
									<input class="mini-combobox" id="valueIdInput_name" required="true" style="width: 150px;" name="val" textField="val" 
										valueField="key" url="${ctxPath}/bpm/core/bpmSolFv/getContextConstant.do" showNullItem="false" />
								</c:if>
							
							</td>
						</tr>
						<tr id="scriptTr" style="display:none">
							<th>值设置</th>
							<td>
									<span><a class="mini-button" plain="false" onclick="insertInto()" iconCls="icon-export">快捷插入→</a></span>
									<input class="mini-combobox" id="fieldCombo" style="width: 150px;"   textField="name" valueField="name" 
										url="${ctxPath}/sys/bo/sysBoEnt/getFieldByBoDefId.do?tableName=${param.tableName}&boDefId=${param.boDefId}" showNullItem="false"  />
									<span><a class="mini-button" plain="false" onclick="showScriptLibrary()">脚本选择</a></span>
									<div>值：</<div>
									<textarea id="scriptVal"  style="width: 90%;height: 100px;"  ></textarea>
									<c:if test="${param.isSingle==0 }">
										<span>显示值：</span>
										<textarea id="scriptVal_name"  style="width: 90%;height: 100px;"  ></textarea>
									</c:if>
							</td>
						</tr>
						<tr id="manualTr" style="display:none">
							<th>值设置</th>
							<td>
								<div>值：</<div>
								<br/>
								<input id="manualVal" class="mini-textbox" style="width: 80%;" required="true"  ></input>
								
								<c:if test="${param.isSingle==0 }">
									<div>显示值：</div>
									<input id="manualVal_name" class="mini-textbox" style="width: 80%;" required="true"  ></input>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	
	</div>
	
	<script type="text/javascript">
	var isSingle=${param.isSingle};
	var data=[{ id: 'constant', text: '常量' },{ id: 'script', text: '脚本'},{ id: 'manual', text: '固定值'}];
	var form=new mini.Form('form1');
	var solId;
	var nodeId;
	var actDefId;
	
	mini.parse();
	
	function cancelWindow(){
		CloseWindow()
	}
	var editor=null;
	$(function(){
		changeType();
		initCodeMirror();//加载完所有textArea后将数据加载进去
	});
	
	function initData(json){
		form.setData(json);
		changeType();
		
		if(json.valType=='script'){
			editor.setValue(json.val);
			if(isSingle==0){
				editor_name.setValue(json.val_name);
			}
		}else if(json.valType=='manual'){
			mini.get('manualVal').setValue(json.val);
			if(isSingle==0){
				mini.get('manualVal_name').setValue(json.val_name);
			}
		}else{
			mini.get('valueIdInput').setValue(json.val);
			if(isSingle==0){
				mini.get('valueIdInput_name').setValue(json.val_name);
			}
		}
	}

	function getConfig(){
		var json=form.getData();
		delete json.field;
		if(json.valType=='script'){
			json.val=editor.getValue();
			if(isSingle==0){
				json.val_name=editor_name.getValue();
			}
		}else if(json.valType=='manual'){
			json.val=mini.get('manualVal').getValue();
			if(isSingle==0){
				json.val_name=mini.get('manualVal_name').getValue();
			}
		}
		else{
			json.val=mini.get('valueIdInput').getValue();
			if(isSingle==0){
				json.val_name=mini.get('valueIdInput_name').getValue();
			}
		}
		
		return json;
	}
	//更改类型
	function changeType(){
		var valType=mini.get("valType").getValue();
		if(!valType){
			mini.get("valType").setValue('constant');
			valType='constant';
		}
		if(valType=='constant'){
			$("#constantTr").css('display','');
			$("#scriptTr").css('display','none');
			$("#manualTr").css('display','none');
		}else if(valType=='script'){
			$("#constantTr").css('display','none');
			$("#scriptTr").css('display','');
			$("#manualTr").css('display','none');
		}else{
			$("#constantTr").css('display','none');
			$("#scriptTr").css('display','none');
			$("#manualTr").css('display','');
		}
	}

	function appendContent(tabName,content){
		var editor=editorJson[tabName];
   		var doc = editor.getDoc();
   		var cursor = doc.getCursor(); // gets the line number in the cursor position
  		doc.replaceRange(content, cursor); // adds a new line
	} 
	
	function initCodeMirror(){
		var el=document.getElementById('scriptVal');
		
		var el_name=document.getElementById('scriptVal_name');
		editor= CodeMirror.fromTextArea(el, {
	        matchBrackets: true,
	        mode: "text/x-groovy"
	      });
		if(isSingle==0){
			editor_name= CodeMirror.fromTextArea(el_name, {
		        matchBrackets: true,
		        mode: "text/x-groovy"
		      });
		}
		
	 }
	
	function insertInto(){
		var field=mini.get('fieldCombo').getValue();
		if(field!=''){
			appendText(field);
		}
	}
	
 	function appendText(scriptText){
   		var doc = editor.getDoc();
   		var cursor = doc.getCursor(); // gets the line number in the cursor position
   		doc.replaceRange(scriptText, cursor); // adds a new line
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
				if(action!='ok') return;
				var win=this.getIFrameEl().contentWindow;
				var row=win.getSelectedRow();
				appendText(row.example);
			}
		});
	}
	</script>
</body>
</html>