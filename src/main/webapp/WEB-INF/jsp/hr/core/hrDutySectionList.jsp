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
</head>
<body>
	<div class="titleBar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addSingleSection">增加单班次</a>
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
			url="${ctxPath}/hr/core/hrDutySection/getAllSingleSection.do"
			idField="sectionId" 
			multiSelect="true" 
			showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="sectionName" width="120" headerAlign="center"
					allowSort="true">班次名称</div>
				<div field="sectionShortName" width="120" headerAlign="center"
					allowSort="true">班次简称</div>	
				<div field="startSignIn" width="80" headerAlign="center"
					allowSort="true">签到提早(分钟)</div>
				<div field="dutyStartTime" width="100" headerAlign="center"
					allowSort="true">上班时间</div>
				<div field="endSignIn" width="80" headerAlign="center"
					allowSort="true">签到结束(分钟)</div>
				<div field="earlyOffTime" width="80" headerAlign="center"
					allowSort="true">提前早退(分钟)</div>
				<div field="dutyEndTime" width="100" headerAlign="center"
					allowSort="true">下班时间</div>
				<div field="signOutTime" width="80" headerAlign="center"
					allowSort="true">下班结束(分钟)</div>
				<div field="isTwoDay" width="80" headerAlign="center"
					allowSort="true" renderer="onIsTwoDayRenderer">是否跨日</div>
				<div field="isGroup" width="100" headerAlign="center"
					allowSort="true" renderer="onIsGroupRenderer">是否组合班次</div>
			</div>
		</div>
	</div>

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
		
		 function onIsTwoDayRenderer(e){
			  var record = e.record;
			  var isTwoDay = record.isTwoDay;
			  var arr = [ {'key' : 'YES','value' : '是','css' : 'green'},
			              {'key' : 'NO','value' : '否','css' : 'red'}];
			  return $.formatItemValue(arr,isTwoDay);
		  }
		 
		 function onIsGroupRenderer(e){
			  var record = e.record;
			  var isGroup = record.isGroup;
			  var arr = [ {'key' : 'YES','value' : '是','css' : 'green'},
			              {'key' : 'NO','value' : '否','css' : 'red'}];
			  return $.formatItemValue(arr,isGroup);
		  }
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.hr.core.entity.HrDutySection" winHeight="560"
		winWidth="740" entityTitle="班次"
		baseUrl="hr/core/hrDutySection" />
		
	<script type="text/javascript">
		function addSingleSection(){
			_OpenWindow({
				url:__rootPath+"/hr/core/hrDutySection/edit.do",
				height:"560",
				width:"740",
				title:"增加单班次",
				ondestroy:function(action){
					grid.load();
				}
			});
		}
	
	</script>	
</body>
</html>