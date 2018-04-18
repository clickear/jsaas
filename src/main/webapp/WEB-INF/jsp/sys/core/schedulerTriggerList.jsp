<%-- 
    Document   : 定时器列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>定时器列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar" >
		<a class="mini-button" iconCls="icon-add" onClick="addTrigger">添加计划</a>
		<a class="mini-button" iconcls="icon-remove"  onclick="getJog()">返回</a>
	</div>
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/core/scheduler/jobTriggersJson.do?jobName=${f:urlEncode(jobName)}"
			multiSelect="true" showColumnsMenu="true" showPager="false"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true"  emptyText="请添加计划.." showEmptyText="false">
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="jobName" width="120" headerAlign="center" allowSort="true">任务名称</div>
				<div field="triggerName" width="120" headerAlign="center" allowSort="true">计划名称</div>
				<div field="description" width="120" headerAlign="center" allowSort="true">备注</div>
				<div  renderer="stateRenderer" width="120" headerAlign="center" allowSort="true">方法</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var jobName="${f:urlEncode(jobName)}";
	
		mini.parse();
	 	var grid = mini.get('datagrid1');
	 	grid.load();
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var state = record.state;
			var triggerName=record.triggerName;
			var result="";
			
			if(state == "NORMAL"){
				result+='<span onclick="toggleTrigger(\''+triggerName+'\')"  class="icon-abstain" title="禁用"></span>';
			}
			if(state == "PAUSED"){
				result+='<span onclick="toggleTrigger(\''+triggerName+'\')" class="icon-agree"  title="启用"></span>';
			}
			result+='<span onclick="getLog(\''+triggerName+'\')" class="icon-Logmsg"  title="日志"></span>';
			
			result+='<span onclick="removeTrigger(\''+triggerName+'\')" class="icon-remove"  title="删除"></span>';
			
			

			return result;
		}
		
		function stateRenderer(e){
			var record = e.record;
			var state = record.state;
			var arr = [ { 'key' : 'NORMAL','value' : '启用','css' : 'green'}, 
			            {'key' : 'PAUSED', 'value' : '禁用','css' : 'red'}, 
			            {'key' : 'ERROR','value' : '执行出错','css' : 'red'}, 
			            {'key' : 'COMPLETE','value' : '已完成','css' : 'green'} ,
			            {'key' : 'BLOCKED','value' : '正在执行','css' : 'green'} ,
			            {'key' : 'NONE','value' : '未启动','css' : 'red'}];
			
			return $.formatItemValue(arr,state);
		}
		
		function addTrigger(){
			_OpenWindow({
				url : __rootPath + "/sys/core/scheduler/triggerAdd.do?name=" +jobName ,
				title : "计划明细",
				width : 820,
				height : 600,
				max : false,
				ondestroy : function(action) {
					grid.load();
				}
			});
		}
		
		function toggleTrigger(triggerName) {
			var url=encodeURI( __rootPath+ "/sys/core/scheduler/toggleTriggerRun.do?name=" +triggerName);
			_SubmitJson({
				url : url,
				method : 'POST',
				success : function(text) {
					grid.load();
				}
			});
		}
	
		function removeTrigger(triggerName) {
			
			if(!confirm("确认删除此项吗?")) return;
			
			var url=encodeURI( __rootPath+ "/sys/core/scheduler/delTrigger.do?name=" +triggerName);
			
			_SubmitJson({
				url : url,
				method : 'POST',
				success : function(text) {
					grid.load();
				}
			});
		}
		
		function getLog(triggerName){
			var url=encodeURI(__rootPath+ "/sys/core/scheduler/logList.do?TRIGGER_NAME_=" +triggerName);
			location.href=url;
		}
		
		function getJog(){
			location.href=__rootPath+ "/sys/core/scheduler/jobList.do"
		}
		
	</script>
	
</body>
</html>