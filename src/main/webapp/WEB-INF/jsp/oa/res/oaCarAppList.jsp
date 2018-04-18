<%-- 
    Document   : 车辆申请列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>车辆申请列表管理</title>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.oa.res.entity.OaCarApp">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-home" plain="true" onclick="onStuts">归还车辆</a>
			<a class="mini-button" iconCls="icon-edit" plain="true" onclick="onPostponed">延迟归还</a>
		</div>
	</redxun:toolbar>

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/res/oaCarApp/listData.do" idField="appId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="carCat" width="120" headerAlign="center" allowSort="true">汽车类别</div>
				<div field="carName" width="120" headerAlign="center" allowSort="true">车辆名称</div>
				<div field="startTime" width="120" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">起始时间</div>
				<div field="endTime" width="120" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">终止时间</div>
				<div field="driver" width="80" headerAlign="center" allowSort="true">驾驶员</div>
				<div field="status" width="120" headerAlign="center" allowSort="true">申请状态</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.res.entity.OaCarApp" winHeight="450"
		winWidth="700" entityTitle="车辆申请" baseUrl="oa/res/oaCarApp" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            if(record.bpmInst){
	            	s+= ' <span class="icon-check" title="审批明细" onclick="checkDetail(\'' + record.bpmInst + '\')"></span>';
	            }
	            return s;
	        }

    	
        grid.on("drawcell", function (e) {
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
        });
		
        //归还车辆
        function onStuts(){
    		var rows=grid.getSelecteds();
    		
    		for(var i=0;i<rows.length;i++){
    			
    			if(rows[i].oaCar.status=='INFREE'){
    				
    				alert('该车辆已经归还，无需再次归还！');
    				return;
    			}
    		}
    		if(rows.length==0){
    			alert('请选择一条记录！');
    			return;
    		}
    		var ids=[];
    		
    		for(var i=0;i<rows.length;i++){
    			ids.push(rows[i].appId);
    		}
    		_SubmitJson({
    			url:__rootPath+'/oa/res/oaCarApp/updateStatus.do',
    		data:{
    			ids:ids.join(',')
    		},
    		method:'POST',
    		success:function(text){
    			grid.load();
    		}
    			
    		});
        }
        
      	//延迟归来
        function onPostponed(){
    		var row=grid.getSelected();
    		if(row==null){
    			alert("请选择一条记录！");
    			return;
    		}
    		var newDate=new Date();
    		if(row.endTime>newDate){
    			if(confirm("结束时间还没到,是否提前归还？")){
    				onStuts();
    			}
    		}else{
    			_OpenWindow({
        			url : "${ctxPath}/oa/res/oaCarApp/end.do?pkId=" + row.pkId,
        			title : "延迟申请车辆时间",
        			width : 800,
        			height : 500,
        		//max:true,
        		});
    		}
        	
        }		
      	//处理添加
        function _add(){
        //检查是否存在流程配置，若没有，则启用本地的默认配置
        _ModuleFlowWin({
         title:'用车申请',
         moduleKey:'OA_CAR_APP_',
         //failCall:add,
         success:function(){
          grid.load(); 
         }
        });
       }  
      
        function checkDetail(bpmInst){
      		_OpenWindow({
      			title:'审批明细',
      			width:800,
      			height:480,
      			url:__rootPath+'/bpm/core/bpmInst/get.do?actInstId='+bpmInst
      		});
      	}		
		</script>
</body>
</html>