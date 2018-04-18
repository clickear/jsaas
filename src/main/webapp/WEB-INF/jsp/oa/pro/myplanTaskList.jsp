<%-- 
    Document   : 计划列表页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>计划列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar 
		entityName="com.redxun.oa.pro.entity.PlanTask" 
		excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove,popupSearchMenu,popupSettingMenu"  
	>
	<div class="self-toolbar">
		<li>
			<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">新增计划</a>
		</li>
	</div>
	</redxun:toolbar>

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"  url="${ctxPath}/oa/pro/planTask/myList.do" onrowdblclick="openDetail(e)"
			idField="planId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="subject" width="120" headerAlign="center"  allowSort="true">计划标题</div>
				<div field="content" width="120" headerAlign="center"  allowSort="true">计划内容</div>
				<div field="pstartTime" width="121" headerAlign="center"   renderer="onRenderer" allowSort="true">计划开始时间</div>
				<div field="pendTime" width="121" headerAlign="center"    renderer="onRenderer" allowSort="true">计划结束时间</div>
				<div field="startTime" width="121" headerAlign="center"    renderer="onRenderer" allowSort="true">实际开始时间</div>
				<div field="endTime" width="121" headerAlign="center"   renderer="onRenderer" allowSort="true">实际结束时间</div>
				<div field="status" width="120" headerAlign="center"  >状态</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();	
	var grid=mini.get("datagrid1");
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
        	
	 	//双击打开项目明细
			function openDetail(e){
				e.sender;
				var record=e.record;
				var pkId=record.pkId;
				detailMyRow(pkId);
			}
	      
	      
	   //编辑行数据
		    function editMyRow(pkId) {        
		        _OpenWindow({
		    		 url: __rootPath + "/oa/pro/planTask/myedit.do?pkId="+pkId,
		            title: "编辑计划",
		            width: 900, height: 500,
		            ondestroy: function(action) {
		                if (action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
		    }
	      
	      //添加数据
		    function addOne() {
		    	_OpenWindow({
		    		url: __rootPath+"/oa/pro/planTask/myedit.do",
		            title: "新增", width: 800, height: 500,
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
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.pro.entity.PlanTask" winHeight="450" winWidth="700" entityTitle="计划"
		baseUrl="oa/pro/planTask" />
</body>
</html>