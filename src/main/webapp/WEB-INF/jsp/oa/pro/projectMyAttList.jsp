<%-- 
    Document   : 有我参与的项目，包括我所在的组
    Created on : 2015-12-16, 18:11:48
    Author     : 陈茂昌
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
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <showHeader="true" showCollapseButton="false">
			<redxun:toolbar 
				entityName="com.redxun.oa.pro.entity.Project" 
				excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove"
			>
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
					pagerButtons="#pagerButtons"
				>
					<div property="columns">
						<div 
							name="action" 
							cellCls="actionIcons" 
							width="22" 
							headerAlign="center" 
							align="center" 
							renderer="onActionRenderer"
							cellStyle="padding:0;"
						>操作</div>
						<!-- <div field="treeId" width="120" headerAlign="center" allowSort="true">分类Id</div>-->
						<!-- <div field="proNo" width="120" headerAlign="center" allowSort="true">编号</div> -->
						<!-- <div field="tag" width="120" headerAlign="center" >标签名称</div> -->
						<div field="name" width="120" headerAlign="center">项目名称</div>
						<!-- <div field="descp" width="120" headerAlign="center" >描述</div> -->
						<div field="reporId" width="120" headerAlign="center">负责人</div>
						<!-- <div field="costs" width="120" headerAlign="center" >预计费用</div> -->
						<div field="status" width="120" headerAlign="center" renderer="onStatusRenderer">状态</div>
						<div field="version" width="120" headerAlign="center">当前版本</div>
						<div field="type" width="120" headerAlign="center" renderer="onTypeRenderer">类型</div>
						<div field="startTime" width="120" headerAlign="center" renderer="onRenderer">启动时间</div>
						<div field="endTime" width="120" headerAlign="center" renderer="onRenderer">结束时间</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	
			mini.get('datagrid1').setUrl("${ctxPath}/oa/pro/project/listMyAttend.do");
		    mini.get('datagrid1').load();
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var uid = record.pkId;
			var  s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\''+uid+ '\')"></span>';
			     s += ' <span class="icon-edit" title="编辑" onclick="editMyRow(\''+ uid+ '\')"></span>';
			     
			return s;
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
		
		

		//绘制负责人等等
		function onDrawCell(e) {
			var tree = e.sender;
			var record = e.record;
			console.log();
			var field = e.field;

			if (field == 'reporId') {
				e.cellHtml = '<a class="mini-user"  userId="'+record.reporId+'"></a>';
			}
		}

		//版本管理
		function versionRow(uid) {
			var row = grid.getRowByUID(uid);
			_OpenWindow({
				url : __rootPath + '/oa/pro/proVers/list.do?projectId='
						+ row.projectId,
				title : '项目版本管理--' + row.name,
				width : 880,
				height : 600,
				ondestroy : function(action) {
					grid.load();
				}
			});
		}

		//双击打开明细
		function openDetail(e) {
			e.sender;
			var record = e.record;
			var pkId = record.pkId;
			detailMyRow(pkId);
		}

		//需求管理
		function reqRow(uid) {

			var row = grid.getRowByUID(uid);
			_OpenWindow({
				url : __rootPath + '/oa/pro/reqMgr/list.do?projectId='
						+ row.projectId,
				title : '项目需求管理--' + row.name,
				width : 950,
				height : 600,
				ondestroy : function(action) {
					grid.load();
				}
			});
		}

		//项目人员管理
		function attendRow(uid) {
			var row = grid.getRowByUID(uid);
			_OpenWindow({
				url : __rootPath + '/oa/pro/proAttend/list.do?projectId='
						+ row.projectId,
				title : '项目人员管理--' + row.name,
				width : 780,
				height : 480,
				ondestroy : function(action) {
					grid.load();
				}
			});
		}

		//添加数据
		function addOne() {
			if (projectCat == undefined) {
				projectCat = firstPage;
			}
			_OpenWindow({
				url : __rootPath + "/oa/pro/project/edit.do?parentId="
						+ projectCat,
				title : "新增项目",
				width : 960,
				height : 730,
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
		//项目明细
		function detailMyRow(pkId) {

			_OpenWindow({
				url : __rootPath + "/oa/pro/project/get.do?pkId=" + pkId,
				title : "项目明细",
				width : 780,
				height : 800,
			});
		}

		//编辑行数据
		function editMyRow(pkId) {
			_OpenWindow({
				url : __rootPath + "/oa/pro/project/edit.do?pkId=" + pkId,
				title : "编辑项目",
				width : 900,
				height : 750,
				ondestroy : function(action) {
					if (action == 'ok') {
						grid.reload();
					}
				}
			});
		}

		//渲染时间
		function onRenderer(e) {
			var value = e.value;
			if (value)
				return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
			return "暂无";
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