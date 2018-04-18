
<%-- 
    Document   : [单据表单方案]编辑页
    Created on : 2017-05-16 10:25:38
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<!DOCTYPE html>
<html>
<head>
<title>[单据表单方案]编辑</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/sys/bo/BoUtil.js" type="text/javascript"></script>

<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>

<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/powertips/jquery.powertip-blue.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/powertips/jquery.powertip.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
<script type="text/javascript">
	//设置左分隔符为 <!
	baidu.template.LEFT_DELIMITER='<!';
	//设置右分隔符为 <!  
	baidu.template.RIGHT_DELIMITER='!>';
</script>
<style  type="text/css">
body{
	background: #fff;
}
.form-table th{
	border:1px solid #ececec;
} 
.form-table td{
	border:1px solid #ececec;
}
.mini-tabs-bodys{
	background: #f7f7f7;
}
.shadowBox{
	margin-top:40px; 
}

.mini-grid-row-alt{
    background:#f7f7f7;
}

.CodeMirror-scroll{
	color: #666;
}

CodeMirror-line{}
#divHelp{
		position: absolute;
}
.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
	border-bottom: none;
}

</style>
<script type="text/javascript">
var buttonDef='${sysCustomFormSetting.buttonDef}';

function selectBo(e){
	var btn=e.sender;
	openBoDefDialog("db",function(action,bo){
	
		btn.setText(bo.name);
		btn.setValue(bo.id);
		//加载字段。
		loadFields();
		boDefId=bo.id;
		getTableRightJsonAndVars(boDefId);
	});
}

function getTableRightJsonAndVars(boDefId){
	$.ajax({
		type:'post',
		data:{boDefId:boDefId},
		url:"${ctxPath}/sys/customform/sysCustomFormSetting/getContextVarsAndTableJson.do",
		success:function(result){
			tableRightJson=mini.decode(result.tableRightJson);
			contextVars=mini.decode(result.contextVars);
			getRightHtml();
		}
	});
}

/**
 * 获取脚本。
 */
function handleFormData(data){
	for(var key in editorJson){
		data[key]=editorJson[key].getValue();
	}
	var buttonGrid=mini.get("buttonGrid");
	var buttons= buttonGrid.getData();
	if(buttons.length>0){
		data.buttonDef=mini.encode(buttons);
	}
	
	return true;
}

function selectForm(e){
	var btn=e.sender;
	var bodefId=mini.get("bodefId").getValue();
	if(!bodefId){
		alert("还没有设置BO,请先设置BO定义!");
		return false;
	}
	
	openBpmFormViewDialog({
		single:true,
		bodefId:bodefId,
		callBack:function(formView){
			if(!formView){
				btn.setText("");
				btn.setValue("");
			}else{
				var name=formView[0].name;
				var key=formView[0].key;
				btn.setText(name);
				btn.setValue(key);				
			}
		}
	});
}

function setMobileRightForm(e){
	var sender=e.sender;
	var bodefId=mini.get("bodefId").getValue();
	
	if(!bodefId){
		alert("还没有设置BO,请先设置BO定义!");
		return false;
	}

	openBpmMobileFormDialog({bodefId:bodefId,single:"true",callBack:function(formView){
		sender.setValue(formView.alias);
		sender.setText(formView.name);
	}})
}

function clearBtn(e){
	var btn=e.sender;
	btn.setText('');
	btn.setValue('');
}

function selectSolution(e){
	openBpmSolutionDialog(true,function(data){
		var obj=data[0];
		if(!obj) return;
		var solObj=e.sender;
		solObj.setValue(obj.solId);
		solObj.setText(obj.name);
	})
}

var editorJson={};

function initCodeMirror(){
	$(".editor").each(function(i){
		var editorName=$(this).attr("name");
		var editor= CodeMirror.fromTextArea(this, {
	        matchBrackets: true,
	        mode: "text/x-groovy"
	      });
		editorJson[editorName]=editor;
	});
	$(".CodeMirror").each(function(i){
		$(this).height(100);
	})
	
}

var selectEditor;

function activeScriptTab(){
	var tabs=mini.get("scriptTabs");
	var tab = tabs.getActiveTab();
	if(tab.name!="script") return;
	for(var key in editorJson){
   	 	var editor=editorJson[key];
   	 	editor.on("focus",function(e){
   	 		selectEditor=e;
   	 	});
       	var doc = editor.getDoc();
       	var script = editor.getValue();
   		if(!script) script=" ";
        doc.setValue(script);
   	 }
}

