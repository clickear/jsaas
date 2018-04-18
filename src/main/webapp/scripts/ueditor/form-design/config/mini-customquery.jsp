<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html >
<html>
<head>
<title>自定查询配置</title>
<%@include file="/commons/mini.jsp"%>
<style type="text/css">
	fieldset{height: 100%;border: 1px solid silver;}
	fieldset legend{font-weight: bold;}
	.droppable { width: 150px; cursor:pointer; height: 30px; padding: 0.5em;  margin: 5px;border: 1px dashed black; }
	.droppable.highlight{ background: orange;}
</style>
</head>
<body>
<script type="text/javascript">
var __rootPath="${ctxPath}";

/**
 * 是否主表中。
 */
var isMain=true;
//表单元数据。
var metaJson={};

var curEditEl=editor.editdom;

window.onload = function() {
	bindTable();
	bindCustomQuery();
	
	bindEvent();
	//初始化自定义查询。
	initCustomQuery();
}

function initCustomQuery(){
	var curEl=$(curEditEl);
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	
	var customQuery=json.customquery;
	if(!customQuery) return;
	
	var gridSetting=mini.get("gridSetting");
	gridSetting.setData(customQuery);
	
	var row=customQuery[0];
	setFormData(row);
}

function bindTable(){
	metaJson=getMetaData(editor,curEditEl);
	isMain=metaJson.isMain;
	
	var tableObj=mini.get("table");
	var tables=[];
	for(var key in metaJson){
		var o={};
		o.id=key;
		o.text=metaJson[key].comment;
		tables.push(o);
	}
	tableObj.setData(tables);
	if(!isMain){
		tableObj.setValue(tables[0].id);
	}
}

function bindEvent(){
	var data=isMain?[{id:'addinit',text:'添加'},{id:'detailinit',text:'明细'},{id:'editinit',text:'编辑'},{id:'enter',text:'回车'},{id:'valuechanged',text:'值改变'}]:[{id:'valuechanged',text:'值改变'}];
	var objEvent=mini.get("event");
	objEvent.setData(data);
	if(!isMain){
		objEvent.setValue("valuechanged");
	}
}

/**
 * {
		key:value
	}
	key: 自定义查询别名
	value : 自定查询对象定义 
 */
var queryMap={};
/**
 * [{id:"",text:""}]
 */
var queryNameMap=[];

function bindCustomQuery(){
	var url=__rootPath +"/sys/db/sysSqlCustomQuery/getList.do";
	$.get(url,function(data){
		for(var i=0;i<data.length;i++){
			var obj=data[i];
			var key=obj.key;
			queryMap[key]=obj;
			var nameObj={};
			nameObj.id=key;
			nameObj.text=obj.name;
			queryNameMap.push(nameObj);
		}
		var queryObj=mini.get("customquery");
		//console.info(queryNameMap);
		queryObj.setData(queryNameMap);
	})
}


dialog.onok = function (){
	var curEl=$(curEditEl);
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	
	var gridSetting=mini.get("gridSetting");
	var rows=gridSetting.getData();
	cleanJson(rows);
	json.customquery=rows;
	
	curEl.attr("data-options",mini.encode(json)) ;
}

function cleanJson(rows){
	for(var k=0;k<rows.length;k++){
		var row=rows[k];
		delete row.isUpdate;
		delete row.pagesize;
		delete row._id;
		delete row._uid;
		delete row._state;
		
		var inputAry=row.gridInput;
		for(var i=0;i<inputAry.length;i++){
			var o=inputAry[i];
			delete o._id;
			delete o._uid;
			delete o._state;
		}
		
		var rtnAry=row.gridReturn;
		for(var i=0;i<rtnAry.length;i++){
			var o=rtnAry[i];
			delete o._id;
			delete o._uid;
			delete o._state;
		}
	}
}

dialog.oncancel = function (){
	
}

/**
 * 改变自定义查询时处理。
 */
function changeCustomquery(e){
	var ctl=e.sender;
	var key=ctl.getValue();
	
	var obj=queryMap[key];
	var conditions=[];
	if(obj.whereField){
		conditions=mini.decode(obj.whereField);
	}
	var resultFields=mini.decode(obj.resultField);
	
	var gridInput=mini.get("gridInput");
	var gridReturn=mini.get("gridReturn");
	
	//绑定条件表格。
	var aryContition=[];
	for(var i=0;i<conditions.length;i++){
		var condition=conditions[i];
		if(condition.valueSource!='param') continue; 
		var o={name:condition.fieldName,comment:condition.comment};
		aryContition.push(o);
	}
	gridInput.setData(aryContition);
	//绑定返回表格。
	var aryReturn=[];
	for(var i=0;i<resultFields.length;i++){
		var field=resultFields[i];
		var o={name:field.fieldName,comment:field.comment};
		aryReturn.push(o);
	}
	gridReturn.setData(aryReturn);
	
}



//绑定模式发生变化时
function gridInputCellCommitEdit(e){
	var grid=e.sender;
	if(e.field=="mode" && e.oldValue && e.oldValue!=e.value){
		grid.updateRow(e.record,{bindVal:""});
	}
}

function gridReturnBeginEdit(e){
	var editor=e.editor;
	var tableObj=mini.get("table");
	var tablename=tableObj.getValue();
	var fields=metaJson[tablename].fields;
	editor.setData(fields);
}

