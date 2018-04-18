<%-- 
    Document   : 自定义Sql对话框页
    Created on : 2015-3-21, 0:11:48
    @author cjx
  	@Email chshxuan@163.com
 	@Copyright (c) 2014-2016 使用范围：
  	本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>自定义Sql对话框页</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div class="mini-toolbar toolbar-margin">
		<label> 别名：</label> <input id="key" class="mini-textbox" style="width: 150px;" onenter="onKeyEnter" /> <a class="mini-button" iconCls="icon-search" onclick="search()">查询</a>
	</div>
	<div class="mini-fit form-outer">

		<div id="grid1" class="mini-datagrid" style="width: 100%; height: 100%;" idField="id" url="${ctxPath}/sys/db/sysSqlCustomQuery/listData.do" allowResize="true"  onrowdblclick="onRowDblClick">
			<div property="columns">
				<div type="indexcolumn" width="40">序号</div>
				<div sortField="NAME_" field="name" width="50%" headerAlign="center" allowSort="true">名称</div>
				<div sortField="KEY_" field="key" width="50%" headerAlign="center" allowSort="true">别名</div>
			</div>
		</div>

	</div>
		<div class="mini-toolbar"
		style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
		borderStyle="border:0;">
		<a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a>
	   <a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div>
	<script type="text/javascript">
		mini.parse();

		var grid = mini.get("grid1");

		//也可以动态设置列 grid.setColumns([]);

		grid.load();

		function GetData() {
			var row = grid.getSelected();
			return row;
		}
		function search() {
			var key = mini.get("key").getValue();
			var url='${ctxPath}/sys/db/sysSqlCustomQuery/listData.do';
			if(key&&key!='')
				url='${ctxPath}/sys/db/sysSqlCustomQuery/search.do?Q_key_S_LK='+key;
			grid.setUrl(url);
			grid.load();
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

