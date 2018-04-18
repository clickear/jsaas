<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/commons/list.jsp" %>
	<title>数据字典分类管理</title>
</head>
<body>
			<div id="toolbar1" class="mini-toolbar" >
				<table style="width:100%;" cellpadding="0" cellspacing="0">
	                <tr>
	                	<td>【${sysTree.name}】分类维护</td>
	                    <td style="width:80%;">
	                        <a class="mini-button" iconCls="icon-save"  onclick="saveItems()">保存数据项</a>
			                <a class="mini-button" iconCls="icon-addfolder" onclick="newRow();">添加数据项</a>
			                <c:if test="${showType=='TREE'}">
			                <a class="mini-button" iconCls="icon-addfolder" onclick="newSubRow();">添加子数据项</a>
			                </c:if>
			                <a class="mini-button" iconCls="icon-remove"  onclick="delRow();">删除数据项</a>
			                
	                    </td>
	                </tr>
	            </table>           
	        </div>
	         <div class="mini-fit rx-grid-fit" style="border:0;">
		        <div id="sysdicGrid" class="mini-treegrid" style="width:100%;height:100%;"     
		            showTreeIcon="true" 
		            treeColumn="name" idField="dicId" parentField="parentId"  valueField="dicId"
		            resultAsTree="false"  
		            allowResize="true" expandOnLoad="true" 
		            allowCellValid="true" oncellvalidation="onCellValidation" 
		            allowCellEdit="true" allowCellSelect="true" url="${ctxPath}/sys/core/sysDic/listByTreeId.do?treeId=${treeId}">
		            <div property="columns">
		            	<div name="action" width="100" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
		                <div name="name" field="name" align="center" width="160">项名
		                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
		                </div>
		                <div field="key" name="key" align="center" width="80">项Key
		                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
		                </div>
		                <div field="value" name="value" align="center" width="80">项值
		                	<input property="editor" class="mini-textbox" style="width:100%;" required="true"/>
		                </div>
		                <div name="descp" field="descp" align="center" width="120">项描述
		                	<input property="editor" class="mini-textbox" style="width:100%;" />
		                </div>
		                <div name="sn" field="sn" align="center" width="60">序号
		                	<input property="editor" changeOnMousewheel="false" class="mini-spinner"  minValue="1" maxValue="100000" required="true"/>
		                </div>
		            </div>
		        </div>
	        </div>
	        
	       
			
	   <script type="text/javascript">
	   	mini.parse();
	   	
	   	var catKey='${catKey}';
	   	
	   	var showType='${showType}';
	   	
	   	var grid=mini.get("sysdicGrid");
	   	
	    var treeId='${treeId}';
	
	   	function onCellValidation(e){
        	if(e.field=='key' && (!e.value||e.value=='')){
        		 e.isValid = false;
        		 e.errorText='项Key不能为空！';
        	}
        	if(e.field=='name' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='项名称不能为空！';
        	}
        	
        	if(e.field=='value' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='项值不能为空！';
        	}
        	
        	if(e.field=='sn' && (!e.value||e.value=='')){
        		e.isValid = false;
       		 	e.errorText='序号不能为空！';
        	}
        	
        }
	   	
	   	function onActionRenderer(e) {
            var record = e.record;
            var uid = record._uid;
            
            var s = '<span class="icon-button icon-rowbefore" title="在前添加数据项" onclick="newBeforeRow(\''+uid+'\')"></span>';
            s+=' <span class="icon-button icon-rowafter" title="在后添加数据项" onclick="newAfterRow(\''+uid+'\')"></span>';
            
            //为树类型才允许往下添加
            if(showType=='TREE'){
            	s+= '<span class="icon-button icon-add" title="添加子数据项" onclick="newSubRow()"></span>';
            }
            s+=' <span class="icon-button icon-save" title="保存" onclick="saveRow(\'' + uid + '\')"></span>';
            s+=' <span class="icon-button icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
            return s;
        }
		//在当前选择行的下添加子记录
        function newSubRow(){
			
			var node = grid.getSelectedNode();
			 //为树类型才允许往下添加
            if(showType=='TREE'){
	            grid.addNode({}, "add", node);
            }else{
	            grid.addNode({}, "before", node);
            }
           
        }
		
        function newRow() {            
        	
        	var node = grid.getSelectedNode();
            grid.addNode({}, "after", node);
        }

        function newAfterRow(row_uid){
        	var node = grid.getRowByUID(row_uid);
        	grid.addNode({}, "after", node);
        	grid.cancelEdit();
        	grid.beginEditRow(node);
        }
        
        function newBeforeRow(row_uid){
        	var node = grid.getRowByUID(row_uid);
        	grid.addNode({}, "before", node);
        	grid.cancelEdit();
        	grid.beginEditRow(node);
        }
			
	   	//保存单行
	    function saveRow(row_uid) {
        	//表格检验
        	grid.validate();
        	if(!grid.isValid()){
            	return;
            }
			var row = grid.getRowByUID(row_uid);

            var json = mini.encode(row);
            
            _SubmitJson({
            	url: "${ctxPath}/sys/core/sysDic/rowSave.do",
            	data:{
            		treeId:treeId,
            		data:json},
            	method:'POST',
            	success:function(text){
            		var result=mini.decode(text);
            		if(result.data && result.data.treeId){
                		row.treeId=result.data.treeId;
                	}
            		grid.acceptRecord(row);
            		grid.load();
            	}
            });
        }
        
      	//批量多行数据字典
        function saveItems(){
			
        	//表格检验
        	grid.validate();
        	if(!grid.isValid()){
            	return;
            }
        	
        	//获得表格的每行值
        	var data = grid.getData();
        	if(data.length<=0)return;
            var json = mini.encode(data);
            
            var postData={treeId:treeId,gridData:json};
            
            _SubmitJson({
            	url: "${ctxPath}/sys/core/sysDic/batchSave.do",
            	data:postData,
            	method:'POST',
            	success:function(text){
            		grid.load();
            	}
            });
        }
      	
        //删除数据项
        function delRow(row_uid) {
        	
        	var row=null;
        	if(row_uid){
        		row = grid.getRowByUID(row_uid);
        	}else{
        		row = grid.getSelected();	
        	}
        	
        	if(!row){
        		alert("请选择删除的数据项！");
        		return;
        	}
        	
        	if (!confirm("确定删除此记录？")) {return;}

            if(row && !row.dicId){
            	grid.removeNode(row);
            	return;
            }else if(row.dicId){
            	_SubmitJson({
            		url: "${ctxPath}/sys/core/sysDic/del.do?ids="+row.dicId,
                	success:function(text){
                		grid.removeNode(row);
                	}
                });
            } 
        }
	</script>
</body>
</html>