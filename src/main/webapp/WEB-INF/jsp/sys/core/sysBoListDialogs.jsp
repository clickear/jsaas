<%-- 
    Document   : [系统自定义业务管理列表]列表页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>系统对话框列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
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
	    	title="列表分类" 
	    	region="west" 
	    	width="180"  
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false" 
	    	class="layout-border-r" 
   		>
	        <div id="toolbar1" class="mini-toolbar-no">
   	             <a class="mini-button" iconCls="icon-add"  onclick="addNode()">添加</a>
                 <a class="mini-button" iconCls="icon-refresh" onclick="refreshSysTree()">刷新</a>           
	        </div>
	         <ul 
	         	id="systree" 
	         	class="mini-tree" 
	         	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BO_LIST_DLG" 
	         	style="width:100%; height:100%;" 
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
		<div region="center" showHeader="false" showCollapseButton="false" title="对话框列表">
		  	<!-- <div class="mini-toolbar" >
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                        <a class="mini-button" iconCls="icon-create"  onclick="editBoList()">增加</a>
		                     <a class="mini-button" iconCls="icon-create"  onclick="editTreeDlg()">增加树型对话框</a>
		                     
		                     <a class="mini-button" iconCls="icon-edit" onclick="editSelRow()">编辑</a>
		                     <a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
		                     <a class="mini-button" iconCls="icon-search"  onclick="searchFrm()">查询</a>
		                     <a class="mini-button" iconCls="icon-cancel"  onclick="clearForm()">清空查询</a>
	                    </td>
	                </tr>
		            <tr>
	                    <td >
	                    	<form id="searchForm" class="text-distance">
								<div>
		                       		<span>名称:</span><input class="mini-textbox" name="Q_NAME__S_LK">
		                       		<span>标识键:</span><input class="mini-textbox" name="Q_KEY__S_LK">
			                    </div>
   							</form> 
	                    </td>
		            </tr>
	            </table>	
	        </div> --> 
	        
	        
	          <div class="titleBar mini-toolbar" >
		         <ul>
					<li>
						<a class="mini-button" iconCls="icon-create"  onclick="editBoList()">增加</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-create"  onclick="editTreeDlg()">增加树型对话框</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-edit" onclick="editSelRow()">编辑</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
					</li>
					<li class="clearfix"></li>
				</ul>
				<div class="searchBox">
					<form class="text-distance" id="searchForm" style="display: none;">					
						<ul>
							<li>
								<span>名称：</span><input class="mini-textbox" name="Q_NAME__S_LK">
							</li>
							<li>
								<span>标识键：</span><input class="mini-textbox" name="Q_KEY__S_LK">
							</li>
							<li class="searchBtnBox">
								<a class="mini-button _search" onclick="searchFrm()">搜索</a>
								<a class="mini-button _reset" onclick="clearForm()">还原</a>
							</li>
							<li class="clearfix"></li>
						</ul>
					</form>	
					<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
						<i class="icon-sc-lower"></i>
					</span>
				</div>
		    </div> 
	        
	        
	        
	        
	        
			<div class="mini-fit"> 
			<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
				url="${ctxPath}/sys/core/sysBoList/listDialogJsons.do" idField="id"
				multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
				<div property="columns">
					<div type="checkcolumn" width="20"></div>
					<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
					<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
					<div field="key"  sortField="KEY_"  width="120" headerAlign="center" allowSort="true">标识键</div>
					<div field="isTreeDlg" sortField="IS_TREE_DLG_"  width="80" headerAlign="center" allowSort="true" renderer="onIsTreeDlgRenderer">是否树型</div>
					<div field="isLeftTree"  sortField="IS_LEFT_TREE_"  width="60" headerAlign="center" allowSort="true" renderer="onIsLeftTreeRenderer">是否显示左树</div>
					<div field="isPage"  sortField="IS_PAGE_"  width="60" headerAlign="center" allowSort="true" renderer="onIsPageRenderer">是否分页</div>
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
			var s = '<span class="icon-edit" title="编辑" onclick="editSelRow(\'' + uid + '\')"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
					if(!record.refId && record.type==0){
						s+= ' <span class="icon-create" title="创建手机对话框"  onclick="createMobileDialog(\'' + pkId + '\')"></span>';
					}
					
					if(record.isGen=='YES'){
						s+= ' <span class="icon-preview" title="预览"  onclick="preview(\'' + uid + '\')"></span>'; 
						s+= ' <span class="icon-html" title="编辑代码"  onclick="editHtml(\'' + uid + '\')"></span>'; 
					}
			return s;
		}
		
		  function onIsTreeDlgRenderer(e) {
	            var record = e.record;
	            var isTreeDlg = record.isTreeDlg;
	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	    			        {'key' : 'NO','value' : '否','css' : 'orange'}];
	    			return $.formatItemValue(arr,isTreeDlg);
	        }
		
		  function onIsLeftTreeRenderer(e) {
	            var record = e.record;
	            var isLeftTree = record.isLeftTree;
	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	    			        {'key' : 'NO','value' : '否','css' : 'orange'}];
	    			return $.formatItemValue(arr,isLeftTree);
	        }
		  
		  function onIsPageRenderer(e) {
	            var record = e.record;
	            var isPage = record.isPage;
	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	    			        {'key' : 'NO','value' : '否','css' : 'orange'}];
	    			return $.formatItemValue(arr,isPage);
	        }
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysBoList" winHeight="450"
		winWidth="700" entityTitle="系统对话框定义" baseUrl="sys/core/sysBoList" />
		
	<script type="text/javascript">
		//添加bo列表
		function editBoList(pkId){
			var id=pkId?pkId:'';
			_OpenWindow({
				title:'系统对话框定义',
				width:800,
				height:450,
				max:true,
				url:__rootPath+'/sys/core/sysBoList/edit.do?isDialog=YES&pkId='+id
			});
		}
		function editSelRow(uid){
			var selected=null;
			if(uid){
				selected=grid.getRowByUID(uid);
			}else{
				selected=grid.getSelected();
			}
			if(!selected){
				alert('请选择行!');
			}
			var isTreeDlg=selected.isTreeDlg;
			var id=selected.id;
			
			if('YES'==isTreeDlg){
				editTreeDlg(id);
			}else{
				editBoList(id);
			}
		}
		function editTreeDlg(pkId){
			var id=pkId?pkId:'';
			_OpenWindow({
				title:'树型对话框定义',
				width:800,
				height:450,
				max:true,
				url:__rootPath+'/sys/core/sysBoList/treeDlgEdit.do?isDialog=YES&pkId='+id
			});
		}
		
		function preview(uid){
			var row=grid.getRowByUID(uid);
			_OpenWindow({
				title: row.name+'-预览',
				max:true,
				url:__rootPath+'/sys/core/sysBoList/'+row.key+'/list.do',
				height:500,
				width:800
			});
		}
		
		function editHtml(uid){
			var row=grid.getRowByUID(uid);
			var url=__rootPath+'/sys/core/sysBoList/edit3.do?id='+row.id;
			_OpenWindow({
				title: row.name+'-代码',
				max:true,
				url:url,
				height:500,
				width:800
			});
		}
		
		
		function addNode(e){
    		var systree=mini.get("systree");
    		var node = systree.getSelectedNode();
	   		var parentId=node?node.treeId:0;
	   		
	   		_OpenWindow({
	   			title:'添加列表分类',
	   			url:__rootPath+'/sys/core/sysTree/edit.do?parentId='+parentId+'&catKey=CAT_BO_LIST_DLG',
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
	   		grid.setUrl(__rootPath+'/sys/core/sysBoList/listDialogJsons.do?treeId='+node.treeId);
	   		grid.load();
	   	}
	   	
	   	function createMobileDialog(pkId){
			_OpenWindow({
	   			title:'创建手机对话框',
	   			url:__rootPath+'/sys/core/sysBoList/mobileEdit.do?refId='+pkId,
	   			width:780,
	   			height:350,
	   			ondestroy:function(action){
	   				if(action=='ok'){
	   					systree.load();
	   				}
	   			}
	   		});
		}
	</script>
</body>
</html>