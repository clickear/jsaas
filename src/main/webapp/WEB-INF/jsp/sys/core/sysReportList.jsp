<%-- 
    Document   : 报表列表
    Created on : 2015-12-16, 18:11:48
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>报表列表</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
	<ul id="treeMenu" class="mini-contextmenu">
		<li iconCls="icon-add" onclick="addNode">添加分类</li>
		<li iconCls="icon-edit" onclick="editNode">编辑分类</li>
		<li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div 
			title="报表分类" 
			region="west" 
			width=250 
			showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
			style=" margin-left:2px" 
		>
			<div id="toolbar1" class="mini-toolbar" style="padding: 2px;border:0px;;">
				<table style="width: 100%;" cellpadding="0" cellspacing="0">
					<tr borderStyle="border:0px;">
						<td style="width: 100%;" class="mini-process-top">
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">新建分类</a> 
						<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a></td>
					</tr>
				</table>
			</div>
			<ul id="systree" class="mini-tree" style="width: 100%; height:100%;" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_REPORT" showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true" ondrawnode="onDrawNode" onnodeclick="treeNodeClick" contextMenu="#treeMenu">
			</ul>
		</div>
		
		<showHeader="true" showCollapseButton="false">
			<redxun:toolbar entityName="com.redxun.sys.core.entity.SysReport" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,detail,edit,remove,popupSettingMenu">
				<div class="self-toolbar">
					<li>
						<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">新增报表</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
					</li>
                    <li>
                    	<a class="mini-button" iconCls="icon-cancel" onclick="clearSearch()">清空查询</a>
                    </li>
				</div>
			</redxun:toolbar>

			<div class="mini-fit rx-grid-fit" style="height: 100%;">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/sys/core/sysReport/listData.do" idField="repId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
					<div property="columns">
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
						<div field="subject" width="220" headerAlign="center" allowSort="true">标题</div>
						<div field="filePath" width="120" headerAlign="center" allowSort="true">报表模板路径</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var reportCat;

		var tree = mini.get(systree);
		var mynodes = tree.getChildNodes(tree.getRootNode());
		var firstPage;
		if (mynodes.length > 0) {
			firstPage = mynodes[0].treeId;//第一个节点的Id
			mini.get('datagrid1').setUrl("${ctxPath}/sys/core/sysReport/listData.do?treeId=" + firstPage);
		} else {//如果没有tree，访问list1，给一个空的列表防止报错
			mini.get('datagrid1').setUrl("${ctxPath}/sys/core/sysReport/listBlank.do");
		}

		//mini.get('datagrid1').load();
		//行功能按钮 
		function onActionRenderer(e) {
			var record = e.record;
			var uid = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + uid + '\')"></span>';
			s += ' <span class="icon-edit" title="编辑" onclick="editMyRow(\'' + uid + '\')"></span>';
			s += ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
			s += ' <span class="icon-agree" title="发布" onclick="publishRow(\'' + uid + '\')"></span>';
			s += ' <span class="icon-reportView" title="预览" onclick="previewRow(\'' + uid + '\')"></span>';
			return s;
		}

		//设置节点图标
		function onDrawNode(e) {
			e.iconCls = 'icon-folder';

		}
		
		//新增节点
		function addNode(e) {
			var systree = mini.get("systree");
			var node = systree.getSelectedNode();
			var parentId = node ? node.treeId : 0;
			//var parentId = 0;
			_OpenWindow({
				title : '添加节点',
				url : __rootPath + '/sys/core/sysTree/edit.do?parentId=' + parentId + '&catKey=CAT_REPORT',
				width : 720,
				height : 350,
				ondestroy : function(action) {
					if (action == 'ok') {
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
				title : '编辑报表分类节点',
				url : __rootPath + '/sys/core/sysTree/edit.do?pkId=' + treeId,
				width : 780,
				height : 350,
				ondestroy : function(action) {
					if (action = 'ok')
						systree.load();

				}
			});
		}

		//删除树节点
		function delNode(e) {
			var systree = mini.get("systree");
			var node = systree.getSelectedNode();
			_SubmitJson({
				url : __rootPath + '/sys/core/sysTree/del.do?ids=' + node.treeId,
				success : function(text) {
					systree.load();
				}
			});
		}

		//按分类树查找数据
		function treeNodeClick(e) {
			var node = e.node;
			grid.setUrl(__rootPath + '/sys/core/sysReport/listData.do?treeId=' + node.treeId);
			grid.load();
			reportCat = node.treeId;
		}

		
		//添加数据
		function addOne() {
			if (reportCat == undefined) {
				reportCat = firstPage;
			}
			_OpenWindow({
				url : __rootPath + "/sys/core/sysReport/edit.do?parentId=" + reportCat,
				title : "上传报表",
				width : 960,
				height : 730,
				ondestroy : function(action) {
					if (action == 'ok'){
						grid.reload();
						mini.showTips({
				            content: "<b>成功</b> <br/>数据保存成功",
				            state: 'success',
				            x: 'center',
				            y: 'center',
				            timeout: 3000
				        });
					}
				}
			});
		}
		
		//项目明细
		function detailMyRow(pkId) {
			_OpenWindow({
				url : __rootPath + "/sys/core/sysReport/get.do?pkId=" + pkId,
				title : "报表明细",
				width : 720,
				height : 600,
			});
		}
		
		//报表发布到菜单
		function publishRow(pkId) {
			_OpenWindow({
				url : __rootPath + "/sys/core/sysReport/publish.do?pkId=" + pkId,
				title : "报表发布到菜单",
				width : 720,
				height : 600,
			});
		}
		
		

		//编辑行数据
		function editMyRow(pkId) {
			_OpenWindow({
				url : __rootPath + "/sys/core/sysReport/edit.do?pkId=" + pkId,
				title : "编辑报表",
				width : 900,
				height : 730,
				ondestroy : function(action) {
					if (action == 'ok') {
						grid.reload();
					}
				}
			});
		}

		/* 预览报表 */
		function previewRow(pkId) {
			var row = grid.getSelected();
			var paramConfig = row.paramConfig;
			_OpenWindow({
				url : __rootPath + "/sys/core/sysReport/preview.do?pkId=" + pkId,
				title : "预览报表",
				width : 1030,
				height :750,
				onload:function(){
	    			 var iframe = this.getIFrameEl();
	                 iframe.contentWindow.setContent(paramConfig);
	    		},
				ondestroy : function(action) {
					if (action == 'ok') {
						grid.reload();
					}
				}
			});
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysReport" winHeight="450" winWidth="700" entityTitle="报表" baseUrl="sys/core/sysReport" />
</body>
</html>