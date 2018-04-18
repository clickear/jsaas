<%-- 
    Document   : [HrDutySection]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutySection]列表管理</title>
<%@include file="/commons/list.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.tmpl.js" type="text/javascript"></script>
<style type="text/css">
	.detailForm{
		border-color: #ececec;
	}
	.detailForm td,
	.detailForm th{
		border:1px solid #ececec;
		background: #fff;
		padding: 4px;
		font-size: 14px;
	}
</style>
</head>
<body>
	<div class="titleBar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-add" onclick="addGroupSection">增加班次</a>
			</li>
			<li class="clearfix"></li>
		</ul>
     </div>
	
	
	
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid"
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/hr/core/hrDutySection/getAllGroupSection.do"
			idField="sectionId" 
			multiSelect="true" 
			showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
			pagerButtons="#pagerButtons"  
			allowRowSelect="false" 
			enableHotTrack="false" 
			onshowrowdetail="onShowRowDetail" 
			autoHideRowDetail="false" 
		>
	        <div property="columns">
	            <div type="expandcolumn"></div>
	            <div name="action" cellCls="actionIcons" width="22"	headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="sectionName" width="120" headerAlign="center" allowSort="true">班次名称</div>
				<div field="sectionShortName" width="120" headerAlign="center" allowSort="true">班次简称</div>              
	        </div>
	    </div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = ' <span class="icon-edit" title="编辑" onclick="editGroupSection(\''
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
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.hr.core.entity.HrDutySection" winHeight="450"
		winWidth="700" entityTitle="班次"
		baseUrl="hr/core/hrDutySection" />
		
	<script id="formTemplate" type="text/x-jquery-tmpl">
    <table class="detailForm" border="1" cellspacing="1" style="width:100%;">
      <tr>
        <td style="width:80px;">小班次名称</td>
        <td style="width:80px;">小班次简称</td>
		<td style="width:80px;">签到提早(分钟)</td>
		<td style="width:80px;">上班时间</td>
		<td style="width:80px;">签到结束(分钟)</td>
		<td style="width:80px;">提前早退(分钟)</td>
		<td style="width:80px;">下班时间</td>
		<td style="width:80px;">下班结束(分钟)</td>
		<td style="width:80px;">是否跨日</td>
      </tr>
    </table>
    
	</script>
	
	<script id="trDetail" type="text/x-jquery-tmpl">
      <tr>
        <td style="width:80px;">{{= sectionName}}</td>
        <td style="width:80px;">{{= sectionShortName}}</td>
		<td style="width:80px;">{{= startSignIn}}</td>
		<td style="width:80px;">{{= dutyStartTime}}</td>
		<td style="width:80px;">{{= endSignIn}}</td>
		<td style="width:80px;">{{= earlyOffTime}}</td>
		<td style="width:80px;">{{= dutyEndTime}}</td>
		<td style="width:80px;">{{= signOutTime}}</td>
		<td style="width:80px;">{{= isTwoDay}}</td>
      </tr>
    
	</script>
	<script type="text/javascript">
		function editGroupSection(pkId){
			_OpenWindow({
				url:__rootPath+"/hr/core/hrDutySection/groupEdit.do?pkId="+pkId,
				height:"500",
				width:"700",
				title:"编辑组合班次",
				ondestroy:function(action){
					grid.load();
				}
			});
		}
	
		function addGroupSection(){
			_OpenWindow({
				url:__rootPath+"/hr/core/hrDutySection/groupEdit.do",
				height:"600",
				width:"1024",
				title:"增加组合班次",
				ondestroy:function(action){
					grid.load();
				}
			});
		}
	
		function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;

            var td = grid.getRowDetailCellEl(row);
			var text=row;
            var o = mini.decode(text);
            td.innerHTML = "";
            $("#formTemplate").tmpl().appendTo(td);
            var group=text.groupList;
            var list=mini.decode(group).sections;
            for(var i=0;i<list.length;i++){
            	$("#trDetail").tmpl(list[i]).appendTo($(td).find(".detailForm"));
            }
        }
	</script>	
</body>
</html>