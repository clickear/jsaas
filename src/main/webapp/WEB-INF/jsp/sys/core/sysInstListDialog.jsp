<%-- 
    Document   : 租用机构列表管理列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>租用机构列表管理列表管理</title>
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
		style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="40"
			showSplitIcon="false" style="width: 100%">
			<div class="mini-toolbar"
				style="text-align: center; padding-top: 8px; border-top: none; border-right: none"
				bodyStyle="border:0">
				<a class="mini-button" iconCls="icon-ok" onclick="onOk()">确定</a> 
				<span style="display: inline-block; width: 25px;"></span> 
				<a	class="mini-button" iconCls="icon-cancel"	onclick="onCancel()">取消</a>
			</div>
		</div>

		<div region="center" title="" bodyStyle="padding:2px;"
			showHeader="true" showCollapseButton="false">

			<redxun:toolbar entityName="com.redxun.sys.core.entity.SysInst"
				excludeButtons="popupSettingMenu,popupAttachMenu,detail,edit,remove,add,copyAdd,popupAddMenu">
			</redxun:toolbar>

			<div class="mini-fit" style="height: 100%;">


				<div id="datagrid1" class="mini-datagrid"
					style="width: 100%; height: 100%;" allowResize="false"
					url="${ctxPath}/sys/core/sysInst/listData.do" idField="instId"
					multiSelect="true" showColumnsMenu="true"
					sizeList="[5,10,20,50,100,200,500]" pageSize="20"
					allowAlternating="true" pagerButtons="#pagerButtons">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22"
							headerAlign="center" align="center" renderer="onActionRenderer"
							cellStyle="padding:0;">#</div>
						<div field="nameCn" width="120" headerAlign="center"
							allowSort="true">公司中文名</div>
						<div field="nameEn" width="100" headerAlign="center"
							allowSort="true">公司英文名</div>
						<div field="domain" width="100" headerAlign="center"
							allowSort="true">域名</div>
						<div field="instNo" width="100" headerAlign="center"
							allowSort="true">机构编号</div>
						<div field="status" width="60" headerAlign="center"
							allowSort="true">状态</div>
					</div>
				</div>
			</div>


		</div>
		<!-- end of the region center -->

	</div>






	<script type="text/javascript">
		mini.parse();

		// alert('mini='+mini);
		var articleDataGrid = mini.get("#datagrid1");
		// alert('articleDataGrid='+articleDataGrid);
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\''
					+ pkId + '\')"></span>';
			return s;
		}

		function onCancel() {
			CloseWindow('cancel');
		}

		function onOk() {
			CloseWindow('ok');
		}

		//返回选择微信文章信息
		function getArticles() {
			var articles = articleDataGrid.getSelecteds();
			return articles;
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.sys.core.entity.SysInst" winHeight="450"
		winWidth="700" entityTitle="租用机构列表管理" baseUrl="sys/core/sysInst" />
</body>
</html>