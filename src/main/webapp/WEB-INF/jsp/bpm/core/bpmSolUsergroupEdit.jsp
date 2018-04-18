<%-- 
    Document   : [BpmSolUsergroup]编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户分组信息编辑</title>
<%@include file="/commons/edit.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctxPath}/scripts/flow/form/userTypeSelect.js"></script>
</head>
<body>
	
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn" >
	     <a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存</a>
	     <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a>
	</div>
	<div class="mini-fit shadowBox90" style="margin-top: 10px;">	
	<form id="form1">	
		<div class="form-outer">
			<input id="pkId" name="id" class="mini-hidden" value="${userGroup.id}" />
			<input id="solId" name="solId" class="mini-hidden" value="${userGroup.solId}" />
			<input id="actDefId" name="actDefId" class="mini-hidden" value="${userGroup.actDefId}" />
			<input id="nodeId" name="nodeId" class="mini-hidden" value="${userGroup.nodeId}" />
			<input id="sn" name="sn" class="mini-hidden" value="${userGroup.sn}" />
			<input id="groupType" name="groupType" class="mini-hidden" value="${userGroup.groupType}" />
			<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
				<caption>用户分组信息</caption>
				<tr>
					<th width="140">名　　称</th>
					<td><input name="groupName" value="${userGroup.groupName}" class="mini-textbox" vtype="maxLength:50" style="width: 90%" />
					</td>
					<th width="140">通知类型</th>
					<td>
						<div name="notifyType" class="mini-checkboxlist"  value="${userGroup.notifyType}"
					        textField="text" valueField="name"  url="${ctxPath}/bpm/core/bpmConfig/getNoticeTypes.do" >
					    </div>
					</td>
				</tr>
			</table>
		</div>	
	
		<div class="mini-toolbar">
	                 <a class="mini-button" id="addBtn" iconCls="icon-add"  onclick="addUserRow()" plain="true" enabled="true">添加</a>
	                 <a class="mini-button" id="delBtn" iconCls="icon-remove" onclick="delUserRow()" plain="true" enabled="true">删除</a>
		</div>
		<div class="form-outer">
	    	<div id="userGrid" class="mini-datagrid" oncellbeginedit="OnCellBeginEdit" style="height:250px;width:100%;"
	         		allowCellEdit="true" allowCellSelect="true"  oncellendedit="OnCellEndEdit"
	         		oncellvalidation="onCellValidation" 
	          		url="${ctxPath}/bpm/core/bpmSolUser/getUserByGroupId.do?groupId=${userGroup.id}&category=${userGroup.groupType}"
					idField="id" showPager="false" style="width:100%;min-height:100px;">
				<div property="columns">
					<div type="indexcolumn" width="20"></div>
					<div field="userType" name="userType" displayField="userTypeName" width="80" headerAlign="center">用户类型
						<input id="userTypeEditor" property="editor" class="mini-combobox" style="width:100%;" popupWidth="450" 
							textField="typeName" valueField="typeKey"	url="${ctxPath}/bpm/core/bpmSolUser/getUserTypes.do"  
							required="true" allowInput="false"/>
					</div>
					<div field="config" name="config" displayField="configDescp" width="160" headerAlign="center" >用户值
						<input id="configEditor" property="editor" class="mini-buttonedit" style="width:100%" onbuttonclick="selConfig" 
							selectOnFocus="true" allowInput="false"/>
					</div>
					<div field="calLogic" name="calLogic" width="60" headerAlign="center" >计算逻辑
						<input id="logicEditor" property="editor" class="mini-combobox" style="width:100%" textField="text" valueField="id" 
  								data="[{id:'AND',text:'与'},{id:'OR',text:'或'},{id:'NOT',text:'非'}]" required="true"  />  
					</div>
					<div type="checkboxcolumn" field="isCal" name="isCal" trueValue="YES" falseValue="NO" width="60" headerAlign="center">是否计算用户</div>
					<div field="sn" name="sn" width="50" headerAlign="center" >序号
						<input id="snEditor" property="editor"  class="mini-spinner" minValue="1" maxValue="200" style="width:100%"/>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
	
	<script type="text/javascript">
	var solId="${param.solId}";
	var actDefId="${param.actDefId}";
	var groupId="${userGroup.id}";
	mini.parse();
	//当前的表格控件
	var grid=mini.get('userGrid');
	if(groupId){
		grid.load();
	}
	
	
	
	function handleFormData(formData){
		var result={isValid:true};
		var json={};
		for(var i=0;i<formData.length;i++){
			var obj=formData[i];
			json[obj.name]=obj.value;
		}
		
		json.userList=removeGirdData(grid.getData());
		result.formData=json;
		
		result.postJson=true;
		
		return result;
	}
	
	function removeGirdData(list){
		for(var i=0;i<list.length;i++){
			var obj=list[i];
			delete obj._id;
			delete obj._uid;
			delete obj.pkId;
		}
		return list;
	}
	
	function OnCellBeginEdit(e){
		
	}
	
	
	
	

	</script>
	<rx:formScript formId="form1" baseUrl="bpm/core/bpmSolUsergroup"
		entityName="com.redxun.bpm.core.entity.BpmSolUsergroup" />
	
</body>
</html>