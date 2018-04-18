<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<html>
<head>
<title>查找用户所在部门下的用户组的用户</title>
<%@include file="/commons/list.jsp" %>
</head>
<body>
	<div class="vform-margin">
		<div class="mini-toolbar mini-toolbar-bottom">
			<table style="width:100%">
				<tr>
					<td>
						<a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
						<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
					</td>
				</tr>
			</table>
		</div>
		<p style="text-indent: 6px;">查找用户所在部门下的用户组的用户</p>
		<form id="vform" class="form-outer2">
			<input id="groupText" name="groupText" class="mini-hidden" value="" />
			<table class="table-detail column_1" cellpadding="0" cellspacing="1" style="width:100%">
				<tbody>
					<tr >
						<th width="150">用户变量</th>
						<td>
							<input id="userIdVar" class="mini-combobox" name="userIdVar" 
								url="${ctxPath}/bpm/core/bpmSolVar/getBySolIdActDefId.do?solId=${param.solId}&actDefId=${param.actDefId}" 
								valueField="key" textField="name" style="width:90%" required="true"/>
						</td>
					</tr>
					<tr >
						<th width="150">所在部门下的用户组</th>
						<td>
							<input id="groupId" class="mini-buttonedit" name="groupId" onbuttonclick="onButtonEdit" style="width:90%" required="true"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('vform');
		var groupId=mini.get('groupId');
		var groupText=mini.get('groupText');
		var userIdVar=mini.get('userIdVar');
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
		
			groupId.setText(mini.get("groupText").getValue());
			
		}

		function getConfigJson(){
			var groupIdText=groupId.getText();
			var configDescp='用户['+userIdVar.getText()+']部门下的用户组'+groupIdText+']的用户';
			
			var formData=form.getData();
			formData.groupVarText=groupId.getText();
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