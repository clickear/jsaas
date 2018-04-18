<%-- 
    Document   : 流程任务列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>我的流程任务列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.Bar  { 
		position: relative; 
		width: 96%; 
	 	border: 1px solid green; 
		padding: 1px;
	}
	.Bar div {
	 	display: block; 
		position: relative;
		background:#00F;
		color: #333333; 
		height: 20px; 
		line-height: 20px;
	}
	
	.Bar div span { position: absolute; width: 96%; text-align: center; font-weight: bold; }
</style>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar">
	    <table style="width:100%;">
	        <tr>
		        <th style="width:100%;" class="mini-process-top">
		           <!-- <a class="mini-button" iconCls="icon-lock" plain="true" onclick="batchClaimUsers()">锁定任务</a>
		            <a class="mini-button" iconCls="icon-unlock" plain="true" onclick="batchUnClaimUsers()">解锁任务</a>
		            <a class="mini-button" iconCls="icon-transfer" plain="true" onclick="batchTransferTasks()">转发任务</a> -->
		            <a class="mini-button" iconCls="icon-commute" plain="true" onclick="communicateTask">沟通任务</a>
		           	<a class="mini-button" iconCls="icon-search" onclick="onSearch()">查询</a>
				    <a class="mini-button" iconCls="icon-clear"  onclick="onClearList(this)">清空</a>
		        </th>
	        </tr>
	        <tr>
	        	<td>
	        		<form id="searchForm"  class="text-distance">
		        		<div>
		        			<span class="text">创建时间从:</span><input name="Q_CREATE_TIME__D_GE" class="mini-datepicker" />至<input name="Q_CREATE_TIME__D_LE" class="mini-datepicker"/>
			        		<span class="text">审批环节:</span><input name="Q_name_S_LK" class="mini-textbox"/>
			        		<span class="text">事项:</span><input name="Q_description_S_LK" class="mini-textbox"/>
			        		<input type="hidden" name="crsf_token" class="mini-hidden"  value="${sessionScope.crsf_token}"/>			        		
		        		</div>	        	
				    </form>
	        	</td>
	        </tr>
	    </table>
	</div>
	
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" onrowdblclick ="showTask(e)"
			url="${ctxPath}/bpm/core/bpmTask/myTasks.do?Q_nodeId_S_EQ=${nodeId}" idField="id" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]"
			pageSize="20" allowAlternating="true" >
			<div property="columns">
				<div type="indexcolumn" width="20">序号</div>
				<!-- <div type="checkcolumn" width="20"></div> -->
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="description" sortField="description_" width="180" headerAlign="center" allowSort="true">事项</div>
				<div field="name" width="80" headerAlign="center">当前节点</div>
				<!-- <div field="owner" sortField="OWNER_" width="50" headerAlign="center" allowSort="true">所属人</div> -->
				<div field="assignee" width="50" headerAlign="center">处理人</div>
				<div field="createTime" sortField="CREATE_TIME_" width="60" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">创建时间</div>
				<!-- <div field="agentUserId" width="50" headerAlign="center" allowSort="true">代理人</div>
				<div field="priority" sortField="PRIORITY_" width="60" headerAlign="center" allowSort="true">优先级</div>
				<div field="createTime" sortField="CREATE_TIME_" width="60" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">创建时间</div>
				<div field="dueDate" sortField="DUE_DATE_" width="60" headerAlign="center" allowSort="true">到期时间</div> -->
			</div>
		</div>
	</div>
	
	<redxun:gridScript gridId="datagrid1" 
		entityName="com.redxun.bpm.core.entity.BpmTask" winHeight="450" winWidth="700" entityTitle="流程任务"
		baseUrl="bpm/core/bpmTask" />
	<script type="text/javascript">
	
			function showTask(e){
				var record = e.record;
				var uid=record._uid;
				handleRow(uid);
			}
	
			function onSearch(){
				var formData=$("#searchForm").serializeArray();
				var data=jQuery.param(formData);
				grid.setUrl('${ctxPath}/bpm/core/bpmTask/myTasks.do?'+data);
				grid.load();
			}
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var s= '<span class="icon-handle" title="办理" onclick="handleRow(\'' + uid + '\')"></span>';
	            return s;
	        } 
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
	            
		        //column = e.column,
		        field = e.field,
		        value = e.value;
	            //优先级
				if(field=='priority'){
					var s='<div class="Bar"><div style="width:'+value+'%;"><span></span></div></div>';
					e.cellHtml= s;
				}
	            /*
/* 	            if(field=='description'){
	            	if(value){
	            		e.cellHtml= '<a href="javascript:handleRow(\'' + record._uid + '\')">'+record.description+'</a>';
	            	}else{
	            		e.cellHtml='<a href="javascript:handleRow(\'' + record._uid + '\')">'+record.name+'</a>';
	            	}
	            }*/
	            
				 if(field=='description'){
		            	if(value){
		            		var str= '';
		            		if(record.taskType=='CMM'){
		            			str+='<img src="'+__rootPath+'/styles/icons/commute.png"> ';
		            		}
		            		e.cellHtml=str+record.description;
		            	}else{
		            		e.cellHtml=record.name;
		            	}
		         }
	            
	            if(field=='dueDate'){
	            	if(value){
	            		var c=new Date();
	            		if(c<value){
	            			e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            		}else{
	            			e.cellHtml = "<span style='color:red;font-weight:bold'>"+mini.formatDate(value, "yyyy-MM-dd HH:mm")+"</span>";
	            		}
	            	}else{
	            		e.cellHtml='<span style="color:green">不限制</span>';
	            	}
	            }
	            if(field=='assignee'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	            if(field=='owner'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"</a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	            if(field=='agentUserId'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"</a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	        });
	        //处理任务
	        function handleRow(uid){
	        	var row=grid.getRowByUID(uid);
	        	//var ref=window.open(__rootPath+'/bpm/core/bpmTask/toStart.do?taskId='+row.id);
	               
	            
	        	 _OpenWindow({
	        		title:'任务办理-'+row.description,
	        		height:400,
	        		width:780,
	        		max:true,
	        		url:__rootPath+'/bpm/core/bpmTask/toStart.do?taskId='+row.id,
	        		ondestroy:function(action){
	        			if(action!='ok') return;
	        			grid.load();
	        		}
	        	}); 
	        }
	        
	        //指转发任务
	        /* function batchTransferTasks(){
				var rows=grid.getSelecteds();
	        	
	        	if(rows.length==0){
	        		alert('请选择任务行！');
	        		return;
	        	}
	        	
	        	_OpenWindow({
	        		title:'任务转发',
	        		url:__rootPath+'/bpm/core/bpmTask/transfer.do?taskIds='+_GetIds(rows),
	        		height:350,
	        		width:800,
	        		ondestroy:function(action){
	        			if(action!='ok')return;
	        			grid.load();
	        		}
	        	});
	        } */
	        
	        grid.on("update",function(e){
	        	_LoadUserInfo();
	        });
	        
	        /* function batchClaimUsers(){
	        	var rows=grid.getSelecteds();
	        	
	        	if(rows.length==0){
	        		alert('请选择任务行！');
	        		return;
	        	}
	        	
	        	var taskIds=[];
	        	for(var i=0;i<rows.length;i++){
	        		if(!rows[i].assignee){
	        			taskIds.push(rows[i].id);	
	        		}
	        	}

        		_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmTask/batchClaimUsers.do',
	        		data:{
	        			taskIds:taskIds.join(',')
	        		},
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        	
	        } */
	        
	        /* function batchUnClaimUsers(){
				var rows=grid.getSelecteds();
	        	
	        	if(rows.length==0){
	        		alert('请选择任务行！');
	        		return;
	        	}
	        	var taskIds=[];
	        	for(var i=0;i<rows.length;i++){
	        		taskIds.push(rows[i].id);
	        	}
        		_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmTask/batchUnClaimUsers.do',
	        		data:{
	        			taskIds:taskIds.join(',')
	        		},
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        } */
	        
	      //沟通用户
			function communicateTask() {
				var rows = grid.getSelecteds();
				var pkIds = [];
				var s = "";

				if (rows.length == 0) {
					alert('请选择任务行！');
					return;
				}
				if (rows.length > 0) {
					s = s+"<ul>";
					for (var i = 0; i < rows.length; i++) {
						s = s + "<li><a class='subject' href='javascript:void(0);' href1='rootPath/bpm/core/bpmInst/inform.do?actInstId=" + rows[i].procInstId + "'>" + rows[i].description + '</a></li>';
					}
					s = s+"</ul>";
					
				}
				
				_OpenWindow({
					url : "${ctxPath}/oa/info/infInnerMsg/send.do?pkId=" + pkIds,
					title : "沟通任务",
					width : 750,
					height : 400,
					iconCls:'icon-newMsg',
					onload:function(){
						var iframe = this.getIFrameEl();
						iframe.contentWindow.setSubject(s);
					}
				});
			}
        </script>

		
</body>
</html>