<%-- 
    Document   : [OdDocTemplate]列表页
    Created on : 2016-3-8, 16:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>公文模板列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div  
	    	title="${name}" 
	    	region="west"  
	    	width=250
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
    	>
	        <div id="toolbar1" class="mini-toolbar" style="padding:2px;border:0px;">
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode(e)">新建分类</a>
                <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>          
	        </div>
	         <ul id="systree" class="mini-tree"  style="width:100%; height:100%;" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=${catKey}"
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true" ondrawnode="onDrawNode"
                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">
            </ul>
	    </div>
	    
	    <showHeader="true" showCollapseButton="false">
			
		<redxun:toolbar entityName="com.redxun.offdoc.core.entity.OdDocTemplate" excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove,popupSearchMenu,,saveCurGridView,saveAsNewGridView">
			<div class="self-toolbar">
				 <li>
				 	<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">撰写模板</a>
				 </li>
			</div>
		</redxun:toolbar>
		
			<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/offdoc/core/odDocTemplate/listData.do?treeId=${CatId}" idField="tempId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">模板名称</div>
				<div field="descp" width="120" headerAlign="center" allowSort="true">模板描述</div>
				<div field="status" width="120" headerAlign="center" allowSort="true" renderer="onStatusRenderer">模板状态</div>
			</div>
		</div>
	</div>
		</div>
	</div>



	<script type="text/javascript">
	mini.parse();
	var treeCat;

	var tree = mini.get(systree);
	var mynodes = tree.getChildNodes(tree.getRootNode());
	var firstPage;
	if (mynodes.length > 0) {
		firstPage = mynodes[0].treeId;//第一个节点的Id
		mini.get('datagrid1').setUrl("${ctxPath}/offdoc/core/odDocTemplate/listData.do?treeId="+ firstPage);
	} else {//如果没有tree，访问list1，给一个空的列表防止报错
		mini.get('datagrid1').setUrl("${ctxPath}/offdoc/core/odDocTemplate/listBlank.do");
	}
	//设置节点图标
	function onDrawNode(e) {
		var tree = e.sender;
		var node = e.node;
		e.iconCls = 'icon-folder';

	}
	
	//按分类树查找数据
	function treeNodeClick(e) {
		var node = e.node;
		grid.setUrl(__rootPath + '/offdoc/core/odDocTemplate/listData.do?treeId='+ node.treeId);
		grid.load();
		treeCat = node.treeId;
	}
	//刷新树
	function refreshSysTree() {
		var systree = mini.get("systree");
		systree.load();
	}

	//新增节点
	function addNode(e) {
		var systree = mini.get("systree");
		var node = systree.getSelectedNode();
		var parentId = node ? node.treeId : 0;
		_OpenWindow({
			title : '添加节点',
			url : __rootPath + '/sys/core/sysTree/edit.do?parentId='
					+ parentId + '&catKey=${catKey}',
			width : 720,
			height : 350,
			ondestroy : function(action) {
				if (action == 'ok'){
				systree.load();
				}
			}
		});
	}
	
	//在根节点建立
	function addNodeInFirst(e){
		var systree = mini.get("systree");
		_OpenWindow({
			title : '添加节点',
			url : __rootPath + '/sys/core/sysTree/edit.do?parentId='
					+"0" + '&catKey=${catKey}',
			width : 720,
			height : 350,
			ondestroy : function(action) {
				if (action == 'ok'){
				systree.load();
				}
			}
		});
	}
	//编辑树节点
	function editNode(e) {
		var systree = mini.get("systree");
		var node = systree.getSelectedNode();
		var treeId = node.treeId;
		_OpenWindow({
			title : '新增项目分类',
			url : __rootPath + '/sys/core/sysTree/edit.do?pkId=' + treeId,
			width : 780,
			height : 350,
			ondestroy : function(action) {
				if(action='ok')
					systree.load();
				
			}
		});
	}

	//删除树节点
	function delNode(e) {
		var systree = mini.get("systree");
		var node = systree.getSelectedNode();
		_SubmitJson({
			url : __rootPath + '/sys/core/sysTree/del.do?ids='+ node.treeId,
			success : function(text) {
				systree.load();
			}
		});
	}
	
	
	
	var tempType='${catKey}';
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s =  ' <span class="icon-edit" title="编辑" onclick="editMyRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	//新增一条模板记录
        	function addOne(){
        		if (treeCat == undefined) {
    				treeCat = firstPage;
    			}
        		_OpenWindow({
   	    		 url: "${ctxPath}/offdoc/core/odDocTemplate/edit.do?parentId="+treeCat,
   	            title: "撰写模板",
   	            width: 700, height: 860,
   	         	max:true,
   	            ondestroy: function(action){
   	                if (action == 'ok') {
   	                    grid.reload();
   	                }
   	            }
   	    	});
        	}
        	
        	
        	
        	//模板明细
    		function detailMyRow(pkId) {
    	        _OpenWindow({
    	        	url: __rootPath + "/offdoc/core/odDocTemplate/get.do?pkId=" + pkId,
    	            title: "模板明细", 
    	            width: 700, 
    	            height: 860,
    	        });
    	    }
    		
    		
    		//编辑模板数据
    	    function editMyRow(pkId) {        
    	        _OpenWindow({
    	    		 url: __rootPath + "/offdoc/core/odDocTemplate/edit.do?pkId="+pkId,
    	            title: "编辑模板",
    	            width: 700, height: 860,
    	            max:true,
    	            ondestroy: function(action) {
    	                if (action == 'ok') {
    	                    grid.reload();
    	                }
    	            }
    	    	});
    	    }
    		
    		 function onStatusRenderer(e) {
    	         var record = e.record;
    	         var status = record.status;
    	         var arr = [ {'key' : 'ENABLED','value' : '启用','css' : 'green'}, 
    			             {'key' : 'DISABLED','value' : '禁用','css' : 'orange'}];
    				return $.formatItemValue(arr,status);
    	     }
    		
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.offdoc.core.entity.OdDocTemplate" winHeight="450" winWidth="700"
		entityTitle="公文模板" baseUrl="offdoc/core/odDocTemplate" />
</body>
</html>