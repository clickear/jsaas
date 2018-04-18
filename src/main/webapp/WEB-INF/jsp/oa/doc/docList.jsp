<%-- 
    Document   : [Doc]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>文件列表管理</title>
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

	<redxun:toolbar entityName="com.redxun.oa.doc.entity.Doc" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/doc/doc/listData.do" idField="docId"
			multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div> 	
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
				<div field="folderId" width="120" headerAlign="center"
					allowSort="true">文件夹ID</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">文档名称</div>
				<div field="content" width="120" headerAlign="center"
					allowSort="true">内容</div>
				<div field="summary" width="120" headerAlign="center"
					allowSort="true">摘要</div>
				<div field="hasAttach" width="120" headerAlign="center"
					allowSort="true">是否包括附件</div>
				<div field="isShare" width="120" headerAlign="center"
					allowSort="true">是否共享</div>
				<div field="author" width="120" headerAlign="center"
					allowSort="true">作者</div>
				<div field="keywords" width="120" headerAlign="center"
					allowSort="true">关键字</div>
				<div field="docType" width="120" headerAlign="center"
					allowSort="true">文档类型</div>
				<div field="swfPath" width="120" headerAlign="center"
					allowSort="true">SWF文件f路径</div>
				<div field="userId" width="120" headerAlign="center"
					allowSort="true">用户ID</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.doc.entity.Doc" winHeight="450"
		winWidth="700" entityTitle="[Doc]" baseUrl="oa/doc/doc" />
</body>
</html>