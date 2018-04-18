<%-- 
    Document   : 我的流程事项申请列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的流程事项申请列表</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
</head>
<body>
	<div id="layout1" class="mini-layout"
		style="width: 100%; height: 100%;"
		borderStyle="border-left:1px solid #aaa;border-right:1px solid #aaa">
		<div title="我的流程事项申请分类" region="west" width="180" showSplitIcon="true">
			<div id="toolbar1" class="mini-toolbar"
				style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
				<table style="width: 100%;" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width: 100%;"></td>
					</tr>
				</table>
			</div>
			<ul id="systree" class="mini-tree"
				url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BPM_SOLUTION"
				style="width: 100%;" showTreeIcon="true" textField="name"
				idField="treeId" resultAsTree="false" parentField="parentId"
				expandOnLoad="true" onnodeclick="treeNodeClick">
			</ul>
		</div>
		<div title="我的流程事项申请列表" region="center" showHeader="true"
			showCollapseButton="false">
			<div class="mini-fit" style="height: 100px;">
				<div id="datagrid1" class="mini-datagrid"
					style="width: 100%; height: 100%;" allowResize="false"
					url="${ctxPath}/oa/personal/ApplybpmSolution/listData.do" idField="solId"
					multiSelect="true" showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" pageSize="20"
					allowAlternating="true" pagerButtons="#pagerButtons">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22"
							headerAlign="left" align="left" renderer="onActionRenderer"
							cellStyle="padding-left:2px;">#</div>
						<div field="name" width="140" headerAlign="center"
							allowSort="true">业务名称</div>
						<div field="key" width="100" headerAlign="center" allowSort="true">标识键</div>
						<div field="status" width="80" headerAlign="center"
							allowSort="true">状态</div>
						<div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss"
							width="80" headerAlign="center" allowSort="true">创建时间</div>
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
			var uid = record._uid;
			var name = record.name;
			  var s = ''
      		
			if (record.status == 'TEST' || record.status == 'DEPLOYED') {
				s += ' <span class="icon-start" title="启动流程" onclick="startRow(\''
						+ uid + '\')"></span>';
			}
			return s;
		}
		//该函数由添加调用时回调函数
		function addCallback(openframe) {
			var bpmSolution = openframe.getJsonData();
			mgrRow(bpmSolution.solId, bpmSolution.name);
		}
		function startRow(uid) {
			var row = grid.getRowByUID(uid);
			_OpenWindow({
				title : row.name + '-流程启动',
				url : __rootPath + '/bpm/core/bpmInst/start.do?solId='
						+ row.solId,
				max : true,
				height : 400,
				width : 800,
			});
		}
		function refreshSysTree() {
			var systree = mini.get("systree");
			systree.load();
		}
		//按分类树查找数据字典
		function treeNodeClick(e) {
			var node = e.node;
			grid
					.setUrl(__rootPath
							+ '/bpm/core/bpmSolution/listData.do?treeId='
							+ node.treeId);
			grid.load();
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.bpm.core.entity.BpmSolution" winHeight="450"
		winWidth="780" entityTitle="业务流程解决方案" baseUrl="bpm/core/bpmSolution" />
</body>
</html>