function insertContent(text){
	var doc = selectEditor.getDoc();
	var cursor = doc.getCursor(); // gets the line number in the cursor position
	doc.replaceRange(text, cursor); // adds a new line
}

function onTreeChange(e){
	var obj=e.sender;
	var val=obj.getChecked();
	
	if(val){
		$("#treeRow").css('display','');
	}else{
		$("#treeRow").css('display','none');
	}
	
	if(!val) return;
	
	
	
	var bodefId=mini.get("bodefId");
	if(!bodefId.getValue()) {
		alert("请先选择业务对象!");
		return;
	}
	loadFields();
}

function loadFields(){
	var treeObj=mini.get("isTree");
	var val=treeObj.getChecked();
	var bodefObj=mini.get("bodefId");
	var bodefId=bodefObj.getValue();

	if(!bodefId) return;
	
	if(val) {
		var objFields=mini.get("displayFields");
		var val=objFields.getValue();
		var url=__rootPath +"/sys/bo/sysBoEnt/getTreeByBoDefId.do?boDefId=" + bodefId +"&needSub=0";
		objFields.load(url);
		objFields.setValue(val);
	}
	
	var url=__rootPath +"/sys/bo/sysBoEnt/getTreeByBoDefId.do?boDefId=" + bodefId +"&needSub=1";
	var fieldsTree=mini.get("fieldsTree");
	fieldsTree.load(url);
}


$(function(){
	mini.parse();
	//初始化codemirror
	initCodeMirror();
	//加载选中字段
	loadFields();
	//加载按钮
	loadButtonDef();
	
	initHelp();
	
});

function loadButtonDef(){
	buttonDef=buttonDef || '[]';
	
	var grid=mini.get("buttonGrid");
	grid.setData(mini.decode(buttonDef));
}

function addRow(){
	var grid=mini.get("buttonGrid");
	grid.addRow({});
}

//上移
function _upGridRow(){
	var grid=mini.get("buttonGrid");
	var row = grid.getSelected();
    if (row) {
        var index = grid.indexOf(row);
        grid.moveRow(row, index - 1);
    }
} 
//下移
function _downGridRow(){
	var grid=mini.get("buttonGrid");
	var row = grid.getSelected();
    if (row) {
        var index = grid.indexOf(row);
        grid.moveRow(row, index + 2);
    }
}

function _delGridRow(){
	var grid=mini.get("buttonGrid");
	var selecteds=grid.getSelecteds();
	grid.removeRows(selecteds);
}

function initHelp(){
	 $('#divHelp').data('powertip', $("#formulaHelp").html());
	 $('#divHelp').powerTip({
	     placement: 'se',
	     mouseOnToPopup: true
	 });
}

function showJson(){
	var bodefId=mini.get("bodefId");
	var boId=bodefId.getValue();
	if(!boId){
		alert("请先选择业务模型!");
		return;
	}
	viewJson(boId);
}

function selectField(e){
	var node=e.node;
	insertContent(node.id);
}

