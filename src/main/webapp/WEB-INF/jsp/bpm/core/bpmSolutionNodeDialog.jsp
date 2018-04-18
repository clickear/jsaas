<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>业务流程解决方案管理-流程定义节点数据交互设置</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
.mini-grid-rows-view{
	overflow-x: hidden;
}
.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
	border-bottom: none;
}

#nodeGrid .mini-panel-border,
.mini-fit{
	background: #fff;
}
</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar topBtn" >
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="onOk">保存</a>
	</div>
	<c:set var="multiSelect" value="true"></c:set>
	<c:if test="${param.single=='true'}">
		<c:set var="multiSelect" value="false"></c:set>
	</c:if>
	
	
	<div class="mini-fit">
		<div 
			id="nodeGrid" 
			class="mini-treegrid"
			showTreeIcon="true"
			style="width: 100%;" 
			treeColumn="name" 
			idField="activityId"
			parentField="parentActivitiId" 
			allowCellSelect="true" 
			allowAlternating="true"
			resultAsTree="false" 
			expandOnLoad="true"
			url="${ctxPath}/bpm/core/bpmNodeSet/getActivityNodes.do?actDefId=${param.actDefId}&end=${param.end}&start=${param.start}"
			showPager="false"
			multiSelect='${multiSelect}'>
		
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="name" name="name" width="120" headerAlign="center">节点名称</div>
				<div 
					field="activityId" 
					name="activityId" 
					width="150"
					headerAlign="center"
				>节点Id</div>
				<div field="type" name="type" width="80" headerAlign="center">节点类型</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var multiSelect=${multiSelect};
		function getData(){
			var grid=mini.get("nodeGrid");
			var rows=[];
			if(multiSelect){
				rows=grid.getSelecteds();
			}
			else{
				var row=grid.getSelected();
				if(row.activityId=="_PROCESS"){
					mini.showTips({content: "不能选择该节点!",state: "warning", x: "center", y: "center", timeout:1000});
					return;
				}
				rows.push(row);
			}
			return rows;
		}
	
		function onOk(){
			CloseWindow('ok');
		}
	</script>
</body>
</html>