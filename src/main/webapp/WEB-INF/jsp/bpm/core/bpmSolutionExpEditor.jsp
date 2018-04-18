<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
	<title>公式设计器</title>
	<%@include file="/commons/edit.jsp"%>
	
	<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
	<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
	<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js"></script>
	<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
	
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn" >
	    <a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok')" plain="true">确定</a>
<!-- 		<a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow()" plain="true">关闭</a> -->
	</div>
	<br/>
	    <div class="form-outer shadowBox90">
	 		 	<ul id="varsMenu" class="mini-menu" style="display:none;">
	 		 		<c:forEach items="${instVars}" var="item">
	 		 			 <li iconCls="icon-var" onclick="addExp('${item.key}')">${item.name}</li>
	 		 		</c:forEach>
			    </ul>
		    	<table class="table-detail column_2" cellpadding="0" cellspacing="1">
			    	<tbody>
				    	<tr id="backTaskTr">
				    		<th>流程变量表达式</th>
				    		<td>
				    			<form id="fieldForm">
					    			<input 
					    				id="varField" 
					    				class="mini-combobox" 
					    				showNullItem="true" 
					    				nullItemText="请选择变量..."  
					    				emptyText="请选择变量..."
					    				url="${ctxPath}/bpm/core/bpmNodeSet/getVars.do?solId=${param['solId']}&actDefId=${param.actDefId}&nodeId=${param['nodeId']}"
					    				valueField="key" 
					    				textField="name" 
					    				name="field" 
					    				onvaluechanged="changeVarField" 
					    				required="true"
				    				/>
					    			<input 
					    				id="varOp" 
					    				class="mini-combobox" 
					    				name="varOp" 
					    				nullItemText="请选择比较符..." 
					    				showNullItem="true" 
					    				emptyText="请选择比较符..." 
					    				required="true"
				    				/>
					    			<span id="valSpan">
					    			<input id="varVal" class="mini-textbox" name="varVal" emptyText="请输入值.." required="true" style="width:150px;"/>
					    			</span>
					    			<a class="mini-button" iconCls="icon-add" onclick="addFieldExp">添加</a>
				    			</form>
				    		</td>
				    	</tr>
				    	<tr >
				    		<th width="120">上一步审批动作</th>
				    		<td >
				    			<form id="nodeForm">
					    			<input 
					    				class="mini-combobox" 
					    				name="nodeCheck" 
					    				valueField="activityId" 
					    				textField="name" 
					    				nullItemText="请选择前一审批环节..." 
					    				required="true"
					    				showNullItem="true" 
					    				emptyText="请选择前一审批环节..."
					    				id="nodeCheck" 
					    				url="${ctxPath}/bpm/core/bpmSolution/getPreNodes.do?solId=${param['solId']}&actDefId=${param.actDefId}&nodeId=${param['nodeId']}"
				    				>
					    			
					    			<input 
					    				id="nodeOp" 
					    				class="mini-combobox" 
					    				name="nodeOp"  
					    				data="[{id:'==',text:'等于'},{id:'!=',text:'不等于'}]"
					    				nullItemText="请选择比较符..." 
					    				showNullItem="true" 
					    				emptyText="请选择比较符..." 
					    				required="true"
				    				/>
					    			
					    			<div 
					    				class="mini-combobox"  
					    				name="nodeJump" 
					    				id="nodeJump" 
					    				required="true"
									    textField="text" 
									    valueField="id" 
									    value="AGREE" 
									    nullItemText="请选择审批操作..." 
									    showNullItem="true" 
									    emptyText="请选择审批操作..."
									    data="[{id:'AGREE',text:'同意'},{id:'REFUSE',text:'不同意'},{id:'BACK',text:'驳回'},{id:'BACK_TO_STARTOR',text:'驳回至发起人'}]" 
								    ></div> 
									<a class="mini-button" iconCls="icon-add" onclick="addCheckExp">添加</a>
								</form>
				    			
				    		</td>
				    	</tr>
				    	<tr>
				    		<th width="120">函数表达式</th>
				    		<td>
				    			<a href="javascript:showScriptLibary()">插入函数脚本</a>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    			<a class="mini-button" plain="true" onclick="addExp('&&')">并且&&</a>
				    			<a class="mini-button" plain="true" onclick="addExp('||')">或||</a>
				    			<a class="mini-button" plain="true" onclick="addExp('()')">括号()</a>
				    			<a class="mini-menubutton"  plain="true"  menu="#varsMenu">插入变量</a>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2" style="padding:0 !important;">
				    			<textarea  id="formula" name="formula" style="width:90%;height:150px"></textarea>
				    		</td>
				    	</tr>
			    	</tbody>
		    	</table>
		   
		</div>
		    <script type="text/javascript">
		    
		    	mini.parse();
		    	function getFormula(){
		    		return editor.getValue()
		    	}
		    	
		    	function addCheckExp(){
		    		var form=new mini.Form('nodeForm');
		    		form.validate();
		    		if(!form.isValid()){
		    			return;
		    		}
		    		var nodeCheck=mini.get('nodeCheck').getValue();
		    		nodeCheck=nodeCheck.split('-').join('_');
		    		var nodeOp=mini.get('nodeOp').getValue();
		    		var nodeJump=mini.get('nodeJump').getValue();
		    		
		    		var str='check_'+nodeCheck+nodeOp+ '"' +nodeJump+'"';
		    		addExp(str);
		    	}
		    	
		    	//更改变量字段
		    	function changeVarField(e){
		    		var combo=e.sender;
		    		var sel=combo.getSelected();
		    		if(!sel){
		    			return;
		    		}
		    		var type=sel.type;
		    		var ops=[];
		    		var fieldVal=mini.get('opVal');
		    		var p= mini.get('opVal');
		    		if(p){
		    			p.destroy();
		    		}
		    		var valSpan=document.getElementById('valSpan');
		    		if(type=='String'){
		    			ops=[{id:'==',text:'等于'},{id:'!=',text:'不等于'}];
		            	var pl=new mini.TextBox();
		            	pl.setId('opVal');
		            	pl.setStyle('width:150px;');
		            	$(valSpan).html('');
		            	pl.render(valSpan);
		    		}else if(type=='Number'){
		    			ops=[{id:'==',text:'等于'},{id:'!=',text:'不等于'},{id:'<',text:'小于'},{id:'<=',text:'小于等于'},{id:'>',text:'大于'},{id:'>=',text:'大于等于'}];
		            	var pl=new mini.TextBox();
		            	pl.setId('varVal');
		            	pl.setRequired(true);
		            	pl.setStyle('width:150px;');
		            	pl.on('validation',onNumberValidation);
		            	$(valSpan).html('');
		            	pl.render(valSpan);	
		    		}else if(type=='Date'){
		    			ops=[{id:'==',text:'等于'},{id:'<',text:'小于'},{id:'>',text:'大于'}];
		            	var pl=new mini.DatePicker();
		            	pl.setId('varVal');
		            	pl.setRequired(true);
		            	pl.setStyle('width:150px;');
		            	pl.setFormat('yyyy-MM-dd');
		            	$(valSpan).html('');
		            	pl.render(valSpan);
		    		}
		    		mini.get('varOp').setData(ops);
		    	}
		    	
		    	var editor = CodeMirror.fromTextArea(document.getElementById("formula"), {
			        lineNumbers: true,
			        matchBrackets: true,
			        mode: "text/x-groovy"
			      });
		    	
		    	var fieldForm=new mini.Form('fieldForm');
		    	
		    	function addFieldExp(){
		    	
		    		fieldForm.validate();
		    		if(!fieldForm.isValid()){
		    			return;
		    		}
		    		var varField=mini.get('varField');
		    		var opField=mini.get('varOp');
		    		var opVal=mini.get('varVal');
		    		
		    		var varStr='';
		    		var type=varField.getSelected().type;
		    		if(type=='String'){
		    			varStr=varField.getValue()+ '' +opField.getValue()+'"'+opVal.getValue()+'"';
		    		}else if(type=='Number'){
		    			varStr=varField.getValue()+ '' +opField.getValue()+opVal.getValue();
		    		}else if(type=='Date'){
		    			if(opField.getValue()=='=='){
		    				varStr=varField.getValue() +'.compareTo(com.redxun.core.util.DateUtil.parseDate(\''+opVal.getFormValue()+'\'))==0';
		    			}else if(opField.getValue=='<'){
		    				varStr=varField.getValue() +'.compareTo(com.redxun.core.util.DateUtil.parseDate(\''+opVal.getFormValue()+'\'))<0';
		    			}else{
		    				varStr=varField.getValue() +'.compareTo(com.redxun.core.util.DateUtil.parseDate(\''+opVal.getFormValue()+'\'))>0';
		    			}
		    		}
		    		addExp(varStr);
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
								addExp(row.example);
							}
						}
					});
				}
		    	
		    	function addExp(scriptText){
		    		var doc = editor.getDoc();
	           		var cursor = doc.getCursor(); // gets the line number in the cursor position
	          		doc.replaceRange(scriptText, cursor); // adds a new line
		    	}
		    </script>
</body>
</html>