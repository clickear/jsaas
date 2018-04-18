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
<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
	    <div  title="流程分类" region="west"  width=250    showSplitIcon="true"  >
	        
	         <ul id="systree" class="mini-tree"  style="width:100%;" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_SOLUTION"
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
		
			<div class="mini-fit" style="height: 100%;">
				
<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/bpm/core/bpmSolution/listData.do" idField="solId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
						<div property="columns">
							<div field="name" width="140" headerAlign="center" allowSort="true">解决方案名称</div>
							<div field="status" width="80" headerAlign="center" allowSort="true">状态</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">	
        	mini.parse();
        	var solId;
        	var solName;
        	
        	function selectThis(){
        		var row = grid.getSelected();
                if (row) {
                	 mini.confirm("确定要选中此方案？"+"["+row.name+"]", "确定？",
                	            function (action) {
                	                if (action == "ok") {
                	                	solId=row.solId;
                	                	solName=row.name;
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
    			var node = e.node;
    			grid.setUrl(__rootPath + '/bpm/core/bpmSolution/listData.do?treeId='+node.treeId);
    			grid.load();
    			projectCat = node.treeId;
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