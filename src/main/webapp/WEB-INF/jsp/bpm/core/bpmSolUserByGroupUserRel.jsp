<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>通过用户关系查找用户</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
		    <table style="width:100%;">
		        <tr>
		      		<td style="width:100%;">
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
						<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
					</td>
				</tr>
			</table>
	</div>
	<div class="form-outer">
		<div>
			说明：左边为变量运算后的最终用户类型，并且把计算后的值作为流程任务的审批者。
		</div>
		<form id="vform">
			<input id="groupText" name="groupText" class="mini-hidden" value="" />
			<table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%">
				
				<tbody>
					<tr>
						<th width="200">用户组来自</th>
						<td>
							<div class="mini-radiobuttonlist" id="from" name="from" data="[{id:'var',text:'来自变量'},{id:'org',text:'选择用户组'},{id:'start-org',text:'发起人所在部门'}]" onvaluechanged="changeFrom" required="true"></div>
						</td>
					</tr>
					<tr id="tr_var" style="display:none">
						<th>变量</th>
						<td>
							<input id="groupVar" class="mini-combobox" name="groupVar" url="${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefId.do?solId=${param.solId}&actDefId=${param.actDefId}" 
								valueField="key" textField="name" style="width:90%" required="true"/>
						</td>
					</tr>
					<tr id="tr_org" style="display:none">
						<th>选择用户组</th>
						<td>
							<input id="groupId" class="mini-buttonedit" name="groupId" onbuttonclick="onButtonEdit" style="width:90%" required="true"/>
						</td>
					</tr>
					<tr>
						<th>用户关系类型</th>
						<td>
							<input id="relTypeKey" class="mini-combobox" name="relTypeKey" url="${ctxPath}/sys/org/osRelType/getGroupUserRelations.do" 
							style="width:90%" required="true" 
							valueField="key" textField="name"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('vform');
		var relTypeKey=mini.get('relTypeKey');
		var party=mini.get('party');
		var groupId=mini.get('groupId');
		var groupVar=mini.get('groupVar');
		
		//更改来源值
		function changeFrom(){
			var from=mini.get('from').getValue();
			if(from=='var'){
				$("#tr_var").css('display','');
				$("#tr_org").css('display','none');
			}else if(from=='org'){
				$("#tr_var").css('display','none');
				$("#tr_org").css('display','');
			}else{
				$("#tr_var").css('display','none');
				$("#tr_org").css('display','none');
			}
		}
		function ok(){
			form.validate();
			if(form.isValid()){
				CloseWindow('ok');
			}else{
				alert('请选择表单值!');
				return;
			}
		}

		function setData(config){
			if(!config){
				return;
			}
			var data=mini.decode(config);
			form.setData(data);
			changeFrom();
			groupId.setText(mini.get("groupText").getValue());
			groupVar.setText(data.groupVarText);
		}

		function getConfigJson(){
			var groupIdText=groupId.getText();
			var relTypeText=relTypeKey.getSelected().name;
			var configDescp='用户来自['+groupIdText+']的用户组关系['+relTypeText+']';
			
			var formData=form.getData();
			if(groupVar.getSelected()){
				formData.groupVarText=groupVar.getSelected().name;
			}
			return{
				config:formData,
				configDescp:configDescp,
			};
		}
		
		function onButtonEdit(e){
			_GroupDlg(true,function(groups){
				var ids="";
				var names="";
				ids=groups.groupId;
				names=groups.name;
				var button=mini.get("groupId");
				button.setValue(ids);
				button.setText(names);
				mini.get("groupText").setValue(names);
			});
		}
	</script>
</body>
</html>