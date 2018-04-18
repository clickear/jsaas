<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>流程任务更改执行路径</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	
		<div class="mini-toolbar">
			<table class="width:100%">
				<tr>
					<td>
						<a class="mini-button" iconCls="icon-submit" onclick="onSubmit" plain="true">提交</a>
						&nbsp;
						<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="form-outer">	
			<div class="mini-panel" height="auto" style="width:100%;" title="更改执行路径" bodyStyle="padding:8px;">
				<form id="form1">	
					<table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%;" id="nodeJumpTable">
						<tr>
							<th>目标节点</th>
							<th>执行人</th>
						</tr>
						<tr>
							<td width="220">
								<input id="destNodeId" class="mini-combobox"  textField="nodeName" valueField="nodeId" required="true" style="width:100%"
			    				 url="${ctxPath}/bpm/core/bpmNodeSet/getJumpNodes.do?actDefId=${bpmTask.procDefId}&excludeNodeId=${bpmTask.taskDefKey}"  popupWidth="450"
			    				 onvaluechanged="changeDestNode" name="destNodeId" 
			    				 showNullItem="true" nullItemText="请选择..."/>  
							</td>
							<td id="mbox">
								<!-- input class="mini-textboxlist" name="users" id="destNodeUserIds" style="width:50%;" required="true" />
								<a class="mini-button" iconCls="icon-users" plain="true"  onclick="selectUsers('')" alt="审批者">审批者</a-->
							</td>
						</tr>
						<tr>
							<th>执行的动作</th>
							<td>
								<div class="mini-radiobuttonlist"  name="jumpType" id="jumpType" required="true"
					    			textField="text" valueField="id"  onvaluechanged="changeJumpType" value="AGREE"
					   			 	data="[{id:'AGREE',text:'通过'},{id:'BACK',text:'驳回'}]" >
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="mini-panel" height="auto" style="width:100%;" title="流程图">
				<img src="${ctxPath}/bpm/activiti/processImage.do?taskId=${bpmTask.id}"/>
			</div>
		</div>
		<script type="text/javascript">
			mini.parse();
			var form=new mini.Form('form1');
			//存放目标路径
			var destNodesBox=[];
			var taskId="${param['taskId']}";

			function onSubmit(){
				form.validate();
				if(!form.isValid()){
					return;
				}
				
				var destIds={};
				var jumpType=mini.get('jumpType');
				var destNode=mini.get('destNodeId');
				var nodeType=destNode.getSelected().nodeType;
				var destNodeUsers=[];
				
				for(var i=0;i<destNodesBox.length;i++){
					destNodeUsers.push({
						nodeId:destNodesBox[i].getId(),
						userIds:destNodesBox[i].getValue()
					});
				}
				
				_SubmitJson({
					url:__rootPath+'/bpm/core/bpmTask/doFreeJump.do',
					method:'POST',
					data:{
						taskId:taskId,
						jumpType:jumpType.getValue(),
						destNodeId:destNode.getValue(),
						destNodeUsers:mini.encode(destNodeUsers)
					},
					success:function(result){
						CloseWindow('ok');
					}
				});
			}
			
			function changeDestNode(){
				var destNodeId=mini.get('destNodeId').getValue();
				if(destNodeId==''){
					return;
				}
				
				_SubmitJson({
					showMsg:false,
					url:__rootPath+'/bpm/core/bpmTask/getDestNodeUsers.do',
					data:{
						actInstId:'${bpmTask.procInstId}',
						nodeId:destNodeId
					},
					success:function(result){
						var data=result.data;
						$('#mbox').html('');
						destNodesBox=[];
						
						for(var i=0;i<data.length;i++){
							if(data[i].nodeType!='userTask'){
								continue;
							}
							var el=document.createElement("div");
							$(el).append('<span>'+data[i].nodeText+'&nbsp;:&nbsp;&nbsp;</span>');
							$('#mbox').append(el);
							var mbox=new mini.TextBoxList();
							mbox.setName('destNodeId');
							mbox.setId(data[i].nodeId);
							mbox.setStyle('width:70%')
							if(data[i].taskNodeUser){
								mbox.setValue(data[i].taskNodeUser.userIds);
								mbox.setText(data[i].taskNodeUser.userFullnames);
							}
							destNodesBox.push(mbox);
							mbox.render(el);
							
							var mb=new mini.Button();
							mb.setName('destButtonId');
							mb.setText('选择');
							mb.setIconCls('icon-user');
							mb.setPlain(true);
							mb.setId('__m__'+data[i].nodeId);
							mb.on('click',function(e){
								var sender=e.sender;
								var nodeId=sender.getId().replace('__m__','');
								
								var box=mini.get(nodeId);
								_UserDlg(false,function(users){
									var uIds=[];
									var uNames=[];
									for(var i=0;i<users.length;i++){
										uIds.push(users[i].userId);
										uNames.push(users[i].fullname);
									}
									box.setValue(uIds.join(','));
									box.setText(uNames.join(','));
								});
							});
							
							mb.render(el);
						}
					}
				});
			}
		</script>	
</body>
</html>