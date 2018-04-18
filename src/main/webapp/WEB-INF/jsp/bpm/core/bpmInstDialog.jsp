<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>流程实例选择</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
<div class="mini-fit rx-grid-fit" style="height: 100%;">
					<div id="datagrid1" class="mini-datagrid" style="width: 100%; height:100%;" allowResize="false"
						url="${ctxPath}/bpm/core/bpmInst/listInstBySolId.do?solId=${param.solId}" idField="instId" multiSelect="${param.flag}" showColumnsMenu="true"
						sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
						<div property="columns">
							<div type="checkcolumn" width="20"></div>
							<div field="treeName" width="60" headerAlign="center" >分类</div>
							<div field="subject" sortField="subject_" width="160" headerAlign="center" allowSort="true">事项</div>
							<div field="status" sortField="status_"   width="60" headerAlign="center" allowSort="true">运行状态</div>
							<div field="taskNodes" width="80" headerAlign="center" >当前节点</div>
							<div field="taskNodeUsers"    width="80" headerAlign="center">当前节点处理人</div>						
							<div field="createBy" sortField="create_by_"  width="60" headerAlign="center" allowSort="true">发起人</div>
							<div field="createTime" sortField="create_time_"  width="60" dateFormat="yyyy-MM-dd HH-mm-ss" headerAlign="center" allowSort="true">创建时间</div>
						</div>
					</div>
				</div>
	<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
		<div style="text-align: center;"><a class="mini-button" iconCls="icon-ok" plain="true" onclick="CloseWindow('ok');">确定</a> 
		<a class="mini-button" iconCls="icon-cancel" plain="true" onclick="CloseWindow('cancel');">取消</a></div> 
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		
		function getInstances(){
			return grid.getSelecteds();
		}
	</script>
</body>
</html>