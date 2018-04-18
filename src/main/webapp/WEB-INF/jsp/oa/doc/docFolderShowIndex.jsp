<%-- 
    Document   : 文件夹和文件共同（treegrid）列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文档列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar">
		<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="location.reload(true)">页面刷新</a> <span class="separator"></span> <input class="text text-border" id="key" name="key" /> <a class="mini-button" plain="true" iconCls="icon-search" onclick="searchForSomeKey" tooltip="包含该字段的条目">查找</a>

	</div>
	<div class="mini-fit rx-grid-fit form-outer">
		<div id="datagrid1" class="mini-treegrid" style="width: 100%; height: 100%;" showTreeLines="true" showTreeIcon="true" treeColumn="name" idField="dafId" parentField="parent" resultAsTree="false" ondrawnode="onDrawNode" onnodedblclick="openNew()" allowResize="true" expandOnLoad="true" allowCellSelect="true">

			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer">操作</div>
				<div name="name" field="name" width="160" headerAlign="center" align="left">文档名称</div>
				<div name="createName" field="createName" width="60" headerAlign="center">创建人</div>
				<div name="createTime" field="createTime" dateFormat="yyyy-MM-dd" width="60" headerAlign="center">创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		/*获取页面传来的值*/
		var folderId = "${folderId}";
		var multi = "${multi}";
		var type = "${type}";
		var isShare = "${isShare}";
		var grid = mini.get('datagrid1');
		grid.setUrl("${ctxPath}/oa/doc/doc/listTree.do?folderId=" + folderId + "&multi=" + multi + "&type=" + type); //listData                                            
		grid.load();
		//设置文件夹图标
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			switch (node.type) {
			case "文件夹":
				e.iconCls = 'icon-folder';
				break;
			case "html":
				e.iconCls = 'icon-html';
				break;
			case "doc":
				e.iconCls = 'icon-myword';
				break;
			case "docx":
				e.iconCls = 'icon-myword';
				break;
			case "xls":
				e.iconCls = 'icon-myexcel';
				break;
			case "xlsx":
				e.iconCls = 'icon-myexcel';
				break;
			case "ppt":
				e.iconCls = 'icon-myppt';
				break;
			case "pptx":
				e.iconCls = 'icon-myppt';
				break;
			}
			if (node.name.length > 15) {
				var shortnodeName = node.name.substring(0, 14) + "…";
				e.nodeHtml = '<a title="' +node.name+ '">' + shortnodeName + '</a>';
			} else {
				e.nodeHtml = '<a title="' +node.name+ '">' + node.name + '</a>';
			}
		}
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var type = record.type;
			var s = '';
			s += '<span class="icon-detail"  title="明细" onclick="detailMyRow()"></span>';
			return s;
		}

		//明细功能按钮
		function detailMyRow() {
			var tree = mini.get("datagrid1");
			var node = tree.getSelectedNode();
			var pkId = node.dafId;
			var grid = mini.get('datagrid1');

			if (node.type == "文件夹") {
				_OpenWindow({
					url : "${ctxPath}/oa/doc/docFolder/get.do?pkId=" + pkId,
					title : "文件夹明细",
					width : 690,
					height : 340,
					ondestroy : function(action) {
						if (action == 'ok') {
							grid.reload();
						}
					}
				});
			} else {
				_OpenWindow({
					url : "${ctxPath}/oa/doc/doc/get.do?pkId=" + pkId,
					title : "文件明细",
					width : 800,
					height : 800,
					ondestroy : function(action) {
						if (action == 'ok') {
							grid.reload();
						}
					}
				});
			}

		}

		//按某些字段查找
		function searchForSomeKey() {
			var key = document.getElementById("key").value;
			var grid = mini.get('datagrid1');
			var encodekey = encodeURIComponent(key);
			grid.setUrl("${ctxPath}/oa/doc/doc/getByKey.do?key=" + encodekey + "&folderId=" + folderId + "&multi=" + multi + "&type=" + type + "&isShare=" + isShare);
			grid.load();
		}

		//打开文档视图模式	
		function openNew() {
			var tree = mini.get("datagrid1");
			var node = tree.getSelectedNode();
			if (node.type == "文件夹") {
				_OpenWindow({
					url : "${ctxPath}/oa/doc/docFolder/listBoxWindow.do?folderId=" + node.dafId + "&type=" + type + "&firstOpenId=" + node.dafId,
					title : "文档视图",
					width : 790,
					height : 580,
					ondestroy : function(action) {
						if (action == 'close')
							tree.load();
					}
				});
			} else {
				_OpenWindow({
					url : "${ctxPath}/oa/doc/doc/get.do?pkId=" + node.dafId,
					title : "文件明细",
					width : 690,
					height : 600,
					ondestroy : function(action) {
						if (action == 'close' || action == 'ok') {
							location.reload(true);
						}
					}
				});
			}
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.doc.entity.Doc" winHeight="450" winWidth="700" entityTitle="文档" baseUrl="oa/doc/doc" />
</body>
</html>