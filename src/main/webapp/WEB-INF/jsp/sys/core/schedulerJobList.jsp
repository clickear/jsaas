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
	<%-- <div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-add" onClick="addJob">添加</a>				
		<a id="btnStop" class="mini-button" iconCls="icon-no" onClick="switchScheduler('false')" style="display:${isStarted?'blocked':'none'}" >停止定时器</a>
		<a id="btnStart"  class="mini-button" iconCls="icon-start" onClick="switchScheduler('true')" style="display:${!isStarted?'blocked':'none'}">启动定时器</a>		
	</div> --%>
	
	<div class="titleBar">
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-add" onClick="addJob">添加</a>
			</li>
			<li>
				<a id="btnStop" class="mini-button" iconCls="icon-no" onClick="switchScheduler('false')" style="display:${isStarted?'blocked':'none'}" >停止定时器</a>
			</li>
			<li>
				<a id="btnStart"  class="mini-button" iconCls="icon-start" onClick="switchScheduler('true')" style="display:${!isStarted?'blocked':'none'}">启动定时器</a>
			</li>
			<li class="clearfix"></li>
		</ul>
     </div>
	
	
		
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid"
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/sys/core/scheduler/jobListJson.do" 
			multiSelect="true" 
			showColumnsMenu="true" 
			showPager="false"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">任务名称</div>
				<div field="className" width="120" headerAlign="center" allowSort="true">类名</div>
				<div field="description" width="120" headerAlign="center" allowSort="true">备注</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	function addJob() {
		_OpenWindow({
			url : __rootPath + "/sys/core/scheduler/jobEdit.do" ,
			title : "新增任务",
			width : 1024,
			height : 700,
			max : false,
			ondestroy : function(action) {
				if (action == 'ok') {
					grid.reload();
				}
			}
		});
	}
	
	function toggle(start){
		var startObj=$("#btnStart");
		var stopObj=$("#btnStop");
		if(start){
			startObj.hide();
			stopObj.show();
		}
		else{
			startObj.show();
			stopObj.hide();
		}
	}
	
	function jobDetail(name) {
		var url=encodeURI(__rootPath + "/sys/core/scheduler/jobGet.do?name=" +name);
		_OpenWindow({
			url : url ,
			title : "任务明细",
			width : 820,
			height : 600,
			max : false,
			ondestroy : function(action) {
				
			}
		});
	}
	
	function switchScheduler(status) {
		_SubmitJson({
			url : __rootPath+ "/sys/core/scheduler/switchScheduler.do?start=" +status,
			method : 'POST',
			success : function(text) {
				var tmp=status=="true";
				toggle(tmp);
			}
		});
	}
	
	   //渲染时间
  	 function onRenderer(e) {
  		var record = e.record;
		var uid = record._uid;
		var pkId = record.pkId;
		var s = "";
		if (record.status == '运行') {
			s = '<span  class="icon-ok">&nbsp;&nbsp;&nbsp;&nbsp;</span>运行';
		} else {
			s = '<span  class="icon-no">&nbsp;&nbsp;&nbsp;&nbsp;</span>'+record.status;
		}
		return s;
      }
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var name = record.name;
			var s = '<span class="icon-detail" title="明细" onclick="jobDetail(\''+ name+ '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delJob(\''+ name + '\')"></span>'
					+ ' <span class="icon-start" title="执行任务" onclick="runJob(\'' + name + '\')"></span>'
					+ ' <span class="icon-timeinput" title="计划列表" onclick="gotoPlan(\'' + name + '\')" ></span>'
					+ ' <span class="icon-detail" title="日志列表" onclick="getLog(\'' + name + '\')" ></span>';
			return s;
		}
		
		function gotoPlan(jobName){
			location.href=encodeURI( "${ctxPath}/sys/core/scheduler/triggerList.do?jobName="+ jobName);
		}
		
		function getLog(jobName){
			location.href=__rootPath+ "/sys/core/scheduler/logList.do?JOB_NAME_=" +jobName
		}
		
		function delJob(jobName) {
			if (!confirm("确定删除选中记录？")) return;
			
			var url=encodeURI( __rootPath + "/sys/core/scheduler/removeJob.do?jobName=" +jobName);

			_SubmitJson({
				url : url,
				method : 'POST',
				success : function(text) {
					grid.load();
				}
			});
		}
		
		function runJob(jobName) {
			var url=encodeURI( __rootPath + "/sys/core/scheduler/runJob.do?jobName=" +jobName);
			_SubmitJson({
				url : url,
				method : 'POST',
				success : function(text) {
					
				}
			});
		}
		
		
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.job.core.entity.SysQuartzJob" winHeight="450"
		winWidth="700" entityTitle="定时器" baseUrl="job/core/sysQuartzJob" />
</body>
</html>