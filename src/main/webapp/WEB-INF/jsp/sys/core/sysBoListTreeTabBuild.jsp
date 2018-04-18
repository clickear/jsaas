
<%-- 
    Document   : [系统自定义业务管理列表]编辑页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>SQL自定义</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/sql/sql.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	            	<a class="mini-button" iconCls="icon-start" plain="true" onclick="onRun">执行</a>
	               	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">确定</a>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	<div class="mini-fit">
		<div class="mini-layout" style="width:100%;height:100%">
			<div title="数据展示" region="south" style="width:100%;"  height="250" showSplitIcon="true" showHeader="true">
					<div id="sampleDataGrid" class="mini-datagrid" style="width:100%;height:100%"  allowCellEdit="true" 
				         url="${ctxPath}/sys/core/sysBoList/getSampleData.do?id=${param.id}"
				         allowCellSelect="true" >
					</div>
				
			</div>
			<div title="Tag树构建器" region="center" showSplitIcon="true" showCollapseButton="false" showHeader="true">
				<form id="form1" method="post">
						
						<table class="table-detail column_2" cellspacing="1" cellpadding="0">
							<tr>
								<th>数据源</th>
								<td>
									<input id="ds" name="ds" class="mini-buttonedit" onbuttonclick="onSelDatasource" style="width:80%"/>
								</td>
								<th>
									SQL构建方式
								</th>
								<td>
									<div class="mini-radiobuttonlist" id="useCondSql" name="useCondSql" value="${sysBoList.useCondSql}" data="[{id:'NO',text:'Freemarker SQL'},{id:'YES',text:'Groovy SQL'}]" onvaluechanged="changeConditionSql"></div>
								</td>
							</tr>
							<tr id="tr_freemarkerSql">
								<th>编写SQL</th>
								<td colspan="3">
									<div class="mini-toolbar">
										<a class="mini-menubutton " plain="true" menu="popupMenu" >插入上下文的SQL条件参数</a>
								    </div>
								    <ul id="popupMenu" class="mini-menu" style="display:none;">
								    	<li iconCls="icon-var" onclick="onSqlExp('{CREATE_BY_}')">当前用户ID</li>
								    	<li iconCls="icon-var" onclick="onSqlExp('{DEP_ID_}')">当前部门ID</li>
								    	<li iconCls="icon-var" onclick="onSqlExp('{TENANT_ID_}')">当前机构ID</li>
								    </ul>
									<textarea id="sql" name="sql" ></textarea>
								</td>						
							</tr>
							<tr id="tr_groovySql" style="display:none">
								<th>Groovy构建SQL</th>
								<td colspan="3" >
							    	<textarea id="condSqls" name="condSqls">${sysBoList.condSqls}</textarea>
								</td>
							<tr>
							<tr>
								<th>Tab标题：</th>
								<td>
									<input name="tabName" class="mini-textbox" required="true" style="width: 60%" />
								</td>
								<th>URL参数名：</th>
								<td>
									<input name="paramName" class="mini-textbox" required="true" style="width: 60%" />
								</td>
							</tr>
							<tr>
								<th>值绑定</th>
								<td colspan="3">
									<span>值字段：</span>
									<input id="idField" name="idField" class="mini-combobox" allowInput="true" 
								            textField="fieldLabel" valueField="fieldName" required="true" 
								            style="width:20%;" />
								 
									<span>显示字段</span>
								
									<input id="textField" name="textField" class="mini-combobox" allowInput="true" 
								           textField="fieldLabel" valueField="fieldName" required="true" 
								            style="width:20%;" />
									<span>父ID字段</span>
								
									<input id="parentField" name="parentField" class="mini-combobox" allowInput="true" 
								           textField="fieldLabel" valueField="fieldName" style="width:20%;" />
								</td>
							</tr>
							<tr>
								<th>树标识：</th>
								<td>
									<input name="treeId" class="mini-textbox"   style="width: 60%" required="true" onBlur="treeIdCopy"/>
								</td>
								<th>点击事件：</th>
								<td>
									<input id="onnodeclick" name="onnodeclick" class="mini-textbox" style="width: 60%" />
								</td> 
							</tr>
						</table>
				
				</form>
			</div>
			
		</div>
	</div>	
	
	
<script type="text/javascript">
	mini.parse();
	
	var form=new mini.Form('form1');
	
	function changeConditionSql(){
		var useCondSql=mini.get('useCondSql').getValue();
		
		if(useCondSql=="YES"){
			$("#tr_groovySql").css('display','');
			$("#tr_freemarkerSql").css('display','none');
		}else{
			$("#tr_groovySql").css('display','none');
			$("#tr_freemarkerSql").css('display','');
		}
	}
	
	function treeIdCopy(e){
		var s=e.sender;
		mini.get('onnodeclick').setValue(s.getValue()+"Click");
	}
		
	function setData(data,ds){
		form.setData(data);
		mini.get('ds').setValue(ds);
		mini.get('ds').setText(ds);
		changeConditionSql();
		sqlEditor.setValue(data.sql);
		groovysqlEditor.setValue(data.condSqls);
	}
	
	function onSqlExp(text){
		var doc = sqlEditor.getDoc();
   		var cursor = doc.getCursor(); // gets the line number in the cursor position
  		doc.replaceRange('$'+text, cursor); // adds a new line
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
	
	sqlEditor.setSize('100%', 100);
	
	var groovysqlEditor = CodeMirror.fromTextArea(document.getElementById("condSqls"), {
       //lineNumbers: true,
       matchBrackets: true,
       mode: "text/x-groovy"
    });
    
	groovysqlEditor.setSize('auto',100);

	function getData(){
		var data=form.getData();
		data.sql=sqlEditor.getValue();
		data.condSqls=groovysqlEditor.getValue();
		return data;
	}
	
	function getSampleData(){
		return mini.get('sampleDataGrid').getData();
	}
	
	function onRun(){
		var sampleGrid=mini.get('sampleDataGrid');
		var ds=mini.get('ds').getValue();
		var sql=null;
		var useCondSql=mini.get('useCondSql').getValue();
		if(useCondSql=='YES'){
			sql=groovysqlEditor.getValue();
		}else{
			sql=sqlEditor.getValue();
		}
		_SubmitJson({
			url:__rootPath+'/sys/core/sysBoList/onRun.do',
			data:{
				ds:ds,
				sql:sql,
				useCondSql:useCondSql
			},
			method:'POST',
			success:function(result){
				var idField=mini.get('idField');
				var textField=mini.get('textField');
				var parentField=mini.get('parentField');
				
				var fields=result.data.headers;
				
				idField.setData(fields);
				textField.setData(fields);
				parentField.setData(fields);
				
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
					sql:sql,
					useCondSql:useCondSql
				});
			}
		});
	}
</script>
</body>
</html>