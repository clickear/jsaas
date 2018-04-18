<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>数据源列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		 <div region="south" showSplit="false" showHeader="false" height="45" showSplitIcon="false"  style="width:100%" bodyStyle="border:0">
			<div class="mini-toolbar dialog-footer" style="text-align:center;border:none;" >
			     <a class="mini-button" iconCls="icon-ok"   onclick="onOk()">确定</a>
				    <a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
			</div>	 
		 </div>
		 <div title="业务视图列表" region="center" showHeader="false" showCollapseButton="false">
		 
		 	<div id="grid1" class="mini-datagrid"
				style="width: 100%; height: 100%;" idField="id" allowResize="true"
				borderStyle="border-left:0;border-right:0;"
				onrowdblclick="onRowDblClick">
				<div property="columns">
					<div type="indexcolumn" width="8%">序号</div>
					<div field="name" width="40%" headerAlign="center" >数据源名称</div>
					<div field="alias" width="40%" headerAlign="center" >别名</div>
				</div>
			</div>
		
		 </div>

	</div>
	
	<script type="text/javascript">
		mini.parse();

		var grid = mini.get("grid1");

		//动态设置URL Q_SUBJECT__S_LK

		grid.setUrl("${ctxPath}/sys/core/sysDataSource/listData.do?Q_ENABLE__S_EQ=yes&Q_INIT_ON_START__S_EQ=yes");
		//也可以动态设置列 grid.setColumns([]);

		grid.load();

		function GetData() {
			var row = grid.getSelected();
			return row;
		}
		function search() {
			var key = mini.get("key").getValue();
			grid.load({
				key : key
			});
		}
		function onKeyEnter(e) {
			search();
		}
		function onRowDblClick(e) {
			onOk();
		}
		
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		function onOk() {
			CloseWindow("ok");
		}
		function onCancel() {
			CloseWindow("cancel");
		}
	</script>
</body>
</html>

