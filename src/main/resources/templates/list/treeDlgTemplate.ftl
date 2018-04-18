 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>	
	<script type="text/javascript">
		var __rootPath='<#noparse>${ctxPath}</#noparse>';
	</script>
	<link href="<#noparse>${ctxPath}</#noparse>/styles/skin/default/index.css" rel="stylesheet" type="text/css" /> 
	
	<link href="<#noparse>${ctxPath}</#noparse>/styles/commons.css" rel="stylesheet" type="text/css" />
	<link href="<#noparse>${ctxPath}</#noparse>/styles/list.css" rel="stylesheet" type="text/css" />
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/mini/boot.js" type="text/javascript"></script>
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/share.js" type="text/javascript"></script>
	
	<link href="<#noparse>${ctxPath}</#noparse>/styles/skin/default/index.css" rel="stylesheet" type="text/css" /> 
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/common/list.js" type="text/javascript"></script>
	<script src="<#noparse>${ctxPath}</#noparse>/scripts/common/form.js" type="text/javascript"></script>
	<link href="<#noparse>${ctxPath}</#noparse>/styles/cover_list.css" rel="stylesheet" type="text/css" />
	
</head>
<body>

<div class="mini-layout" style="width:100%;height:100%;">
	<div region="south" showHeader="false" height="50">
		<div style="width:100%;text-align:center " class="dialog-footer">
			<a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok')">确定</a>
			<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
		</div>
	</div>
	<div region="center">
		<div class="mini-toolbar">
			 <a class="mini-button" iconCls="icon-expand" plain="true" onclick="onExpand()">展开</a>
			 <a class="mini-button" iconCls="icon-collapse" plain="true" onclick="onCollapse()">收起</a>
             <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>
		</div>
		<div class="mini-fit">
		 	<ul id="tree1" class="mini-tree" url="<#noparse>${ctxPath}</#noparse>/dev/cus/customData/${sysBoList.key}/getTreeJson.do" style="width:100%;height:100%;" 
		        showTreeIcon="true" textField="${textField}" idField="${idField}" parentField="${parentField}" resultAsTree="false" expandOnLoad="true"
		        <#if onlySelLeaf=='YES'>
		         onbeforenodeselect="onBeforeNodeSelect" 
		        </#if>
		        <#noparse>
		        	<#if params?? && params.single?? && (params.single=='false')>
		     			showCheckBox="true"
		     	</#noparse>
		     	<#noparse>
		     		<#else>
		     	</#noparse>
		     	    	<#if allowCheck=='true'>
			        		showCheckBox="true"
			        	</#if>
			    <#noparse>
		     		</#if>
		     	</#noparse>
		        showArrow="true">
		    </ul>
	    </div>
    </div>
</div>
<script type="text/javascript">
	mini.parse();
	
	var tree=mini.get('tree1');
	
	function getData(){
		var single=${allowCheck};
		<#noparse>
			<#if params??>
        	<#if params.single??>
        		single=${params.single};
        	</#if>
        	</#if>
        </#noparse>
	
		var rows=[];
		
		<#noparse>
		     <#if params?? && params.single??>
		     	<#if params.single=='true'>
					row=tree.getSelectedNode();
					rows.push(row);
				<#else>
					rows=tree.getCheckedNodes(false);
				</#if>
		</#noparse>
		<#noparse>
		     <#else>
		</#noparse>
				<#if allowCheck=='true'>
					rows=tree.getCheckedNodes(false);
				<#else>
					row=tree.getSelectedNode();
					rows.push(row);
				</#if>
		<#noparse>
		    </#if>
		</#noparse>
		var data={rows:rows,single:single};
		return data;
	}
	
	function onExpand(){
   		tree.expandAll();
   	}
   	
   	function onCollapse(){
   		tree.collapseAll();
   	}
	
	function refreshSysTree(){
   		tree.load();
   	}
   	
   	 function onBeforeNodeSelect(e) {
        
        var tree = e.sender;
        var node = e.node;
        if (tree.hasChildren(node)) {
            e.cancel = true;
        }
    }
</script>
</body>
</html>