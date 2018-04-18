<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>按钮编辑框-mini-buttonedit</title>
<%@include file="/commons/mini.jsp"%>
</head>
<body>
	<div class="form-outer">
			<form id="miniForm">
				<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
					<tr>
						<th>长　度</th>
						<td><input id="length" required="true" name="length"
							class="mini-textbox" value="50" vtype="max:4000" style="width: 60px" /></td>
						<th>最小长度</th>
						<td><input id="minlen" name="minlen" class="mini-textbox"
							value="0" vtype="max:4000" /></td>
					</tr>
					<tr>
						<th>必　填<span class="star">*</span></th>
						<td><input class="mini-checkbox" name="required"
							id="required" /> 是</td>
						<th>允许文本输入</th>
						<td><input class="mini-checkbox" name="allowinput"
							id="allowinput" /> 是</td>
					</tr>
					<tr>
						<th>自定义对话框</th>
						<td colspan="3">
							<div id="ckselfdlg" name="ckselfdlg" class="mini-checkbox"
								value="false" readOnly="false" text="是"
								onvaluechanged="onSelfDlgChanged"></div>
							<span id="bindDlgDiv" style="display: none">
								绑定的对话框&nbsp; 
								<input class="mini-buttonedit" id="dialogalias" textName="dialogname" allowInput="false" name="dialogalias" onbuttonclick="selectDialog" style="width: 60%" />
									
								<div id="gridInput" class="mini-datagrid"  showPager="false"
								 allowCellEdit="true" allowCellSelect="true" allowSortColumn="false" 
								 oncellbeginedit="gridInputBeginEdit" oncellcommitedit="gridInputCellCommitEdit">
								    <div property="columns">
								        <div field="fieldName" displayfield="fieldText"  headerAlign="center" allowSort="true">查询条件
								        </div>    
								        <div field="mode" displayfield="modeName"  headerAlign="center" allowSort="true">方式
								        	 <input property="editor" class="mini-combobox" style="width:100%;"  data="[{id:'mapping',text:'映射'}, {id:'script', text: '脚本'}]"/>       
								        </div>
								        <div field="bindVal"   headerAlign="center" allowSort="true">绑定值
								        </div>    
								    </div>
								</div> 
							</span>
						</td>
					</tr>
					<tr>
						<th>绑定返回文本</th>
						<td><input class="mini-combobox" textField="field"
							valueField="field" name="textfield" id="textField"
							allowInput="true" /></td>
						<th>绑定返回值</th>
						<td><input class="mini-combobox" textField="field"
							valueField="field" name="valuefield" id="valueField"
							allowInput="true" /></td>
					</tr>
					<tr>
						<th>返回值绑定</th>
						<td colspan="3" style="padding: 5px;">
							<div class="mini-toolbar">
								<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow">删除</a>
							</div>
							<div id="returnFields" class="mini-datagrid" style="width:100%;minHeight:120px" height="auto" showPager="false" 
								allowCellEdit="true" allowCellSelect="true" multiSelect="true" oncellbeginedit="gridBtnReturnBeginEdit" allowSortColumn="false">
							    <div property="columns">
							        
							        <div type="checkcolumn" width="20"></div>
							        <div field="field" displayfield="header"  headerAlign="center" >返回字段</div> 
							        <div field="bindField" displayField="bindFieldName" width="120" headerAlign="center">绑定字段
							         	<input property="editor"  class="mini-combobox"  valueField="name" textField="comment" showNullItem="true" nullItemText="请选择..." />
							        </div>
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<th>按钮事件方法</th>
						<td><input id="onbuttonclick" name="onbuttonclick" class="mini-textbox" /></td>
						<th>默认值</th>
						<td><input class="mini-textbox" name="value"
							style="width: 90%" /></td>
					</tr>
					
				</table>
			</form>
	</div>
	<div style="display:none;">
		<textarea id="inputScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
		<textarea id="rtnScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
		<input id="inputColumnMapEditor" class="mini-combobox"  valueField="name" textField="comment" />
	</div>
	<script type="text/javascript">
		
	    var isMain = true;
	    var gridName = "main";
	
		
		
		mini.parse();
		var form=new mini.Form('miniForm');
		
		function initDialog(formData){
			
			 //设置其返回的字段绑定
	        mini.get('dialogalias').setText(formData.dialogname);
	        var gridRtn=mini.get("returnFields");
	        var gridInput=mini.get("gridInput");
	        var dataOptions=mini.decode(formData['data-options']);
	        var binding=dataOptions.binding;
	        if(!binding) return;
	        
	        if(binding.returnFields){
	        	gridRtn.setData(binding.returnFields);
	        }
	        
	        if(binding.gridInput){
	        	gridInput.setData(binding.gridInput);
	        }
	        
		}
		
		
		function getDialogBind(formData){
			var fields=mini.get('returnFields').getData();
			var inputJson=mini.get('gridInput').getData();
			var callback=mini.get('onbuttonclick').getValue();
			
			cleanJson(fields);
			tidyJson(inputJson);
			var json = {};
			json.dialogalias = formData.dialogalias;
			json.dialogname = formData.dialogname;
			json.returnFields = fields;
			json.gridInput=inputJson;
			json.textField=formData.textfield;
			json.valueField=formData.valuefield;
			
			
			json.isMain = isMain;
			json.gridName = gridName;
			json.callback=callback;
			
			return json;
		}
		
		function cleanJson(rows){
			for(var k=0;k<rows.length;k++){
				var row=rows[k];
				delete row.isUpdate;
				delete row.pagesize;
				delete row._id;
				delete row._uid;
				delete row._state;
				delete row.dataType;
				delete row.isReturn;
				delete row.queryDataType;
				delete row.width;
			}
		}
		
		function tidyJson(jsonAry){
			for(var k=0;k<jsonAry.length;k++){
				var row=jsonAry[k];
				delete row.isUpdate;
				delete row.pagesize;
				delete row._id;
				delete row._uid;
				delete row._state;
			}
		}
		
		
		
		function addRow(){
			grid.addRow({});
		}
		
		function removeRow(){
			var selecteds=grid.getSelecteds();
			grid.removeRows(selecteds);
		}
		
		function selectDialog(){
			_OpenWindow({
				url:'${ctxPath}/sys/core/sysBoList/dialog.do',
				height:450,
				width:800,
				title:'选择对话框',
				ondestroy:function(action){
					if(action!='ok') return;
					var win=this.getIFrameEl().contentWindow;
					var rs=win.getData();
					
					if(!rs) return;
					
					var dialogObj=mini.get('dialogalias');
					
					dialogObj.setValue(rs.key);
					dialogObj.setText(rs.name);
					
					//返回值设定
					var rtnAry=getReturnList(rs);
					
					
					if(rtnAry.length==0) {
						alert("请设置对话框的返回列!");
						return;
					}
				 
					mini.get('returnFields').setData(rtnAry);
					mini.get('valueField').setData(rtnAry);
					mini.get('textField').setData(rtnAry);
					//绑定参数
					var params=getDialogParams(rs.searchJson);
					mini.get("gridInput").setData(params)
				}
			});
		}
		
		function gridBtnReturnBeginEdit(e){
			var editor=e.editor;
			var fields=metaJson[gridName].fields;
			editor.setData(fields);
		}
		
		function gridInputBeginEdit(e){
			var field=e.field;
			var record=e.record;
			//当点击绑定值字段时处理。
			if(field=='bindVal'){
				var orginEditor=e.editor;
				if(record.mode=="mapping"){
					var editor=mini.get("inputColumnMapEditor");
					editor.grid=e.sender;
					//主表情况
					if(isMain){
						editor.setData(metaJson["main"].fields);
					}
					//子表情况
					else{
						editor.setData(metaJson[gridName].fields);
					}
					e.editor=editor;
					e.column.editor=e.editor;
				}
				else{
					var editor=mini.get("inputScriptEditor");
					editor.grid=e.sender;
					e.editor=editor;
					e.column.editor=e.editor;
				}
			}
		}
			
	</script>
</body>
</html>
