<%-- 
    Document   : 任务提示消息列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.bpm.core.entity.TaskTipMsg" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/taskTipMsg/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="senderId" width="120" headerAlign="center"
					allowSort="true">发送者姓名</div>
				<div field="senderTime" width="120" headerAlign="center"
					allowSort="true">发送时间</div>
				<div field="receiverId" width="120" headerAlign="center"
					allowSort="true">接收者姓名</div>
				<div field="subject" width="120" headerAlign="center"
					allowSort="true">主题</div>
				<div field="content" width="120" headerAlign="center"
					allowSort="true">内容</div>
				<div field="linked" width="120" headerAlign="center"
					allowSort="true">链接</div>
				<!--  <div field="sendbackStatus" width="120" headerAlign="center"
					allowSort="true">返回状态</div>
				<div field="shortContent" width="120" headerAlign="center"
					allowSort="true">简介</div>
				<div field="status" width="120" headerAlign="center"
					allowSort="true">状态</div>
				<div field="importantStatus" width="120" headerAlign="center"
					allowSort="true">主要状态</div>
				<div field="msgStatus" width="120" headerAlign="center"
					allowSort="true">消息状态</div>
				<div field="readTime" width="120" headerAlign="center"
					allowSort="true">阅读时间</div>
				<div field="genTime" width="120" headerAlign="center"
					allowSort="true">资料时间</div>-->
				<div field="isinvalid" width="120" headerAlign="center"
					allowSort="true">撤销</div>
				<!-- <div field="incalidTime" width="120" headerAlign="center"
					allowSort="true">撤销时间</div>-->
			</div>
		</div>
	</div>

	<script type="text/javascript">
	//将html标签解析为miniui控件。
	mini.parse();			
        	//行功能按钮
        
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	var grid=mini.get("datagrid1");
        	grid.load();
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        //column = e.column,
		        field = e.field,
		        value = e.value;
	            
	            if(field=='linked'){
	            	
	           		
	            	if(value){
	         
	            		e.cellHtml= '<a href="${ctxPath}//bpm/core/bpmTask/toStart.do?taskId='+record.taskId+'">'+record.linked+'</a>';
	            	}
	            }
	            
	            if(field=='senderId'){ //获取id，控件自动对该id对应os_user表的userid对应查询出相应的人名
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	            
	            if(field=='receiverId'){ //获取id，控件自动对该id对应os_user表的userid对应查询出相应的人名
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	            
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.bpm.core.entity.TaskTipMsg" winHeight="450"
		winWidth="700" entityTitle="任务提示消息"
		baseUrl="bpm/core/taskTipMsg" />
</body>

</html>