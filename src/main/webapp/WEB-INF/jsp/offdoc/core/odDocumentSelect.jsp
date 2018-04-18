<%-- 
    Document   : 选中树的节点确定文件夹
    Created on : 2015-11-6, 16:11:48
    Author     : 陈茂昌
--%>
<%-- 
    Document   : 业务流程解决方案列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务流程解决方案列表管理</title>
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
<div class="mini-fit">
					<ul id="tree1" class="mini-tree"  style="width:100%;" url="${ctxPath}/offdoc/core/odDocument/selectGroup.do?"
				showTreeIcon="true" textField="name" idField="groupId" resultAsTree="false" parentField="parentId" expandOnLoad="true" ondrawnode="onDrawNode"
                onnodeclick="treeNodeClick"  >
            </ul>
					</div>
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" 
        borderStyle="border-left:0;border-bottom:0;border-right:0;">
        <a class="mini-button" style="width:60px;" onclick="selectThis()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="CloseWindow();">取消</a>
    </div>





<%-- <div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
	    <div  title="流程分类" region="west"  width=250    showSplitIcon="true"  >
	        
	         <ul id="systree" class="mini-tree"  style="width:100%;" url="${ctxPath}/offdoc/core/odDocument/selectGroup.do?"
				showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true" ondrawnode="onDrawNode"
                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">
            </ul>
	    </div>
	    
	    
	    <div title="项目" region="center" showHeader="true" showCollapseButton="false">
			<redxun:toolbar entityName="com.redxun.bpm.core.entity.BpmSolution" style="border:none;" excludeButtons="fieldSearch,popupAddMenu,popupAttachMenu,popupSearchMenu,detail,edit,remove,saveCurGridView,saveAsNewGridView,popupSettingMenu">
				<div class="self-toolbar">
				<div align="center">
				<a class="mini-button" iconCls="icon-select" onclick="selectThis()" plain="false" >确定</a>
				
				<a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');" plain="false" >取消</a>
				</div>
				</div>
				</redxun:toolbar>
		
		</div>
	</div> --%>
	<script type="text/javascript">	
        	mini.parse();
        	var groupId;
        	var groupName;
        	//设置树图标
   		 function onDrawNode(e) {
   		        var tree = e.sender;
   		        var node = e.node;
   		        	e.iconCls='icon-folder';
   		    }
        	function selectThis(){
        		var row = mini.get("tree1").getSelected();
                if (row) {
                	 mini.confirm("确定要选中此方案？"+"["+row.name+"]", "确定？",
                	            function (action) {
                	                if (action == "ok") {
                	                	groupId=row.groupId;
                	                	groupName=row.name;
                	                	CloseWindow('ok');
                	                	}
                	    	            });
                }else{
                	mini.showTips({
	                         content: "<b>未选择节点</b>",
	                         state: 'warning',
	                         x: "center",
	                         y: "center",
	                         timeout: 3000 });
	                	}
                }
        	
        	//按分类树查找数据
    		function treeNodeClick(e) {
    			var tree = mini.get("tree1");
    			var node = tree.getSelectedNode();
    			groupId=node.groupId;
    			groupName=node.name;
    		}
    		//设置节点图标
    		function onDrawNode(e) {
    			var tree = e.sender;
    			var node = e.node;
    			e.iconCls = 'icon-folder';

    		}
        	 
	
	
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmSolution" 
	winHeight="450" winWidth="780" entityTitle="业务流程解决方案" baseUrl="bpm/core/bpmSolution" />
</body>
</html>