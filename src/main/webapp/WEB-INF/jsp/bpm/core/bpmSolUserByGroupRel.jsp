<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
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

		function setData(config){
			form.setData(mini.decode(config));
		}

		function getConfigJson(){
			var userIdText=userId.getSelected().name;
			var relTypeText=relTypeKey.getSelected().name;
			var configDescp='用户来自['+userIdText+']的用户组关系['+relTypeText+']';
			return{
				config:form.getData(),
				configDescp:configDescp
			};
		}
	</script>
</body>
</html>