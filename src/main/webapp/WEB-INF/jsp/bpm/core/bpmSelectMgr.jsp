
<%@page import="com.redxun.sys.org.controller.SysOrgMgrController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div title="center" region="center" bodyStyle="overflow:hidden;" style="border: 0;">
			<!--Splitter-->
			<div class="mini-splitter" style="width: 100%; height: 100%;" borderStyle="border:0;">
				<div size="360" maxSize="500" minSize="100" showCollapseButton="true" style="border-width: 1px;">
					<div id="panel1" name="panel" class="mini-panel" title="模版分类" iconCls="icon-abstain" style="width: 100%; height: 100%;"
						showToolbar="true" showCollapseButton="true" showFooter="true" allowResize="true" collapseOnTitleClick="true">
						
						<ul id="tree1" class="mini-tree" url="${ctxPath}/sys/core/sysTree/getBpmTemBykey.do" style="width: 100%; padding: 5px; height: 100%"
							showTreeIcon="true" textField="name" idField="treeId"  expandOnLoad="true" parentField="parentId" resultAsTree="false"
							contextMenu="#treeMenu" ondrawnode="onDrawNode" expand="onExpand" >
						</ul>
					</div>

				</div>

			</div>
		</div>
	</div>


	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
		<li name="subadd" iconCls="icon-add" onclick="onAddNode">加入子分类</li>
		<li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑分类</li>
		<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除分类</li>
	</ul>



	<script type="text/javascript">
		mini.parse();
		var tree=mini.get("tree1");
		
		//设置节点图标
		function onDrawNode(e) {
	        var tree = e.sender;
	        var node = e.node;
	        if(node.childs>0){
	        	e.iconCls='icon-branch';}else{
	        		e.iconCls='icon-solidNode';
	        	}
	        	
	        	/* 
	        	var nodename=node.name;
	        	if(nodename.length>10){
	        		var shortnodeName=node.name.substring(0,9)+"…";
	        	e.nodeHtml= '<a title="' +node.name+ '">' +shortnodeName+ '</a>';
	        	}else{
	        		e.nodeHtml= '<a title="' +node.name+ '">' +node.name+ '</a>';
	        	} */
	    }
		
		
		//增加子分类
		function onAddNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
            var pkId=node.treeId;
			var parent=node.parentId;
            var parentnode=tree.getParentNode(node);
            var type="PERSONAL"
			_OpenWindow({
				url : '${ctxPath}/sys/core/sysTree/templateEdit.do?parentId='+pkId,
				title : "新增子分类",
				width : 600,
				height : 400,
				onload : function() {
				},
				ondestroy : function(action) {
					tree.load();
				}

			});
		}
	
		//编辑节点文本（URL）
		function onEditNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
            var pkId=node.treeId;
            var parent=node.parentId;
			_OpenWindow({
				url : '${ctxPath}/sys/core/sysTree/templateEdit.do?pkId='+pkId+"&parentId="+parent,//
				title : "编辑分类",
				width : 600,
				height : 400,
				method :'POST',
				onload : function() {

				},
				ondestroy : function(action) {
					tree.load();
				}

			});
			
		}
		//删除节点
		function onRemoveNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
            var pkId=node.treeId;
			if (node) {
				if (confirm("确定删除选中分类?")) {
					$.ajax({
		                type: "Post",
		                async:false,
		                url : '${ctxPath}/sys/core/sysTree/delBpmTem.do?pkId='+pkId,
		                beforeSend: function () {
		                },
		                success: function () {
		                	
		                }
		            }); 
					tree.load();
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
				mini.getByName("remove").hide();
			}else{
				mini.getByName("remove").show();
			}
		}
		
	</script>

</body>
</html>