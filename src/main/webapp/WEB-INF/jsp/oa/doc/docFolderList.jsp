<%-- 
    Document   : 个人文档目录列表页
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
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div title="center" region="center" bodyStyle="overflow:hidden;" style="border: 0;">
			<!--Splitter-->
			<div class="mini-splitter" style="width: 100%; height: 100%;" borderStyle="border:0;">
				<div 
					size="270" 
					maxSize="350" 
					minSize="100" 
					showCollapseButton="true"
				>
					<div 
						id="panel1" 
						name="panel" 
						class="mini-panel" 
						title="个人文档" 
						iconCls="icon-abstain" 
						style="width: 440px; height: 100%;"
						showToolbar="true" 
						showFooter="false" 
						allowResize="true" 
						collapseOnTitleClick="true"
					>
						<!--toolbar-->
						<div property="toolbar" class="toolbar-margin">
							<div id="treetoolbar">
								<a 
									class="mini-button" 
									iconCls="icon-down" 
									onclick="mini.get('tree1').expandAll()" 
									style="border:none;background: #f8f8f8;"
								>展开目录</a> 
								<a 
									class="mini-button" 
									iconCls="icon-up" 
									onclick="mini.get('tree1').collapseAll()" 
									style="border:none;background: #f8f8f8;"
								>收起目录</a> 
								<input type='checkbox' name='multi' id='multi' checked="true" />
								<h4>包含子目录</h4>
							</div>
							<a class="mini-button" iconCls="icon-add" onclick="addroot" plain="true" id="treeadd">新建个人文档目录</a>
						</div>
						<ul 
							id="tree1" 
							class="mini-tree" 
							url="${ctxPath}/oa/doc/docFolder/listPersonal.do" 
							style="width: 100%; padding: 5px; height: 100%"
							showTreeIcon="true" 
							textField="name" 
							idField="folderId" 
							value="base" 
							expandOnLoad="true" 
							parentField="parent" 
							resultAsTree="false"
							contextMenu="#treeMenu" 
							ondrawnode="onDrawNode" 
							expand="onExpand" 
							onnodeselect="onNodeSelect"
						></ul>
					</div>

				</div>

				<div showCollapseButton="false" style="border: 0px;">
					<!--Tabs-->
					<div 
						id="mainTabs" 
						class="mini-tabs bg-toolbar" 
						activeIndex="0" 
						style="width: 100%; height: 100%;"
						onactivechanged="onTabsActiveChanged"
					></div>
				</div>
			</div>
		</div>
	</div>


	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
		<li name="add" iconCls="icon-add" onclick="onAddAfter">新增同级文件夹</li>
		<li name="subadd" iconCls="icon-add" onclick="onAddNode">加入子文件夹</li>
		<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑文件夹</li>
		<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除文件夹</li>
	</ul>



	<script type="text/javascript">
		mini.parse();
		var tree=mini.get("tree1");
		
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
			var iscompany = "x";//可用来判断是否公司
			var type = "PERSONAL";
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
					location.reload(true);
					}
				}

			});
			
		}
		
		function getNodeNow(){
			var NowNode=mini.get("tree1").getSelectedNode();
			showTab(NowNode);
		}
	
		
		
		//增加同级节点
		function onAddAfter(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			var newNode = {};
			var parent=node.parent;
             var depth=tree.getLevel(node);
			 var pkId=node.folderId;
			var path="x";
			var type="PERSONAL";
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?folderId'+node.folderId+'&parent='+parent+'&path='+path+'&depth='+depth+"&type="+type,
				title : "新增同级文件夹",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					 if (action == 'ok'){
						 tree.load();
				           	//location.reload(true);
				               }
				}

			});	
		}
		//设置节点图标
		function onDrawNode(e) {
	        var tree = e.sender;
	        var node = e.node;
	        	e.iconCls='icon-folder';
	        	var nodename=node.name;
	        	if(nodename.length>10){
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
            var parent=node.parent;
            var parentnode=tree.getParentNode(node);
            var depth=tree.getLevel(node)+1;
           var path="x";
            var bronode=tree.getChildNodes(node).length;
			var newNode = {};//新建空节点
            var type="PERSONAL"
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='+node.folderId+"&depth="+depth+"&path="+path+"&type="+type,
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
            var pkId=node.folderId;
            var parent=node.parent;
            var path=node.path;
            var depth=tree.getLevel(node);
            var share=node.share;
			
			
			_OpenWindow({
				url : '${ctxPath}/oa/doc/docFolder/edit.do?pkId='+node.folderId+"&path="+path+"&parent="+parent,//
				title : "编辑文件夹",
				width : 600,
				height : 400,
				method :'POST',
				onload : function() {

				},
				ondestroy : function(action) {
					if (action == 'ok')
					location.reload(true);

				}

			});
			
		}
		//删除节点
		function onRemoveNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			var isLeaf = tree.isLeaf(node);
			var ids=node.folderId;
			if (node) {
				if (confirm("确定删除选中文件夹?")) {
					$.ajax({
		                type: "Post",
		                url : '${ctxPath}/oa/doc/docFolder/delContain.do?folderId='+ids+'&type='+node.type,
		                data: {},
		                beforeSend: function () {
		                    
		                },
		                success: function () {
		                    
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

			if (tree.getLevel(node)==0) {//在根节点的时候不允许其他操作
				mini.getByName("add").hide();
				//mini.getByName("remove").hide();
			}else{
				mini.getByName("add").show();
				//mini.getByName("remove").show();
			}
		}
		
		
//显示节点页面
		function showTab(node) {
			var tabs = mini.get("mainTabs");
			var type="PERSONAL";
			var multi=document.getElementById("multi").checked;//是否选中子集的开关
			if(multi==false){
				multi="0";//不选子集
			}else{
				multi="1";//选子集
			}
			var id = node.folderId;
			var tab = tabs.getTab(id);
			if (!tabs.getTab(id)) {
				tab = {};
				tab._nodeid = node.folderId;
				tab.name = id;
				tab.title = node.name;
				tab.showCloseButton = true;
				tab.url = __rootPath+"/oa/doc/docFolder/index.do?folderId="+node.folderId+"&multi="+multi+"&type="+type;//跳转到此页面显示在panel里
				tabs.addTab(tab);
				tabs.removeAll(tab);//只显示当前
			}
			tabs.activeTab(tab); 
		}
//显示选中的节点的tab
		function onNodeSelect(e) {
      	    var node = e.node;
				showTab(node);
		}
		

		function onTabsActiveChanged(e) {
			var tree =mini.get("tree1");
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