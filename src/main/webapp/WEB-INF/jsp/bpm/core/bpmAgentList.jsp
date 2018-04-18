<%-- 
    Document   : 我的流程代理设置列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title>我的流程代理设置列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<redxun:toolbar entityName="com.redxun.bpm.core.entity.BpmAgent" />
	<div class="mini-fit rx-grid-fit">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/core/bpmAgent/listData.do" idField="agentId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="subject" width="160" headerAlign="center" allowSort="true">代理简述</div>
				<div field="toUserId" width="60" headerAlign="center" allowSort="true">代理给</div>
				<div field="startTime" width="100" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd">开始时间</div>
				<div field="endTime" width="100" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd">结束时间</div>
				<div field="type" width="80" headerAlign="center" allowSort="true">代理类型</div>
				<div field="status" width="80" headerAlign="center" allowSort="true" renderer="onStatusRenderer">状态</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmAgent"
	 winHeight="450" winWidth="700" entityTitle="流程代理设置" baseUrl="bpm/core/bpmAgent" />
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
        	
	        function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            
	            var arr = [ {'key' : 'ENABLED','value' : '启用','css' : 'green'}, 
	   		                {'key' : 'DISABLED','value' : '禁用','css' : 'red'}];
	   		    return $.formatItemValue(arr,status);
	        }
        	
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            var status = record.status;
	            var type=record.type;
	            if(field=='toUserId'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	          
	            if(field=='type'){
	            	if(type=='ALL'){
	            		e.cellHtml='全部';
	            	}else if(type=='PART'){
	            		e.cellHtml='部分';
	            	}
	            }
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });

        </script>
</body>
</html>