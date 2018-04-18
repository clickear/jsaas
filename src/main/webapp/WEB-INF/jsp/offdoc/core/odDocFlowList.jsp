<%-- 
    Document   : [OdDocFlow]列表页
    Created on : 2016-3-8, 16:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>公文接收列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
.mini-tree .mini-grid-viewport{
	background: #fff;
}
</style>
</head>
<body>


<div class="mini-fit" style="height: 100%;">
	<div 
		id="datagrid1" 
		class="mini-treegrid" 
		style="width:100%;height:100%;"    
		showTreeLines="true"
     	showTreeIcon="true"   
     	url="${ctxPath}/offdoc/core/odDocFlow/listSol.do"  
     	allowCellEdit="true" 
     	onnodeselect="setNode(e)"
    	treeColumn="groupName" 
    	idField="depId" 
    	parentField="groupParent" 
    	resultAsTree="false"  
    	ondrawnode="onDrawNode"  
    	allowResize="true" 
    	expandOnLoad="false" 
    	allowCellSelect="true"
    	allowAlternating="true"
   	>
			<div property="columns">
				<div name="groupName" field="groupName" width="120" headerAlign="center" allowSort="true">部门</div>
				<div  name="sendSolName" field="sendSolName" width="120" headerAlign="center" allowSort="true">发文流程方案名称
				<input name="sendSolName" id="thisSendSolName" property="editor" class="mini-buttonedit" allowInput="false"  onbuttonclick="onDispatch" /></div>
				<div name="recSolName" field="recSolName" width="120" headerAlign="center" allowSort="true">收文流程方案名称
				<input name="recSolName" id="thisRecSolName" property="editor" class="mini-buttonedit" allowInput="false"  onbuttonclick="onIncoming" /></div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		mini.parse();
		var grid=mini.get("datagrid1");
		var schemeId;
		var depId;
		//绘制节点图标
		function onDrawNode(e) {
	        var tree = e.sender;
	        var node = e.node;
	   	e.iconCls='icon-flow-design';}
		//点击node时激发
		function setNode(e){
			schemeId=e.node.schemeId;
			depId=e.node.depId;
		}
		//选择发文流程
		function onDispatch(){//  
			_OpenWindow({
				title : '选择流程',
				url :__rootPath+'/bpm/core/bpmSolution/dialog.do?single='+true,
				width : 780,
				height : 350,
				ondestroy : function(action) {
					var iframe = this.getIFrameEl();
					if('ok'==action){
						 var sols = iframe.contentWindow.getSolutions();
				            mini.get("thisSendSolName").setValue(sols[0].name);
				        	mini.get("thisSendSolName").setText(sols[0].name);
			            if(schemeId==null){//当只有group没有流程时
			            	$.ajax({
		    	                type: "Post",
		    	                url : '${ctxPath}/offdoc/core/odDocFlow/saveDOne.do?solution='+sols[0].solId+"&depId="+depId,
		    	                data: {},
		    	                beforeSend: function () { },
		    	                success: function (data) {
		    	                	schemeId=data;
		    	                	mini.showTips({
		   	                         content: "<b>成功</b> <br/>流程创建完成",
		   	                         state: 'success',
		   	                         x: "center",
		   	                         y: "top",
		   	                         timeout: 3000 });
		    	                	}
		    	            });
			            } else  if(schemeId!=null){//当有group也有流程时
			            	$.ajax({
	    	                type: "Post",
	    	                url : '${ctxPath}/offdoc/core/odDocFlow/saveDOne.do?solution='+sols[0].solId+"&schemeId="+ schemeId,
	    	                data: {},
	    	                beforeSend: function () { },
	    	                success: function (data) {
	    	                	mini.showTips({
	   	                         content: "<b>成功</b> <br/>流程修改完成",
	   	                         state: 'success',
	   	                         x: "center",
	   	                         y: "top",
	   	                         timeout: 3000 });
	    	                	}
	    	            });}
			            
					}
					
				}
			});
		}
		//选择收文流程
		function onIncoming(){//  
			_OpenWindow({
				title : '选择流程',
				url :__rootPath+'/bpm/core/bpmSolution/dialog.do?single='+true,
				width : 780,
				height : 350,
				ondestroy : function(action) {
					if('ok'==action){
						var iframe = this.getIFrameEl();
						var sols = iframe.contentWindow.getSolutions();
			            mini.get("thisRecSolName").setValue(sols[0].name);
			        	mini.get("thisRecSolName").setText(sols[0].name);
			            if(schemeId==null){
			            	$.ajax({
		    	                type: "Post",
		    	                url : '${ctxPath}/offdoc/core/odDocFlow/saveIOne.do?solution='+sols[0].solId+"&depId="+depId,
		    	                data: {},
		    	                beforeSend: function () { },
		    	                success: function (data) {
		    	                	schemeId=data;
		    	                	mini.showTips({
		   	                         content: "<b>成功</b> <br/>流程创建完成",
		   	                         state: 'success',
		   	                         x: "center",
		   	                         y: "top",
		   	                         timeout: 3000 });
		    	                	}
		    	            });
			            } else  if(schemeId!=null){
			            	$.ajax({
	    	                type: "Post",
	    	                url : '${ctxPath}/offdoc/core/odDocFlow/saveIOne.do?solution='+sols[0].solId+"&schemeId="+ schemeId,
	    	                data: {},
	    	                beforeSend: function () { },
	    	                success: function (data) {
	    	                	mini.showTips({
	   	                         content: "<b>成功</b> <br/>流程修改完成",
	   	                         state: 'success',
	   	                         x: "center",
	   	                         y: "top",
	   	                         timeout: 3000 });
	    	                	}
	    	            });}
					}
				}
			});
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</body>
</html>