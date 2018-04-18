<%-- 
    Document   : 流程任务明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="rxTag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>流程任务明细</title>
<script type="text/javascript">
var _enable_openOffice="<%=com.redxun.sys.core.util.SysPropertiesUtil.getGlobalProperty("openoffice")%>";
</script>
<%@include file="/commons/get.jsp"%>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.js" type="text/javascript"></script>

<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>


<script src="${ctxPath}/scripts/common/baiduTemplate.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/customer/mini-custom.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/form/customFormUtil.js" type="text/javascript"></script>

<script src="${ctxPath}/scripts/form/FormCalc.js" type="text/javascript"></script>

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
	<rx:toolbar toolbarId="toolbar1" excludeButtons="prevRecord,nextRecord,remove" />
	<div class="mini-fit">
		<div class="mini-tabs" style="width: 100%; height: 100%;">
			<rxTag:processForm formModels="${formModels}"></rxTag:processForm>
			<script type="text/javascript">
				$(function(){
					//解析表单。
					renderMiniHtml({});
				});
			</script>
			<div title="任务基本信息">

				<table style="width: 100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
					<caption>任务信息</caption>
					<tr>
						<th>事项标题</th>
						<td colspan="3">${bpmTask.description}</a></td>
					</tr>
					<tr>
						<th>流程定义</th>
						<td colspan="3">${bpmDef.subject}</a></td>
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
						<td colspan="3">${bpmTask.suspensionState}</td>
					</tr>
				</table>
				<br />
				<form id="userForm">
					<table style="width: 100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<caption>任务执行人</caption>
						<tr>
							<th>任务所属人</th>
							<td><input id="owner" name="owner" class="mini-textbox" value="${owner.fullname}" readonly= true style="width: 90%" /></td>
							<th>任务执行人</th>
							<td><input id="assignee" name="assignee" class="mini-textbox" value="${assignee.fullname}" readonly= true style="width: 90%" /></td>
						</tr>
						<tr>
							<th>候选用户</th>
							<td><input id="userLinks" name="userLinks" allowInput="false" class="mini-textboxlist" style="width: width:90%;" value="${canUserIds}" text="${canUserNames}" readonly=true /> </td>
							<th>候选用户组</th>
							<td><input id="groupLinks" name="groupLinks" allowInput="false" class="mini-textboxlist" style="width: width:90%;" value="${canGroupIds}" text="${canGroupNames}" readonly=true /> </td>
						</tr>
					</table>
				</form>
			</div>
			<div title="流程示意图">
				<img src="${ctxPath}/bpm/activiti/processImage.do?actInstId=${bpmTask.procInstId}" />
			</div>
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
		//实例信息明细
		function showBpmInstInfo(procInstId){
			_OpenWindow({
				title:'流程实例明细',
				url:__rootPath+'/bpm/core/bpmInst/get.do?hideRecordNav=true&actInstId='+procInstId,
				height:400,
				width:780
			});
		}
		
		
		
		//实例信息明细
		function showBpmDefInfo(procDefId){
			_OpenWindow({
				title:'流程定义明细',
				url:__rootPath+'/bpm/core/bpmDef/get.do?hideRecordNav=true&actDefId='+procDefId,
				height:400,
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
				width : 450,
				height : 400,
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