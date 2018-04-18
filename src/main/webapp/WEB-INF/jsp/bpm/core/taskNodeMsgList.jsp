<%-- 
    Document   :任务通知节点消息列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>节点消息列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.bpm.core.entity.TaskNodeMsg" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/taskNodeMsg/listData.do" idField="msgId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="subject" width="120" headerAlign="center"
					allowSort="true">主题</div>
				<div field="content" width="120" headerAlign="center"
					allowSort="true">内容</div>
				<div field="linked" width="120" headerAlign="center"
					allowSort="true" >链接</div>
				<div field="taskNodeName" width="120" headerAlign="center"
					allowSort="true">任务节点名称</div>
					<!--  
				<div field="deployId" width="120" headerAlign="center"
					allowSort="true">部署ID</div>-->
			</div>
		</div>
	</div>

	<script type="text/javascript">
	//将html标签转换成mini控件
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
	     
	        var grid = mini.get("datagrid1");
	        grid.load();
	       //drawcell是自定义单元表格内容
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            
	            if(field=='linked'){
	            	e.cellHtml= '<a href="${ctxPath}/bpm/core/bpmTask/toStart.do?taskId='+record.taskId+'">'+record.linked+'</a>';
	            }
	        });
	        
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.bpm.core.entity.TaskNodeMsg" winHeight="450"
		winWidth="700" entityTitle="任务节点"
		baseUrl="bpm/core/taskNodeMsg" />
</body>
</html>