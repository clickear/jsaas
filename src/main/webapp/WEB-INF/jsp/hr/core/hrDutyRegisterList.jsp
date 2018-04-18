<%-- 
    Document   : [HrDutyRegister]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[HrDutyRegister]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<%-- <redxun:toolbar entityName="com.redxun.hr.core.entity.HrDutyRegister" excludeButtons=""  /> --%>

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/hr/core/hrDutyRegister/getAllRegister.do" idField="registerId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<!-- <div type="checkcolumn" width="20"></div> -->
				<!-- <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div> -->
				<div field="userName" width="120" headerAlign="center" allowSort="true" align="center">人员</div>
				<div field="date" width="120" headerAlign="center" allowSort="true" align="center" dateFormat="yyyy-MM-dd">考勤日期</div>
				<div field="registerTime" width="120" headerAlign="center" align="center" allowSort="true">登记时间</div>
				<div field="regFlag" width="120" headerAlign="center" renderer="onRegFlagRenderer" align="center" allowSort="true">考勤结果</div>
				<div field="regMins" width="120" headerAlign="center" align="center" allowSort="true">迟到、早退(分钟)</div>
				<!-- <div field="reason" width="120" headerAlign="center" allowSort="true">迟到原因</div> -->
				<div field="dayofweek" width="120" headerAlign="center" align="center" allowSort="true">星期</div>
				<div field="inOffFlag" width="120" headerAlign="center" renderer="onInOffFlagRenderer" align="center" allowSort="true">上下班</div>
			</div>
		</div>
	</div>
	
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.hr.core.entity.HrDutyRegister" winHeight="450" winWidth="700" entityTitle="[HrDutyRegister]" baseUrl="hr/core/hrDutyRegister" />
	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editRow(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delRow(\''
					+ pkId + '\')"></span>';
			return s;
		}
		
		 function onRegFlagRenderer(e){
			  var record = e.record;
			  var regFlag = record.regFlag;
			  
			  var arr = [ {'key' : '1','value' : '正常','css' : 'green'},
			              {'key' : '2','value' : '迟到','css' : 'orange'}, 
			              {'key' : '3','value' : '早退','css' : 'red'}];
			  return $.formatItemValue(arr,regFlag);
		  }
		
		 function onInOffFlagRenderer(e){
			  var record = e.record;
			  var inOffFlag = record.inOffFlag;
			  
			  var arr = [ {'key' : '1','value' : '上班','css' : 'purple'},
			              {'key' : '2','value' : '下班','css' : 'blue'}];
			  return $.formatItemValue(arr,inOffFlag);
		  }
		 
		grid.on("drawcell", function (e) {
            //var record = e.record,
	        field = e.field,
	        value = e.value;
            if (field == "registerTime") {
                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm:ss");
            }
            if(field == "dayofweek"){
            	var array=['日','一','二','三','四','五','六'];
            	e.cellHtml=array[value];
            }
           
    	});
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	
</body>
</html>