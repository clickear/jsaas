<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>流程变量编辑页</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
			<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            
                 <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加变量</a>
                 <a class="mini-button" iconCls="icon-save" onclick="saveRows()" plain="true">保存变量</a>  
                 <a class="mini-button" iconCls="icon-remove" onclick="delRows()" plain="true">删除</a>
                 <a class="mini-button" iconCls="icon-refresh" onclick="refreshGrid()" plain="true">刷新</a>
                 <span class="separator"></span> 
                 <a class="mini-button" iconCls="icon-close" onclick="CloseWindow()" plain="true">关闭</a>      
	        </div>
	        <div class="mini-fit">
				<div id="varGrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"
	                 url="${ctxPath}/bpm/core/bpmInst/listVars.do?actInstId=${param.actInstId}"  idField="id" multiSelect="true" showColumnsMenu="true"
	                 allowCellEdit="true" allowCellSelect="true" multiSelect="true" showPager="false" oncellvalidation="onCellValidation" 
	                 allowAlternating="true" >
	                <div property="columns">
	                    <div type="checkcolumn" width="20"></div>
	                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>        
	                    <div field="key" width="120" headerAlign="center" >变量Key
	                    	<input property="editor" class="mini-textbox" style="width:100%" required="true" vtype="maxLength:255" />
	                    </div>    
	                    <div field="type" width="100" headerAlign="center" >变量类型
	                    	<input  property="editor" class="mini-combobox" data="[{id:'java.lang.String',text:'java.lang.String'},{id:'java.lang.Double',text:'java.lang.Double'},{id:'java.util.Date',text:'java.util.Date'}]"
								     required="true" allowInput="true"/> 
	                    	
	                    </div>
	                    <div field="val"  width="100" headerAlign="center" >值
	                    	 <input property="editor" class="mini-textarea" style="width:100%;"/>
	                    </div>
	                </div>
	            </div>
            </div>
            <script type="text/javascript">
            	mini.parse();
            	var grid=mini.get('varGrid');
            	grid.load();
            	function onActionRenderer(e) {
    	            var record = e.record;
    	            var uid = record._uid;
    	            var s = '<span class="icon-save" title="保存" onclick="saveRow(\'' + uid + '\')"></span>'
    	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
    	            return s;
    	        }
            	
            	function addRow(){
            		grid.addRow({});
            	}
            	
            	function refreshGrid(){
            		grid.load();
            	}
            	
            	function onCellValidation(e){
     	        	if(e.field=='key' && (!e.value||e.value=='')){
     	        		 e.isValid = false;
     	        		 e.errorText='Key不能为空！';
     	        	}
     	        	if(e.field=='type' && (!e.value||e.value=='')){
     	        		e.isValid = false;
     	       		 	e.errorText='类型不能为空！';
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
    	        		url:'${ctxPath}/bpm/core/bpmInst/saveVarRow.do',
    	        		data:{
    	        			actInstId:'${param.actInstId}',
    	        			data:mini.encode(row)
    	        		},
    	        		method:'POST',
    	        		success:function(text){
    	        			
    	        		}
    	        	});
    	        }
            	
            	function saveRows(){
            		//表格检验
    	        	grid.validate();
    	        	if(!grid.isValid()){
    	            	return;
    	            }
    	        	_SubmitJson({
    	        		url:'${ctxPath}/bpm/core/bpmInst/saveVarRows.do',
    	        		data:{
    	        			actInstId:'${param.actInstId}',
    	        			data:mini.encode(grid.getData())
    	        		},
    	        		method:'POST',
    	        		success:function(text){
    	        		}
    	        	});
            	}
            	
            	function delRow(uid){
            		var row=grid.getRowByUID(uid);
            		_SubmitJson({
    	        		url:'${ctxPath}/bpm/core/bpmInst/delVarRows.do',
    	        		data:{
    	        			actInstId:'${param.actInstId}',
    	        			keys:row.key
    	        		},
    	        		method:'POST',
    	        		success:function(text){
    	        			grid.load();
    	        		}
    	        	});
            	}
            	
            	function delRows(){
            		var keys=[];
            		var rows=grid.getSelecteds();
            		for(var i=0;i<rows.length;i++){
            			keys.push(rows[i].key);
            		}
            		_SubmitJson({
    	        		url:'${ctxPath}/bpm/core/bpmInst/delVarRows.do',
    	        		data:{
    	        			actInstId:'${param.actInstId}',
    	        			keys:keys.join(',')
    	        		},
    	        		method:'POST',
    	        		success:function(text){
    	        			grid.load();
    	        		}
    	        	});
            	}
            </script>
</body>
</html>