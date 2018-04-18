<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>维度等级自定义管理</title>
		<%@include file="/commons/list.jsp"%>
		<style>.shadowBox90 #datagrid1>.mini-panel-border{width: 100%;}</style>
    </head>
    <body>
    	 <div class="mini-toolbar mini-toolbar-bottom topBtn">
	        <a class="mini-button" plain="true" iconCls="icon-add" onclick="addRankType()">增加等级</a>
            <a class="mini-button" plain="true" iconCls="icon-save" onclick="batchSave()">保存等级</a>
            <a class="mini-button" plain="true" iconCls="icon-remove" onclick="batchDel()">删除等级</a>
	    </div>
    	<div class="form-outer2 shadowBox90" style="padding: 0;">
            <div 
            	id="datagrid1" 
            	class="mini-datagrid" 
            	style="width:100%;" 
            	allowResize="false"
                url="${ctxPath}/sys/org/osRankType/listByDimId.do?dimId=${osDimension.dimId}"  
                idField="id" 
                multiSelect="true" 
                showColumnsMenu="true"
                allowCellEdit="true" 
                allowCellSelect="true" 
                multiSelect="true" 
                showPager="false" 
                oncellvalidation="onCellValidation" 
                allowAlternating="true"
			>
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div 
                    	name="action" 
                    	cellCls="actionIcons" 
                    	width="50" 
                    	headerAlign="center" 
                    	align="center" 
                    	renderer="onActionRenderer" 
                    	cellStyle="padding:0;"
                   	>操作</div>        
                    <div field="name" width="120" headerAlign="center" >名称
                    	<input property="editor" class="mini-textbox" style="width:100%" required="true" vtype="maxLength:255" />
                    </div>    
                    <div field="key" width="100" headerAlign="center" >Key
                    	<input property="editor" class="mini-textbox" style="width:100%" required="true" vtype="maxLength:64"/>
                    </div>
                    <div field="level" name="level" width="100" headerAlign="center" >级别
                    	 <input property="editor" class="mini-spinner"  minValue="0" maxValue="100" value="1" style="width:100%;"/>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
        	addBody();
        	mini.parse();
        	var grid=mini.get("#datagrid1");
        	grid.load();
        	var dimId='${osDimension.dimId}';
        	function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record._uid;
	            var s = '<span class="icon-save" title="保存" onclick="saveRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
	            return s;
	        }
        	
	        function addRankType(){
	        	  var newRow = { name: "新等级" };
	              grid.addRow(newRow, 0);
	              grid.beginEditCell(newRow, "新等级");
	        }
	        
	        function onCellValidation(e){
	        	if(e.field=='key' && (!e.value||e.value=='')){
	        		 e.isValid = false;
	        		 e.errorText='Key不能为空！';
	        	}
	        	if(e.field=='name' && (!e.value||e.value=='')){
	        		e.isValid = false;
	       		 	e.errorText='名称不能为空！';
	        	}
	        }
	        
	        function saveRow(uid){
	        	//表格检验
	        	grid.validate();
	        	if(!grid.isValid()){
	            	return;
	            }
	        	var row=grid.getRowByUID(uid);
	        	_SubmitJson({
	        		url:'${ctxPath}/sys/org/osRankType/saveRow.do',
	        		data:{
	        			dimId:dimId,
	        			data:mini.encode(row)},
	        		method:'POST',
	        		success:function(text){
	        			var result=mini.decode(text);
	        			row.id=result.data.id;
	        			grid.acceptRecord(row);
	        		}
	        	});
	        }
	        
	        function batchSave(){
	        	//表格检验
	        	grid.validate();
	        	if(!grid.isValid()){
	            	return;
	            }
	        	var data=grid.getData();
	        	_SubmitJson({
		        	url:'${ctxPath}/sys/org/osRankType/saveBatch.do',
	        		data:{dimId:dimId,data:mini.encode(data)},
	        		method:'POST',
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        }
	        
	        function delRow(uid){
	        	var row=grid.getRowByUID(uid);
	        	if(row.id){
	        		_SubmitJson({
			        	url:'${ctxPath}/sys/org/osRankType/del.do',
		        		data:{ids:row.id},
		        		method:'POST',
		        		success:function(text){
		        			grid.load();
		        		}
		        	});	
	        	}else{
	        		batchDel();
	        	}
	        	
	        }
	        
	        function batchDel(){
	        	debugger;
	        	var rows=grid.getSelecteds();
	        	if(rows.length==0){
	        		alert("请选择要删除的行!");
	        		return;
	        	}
	        	var ids=[];
	        	for(var i=0;i<rows.length;i++){
	        		ids.push(rows[i].id);
	        	}
	        	_SubmitJson({
		        	url:'${ctxPath}/sys/org/osRankType/del.do',
	        		data:{ids:ids.join(',')},
	        		method:'POST',
	        		success:function(text){
	        			grid.load();
	        		}
	        	});
	        }
	        
        </script>
    </body>
</html>