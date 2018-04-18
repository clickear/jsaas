<%-- 
    Document   : [OaMeeting]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>会议申请列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/list.jsp"%>
</head>
<body>
	
	<redxun:toolbar entityName="com.redxun.oa.res.entity.OaMeeting" >
	</redxun:toolbar>

	<div class="mini-fit rx-grid-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/res/oaMeeting/listData.do" idField="meetId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="name" width="160" headerAlign="center" allowSort="true">会议名称</div>
				<div field="start" width="80" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">开始时间</div>
				<div field="end" width="80" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">结束时间</div>
				<div field="location" width="120" headerAlign="center" allowSort="true">会议地点</div>
				<div field="hostUid" width="120" headerAlign="center"
					allowSort="true">主持人</div>
				<div field="status" width="80" headerAlign="center"
					allowSort="true">会议状态</div>
			</div>
		</div>
	</div>
<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.res.entity.OaMeeting" winHeight="450"
		winWidth="800" entityTitle="会议申请管理" baseUrl="oa/res/oaMeeting" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    +'<span class="icon-writemail" title="会议总结" onclick="editB1Row(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            if(record.bpmInstId){
	            	s+= ' <span class="icon-check" title="审批明细" onclick="checkDetail(\'' + record.bpmInstId + '\')"></span>';
	            }
	            return s;
	        }
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            var status = record.status;
	            //格式化日期
	            if (field == "start") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if (field == "end") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if(field=='name'){
	            	e.cellHtml= '<a href="javascript:detailRow(\'' + record.pkId + '\')">'+record.name+'</a>';
	            }
	            if(field=='status'){
	            	if(status=='ENABLED'){
	            		e.cellHtml='启用';
	            	}else if(status=='DISABLED'){
	            		e.cellHtml='禁止';
	            	}
	            }
	            
	            if(field=='hostUid'){
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

	        
	       //处理添加
 /* 	        function _add(){
	         //检查是否存在流程配置，若没有，则启用本地的默认配置
	         _ModuleFlowWin({
	          title:'会议申请',
	          moduleKey:'OA_MEETING_',
	          //failCall:add,
	          success:function(){
	           grid.load(); 
	          }
	         });
	        }   */ 
	       
/* 	        function checkDetail(bpmInstId){
	       		_OpenWindow({
	       			title:'审批明细',
	       			width:800,
	       			height:480,
	       			url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+bpmInstId
	       		});
	       	} */
	        
	        
	        //跳转到编辑总结页面
	        function editB1Row(pkId){
	        	var row = grid.getSelected();
	        	_OpenWindow({
	    			url : "${ctxPath}/oa/res/oaMeeting/summary.do?pkId=" + row.pkId,
	    			title :"会议申请纪要",
	    			iconCls:'icon-writemail',
	    			width : 800,
	    			height : 500,
	    		});
	        }
        </script>
</body>
</html>