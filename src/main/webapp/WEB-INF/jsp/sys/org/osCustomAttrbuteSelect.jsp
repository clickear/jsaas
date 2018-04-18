<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>系统分类选择页</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom">
		<label>名称：</label> <input id="key" class="mini-textbox" style="width: 150px;" onenter="onKeyEnter" /> <a class="mini-button" onclick="search()">查询</a>
	</div>
	<div class="mini-fit">
	    <ul id="tree1" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_CUSTOMATTRIBUTE" style="width:300px;padding:5px;" 
	        showTreeIcon="true" resultAsTree="false" expandOnLoad="0" textField="name" idField="treeId" parentField="parentId" expandOnNodeClick="false">        
	    </ul>
	</div>
	<div class="mini-toolbar dialog-footer" style="border: none;">
		<a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a>
		<a class="mini-button" iconCls="icon-cancel" onclick="onCancel()">取消</a>
	</div>
	<script type="text/javascript">
		mini.parse();

		function GetData() {
			var tree = mini.get("tree1");
			return tree.getSelectedNode();
		}
		
		function search() {
/* 			var key = mini.get("key").getValue();
			tree.load({
				key : key
			}); */
		}
		function onKeyEnter(e) {
			search();
		}
		function onRowDblClick(e) {
			onOk();
		}
		//////////////////////////////////
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