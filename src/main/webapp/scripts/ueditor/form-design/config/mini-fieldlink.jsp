<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>关联字段查询</title>
<%@include file="/commons/mini.jsp"%>

</head>
<body>
	<div style="display:none">
		<textarea id="inputScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
		<input id="inputColumnMapEditor" class="mini-combobox"  valueField="name" textField="comment" />
	</div>
	
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	    <table width="100%">
	        <tr>
	      		<td style="width:100%;">
					<a class="mini-button" iconCls="icon-edit" plain="true" onclick="toggleAdvSearch">高级自定义查询绑定</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="form-outer">
			<form id="configForm">
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					<caption></caption>
					<tr>
						<td colspan="4" style="height:20px;">【说明】关联字段查询用于通过本字段关联至另一地址查看具体的明细信息</td>
					</tr>
					<tr>
						<th>绑定控件名称</th>
						<td>
							<input id="cur_label" name="cur_label" class="mini-textbox asLabel"/>
						</td>
						<th>绑定控件标识</th>
						<td>
							<input id="cur_control" name="cur_control" class="mini-textbox asLabel"/>
						</td>
					</tr>
					<tr>
						<th>URL快捷方式</th>
						<td colspan="3">
							<a class="mini-button"  iconCls="icon-form" menu="" onclick="selCustomQueryForm">获取自定义表单URL</a>
							&nbsp;
							替换参数
							<input class="mini-combobox" name="paramFields" id="paramFields" valueField="name" textField="comment" onvaluechanged="changeParamField"/>
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
							<textarea id="url" name="url" style="width:100%"></textarea>
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
				<table class="table-detail column_2_m" style="display:none" id="advQueryTable">
					<tr>
						<td colspan="4" style="height:20px;">【说明】通过自定义查询绑定查询字段作为展示文本</td>
					</tr>
					<tr>
						<th>
							绑定自定义查询
						</th>
						<td colspan="3">
							<input class="mini-buttonedit" id="cusQuery" name="cusQuery" showClose="true" oncloseclick="_OnButtonEditClear" onbuttonclick="selectCustomQuery" style="width:250px;"/>
						</td>
					</tr>
					<tr>
						<th>绑定参数</th>
						<td colspan="3">
							
								<div id="gridInput" class="mini-datagrid" autoHeight="true" showPager="false" allowCellEdit="true" allowCellSelect="true" allowSortColumn="false"
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
						<th>显示文本替换规则</th>
						<td>
							<input class="mini-textbox" name="replaceRule" id="replaceRule" style="width:250px"/>
						</td>
						<th>插入查询返回值</th>
						<td> 
							<input class="mini-combobox" name="returnFields" id="returnFields" valueField="fieldName" textField="comment" onvaluechanged="changeReturnField"/>
							<input class="mini-button" iconCls="icon-right" onclick="appendReturnField"/>
						</td>
					</tr>
				</table>
			</form>
	</div>
	<script type="text/javascript">
		var curEditEl=editor.editdom;
		mini.parse();
		var form=new mini.Form('configForm');
		var gridInput=mini.get('gridInput');
		
		var urlEditor = CodeMirror.fromTextArea(document.getElementById("url"), {
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-markdown"
	     });
		urlEditor.setSize('auto',80);
		
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

		window.onload=function(){
			var curEl=$(curEditEl);
			var label=curEl.attr('label');
			var name=curEl.attr('name');
			
			mini.get('cur_label').setValue(label);
			mini.get('cur_control').setValue(name);
			initComboBox();
			
			var options=curEl.attr("data-options") || "{}";
			var json=mini.decode(options) ;
			var formData=json.linkFieldConfig;
			/**
			 * linkFieldConfig的格式如下：
			 * {
					"cur_label": "勘察单单号",
					"cur_control": "surveyFormId",
					"paramFields": "",
					"linkType": "newWindow",
					"iconCls": " icon-book-18",
					"cusQuery": "surveyOrderQuery",
					"pagesize": 10,
					"replaceRule": "${F_FILETITLE} ",
					"returnFields": "F_FILETITLE",
					"cusInputData": "[]",
					"url": "${ctxPath}/sys/customform/sysCustomFormSetting/form/accOpenSol/${surveyFormId}.do",
					"cusQueryName": "勘察单查询"
				}
			 */
			if(formData){
				form.setData(formData);
				urlEditor.setValue(formData.url);
				gridInput.setData(mini.decode(formData.cusInputData));
				mini.get('iconCls').setText(formData.iconCls);
				mini.get('icnClsBtn').setIconCls(formData.iconCls);
				var cusQuery=mini.get('cusQuery');
				if(cusQuery.getValue()!=''){
					cusQuery.setText(formData.cusQueryName);
					$('#advQueryTable').css('display','');
				}
			}
		};
		//取消按钮
		dialog.oncancel = function () {
		    
		};
		//确认
		dialog.onok = function (){
			var curEl=$(curEditEl);
			var options=curEl.attr("data-options") || "{}";
			var json=mini.decode (options) ;
			var formData=form.getData();
			formData.cusInputData=mini.encode(gridInput.getData());
			formData.url=urlEditor.getValue();
			var cusQuery=mini.get('cusQuery');
			formData.cusQueryName=cusQuery.getText();
			json.linkFieldConfig=formData;
			console.log(mini.encode(json.linkFieldConfig));
			curEl.attr("data-options",mini.encode(json));
		};

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
			      		doc.replaceRange("{ctxPath}/sys/customform/sysCustomFormSetting/form/"+ data.alias +"/{replace-field}.do"  , cursor);	
					}
				}
			});
		}
		
		function toggleAdvSearch(){
			$("#advQueryTable").toggle();
		}
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