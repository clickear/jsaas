<%-- 
    Document   : 流程测试用例明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程测试用例明细</title>
<%@include file="/commons/get.jsp"%>
</head>
<body>
	<c:if test="${empty bpmInst || bpmInst.status=='RUNNING'}">
		<div class="mini-toolbar">
			<c:if test="${empty bpmTestCase.instId }">
				<a class="mini-button" iconCls="icon-start" onclick="onRun">执行</a>
			</c:if>
			<c:if test="${not empty bpmTestCase.instId }">
				<c:if test="${bpmInst.status=='RUNNING'}">
					<a class="mini-button" iconCls="icon-next" onclick="doNext">下一步</a>
				</c:if>
			</c:if>
		</div>
	</c:if>
	<div id="form1">
		<table style="width: 100%" class="table-detail" cellpadding="0" cellspacing="1">
			<caption>测试用例基本信息</caption>
			<tr>
				<th width="100">用例名称：</th>
				<td>${bpmTestCase.caseName}</td>
			</tr>
			<c:if test="${not empty bpmInst }">
				<tr>
					<th width="100">执行明细</th>
					<td>
						<%-- <a href="#" onclick="showBpmInstDetail('${bpmInst.instId}')">${bpmInst.subject}</a> --%>
						${bpmInst.subject}
						<a class="mini-button" iconCls="icon-expand" onclick="expandBpmImage">展开流程图</a>
					</td>
				</tr>
				<tr id="bpmImageTr" style="display:none;">
					<th>流程状态图</th>
					<td>
						<c:choose>
							<c:when test="${bpmInst.status=='RUNNING'}">
								<img src="${ctxPath}/bpm/activiti/processImage.do?actInstId=${bpmInst.actInstId}"/>
							</c:when>
							<c:otherwise>
								<img src="${ctxPath}/bpm/activiti/processImage.do?instId=${bpmInst.instId}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:if>
			<tr>
				<th>发起人</th>
				<td>
					<input id="startUserId" name="startUserId" class="mini-buttonedit" value="${bpmTestCase.startUserId}" text="${startUser.fullname}" onbuttonclick="changeStartUser()" selectOnFocus="true" allowInput="false" />
				</td>
			</tr>
				
		</table>
		
		<c:if test="${bpmInst.status=='RUNNING'}">
			<table class="table-detail" cellpadding="0" cellspacing="1" style="width:100%">
				<caption>流程下一步</caption>
				<thead>
					<tr>
						<th>任务</th>
						<th>执行人</th>
						<th>审批动作</th>
						<th>意见</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${taskUserList}" var="taskUser">
					<tr>
						<td>
							<%-- <a href="#" onclick="showTaskInfo('${taskUser.taskId}')">${taskUser.taskName}</a> --%>
							${taskUser.taskName}
							<input class="mini-hidden" id="taskName${taskUser.taskId}" name="taskName" value="${taskUser.taskName}"/>
							<input type="hidden" name="taskId" value="${taskUser.taskId}"/>
						</td>
						<td>
							<div id="taskUserId${taskUser.taskId}" name="taskUserId"  class="mini-radiobuttonlist" textfield="fullname" valuefield="userId" data='${taskUser.userJsons}'></div>
						</td>
						<td>
							<div id="checkStatus${taskUser.taskId}" name="checkStatus" class="mini-radiobuttonlist" data="[{id:'AGREE',text:'同意'},{id:'REFUSE',text:'反对'},{id:'ABSTAIN',text:'弃权'},{id:'BACK',text:'回退'},{id:'ABACK_TO_STARTOR',text:'回退发起人'}]"></div>
						</td>
						<td>
							<textarea id="opinion${taskUser.taskId}" class="mini-textarea" rows="3" cols="60" name="opinion"></textarea>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
		<div class="caption">输入变量值</div>
		<div id="varGrid" class="mini-datagrid" 
          	allowCellEdit="true" allowCellSelect="true" ondrawgroup="onDrawGroup"
           	url="${ctxPath}/bpm/core/bpmTestCase/getBySolIdTestId.do?solId=${bpmTestCase.bpmTestSol.solId}&testId=${bpmTestCase.testId}"
			idField="Id" showPager="false">
			<div property="columns">
				<div type="indexcolumn" width="20"></div>
				<div field="nodeName" width="120" headerAlign="center" >节点名称</div>
				<div field="name" name="name" width="100" headerAlign="center">变量名称</div>
				<div field="key" name="key" width="100" headerAlign="center">变量key</div>
				<div field="type" name="type" width="100" headerAlign="center">类型</div>
				<div field="defVal" name="defVal" width="100" headerAlign="center">变量值
					<input property="editor" class="mini-textbox" style="width:100%;"/>
				</div>
			</div>
		</div>
	</div>
	<script text="text/javascript">
		mini.parse();
		var grid=mini.get('varGrid');
		grid.groupBy('nodeName');
		grid.load();
		function onDrawGroup(e) {        
	        e.cellHtml = '['+e.value+']变量';
	    }
		
		function expandBpmImage(){
			$("#bpmImageTr").toggle();
		}
		//显示流程明细
		function showBpmInstDetail(instId){
			_OpenWindow({
				title:'流程实例明细',
				url:'${ctxPath}/bpm/core/bpmInst/get.do?pkId='+instId,
				width:650,
				height:450
			});
		}
		
		function showTaskInfo(taskId){
			_OpenWindow({
				title:'流程任务明细',
				url:'${ctxPath}/bpm/core/bpmTask/get.do?pkId='+taskId,
				width:650,
				height:450
			});
		}
		
		function changeStartUser(){
			var startUserId=mini.get('startUserId');
			_UserDlg(true,function(user){
				startUserId.setValue(user.userId);
				startUserId.setText(user.fullname);
			});
		}
		
		//执行下一步
		function doNext(){
			
			var cmdData=[];
			var taskName=null;
			$('input[name="taskId"]').each(function(){
				var taskId=$(this).val();
				var taskName=mini.get('taskName'+taskId).getValue();
				var taskUserId=mini.get('taskUserId'+taskId);
				
				var userId=null;
				if(taskUserId.getValue()!=''){
					userId=taskUserId.getValue();
				}
				if(userId==null && taskUserId.getData().length>0){
					userId=taskUserId.getData()[0].userId;
				}
				if(userId==null){
					taskName=taskName;
					return;
				}
				
				var jumpType=null;
				var checkStatus=mini.get('checkStatus'+taskId);
				jumpType=checkStatus.getValue();
				if(jumpType==''){
					jumpType='AGREE';
				}
				var opinion=mini.get('opinion'+taskId).getValue();
				cmdData.push({
					taskId:taskId,
					userId:userId,
					jumpType:jumpType,
					opinion:opinion
				});
			});
			var vars=[];
			for(var i=0;i<grid.getTotalCount();i++){
				var row=grid.getRow(i);
				vars.push({
					name:row.key,
					type:row.type,
					value:row.defVal
				});
			}
			if(taskName!=null){
				alert('任务['+taskName+']没有配置执行人!');
				return;
			}
			
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmTestCase/runNext.do?testId=${bpmTestCase.testId}',
				method:'POST',
				data:{
					cmdData:mini.encode(cmdData),
					vars:mini.encode(vars)
				},
				success:function(result){
					location.reload();
				}
			});
		}
		
		//模拟执行
		function onRun(){
			
			var startUserId=mini.get('startUserId').getValue();
			if(startUserId==''){
				return;
			}
			var vars=[];
			for(var i=0;i<grid.getTotalCount();i++){
				var row=grid.getRow(i);
				vars.push({
					name:row.key,
					type:row.type,
					value:row.defVal
				});
			}
			
			var data={
				startUserId:startUserId,
				vars:mini.encode(vars)
			};
			
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmTestCase/run.do?testId=${bpmTestCase.testId}',
				method:'POST',
				data:data,
				success:function(result){
					if(result.success){
						location.reload();
					}
				}
			});
		}
	</script>
	
</body>
</html>