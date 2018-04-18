<%-- 
    Document   : [BpmInstCtl]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/list.jsp"%>
</head>
<body>

<div class="mini-toolbar mini-toolbar-bottom topBtn">
    <a class="mini-button" iconCls="icon-save" onclick="saveSelect()">保存 </a>
    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel')">关闭</a>
</div>
	<div class="mini-fit form-outer shadowBox90" style="height: 100%;">
		<div 
			id="treegrid1" 
			class="mini-treegrid" 
			style="width:100%;height:100%;"  
			multiSelect="true"   
	    	url="${ctxPath}/bpm/core/bpmNodeSet/getTaskNodes.do?actDefId=${actDefId}" 
	    	showTreeIcon="true" 
	    	treeColumn="name" 
	    	idField="activityId" 
	    	parentField="parentActivitiId" 
	    	allowAlternating="true"
	   	>
		    <div property="columns">
	   			<div type="checkcolumn" ></div>
		    	<div field="activityId" width="50">节点Id</div>
		    	<div name="name" field="name" width="200">节点名称</div>
	    	</div>
		</div>
	</div>

	<script type="text/javascript">
        	mini.parse();
        	var tree=mini.get("treegrid1");
        	var nodeName="";
        	var nodeId="";
        	function saveSelect(){
        		var node=tree.getSelecteds();
        		for(var x=0;x<node.length;x++)
        		{
        			nodeName=nodeName+","+node[x].name;
        			nodeId=nodeId+","+node[x].activityId;
        		}
        		
        		if(node.length>0){
        			nodeName=nodeName.substring(1,nodeName.length);
        			nodeId=nodeId.substring(1,nodeId.length);
        		}
        		CloseWindow('ok');
        	}
        	
        	function getNodeName(){
        		return nodeName;
        	}
        	function getNodeId(){
        		return nodeId;
        	}
        	
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>

</body>
</html>