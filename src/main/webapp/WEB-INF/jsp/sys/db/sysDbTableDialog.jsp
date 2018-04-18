<%-- 
    Document   :通过数据源查询表名的对话框
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar"
		style="text-align: center; line-height: 30px;" borderStyle="border:0;">
		<label>名称：</label> <input id="tableName" class="mini-textbox"
			style="width: 150px;" onenter="onKeyEnter" /> 
			<a class="mini-button" onclick="search()">查询</a>
	</div>
	<div class="mini-fit">
		<div id="grid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" idField="id"
			showPager="false"
			 allowResize="true"
			borderStyle="border-left:0;border-right:0;"
			onrowdblclick="onRowDblClick">
			<div property="columns">
				<div type="indexcolumn" width="10%">序号</div>
				<div field="tableName" width="40%" headerAlign="center">名称</div>
				<div field="comment" width="40%" headerAlign="center">注释</div>
				<div field="type" width="40%" headerAlign="center">对象</div>
			</div>
		</div>
	</div>
	<div class="mini-toolbar"
		style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
		borderStyle="border:0;">
		<a  iconCls="icon-ok" class="mini-button"  onclick="onOk()">确定</a> <span
			style="display: inline-block; width: 25px;"></span> <a
			iconCls="icon-cancel" class="mini-button" onclick="onCancel()">取消</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("grid1");
		//动态设置URL
		var url="${ctxPath}/sys/db/sysDb/findTableList.do";
		//也可以动态设置列 grid.setColumns([]);
		function GetData() {
			var row = grid.getSelected();
			return row;
		}
		function search() {
			var tableName = mini.get("tableName").getValue();
			grid.setUrl(url);
			grid.load({
				tableName : tableName,ds:"${ds}"
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
