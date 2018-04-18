<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/commons/list.jsp" %>
	<title>数据字典分类管理</title>
	<style type="text/css">
		.mini-layout-region-body  .mini-fit{
			overflow: hidden;
		}
		
		.mini-layout-region{
			background: transparent;
		}
		
		.mini-tree .mini-grid-viewport,
		.mini-layout-region-west{
			background: #fff;
		}
	</style>
</head>
<body>
	<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
		    <div 
		    	title="数据分类树" 
		    	region="west" 
		    	width="208"  
		    	showSplitIcon="true"
		    	showCollapseButton="false"
		    	showProxy="false"
		    	style=" margin-left: 2px;"
	    	>
		        <div id="toolbar1" class="mini-toolbar" >
					<a class="mini-button" iconCls="icon-add"  onclick="addNode()" tooltip="添加"></a>
		            <a class="mini-button" iconCls="icon-expand"  onclick="onExpand()" tooltip="展开" plain="true"></a>
			    	<a class="mini-button" iconCls="icon-collapse"  onclick="onCollapse()" tooltip="收起" plain="true"></a>
		            <a class="mini-button" iconCls="icon-refresh"  onclick="refreshSysTree()" tooltip="刷新" plain="true"></a>   
		            <input 
		            	id="catCombo" 
		            	class="mini-combobox" 
		            	style="width:100%;" 
		            	textField="name" 
		            	valueField="key" 
		            	onvaluechanged="onCatChange" 
    					url="${ctxPath}/sys/core/sysTreeCat/allJson.do" 
    					value="CAT_DIM" 
   					/>            
		                         
		        </div>
		        
    			<div class="mini-fit">
    			  
		         <ul 
		         	id="systree" 
		         	class="mini-tree" 
		         	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_DIM" 
		         	style="width:100%;" 
					showTreeIcon="true" 
					textField="name"  
					idField="treeId" 
					resultAsTree="false" 
					parentField="parentId" 
					onload="onLoadTree" 
					expandOnLoad="1" 
					showTreeLines="true"
	                onnodeclick="treeNodeClick"  
	                contextMenu="#treeMenu"
                >        
	            </ul>
	           </div>
		    </div>
		    <div showHeader="false" showCollapseButton="false">
		        <div class="titleBar mini-toolbar" >
		         	<ul>
						<li>
							<a class="mini-button" iconCls="icon-save"  onclick="saveItems()">保存数据项</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-addfolder" onclick="newRow();">添加数据项</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-addfolder" onclick="newSubRow();">添加子数据项</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-remove"  onclick="delRow();">删除数据项</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-script" onclick="genQuerySql()">生成查询SQL</a>
						</li>
						<li>
							<a class="mini-button" iconCls="icon-main" onclick="doDeployMenu()">发布至管理菜单</a>
						</li>
						<li class="clearfix"></li>
					</ul>
			    </div>
		         <div class="mini-fit rx-grid-fit" style="border:0;">
			        <div id="sysdicGrid" class="mini-treegrid" style="width:100%;height:100%;"     
			            showTreeIcon="true" 
			            treeColumn="name" idField="dicId" parentField="parentId"  valueField="dicId"
			            resultAsTree="false"  
			            allowResize="true" expandOnLoad="true" 
			            allowCellValid="true" oncellvalidation="onCellValidation" 
			            allowCellEdit="true" allowCellSelect="true" url="${ctxPath}/sys/core/sysDic/listByTreeId.do"
		            	allowAlternating="true"
		            >
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
		    </div><!-- end of the center region  -->
		   </div>
		    <div id="sqlWin" class="mini-window" iconCls="icon-script" title="SQL查询语句" style="width: 550px; height: 350px; display: none;" showMaxButton="true" showShadow="true" showToolbar="true" showModal="true" allowResize="true" allowDrag="true">
				<textarea id="sql" class="mini-textarea" style="height: 100%; width: 100%"></textarea>
			</div>
		   <script type="text/javascript">
		   	mini.parse();
		   	var grid=mini.get("sysdicGrid");
		   	var systree=mini.get("systree");
		   	var catCombo=mini.get("catCombo");
		   	
		   	function onLoadTree(){
		   		//仅展开第二层
			   //systree.expandLevel(2);	
		   	}
		   	function onExpand(){
		   		systree.expandAll();
		   	}
		   	
		   	function onCollapse(){
		   		systree.collapseAll();
		   	}
		   	
		   	function addNode(e){
		   		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		var catKey=catCombo.getValue();
		   		_OpenWindow({
		   			title:'添加节点',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey='+catKey,
		   			width:800,
		   			height:450,
		   			ondestroy:function(action){
		   				if(action=='ok'){
		   					systree.load();
		   				}
		   			}
		   		});
		   	}
		   	
		   	function refreshSysTree(){
		   		systree.load();
		   	}
		   	
		   	function editNode(e){
		   		var node = systree.getSelectedNode();
		   		var treeId=node.treeId;
		   		_OpenWindow({
		   			title:'编辑节点',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?pkId='+treeId,
		   			width:720,
		   			height:385,
		   			ondestroy:function(action){
		   				if(action=='ok'){
		   					systree.load();
		   				}
		   			}
		   		});
		   	}
		   	
		   	function delNode(e){
		   		var node = systree.getSelectedNode();
		   		_SubmitJson({
		   			url:__rootPath+'/sys/core/sysTree/del.do?ids='+node.treeId,
		   			success:function(text){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	
		   	//更改分类生成树
		   	function onCatChange(e){
		   		var catKey=mini.get("#catCombo").getValue();
				systree.setUrl(__rootPath+'/sys/core/sysTree/listByCatKey.do?catKey='+catKey);
				systree.load();
		   	}
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/sys/core/sysDic/listByTreeId.do?treeId='+node.treeId);
		   		grid.load();
		   	}
		   	
		   	
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
	            var node=systree.getSelectedNode();
	            //为树类型才允许往下添加
	            if(node.dataShowType=='TREE'){
	            	s+= '<span class="icon-button icon-add" title="添加子数据项" onclick="newSubRow()"></span>';
	            }
	            s+=' <span class="icon-button icon-save" title="保存" onclick="saveRow(\'' + uid + '\')"></span>';
	            s+=' <span class="icon-button icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
	            return s;
	        }
			//在当前选择行的下添加子记录
	        function newSubRow(){
				var treeNode=systree.getSelectedNode();
				if(!treeNode){
					alert("请选择系统分类树节点！");
					return;
				}
				var node = grid.getSelectedNode();
				 //为树类型才允许往下添加
	            if(treeNode.dataShowType=='TREE'){
		            grid.addNode({}, "add", node);
	            }else{
		            grid.addNode({}, "before", node);
	            }
	           
	        }
			
	        function newRow() {            
	        	var treeNode=systree.getSelectedNode();
				if(!treeNode){
					alert("请选择系统分类树节点！");
					return;
				}
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
	            
				var node = systree.getSelectedNode();
				var treeId=null;
				if(node){
					treeId=node.treeId;
				}else{
					alert("请左树分类！");
					return;
				}

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
				var node = systree.getSelectedNode();
				var treeId=null;
				if(node){
					treeId=node.treeId;
				}else{
					alert("请左树分类！");
					return;
				}
				
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
	        
	        //生成查询的Sql
	        function genQuerySql(){
	        	var node = systree.getSelectedNode();
	        	if(!node){
	        		alert('请选择左树节点！');
	        		return;
	        	}
	        	var sql="select d.* from sys_dic d left join sys_tree t on d.TYPE_ID_=t.tree_id_ ";
				sql=sql+"where t.key_='"+node.key+"' and t.cat_key_='"+catCombo.getValue()+"' order by d.sn_ asc";
	        	
	        	var win = mini.get("sqlWin");
				mini.get('sql').setValue(sql);
				win.show();
	        }
	        
	        function doDeployMenu(){
	        	var node = systree.getSelectedNode();
	        	if(!node){
	        		alert('请选择左树节点！');
	        		return;
	        	}
	        	var dicKey=node.key;
	        	var catKey=catCombo.getValue();
	        	
	        	_OpenWindow({
					title:'发布菜单 ',
					height:450,
					width:800,
					url:__rootPath+"/sys/core/sysMenu/addNode.do",
					onload:function(){
						var win=this.getIFrameEl().contentWindow;
						win.setData({
							name:node.name+'分类维护',
							key:dicKey+'_DIC_MGR',
							parentId:'',
							url:'/sys/core/sysDic/'+catKey+'/'+dicKey+'/oneMgr.do',
							showType:'URL',
							isBtnMenu:'NO',
							isMgr:'NO'
						});
					}
				});
	        }

		   </script>
</body>
</html>