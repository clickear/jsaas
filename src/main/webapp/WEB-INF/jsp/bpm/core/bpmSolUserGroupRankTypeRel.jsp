<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
		
		<form id="vform">
			<input id="groupText" name="groupText" class="mini-hidden" value="" />
			<table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%">
				<tbody>
					<tr>
						<th >发起人所在部门往上查找的符合等级的部门</th>
						<td>
							<input id="rankLevel" name="rankLevel" class="mini-combobox" url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId=1" 
							showNullItem="true" nullItemText="请选择..." required="true"
							textField="name" valueField="level" />
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
		var rankLevel=mini.get('rankLevel');
		
		
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
			var rankType=rankLevel.getSelected().name;
			var relTypeText=relTypeKey.getSelected().name;
			var configDescp='用户来自发起人往上查找符合等级('+rankType+')的用户组的关系['+relTypeText+']用户';
			return{
				config:form.getData(),
				configDescp:configDescp,
			};
		}
	</script>
</body>
</html>