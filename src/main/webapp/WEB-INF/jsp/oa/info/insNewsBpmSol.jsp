<%-- 
    Document   : Portal门户里我的流程解决方案的显示页面
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程解决方案</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>


</head>
<body>
	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid" url="${ctxPath}/oa/personal/bpmSolApply/mySolutions.do?pageSize=${param['pageSize']}" style="width: 100%; height: 100%; padding: 100px;" allowResize="false" showVGridLines="false" showHGridLines="false" idField="solId" ondrawcell="onDrawCell" multiSelect="true" showColumnsMenu="true" showPager="false" sizeList="[5,20,50]" showColumns="false" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name" headerAlign="center" width="180" allowSort="true">消息内容</div>
				<div field="createTime" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">收到时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
				
		//在最前面加一个图标
		function onActionRenderer(e) {
			var record = e.record;
			var s = '<span class="icon-task" title="启动"></span>';
			return s;
		}
		
		//将名字超链接化
		function onDrawCell(e) {
            var record = e.record;
            var uid = record._uid;//行数
            if (e.field == "name") {
                var name = record.name;
                e.cellStyle = "text-align:left";
                e.cellHtml = '<a href="javascript:detailRow1(\'' + uid + '\')">' + name +'</a>';

            }
        }
		
		//点击新闻超链接弹出新网页显示内容
		 function detailRow1(uid) {
			 var row=grid.getRowByUID(uid);
     		_OpenWindow({
     			title:row.name+'-流程启动',
     			url:__rootPath+'/bpm/core/bpmInst/start.do?solId='+row.solId,
     			max:true,
     			height:400,
     			width:800,
     		});
		 }
	</script>
	<style type="text/css">
a:link,a:visited {
	text-decoration: none; /*超链接无下划线*/
}
</style>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.info.entity.InfInnerMsg" winHeight="550" winWidth="780" entityTitle="消息" baseUrl="oa/info/infInnerMsg" />
</body>
</html>