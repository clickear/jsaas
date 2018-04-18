<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>通过用户关系查找用户</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn" >
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a> -->
	</div>
	<div class="form-outer shadowBox90">
		<div>
			说明：左边为变量运算后的最终用户类型，并且把计算后的值作为流程任务的审批者。
		</div>
		<form id="vform">
			<table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%">
				
				<tbody>
					<tr>
						<th width="200">用户</th>
						<td>
							<input id="userId" class="mini-combobox" name="userId" url="${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefId.do?solId=${param.solId}&actDefId=${param.actDefId}" 
							valueField="key" textField="name" style="width:90%" required="true"/>
						</td>
					</tr>
					<tr>
						<th>用户关系类型</th>
						<td>
							<input id="relTypeKey" class="mini-combobox" name="relTypeKey" url="${ctxPath}/sys/org/osRelType/getUserRels.do" 
							style="width:90%" required="true" onvaluechanged="onRelTypeChanged"
							valueField="key" textField="name"/>
						</td>
					</tr>
					<tr>
						<th>查找关系方</th>
						<td>
							<input id="party" class="mini-combobox" name="party" style="width:90%" required="true"
							data="[]" valueField="name" textField="name"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		addBody();
		mini.parse();
		var form=new mini.Form('vform');
		var relTypeKey=mini.get('relTypeKey');
		var party=mini.get('party');
		var userId=mini.get('userId');
		
		function ok(){
			form.validate();
			if(form.isValid()){
				CloseWindow('ok');
			}else{
				alert('请选择表单值!');
				return;
			}
		}
		
		function onRelTypeChanged(e){
			var val=relTypeKey.getValue();
			var typeData=relTypeKey.getData();
			for(var i=0;i<typeData.length;i++){
				if(typeData[i].key==val){
					var data=[{name:typeData[i].party1},{name:typeData[i].party2}];
					party.setData(data);
					return;
				}
			}
		}
		
		function setData(config){
			form.setData(mini.decode(config));
		}

		function getConfigJson(){
			var userIdText=userId.getSelected().name;
			var relTypeText=relTypeKey.getSelected().name;
			var partyText=party.getSelected().name;
			
			var configDescp='用户来自['+userIdText+']的用户关系['+relTypeText+']的关系方['+partyText+']';
			
			return{
				config:form.getData(),
				configDescp:configDescp
			};
		}
	</script>
</body>
</html>