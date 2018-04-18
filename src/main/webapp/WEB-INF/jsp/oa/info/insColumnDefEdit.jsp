
<%-- 
    Document   : [ins_column_def]编辑页
    Created on : 2017-08-16 11:39:47
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>自定义栏目编辑</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>

</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="insColumnDef.colId" />
	
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
		
			<input id="pkId" name="id" class="mini-hidden" value="${insColumnDef.colId}" />
			<table class="table-detail column_1_m" cellspacing="1" cellpadding="0">
				<caption>自定义栏目基本信息</caption>
				<tr>
					<th>栏目名称</th>
					<td>
						<input name="name" value="${insColumnDef.name}" class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>栏目KEY</th>
					<td>
						<input name="key" value="${insColumnDef.key}" class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>更多Url</th>
					<td>
						<input name="dataUrl" value="${insColumnDef.dataUrl}" class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<tr>
					<th>调用方法</th>
					<td>
					
					<input name="function" value='${insColumnDef.function}' class="mini-textbox"   style="width: 90%" />
					<a href="javascript:showScriptLibary()">常用脚本</a>
					<a href="javascript:showSQLLibary()">自定义SQL</a>
					</td>
				</tr>
				<tr>
					<th>模板HTML</th>
					<td>
						<textarea id="content" class="textarea" name="script" style="width:90%;height:100%">${insColumnDef.templet}</textarea>
					</td>
				</tr>
			</table>
		
		</form>
	</div>
	
	
	<rx:formScript formId="form1" baseUrl="oa/info/insColumnDef"
		entityName="com.redxun.oa.info.entity.InsColumnDef" />
	<script >
	addBody();
	function SaveData() {
		var form = new mini.Form("form1");
        form.validate();
        if (!form.isValid()) {
            return;
        }
        var formData=form.getData();
        var temp = editor.getValue();
        formData.templet = temp;
        var config={
        	url:"${ctxPath}/oa/info/insColumnDef/save.do",
        	method:'POST',
        	data:formData,
        	success:function(result){
        		//如果存在自定义的函数，则回调
        		if(isExitsFunction('successCallback')){
        			successCallback.call(this,result);
        			return;	
        		}
        		
        		CloseWindow('ok');
        	}
        }
        
        _SubmitJson(config);
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
				if(action!='ok')  return;
				
				var win=this.getIFrameEl().contentWindow;
				var row=win.getSelectedRow();
				
				if(row){
					 appendScript(row.example);
				}
			}
		});
	}
	
	//显示SQL
	function showSQLLibary(){
		_OpenWindow({
			title:'自定义SQL',
			iconCls:'icon-script',
			url:__rootPath+'/sys/db/sysSqlCustomQuery/dialog.do',
			height:450,
			width:800,
			onload:function(){
				
			},
			ondestroy:function(action){
				if(action!='ok'){
					return;
				}
				var win=this.getIFrameEl().contentWindow;
				var row=win.getData();
				
				if(row){
					 appendSql(row);
				}
			}
		});
	}
	
	function appendSql(row){
		var textbox = mini.getByName('function');
		var name = 'portalScript.getSysBoList(colId,"'+row.key+'")';
		textbox.setValue(name);
		var resultField =mini.decode(row.resultField);
 		var config={
        	url:"${ctxPath}/oa/info/insColumnDef/getSQLHtml.do",
        	method:'POST',
        	data:{resultField:row.resultField,
        			bokey:row.name},
        	success:function(result){
        		mini.get("isNews").setValue("NO");
				editor.setValue(result.data);
        	}
        }
 		
 		_SubmitJson(config);
 	
	}
	
	//在当前活动的tab下的加入脚本内容
	function appendScript(scriptText){
		var textbox = mini.getByName('function');
		textbox.setValue(scriptText);        
	}

	
		var editor=null;
		$(function(){
			initCodeMirror();//加载完所有textArea后将数据加载进去
		});
		function initCodeMirror(){
			var el=document.getElementById('content');
			editor= CodeMirror.fromTextArea(el, {
		        matchBrackets: true,
		        mode: "text/x-groovy"
		      });
		 }
	</script>
</body>
</html>