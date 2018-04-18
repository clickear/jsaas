<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      	<%@include file="/commons/get.jsp" %>
		<title>流程实例明细（不含表单）</title>
</head>
<body>
	
						<table class="table-detail" cellpadding="0" cellspacing="1">
						 	<caption>流程图</caption>
							<tr>
								<td>
									<img src="${ctxPath}/bpm/activiti/processImage.do?actInstId=${actInstId}"/>	
								</td>
							</tr>
						</table>
						
						<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
						 	<caption>流程审批记录</caption>
							<tr>
								<td>
									<div id="datagrid1" class="mini-datagrid" style="width: 100%;" height="auto" allowResize="false" showPager="false"
											url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${actInstId}" idField="jumpId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
											<div property="columns">
												<div type="indexcolumn" width="50"></div>
												<div field="nodeName" width="150" headerAlign="center" >审批环节</div>
												<div field="remark" width="120" headerAlign="center" >意见</div>
												<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" >发送时间</div>
												<div field="completeTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="150" headerAlign="center" >审批时间</div>
												<div field="durationFormat" width="100" headerAlign="center" >持续时长</div>
												<div field="handlerId" width="120" headerAlign="center" >审批人</div>
												<div field="checkStatusText" width="80" headerAlign="center" >审批状态</div>
											</div>
									</div>	
								</td>
							</tr>
						</table>
	
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
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;(代:'+
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