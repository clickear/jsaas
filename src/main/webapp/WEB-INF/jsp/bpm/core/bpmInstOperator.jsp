<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html>
<html>
<head>
<title>流程实例干预</title>
<%@include file="/commons/edit.jsp"%>
<script src="${ctxPath}/scripts/bpmAnimate.js" type="text/javascript"></script>
<style type="text/css">
	.nodeBO .table-view > tbody > tr > td{
		border-bottom: none;
	}
</style>
</head>
<body>
			<div class="mini-toolbar mini-toolbar-bottom topBtn">
					<a class="mini-button" iconCls="icon-save" onclick="onSave()" >保存</a>
					<a class="mini-button" iconCls="icon-detail" onclick="showInstDetail()">实例明细</a>
					<a class="mini-button" iconCls="icon-var" onclick="modifyInstVars()">修改流程变量</a>
					<a class="mini-button" iconCls="icon-form" onclick="modifyFormInst()">修改表单数据</a>
					<a class="mini-button" iconCls="icon-property" onclick="showProcessProperties()">属性配置</a>
					<a class="mini-button" iconCls="icon-property" onclick="bpmSolutionPros()">方案配置</a>
					<a class="mini-button" iconCls="icon-refresh" onclick="location.reload()">刷新</a>
					<li class="separator"></li>
					<input type="checkbox" name="isCheck" onclick="showImgRow()" checked="checked"/>流程图
			</div>
			<div class="heightBox"></div>
			<div class="form-outer2 shadowBox90" style="padding-top: 8px;">		
					
					<ul id="jumpMenu" class="mini-menu" style="display:none;">
						<c:forEach items="${taskNodeUsers}" var="taskNode">
							<c:if test="${taskNode.running!=true}">
							  <li iconCls="icon-user" onclick="changeFlowPath('${taskNode.nodeId}','${taskNode.nodeText}')">跳至【${taskNode.nodeText}】</li>
							</c:if>
						</c:forEach>
					</ul>
			
				    <div class="nodeBO">
				    	<table class="table-view" cellpadding="0" cellspacing="1" style="width:100%">
					    	<tr>
					    		<th width="15%">节点名称</th>
					    		<th width="25%">流程人员配置</th>
					    		<th width="35%">干预人员配置</th>
					    		<th width="25%">审批人员</th>
					    	</tr>
							<c:forEach items="${taskNodeUsers}" var="taskNode" >
								<tr class="table-row"<c:if test="${taskNode.running==true}"></c:if>>
									<td>
										${taskNode.nodeText}
									</td>
									<td>
										${taskNode.refUserFullnames}
										<input type="hidden" id="refUserId_${taskNode.nodeId}" value="${taskNode.refUserIds}"/>
										<input type="hidden" id="refUserFullnames_${taskNode.nodeId}" value="${taskNode.refUserFullnames}"/>
										<a class="mini-button" iconCls="icon-setting" plain="true" onclick="changeNodeUserSet('${taskNode.nodeId}','${taskNode.nodeText}')">设置</a>
									</td>
									<td>
										<input type="hidden" name="nodeId" value="${taskNode.nodeId}"/>
										<div class="mini-textboxlist" id="userId_${taskNode.nodeId}" allowInput="false" name="userId" value="${taskNode.userIds}" text="${taskNode.userFullnames}" ></div>
										<a class="mini-button" iconCls="icon-user" plain="true" onclick="onSetting('${taskNode.nodeId}')">设置</a>
										<a class="mini-button" iconCls="icon-clear" plain="true" onclick="onClear('${taskNode.nodeId}')">清空</a>
										
										<a class="mini-button"  iconCls="icon-copy" plain="true" onclick="copyRef('${taskNode.nodeId}')">复制</a>
												
									</td>
									<td>
										${taskNode.exeUserFullnames}
										<c:if test="${taskNode.running==true}">
											<a class="mini-button" iconCls="icon-edit" plain="true" onclick="changeTaskUser('${taskNode.nodeId}','${taskNode.exeUserIds }','${taskNode.exeUserFullnames}')">更改执行人</a>
											<a class="mini-menubutton" menu="#jumpMenu"  iconCls="icon-flow"  onclick="taskNodeClick('${taskNode.nodeId}','${taskNode.nodeText}')">更改执行路径</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>

					<table id="imgTable" class="table-view" cellpadding="0" cellspacing="1" style="width:100%;" >
						<tr>
							<td>
								在流程图上右键可以更改流程方案设置
							</td>
						</tr>
						<tr >
							<td>
								<div style="width:100%;overflow: auto;">
									<img src="${ctxPath}/bpm/activiti/processImage.do?instId=${bpmInst.instId}" usemap="#imgHref" style="border:0px;"/>
									<imgArea:imgAreaScript actDefId="${bpmInst.actDefId}"></imgArea:imgAreaScript>
								</div>
							</td>
						</tr>
					</table>
					
					<ul id="contextMenu" class="mini-contextmenu" style="display:none">
						<li name="prop" iconCls="icon-mgr"  onclick="nodeSetting()">属性设置</li>
						<li name="usermenu" iconCls="icon-user" onclick="userSetting()">人员配置</li>
						
					</ul>
			</div>
			
		  <div id="pathWin" class="mini-window" iconCls="icon-flow-white" title="更改流程执行路径" 
		  	style="width:500px;height:350px;" showModal="true" showFooter="true" 
		  	allowResize="false" allowDrag="true">
		    
		    <form id="opinionForm">
		    	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
				    <table width="100%">
				        <tr>
				      		<td style="width:100%;">
								<a class="mini-button" iconCls="icon-ok" plain="true" onclick="changePath()">确定</a>
		   						<a class="mini-button" iconCls="icon-cancel"  plain="true" onclick="closePathWin()">关闭</a>
							</td>
						</tr>
					</table>
				</div>
		    
		    	
		    	<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
			    	<tbody>
			    		<tr>
			    			<th>更改路径</th>
			    			<td>
			    				<div id="pathInfo"></div>
			    			</td>
			    		</tr>
				    	<tr>
				    		<th width="120">操作类型</th>
				    		<td>
								<div id="opJumpType" class="mini-radiobuttonlist" 
								    data="[{id:'AGREE',text:'通过'},{id:'BACK',text:'驳回'},{id:'INTERPOSE',text:'干预'}]"
								    textField="text" valueField="id" value="INTERPOSE"  >
								</div>
				    		</td>
				    	</tr>
				    	<tr>
				    		<th width="120">干预后操作</th>
				    		<td>
								 <input class="mini-radiobuttonlist" id="nextJumpType" name="nextJumpType" value="normal" data="[{id:'orgPathReturn',text:'原路返回'},{id:'normal',text:'正常执行'}]" required="true"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<th>
				    			意　见
				    		</th>
				    		<td style="padding:5px;">
				    			<textarea class="mini-textarea" id="opinion" style="height:80px;width:98%"></textarea>
				    		</td>
				    	</tr>
			    	</tbody>
		    	</table>
		    	
		    </form>
		    
		</div>
			
		<script type="text/javascript">
			addBody();
			var nodeId;
			var nodeType;
			var nodeName;
			var solId="${bpmInst.solId}";
			mini.parse();
			
			function onSetting(nodeId){
				var userbox=mini.get('userId_'+nodeId);
				_UserDlg(false,function(users){
					var uIds=[];
					var uNames=[];
					for(var i=0;i<users.length;i++){
						uIds.push(users[i].userId);
						uNames.push(users[i].fullname);
					}
					if(userbox.getValue()!=''){
						uIds.unshift(userbox.getValue().split(','));
					}
					if(userbox.getText()!=''){
						uNames.unshift(userbox.getText().split(','));	
					}
					userbox.setValue(uIds.join(','));
					userbox.setText(uNames.join(','));
				});
			}
			//当前节点
			var nodeId=null;
			var nodeName=null;
			//跳至的目标节点
			var destNodeId=null;
			function taskNodeClick(cnodeId,cnodeName){
				nodeId=cnodeId;
				nodeName=cnodeName;
			}
			
			function changePath(){
				var jumpType=mini.get('opJumpType').getValue();
				var opinion=mini.get('opinion').getValue();
				var nextJumpType=mini.get('nextJumpType').getValue();
				
				_SubmitJson({
					url:__rootPath+'/bpm/core/bpmTask/changePath.do',
					data:{
						nodeId:nodeId,
						destNodeId:destNodeId,
						instId:'${bpmInst.instId}',
						nextJumpType:nextJumpType,
						jumpType:jumpType,
						opinion:opinion
					},
					success:function(result){
						location.reload();
					}
				});
			}
			//更改执行路径
			function changeFlowPath(dNodeId,destNodeName){
				var win=mini.get('pathWin');
				destNodeId=dNodeId;
				$("#pathInfo").html(nodeName+'->' + destNodeName);
				win.show();
			}
			
			function closePathWin(){
				var win=mini.get('pathWin');
				win.hide();
			}
			
			function onClear(nodeId){
				var userbox=mini.get('userId_'+nodeId);
				userbox.setValue('');
				userbox.setText('');
			}
			
			//复制引用
			function copyRef(nodeId){
				var userIds=$('#refUserId_'+nodeId).val();
				var userfullnames=$("#refUserFullnames_"+nodeId).val();
				
				var userbox=mini.get('userId_'+nodeId);
				userbox.setValue(userIds);
				userbox.setText(userfullnames);
			}
			
			function onSave(){
				var nodeIdMap=[];
				$("input[name='nodeId']").each(function(){
					var nodeId=$(this).val();
					var userIds=mini.get('userId_'+nodeId).getValue();
					if(userIds!=''){
						nodeIdMap.push({
							nodeId:nodeId,
							userIds:userIds
						});
					}
				});
				
				_SubmitJson({
					url:__rootPath+'/bpm/core/bpmInst/saveNodeUsers.do',
					method:'POST',
					data:{
						instId:'${bpmInst.instId}',
						nodeUsers:mini.encode(nodeIdMap)
					},
					success:function(result){
						
					}
				});
			}
			
			//更改任务节点属性配置
			function changeNodeUserSet(nodeId,nodeName){
				_OpenWindow({
					title : '流程节点[' + nodeName + ']-人员配置',
					iconCls : 'icon-user',
					max : true,
					width : 600,
					height : 500,
					url : __rootPath + '/bpm/core/bpmSolution/nodeUser.do?nodeId=' + nodeId + '&nodeType=userTask&solId=${bpmInst.solId}&actDefId=${bpmInst.actDefId}',
					ondestroy:function(action){
						if(action=='ok'){
							location.reload();
						}
					}	
				});
			}
			
			//更改任务的执行人
			function changeTaskUser(nodeId,userIds,userNames){
				var users=[];
				if(userIds){
					var aryUserId=userIds.split(",");
					var aryUserName=userNames.split(",");
					for(var i=0;i<aryUserId.length;i++){
						var user={userId:aryUserId[i],fullname:aryUserName[i]};
						users.push(user);
					}
				}
				
				_UserDialog({single:false,users:users,callback: function(users){
					var userIds=[];
					for(var i=0;i<users.length;i++){
						userIds.push(users[i].userId);
					}
					_SubmitJson({
						url:__rootPath+'/bpm/core/bpmInst/changeTaskUser.do',
						data:{
							instId:'${bpmInst.instId}',
							nodeId:nodeId,
							userIds:userIds.join(',')
						},
						success:function(result){
							CloseWindow('ok');
						}
					});
				}});
			}
				
				
			function showImgRow(){
				$("#imgTable").toggle();
			}
			function modifyInstVars(){
				_OpenWindow({
					iconCls:'icon-var',
					title:'${bpmInst.subject}-编辑变量',
					width:800,
					height:450,
					max:true,
					url:__rootPath+'/bpm/core/bpmInst/varEdit.do?actInstId=${bpmInst.actInstId}'
				});
			}
			function showInstDetail(){
				_OpenWindow({
					iconCls:'icon-detail',
					title:'${bpmInst.subject}',
					width:800,
					height:450,
					max:true,
					url:__rootPath+'/bpm/core/bpmInst/inform.do?instId=${bpmInst.instId}'
				});
			}
			//流程解决方案属性配置
			function bpmSolutionPros(){
				_OpenWindow({
					iconCls:'icon-mgr',
        			title:'${bpmInst.subject}-流程解决方案配置',
        			url:'${ctxPath}/bpm/core/bpmSolution/mgr.do?solId='+solId,
        			width:800,
        			height:450,
        			max:true
				});
			}

			//修改当前节点表单实例
			function modifyFormInst(){
				_OpenWindow({
					title:'${bpmInst.subject}-表单数据更改',
					height:450,
					width:800,
					url:__rootPath+'/bpm/core/bpmInst/formDataEdit.do?instId=${bpmInst.instId}',
				});
			}
				
			$(function(){
					$("#imgHref").bind("contextmenu", function (e) {
		                var menu = mini.get("contextMenu");
		                var usermenu = mini.getByName("usermenu", menu);
		               
		                if(nodeType=='userTask'){
		   				 	usermenu.show();
		   				 }else{
			   				usermenu.hide();
			   			 }
		                menu.showAtPos(e.pageX, e.pageY);
		                return false;
		            }); 

				$("area").bind("mouseover",function(){
					  nodeId=$(this).attr("id");
					  nodeType=$(this).attr('type');
					  nodeName=$(this).attr('name');
				});
				 $(".thisPlayButton").remove();
			});
			
			function nodeSetting(){
				_OpenWindow({
					title : '流程节点[' + nodeName + ']-属性配置',
					iconCls : 'icon-mgr',
					max : true,
					width : 600,
					height : 500,
					url : __rootPath + '/bpm/core/bpmNodeSet/getNodeConfig.do?actDefId=${bpmInst.actDefId}&nodeId=' + nodeId + '&nodeType=' + nodeType + '&solId=' + solId
				});
			}
			
			function userSetting(){
				_OpenWindow({
					title : '流程节点[' + nodeName + ']-人员配置',
					iconCls : 'icon-user',
					max : true,
					width : 600,
					height : 500,
					url : __rootPath + '/bpm/core/bpmSolution/nodeUser.do?nodeId=' + nodeId + '&nodeType=' + nodeType + '&solId=' + solId+'&actDefId=${bpmInst.actDefId}'
				});
			}
			
			function showProcessProperties(){
				_OpenWindow({
					title : '流程属性配置-[${bpmInst.subject}]',
					iconCls : 'icon-properties',
					max : true,
					width : 600,
					height : 500,
					url : __rootPath + '/bpm/core/bpmNodeSet/getNodeConfig.do?nodeId=_PROCESS&nodeType=process&solId=${bpmInst.solId}&actDefId=${bpmInst.actDefId}' 
				});
			}
			
			function formSetting(){
				_OpenWindow({
					title : '流程节点[' + nodeName + ']-表单配置',
					iconCls : 'icon-form',
					max : true,
					width : 600,
					height : 500,
					url : __rootPath + '/bpm/core/bpmSolution/nodeForm.do?actDefId=${bpmInst.actDefId}&nodeId=' + nodeId + '&nodeType=' + nodeType + '&solId=' + solId
				});
			}
		</script>
</body>
</html>