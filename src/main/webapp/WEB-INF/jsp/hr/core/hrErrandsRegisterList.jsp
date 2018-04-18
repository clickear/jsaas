<%-- 
    Document   : 请假加班外出出差列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>请假加班外出出差列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width: 100%; height: 100%;" onactivechanged="">
		<div title="请假申请" url="${ctxPath}/hr/core/hrVacationExt/list.do">
        	
    	</div>
    	<div title="加班申请" url="${ctxPath}/hr/core/hrOvertimeExt/list.do">
        
    	</div>
    	<div title="调休申请" url="${ctxPath}/hr/core/hrTransRestExt/list.do">
        
    	</div>
	</div>
	

	<%-- <div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/hr/core/hrErrandsRegister/listData.do" idField="erId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<!-- <div field="desc" width="120" headerAlign="center" allowSort="true">描述</div> -->
				<div field="startTime" width="120" headerAlign="center" allowSort="true">开始日期</div>
				<div field="endTime" width="120" headerAlign="center" allowSort="true">结束日期</div>
				<div field="flag" width="120" headerAlign="center" allowSort="true">标识</div>
				<!-- <div field="bpmInstId" width="120" headerAlign="center"
					allowSort="true">流程实例ID</div> -->
				<div field="type" width="120" headerAlign="center" allowSort="true">类型</div>
				<div field="status" width="120" headerAlign="center" allowSort="true">状态</div>
			</div>
		</div>
	</div> --%>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<%-- <redxun:gridScript gridId="datagrid1" entityName="com.redxun.hr.core.entity.HrErrandsRegister" winHeight="450" winWidth="700" entityTitle="请假加班外出出差" baseUrl="hr/core/hrErrandsRegister" /> --%>
	<script type="text/javascript">
	mini.parse();
/* 	var grid=mini.get("datagrid1"); */
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\''+ pkId+ '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editRow(\''+ pkId+ '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delRow(\''+ pkId + '\')"></span>';
            if(record.bpmInstId){
            	s+= ' <span class="icon-check" title="审批明细" onclick="checkDetail(\'' + record.bpmInstId + '\')"></span>';
            }
			return s;
		}
		
        //处理添加
        function _add(){
	         //检查是否存在流程配置，若没有，则启用本地的默认配置
	         _ModuleFlowWin({
	          title:'请假加班外出出差申请',
	          moduleKey:'HR_ERRANDS_REGISTER',
	          //failCall:add,
	          success:function(){
	           grid.load(); 
	          }
	         });
        }
		//查看流程审批实例信息
		function checkDetail(bpmInstId){
		         _OpenWindow({
		          title:'审批明细',
		          width:800,
		          height:480,
		          url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+bpmInstId
		         });
        }
		
		
     /*    grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
            var status = record.status;
            
            //格式化日期
            if (field == "startTime") {
                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
            }
            if (field == "endTime") {
                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
            }
            
            if(field=='carCat'){
            	e.cellHtml= '<a href="javascript:detailRow(\'' + record.pkId + '\')">'+record.carCat+'</a>';
            }
            if(field=='status'){
            	if(status=='NOTAPPROVED'){
            		e.cellHtml='未审批';
            	}else if(status=='APPROVED'){
            		e.cellHtml='审批通过';
            	}
            }
    		});
        grid.on('update',function(){
        	_LoadUserInfo();
        }); */

	</script>
</body>
</html>