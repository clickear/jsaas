<%-- 
    Document   : 定时器日志列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>定时器日志列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<!-- <div class="mini-toolbar" >
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                        <a class="mini-button" iconcls="icon-remove" plain="true" onclick="removeAll">清空日志</a>
                        <a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
	                    <a class="mini-button" iconCls="icon-cancel" onclick="clearSearch()">清空查询</a>
                    </td>
                  
                </tr>
	                <tr>
	                    <td style="white-space:nowrap;padding:5px;" colspan="2">
	                                                任务名:<input name="Q_JOB_NAME__S_LK"   />
	                                                计划名:<input name="Q_TRIGGER_NAME__S_LK"   />
	                                               开始时间:<input name="Q_START_D_GE" class="mini-datepicker" format="yyyy-MM-dd" />
	                           &nbsp;至&nbsp;<input name="Q_END_D_LE" class="mini-datepicker" format="yyyy-MM-dd" />
	                        
	                    </td>
	                </tr>
            </table>
     </div> -->
     
     
     <div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
			</li>
			<li>
				<a class="mini-button" iconcls="icon-remove" plain="true" onclick="removeAll">清空日志</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						<span>任务名：</span><input class="mini-textbox" name="Q_JOB_NAME__S_LK">
					</li>
					<li>
						<span>计划名：</span><input class="mini-textbox" name="Q_TRIGGER_NAME__S_LK">
					</li>
					<li>
						<span>开始时间：</span><input name="Q_START_D_GE" class="mini-datepicker" format="yyyy-MM-dd" />
					</li>
					<li>
						<span>至</span><input name="Q_END_D_LE" class="mini-datepicker" format="yyyy-MM-dd" />
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="clearSearch()">清空</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
     </div>
     
     
     
     
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid"
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/sys/core/scheduler/logListJson.do" 
			multiSelect="true" 
			showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				
				<div field="jobName" sortField="JOB_NAME_" width="120" headerAlign="center" allowSort="true">任务名称</div>
				
				<div field="triggerName" width="120" headerAlign="center"
					>计划key</div>
				<div field="content" width="120" headerAlign="center"
					>日志内容</div>
				<div dateFormat="yyyy-MM-dd HH:mm:ss"  field="startTime" width="120" headerAlign="center"
					>开始时间</div>
				<div  dateFormat="yyyy-MM-dd HH:mm:ss" field="endTime" width="120" headerAlign="center"
					>结束时间</div>
				<div field="runTime" width="120" headerAlign="center"
					>持续时间</div>
				<div field="status" width="120" headerAlign="center" renderer="onActionRenderer"
					>状态</div>
			</div>
		</div>
	</div>
	
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.job.core.entity.SysQuartzLog" winHeight="450"
		winWidth="700" entityTitle="定时器日志" baseUrl="sys/core/scheduler" />
		
	<script type="text/javascript">
	
		var triggerName="${param.TRIGGER_NAME_}";
		var jobName="${param.JOB_NAME_}";
	
		
		var params={};
		if(triggerName){
			params.TRIGGER_NAME_=triggerName;
		}
		if(jobName){
			params.JOB_NAME_=jobName;
		}
	
		
		var grid = mini.get('datagrid1');
		grid.load(params);
		
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var value = record.status;
			var arr = [ {'key' : "0",'value' : '失败','css' : 'red'}, 
			            {'key' : '1','value' : '成功','css' : 'green'} ];
			return $.formatItemValue(arr,value);
					
			return s;
		}
		//清空日志
		function removeAll() {
			var row = grid.getSelected();
			var pkId =null;
			_SubmitJson({
				url : "${ctxPath}/sys/core/scheduler/cleanLog.do",
				method : 'POST',
				success : function(text) {
					grid.load();
				}
			});
		}
		
		function getJog(){
			location.href=__rootPath+ "/sys/core/scheduler/jobList.do"
		}
	</script>
</body>
</html>