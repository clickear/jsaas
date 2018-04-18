<%-- 
    Document   : Portal门户里我的消息的显示页面
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>待办列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>

<style type="text/css">
a:link,a:visited {
	text-decoration: none; /*超链接无下划线*/
}
</style>
</head>
<body>
	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" url="${ctxPath}/bpm/core/bpmTask/myTasks.do?pageSize=${param['pageSize']}" style="width: 100%; height: 100%; padding: 100px;" allowResize="false" showVGridLines="false" showHGridLines="false" idField="id" ondrawcell="onDrawCell" multiSelect="true" showColumnsMenu="true" showPager="false" sizeList="[5,20,50]" showColumns="false" allowAlternating="true" >
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="description" sortField="DESCRIPTION_" width="180" headerAlign="center" allowSort="true">任务描述</div>
				<div field="createTime" sortField="CREATE_TIME_" width="60" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" allowSort="true">创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		//在最前面加一个图标
		function onActionRenderer(e) {
			var s = '<span class="icon-handle" title="办理"></span>';
			return s;
		}

		//将新闻名字超链接化
		function onDrawCell(e) {
			var record = e.record;
			var uid = record._uid;//行数
			if (e.field == "description") {
				var sub = record.description;
				e.cellStyle = "text-align:left";
				e.cellHtml = '<a href="javascript:handleRow(\'' + uid + '\')">' + sub + '</a>';

			}
		}

		//点击代办超链接弹出新网页显示内容
		function handleRow(uid) {
			var row = grid.getRowByUID(uid);
			_OpenWindow({
				title : '任务办理-' + row.description,
				height : 400,
				width : 780,
				//max:true,
				url : __rootPath + '/bpm/core/bpmTask/toStart.do?taskId=' + row.id,
				ondestroy : function(action) {
					if (action != 'ok')
						return;
					grid.load();
				}
			});
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	
</body>
</html>