</script>
</head>
<body>
<script id="rightTemplate"  type="text/html">
		<table class="form-table" >
			<tr>
				<th>子表名</th>
				<th>权限</th>
			</tr>
		<!for(var key in data){
			var json=data[key];
		!>
			<tr class="trName" tbName="<!=key!>">
				<td class="tbName"><!=key!>(<!=json.comment!>)</td>
				<td class="tbRight">
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="user" <!if(json.type=='user'){!>checked='checked'<!}!> />当前用户
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="group" <!if(json.type=='group'){!>checked='checked'<!}!>/>当前用户组
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="sql" <!if(json.type=='sql'){!>checked='checked'<!}!>/>SQL
					<input type="radio" name="<!=key!>" onclick="handType(this)" value="all" <!if(json.type=='all'){!>checked='checked'<!}!>/>全部
					<div class="sql" <!if(json.type!='sql'){!>style="display:none;"<!}!>>
						<div>常量:<select onchange='changeConstant(this)' style="width:200px;">
								<!for(var i=0;i<vars.length;i++){
									var obj=vars[i];
								!>
									<option value="<!=obj.key!>"><!=obj.val!></option>
								<!}!>
							</select>  字段选择:<input id="fieldTree" onvaluechanged="fieldSelect" class="mini-treeselect" url="<!=ctx!>/sys/bo/sysBoEnt/getTreeByBoDefId.do?boDefId=<!=boDefId!>" 
          expandOnLoad="true"   popupWidth="200"  parentField="pid" textField="text" valueField="id" />
							通用字段:<input id="commonTree" onvaluechanged="fieldSelect" class="mini-treeselect" url="<!=ctx!>/sys/bo/sysBoEnt/getCommonField.do" 
          expandOnLoad="true"   popupWidth="200"  parentField="pid" textField="text" valueField="id" />
</div>
						<textarea tbName="<!=key!>" style="width:600px;height:100px">
						 <!if(json.sql){!><!=json.sql!><!}else{!>select * from w_<!=key!> where REF_ID_=#{fk}  <!}!>
						</textarea>
					<div>
				</td>
			</tr>
		<!}!>
		</table>
	</script>

	<div id="toolbar1" class="mini-toolbar " >
		<a class="mini-button" plain="true" iconCls="icon-save" onclick="onSave('form1')">保存</a>	
	</div>

	<div id="p1" class=" mini-fit form-outer">
		<form id="form1" method="post" action="/sys/customform/sysCustomFormSetting/save.do" style="width:100%;height:100%;">
			<div id="scriptTabs" class="mini-tabs" activeIndex="0"   style="width:100%;height:100%;" onactivechanged="activeScriptTab">
			    <div title="基本信息">
					<div class="shadowBox">
				    	<input id="pkId" name="id" class="mini-hidden" value="${sysCustomFormSetting.id}" />
				        <table class="table-detail column_2" cellspacing="1" cellpadding="0">
							<caption>[单据表单方案]基本信息</caption>
							<tr>
								<th>名　　称</th>
								<td >
									<input id="name" name="name" value="${sysCustomFormSetting.name}" required="true"
									class="mini-textbox"   style="width: 90%" />
								</td>
								<th>别　　名</th>
								<td>
									<input id="alias" name="alias" value="${sysCustomFormSetting.alias}" required="true"
									class="mini-textbox"   style="width: 90%" />
								</td>
							</tr>
							<tr>
								<th>方案分类</th>
								<td>
									 <input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listAllByCatKey.do?catKey=CAT_FORMSOLUTION" 
											 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId" required="true" text="${sysTree.name}" value="${sysTree.treeId}"
										        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree"  style="width: 90%"/>
								</td>
								<th>业务对象</th>
								<td>
									<input id="bodefId" name="bodefId" textName="bodefName" showClose="true" text="${sysCustomFormSetting.bodefName}" value="${sysCustomFormSetting.bodefId}"
									class="mini-buttonedit"  allowInput="false" style="width: 90%" required="true" onbuttonclick="selectBo" oncloseclick="clearBtn" />
								</td>
							</tr>
							<tr>
								<th>手机表单</th>
								<td>
									<input name="mobileFormAlias" textName="mobileFormName" showClose="true" text="${sysCustomFormSetting.mobileFormName}"  value="${sysCustomFormSetting.mobileFormAlias}"
									class="mini-buttonedit"  allowInput="false" style="width: 90%" onbuttonclick="setMobileRightForm" oncloseclick="clearBtn"/>
								</td>
								<th>表  单  名</th>
								<td>
									<input name="formAlias" textName="formName" required="true" showClose="true" text="${sysCustomFormSetting.formName}"  value="${sysCustomFormSetting.formAlias}"
									class="mini-buttonedit" allowInput="false"  style="width: 90%" onbuttonclick="selectForm" oncloseclick="clearBtn"/>
								</td>
								
							</tr>
							<tr>
								<th>流程方案</th>
								<td >
									<input name="solId" textName="solName" showClose="true" text="${sysCustomFormSetting.solName}"  value="${sysCustomFormSetting.solId}"
									class="mini-buttonedit"  allowInput="false" style="width: 90%" onbuttonclick="selectSolution" oncloseclick="clearBtn"/>
								</td>
								<th>树形表单</th>
								<td >
									<input id="isTree" name="isTree" value="${sysCustomFormSetting.isTree}"
									class="mini-checkbox" trueValue="1" falseValue="0"  onvaluechanged="onTreeChange" />
								</td>
							</tr>
							<tr id="treeRow" <c:if test="${sysCustomFormSetting.isTree!='1'}"> style="display:none"</c:if>>
								<th>加载方式</th>
								<td >
									<input id="loadMode" name="loadMode" class="mini-combobox" style="width:150px;" value="${sysCustomFormSetting.loadMode}" textField="text" valueField="id" emptyText="请选择..."
	    								data="[{id:0,text:'一次性加载'},{id:1,text:'懒加载'}]"   />  
								</td>
								<th>显示字段</th>
								<td>
									<input id="displayFields" name="displayFields"   class="mini-treeselect" value="${sysCustomFormSetting.displayFields}" valueField="id" textField="text" style="width:300px;"/>
								</td>
							</tr>
							<tr>
								<th>数据处理器</th>
								<td colspan="3">
									<input id="dataHandler" name="dataHandler" value="${sysCustomFormSetting.dataHandler}" 
									class="mini-textbox"   style="width: 40%" />
									处理器需要实现ICustomFormDataHandler接口，此处填写接口实现类的spring beanId。 
								</td>							
							</tr>
							<tr>
				              	<th>子表权限</th>
				              	<td colspan="3">
				              	
				              		<input id="tableRightJson" name="tableRightJson" class="mini-hidden"  />
				              		<span id="spanTableRightJson"></span>
				              	</td>
				             </tr>
							
							<tr>
								<th>自定义按钮配置</th>
								<td colspan="3" style="padding:0 !important;">
									<div class="mini-toolbar" style="padding-bottom:4px;border-bottom:none;">
			    						<a class="mini-button" name="addbutton" iconCls="icon-add" plain="true" onclick="addRow">添加</a>
			    						<a class="mini-button" name="delbutton" iconCls="icon-remove" plain="true" onclick="_delGridRow()">删除</a>
										<span class="separator"></span>
										<a class="mini-button" iconCls="icon-up" name="upbutton" plain="true" onclick="_upGridRow()">上移</a>
										<a class="mini-button" iconCls="icon-down" name="downbutton" plain="true" onclick="_downGridRow()">下移</a>
									</div>
								
									<div 
										id="buttonGrid" 
										class="mini-datagrid" 
										style="width:100%;"
										allowResize="false" 
										showPager="false"
									 	allowAlternating="true" 
									 	allowCellWrap="true" 
									 	multiSelect="true" 
										allowCellEdit="true" 
										allowCellSelect="true"
									>
										<div property="columns">
											<div field="name" width="100" headerAlign="center" >按钮名
												<input property="editor" class="mini-textbox" />
											</div>
											<div field="icon" width="80" headerAlign="center" >图标
												<input property="editor" class="mini-textbox" />
											</div>
											<div field="method" width="180" headerAlign="center" >事件方法名
												<input property="editor" class="mini-textbox" />
											</div>
										</div>									
									</div>
								</td>
							</tr>
							
							
						</table>
				    
					</div>
			    </div>
			    <div title="脚本配置" name="script" >
			    	<div class="shadowBox">
			    		<div  class="mini-toolbar mini-toolbar-bottom" >
						    <table style="width:100%;">
						        <tr>
						            <th style="width:100%;">
						            	<a class="mini-button" iconCls="icon-dataStructure" plain="true" onclick="showJson">表单数据数据结构</a>
						            	<input id="fieldsTree" class="mini-treeselect" onnodeclick="selectField"   valueField="id" textField="text" style="width:300px"/>
						            	<div  class="iconfont icon-Help-configure" style="display: inline-block;" id="divHelp"  title="帮助" ></div>
						            </th>
						        </tr>
						    </table>
						</div>
				    	<table class="table-detail column_2" cellspacing="1" cellpadding="0">
							<tr>
								<th width="15%">前置JS脚本</th>
								<td width="85%">
									<textarea name="preJsScript" class="editor">${sysCustomFormSetting.preJsScript}</textarea>
								</td>
							</tr>
							<tr>
								<th>后置JS脚本</th>
								<td>
									<textarea name="afterJsScript" class="editor">${sysCustomFormSetting.afterJsScript}</textarea>
								</td>
							</tr>
							<tr>
								<th>
									前置JAVA脚本:<br>
									
								</th>
								<td>
									<textarea name="preJavaScript" class="editor">${sysCustomFormSetting.preJavaScript}</textarea>
									<br/>
									<div>
										数据JSON为data，使用方法:主表字段取值为data.字段名，如data.name,子表字段则为data.SUB_子表名
									</div>
								</td>
							</tr>
							<tr>
								<th>后置JAVA脚本</th>
								<td>
									<textarea name="afterJavaScript" class="editor">${sysCustomFormSetting.afterJavaScript}</textarea>
								</td>
							</tr>
						</table>
			    	</div>
			    </div>
			</div>
		</form>
	</div>
	
	<div style="display:none" id="formulaHelp">
<pre>
	1.表单数据结构为一个JSON对象，数据结构可以点击"表单数据数据结构"按钮查看。
	2.前置JS脚本。
		可以返回true或false。如果返回false则表单不提交。
	3.后置JS脚本，则在表单保存成功后可以执行的脚本。
	4.前置JAVA脚本。
		在数据保存到数据库之前可以对表单数据进行干预，或者做其他的操作，如果数据有问题可以抛出异常将数据回滚。
		data在脚本中可以直接在脚本中使用，data为一个JSONOBJECT对象。
	5.后置JAVA脚本。
		在脚本上下文中除了data对象，还增加了一个result_对象，这个对象类型为BoResult。
</pre>
	</div>
	<%@include file="/WEB-INF/jsp/sys/bo/incJsonView.jsp" %>
<script type="text/javascript">
var tableRightJson=<c:choose><c:when test="${empty tableRightJson}">{}</c:when><c:otherwise>${tableRightJson}</c:otherwise></c:choose>;
var contextVars=mini.decode( '${contextVars}');
var boDefId='${sysCustomFormSetting.bodefId}';
mini.parse();
var treeId=mini.get("treeId");

//编辑器存储。
var editorJson={};

function getRightHtml(){
	if(!tableRightJson) return "";
	var bt=baidu.template;
	var data={data:tableRightJson,vars:contextVars,boDefId:boDefId,ctx:__rootPath};
	var html=bt("rightTemplate",data);
	var parent=$("#spanTableRightJson");
	
	parent.html(html);
	
	$("textarea",parent).each(function(i){
		var tabName=$(this).attr("tbName");
		var editor= CodeMirror.fromTextArea(this, {
	        matchBrackets: true,
	        mode: "text/x-sql"
	      });
		editorJson[tabName]=editor;
	});
	$(".CodeMirror",parent).each(function(i){
		$(this).height(150);
	})

	mini.parse();
}
//加载权限HTML
getRightHtml();

function getRightJson(){
	var spanObj=$("#spanTableRightJson");
	var json={};
	$(".trName",spanObj).each(function(i){
		var tr=$(this);
		var tableName=tr.attr("tbName");
		var tbRight=$(".tbRight",tr);
		var val = $('input:radio:checked',tbRight).val();
		var obj={type:val};
		if(val=="sql"){
			var editor=editorJson[tableName];
			obj.sql=editor.getValue();
		}
		json[tableName]=obj;
	});
	return mini.encode (json );
}

function selectTreeId(){
	_OpenWindow({
		title:"选择分类",
		url:__rootPath+'/sys/customform/sysCustomFormSetting/treeDialog.do',
		max:false,
		height:600,
		width:400,
		ondestroy:function (action){
			if(action=='ok'){
				var iframe = this.getIFrameEl().contentWindow;
				var node=iframe.GetData();
				console.log(node.treeId);
				treeId.setValue(node.treeId);
				treeId.setText(node.name);
			}
		}
	});

}




function onSave(formId){
	//若有自定义函数，则调用页面本身的自定义函数
	if(isExitsFunction('selfSaveData')){
		selfSaveData.call();
		return;
	}
	var form = new mini.Form("#"+formId); 
    form.validate();
    if (!form.isValid())  return;
    
    var formData=_GetFormJsonMini(formId);
    var url=__rootPath + $("#" + formId).attr("action");
  
   
   //若定义了handleFormData函数，需要先调用 
   if(isExitsFunction('handleFormData')){
    	var result=handleFormData(formData);
    	if(!result) return;
    }
   var tabRightJson=getRightJson();
	
   formData.tableRightJson=tabRightJson;
    
    var config={
    	url:url,
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
    
    config.postJson=true;
    
    _SubmitJson(config);
}

function handType(obj){
	var jqObj=$(obj);
	var parent=$(obj).closest("td");
	var divSql=$(".sql",parent);
	var type=jqObj.val();
	if(type=="sql"){
		divSql.show();
	}
	else{
		divSql.hide();
	}
}
</script>

</body>
</html>