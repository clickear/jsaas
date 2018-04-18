
<%-- 
    Document   : 系统自定义业务EXCEL导出页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>表单导出Excel</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	#headerGrid{
		margin: 20px auto;
	}
	#headerGrid>.mini-panel-border{
		background: #fff;
	}
</style>
</head>
<body>
   	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-export" plain="true" onclick="onMutiExport">多表头导出</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-export" plain="true" onclick="onSingleExport">单表头导出</a>
			</li>
			<li class="clearfix"></li>
		</ul>
		<ul>
			<li>
				<a class="mini-button" iconCls="icon-save" plain="true" onclick="reloadColumns">重新合并表头</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addRow('headerGrid')">添加</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeRow('headerGrid')">删除</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow('headerGrid')">向上</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow('headerGrid')">向下</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-left" plain="true" onclick="topRow('headerGrid')">上升一级</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-right" plain="true" onclick="subRow('headerGrid')">下降一级</a>
			</li>
			<li class="clearfix"></li>
		</ul>
     </div>
   	
   	
   	
   	
   	
   	
   	   	
    <div 
    	id="headerGrid" 
    	class="mini-treegrid" 
    	style="" 
    	showPager="false"
     	treeColumn="header" 
     	idField="_uid" 
     	parentField="_pid" 
     	multiSelect="true"
        showTreeIcon="true" 
        allowCellEdit="true" 
        allowCellValid="true" 
        oncellvalidation="onHeaderGridCellValidation" 
	    resultAsTree="false" 
	    expandOnLoad="true" 
		allowCellSelect="true"
		allowAlternating="true"
	>
		    <div property="columns" class="border-right">
		     	<div type="indexcolumn" width="20"></div>
		        <div type="checkcolumn" width="20"></div>
		        <div name="header" field="header" width="150" headerAlign="center">字段名称(*)
		        	<input property="editor" class="mini-textbox" style="width:100%;" required="true" />
		        </div>              
		        <div field="field" width="120" headerAlign="center">字段Key(*)
		            <input property="editor" class="mini-textbox" allowInput="true" 
		             required="true" /> 
		        </div>
		    </div>
    </div>

	<textarea id="headerColumns" style="display:none">${headerColumns}</textarea>
	<form name="hiform" id="hiform" action="${ctxPath}/sys/core/sysBoList/${boKey}/exportExcel.do" method="post">
		<input id="columns" type="hidden" name="columns">
		<input id="boKey" type="hidden" name="boKey">
		<input id="isMuti" type="hidden" name="isMuti">
	</form>

	<script type="text/javascript">
	    mini.parse();
	    var headerGrid= mini.get("headerGrid");
	    var headerColumns=$('#headerColumns').val();
	    $(function(){
	    	var headers=mini.decode(headerColumns);
	    	headerGrid.setData(headers);
	    })    	
	    	
	    
	    function reloadColumns(){
        	var id='${sysBoList.id}';
        	_SubmitJson({
        		url:__rootPath+'/sys/core/sysBoList/reloadColumns.do',
        		data:{
        			id:id
        		},
        		success:function(result){
        			var data=result.data;
        			headerGrid.setData(data);
        		}
        	});
        }
	    function removeRow(gridName){
			var grid = mini.get(gridName);
			var selecteds=grid.getSelecteds();
			grid.removeNodes(selecteds);
			
		}
	  //添加行
		function addRow(gridName){
			var grid = mini.get(gridName);
		 	var node = grid.getSelectedNode();
		 	grid.addNode({}, "after", node);
		 	grid.cancelEdit();
		 	grid.beginEditRow(node);
		}
	    
	    function upRow(gridName) {
			var grid = mini.get(gridName);
            var moveNode = grid.getSelected();
            var index=grid.indexOf(moveNode);
            var targetNode=null;
            for(var i=index-1;i>=0;i--){
            	targetNode=grid.getRow(i);
            	if(targetNode._level==moveNode._level){
            		break;
            	}
            }
            if (targetNode!=null) {
            	grid.moveNode(moveNode, targetNode,"before");
               
            }
        }
        function downRow(gridName) {
       	  	var grid = mini.get(gridName);
       	  	var moveNode = grid.getSelected();
             var index=grid.indexOf(moveNode);
             var targetNode=null;
             for(var i=index+1;i<grid.getData().length;i++){
             	targetNode=grid.getRow(i);
             	if(targetNode._level==moveNode._level){
             		break;
             	}
             }
             if (targetNode!=null) {
            	 grid.moveNode(moveNode, targetNode,"after");
             }
        }
       
        //下降当前选择节点
        function subRow(gridName){
        	var grid = mini.get(gridName);
        	//当前要下降的节点
        	var moveNode = grid.getSelectedNode();
        	
        	var index=grid.indexOf(moveNode);
        	var pNode=null;
        	for(var i=index-1;i>=0;i--){
        		pNode=grid.getRow(i);
        		if(pNode._level==moveNode._level){
        			break;
        		}
        	}
        	if(pNode!=null){
        		grid.moveNode(moveNode,pNode,"add");
        		
        	}else{
        		alert('前一级没有父节点！');
        	}
        }
        
        function topRow(gridName){
        	var grid = mini.get(gridName);
        	var moveNode = grid.getSelectedNode();
        	var pNode=grid.getParentNode(moveNode);
        	if(pNode!=null){
        		grid.moveNode(moveNode,pNode,"after");
        	}
        }
        function onMutiExport(){
        	var items = headerGrid.getData();
/* 	        var valCols = [];
	        for (var i = 0; i < items.length; i++) {
	            var col = items[i];
	            if (col.type == 'checkcolumn' || col.name == 'checkcolumn') {
	                continue;
	            }
	            valCols.push(col);
	        } */
	        $("#columns").val(mini.encode(items));
	        $("#boKey").val('${boKey}');
	        $("#isMuti").val(true);
	        document.getElementById("hiform").submit();
        }
        
        function onSingleExport(){
        	var items = headerGrid.getData();
/* 	        var valCols = [];
	        for (var i = 0; i < items.length; i++) {
	            var col = items[i];
	            if (col.type == 'checkcolumn' || col.name == 'checkcolumn') {
	                continue;
	            }
	            valCols.push(col);
	        } */
	        $("#columns").val(mini.encode(items));
	        $("#boKey").val('${boKey}');
	        $("#isMuti").val(false);
	        document.getElementById("hiform").submit();
        }
        
		function onHeaderGridCellValidation(e){
			if(e.field=='header' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='字段名称不能为空！';
	       	}
			if(e.field=='field' && (!e.value||e.value=='')){
	       		 e.isValid = false;
	       		 e.errorText='字段Key不能为空！';
	       	}
		}
	</script>
</body>
</html>