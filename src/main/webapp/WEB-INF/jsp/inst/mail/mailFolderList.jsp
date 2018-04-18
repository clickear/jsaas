<%-- 
    Document   : [MailFolder]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[MailFolder]列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.header {
	background: url(../header.gif) repeat-x 0 -1px;
}
</style>
</head>
<body>
	<div id="layout1" class="mini-layout"
		style="width: 100%; height: 100%;">


		<div title="center" region="center" bodyStyle="overflow:hidden;"
			style="border: 0;">
			<!--Splitter-->
			<div class="mini-splitter" style="width: 100%; height: 100%;"
				borderStyle="border:0;">
				<div size="180" maxSize="250" minSize="100"
					showCollapseButton="true" style="border-width: 1px;">

					<div id="toolbar1" class="mini-toolbar"
						style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
						<table style="width: 100%;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="width: 100%;"><a class="mini-button"
									iconCls="icon-add" plain="true" onclick="addNode()">添加</a> <a
									class="mini-button" iconCls="icon-refresh" plain="true"
									onclick="refreshSysTree()">刷新</a></td>
							</tr>
						</table>
					</div>
					<!--Tree-->

					<c:forEach items="${allConfigs }" var="config" varStatus="index">
						<ul id="tree${index.count}" class="mini-tree"
							url="${ctxPath}/inst/mail/mailFolder/list2.do"
							style="width: 200px; padding: 5px;" showTreeIcon="true"
							textField="name" idField="folderId" parentField="parentId"
							resultAsTree="false" showArrow="false" expandOnNodeClick="false"
							expandOnLoad="true" contextMenu="#treeMenu"
							onnodeselect="onNodeSelect" onendedit="saveorupdate">
						</ul>
					</c:forEach>

					<ul id="treeMenu" class="mini-contextmenu"
						onbeforeopen="onBeforeOpen">
						<li name="brother" iconCls="icon-add" onclick="onAddAfter">插入同级节点</li>
						<li name="children" iconCls="icon-add" onclick="onAddNode">插入子节点</li>
						<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑节点</li>
						<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除节点</li>
					</ul>

				</div>
				<div showCollapseButton="false" style="border: 0px;">
					<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
						<table style="width: 100%;">
							<tr>
								<td style="width: 100%;"><a class="mini-button"
									iconCls="icon-addfolder" plain="true">增加</a> <a
									class="mini-button" iconCls="icon-edit" plain="true">修改</a> <a
									class="mini-button" iconCls="icon-remove" plain="true">删除</a></td>
							</tr>
						</table>
					</div>
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0"
						style="width: 100%; height: 100%;"
						onactivechanged="onTabsActiveChanged"></div>
				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		/*$(function(){
			<c:forEach items="${allConfigs }" var="config" varStatus="index">
				var tree${index.count}=mini.get('tree${index.count}');
				tree${index.count}.load();
			</c:forEach>
		});*/
		function onAddAfter(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			_OpenWindow({
				url : "${ctxPath}" + "/inst/mail/mailFolder/edit.do?id="
						+ node.folderId,
				title : "新建同级节点",
				height : 400,
				width : 600,
				ondestroy : function() {
					/*this.reload();*/
				}
			});
			var newNode = {
				name : "新建文件夹"
			};
			tree.addNode(newNode, "after", node);
			/*tree.beginEdit(node);*/
		}
		function onAddNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();

			_OpenWindow({
				url : "${ctxPath}" + "/inst/mail/mailFolder/edit.do",
				title : "新建子节点",
				height : 400,
				width : 600,
				ondestroy : function() {
					/*this.reload();*/
				}
			});
			var newNode = {
				name : "新建文件夹"
			};
			tree.addNode(newNode, "add", node);
			/*tree.beginEdit(node);*/
		}
		function onEditNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if (node.name == "我的邮箱" || node.name == "收件箱" || node.name == "发件箱"
					|| node.name == "草稿箱" || node.name == "垃圾箱") {
				return;
			}
			tree.beginEdit(node);
		}
		function onRemoveNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();

			if (node) {
				if (confirm("确定删除选中节点?")) {
					tree.removeNode(node);
				}
			}
		}
		function onBeforeOpen(e) {
			var menu = e.sender;
			var tree = mini.get("tree1");

			var node = tree.getSelectedNode();
			if (!node) {
				e.cancel = true;
				return;
			}
			/* if (node && node.fatherId == "0") {
			     e.cancel = true;
			     //阻止浏览器默认右键菜单
			     e.htmlEvent.preventDefault();
			     return;
			 }*/

			////////////////////////////////
			var brotherItem = mini.getByName("brother", menu);
			var editItem = mini.getByName("edit", menu);
			var removeItem = mini.getByName("remove", menu);
			brotherItem.show();
			editItem.show();
			removeItem.show();

			if (node.parentId == "0") {
				brotherItem.hide();
				editItem.hide();
				removeItem.hide();
			}
			if (node.name == "垃圾箱" || node.name == "收件箱" || node.name == "发件箱"
					|| node.name == "草稿箱") {
				editItem.hide();
				removeItem.hide();
			}
		}

		mini.parse();
		var tree = mini.get("tree1");

		/*打开标签*/
		function showTab(node) {
			var tabs = mini.get("mainTabs");

			var id = "tab$" + node.folderId;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = node.folderId;
				tab.name = id;
				tab.title = node.name;
				tab.showCloseButton = true;

				//这里拼接了url，实际项目，应该从后台直接获得完整的url地址
				tab.url = "${ctxPath}" + "/inst/mail/mail/list.do";

				tabs.addTab(tab);
			}
			tabs.activeTab(tab);
		}

		/*选择节点时触发*/
		function onNodeSelect(e) {

			var node = e.node;
			/*var isLeaf = e.isLeaf;*/
			showTab(node);
		}
		/*单元格结束编辑只能执行*/
		function saveorupdate(e) {
			var node = e.node;
			/*alert(node.folderId);*/
			_SubmitJson({
				url : "${ctxPath}" + "/inst/mail/mailFolder/saveorupdate.do",
				data : {
					fid : node.folderId,
					fname : node.name
				},
				method : 'POST',
				success : function(text) {

				}
			});
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="tree"
		entityName="com.redxun.inst.mail.entity.MailFolder" winHeight="450"
		winWidth="700" entityTitle="[MailFolder]"
		baseUrl="inst/mail/mailFolder" />
</body>
</html>