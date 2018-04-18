<%-- 
    Document   : 计划列表页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<!DOCTYPE html >
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>计划列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.oa.pro.entity.PlanTask" excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove,popupSearchMenu,fieldSearch,popupSettingMenu" >
	<div class="self-toolbar">
		<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">新增计划</a>
	</div>
	</redxun:toolbar>

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"   onrowdblclick="openDetail(e)"
			idField="planId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="35" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="subject" width="120" headerAlign="center"  >计划标题</div>
				<div field="content" width="120" headerAlign="center"  >计划内容</div>
				<div field="pstartTime" width="120" headerAlign="center"   renderer="onRenderer">计划开始时间</div>
				<div field="pendTime" width="120" headerAlign="center"    renderer="onRenderer">计划结束时间</div>
				<div field="startTime" width="120" headerAlign="center"    renderer="onRenderer">实际开始时间</div>
				<div field="endTime" width="120" headerAlign="center"   renderer="onRenderer">实际结束时间</div>
				<div field="status" width="120" headerAlign="center"  >状态</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var grid=mini.get("datagrid1");
	grid.setUrl("${ctxPath}/oa/pro/planTask/listProjectPlan.do?projectId=${projectId}");
	grid.load();
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editMyRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	      //项目明细
			function detailMyRow(pkId) {
		        _OpenWindow({
		        	url: __rootPath + "/oa/pro/planTask/get.do?pkId=" + pkId,
		            title: "计划明细", 
		            width: 880, 
		            height: 500,
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
	      //编辑任务
	   	function editMyRow(pkId){
	   	 _OpenWindow({
    		 url: __rootPath + "/oa/pro/planTask/edit.do?pkId="+pkId,
            title: "编辑计划",
            width: 800, height: 400,
            ondestroy: function(action) {
                if (action == 'ok') {
                    grid.reload();
                }
            }
    	});
	   	}
        	
	 	//双击打开项目明细
			function openDetail(e){
				var sender=e.sender;
				var record=e.record;
				var pkId=record.pkId;
				detailMyRow(pkId);
			}
	      
	      //添加数据
		    function addOne() {
		    	_OpenWindow({
		    		url: __rootPath+"/oa/pro/planTask/edit.do?projectId="+${projectId},
		            title: "新增", width: 800, height: 400,
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
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.pro.entity.planTask" winHeight="450" winWidth="700" entityTitle="计划"
		baseUrl="oa/pro/planTask" />
</body>
</html>