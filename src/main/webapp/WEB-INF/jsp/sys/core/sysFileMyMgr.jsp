<%-- 
    Document   : [SysTree]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>我的个人附件列表</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
		<c:choose>
			<c:when test="${param['single']=='true'}">
				<c:set var="multiSelect" value="false"/>
			</c:when>
			<c:otherwise>
				<c:set var="multiSelect" value="true"/>
			</c:otherwise>
		</c:choose>
		<ul id="treeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeShowMenu">
			<li iconCls="icon-add" onclick="addNode" name="addNode">添加目录</li>        
		    <li iconCls="icon-edit" onclick="editNode" name="editNode">编辑目录</li>
		    <li iconCls="icon-remove" onclick="delNode" name="delNode">删除目录</li>
		</ul>
		
		<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;" >
			<c:if test="${not empty param['dialog']}">
			<div region="south" showSplitIcon="false"  showHeader="false" bodyStyle="padding:5px;" height="42">
				<div style="width:100%;padding:0;margin:0px;text-align: center">
						<a class="mini-button" iconCls="icon-ok" onclick="onOk">确定</a>
						&nbsp;
						<a class="mini-button" iconCls="icon-cancel" onclick="onCancel">关闭</a>
				</div>
			</div>
			</c:if>
			<div 
				title="我的文件夹" 
				region="west" 
				width="230"  
				showSplitIcon="true"
		    	showCollapseButton="false"
		    	showProxy="false"
			>
				 <div class="mini-toolbar" >
					<a class="mini-button" iconCls="icon-add"  onclick="addNode()">添加</a>
		            <a class="mini-button" iconCls="icon-remove"  onclick="delNode()">删除</a>
		            <a class="mini-button" iconCls="icon-refresh"onclick="onRefresh()">刷新</a>          
		         </div>
		         <div class="mini-fit" >
			         <ul id="folderTree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/myFileFolder.do" style="width:100%;height:100%;" 
						showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
		                onnodeclick="treeNodeClick"  contextMenu="#treeMenu">        
		            </ul>
	             </div>
			</div><!-- end of west -->
			<div title="文件列表" region="center">
				<!-- <div class="mini-toolbar">
					<table style="width:100%;" cellpadding="0" cellspacing="0" >
		                <tr>
		                    <td>
		                        <a class="mini-button" iconCls="icon-upload"  onclick="upload()">上传</a>
		                        <a class="mini-button" iconCls="icon-File"  onclick="moves()">批量归档文件</a>
		                        <a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
								<a class="mini-button" iconCls="icon-clear" onclick="onClearList(this)">清空</a>
		                    </td>
		                </tr>
		                <tr>
				        	<td >
				        		<form id="searchForm"  class="text-distance">
					        		<div>
					        			<span class="text">上传时间从:</span><input class="mini-datepicker" name="Q_createTime_D_GE" />
					        			至
					        			<input class="mini-datepicker" name="Q_createTime_D_LE"  />	
						        		<span class="text"> 文件名:</span><input class="mini-textbox" name="Q_fileName_S_LK" emptyText="输入文件名"  />                        
					        		</div>	                        
		                         </form>
				        	</td>
		                </tr>
		            </table>           
		         </div> -->
		         
		         
		         
	       	<div class="titleBar mini-toolbar" >
	         <ul>
				<li>
					<a class="mini-button" iconCls="icon-upload"  onclick="upload()">上传</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-File"  onclick="moves()">批量归档文件</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
				</li>
				<li class="clearfix"></li>
			</ul>
			<div class="searchBox">
				<form id="searchForm" class="search-form" style="display: none;">						
					<ul>
						<li>
							<span class="text">上传时间从:</span>
							<input class="mini-datepicker" name="Q_createTime_D_GE" />
		        			<span class="text">至</span>
		        			<input class="mini-datepicker" name="Q_createTime_D_LE"  />
						</li>
						<li>
							<span class="text"> 文件名:</span><input class="mini-textbox" name="Q_fileName_S_LK" emptyText="输入文件名"  />
						</li>
						<li class="searchBtnBox">
							<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
							<a class="mini-button _reset" onclick="onClearList(this)" >清空</a>
						</li>
						<li class="clearfix"></li>
					</ul>
				</form>	
				<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
					<i class="icon-sc-lower"></i>
				</span>
			</div>
	     </div>
		         
		         
		         
		         
		         
		         
				<div class="mini-fit rx-grid-fit">
					<div id="fileGrid" class="mini-datagrid" style="width: 100%; height: 100%; border:none" allowResize="false"
						url="${ctxPath}/sys/core/sysFile/myFiles.do" idField="keyVar" showColumnsMenu="true" multiSelect="${multiSelect}"
						sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
						<div property="columns">
							<div type="checkcolumn" width="25"></div>
							<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
							<div field="fileName" width="160" headerAlign="center" allowSort="true">文件名称</div>
							<div field="sizeFormat" width="120" headerAlign="center" sortField="totalBytes" allowSort="true">大小</div>
							<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">上传时间</div>
						</div>
					</div>
				</div>
			</div><!-- end of center -->
		</div>
	

	<script type="text/javascript">
		
    	mini.parse();
    	var grid=mini.get('fileGrid');
    	var tree=mini.get('folderTree');
    	grid.load();
    	
    	//显示上传目录
    	function upload(){
    		_UploadDialogShowFile({
   				from:'SELF',
   				types:'Office',
   				single:false,
   				onlyOne:false,
   				sizelimit:50,
   				showMgr:false,
   				callback:function(files){
   					grid.load();
   				}
   			});
            
    	}
    	//移动文件
    	function moves(){
    		var rows=grid.getSelecteds();
    		if(rows.length==0){
    			alert('请选择要移动的文件！') 
    			return;
    		}
    		var fileIds=_GetIds(rows);
    		_OpenWindow({
       			title:'移动目录',
       			url:__rootPath+'/sys/core/sysFile/moveFolder.do?fileIds='+fileIds,
       			width:780,
       			height:300
       		});
    	}
    	
    	function addNode(){
    		var node=tree.getSelectedNode();
    		var parentId=(node)? node.treeId:'0';
    		
    		_OpenWindow({
    			title:'编辑我的附件目录',
    			height:300,
    			width:580,
    			url:__rootPath+'/sys/core/sysTree/edit2.do?parentId='+parentId,
    			ondestroy:function(action){
    				if(action!='ok'){
    					return;
    				}
    				tree.load();
    			}
    		});
    	}
    	
    	function getFiles(){
    		return grid.getSelecteds();
    	}
    	
    	function delNode(){
    		var node=tree.getSelectedNode();
    		if(node==null) return;
    		
    		mini.confirm("确定删除记录？", "确定？",
   	            function (action) {
   					if(action!='ok') return;
	    			_SubmitJson({
	        			url:__rootPath+'/sys/core/sysTree/del.do?ids='+node.treeId,
	        			method:'POST',
	        			success:function(text){
	        				tree.load();
	        			}
	        		});
   	            }
   	        );
    	}
    	
    	//编辑节点
    	function editNode(){
    		var node=tree.getSelectedNode();
    		
    		_OpenWindow({
    			title:'编辑我的附件目录',
    			height:300,
    			width:580,
    			url:__rootPath+'/sys/core/sysTree/edit2.do?treeId='+node.treeId,
    			ondestroy:function(action){
    				if(action!='ok'){
    					return;
    				}
    				tree.load();
    			}
    		});
    	}
    	
    	//树节点点击
    	function treeNodeClick(e){
    		var node=e.node;
    		var treeId=node.treeId;
    		grid.setUrl("${ctxPath}/sys/core/sysFile/myFiles.do?treeId="+treeId);
    		grid.load();
    	}
    	
    	//编辑
        function onActionRenderer(e) {
            var record = e.record;
            var pkId = record.pkId;
            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
            		+ ' <span class="icon-move" title="移动目录" onclick="moveRow(\'' + pkId + '\')"></span>'
                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
            return s;
        }

		//刷新树
		function onRefresh(){
			tree.load();
		}
		
       	//onOk
       	function onOk(){
       		CloseWindow('ok');
       	}
       	//onCancel
       	function onCancel(){
       		CloseWindow('cancel');
       	}
       	
       	//显示菜单
       	function onBeforeShowMenu(e) {
       	    var menu = e.sender;
       	    var node = tree.getSelectedNode();
       	    if (!node) {
       	        e.cancel = true;
       	        return;
       	    }
       	 	var editItem = mini.getByName("editNode", menu);
 	    	var removeItem = mini.getByName("delNode", menu);
       	    if (node && node.treeId == "0") {
        	    editItem.hide();
        	    removeItem.hide();
       	    }else{
       	    	editItem.show();
        	    removeItem.show();
       	    }
       	}
       	
       	function delRow(pkId){
       		mini.confirm("确定删除记录？", "确定？",
      	            function (action) {
      					if(action!='ok') return;
      					_SubmitJson({
   	        			url:__rootPath+'/sys/core/sysFile/del.do?fileId='+pkId,
   	        			method:'POST',
   	        			success:function(text){
   	        				grid.load();
   	        			}
   	        		});
      			}
       		);
       	}
       	
       	function moveRow(pkId){
       		_OpenWindow({
       			title:'移动目录',
       			url:__rootPath+'/sys/core/sysFile/moveFolder.do?fileId='+pkId,
       			width:780,
       			height:450
       		});
       	}
       	
       	//附件明细
       	function detailRow(pkId){
       		_OpenWindow({
       			title:'附件明细',
       			height:500,
       			width:800,
       			url:__rootPath+'/sys/core/sysFile/get.do?fileId='+pkId
       		});
       	}
       	
       	//附件预览
       	function previewRow(pkId){
       		_OpenWindow({
       			title:'附件预览',
       			height:500,
       			width:800,
       			url:__rootPath+'/sys/core/sysFile/preview.do?fileId='+pkId
       		});
       	}
       	
       	function onSearch(){
       		var formData=$("#searchForm").serializeArray();
       		//var urlData=jQuery.param(formData);
       		//alert(urlData);
       		var filter=mini.encode(formData);
       		var node = tree.getSelectedNode();
       		var treeId=node==null?'0':node.treeId;
       		grid.setUrl("${ctxPath}/sys/core/sysFile/myFiles.do?treeId="+treeId + "&filter="+filter);
    		grid.load();
       	}
    </script>
	
</body>
</html>