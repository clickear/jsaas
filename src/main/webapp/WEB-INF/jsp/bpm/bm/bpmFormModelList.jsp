<%-- 
    Document   : 业务模型管理列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务模型管理列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	
	
	
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
		    <div title="业务模型分类" region="west" width="180"  showSplitIcon="true" >
		        <div id="toolbar1" class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
					<table style="width:100%;" cellpadding="0" cellspacing="0">
		                <tr>
		                    <td style="width:100%;"  class="background-top">
		                        <a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">添加</a>
		                        <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>
		                    </td>
		                </tr>
		            </table>           
		        </div>
		         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_MODEL" style="width:100%;" 
					showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
	                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">        
	            </ul>
		    </div>
		    <showHeader="true" showCollapseButton="false">
		        <redxun:toolbar entityName="com.redxun.bpm.bm.entity.BpmFormModel" >
		        	<div class="self-toolbar">
			        <a class="mini-button" iconCls="icon-unlock" plain="true" onclick="enable()">启用</a>
	        		<a class="mini-button" iconCls="icon-lock" plain="true" onclick="disable()">禁用</a>
	        		
	        		</div>
		        </redxun:toolbar>
		         <div class="mini-fit" style="border:0;">
			        <div class="mini-fit" style="height: 100px;">
						<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
						url="${ctxPath}/bpm/bm/bpmFormModel/listData.do" idField="fmId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" 
						pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
							<div property="columns">
								<div type="checkcolumn" width="20"></div>
								<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
								<div field="name" width="140" headerAlign="center" allowSort="true">名称</div>
								<div field="key" width="140" headerAlign="center" allowSort="true">标识键</div>
								<div field="version" width="80" headerAlign="center" allowSort="true">版本号</div>
								<div field="status" width="60" headerAlign="center" allowSort="true">状态</div>
								<div field="isPublic" width="60" headerAlign="center" allowSort="true">是否公共</div>
							</div>
						</div>
					</div>
		        </div>
		    </div><!-- end of the center region  -->
		   </div>
		<script type="text/javascript">
			
			//编辑;
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var mainModelId=record.mainModelId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-version" title="版本管理" onclick="versionRow(\'' + mainModelId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
	            return s;
	        }
			//展示所有版本
			function versionRow(uid){

				_OpenWindow({
					url:__rootPath+'/bpm/bm/bpmFormModel/versions.do?fmId='+uid,
					title:'业务模型版本管理',
					width:780,
					height:480,
					ondestroy:function(action){
						grid.load();
					}
				});
			}
			//展示其最新版本的JSON
			function jsonRow(uid){
				_OpenWindow({
					url:__rootPath+'/bpm/bm/bpmFormModel/json.do?fmId='+uid,
					title:'JSON数据示例',
					width:780,
					height:480
				});
			}
	      	//启用
        	function enable(){
        		changeStatus('ENABLED');
        	}
        	//禁用
        	function disable(){
        		changeStatus('DISABLED');
        	}
        	
        	function changeStatus(status){
        		var ids=_GetGridIds('datagrid1');
        		if(ids.length==0) return;
        		_SubmitJson({
        			url:'${ctxPath}/bpm/bm/bpmFormModel/enable.do',
        			data:{ids:ids.join(','),enable:status},
        			method:'POST',
        			success:function(result){
        				grid.load();
        			}
        		});
        	}
			
	    	function addNode(e){
	    		var systree=mini.get("systree");
	    		var node = systree.getSelectedNode();
		   		var parentId=node?node.treeId:0;
		   		
		   		_OpenWindow({
		   			title:'添加业务模型分类',
		   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_FORM_MODEL',
		   			width:720,
		   			height:350,
		   			ondestroy:function(action){
		   				if(action=='ok'){
		   					systree.load();
		   				}
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
		   		grid.setUrl(__rootPath+'/bpm/bm/bpmFormModel/listData.do?treeId='+node.treeId);
		   		grid.load();
		   	}
			
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.bm.entity.BpmFormModel" winHeight="500" winWidth="800" 
	entityTitle="业务模型管理" baseUrl="bpm/bm/bpmFormModel" />
</body>
</html>