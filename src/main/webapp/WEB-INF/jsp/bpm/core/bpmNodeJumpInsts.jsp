<%-- 
    Document   : [BpmNodeJump]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程实例的流转列表记录</title>
<%@include file="/commons/list.jsp" %>
</head>
<body>

	<div class="mini-fit form-outer" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" showPager="false"
			url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${param['actInstId']}" idField="jumpId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="indexcolumn" width="50"></div>
				<div field="nodeName" width="150" headerAlign="center" >节点名称</div>
				<div field="remark" width="120" headerAlign="center" >意见备注</div>
				<div field="createTime" dateFormat="yyyy-MM:dd HH:mm:ss" width="150" headerAlign="center" >创建时间</div>
				<div field="completeTime" dateFormat="yyyy-MM:dd HH:mm:ss" width="150" headerAlign="center" >审批时间</div>
				<div field="durationFormat" width="100" headerAlign="center" >持续时长</div>
				<div field="handlerId" width="120" headerAlign="center" >审批人</div>
				<div field="checkStatusText" width="80" headerAlign="center" >审批状态</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		grid.load();
		grid.on("drawcell", function (e) {
            var record = e.record,
	        field = e.field,
	        value = e.value;
          	var ownerId=record.ownerId;
            if(field=='handlerId'){
            	if(ownerId && ownerId!=value){
            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;(原审批人:'+
            		'<a class="mini-user" iconCls="icon-user" userId="'+ownerId+'"></a>'+')';
            	}else if(value){
            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
            	}else{
            		e.cellHtml='<span style="color:red">无</span>';
            	}
            } 
            
        });
        
        grid.on('update',function(){
        	_LoadUserInfo();
        });
	</script>
</body>
</html>