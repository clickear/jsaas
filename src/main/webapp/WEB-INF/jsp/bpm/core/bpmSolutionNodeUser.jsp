<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>业务流程解决方案管理-某一节点人员配置</title>
	<%@include file="/commons/edit.jsp"%>
	<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctxPath}/scripts/flow/form/userTypeSelect.js"></script>
	<style>
		.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
			border-bottom: none;
		}
		.mini-panel-border{
			border: 1px solid #ececec;
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
	<div class="mini-toolbar topBtn">
        <a class="mini-button" iconCls="icon-add"  onclick="addUserRow('${param['nodeId']}')" plain="true">添加</a>
        <a class="mini-button" iconCls="icon-remove" onclick="delUserRow('${param['nodeId']}')" plain="true">删除</a>
        <a class="mini-button" iconCls="icon-save"  onclick="saveTaskUsers('${param['nodeId']}')" plain="true">保存</a>
<!--         <a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a> -->
	  	<input class="mini-hidden" id="nodeText" value=""/>
	</div>
 	<div class="shadowBox90" style="padding-top: 8px;">
		<div 
			id="userGrid" 
			class="mini-datagrid" 
			oncellbeginedit="OnCellBeginEdit"
       		allowCellEdit="true" 
       		allowCellSelect="true" 
       		height="auto" 
       		oncellendedit="OnCellEndEdit"
       		oncellvalidation="onCellValidation" 
       		data-options="{nodeId:'${param['nodeId']}'}"
       		url="${ctxPath}/bpm/core/bpmSolUser/getUserBySolIdNodeId.do?actDefId=${param['actDefId']}&solId=${param['solId']}&nodeId=${param['nodeId']}"
			idField="id" 
			showPager="false" 
			style="width:100%;min-height:100px;"
			allowAlternating="true"
		>
			<div property="columns">
				<div type="indexcolumn" width="20"></div>
				<div field="userType" name="userType" displayField="userTypeName" width="80" headerAlign="center">用户类型</div>
				<div field="config" name="config" displayField="configDescp" width="160" headerAlign="center" >用户值</div>
				<div field="calLogic" name="calLogic" width="60" headerAlign="center" >计算逻辑</div>
				<div type="checkboxcolumn" field="isCal" name="isCal" trueValue="YES" falseValue="NO" width="60" headerAlign="center">是否计算用户</div>
				<div field="sn" name="sn" width="50" headerAlign="center" >序号</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	addBody();
	mini.parse();
	var solId="${param['solId']}";
	var actDefId="${param.actDefId}";
	var nodeId="${param.nodeId}";
	var grid=mini.get('userGrid');
	grid.load();
	
	//保存某个节点的人员配置
	function saveTaskUsers(nodeId){
		var grid=mini.get('userGrid');
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
				actDefId:actDefId,
				category:"task",
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