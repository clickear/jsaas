<%-- 
    Document   : [KdDoc]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>知识文档列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>	
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div 
			region="west" 
			showSplit="true" 
			showHeader="false" 
			width="200" 
			borderStyle="border-right:0px"
			
	    	
	    	showCollapseButton="false"
	    	showProxy="false" 
		>
			<div id="toolbar1" class="mini-toolbar" style="padding: 2px; border: 0;">
				<a class="mini-button" iconCls="icon-add" onclick="addNewType" tooltip="添加一级分类">添加分类</a> 
				<a class="mini-button" iconCls="icon-refresh" onclick="refresh" tooltip="刷新">刷新</a>
			</div>

			<div class="mini-fit"  borderStyle="border:0px;">
				<div 
					id="tree" 
					class="mini-tree" 
					url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_KMS_KDDOC" 
					style="width: 100%; height: 100%; padding: 5px;" 
					showTreeIcon="true" 
					textField="name" 
					idField="treeId" 
					parentField="parentId" 
					resultAsTree="false"  
					expandOnNodeClick="false" 
					expandOnLoad="0" 
					contextMenu="#treeMenu" 
					onnodeselect="onNodeSelect" 
					ondrawnode="" 
					onload="" 
				></div>
			</div>

			<ul id="treeMenu" class="mini-contextmenu"onbeforeopen="">
				<li name="brother" iconCls="icon-add" onclick="addNewType">添加一级分类</li>
				<li name="children" iconCls="icon-add" onclick="addNewSubType">添加子分类</li>
				<li name="edit" iconCls="icon-edit" onclick="editType">编辑分类</li>
				<li name="remove" iconCls="icon-remove" onclick="delType">删除分类</li>
			</ul>
		</div>

		<div title="center" region="center" bodyStyle="overflow:hidden;" style="border: 0;">
			<div 
				id="mainTabs" 
				class="mini-tabs bg-toolbar" 
				activeIndex="0" 
				style="width: 100%; height: 100%;" 
				onactivechanged="onTabsActiveChanged"
			></div>
		</div>
	</div>

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<script type="text/javascript">
		mini.parse();
		var tree=mini.get("tree");	
		
		function refresh(){
			tree.load();
		}
		
		//增加一级分类
		function addNewType() {
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?nodetype=parent&catKey=CAT_KMS_KDDOC",
				title : "添加一级知识分类",
				height : 290,
				width : 710,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
		}
		
		//增加子分类
        function addNewSubType(){
        	var node = tree.getSelectedNode();
        	if(node==null){
        		alert("请选择一个分类！");
        		return;
        	}
        	//判断是否超过三级分类
        	if(node.path.split(".").length > 3){
        		alert("目前只支持三级分类！");
        		return;
        	}
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?nodetype=sub&catKey=CAT_KMS_KDDOC&parentId="+node.treeId,
				title : "添加子分类",
				height : 290,
				width : 710,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
        }
        
		//编辑分类
        function editType(){
        	var node = tree.getSelectedNode();
        	if(node==null){
        		alert("请选择一个分类！");
        		return;
        	}
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?treeId="+node.treeId,
				title : "知识分类编辑",
				height : 290,
				width : 710,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
        }
        
		//删除分类
        function delType(){
        	var node = tree.getSelectedNode();
        	if(node==null){
        		alert("请选择一个分类！");
        		return;
        	}
        	
	       	if (!confirm("确定删除此分类（此分类下的子分类也会被删除！")) {return;}

            _SubmitJson({
            	url:"${ctxPath}/sys/core/sysTree/del.do",
            	data:{
            		ids:node.treeId
            	},
            	method:'POST',
            	success:function(){
            		tree.load();
            	}
            });
        }
        
		/*打开标签*/
		function showTab(node) {
			var tabs = mini.get("mainTabs");
			var id = "tab$" + node.treeId;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = node.treerId;
				tab.name = id;
				tab.title = "知识分类-" + node.name;
				tab.showCloseButton = true;

				tab.url = "${ctxPath}" + "/kms/core/kdDoc/miniList.do?catId="+node.treeId;
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
			showTab(node);
		}

		/*激活tabs事件处理*/
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
	</script>
</body>
</html>