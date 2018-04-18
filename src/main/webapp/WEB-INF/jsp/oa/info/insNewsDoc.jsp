<%-- 
    Document   : Portal新闻公告列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新闻公告列表管理</title>
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
	<div class="mini-fit" style="height: 100px;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%; padding:100px;" allowResize="false" showVGridLines="false" showHGridLines="false"
			 idField="newId" ondrawcell="onDrawCell"
			multiSelect="true" showColumnsMenu="true" showPager="false" showColumns="false"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns" >
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name" width="180" headerAlign="center"
					allowSort="true"></div>
				<div field="createTime"  width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//编辑
		mini.parse();
		var pageSize = ${param['pageSize']};
		var url='${ctxPath}/oa/doc/doc/portalList.do?&pageSize='+pageSize;//显示list的url
		mini.get("datagrid1").setUrl(url);
				
		//在最前面加一个图标
		function onActionRenderer(e) {
			var s = '<span class="icon-html"></span>';
			return s;
		}
		
		//将新闻名字超链接化
		function onDrawCell(e) {
            var record = e.record;
            if (e.field == "name") {
                var name = record.name;
                e.cellStyle = "text-align:left";
                e.cellHtml = '<a href="javascript:detailRow1(\'' + record.id + '\')">' + name +'</a>';
            }
        }
		
		//点击新闻超链接弹出新网页显示内容
		
		function detailRow1(pkId) {
			var row = grid.getSelected();
			_OpenWindow({
				url : "${ctxPath}/oa/doc/doc/get.do?pkId=" + row.pkId,
				title : row.name,
				width : 800,
				height : 800,
			//max:true,
			});
		}
	</script>
	<style type="text/css">
		a:link,a:visited{
		text-decoration:none;  /*超链接无下划线*/
		}
	</style>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.doc.entity.Doc" winHeight="550"
		winWidth="780" entityTitle="个人文档" baseUrl="oa/doc/doc" />
</body>
</html>