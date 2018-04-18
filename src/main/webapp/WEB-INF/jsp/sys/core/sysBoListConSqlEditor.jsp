
<%-- 
    Document   : [系统自定义业务管理列表]编辑页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>分支SQL定义配置</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/sql/sql.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
</head>
<body>
				<div class="mini-toolbar mini-toolbar-bottom" >
				    <table style="width:100%;">
				        <tr>
				            <th style="width:100%;" id="toolbarBody">
				               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">确定</a>
				                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
				            </th>
				        </tr>
				    </table>
				</div>

			
				<div class="mini-fit">
		
				<form id="form1" method="post">
						<table class="table-detail" cellspacing="1" cellpadding="0">
							<tr>
								<th width="15%">条件名称</th>
								<td width="85%">
									<input name="name" class="mini-textbox" required="true" style="width: 90%" />
								</td>
							</tr>
							<tr>
								<th width="15%">编写SQL</th>
								<td width="85%">
									<div class="mini-toolbar" style="border-top:none;border-left:none;border-right:none;">
										<span>数据源:</span>
										<input id="ds" name="ds" style="width:250px;" class="mini-buttonedit" onbuttonclick="onSelDatasource" showClose="true" oncloseclick="_OnButtonEditClear"/>
										<a class="mini-menubutton " plain="true" menu="popupMenu" >插入上下文的SQL条件参数</a>
										<a class="mini-button" iconCls="icon-start" plain="true" onclick="onRun">执行</a>
								    </div>
								    <ul id="popupMenu" class="mini-menu" style="display:none;">
								    	<li iconCls="icon-var" onclick="onSqlExp('{CREATE_BY_}')">当前用户ID</li>
								    	<li iconCls="icon-var" onclick="onSqlExp('{DEP_ID_}')">当前部门ID</li>
								    	<li iconCls="icon-var" onclick="onSqlExp('{TENANT_ID_}')">当前机构ID</li>
								    </ul>
									<textarea id="sql" name="sql" ></textarea>
								</td>						
							</tr>
							<tr>
								<th width="15%">条件表达式<br/>
									Groovy表达式,<br/>返回(true,false)
								</th>
								<td width="85%">
									<div class="mini-toolbar" style="border-top:none;border-left:none;border-right:none;">
										<a href="javascript:showScriptLibary()">常用脚本</a>
										<a class="mini-menubutton " plain="true" menu="popupMenu2" >插入上下文变量</a>
									</div>
									<ul id="popupMenu2" class="mini-menu" style="display:none;">
								    	<li iconCls="icon-var" onclick="onCondExp('userId')">当前用户ID</li>
								    	<li iconCls="icon-var" onclick="onCondExp('depId')">当前部门ID</li>
								    	<li iconCls="icon-var" onclick="onCondExp('tenantId')">当前机构ID</li>
								    </ul>
									<textarea id="condition" class="textarea" name="condition" style="width:90%;height:100%"></textarea>
								</td> 
							</tr>
						</table>
				</form>
				<div id="dataDiv" style="display:none">
					<div id="sampleDataGrid" class="mini-datagrid" style="width:100%;height:300px;"  allowCellEdit="true" 
				         url="${ctxPath}/sys/core/sysBoList/getSampleData.do?id=${param.id}"
				         allowCellSelect="true" >
					</div>
				</div>
			</div>
	
<script type="text/javascript">
	mini.parse();
	
	var form=new mini.Form('form1');
	
	
	
	function onSqlExp(text){
		var doc = sqlEditor.getDoc();
   		var cursor = doc.getCursor(); // gets the line number in the cursor position
  		doc.replaceRange('$'+text, cursor); // adds a new line
	}
	
	function onCondExp(text){
		var doc = conditionEditor.getDoc();
   		var cursor = doc.getCursor(); // gets the line number in the cursor position
  		doc.replaceRange(text, cursor); // adds a new line
	}
	
	
	function onSelDatasource(e){
		var btnEdit=e.sender;
		mini.open({
			url : "${ctxPath}/sys/core/sysDatasource/dialog.do",
			title : "选择数据源",
			width : 650,
			height : 380,
			ondestroy : function(action) {
				if (action == "ok") {
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					data = mini.clone(data);
					if (data) {
						btnEdit.setValue(data.alias);
						btnEdit.setText(data.name);
					}
				}
			}
		});
	}

	function onSave(){
		CloseWindow('ok');
	}
	

	var sqlEditor = CodeMirror.fromTextArea(document.getElementById("sql"), {
       lineNumbers: true,
       matchBrackets: true,
       mode: "text/x-sql"
     });
	
	var conditionEditor = CodeMirror.fromTextArea(document.getElementById('condition'), {
        lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-groovy"
      });
	
	sqlEditor.setSize('100%', 150);
	conditionEditor.setSize('100%',150);

	//在当前活动的tab下的加入脚本内容
	function appendScript(scriptText){
   		var doc = conditionEditor.getDoc();
   		var cursor = doc.getCursor(); 
  		doc.replaceRange(scriptText, cursor);
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
	
	function setData(data){
		form.setData(data);
		mini.get('ds').setText(data.dsName);
		sqlEditor.setValue(data.sql);
		conditionEditor.setValue(data.condition);
	}
	

	function getData(){
		var data=form.getData();
		data.dsName=mini.get('ds').getText();
		data.sql=sqlEditor.getValue();
		data.condition=conditionEditor.getValue();
		
		return data;
	}
	function onRun(){
		
		var sampleGrid=mini.get('sampleDataGrid');
		var ds=mini.get('ds').getValue();
		var sql=sqlEditor.getValue();
		_SubmitJson({
			url:__rootPath+'/sys/core/sysBoList/onRun.do',
			data:{
				ds:ds,
				sql:sql
			},
			method:'POST',
			success:function(result){

				var fields=result.data.headers;

				var cols=[{type: "indexcolumn"}];
				
				for(var i=0;i<fields.length;i++){
					cols.push({
						header:fields[i].fieldLabel,
						field:fields[i].fieldName,
						editor: { type: "textbox"}
					});
				}
				sampleGrid.setUrl(__rootPath+'/sys/core/sysBoList/getDataBySql.do');
				sampleGrid.set({
					columns:cols
				});
				sampleGrid.load({
					ds:ds,
					sql:sql
				});
				$("#dataDiv").css('display','');
			}
		});
	}
</script>
</body>
</html>