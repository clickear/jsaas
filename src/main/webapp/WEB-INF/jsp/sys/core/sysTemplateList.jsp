<%-- 
    Document   : 系统模板列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统模板列表管理</title>
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
	<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div title="系统模板分类" region="west" width="180"  showSplitIcon="true" >
	        <div id="toolbar1" class="mini-toolbar" style="padding:2px;border:0;">
				<table style="width:100%;" cellpadding="0" cellspacing="0">
	                <tr>
	                    <th style="width:100%;" class="mini-process-top">
	                        <a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">添加</a>
	                        <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>
	                    </th>
	                </tr>
	            </table>           
	        </div>
	         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_TEMPLATE" style="width:100%;" 
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">        
            </ul>
	    </div>
	    <showHeader="true" showCollapseButton="false">
			<redxun:toolbar entityName="com.redxun.sys.core.entity.SysTemplate" />
			<div class="mini-fit rx-grid-fit">
				<div id="datagrid1" class="mini-datagrid"
					style="width: 100%; height: 100%" allowResize="false"
					url="${ctxPath}/sys/core/sysTemplate/listData.do" idField="tempId"
					multiSelect="true" showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" pageSize="20"
					allowAlternating="true" pagerButtons="#pagerButtons">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
						<div field="name" width="120" headerAlign="center" allowSort="true">模板名称</div>
						<div field="key" width="120" headerAlign="center" allowSort="true">模板KEY</div>
						<div field="isDefault" width="50" headerAlign="center" allowSort="true">是否系统缺省</div>
						<div field="createTime" width="80" headerAlign="center" dateformat="yyyy-MM-dd" allowSort="true">创建时间</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.sys.core.entity.SysTemplate" winHeight="450"
		winWidth="700" entityTitle="系统模板"
		baseUrl="sys/core/sysTemplate" />
	<script type="text/javascript">
		var systree=mini.get('systree');
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	        function addNode(e){
	    		var systree=mini.get("systree");
	    		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		
		   		_OpenWindow({
		   			title:'添加系统模板分类',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_TEMPLATE',
		   			width:720,
		   			height:350,
		   			ondestroy:function(action){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	function refreshSysTree(){
		   		var systree=mini.get("systree");
		   		systree.load();
		   	}
		   	
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
		   		grid.setUrl(__rootPath+'/sys/core/sysTemplate/listData.do?treeId='+node.treeId);
		   		grid.load();
		   	}

        	
        </script>
	
</body>
</html>