function getFormData(){
	var form = new mini.Form("#formSetting"); 
	var frm=$("#formSetting");
	var data=form.getData();
	data.isMain=isMain;
	
	$('.mini-datagrid',frm).each(function(){
		var name=$(this).attr('id');
		var grid=mini.get(name);
		data[name]=grid.getData();
	});
	return data;
}

/**
 * 添加设定。
 */
function saveSetting(){
	var form = new mini.Form("#formSetting"); 
	form.validate();
	if(!form.isValid()) return;
	
	
	var data = getFormData(); 
	var gridSetting=mini.get("gridSetting");
	var rows=gridSetting.getData();
	//是否存在。
	var row=getRow(data,rows);
	if(row) {
		if(data.isUpdate=="1"){
			gridSetting.updateRow ( row, data );
		}
	}
	else{
		gridSetting.addRow(data);
	}
	
	clearData();
}

function clearData(){
	var objName=mini.get("name");
	var objUpate=mini.get("isUpdate");
	objName.setValue("");
	objUpate.setValue("0");
}

function getRow(data,settingAry){
	var name=data.name;
	for(var i=0;i<settingAry.length;i++){
		var o=settingAry[i];
		if(o.name==name) return o;
	}
	return null;
}

function rowClick(e){
	var row=e.record;
	row.isUpdate="1";
	setFormData(row);
}

function setFormData(data){
	var form = new mini.Form("#formSetting"); 
	var frm=$("#formSetting");
	form.setData(data);
	
	$('.mini-datagrid',frm).each(function(){
		var name=$(this).attr('id');
		var grid=mini.get(name);
		grid.setData(data[name]);
	});
}



</script>

<div style="display:none;">
	<textarea id="inputScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
	<textarea id="rtnScriptEditor"  class="mini-textarea" emptyText="请输入脚本"></textarea>
	<input id="inputColumnMapEditor" class="mini-combobox" showNullItem="true"  valueField="name" textField="comment" />
</div>

<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		<div title="配置列表" region="east" width="200" >
			<div id="gridSetting" class="mini-datagrid" style="width:100%;height:100%;" showPager="false" 
			    onrowclick="rowClick" 
			    >
				    <div property="columns">
				        <div field="name"   headerAlign="center" allowSort="true" width="100">名称</div>    
				        <div name="action" width="50" cellCls="actionIcons" renderer="onActionRenderer"  headerAlign="center" >#</div>
				    </div>
			</div>
		</div>
		<div title="自定义查询配置" region="center"  showHeader="false" >
			<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
			    <table style="width:100%;">
			        <tr>
			            <td style="width:100%;" id="toolbarBody">
			               	<a id="btnSave" class="mini-button" iconcls="icon-save" plain="true" onclick="saveSetting()">保存</a> 
			               	
			            </td>
			        </tr>
			    </table>
    		</div>
			
			<table id="formSetting" class="table-detail column_2_m" cellspacing="1" cellpadding="0" style="width:100%;">
				<tr>
					<th>名　称</th>
					<td >
						<input id="name" name="name" value="" required="true"
						class="mini-textbox"   />
						<input id="isUpdate" name="isUpdate" value="0"  class="mini-hidden"   />
					</td>
					<th>事　件</th>
					<td >
						<input id="event" name="event" class="mini-checkboxlist" repeatLayout="flow" repeatDirection="horizontal" repeatItems="3" textField="text" valueField="id" 
    						data="[{id:'addinit',text:'添加'},{id:'detailinit',text:'明细'},{id:'editinit',text:'编辑'},{id:'enter',text:'回车'},{id:'valuechanged',text:'值改变'}]" value="" showNullItem="true"/>
					</td>
					
				</tr>
				<tr>
					<th>自定义查询</th>
					<td colspan="3">
						<input id="customquery" name="customquery" class="mini-combobox"   textField="text" valueField="id" value="" showNullItem="true" onvaluechanged="changeCustomquery"/>
					</td>
					
				</tr>
				<tr>
					<th>输入条件</th>
					<td colspan="3" >
						<div id="gridInput" class="mini-datagrid"  showPager="false"
						 allowCellEdit="true" allowCellSelect="true" allowSortColumn="false"
						
							oncellbeginedit="gridInputCellBeginEdit" oncellcommitedit="gridInputCellCommitEdit">
						    <div property="columns">
						        <div field="name" displayfield="comment"  headerAlign="center" allowSort="true">查询条件
						        	
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
					<th>返回值绑定</th>
					<td colspan="3" >
						选择映射表
						<input id="table" name="table" class="mini-combobox"   textField="text" valueField="id" value="" />
						<div id="gridReturn" class="mini-datagrid"  showPager="false"  allowSortColumn="false"  
						allowCellEdit="true" allowCellSelect="true" oncellbeginedit="gridReturnBeginEdit" height="150">
						    <div property="columns">
						        <div field="name" displayfield="comment"  headerAlign="center" allowSort="true">返回字段</div>    
						        <div field="mapVal"   headerAlign="center" allowSort="true"  >映射值
						        	<input property="editor"  class="mini-combobox" showNullItem="true"  valueField="name" textField="comment"  />
						        </div>    
						    </div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
	</div>
<script type="text/javascript">
	mini.parse();
	
	//initBtn(true);

	function onActionRenderer(e){
		var record = e.record; 
		var uid=record._uid;
		var s = '<span class="icon-remove iconfont" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
    	return s;
	}
	
	function delRow(uid){
		var grid=mini.get("gridSetting");
		var row=grid.getRowByUID(uid);
		grid.removeRow(row);
	}

</script>
</body>
</html>