<%-- 
    Document   : 流程定义管理列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>流程定义管理列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
	<span url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_DEF"></span>
	<ul id="treeMenu" class="mini-contextmenu">
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	
	<div 
		id="layout1" 
		class="mini-layout" 
		style="width:100%;height:100%;"
	>
	    <div 
	    	title="流程定义分类" 
	    	region="west" 
	    	width="200"
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
	    	class="layout-border-r" 
    	>
	        <div id="toolbar1" class="mini-toolbar-no" style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">添加</a>
                <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>          
	        </div>
	         <ul 
	         	id="systree" 
	         	class="mini-tree" 
	         	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_DEF" 
	         	style="width:100%;" 
				showTreeIcon="true" 
				textField="name" 
				idField="treeId" 
				resultAsTree="false" 
				parentField="parentId" 
				expandOnLoad="true"
                onnodeclick="treeNodeClick"  
                contextMenu="#treeMenu"
               >        
            </ul>
	    </div>
	    <div region="center" showHeader="false" showCollapseButton="false">
			<redxun:toolbar entityName="com.redxun.bpm.core.entity.BpmDef">
				<div class="self-toolbar ">
					<li>
						<a class="mini-button" iconCls="icon-upload" onclick="uploadBpmnFile()" plain="true">上传BPMN设计文件</a>
					</li>
					<!--  a class="mini-button" iconCls="icon-bpmn" onclick="bpmnDesign()" plain="true">BPMN流程建模</a>-->
				</div>
			</redxun:toolbar>
		
			<div class="mini-fit rx-grid-fit" style="height: 100px;">
				<div 
					id="datagrid1" 
					class="mini-datagrid" 
					style="width: 100%; height: 100%;" 
					allowResize="false" 
					pagerButtons="#pagerButtons"
					url="${ctxPath}/bpm/core/bpmDef/listData.do" 
					idField="defId" 
					multiSelect="true" 
					showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" 
					allowAlternating="true"
				>
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
						<div field="subject" width="160" headerAlign="center" allowSort="true">标题</div>
						<div field="key" width="120" headerAlign="center" allowSort="true">标识Key</div>
						<div field="status" width="60" headerAlign="center" allowSort="true" renderer="onStatusRenderer">状态</div>
						<div field="version" width="60" headerAlign="center" allowSort="true">版本号</div>
						<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="80" headerAlign="center" allowSort="true">创建时间</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var modelId=record.modelId;
	            
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-flow-design" title="设计" onclick="designRow(\'' + modelId + '\')"></span>'
	                    + ' <span class="icon-version" title="版本管理" onclick="versionRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-upload" title="上传新版本BPMN文件" onclick="uploadNewBpmnFile('+pkId+')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            
	                    return s;
	        }
        	
	        function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            
	            var arr = [
				            {'key' : 'DEPLOYED', 'value' : '发布','css' : 'green'}, 
				            {'key' : 'INIT','value' : '创建','css' : 'orange'} ];
				
				return $.formatItemValue(arr,status);
	            
	        }
        	
        	//该函数由添加调用时回调函数
	        function addCallback(openframe){
	        	var bpmDef=openframe.getJsonData();
	        	designRow(bpmDef.modelId);
	        }
        	//版本管理
        	function versionRow(uid){
        		var row=grid.getRowByUID(uid);
        		
        		_OpenWindow({
					url:__rootPath+'/bpm/core/bpmDef/versions.do?mainDefId='+row.mainDefId,
					title:'流程定义版本管理--'+row.subject,
					width:780,
					height:480,
					ondestroy:function(action){
						grid.load();
					}
				});
        	}
        	
        	function bpmnDesign(){
        		var row=grid.getSelected();
        		if(!row){
        			alert('请选择行！');
        		}
        		var subject=row?'':row.subject;
        		_OpenWindow({
        			title:'BPMN流程建模_'+subject,
        			width:780,
					height:480,
					url:__rootPath+'/bpm/modeler/designer.do?defId='+row.defId,
					ondestroy:function(action){
						if(action!='ok') return;
						grid.load();
					}
        		});
        	}

        	function imageRow(uid){
        		var row=grid.getRowByUID(uid);
        		_OpenWindow({
        			title:'流程图-'+row.subject,
        			height:450,
        			width:780,
        			max:true,
        			url:__rootPath+'/bpm/core/bpmDef/image.do?actDefId='+row.actDefId
        		});
        	}
        	
	        function designRow(modelId){
        		_OpenWindow({
        			width:800,
        			height:600,
        			max:true,
        			url:__rootPath+'/process-editor/modeler.jsp?modelId='+modelId,
        			title:'流程建模设计',
        			ondestroy:function(action){
        				if(action!='ok')return;;
        				grid.load();
        			}
        		});
        	}
	        function addNode(e){
	    		var systree=mini.get("systree");
	    		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		
		   		_OpenWindow({
		   			title:'添加流程定义分类',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_BPM_DEF',
		   			width:720,
		   			height:350,
		   			ondestroy:function(action){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	function refreshSysTree(){
		   		var systree=mini.get("systree");
		   		systree.load();
		   	}
		   	
		   	function editNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		var treeId=node.treeId;
		   		_OpenWindow({
		   			title:'编辑节点',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?pkId='+treeId,
		   			width:780,
		   			height:350,
		   			ondestroy:function(action){
		   				if(action=='ok'){
		   					systree.load();
		   				}
		   			}
		   		});
		   	}
		   	
		   	function delNode(e){
		   		var systree=mini.get("systree");
		   		var node = systree.getSelectedNode();
		   		_SubmitJson({
		   			url:__rootPath+'/sys/core/sysTree/del.do?ids='+node.treeId,
		   			success:function(text){
		   				systree.load();
		   			}
		   		});
		   	}
		   	
		   	//按分类树查找数据字典
		   	function treeNodeClick(e){
		   		var node=e.node;
		   		grid.setUrl(__rootPath+'/bpm/core/bpmDef/listData.do?treeId='+node.treeId);
		   		grid.load();
		   	}
		   	
		   	function uploadBpmnFile(){
		   		_OpenWindow({
		   			title:'上传BPMN文件',
		   			url:__rootPath+'/bpm/core/bpmDef/upBpmnFile.do',
		   			width:590,
		   			height:360,
		   			ondestroy:function(action){
		   				if(action!='ok') return;
		   				grid.load();
		   			}
		   		});
		   	}
		   	
		   	function uploadNewBpmnFile(pkId){
		   		_OpenWindow({
		   			title:'上传BPMN文件',
		   			url:__rootPath+'/bpm/core/bpmDef/upBpmnFile.do?defId='+pkId,
		   			width:605,
		   			height:360,
		   			ondestroy:function(action){
		   				if(action!='ok') return;
		   				grid.load();
		   			}
		   		});
		   	}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmDef" winHeight="400" winWidth="900" entityTitle="流程定义管理" baseUrl="bpm/core/bpmDef" />
</body>
</html>