<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html>
<html >
<head>
<title>选择分类</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	
</style>

</head>
<body>

<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
<div class="mini-fit">
<div id="toolbar1" class="mini-toolbar" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	 <a class="mini-button" iconCls="icon-save" plain="true" onclick="close">确定</a>	               
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow('cancel');">关闭</a>	
	                <a class="mini-button" iconCls="icon-ok" plain="true" onclick="allSelect">全选</a>	                
	            </td>
	        </tr>
	    </table>
	</div>
<div id="treegrid1" class="mini-treegrid" style="width:100%;"     
    url="${ctxPath}/sys/core/sysTree/listBpmSolution.do"
 showTreeIcon="true"  treeColumn="name" idField="treeId" parentField="parentId" resultAsTree="false"  
    expandOnLoad="true"  ondrawcell="drawcell"  showCheckBox="true" autoCheckParent="false" checkRecursive="false"
>
    <div property="columns">
        <div name="name" field="name" width="160" >分类名称</div>
        <div field="key" width="80">key</div>
    </div>
</div>
</div>
</div>
<script type="text/javascript">
	mini.parse();
	var treeGrid=mini.get("treegrid1");
	var trees=[];
	var clickYet=false;

	function drawcell(e){
		var node =e.node;
		var iconCls=e.iconCls;
		if(node.type=="solution"){
			//e.showCheckBox=false; 
			e.iconCls="icon-arrowDown";
		}else{
			//e.showCheckBox=true; 
			e.iconCls="icon-arrowUp";
		}
	}
	
	function close(){
		var nodes=treeGrid.getCheckedNodes(true);
		trees=nodes;
		CloseWindow('ok');
	}
	
	function getTrees(){
		return trees;
	}
	
	function allSelect(){
		if(clickYet){
			treeGrid.uncheckAllNodes();
			clickYet=false;
		}else{
			treeGrid.checkAllNodes();
			clickYet=true;
		}
		
	}

</script>

</body>
</html>