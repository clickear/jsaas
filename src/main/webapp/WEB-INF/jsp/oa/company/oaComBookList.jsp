<%-- 
    Document   : [OaComBook]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公司通讯录列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

</head>
<body>

	<!--Splitter-->
	<div class="mini-splitter" style="width: 100%; height: 100%;" borderStyle="border:0;">
		<div size="270" maxSize="350" minSize="100" showCollapseButton="true" style="border-width: 1px;">
			<!--Tree-->

			<div 
				id="panel1" 
				class="mini-panel" 
				title="公司通讯录" 
				iconCls="icon-abstain" 
				style="width: 268px; height: 850px;" 
				showToolbar="true" 
				showCollapseButton="true" 
				showFooter="true" 
				collapseOnTitleClick="true"
			>
				<!--toolbar-->

				<div property="toolbar">
					<div id="treetoolbar">
						<a class="mini-button" iconCls="icon-down" onclick="mini.get('tree1').expandAll()" plain="true">展开目录</a> 
						<a class="mini-button" iconCls="icon-up" onclick="mini.get('tree1').collapseAll()" plain="true">收起目录</a>
					</div>
					<a class="mini-button" iconCls="icon-add" onclick="addNewType" plain="true" id="treeadd">新建通讯录目录</a>
				</div>
				<!--Tree-->
				<ul id="tree1" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_COM_BOOK" 
				style="width: 100%; padding: 5px; height: 100%;" showTreeIcon="true" textField="name" idField="treeId" 
				parentField="parentId" value="base" expandOnLoad="true" resultAsTree="0" contextMenu="#treeMenu" 
				ondrawnode="onDrawNode" onnodeselect="onNodeSelect">
				</ul>

			</div>
			
			<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="">
				<li name="brother" iconCls="icon-add" onclick="addNewType">添加一级分类</li>
				<li name="children" iconCls="icon-add" onclick="addNewSubType">添加子分类</li>
				<li name="edit" iconCls="icon-edit" onclick="editType">编辑分类</li>
				<li name="remove" iconCls="icon-remove" onclick="delType">删除分类</li>
			</ul>

		</div>

		<div showCollapseButton="false" style="border: 0px;">
			<!--Tabs-->
			<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width: 100%; height: 100%;" ></div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var tree = mini.get("tree1");

		//在没有根目录的清空下建立新目录的按钮
		$(function() {
			if (tree.getChildNodes(tree.getRootNode()).length < 1) {
				$("#treetoolbar").hide();
			} else {
				$("#treeadd").hide();
			}
		});
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>' 
			+ ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>' 
			+ ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
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
		
		/*选择节点时触发*/
		function onNodeSelect(e) {
			var node = e.node;
			showTab(node);
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
				tab.title = "通讯录-" + node.name;
				tab.showCloseButton = true;

				tab.url = "${ctxPath}/oa/company/oaComBook/showList.do?catId="+node.treeId;
				tabs.addTab(tab);
				tabs.activeTab(tab);
			}
			else{
				tabs.activeTab(tab);
				tabs.reloadTab(tab);
			}
			
		}
		
		//增加一级分类
		function addNewType() {
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?nodetype=parent&catKey=CAT_COM_BOOK",
				title : "添加通讯录分类",
				height : 300,
				width : 800,
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
        	if(node.path.split(".").length > 5){
        		alert("目前只支持五级分类！");
        		return;
        	}
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?nodetype=sub&catKey=CAT_COM_BOOK&parentId="+node.treeId,
				title : "添加子分类",
				height : 300,
				width : 800,
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
				title : "通讯录分类编辑",
				height : 300,
				width : 800,
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
            		var tabs = mini.get("mainTabs");
        			var id = "tab$" + node.treeId;
        			var tab = tabs.getTab(id);
        			tabs.removeTab(tab);
            	}
            });
        }
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</body>
</html>