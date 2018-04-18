<%-- 
    Document   : 产品类型列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品类型列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet"　type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet"　type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js"　	type="text/javascript"></script>
</head>
<body>
	<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
	    <div title="参数定义分类" region="west" width="180"  showSplitIcon="true" >
	        <div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<table style="width:100%;" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td style="width:100%;">
	                        <a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">添加</a>
	                        <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
	         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_ASSETS_SORT" style="width:100%;" 
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">        
            </ul>
	    </div>
<div title="产品类型表单" region="center" showHeader="true" showCollapseButton="false">
	<redxun:toolbar
		entityName="com.redxun.oa.product.entity.OaProductDefParaKey" />
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/product/oaProductDefParaKey/listData.do"
			idField="keyId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="controlType" width="120" headerAlign="center"
					allowSort="true">类型</div>
				<div field="dataType" width="120" headerAlign="center"
					allowSort="true">数据类型</div>
				<div field="desc" width="120" headerAlign="center" allowSort="true">描述</div>
			</div>
		</div>
	</div>
  </div>
</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.product.entity.OaProductDefParaKey" winHeight="550" winWidth="800" entityTitle="产品类型" baseUrl="oa/product/oaProductDefParaKey" />
	<script type="text/javascript">
			mini.parse();
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	        
		      //新增节点
	        function addNode(e){
	    		var systree=mini.get("systree");
	    		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		
		   		_OpenWindow({
		   			title:'添加表单视图分类',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_ASSETS_SORT',
		   			width:720,
		   			height:350,
		   			ondestroy:function(action){
		   				systree.load();
		   			}
		   		});
		   	}
	      
	      //刷新树
		   	function refreshSysTree(){
		   		var systree=mini.get("systree");
		   		systree.load();
		   	}
		   	
		  //编辑树节点
		   	function editNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		var treeId=node.treeId;
		   		_OpenWindow({
		   			title:'编辑节点',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?pkId='+treeId,
		   			width:780,
		   			height:350,
		   			ondestroy:function(action){
		   				if(action=='ok'){
		   					systree.load();
		   				}
		   			}
		   		});
		   	}
		  
		  //删除树节点
		   	function delNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		_SubmitJson({
		   			url:__rootPath+'/sys/core/sysTree/del.do?ids='+node.treeId,
		   			success:function(text){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/oa/product/oaProductDefParaKey/listData.do?treeId='+node.treeId);
		   		grid.load();
		   	}
        </script>

</body>
</html>