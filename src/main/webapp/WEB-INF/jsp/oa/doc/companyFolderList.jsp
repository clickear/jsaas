<%-- 
    Document   : 公司文档目录列表页
    Created on : 2015-11-6, 16:11:48
    Author     : 陈茂昌
--%>
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文档目录管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="layout1" class="mini-layout"
		style="width: 100%; height: 100%;">
		<div title="center" region="center" bodyStyle="overflow:hidden;"
			style="border: 0;">
			<!--Splitter-->
			<div class="mini-splitter" style="width: 100%; height: 100%;"
				borderStyle="border:0;">
				<div size="270" maxSize="350" minSize="100"
					showCollapseButton="true" style="border-width: 1px;">
					<!--Tree-->

					<div id="panel1" class="mini-panel" title="公司文档"
						iconCls="" style="width: 440px; height: 100%;"
						showToolbar="true" showCollapseButton="true" showFooter="false"
						allowResize="true" collapseOnTitleClick="true">
						<!--toolbar-->
						
						<div property="toolbar"  class="toolbar-margin">
						<div id="treetoolbar">
						<a class="mini-button" iconCls="icon-down"
								onclick="mini.get('tree1').expandAll()">展开目录</a>
								 <a class="mini-button" iconCls="icon-up"
								onclick="mini.get('tree1').collapseAll()">收起目录</a> 
								<input type='checkbox' name='multi' id='multi' checked="true"  />
								<h4>包含子目录</h4>
						</div>
							<a class="mini-button" iconCls="icon-add" onclick="addroot" plain="true"  id="treeadd">新建公司文档目录</a>
						</div>
					
						<!--Tree-->
						<ul id="tree1" class="mini-tree"
							url="${ctxPath}/oa/doc/docFolder/listCompany.do"
							style="width: 100%; padding: 5px; height: 100%;" showTreeIcon="true" 
							textField="name" idField="folderId" value="base" expandOnLoad="true"
							parentField="parent"
							resultAsTree="0" contextMenu="#treeMenu"
							ondrawnode="onDrawNode" 
							onnodeselect="onNodeSelect">
						</ul>

					</div>

				</div>

				<div showCollapseButton="false" style="border: 0px;">
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0"
						style="width: 100%; height: 100%;"
						onactivechanged="onTabsActiveChanged"></div>
				</div>
			</div>
		</div>
	</div>
	
<!-- 树右键菜单 -->
	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
		<li name="add" iconCls="icon-add" onclick="onAddAfter">新增同级文件夹</li>
		<li name="subadd" iconCls="icon-add" onclick="onAddNode">增加子文件夹</li>
		<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑文件夹</li>
		<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除文件夹</li>
	</ul>



	<script type="text/javascript">
		mini.parse();
		var tree = mini.get("tree1");

		
		//在没有根目录的清空下建立新目录的按钮
		$(function(){
			if(tree.getChildNodes(tree.getRootNode()).length<1){
				$("#treetoolbar").hide();
			}else {
				$("#treeadd").hide();
			}
		});
		
		
		//建立根目录
		function addroot(){
			var iscompany = 0;//可用来判断是否公司
			var newNode = {};//新建空节点
			//tree.addNode(newNode, "add", node);
			var type = "COMPANY";
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='
						+"0"+ "&path=" + iscompany
						+ "&type=" + type,
				title : "新建目录",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					if (action == 'ok'){
					tree.load();
					}
				}

			});
			
		}
		
		
		//增加同级节点
		function onAddAfter(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			var newNode = {};
			var parent = node.parent;
			var pkId = node.folderId;//节点的唯一标识符
			var iscompany = 0;
			var type = "COMPANY";
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?folderId'+node.folderId+'&parent='+parent+'&path='+iscompany+"&type="+type,
				title : "新增同级节点",
				width : 600,
				height : 400,
				onload : function() {

				},
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}

			});
		}
		
		
		/*设置节点的图标*/
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			e.iconCls = 'icon-folder';
			if(node.name.length>10){
        		var shortnodeName=node.name.substring(0,9)+"…";
        	e.nodeHtml= '<a title="' +node.name+ '">' +shortnodeName+ '</a>';
        	}else{
        		e.nodeHtml= '<a title="' +node.name+ '">' +node.name+ '</a>';
        	}

		}


		//增加子节点
		function onAddNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			var iscompany = 0;//可用来判断是否公司
			var newNode = {};//新建空节点
			var type = "COMPANY";
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='
						+ node.folderId+ "&path=" + iscompany
						+ "&type=" + type,
				title : "新增子文件夹",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					if (action == 'ok'){
						tree.load();
					}
				}

			});
		}

		//编辑节点文本（URL）
		function onEditNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			var pkId = node.folderId;
			var parent = node.parent;
			var path = node.path;
			var share = node.share;
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?pkId=' + pkId
						+ "&path=" + path + "&parent=" + parent,//
				title : "编辑文件",
				width : 600,
				height : 400,
				method : 'POST',
				onload : function() {

				},
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();

				}

			});

		}
		//删除节点
		function onRemoveNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			var isLeaf = tree.isLeaf(node);
			var folderId = node.folderId;
			if (node) {
				if (confirm("确定删除选中节点?")) {
					$.ajax({
								type : "Post",
								url : '${ctxPath}/oa/doc/docFolder/delContain.do?folderId='
										+ folderId + '&type=' + node.type,
								data : {},
								beforeSend : function() {

								},
								success : function() {
								}
							});
					tree.removeNode(node);
				}
			}
		}

		//阻止浏览器默认右键菜单
		function onBeforeOpen(e) {
			var menu = e.sender;
			var tree = mini.get("tree1");

			var node = tree.getSelectedNode();
			if (!node) {
				e.cancel = true;
				return;
			}
			if (node && node.text == "Base") {
				e.cancel = true;
				e.htmlEvent.preventDefault();
				return;
			}
			////////////////////////////////////////////////////
			var editItem = mini.getByName("edit", menu);

			if (tree.getLevel(node) == 0) {
				
				mini.getByName("add").hide();
				mini.getByName("remove").hide();
			} else {

				mini.getByName("add").show();
				mini.getByName("remove").show();
			}

		}

		//显示节点页面
		function showTab(node) {
			var tabs = mini.get("mainTabs");
			var multi = document.getElementById("multi").checked;//是否选中子集的开关
			if (multi == false) {
				multi = "0";
			} else {
				multi = "1";
			}
			var id = node.folderId;
			var tab = tabs.getTab(id);

			if (!tabs.getTab(id)) {//如果id不为空的话
				tab = {};
				tab._nodeid = node.folderId;
				tab.name = id;
				tab.title = node.name;
				tab.showCloseButton = true;
				tab.url = __rootPath
						+ "/oa/doc/docFolder/index.do?folderId="
						+ node.folderId + "&multi=" + multi + "&type="
						+ "COMPANY";

				tabs.addTab(tab);
				tabs.removeAll(tab);//只显示当前
			}
			tabs.activeTab(tab);
		}
        //点击节点时
		function onNodeSelect(e) {
			var node = e.node;
			showTab(node);
		}

		function onTabsActiveChanged(e) {
			var tree = mini.get("tree1");
			var tabs = e.sender;
			var tab = tabs.getActiveTab();
			if (tab && tab._nodeid) {

				var node = tree.getNode(tab._nodeid);
				if (node && !tree.isSelectedNode(node)) {
					tree.selectNode(node);
				}
			}
		}
	</script>

</body>
</html>