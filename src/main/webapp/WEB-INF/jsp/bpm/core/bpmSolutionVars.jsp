<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title> 业务流程解决方案管理-流程变量配置</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/list.jsp"%>
<style>
	.Tree .mini-panel-border{
		width: 100%;
	}
	
	#center{
		background: transparent;
	}
</style>
</head>
<body>
		
		<div id="orgLayout" class="mini-layout" style="width:100%;height:100%;" bodyStyle="border:none">
		   
		    <div 
		    	title="流程任务节点" 
		    	region="west" 
		    	width="220"  
		    	showSplitIcon="true" 
		    	showCollapseButton="false"
		    	showProxy="false"
		    	iconCls="" 
	    	>
		    	 	<div class="mini-fit">
			    	 	<ul 
			    	 		id="bpmTaskNodeTree" 
			    	 		class="mini-tree Tree" 
			    	 		url="${ctxPath}/bpm/core/bpmNodeSet/getTaskNodes.do?includeProcess=false&actDefId=${bpmDef.actDefId}" 
				         	style="width:100%;height: 100%;overflow: auto;"
							parentField="parentActivitiId" 
							expandOnLoad="true"
							showTreeIcon="true" 
							textField="name" 
							idField="activityId" 
							resultAsTree="false"  
			                onnodeclick="nodeClick"
		                ></ul>
	            	</div>
		    </div>        
		    <div 
		    	title="流程变量" 
		    	region="center" 
		    	showHeader="false" 
		    	showCollapseButton="false" 
		    	iconCls="" 
	    	> 	
	    		<div class="mini-toolbar topBtn">
		    		<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow()">添加</a>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeScopeVars()">删除</a>
					<a class="mini-button" iconCls="icon-formAdd" plain="true" onclick="showFormFieldDlg()">从表单中添加</a>
					<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveScopeVars()">保存</a>	
			  	</div>
		    	<div class="mini-fit rx-grid-fit form-outer5">
					<div 
						id="varGrid" 
						class="mini-datagrid" 
						allowAlternating="true"
	            		allowCellEdit="true" 
	            		allowCellSelect="true" 
	            		style="height:100%;width:100%;"
	            		oncellvalidation="onCellValidation" 
	            		idField="varId" 
	            		multiSelect="true"
	             		url="${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefIdNodeId.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}&nodeId=_PROCESS"
						idField="Id" 
						showPager="false" 
						style="width:100%;min-height:100px;"
					>
						<div property="columns">
							<div type="indexcolumn" width="30"></div>
							<div type="checkcolumn" width="30"></div>
							<div field="name" name="name" width="80" headerAlign="center">变量名
								<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
							</div>
							<div field="formField" name="formField" width="80" headerAlign="center">表单字段
								<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
							</div>
							<div field="key" name="key" width="80" headerAlign="center">变量Key
								<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
							</div>
							<div field="type" name="type" width="80" headerAlign="center">类型
								<input 
									property="editor" 
									class="mini-combobox" 
									data="[{id:'String',text:'字符串'},{id:'Number',text:'数字'},{id:'Date',text:'日期'}]" 
									style="width:100%" 
									required="true"
								/>
							</div>
							<div field="defVal" name="defVal" width="80" headerAlign="center" >缺省值
								<input property="editor" class="mini-textbox" style="width:100%;"/>
							</div>
							<div field="express" name="express" width="120" headerAlign="center" >计算表达式
								<input property="editor" class="mini-textbox" style="width:100%;"/>
							</div>
							<div type="checkboxcolumn" field="isReq" name="isReq" trueValue="YES" falseValue="NO" width="60" headerAlign="center">是否必填</div>
							<div field="sn" name="sn" width="50" headerAlign="center" >序号
								<input property="editor" class="mini-spinner" style="width:100%" minValue="1" maxValue="200"/>
							</div>
						</div>
					</div>
		    	</div>
		    
		    </div>
		</div>
		
		<script type="text/javascript">
			mini.parse();
			var grid=mini.get('varGrid');
			grid.load();
			var solId='${bpmSolution.solId}';
			var actDefId='${bpmDef.actDefId}';
			var tree=mini.get('bpmTaskNodeTree');

			function addRow(){				
				grid.addRow({});
			}
			
			function nodeClick(e){
				var nodeId=e.node.activityId;
				grid.setUrl('${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefIdNodeId.do?solId=${bpmSolution.solId}&nodeId='+nodeId +'&actDefId='+actDefId);
				grid.load();
			}

			function removeScopeVars(){
			
				var ids=[];
				var selRows=grid.getSelecteds();
				for(var i=0;i<selRows.length;i++){
					if(selRows[i].varId){
						ids.push(selRows[i].varId);
					}
				}
				grid.removeRows(selRows);
				if(ids.length>0){
					_SubmitJson({
						url:__rootPath+'/bpm/core/bpmSolution/delScopeVars.do',
						method:'POST',
						data:{
							varIds:ids.join(',')
						}
					});
				}
				
			}
			
			function onCellValidation(e){
	        	
	        	if(e.field=='name' && (!e.value||e.value=='')){
	        		e.isValid = false;
	       		 	e.errorText='变量名称不能为空！';
	        	}
	        	if(e.field=='key' && (!e.value||e.value=='')){
	       		 	e.isValid = false;
	       		 	e.errorText='变量Key不能为空！';
	       		}
	        	if(e.field=='type' && (!e.value||e.value=='')){
	        		e.isValid = false;
	        		e.errorText='变量类型不能为空！';
	        	}
	        }
			
			function saveScopeVars(){
				var nodeId=null;
				var nodeName=null;
				
				var node=tree.getSelectedNode();
				if(!node){
					nodeId='_PROCESS';
				}else{
					nodeId=node.activityId;
					nodeName=node.name;
				}
				//表格数据检验
	        	grid.validate();
	        	if(!grid.isValid()){
	            	return;
	            }
				_SubmitJson({
					url:__rootPath+'/bpm/core/bpmSolution/saveScopeVars.do',
					method:'POST',
					data:{
						nodeId:nodeId,
						nodeName:nodeName,
						solId:solId,
						actDefId:actDefId,
						vars:mini.encode(grid.getData())
					},
					success:function(action){
						grid.load();
					}
				});
			}
			
			function showFormFieldDlg(){
				var nodeId;
				var node=tree.getSelectedNode();
				if(!node){
					nodeId='_PROCESS';
				}else{
					nodeId=node.activityId;
				}
				_OpenWindow({
					title:'选择表单字段',
					height:450,
					width:680,
					url:__rootPath+'/bpm/core/bpmSolution/modelFieldsDlg.do?solId=${bpmSolution.solId}&nodeId='+nodeId+'&actDefId='+actDefId,
					ondestroy:function(action){
						if(action!='ok') return;
						var iframe=this.getIFrameEl();
						var fields=iframe.contentWindow.getSelectedFields();
						var rowCounts=grid.getTotalCount();
						for(var i=0;i<fields.length;i++){
					 	  var isFound=false;
						  for(var j=0;j<rowCounts.length;j++){
							var row=grid.getRow(j);
							if(row.key==fields[i].fieldName){
						        isFound=true;
								break;
						    }
						  }
						  if(!isFound){
							grid.addRow(fields[i]);
						  }
						}
					}
				});
			}
		</script>
</body>
</html>