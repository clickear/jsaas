<%-- 
    Document   : [KdQuestion]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>问答分类列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

<%-- 	<redxun:toolbar entityName="com.redxun.kms.core.entity.KdQuestion" excludeButtons="popupAddMenu,popupSearchMenu,popupAttachMenu,popupSettingMenu,fieldSearch,detail,edit,remove">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveType">保存分类</a><a class="mini-button" iconCls="icon-add" plain="true" onclick="addNewType">添加一级分类</a> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addNewSubType">添加子分类</a><a class="mini-button" iconCls="icon-remove" plain="true" onclick="delType()">删除分类</a>
		</div>
	</redxun:toolbar>
	<div class="mini-fit" style="height: 100%;">
		<div id="treegrid1" class="mini-treegrid" style="width: 100%; height: 100%;" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_KMS" showTreeIcon="true" treeColumn="name" idField="treeId" parentField="parentId" resultAsTree="false" expandOnLoad="2" allowCellEdit="true" allowResize="true"  allowCellValid="true" oncellvalidation="onCellValidation" allowCellSelect="true">
			<div property="columns">
				<div name="name" field="name" width="">
					<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
				</div>
				 <div field="key" name="key" align="left" width="80">菜单Key
                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
                </div>
			</div>
		</div>
	</div> --%>
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="west" showSplit="true" showHeader="false" width="200">
			<div id="toolbar1" class="mini-toolbar" style="padding: 2px; border: 0;">
				<table style="width: 100%;" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 100%;"><a class="mini-button" iconCls="icon-add" plain="true" onclick="addNewType" tooltip="添加一级分类">添加一级分类</a> <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refresh" tooltip="刷新">刷新</a></td>
					</tr>
				</table>
			</div>

			<div class="mini-fit" borderStyle="border:0px;">
				<div id="tree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_KMS_KDQUE" style="width: 100%; height: 100%; padding: 5px;" showTreeIcon="true" textField="name" idField="treeId" parentField="parentId" resultAsTree="false"  expandOnNodeClick="false" expandOnLoad="0" contextMenu="#treeMenu" onnodeselect="onNodeSelect" ondrawnode="" onload="" ></div>
			</div>

			<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="">
				<li name="brother" iconCls="icon-add" onclick="addNewType">添加一级分类</li>
				<li name="children" iconCls="icon-add" onclick="addNewSubType">添加子分类</li>
				<li name="edit" iconCls="icon-edit" onclick="editType">编辑分类</li>
				<li name="remove" iconCls="icon-remove" onclick="delType">删除分类</li>
			</ul>
		</div>

		<div title="center" region="center" bodyStyle="overflow:hidden;" style="border: 0;">
			<div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width: 100%; height: 100%;" onactivechanged="onTabsActiveChanged"></div>
		</div>
	</div>

	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<script type="text/javascript">
		mini.parse();
		var tree=mini.get("tree");	
		
		function refresh(){
			tree.load();
		}
		
		function addNewType() {
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?nodetype=parent&catKey=CAT_KMS_KDQUE",
				title : "添加一级问题分类",
				height : 200,
				width : 800,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
		}
		
        function addNewSubType(){
        	var node = tree.getSelectedNode();
        	if(node==null){
        		alert("请选择一个分类！");
        		return;
        	}
        	if(node.depth>1){
        		alert("不能添加四级以上的分类！");
        		return;
        	}
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?nodetype=sub&catKey=CAT_KMS_KDQUE&parentId="+node.treeId,
				title : "添加子问题分类",
				height : 200,
				width : 800,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
        }
        
        function editType(){
        	var node = tree.getSelectedNode();
        	if(node==null){
        		alert("请选择一个分类！");
        		return;
        	}
			_OpenWindow({
				url : "${ctxPath}/sys/core/sysTree/editKmsType.do?treeId="+node.treeId,
				title : "问题分类编辑",
				height : 200,
				width : 800,
				ondestroy : function(action) {
					if (action == 'ok')
						tree.load();
				}
			});
        }
        
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
				tab.title = "问题分类-" + node.name;
				tab.showCloseButton = true;

				tab.url = "${ctxPath}" + "/kms/core/kdQuestion/list.do?treeId="+node.treeId;
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