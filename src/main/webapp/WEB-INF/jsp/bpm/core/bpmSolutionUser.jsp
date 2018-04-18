<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>业务流程解决方案管理-流程定义节点人员配置</title>
	<%@include file="/commons/edit.jsp"%>
	<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctxPath}/scripts/flow/form/userTypeSelect.js"></script>
	<style type="text/css">
		.Tree .mini-panel-border{
			width: 100%;
		}
		#center{
			background: transparent;
		}
	</style>
</head>
<body>
		<div style="display:none">
			<input 
				id="userTypeEditor" 
				class="mini-combobox" 
				style="width:100%" 
				textField="typeName" 
				valueField="typeKey" 
				popupWidth="450"
				url="${ctxPath}/bpm/core/bpmSolUser/getUserTypes.do"  
				required="true" 
				allowInput="false" 
			/>
			<input 
				id="configEditor" 
				class="mini-buttonedit" 
				style="width:100%" 
				onbuttonclick="selConfig" 
				selectOnFocus="true" 
				allowInput="false"
			/>
			<input 
				id="snEditor" 
				class="mini-spinner" 
				minValue="1" 
				maxValue="200" 
				style="width:100%"
			/>
			<input 
				id="logicEditor" 
				class="mini-combobox" 
				style="width:100%" 
				textField="text" 
				valueField="id" 
    			data="[{id:'AND',text:'与'},{id:'OR',text:'或'},{id:'NOT',text:'非'}]" 
    			required="true"  
   			/>  
		</div>
		<div id="orgLayout" class="mini-layout" style="width:100%;height:100%;"> 
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
		         	url="${ctxPath}/bpm/core/bpmNodeSet/getTaskNodes.do?actDefId=${bpmDef.actDefId}" 
		         	style="width:100%;height:100%;"
					parentField="parentActivitiId" 
					expandOnLoad="true" 
					style="overflow: auto;"
					showTreeIcon="true" 
					textField="name" 
					idField="activityId" 
					resultAsTree="false"  
	                onnodeclick="nodeClick">        
	            </ul>
	            </div>
		    </div>
		    <div 
		    	title="流程审批人员" 
		    	region="center" 
		    	showHeader="false" 
		    	showCollapseButton="false" 
		    	iconCls="icon-group"
		    	>
		    	<div class="mini-toolbar topBtn">
		    		<a class="mini-button" id="addBtn"  iconCls="icon-add" onclick="addUserRow()" plain="true" enabled="false">添加</a>
           			<a class="mini-button" id="delBtn" iconCls="icon-remove" onclick="delUserRow()" plain="true" enabled="false">删除</a>
           			<a class="mini-button" id="saveBtn" iconCls="icon-save"  onclick="saveTaskUsers()" plain="true" enabled="false">保存</a>	                    
		    	</div>
		    	<div class="mini-fit">
			    	<div id="userGrid" class="mini-datagrid" oncellbeginedit="OnCellBeginEdit" allowAlternating="true"
	            		allowCellEdit="true" allowCellSelect="true"  oncellendedit="OnCellEndEdit"
	            		oncellvalidation="onCellValidation" 
	             		url="${ctxPath}/bpm/core/bpmSolUser/getUserBySolIdNodeId.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}"
						idField="id" showPager="false" style="width:100%;height:100%;">
						<div property="columns">
							<div type="indexcolumn" width="20"></div>
							<div field="userType" name="userType" displayField="userTypeName" width="80" headerAlign="center">用户类型</div>
							<div field="config" name="config" displayField="configDescp" width="160" headerAlign="center" >用户值</div>
							<div field="calLogic" name="calLogic"  width="60" headerAlign="center" >计算逻辑</div>
							<div type="checkboxcolumn" field="isCal" name="isCal"  trueValue="YES" falseValue="NO" width="60" headerAlign="center">是否计算用户</div>
							<div field="sn" name="sn" width="50" headerAlign="center" >序号</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		mini.parse();
		//当前的表格控件
		var grid=mini.get('userGrid');
		var solId="${param.solId}";
		var actDefId="${param.actDefId}";
		var nodeId='${param.nodeId}';
		var tree=mini.get('bpmTaskNodeTree');
		
		function nodeClick(e){
			mini.get('addBtn').setEnabled(true);
			mini.get('delBtn').setEnabled(true);
			mini.get('saveBtn').setEnabled(true);
			nodeId=e.node.activityId;
			grid.setUrl("${ctxPath}/bpm/core/bpmSolUser/getUserBySolIdNodeId.do?solId=${bpmSolution.solId}&actDefId=${bpmDef.actDefId}&nodeId="+nodeId);
			grid.load();
		}
		
		grid.on('drawcell',function(e){
			var recored=e.record;
			var field=e.field;
			var val=e.value;
			if(field=='calLogic'){
				if(val=='OR'){
					e.cellHtml='或';
				}else if(val=='AND'){
					e.cellHtml='与';
				}else{
					e.cellHtml='非';
				}
			}
		});

		//保存某个节点的人员配置
		function saveTaskUsers(){
			
			var nodeId=null;
			var nodeName=null;
			var node=tree.getSelectedNode();
			if(!node){
				nodeId='process';
			}else{
				nodeId=node.activityId;
				nodeName=node.name;
			}
			//表格检验
        	grid.validate();
        	if(!grid.isValid()){
            	return;
            }
        	
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmSolUser/saveNodeUsers.do',
				method:'POST',
				data:{
					nodeId:nodeId,
					solId:solId,
					actDefId:"${bpmDef.actDefId}",
					nodeText:nodeName,
					category:'task',
					usersJson:mini.encode(grid.getData())
				},
				success:function(text){
					grid.load();
				}
			});
		}
		
	</script>
</body>
</html>