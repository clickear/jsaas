<%-- 
    Document   : 流程任务列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>流程任务列表管理</title>
<%@include file="/commons/list.jsp"%>
<script type="text/javascript" src="${ctxPath}/scripts/form/customFormUtil.js"></script>
<style type="text/css">
	.Bar{ 
		position: relative; 
		width: 96%; 
	 	border: 1px solid green; 
		padding: 1px;
	}
	.Bar div{
	 	display: block; 
		position: relative;
		background:#00F;
		color: #333333; 
		height: 20px; 
		line-height: 20px;
	}
	
	.Bar div span{ 
		position: absolute; 
		width: 96%; 
		text-align: center; 
	}
	
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>

</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
		    <div 
		    	title="流程方案分类" 
		    	region="west" 
		    	width="200"  
		    	showSplitIcon="true"
		    	showCollapseButton="false"
		    	showProxy="false"
		    	class="layout-border-r"
	    	>
		         <div 
		         	id="systree" 
		         	class="mini-tree" 
		         	url="${ctxPath}/bpm/core/bpmTask/getTaskTree.do" 
		         	style="width:100%; height:100%;" 
					showTreeIcon="true" 
					textField="name" 
					idField="treeId" 
					resultAsTree="false" 
					parentField="parentId" 
					expandOnLoad="true"
	                onnodeclick="treeNodeClick"
                >        
	            </div>
		    </div>
		    <div showCollapseButton="false" region="center">	
				<div class="titleBar">
					<ul>
						<li>
							<a class="mini-button" iconCls="icon-user"  onclick="batchClaimUsers">分配用户</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-Relieve-user"  onclick="batchUnClaimUsers">解除用户</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-commute" onclick="communicateTask">消息沟通任务</a>
						</li>
						<li class="clearfix"></li>
					</ul>
					<div class="searchBox">
						<form id="searchForm" class="text-distance" style="display: none;">						
							<ul>
								<li>
									<span class="text">任务　ID</span>
									<input class="mini-textbox" style="width:150px" name="Q_ID__S_EQ" onenter="onSearch"/>
								</li>
								<li>
									<span class="text">执  行  人</span>
									<input class="mini-buttonedit" style="width:150px" allowInput="false"  name="Q_ASSIGNEE__S_EQ" onbuttonclick="selectUser"/>
								</li>
								<li>
									<span class="text">事　　项</span>
									<input class="mini-textbox" name="Q_DESCRIPTION__S_LK" onenter="onSearch"/>
								</li>
								<li class="clearfix"></li>
							</ul>
							<ul>
								<li>
									<span class="text">审批环节</span>
									<input class="mini-textbox" name="Q_name__S_LK"  onenter="onSearch"/>
								</li>
								<li>
									<span class="text">方  案  名</span>
									<input class="mini-textbox" name="Q_SOL_NAME__S_LK"  onenter="onSearch"/>
								</li>
								<li>
									<span class="text">创建时间</span>
									<input class="mini-datepicker mini-textbox" name="Q_CREATE_TIME__D_GE"  onenter="onSearch"/>
									至
									<input class="mini-datepicker mini-textbox" name="Q_CREATE_TIME__D_LE"  onenter="onSearch"/>
								</li>
								<li class="searchBtnBox">
									<input class="_search" type="button" value="搜索" onclick="onSearch()">
									<input class="_reset" type="button" value="清空查询" onclick="onClear()">
								</li>
								<li class="clearfix"></li>
							</ul>
						</form>	
						<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
							<i class="icon-sc-lower"></i>
						</span>
					</div>
				</div>	
				
				<div class="mini-fit">
					<div 
						id="datagrid1" 
						class="mini-datagrid" 
						style="width: 100%; height: 100%;" 
						allowResize="false"
						url="${ctxPath}/bpm/core/bpmTask/getAllTasks.do" 
						idField="id" 
						multiSelect="true" 
						showColumnsMenu="true" 
						sizeList="[5,10,20,50,100,200,500]"
						pageSize="20" 
						allowAlternating="true" 
						>
						<div property="columns">
							<div type="indexcolumn" width="20">序号</div>
							<div type="checkcolumn" width="20"></div>
							<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
							<div field="description" sortField="description_" width="180" headerAlign="center" allowSort="true">事项</div>
							<div field="name" sortField="name_" width="80" headerAlign="center" allowSort="true">审批环节</div>
							<div field="assigneeNames"  width="50" headerAlign="center" allowSort="true">执行人</div>
							<div field="stayTime" width="50" headerAlign="center" >停留时间</div>
							<!-- div field="agentUserId"  sortField="agent_user_id_"width="50" headerAlign="center" allowSort="true">代理人</div-->
							<div field="createTime" sortField="create_time_" width="60" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">创建时间</div>
							<div field="dueDate" sortField="due_date_" width="60" headerAlign="center" allowSort="true">到期时间</div>
							<div field="overtime"  sortField="overtime_" width="60" headerAlign="center" allowSort="true" renderer="onOvertimeRenderer">超时信息</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" 
		entityName="com.redxun.bpm.core.entity.BpmTask" winHeight="450" winWidth="700" entityTitle="流程任务"
		baseUrl="bpm/core/bpmTask" />
	<script type="text/javascript">
	
		function onOvertimeRenderer(e){
			var record = e.record;
			var overtime=record.overtime;
			var rtnString='';
			if(overtime){
				var overtimeArray=JSON.parse(overtime);
				if(overtimeArray.length>1){//如果有多个超时配置人员时长
					var title='';
					for(var i=0;i<overtimeArray.length;i++){
						title+=overtimeArray[i].belong+" 于"+overtimeArray[i].time+"过期,"+overtimeArray[i].timeconfig+"共剩"+overtimeArray[i].timezone+"&#13;";
					}
					rtnString='<span class="overtimeCls" style="color:#8968CD" title="'+title+'" >&nbsp;&nbsp;❁&nbsp;&nbsp; </span>';
				}else if(overtimeArray.length==1){//如果只有一个
					rtnString="于"+overtimeArray[0].time+"过期,共剩"+overtimeArray[0].timezone;
				}else{
					rtnString="无人处理";
				}
			}else{
				rtnString='<span style="color:#6C7B8B">未配置</span>';
			}
	        return rtnString;
		}
	
		function treeNodeClick(e){
	   		var node=e.node;
	   		grid.setUrl(__rootPath+'/bpm/core/bpmTask/getAllTasks.do?treeId='+node.treeId);
	   		grid.load();
	   	}

		function selectUser(e){
			var btn=e.sender;
			_UserDlg(true,function(user){
				btn.setValue(user.userId);
				btn.setText(user.fullname);
			});
		}
		
		function onClear(){
			$("#searchForm")[0].reset();
			grid.setUrl("${ctxPath}/bpm/core/bpmTask/getAllTasks.do");
			grid.load();
		}
	
		function onSearch(){
			var formData=$("#searchForm").serializeArray();
			grid.load(formData);
		}
       	//行功能按钮
        function onActionRenderer(e) {
            var record = e.record;
            var pkId = record.pkId;
            
            var uid=record._uid;
            var taskId=record.id;
            var taskType=record.taskType;
            var taskJson=record.rightJson.task;

            var s= '';

            for(var i=0;i<taskJson.length;i++){
          			var json=taskJson[i];
          			s+=getByJson(record,json);
          			
          		}
        
            return s;
        }
        	
        	
       	function getByJson(record,json){
            var pkId = record.pkId;
            var uid=record._uid;
            var name=record.name;
       		var str="";
       		switch(json.key){
       			case "intervene":
       				if(json.val){
       					str=' <span class="icon-property" title="干预" onclick="opRow(\'' + uid + '\')"></span>';
       				}
       				break;
       			case "handle":
       				if(json.val){
       					str='<span class="icon-handle" title="办理" onclick="handTask(\'' + uid + '\')"></span>';
       				}
       				break;
       		}
       		return str;
       	}
        	
	       
        	
        function opRow(uid){
       		var row=grid.getRowByUID(uid);
       		_OpenWindow({
       			iconCls:'icon-group',
       			title:'待办干预['+row.description+']',
       			url:__rootPath+'/bpm/core/bpmInst/operator.do?taskId='+row.id,
       			max:"true",
       			ondestroy:function(action){
       				if(action!='ok')return;
       				grid.load();
       			}
       		});
       	}
       	
       	function taskDetail(){
			var rows=grid.getSelecteds();
        	if(rows.length==0){
        		alert('请选择任务行！');
        		return;
        	}
       		_OpenWindow({
       			title:rows[0].description,
       			url:__rootPath+'/bpm/core/bpmTask/get.do?mgr=true&pkId='+rows[0].pkId,
       			height:450,
       			width:680,
       			max:true
       		});
       	}
        	
        grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
            //优先级
			if(field=='priority'){
				var s='<div class="Bar"><div style="width:'+value+'%;"><span></span></div></div>';
				e.cellHtml= s;
			}
            if(field=='description'){
            	if(value){
            		var str= '<a href="javascript:handTask(\'' + record._uid + '\')">';
            		if(record.taskType=='CMM'){
            			str+='<img src="'+__rootPath+'/styles/icons/commute.png"> ';
            		}
            		e.cellHtml=str+record.description+'</a>';
            	}else{
            		e.cellHtml='<a href="javascript:handTask(\'' + record._uid + '\')">'+record.name+'</a>';
            	}
            }
            if(field=='dueDate'){
            	if(value){
            		var c=new Date();
            		if(c<value){
            			e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm") + ",距到期还有" + value.diff(c) ;
            		}else{
            			e.cellHtml = "<span style='color:red;font-weight:bold'>"+mini.formatDate(value, "yyyy-MM-dd HH:mm") +",超时:"+ c.diff(value) +"</span>";
            		}
            	}else{
            		e.cellHtml='<span style="color:green">不限制</span>';
            	}
            }
           
        });
	   
        
        
	        
        function batchClaimUsers(){
        	var rows=grid.getSelecteds();
        	
        	if(rows.length==0){
        		alert('请选择任务行！');
        		return;
        	}
        	
        	var taskIds=[];
        	for(var i=0;i<rows.length;i++){
        		taskIds.push(rows[i].id);	
        	}
        	
        	_UserDlg(true,function(user){
        		_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmTask/batchClaimUsers.do',
	        		data:{
	        			taskIds:taskIds.join(','),
	        			userId:user.userId
	        		},
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
        	});
        }
	        
        function batchUnClaimUsers(){
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
        }
	        
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
				width : 812,
				height : 435,
				iconCls:'icon-newMsg',
				onload:function(){
					var iframe = this.getIFrameEl();
					iframe.contentWindow.setSubject(s);
				}
			});
		}
	    
	    //更改执行路径
	    function changePath(taskId,taskType){
        	if(taskType=='CMM'){
        		alert('该任务为沟通任务，不允许更改执行路径！');
        		return;
        	}
        	
	    	_OpenWindow({
	    		title:'更改执行路径',
	    		url:__rootPath+'/bpm/core/bpmTask/toChangePath.do?taskId='+taskId,
	    		width:750,
	    		height:500,
	    		ondestroy:function(action){
        			if(action!='ok') return;
        			grid.load();
        		}
	    	});
	    }
        </script>

		
</body>
</html>