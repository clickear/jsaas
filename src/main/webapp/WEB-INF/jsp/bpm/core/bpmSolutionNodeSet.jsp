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
			url="${ctxPath}/bpm/core/bpmNodeSet/getActivityNodes.do?actDefId=${bpmDef.actDefId}"
			showPager="false"
		>
			<div property="columns">
				<div 
					name="action" 
					width="80" 
					headerAlign="center" 
					align="center"
					renderer="onActionRenderer" 
					cellStyle="padding-left:4px;"
				>操作</div>
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
		mini.parse();
		var grid = mini.get('nodeGrid');
		var solId = '${bpmSolution.solId}';
		grid.load();
		function onActionRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var node = grid.getRowByUID(uid);

			var s = '<span class="icon-button icon-mgr" title="节点属性配置" onclick="nodeProperties(\'' + uid + '\')"></span>';
			if(node.type=='process' || node.type=='userTask'){
				s +=  '<span class="icon-button icon-property" title="催办配置" onclick="reminder(\'' + uid + '\')"></span>';
			}
			if (node.type == 'process'){
				s += '<span class="icon-button icon-bpm-draft" title="抄送设置" onclick="copyto(\'' + uid + '\')"></span>';
			}

			return s;
		}

		function nodeProperties(uid) {
			var node = grid.getRowByUID(uid);
			var title = "";
			if (node.type == 'process') {
				title = '流程[' + node.name + ']-属性配置';
			} else {
				title = '流程节点[' + node.name + ']-属性配置';
			}

			_OpenWindow({
				title : title,
				iconCls : 'icon-mgr',
				max : true,
				width : 600,
				height : 500,
				url : __rootPath + '/bpm/core/bpmNodeSet/getNodeConfig.do?actDefId=${bpmDef.actDefId}&nodeId=' + node.activityId + '&nodeType=' + node.type + '&solId=' + solId
			});

		}

		function reminder(uid) {
			var node = grid.getRowByUID(uid);
			var title = "";
			var url="";
			if (node.type == 'process') {
				title = '流程[' + node.name + ']-催办配置';
				url=__rootPath + '/bpm/core/bpmRemindDef/globalEdit.do?actDefId=${bpmDef.actDefId}&solId=' + solId + '&nodeName=' + node.name
				
			}else{
				title = '流程节点[' + node.name + ']-催办配置';
				url=__rootPath + '/bpm/core/bpmRemindDef/edit.do?actDefId=${bpmDef.actDefId}&nodeId=' + node.activityId + '&solId=' + solId + '&nodeName=' + node.name;
			}

			_OpenWindow({
				title : title,
				iconCls : 'icon-property',
				width : 840,
				height : 600,
				url : url
			});

		}

		function copyto(uid) {
			var node = grid.getRowByUID(uid);
			var title = '流程节点[' + node.name + ']-抄送设置';
			_OpenWindow({
				title : title,
				iconCls : 'icon-property',
				max : false,
				width : 800,
				height : 500,
				url : __rootPath + '/bpm/core/bpmSolution/nodeUserGroup.do?actDefId=${bpmDef.actDefId}&nodeId=_PROCESS&groupType=copy&solId=' + solId 
			});

		}
		
		function sql(uid) {
			var node = grid.getRowByUID(uid);
			var title = "";
			if (node.type == 'process') {
				title = '流程[' + node.name + ']-sql配置';
			} else {
				title = '流程节点[' + node.name + ']-sql配置';
			}

			_SubmitJson({
				url : __rootPath + '/bpm/sql/bpmSqlNode/isFormExists.do?solId=' + solId,

				method : 'POST',
				showMsg : true,
				success : function(result) {

					_OpenWindow({
						title : title,
						iconCls : 'icon-property',
						max : true,
						width : 600,
						height : 500,
						url : __rootPath + '/bpm/sql/bpmSqlNode/edit.do?actDefId=${bpmDef.actDefId}&nodeId=' + node.activityId + '&nodeType=' + node.type + '&solId=' + solId + '&nodeName=' + node.name
					});

				}
			});
		}
	</script>
</body>
</html>