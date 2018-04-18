<%-- 
    Document   : [KdDoc]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>知识文档列表管理</title>
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

	<%-- <redxun:toolbar entityName="com.redxun.kms.core.entity.KdDoc" /> --%>

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDoc/getByDocIds.do?docIds=${param['docIds']}" idField="docId" multiSelect="true" showPager="false" allowAlternating="true" ondrawcell="onDrawCell">
			<div property="columns">
				<!-- <div type="indexcolumn" width="20"></div> -->
				<div field="subject" width="50" headerAlign="center" align="center" allowSort="true">文档标题</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();

		function onDrawCell(e) {
			field = e.field, value = e.value;
			var s;
			if (field == "subject") {
					//e.cellHtml = '<a href="${ctxPath}/kms/core/kdDoc/show.do?pkId='+e.record.docId+'">'+e.value+'&nbsp;</a>';
					s='<a href="${ctxPath}/kms/core/kdDoc/show.do?docId='+e.record.docId+'" target="_blank">'+value+'&nbsp;</a>';
					e.cellHtml = s;
			}
		}




	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.kms.core.entity.KdDoc" winHeight="450" winWidth="700" entityTitle="知识文档" baseUrl="kms/core/kdDoc" />
</body>
</html>