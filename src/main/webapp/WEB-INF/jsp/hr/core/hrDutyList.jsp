<%-- 
    Document   : [HrDuty]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDuty]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.hr.core.entity.HrDuty" excludeButtons="detail,popupAttachMenu,popupSettingMenu,popupSearchMenu,copyAdd,fieldSearch,query" />

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/hr/core/hrDuty/listData.do" idField="dutyId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="dutyName" width="120" headerAlign="center" allowSort="true">排班名称</div>
				<div field="systemId" width="120" headerAlign="center" allowSort="true">班制</div>
				<div field="startTime" width="120" headerAlign="center" allowSort="true">开始时间</div>
				<div field="endTime" width="120" headerAlign="center" allowSort="true">结束时间</div>
				<div field="userId" width="120" headerAlign="center" allowSort="true">使用者</div>
				<div field="groupId" width="120" headerAlign="center" allowSort="true">用户组</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s =  ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-goto" title="执行" onclick="runDuty(\'' + pkId + '\')"></span>';
	            return s;
	            /*'<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
                +*/
	        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.hr.core.entity.HrDuty" winHeight="450" winWidth="700" entityTitle="排班" baseUrl="hr/core/hrDuty" />
	<script type="text/javascript">
		function runDuty(pkId){
			_SubmitJson({
				url:__rootPath+"/hr/core/hrDuty/createDuty.do",
				data:{
					dutyId:pkId
				},
				method:'POST',
				success:function(result){
					
				}
			});
		}
		
		grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
            if(field == "systemId"){
            	e.cellHtml=record.hrDutySystem.name;
            }
            if(field == "userId"){
            	e.cellHtml=record.userName;
            }
			if(field == "groupId"){
				e.cellHtml=record.groupName;
            }
            
            
            
    	});
	</script>
</body>
</html>