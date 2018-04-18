<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>连接类型的渲染</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/markdown/markdown.js"></script>
</head>
<body>
	<div style="display:none">
		<textarea id="inputScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
		<input id="inputColumnMapEditor" class="mini-combobox"  textField="header" valueField="field" />
	</div>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<form id="miniForm">
		<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
			<tr>
				<th width="150">
					关联自定义查询
				</th>
				<td colspan="3">
					<input class="mini-buttonedit" id="cusQuery" name="cusQuery" allowInput="false" showClose="true" oncloseclick="_OnButtonEditClear" onbuttonclick="selectCustomQuery" style="width:250px;"/>
				</td>
			</tr>
			<tr>
				<th>绑定参数</th>
				<td colspan="3" style="padding:5px;">
					<div id="gridInput" class="mini-datagrid" 
						height="auto" showPager="false" allowCellEdit="true" allowCellSelect="true" allowSortColumn="false"
						oncellbeginedit="gridInputCellBeginEdit">
					    <div property="columns">
					        <div field="fieldName" displayfield="comment"  headerAlign="center" allowSort="true">查询条件
					        </div>    
					        <div field="mode" displayfield="modeName"  headerAlign="center" allowSort="true">方式
					        	 <input property="editor" class="mini-combobox" style="width:100%;"  data="[{id:'mapping',text:'映射'}, {id:'script', text: '脚本'}]"/>       
					        </div>
					        <div field="bindVal"   headerAlign="center" allowSort="true">绑定值
					        </div>    
					    </div>
					</div>
				</td>
			</tr>
			<tr>
				<th width="150">链接文本显示规则</th>
				<td>
					<input class="mini-textbox" name="replaceRule" id="replaceRule" style="width:90%"/>
				</td>
				<th width="150">插入查询返回值</th>
				<td> 
					<input class="mini-combobox" name="returnFields" id="returnFields" valueField="fieldName" textField="comment" onvaluechanged="changeReturnField"/>
					<input class="mini-button" iconCls="icon-right" onclick="appendReturnField"/>
				</td>
			</tr>
			<tr>
				<th>URL快捷方式</th>
				<td colspan="3">
					<a class="mini-button"  iconCls="icon-form" menu="" onclick="selCustomQueryForm">获取自定义表单URL</a>
					&nbsp;
					替换参数
					<input class="mini-combobox" name="paramFields" id="paramFields" textField="header" valueField="field" onvaluechanged="changeParamField"/>
						<ul id="popVarsMenu" style="display:none" class="mini-menu">
					 		<li iconCls="icon-var"  onclick="appUrlParam('ctxPath')">应用程序路径</li>
					    </ul>
					<a class="mini-menubutton " iconCls="icon-var" menu="#popVarsMenu" >插入变量</a>
				</td>
			</tr>
			
			<tr>
				<th>URL(<span style="color:red">*</span>)</th>
				<td colspan="3">
					<textarea id="url" name="url" style="width:90%"></textarea>
				</td>
			</tr>
			
			<tr>
				<th>打开方式<span style="color:red">*</span></th>
				<td colspan="3" >
					<input name="linkType" required="true" class="mini-combobox" textField="linkTypeName" valueField="linkType" data="[{linkType:'newWindow',linkTypeName:'新浏览器窗口'},{linkType:'openWindow',linkTypeName:'弹出窗口'}]">
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
				gridInput.setData(formData.inputFields);
				mini.get('cusQuery').setText(formData.cusQueryName);
				if(formData.url){
					urlEditor.setValue(formData.url);
				}
				if(formData.reFieldData){
					mini.get('returnFields').setData(mini.decode(formData.reFieldData));
				}
			}
		}
		
		function getData(){
			var data=form.getData();
			data.inputFields=gridInput.getData();
			data.cusQueryName=mini.get('cusQuery').getText();
			data.url=urlEditor.getValue();
			data.reFieldData=mini.get('returnFields').getData();
			return data;
		}
		
		function setFields(fields){
			mini.get('paramFields').setData(fields);
		}
		
		function onClear(){
			CloseWindow('clear');
		}
		
		//输入条件表格点击时处理。
		function gridInputCellBeginEdit(e){
			var field=e.field;
			var record=e.record;
			//当点击绑定值字段时处理。
			if(field=='bindVal'){
				if(record.mode=="mapping"){
					var editor=mini.get("inputColumnMapEditor");
					editor.setData(mini.get('paramFields').getData());
					e.column.editor=editor;
					e.editor=editor;
				}else{
					var editor=mini.get("inputScriptEditor");
					e.column.editor=editor;
					e.editor=editor;
				}
			}
		}
		
		var urlEditor = CodeMirror.fromTextArea(document.getElementById("url"), {
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-markdown"
	     });
	     
		urlEditor.setSize('auto',80);
		
       function changeReturnField(e){
       		var combo=e.sender;
	       	var text=combo.getValue();
	       	var rule=mini.get('replaceRule');
	       	rule.setValue(rule.getValue()+'{ref.' + text + '} ');
       }
       function changeParamField(e){
	       	var combo=e.sender;
	       	var text=combo.getValue();
	       	appUrlParam("self."+text);
       }
       
       function initComboBox(){
			var metaJson=getMetaData(editor,curEditEl);
			var fields=metaJson['main'].fields;
			mini.get('paramFields').setData(fields);
		}
		
		function appendReturnField(e){
			var val=mini.get('returnFields').getValue();
			appUrlParam("ref."+val);
		}
		
		function appUrlParam(text){
			var doc = urlEditor.getDoc();
       		var cursor = doc.getCursor(); // gets the line number in the cursor position
      		doc.replaceRange("{" + text + "}", cursor); // adds a new line
		}
		
		//绑定自定义表单
		function selCustomQueryForm(){
			_OpenWindow({
				title:'自定义表单方案URL',
				url:__rootPath+'/sys/customform/sysCustomFormSetting/dialog.do',
				height:400,
				width:800,
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var data=win.getData();
					if(data!=null){
						var doc = urlEditor.getDoc();
			       		var cursor = doc.getCursor(); 
			      		doc.replaceRange("{ctxPath}/sys/customform/sysCustomFormSetting/detail/"+ data.alias +"/{replace-field}.do"  , cursor);	
					}
				}
			});
		}
		
		//绑定自定义查询 
		function selectCustomQuery(e){
			var btn=e.sender;
			_OpenWindow({
				url:__rootPath+'/sys/db/sysSqlCustomQuery/dialog.do',
				title:'自定义查询',
				height:500,
				width:800,
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var data=win.getData();
				
					var initParams=[];
					if(data.whereField){
						var initFields=mini.decode(data.whereField);
						for(var i=0;i<initFields.length;i++){
							if(initFields[i].valueSource=='param'){
								initParams.push(initFields[i]);
							}
						}
					}
					mini.get('returnFields').setData(data.resultField);
					gridInput.setData(initParams);
					btn.setText(data.name);
					btn.setValue(data.key);
				}
			});
		}
	</script>				
</body>
</html>