<%-- 
    Document   :通过数据源查询列名的对话框
    Created on : 2015-3-21, 0:11:48
    @author cjx
  	@Email chshxuan@163.com
 	@Copyright (c) 2014-2016 使用范围：
  	本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-fit">
		<div id="grid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" idField="id" showPager="false"
			allowResize="true" borderStyle="border-left:0;border-right:0;"
			onrowdblclick="onRowDblClick" allowCellEdit="true"
			allowCellSelect="true" multiSelect="true"
			oncellbeginedit="OnCellBeginEdit">
			<div property="columns">
				<div type="checkcolumn"></div>
				<div field="fieldName" width="40%" headerAlign="center"
					allowSort="true">列的名称</div>
				<div field="comment" width="40%" headerAlign="center"
					allowSort="true">列的注释</div>
				<div field="columnType" width="40%" headerAlign="center"
					allowSort="true">数据类型</div>

					<!--
	<div valueField="id" textField="text" field="typeOperate"
					displayField="typeOperate_name" vtype="required" width="100"
				 align="center" headerAlign="center">
					操作 <input property="editor" class="mini-combobox"
						style="width: 100%;" />
					-->
			
				</div>
			</div>
		</div>
	</div>
	<div class="mini-toolbar"
		style="text-align: center; padding-top: 8px; padding-bottom: 8px;"
		borderStyle="border:0;">
		<a iconCls="icon-ok" class="mini-button" onclick="onOk()">确定</a> <span
			style="display: inline-block; width: 25px;"></span> <a
			iconCls="icon-cancel" class="mini-button" onclick="onCancel()">取消</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("grid1");
		var returnArray = new Array();
		//动态设置URL
		//alert('ds='+'${ds}');
		var tableType="${param.tableType}"=="0"?'view':'table';
		
		grid.setUrl("${ctxPath}/sys/db/sysDb/findColumnList.do?tableType="+tableType+"&tableName=${param.tableName}&ds=${param.ds}");
		
		
		//也可以动态设置列 grid.setColumns([]);
		grid.load();
		function GetData() {
			var row = grid.getSelecteds();
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

		function OnCellBeginEdit(e) {
			var grid = e.sender;
			var record = e.record;
			var field = e.field, value = e.value;
			var editor = e.editor;
			if (field == "typeOperate") {
				var columnName = record.columnName;
				if (columnName) {
					var url = "${ctxPath}/sys/db/sysDb/findColum.do?ds=" + "${ds}" + "&&tableName=" + "${tableName}";
					url = url + "&&columnName=" + columnName + "";
					//alert('url='+url);
					editor.setUrl(url);
				} else {
					e.cancel = true;
				}
			}
		}
	</script>
</body>
</html>
