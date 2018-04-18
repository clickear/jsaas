<%-- 
    Document   : 内部邮件收件箱列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>内部邮件收件箱列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-tabs-bodys{
		border: none;
	}
	
	#center>.mini-layout-region-body{
		padding-top:6px !important; 
	}
	
</style>
</head>
<body>

	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">

		<div region="west" showSplit="true" showHeader="false" width="200">
			<div 
				id="toolbar1" 
				class="mini-toolbar-no-border" 
				style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;"
				
			>
				<a class="mini-button" iconCls="icon-writemail" plain="true" onclick="writeMail" tooltip="写邮件">写邮件</a> 
				<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshFolder" tooltip="刷新">刷新</a>
			</div>

			<div class="mini-fit mini-fit-no-border">
				<div 
					id="tree" 
					class="mini-tree" 
					url="${ctxPath}/oa/mail/mailFolder/getInnerMailFolder.do" 
					style="width: 100%; height: 100%; padding: 5px;" 
					showTreeIcon="true" 
					textField="name" 
					idField="folderId" 
					parentField="parentId" 
					resultAsTree="false" 
					showArrow="false" 
					expandOnNodeClick="true" 
					expandOnLoad="true" 
					contextMenu="#treeMenu" 
					imgPath="${ctxPath}/upload/icons/" 
					onnodeselect="onNodeSelect" 
					ondrawnode="draw" 
					onLoad"="treeOnload"
				></div>
			</div>

			<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
				<li name="brother" iconCls="icon-add" onclick="addBrotherFolder">添加同级文件夹</li>
				<li name="children" iconCls="icon-add" onclick="addChildrenFolder">添加子文件夹</li>
				<li name="edit" iconCls="icon-edit" onclick="editFolder">编辑文件夹</li>
				<li name="remove" iconCls="icon-remove" onclick="delFolder">删除文件夹</li>
			</ul>
		</div><!-- end of west -->




		<div title="center" region="center" bodyStyle="overflow:hidden;" style="border: 0;">
			<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width: 100%; height: 100%;" onactivechanged="onTabsActiveChanged"></div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var tree = mini.get("tree");
		top['mailbox'] = window;

		$(function(){
			var recNode={};
			tree.findNodes(function(node){
				if(node.type=="RECEIVE-FOLDER"){
				    	recNode=node;
				    	return;
				    }
			});
			tree.selectNode(recNode);
			var isShowDel="YES";
			var tabs = mini.get("mainTabs");
			var id = "tab$" + recNode.folderId;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = recNode.folderId;
				tab.name = id;
				tab.title = "内部邮件-"+ (recNode.name).substring(0, (recNode.name).indexOf("("));
				tab.showCloseButton = true;

				tab.url = "${ctxPath}" + "/oa/mail/innerMail/list.do?folderId="
						+ recNode.folderId+"&isShowDel="+isShowDel; 
				tabs.addTab(tab);
				tabs.activeTab(tab);
			}
			else{
				tabs.activeTab(tab);
				tabs.reloadTab(tab);
			}
		});
		
		/*增加同级文件夹*/
		function addBrotherFolder(e) {
			var tree = mini.get("tree");
			var node = tree.getSelectedNode();
			_OpenWindow({
				url : "${ctxPath}" + "/oa/mail/mailFolder/edit.do?parentId="
						+ node.parentId + "&depth=" + node.depth + "&folderId=" + node.folderId,
				title : "新建同级文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
		}

		/*增加子文件夹*/
		function addChildrenFolder(e) {
			var tree = mini.get("tree");
			var node = tree.getSelectedNode();
			_OpenWindow({
				url : "${ctxPath}" + "/oa/mail/mailFolder/edit.do?parentId="
						+ node.folderId+ "&depth=" + (node.depth + 1) + "&folderId="
						+ node.folderId,
				title : "新建子文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load('${ctxPath}/oa/mail/mailFolder/getInnerMailFolder.do');
				}
			});
		}

		/*编辑文件夹*/
		function editFolder(e) {
			var tree = mini.get("tree");
			var node = tree.getSelectedNode();
			if (node.type == "RECEIVE-FOLDER" || node.type=="SENDER-FOLDER" || node.type == "DRAFT-FOLDER"|| node.type == "DEL-FOLDER") { //如果是四个基本目录
				return;
			}
			_OpenWindow({
				url : __rootPath + "/oa/mail/mailFolder/edit.do?pkId="
						+ node.folderId,
				title : "编辑文件夹",
				height : 300,
				width : 600,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load('${ctxPath}/oa/mail/mailFolder/getInnerMailFolder.do');
				}
			});
		}
		/*删除文件夹*/
		function delFolder(e) {
			var tree = mini.get("tree");
			var tabs = mini.get("mainTabs");
			var node = tree.getSelectedNode();
			if (node) {
				if (confirm("确定删除选中目录?")) {
					_SubmitJson({
						url : "${ctxPath}" + "/oa/mail/mailFolder/del.do",
						data : {
							ids : node.folderId
						},
						method : 'POST',
						success : function(text) {
 							tree.findNodes(function(f_node){   //删除文件夹以下的tabs
								if(f_node.parentId.indexOf(node.folderId)>-1){
									tabs.removeTab("tab$" +f_node.folderId);
								}
							});  
 							tabs.removeTab("tab$" +node.folderId);   //删除文件夹的tabs
							tree.load('${ctxPath}/oa/mail/mailFolder/getInnerMailFolder.do');
						}
					});

				}
			}
		}

		/*右键菜单控制*/
		function onBeforeOpen(e) {
			var menu = e.sender;
			var tree = mini.get("tree");
			var node = tree.getSelectedNode();
			if (!node) {
				e.cancel = true;
				return;
			}
			if (node && node.parentId == "0") {
				e.cancel = true;
				//阻止浏览器默认右键菜单
				e.htmlEvent.preventDefault();
				return;
			}

			var brotherItem = mini.getByName("brother", menu);
			var editItem = mini.getByName("edit", menu);
			var removeItem = mini.getByName("remove", menu);
			brotherItem.show();
			editItem.show();
			removeItem.show();

			if (node.parentId == "0") {  //如果是根目录
				brotherItem.hide();    //隐藏添加同级文件夹
				editItem.hide();         //隐藏编辑文件夹
				removeItem.hide();   //隐藏删除文件夹
			}
			if (node.type == "RECEIVE-FOLDER" || node.type=="SENDER-FOLDER" || node.type == "DRAFT-FOLDER"|| node.type == "DEL-FOLDER") { //如果是四个基本目录
				editItem.hide();
				removeItem.hide();
			}
 			var checkNode=null;                      //查找节点
			tree.findNodes(function(f_node){    //查找是否有该节点
				if(f_node.folderId==node.folderId){
					checkNode=f_node;   //如果有
					return;
				}
			}); 
			if(checkNode==null)
				e.cancel=true;
		}

		mini.parse();
		var tree = mini.get("tree");

		/*打开标签*/
		function showTab(node) {
			var delNode={};
			tree.findNodes(function(node){
			    if(node.type=="DEL-FOLDER"){
			    	delNode=node;
			    	return ;
			    }
			});
			var isShowDel="YES";
			var delFolderId=delNode.folderId;
			if(node.parentId.indexOf(delFolderId)>-1||node.folderId.indexOf(delFolderId)>-1)
				isShowDel="NO";
			var tabs = mini.get("mainTabs");
			var id = "tab$" + node.folderId;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = node.folderId;
				tab.name = id;
				if (node.type=='RECEIVE-FOLDER') {
					tab.title = "内部邮件-"+ (node.name).substring(0, (node.name).indexOf("("));
				} else
					tab.title = "内部邮件-" + node.name;
				tab.showCloseButton = true;

				tab.url = "${ctxPath}" + "/oa/mail/innerMail/list.do?folderId="
						+ node.folderId+"&isShowDel="+isShowDel; 
				tabs.addTab(tab);
				tabs.activeTab(tab);
			}
			else{
				tabs.activeTab(tab);
				tabs.reloadTab(tab);
			}
			
		}

		/*选择节点时触发*/
		function onNodeSelect(e) {
			var node = e.node;
			if(node.parentId=='0')
				return;
			showTab(node);
		}

		/*激活tabs*/
		function onTabsActiveChanged(e) {
			var tabs = e.sender;
			var tab = tabs.getActiveTab();
			if (tab && tab._nodeid) {

				var node = tree.getNode(tab._nodeid);
				if (node && !tree.isSelectedNode(node)) {
					tree.selectNode(node);
				}
			}
		}
		
		/*绘制联系人分组菜单节点图标*/
		function draw(e) {
			if (e.node.parentId == "0")
				e.iconCls = "icon-folder";
			if (e.node.type == "RECEIVE-FOLDER")
				e.iconCls = "icon-receive";
			if (e.node.type == "SENDER-FOLDER")
				e.iconCls = "icon-sender";
			if (e.node.type == "DRAFT-FOLDER")
				e.iconCls = "icon-draft";
			if (e.node.type == "DEL-FOLDER")
				e.iconCls = "icon-trash";
			if (e.node.type == "OTHER-FOLDER")
				e.iconCls = "icon-folder";
		}
		
		/*刷新联系人分组菜单*/
		function refreshFolder(){
			tree.load();
		}
		
		/*写邮件*/
		function writeMail() {
				_OpenWindow({
					url : __rootPath + "/oa/mail/innerMail/edit.do?",
					title : "写邮件",
					max:"true"
				});
			}
		
		//树加载
		
 		function treeOnload() {
            tree.setActiveIndex(0);
		}
		
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</body>
</html>