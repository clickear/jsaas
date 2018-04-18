<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<html>
<head>
<title>空闲时间车辆列表</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
<redxun:toolbar entityName="com.redxun.oa.res.entity.OaCar" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,popupSettingMenu,detail,edit,remove,fieldSearch" >
	<div class="self-toolbar">
		<form id="searchForm">
	    <table>
	    <tr>
	    <td>
	    <span>起始时间：</span>
	    <input id="startTime" name="startTime" class="mini-datepicker" showTime="true" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" emptyText="请输入起始时间" />
	           终止时间;
	    <input id="endTime" name="endTime" class="mini-datepicker" showTime="true" format="yyyy-MM-dd HH:mm:ss" timeFormat="H:mm:ss" vtype="maxLength:19" emptyText="请输入终止时间" />	
	    <a class="mini-button" iconCls="icon-search" onclick="onSearch">搜索车辆</a>
	    <a class="mini-button" iconCls="icon-cancel" onclick="onClear()">清空</a>
	    <a class="mini-button" iconCls="icon-remove"  onclick="onCancel">关闭</a>
	    </td>
	   </tr>
	</table> 
</form>	
	</div>
</redxun:toolbar>
	<div class="mini-fit form-outer" style="height: 100%;">
	<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/res/oaCar/getTimeByStutsCar.do?startTime=${param['startTime']}&endTime=${param['endTime']}" idField="carId"
				multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons" >
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">选车处</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">车辆名称</div>
				<div field="sysDicId" width="80" headerAlign="center" allowSort="true">车辆类型</div>
				<div field="carNo" width="120" headerAlign="center" allowSort="true">车牌号</div>
				<div field="status" width="80" headerAlign="center"
					allowSort="true">车辆状态</div>
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
        var s = '<span class="icon-attachAdd" title="立即选车" onclick="onOKCar()"></span>';
        return s;
    }
	
	$(".icon-attachAdd").click(function(){
		onOKCar();
	});
	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            var status = record.status;
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
	        
	        function onCancel(){   
	        	CloseWindow('canel'); 
	        } 
	        
	        function onOKCar(){
	        	CloseWindow("ok");
	        }
	        
	        function getOaCar(){
	        	var row=grid.getSelected();
	        	return row;
	        }
			//清空查询
			function onClear(){
				$("#searchForm")[0].reset();		
			}
	        //查询
	        function onSearch(){
	    		var startTime=mini.get("startTime").getFormValue();
	    		var endTime=mini.get("endTime").getFormValue();
				grid.setUrl(__rootPath+'/oa/res/oaCar/getTimeByStutsCar.do?startTime='+startTime+'&endTime='+endTime);
				grid.load();
	        }
     </script>
</body>
</html>