<%-- 
    Document   : project列表
    Created on : 2015-12-16, 18:11:48
    Author     : cmc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目列表</title>
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
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
	    <div  
	    	title="项目分类" 
	    	region="west"  
	    	width=250    
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
    	>
	        <div id="toolbar1">
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">新建分类</a>
	            <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>           
	        </div>
	        
	        
	        
	        <div class="mini-fit">
		        <ul 
		        	id="systree" 
		        	class="mini-tree"  
		        	style="width:100%;height:100%;" 
		        	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_PRO"
					showTreeIcon="true" 
					textField="name" 
					idField="treeId" 
					resultAsTree="false" 
					parentField="parentId" 
					expandOnLoad="true" 
					ondrawnode="onDrawNode"
	                onnodeclick="treeNodeClick"  
	                contextMenu="#treeMenu"
                >
	            </ul>
            </div>
	    </div>
	    <div showHeader="false" showCollapseButton="false"  region="center">
			<redxun:toolbar 
				entityName="com.redxun.oa.pro.entity.Project" 
				excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,popupSettingMenu,detail,edit,remove,saveCurGridView,saveAsNewGridView"  
			>
				<div class="self-toolbar mini-toolbar-top">
					<li>
						<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">新增项目</a>
					</li>
				</div>
			</redxun:toolbar>
		
			<div class="mini-fit rx-grid-fit" style="height: 100%;">
				<div 
					id="datagrid1" 
					class="mini-datagrid"
					style="width: 100%; height: 100%;" 
					allowResize="false"
					idField="viewId" 
					multiSelect="true" 
					showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" 
					onrowdblclick="openDetail(e)" 
					ondrawcell="onDrawCell" 
					onupdate="_LoadUserInfo();"
					allowAlternating="true" 
				>
					<div property="columns">
						<div name="action" cellCls="actionIcons" width="35" headerAlign="center" align="center" renderer="onActionRenderer"
							cellStyle="padding:0;">操作</div>
						<div field="name" width="200" headerAlign="center">项目名称</div>
						<div field="reporId" width="120" headerAlign="center">负责人</div>
						<div field="status" width="70" headerAlign="center" renderer="onStatusRenderer">状态</div>
						<div field="version" width="90" headerAlign="center">当前版本</div>
						<div field="type" width="90" headerAlign="center" renderer="onTypeRenderer">类型</div>
						<div field="startTime" width="100" headerAlign="center" renderer="onRenderer">启动时间</div>
						<div field="endTime" width="100" headerAlign="center" renderer="onRenderer">结束时间</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var projectCat;


		var tree = mini.get(systree);
		var mynodes = tree.getChildNodes(tree.getRootNode());
		var firstPage;
		if (mynodes.length > 0) {
			firstPage = mynodes[0].treeId;//第一个节点的Id
			mini.get('datagrid1').setUrl("${ctxPath}/oa/pro/project/listData.do?treeId="+ firstPage);
		} else {//如果没有tree，访问list1，给一个空的列表防止报错
			mini.get('datagrid1').setUrl("${ctxPath}/oa/pro/project/listBlank.do");
		}

		//mini.get('datagrid1').load();
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var uid = record.pkId;
			var  s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\''+uid+ '\')"></span>';
			     s += ' <span class="icon-edit" title="编辑" onclick="editMyRow(\''+ uid+ '\')"></span>';
			     s += ' <span class="icon-remove" title="删除" onclick="delRow(\''+ uid + '\')"></span>';
			return s;
		}

		//设置节点图标
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			e.iconCls = 'icon-folder';

		}
		
		
		//绘制负责人等等
		function onDrawCell(e) {
	        var tree = e.sender;
	        var record=e.record;
	        console.log();
	        var field=e.field;
	        	if(field=='reporId'){//给reporId那列显示成名字
	        	e.cellHtml='<a class="mini-user"  userId="'+record.reporId+'"></a>';
	        	}
	    } 

		//版本管理
		function versionRow(uid) {
			var row = grid.getRowByUID(uid);
			_OpenWindow({
				url : __rootPath + '/oa/pro/proVers/list.do?projectId='+ row.projectId,
				title : '项目版本管理--' + row.name,
				width : 880,
				height : 600,
				ondestroy : function(action) {
					grid.load();
				}
			});
		}
		
		
		//双击打开项目明细
		function openDetail(e){
			e.sender;
			var record=e.record;
			var pkId=record.pkId;
			detailMyRow(pkId);
		}

		//需求管理
		function reqRow(uid) {

			var row = grid.getRowByUID(uid);
			_OpenWindow({
				url : __rootPath + '/oa/pro/reqMgr/list.do?projectId='+ row.projectId,
				title : '项目需求管理--' + row.name,
				width : 950,
				height : 600,	
				ondestroy : function(action) {
					grid.load();
				}
			});
		}
		
		//项目人员管理
		function attendRow(uid){
			var row = grid.getRowByUID(uid);
			_OpenWindow({
				url : __rootPath + '/oa/pro/proAttend/list.do?projectId='+ row.projectId,
				title : '项目人员管理--' + row.name,
				width : 780,
				height : 480,
				ondestroy : function(action) {
					grid.load();
				}
			});
		}
		
		//新增节点
		function addNode(e) {
			var systree = mini.get("systree");
			var node = systree.getSelectedNode();
			var parentId = node ? node.treeId : 0;
			_OpenWindow({
				title : '添加节点',
				url : __rootPath + '/sys/core/sysTree/edit.do?parentId='
						+ parentId + '&catKey=CAT_PRO',
				width : 720,
				height : 350,
				ondestroy : function(action) {
					if (action == 'ok'){
					systree.load();
					}
				}
			});
		}
		
		//在根节点建立
		function addNodeInFirst(e){
			var systree = mini.get("systree");
			_OpenWindow({
				title : '添加节点',
				url : __rootPath + '/sys/core/sysTree/edit.do?parentId='
						+"0" + '&catKey=CAT_PRO',
				width : 720,
				height : 350,
				ondestroy : function(action) {
					if (action == 'ok'){
					systree.load();
					}
				}
			});
		}
		//刷新树
		function refreshSysTree() {
			var systree = mini.get("systree");
			systree.load();
		}

		//编辑树节点
		function editNode(e) {
			var systree = mini.get("systree");
			var node = systree.getSelectedNode();
			var treeId = node.treeId;
			_OpenWindow({
				title : '新增项目分类',
				url : __rootPath + '/sys/core/sysTree/edit.do?pkId=' + treeId,
				width : 780,
				height : 350,
				ondestroy : function(action) {
					if(action='ok')
						systree.load();
					
				}
			});
		}

		//删除树节点
		function delNode(e) {
			var systree = mini.get("systree");
			var node = systree.getSelectedNode();
			_SubmitJson({
				url : __rootPath + '/sys/core/sysTree/del.do?ids='+ node.treeId,
				success : function(text) {
					systree.load();
				}
			});
		}

		//按分类树查找数据
		function treeNodeClick(e) {
			var node = e.node;
			grid.setUrl(__rootPath + '/oa/pro/project/listData.do?treeId='+ node.treeId);
			grid.load();
			projectCat = node.treeId;
		}

		//添加数据
		function addOne() {
			if (projectCat == undefined) {
				projectCat = firstPage;
			}
			_OpenWindow({
				url : __rootPath + "/oa/pro/project/edit.do?parentId="+ projectCat+"&isadd="+"YES",
				title : "新增项目",
				width : 1300,
				height : 760,
				ondestroy : function(action) {
					if (action == 'ok' && typeof (addCallback) != 'undefined') {
						var iframe = this.getIFrameEl().contentWindow;
						addCallback.call(this, iframe);
					} else if (action == 'ok') {
						grid.reload();
					}
				}
			});
		}
		/*是否有project存在*/
		function isItemExist(id){
			var ifSuccess=false;
			$.ajax({
				type:'post',
				url:__rootPath+"/oa/pro/project/ifExist.do?id="+id,
			    async:false,
			    success:function(result){
			    	ifSuccess=result.success;
			    }
			});
			return ifSuccess;
		}
		
		
		//项目明细
		function detailMyRow(pkId) {
			if(isItemExist(pkId)){
				_OpenWindow({
		        	url: __rootPath + "/oa/pro/project/get.do?pkId=" + pkId,
		            title: "项目明细", 
		            max:"true"
		        });		
			}else{
				
			}
			
				
			
				
			
	        
	    }
		
		
		//编辑行数据
	    function editMyRow(pkId) {   
	    	if(isItemExist(pkId)){
	    		_OpenWindow({
		    		 url: __rootPath + "/oa/pro/project/edit.do?pkId="+pkId,
		            title: "编辑项目",
		            width: 900, height: 760,
		            ondestroy: function(action) {
		                if (action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});	
	    	}else{
	    		grid.load();
	    	}
	        
	    }
		
	//渲染时间
	 function onRenderer(e) {
            var value = e.value;
            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
            return "暂无";
        }
	
	//评论项目
	function discussRow(pkId){
		_OpenWindow({
		url:__rootPath+"/oa/pro/proWorkMat/edit.do?projectId="+pkId+"&type="+"PROJECT",
		title:"评论项目",
		width:960,
		height:730,
		ondestroy:function(action){
			if(action=='ok'){
				//XX操作
			}
		}
			
		});
	}
	
	 function onStatusRenderer(e) {
         var record = e.record;
         var status = record.status;
         
         var arr = [ {'key' : 'DEPLOYED','value' : '发布','css' : 'green'}, 
		             {'key' : 'DRAFTED','value' : '草稿','css' : 'orange'},
		             {'key' : 'RUNNING','value' : '进行中','css' : 'blue'}, 
		             {'key' : 'FINISHED','value' : '完成','css' : 'red'} ];
		
			return $.formatItemValue(arr,status);
     }
	 
	  function onTypeRenderer(e){
		  var record = e.record;
		  var type = record.type;
		  
		  var arr = [ {'key' : 'PROJECT','value' : '项目'},
		              {'key' : 'PRODUCT','value' : '产品'}, 
		              {'key' : 'OTHER','value' : '其他'}];
		  return $.formatItemValue(arr,type);
	  }
	  
	
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.pro.entity.Project" winHeight="450"
		winWidth="700" entityTitle="项目" baseUrl="oa/pro/project" />
 	
</body>
</html>