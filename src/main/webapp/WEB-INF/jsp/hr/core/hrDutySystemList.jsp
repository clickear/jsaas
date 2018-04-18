<%-- 
    Document   : [HrDutySystem]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySystem]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.hr.core.entity.HrDutySystem" excludeButtons="detail,popupAttachMenu,popupSettingMenu,popupSearchMenu,copyAdd,fieldSearch,query" />

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/hr/core/hrDutySystem/listData.do" idField="systemId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="isDefault" width="120" headerAlign="center" allowSort="true">是否缺省</div>
				<div field="type" width="120" headerAlign="center" allowSort="true">分类</div>
				<div field="workSection" width="120" headerAlign="center" allowSort="true">作息班次</div>
				<div field="wkRestSection" width="120" headerAlign="center" allowSort="true">休息日加班班次</div>
				<div field="restSection" width="120" headerAlign="center" allowSort="true">休息日</div>
			</div>
		</div>
	</div>
	
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.hr.core.entity.HrDutySystem" winHeight="450" winWidth="700" entityTitle="班制" baseUrl="hr/core/hrDutySystem" />
	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = ' <span class="icon-edit" title="编辑" onclick="editRow(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delRow(\''
					+ pkId + '\')"></span>';
			return s;
			/*'<span class="icon-detail" title="明细" onclick="detailRow(\''
					+ pkId
					+ '\')"></span>'
					+ */
		}
		
		grid.on("drawcell", function (e) {
            //var record = e.record,
	        field = e.field,
	        value = e.value;
            if(field == "isDefault"){
            	if(value=="0")
            		e.cellHtml="<span style='color:orange'>否</span>";
            	else if(value=="1")
            		e.cellHtml="<span style='color:green'>是</span>";
            }
            if(field == "type"){
            	if(value=="WEEKLY")
            		e.cellHtml="标准一周";
            	else if(value=="PERIODIC")
            		e.cellHtml="周期";
            }
            if(field=="restSection"){
            	if(value.indexOf("0")>-1){
            		e.cellHtml=e.cellHtml.replace('0',"星期天");
            	}
            	if(value.indexOf("1")>-1){
            		e.cellHtml=e.cellHtml.replace('1',"星期一");
            	}
            	if(value.indexOf("2")>-1){
            		e.cellHtml=e.cellHtml.replace('2',"星期二");
            	}
            	if(value.indexOf("3")>-1){
            		e.cellHtml=e.cellHtml.replace('3',"星期三");
            	}
            	if(value.indexOf("4")>-1){
            		e.cellHtml=e.cellHtml.replace('4',"星期四");
            	}
            	if(value.indexOf("5")>-1){
            		e.cellHtml=e.cellHtml.replace('5',"星期五");
            	}
            	if(value.indexOf("6")>-1){
            		e.cellHtml=e.cellHtml.replace('6',"星期六");
            	}
            }
            
            
    	});
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	
</body>
</html>