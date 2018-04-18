<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>单据表单方案管理</title>
<%@include file="/commons/list.jsp"%>
<script src="${ctxPath}/scripts/share/dialog.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/sys/bo/BoUtil.js" type="text/javascript"></script>
<style type="text/css">
#iframeForm{
	width: 100%;
	height: 100%;
	border: none;
}
</style>
<script type="text/javascript">
function onBeforeOpen(e) {
    var menu = e.sender;
    var tree = mini.get("dataTree");

    var node = tree.getSelectedNode();
    
    var editItem = mini.getByName("edit", menu);
    var deleteItem = mini.getByName("delete", menu);
    //根节点
    if(node.REF_ID_=="-1") {
    	editItem.hide();
    	deleteItem.hide();
    }
    else{
    	editItem.show();
    	deleteItem.show();
    }
}

function addNode(e){
	 var tree = mini.get("dataTree");
     var node = tree.getSelectedNode();
	 var url=__rootPath +"/sys/customform/sysCustomFormSetting/form/${alias}.do?pid="+node.ID_;
	 $("#iframeForm").attr("src",url);
	 $("#iframeForm").unbind("load");
	 $("#iframeForm").bind("load",function(){
		 var win=$("#iframeForm")[0].contentWindow;
		 win.tree=tree;
		 win.selectNode=node;
	 })
}

function closeWin(){
	CloseWindow('cancel');
}

function editNode(e){
	 var tree = mini.get("dataTree");
     var node = tree.getSelectedNode();
	 var url=__rootPath +"/sys/customform/sysCustomFormSetting/form/${alias}/"+node.ID_ +".do";
	 $("#iframeForm").attr("src",url);
	 $("#iframeForm").unbind("load");
	 $("#iframeForm").bind("load",function(){
		 var win=$("#iframeForm")[0].contentWindow;
	 	 win.tree=tree;
	 	 win.selectNode=node;
	 })
}

function delNode(e){
	 var rtn=confirm("确认删除该节点吗?");
	 if(!rtn) return;
	 var tree = mini.get("dataTree");
   	 var node = tree.getSelectedNode();
	 var url=__rootPath +"/sys/customform/sysCustomFormSetting/remove/${alias}.do";
	 _SubmitJson({
		 url:url,
		 data:{id:node.ID_},
		 success:function(result){
			 tree.removeNode(node);
		 }
	 });
}

function treeClick(e){
	var node=e.node;
	var id=node.ID_;
	if(id=="0") return ;
	var url=__rootPath +"/sys/customform/sysCustomFormSetting/detail/${alias}/"+id+".do";
	 $("#iframeForm").attr("src",url);
}

</script>
</head>
<body>
	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen">
		<li iconCls="icon-add" name="add" onclick="addNode">添加</li>        
	    <li iconCls="icon-edit" name="edit" onclick="editNode">编辑</li>
	    <li iconCls="icon-remove" name="delete" onclick="delNode">删除</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div 
	    	title="" 
	    	showHeader="false"  
	    	region="west" 
	    	width="220"  
	    	showSplitIcon="true" 
    		showProxy="false"
    	>
	       
	         <ul 
	         	id="dataTree" 
	         	class="mini-tree" 
	         	url="${ctxPath}/sys/customform/sysCustomFormSetting/tree/${alias}.do" 
	         	style="width:100%;" 
				showTreeIcon="true" 
				onnodeclick="treeClick" 
				textField="text_" 
				idField="ID_" 
				resultAsTree="false" 
				parentField="REF_ID_" 
				expandOnLoad="true"
                contextMenu="#treeMenu"
             >        
            </ul>
	    </div>
	    <div showHeader="false" showCollapseButton="false">
	    	<iframe id="iframeForm" ></iframe>
		</div>	
	</div>
	
</body>
</html>