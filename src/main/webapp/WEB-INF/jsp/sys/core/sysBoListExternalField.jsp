<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>列表中的外部字段关联查询</title>
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
		<a class="mini-button" iconCls="icon-save"  plain="true" onclick="onSave">保存</a>
		<a class="mini-button" iconCls="icon-clear"  plain="true" onclick="onClear">清空</a>
		<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
	</div>

	<div id="p1" class="form-outer">
		<form id="dataForm" method="post">
				<table class="table-detail column_2_m" style="width:100%">
					<tr>
						<th width="150">
							关联查询
						</th>
						<td colspan="3">
							<input class="mini-buttonedit" id="cusQuery" name="cusQuery" showClose="true" oncloseclick="_OnButtonEditClear" onbuttonclick="selectCustomQuery" style="width:250px;"/>
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
						<th width="150">链接文本规则</th>
						<td>
							<input class="mini-textbox" name="replaceRule" id="replaceRule" style="width:250px"/>
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
							 		<li iconCls="icon-var"  onclick="appUrlParam('curUserId')">当前用户ID</li>
							 		<li iconCls="icon-var"  onclick="appUrlParam('curDepId')">当前主部门</li>
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
						<th>打开方式</th>
						<td>
							<input name="linkType" class="mini-combobox" textField="linkTypeName" valueField="linkType" data="[{linkType:'newWindow',linkTypeName:'新浏览器窗口'},{linkType:'openWindow',linkTypeName:'弹出窗口'}]">
						</td>
						<th>连接旁边图标</th>
						<td>
							<input name="iconCls" id="iconCls"  class="mini-buttonedit" onbuttonclick="selectIcon" style="width:70%"/>
							<a class="mini-button" id="icnClsBtn"></a>
						</td>
					</tr>
				</table>
		</form>
	</div>

	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('dataForm');
		var gridInput=mini.get('gridInput');
		function onSave(){
			CloseWindow('ok');
		}
		
		function setData(formData){
			form.setData(formData);
			if(formData){
				gridInput.setData(formData.inputFields);
				mini.get('cusQuery').setText(formData.cusQueryName);
				mini.get('iconCls').setText(formData.iconClsName);
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
			data.iconClsName=mini.get('iconCls').getText();
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
		//选择图标
       function selectIcon(e){
    	   var btn=e.sender;
    	   _IconSelectDlg(function(icon){
			btn.setText(icon);
			btn.setValue(icon);
			mini.get('icnClsBtn').setIconCls(icon);
		});
       }
       function changeReturnField(e){
       		var combo=e.sender;
	       	var text=combo.getValue();
	       	var rule=mini.get('replaceRule');
	       	rule.setValue(rule.getValue()+'{' + text + '} ');
       }
       function changeParamField(e){
	       	var combo=e.sender;
	       	var text=combo.getValue();
	       	appUrlParam(text);
       }
       
       function initComboBox(){
			var metaJson=getMetaData(editor,curEditEl);
			var fields=metaJson['main'].fields;
			mini.get('paramFields').setData(fields);
		}
		
		function appendReturnField(e){
			var val=mini.get('returnFields').getValue();
			appUrlParam(val);
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