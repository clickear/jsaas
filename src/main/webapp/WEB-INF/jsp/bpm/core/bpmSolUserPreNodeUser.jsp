<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>用户节点来自另一个节点的审批人</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn" >
	    <a class="mini-button" iconCls="icon-save" plain="true" onclick="ok">确定</a>
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a> -->
	</div>
	
	<div class="form-outer shadowBox90" style="margin-top: 10px;">
		
		<form id="vform">
			<input id="groupText" name="groupText" class="mini-hidden" value="" />
			<table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%">
				<tbody>
					<tr>
						<th >用户来自另一审批节点</th>
						<td>
							<input class="mini-combobox" id="nodeId" name="nodeId" required="true"
							 style="width:380px" url="${ctxPath}/bpm/core/bpmNodeSet/getTaskNodes.do?actDefId=${param['actDefId']}" idField="activityId" valueField="activityId" textField="name" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('vform');
		
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
			if(config){
				config=mini.decode(config);
				var node=mini.get('nodeId');
				node.setValue(config.nodeId);
			}
		}

		function getConfigJson(){
			var node=mini.get('nodeId');
			var nodeName=node.getText();
			
			var nodeId=node.getValue();;
			var configDescp='用户来自任务节点('+nodeName+')的审批用户';
			return{
				config:{nodeId:nodeId,nodeName:nodeName},
				configDescp:configDescp,
			};
		}
	</script>
</body>
</html>