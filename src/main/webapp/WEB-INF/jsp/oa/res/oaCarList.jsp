<%-- 
    Document   : 车辆列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>车辆列表管理</title>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.oa.res.entity.OaCar" />

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/res/oaCar/listData.do" idField="carId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">车辆名称</div>
				<div field="sysDicId" width="80" headerAlign="center" allowSort="true">车辆类型</div>
				<div field="carNo" width="120" headerAlign="center" allowSort="true">车牌号</div>
				<div field="buyDate" width="80" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">购买日期</div>
				<div field="status" width="80" headerAlign="center"
					allowSort="true">车辆状态</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.res.entity.OaCar" winHeight="450"
		winWidth="700" entityTitle="车辆" baseUrl="oa/res/oaCar" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            var status = record.status;
	            //格式化日期
	            if (field == "buyDate") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if(field=='name'){
	            	e.cellHtml= '<a href="javascript:detailRow(\'' + record.pkId + '\')">'+record.name+'</a>';
	            }
	            if(field=='status'){
	            	if(status=='INUSED'){
	            		e.cellHtml='在使用';
	            	}else if(status=='INFREE'){
	            		e.cellHtml='空闲';
	            	}else if(status=='SCRAP'){
	            		e.cellHtml='报废';
	            	}
	            }
        		});
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
	        
	        
     </script>
</body>
</html>