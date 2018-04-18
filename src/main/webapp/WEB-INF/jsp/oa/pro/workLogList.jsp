<%-- 
    Document   : 日志列表页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>日志列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.oa.pro.entity.workLog"  excludeButtons="popupAttachMenu,detail,edit,popupSearchMenu,popupAddMenu,remove,popupSettingMenu">
	<div class="self-toolbar">
	<a class="mini-button" iconCls="icon-add" plain="true" onclick="addOne()">撰写日志</a>
	</div>
	</redxun:toolbar>
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			 idField="taskId" multiSelect="true" showColumnsMenu="true" onrowdblclick="openDetail(e)"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="35" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="content" width="120" headerAlign="center"  >日志内容</div>
				<div field="startTime" width="120" headerAlign="center"   renderer="onRenderer">开始时间</div>
				<div field="endTime" width="120" headerAlign="center"   renderer="onRenderer">结束时间</div>
				<div field="status" width="120" headerAlign="center"  >状态</div>
				<div field="last" width="120" headerAlign="center"  >耗时(分）</div>
				<div field="checker" width="120" headerAlign="center"  >审核人</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var grid=mini.get("datagrid1");
	grid.setUrl("${ctxPath}/oa/pro/workLog/listByProject.do?planId=${planId}");
	grid.load();
	var plan//有则置为YES,判断是否是从计划点进去的创建日志
    if(${!empty planTask}){
    	planTask="YES";
    }
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="_editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	
	      //日志明细
			function detailMyRow(pkId) {
		        _OpenWindow({
		        	url: __rootPath + "/oa/pro/workLog/get.do?pkId=" + pkId,
		            title: "日志明细", 
		            width: 700, 
		            height: 460,
		        });
		    }
        	//新增
	        function addOne() {
		    	_OpenWindow({
		    		url: __rootPath+"/oa/pro/workLog/edit.do?planId="+${planId}+"&planTask="+"${planTask}",
		            title: "新增", width: 800, height: 450,
		            ondestroy: function(action) {
		            	if(action == 'ok' && typeof(addCallback)!='undefined'){
		            		var iframe = this.getIFrameEl().contentWindow;
		            		addCallback.call(this,iframe);
		            	}else if (action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
		    }
        	
	      //渲染时间
		   	 function onRenderer(e) {
		               var value = e.value;
		               if (value){
		            	   var date = new Date(value);
		            	   return mini.formatDate(date, 'yyyy-MM-dd HH:mm:ss');
		            	   }
		               else
		               return "暂无";
		           }
	      
		   //双击打开项目明细
				function openDetail(e){
					e.sender;
					var record=e.record;
					var pkId=record.pkId;
					detailRow(pkId);
				}
		   
		   
		   //编辑
		   function _editRow(pkId){
			   _OpenWindow({
		    		 url: __rootPath+"/oa/pro/workLog/edit.do?pkId="+pkId+"&planTask="+planTask,
		            title: "编辑日志",
		            width: 785, height: 430,
		            ondestroy: function(action) {
		                if (action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
		   }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.pro.entity.WorkLog" winHeight="450" winWidth="700" entityTitle="日志"
		baseUrl="oa/pro/workLog" />
</body>
</html>