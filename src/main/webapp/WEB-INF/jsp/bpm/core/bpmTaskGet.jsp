<%-- 
    Document   : 流程任务明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<html>
<head>
<title>流程任务明细</title>
<%@include file="/commons/get.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>

<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.img.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
<script language="javascript" src="${ctxPath}/scripts/jquery/plugins/jQuery.print.js"></script>

<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/bpmAnimate.js" type="text/javascript"></script>
<style type="text/css">
.Bar {
	position: relative;
	width: 96%;
	border: 1px solid green;
	padding: 1px;
}

.Bar div {
	display: block;
	position: relative;
	background: #00F;
	color: #333333;
	height: 20px;
	line-height: 20px;
}

.Bar div span {
	position: absolute;
	width: 96%;
	text-align: center;
	font-weight: bold;
}
</style>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-commute" plain="true" onclick="doCommute();">沟通</a>
		</div>
	</rx:toolbar>
	<div class="mini-fit form-outer">
		<div class="mini-tabs" style="width: 100%; height: 100%;">

			<div title="任务基本信息">

				<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
					<caption>任务信息</caption>
					<tr>
						<th>事项标题</th>
						<td colspan="3"><a href="javascript:;" onclick="showBpmInstInfo('${bpmTask.procInstId}')">${bpmTask.description}</a></td>
					</tr>
					<tr>
						<th>流程解决方案</th>
						<td colspan="3" ><a href="javascript:;" onclick="showBpmSolutionInfo('${bpmSolution.solId}')">${bpmSolution.name}</a></td>
					</tr>
					<tr>
						<th>流程定义</th>
						<td colspan="3"><a href="javascript:;" onclick="showBpmDefInfo('${bpmTask.procDefId}')">${bpmDef.subject}</a></td>
					</tr>
					<tr>
						<th>任务名称</th>
						<td>${bpmTask.name}</td>
						<th>任务定义Key</th>
						<td>${bpmTask.taskDefKey}</td>
					</tr>
					<tr>
						<th>代理状态</th>
						<td>${bpmTask.delegation}</td>
						<th>优先级</th>
						<td>
							<div class="Bar">
								<div style="width:${bpmTask.priority}%">
									<span></span>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${bpmTask.createTime}" /></td>
						<th>到期时间</th>
						<td><c:choose>
								<c:when test="${not empty bpmTask.dueDate}">
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${bpmTask.dueDate}" />
								</c:when>
								<c:otherwise>
									<span style="color: green">无限制</span>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th>挂起状态</th>
						<td>${bpmTask.suspensionState}</td>
						<th>任务ID</th>
						<td>${bpmTask.id}</td>
					</tr>
				</table>
				<br />
				<table class="mini-toolbar mini-toolbar-bottom" style="width: 100%">
					<tr>
						<td><a class="mini-button" iconCls="icon-save" plain="true" onclick="saveTaskUsers()">保存任务人员</a></td>
					</tr>
				</table>
				<form id="userForm">
					<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
						<caption>任务执行人</caption>
						<tr>
							<th>任务所属人</th>
							<td><input id="owner" name="owner" class="mini-buttonedit" value="${owner.userId}" text="${owner.fullname}" onbuttonclick="changeOwner()" selectOnFocus="true" allowInput="false" style="width: 90%" /></td>
							<th>任务执行人</th>
							<td><input id="assignee" name="assignee" class="mini-buttonedit" value="${assignee.userId}" text="${assignee.fullname}" onbuttonclick="changeAssignee()" selectOnFocus="true" allowInput="false" style="width: 90%" /></td>
						</tr>
						<tr>
							<th>候选用户</th>
							<td><input id="userLinks" name="userLinks" allowInput="false" class="mini-textboxlist" style="width: width:80%;" value="${canUserIds}" text="${canUserNames}" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addCanUsers()">添加</a></td>
							<th>候选用户组</th>
							<td><input id="groupLinks" name="groupLinks" allowInput="false" class="mini-textboxlist" style="width: width:80%;" value="${canGroupIds}" text="${canGroupNames}" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addCanGroups()">添加</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div title="流程示意图">
				<img src="${ctxPath}/bpm/activiti/processImage.do?actInstId=${bpmTask.procInstId}" usemap="#imgHref"/>
				<imgArea:imgAreaScript actInstId="${bpmTask.procInstId}"></imgArea:imgAreaScript>
			</div>
			<div title="业务数据">
				<div id="jsonview">${bpmFormInst.jsonData}</div>
			</div>
			<div title="流程变量">
				<table class="table-view" cellpadding="0" cellspacing="1" style="width:100%">
					<thead>
						<tr>
							<th width="120">Key</th>
							<th width="80">类型</th>
							<th width="*">值</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${varInstList}" var="var">
						<tr>
							<td>${var.key}</td>
							<td>
								${var.type}
							</td>
							<td>${var.val}</td>
						</tr>			
					</c:forEach>
					</tbody>
				</table>
			</div>

			<c:if test="${not empty formConfig}">
				<c:choose>
					<c:when test="${formConfig.formType=='ONLINE-DESIGN'}">
						<div title="任务表单">
							<div class="mini-toolbar" style="border-bottom: none;">
								<a class="mini-button" iconCls="icon-print" onclick="formPrint" plain="true">打印</a>
							</div>
							<div id="taskForm"></div>
							<script type="text/javascript">
								$(function(){
									//解析动态表单
									reqFormParse({
										taskId:'${bpmTask.id}',
										containerId:'taskForm'
									});
								});
							</script>
						</div>
					</c:when>
					<c:otherwise>
						<div title="任务表单" url="${formConfig.formUrl}"></div>
					</c:otherwise>
				</c:choose>
			</c:if>
			<div title="流程流转记录" url="${ctxPath}/bpm/core/bpmNodeJump/insts.do?actInstId=${bpmTask.procInstId}"></div>
		</div>

	</div>
	<!-- end of mini tabs -->
	</div>
	<!-- end of mini-fit -->
	<rx:detailScript baseUrl="bpm/core/bpmTask" entityName="com.redxun.bpm.core.entity.BpmTask" formId="form1" />
	<script type="text/javascript">
		var userForm=new mini.Form('userForm');
		var taskId='${bpmTask.id}';
		var description = '${bpmTask.description}';
		var list;
		$(function(){
			
			$.ajax({
		          url: "${ctxPath}/bpm/core/bpmRuPath/calculateNode.do",
		          data: {"instId":"${bpmInst.instId}","taskId":"${param['taskId']}"}, 
		          success: function (text) {
		        	  list=text;//把返回的json数据传到list
		        	  var length=text.length;
		        	  cartoon($("[type='startNode']").attr("id"),index);//从开始节点出发
		          }
		      });
			var json=$("#jsonview").html();
			$("#jsonview").html('');
			if(json!=''){
				$("#jsonview").jsonViewer(mini.decode(json));
			}
			
			$("area[type='userTask']").each(function(){
			 	var nodeId=$(this).attr('id');
				$(this).qtip({
					content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: __rootPath+"/bpm/core/bpmNodeJump/byNodeId.do?nodeId="+nodeId+"&taskId=${bpmTask.id}"
		                    })
		                    .then(function(content) {
		                        api.set('content.text', content);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', status + ': ' + error);
		                    });
		                    return '正在加载...'; 
		                }
		            },
		            position: {
		                target: 'mouse', // Position it where the click was...
		                adjust: { mouse: false } // ...but don't follow the mouse
		            },
			    });
		 });
			
		});
		
		function formPrint(){
			_OpenWindow({
				url:__rootPath+'/bpm/form/bpmFormView/print.do?taskId=${bpmTask.id}',
				title:'表单打印',
				width:600,
				height:450,
				max:true
			});
		}
		
		//实例信息明细
		function showBpmInstInfo(procInstId){
			_OpenWindow({
				title:'流程实例明细',
				url:__rootPath+'/bpm/core/bpmInst/get.do?hideRecordNav=true&actInstId='+procInstId,
				height:400,
				width:780
			});
		}
		
		//定义信息明细
		function showBpmDefInfo(procDefId){
			_OpenWindow({
				title:'流程定义明细',
				url:__rootPath+'/bpm/core/bpmDef/get.do?hideRecordNav=true&actDefId='+procDefId,
				height:400,
				width:780
			});
		}
		
		function showBpmSolutionInfo(solId){
			
			_OpenWindow({
				title:'流程解决方案明细',
				url:__rootPath+'/bpm/core/bpmSolution/get.do?pkId='+solId,
				height:430,
				width:780
			});
		}
		
		function changeAssignee(){
			var assignee=mini.get('assignee');
			_UserDlg(true,function(user){
				assignee.setValue(user.userId);
				assignee.setText(user.fullname);
			});
		}
		
		function changeOwner(){
			var owner=mini.get('owner');
			_UserDlg(true,function(user){
				owner.setValue(user.userId);
				owner.setText(user.fullname);
			});
		}
		
		//添加候选用户 
		function addCanUsers(){
			var userLinks=mini.get('userLinks');
			_UserDlg(false,function(users){
				var uIds=[];
				var uNames=[];
				for(var i=0;i<users.length;i++){
					uIds.push(users[i].userId);
					uNames.push(users[i].fullname);
				}
				if(userLinks.getValue()!=''){
					uIds.unshift(userLinks.getValue().split(','));
				}
				if(userLinks.getText()!=''){
					uNames.unshift(userLinks.getText().split(','));	
				}
				userLinks.setValue(uIds.join(','));
				userLinks.setText(uNames.join(','));
			});
		}
		
		//添加候选用户 组
		function addCanGroups(){
			var groupLinks=mini.get('groupLinks');
			
			_GroupDlg(false,function(groups){
				var uIds=[];
				var uNames=[];
				for(var i=0;i<groups.length;i++){
					uIds.push(groups[i].groupId);
					uNames.push(groups[i].name);
				}
				if(groupLinks.getValue()!=''){
					uIds.unshift(groupLinks.getValue().split(','));
				}
				if(groupLinks.getText()!=''){
					uNames.unshift(groupLinks.getText().split(','));	
				}
				groupLinks.setValue(uIds.join(','));
				groupLinks.setText(uNames.join(','));
			});
		}
		
		//保存任务用户
		function saveTaskUsers(){
			var formData=userForm.getData();
			formData.taskId=taskId;
			_SubmitJson({
				url:__rootPath+'/bpm/core/bpmTask/saveTaskUsers.do',
				data:formData,
				method:'POST',
				success:function(text){
					
				}
			});
		}
		
		//沟通任务
		function doCommute() {
			var s = "";
			s = s + "<ul>";
			s = s + "<li><a class='subject' href='javascript:void(0);' href1='rootPath/bpm/core/bpmTask/comunicateGet.do?pkId=" + taskId + "'>" + description + '</a></li>';
			s = s + "</ul>";

			_OpenWindow({
				url : "${ctxPath}/oa/info/infInnerMsg/send.do",
				title : "沟通任务",
				width : 620,
				height : 490,
				iconCls : 'icon-newMsg',
				onload : function() {
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setSubject(s);
				}
			});
		}
	</script>
</body>
</html>