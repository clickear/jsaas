<%-- 
    Document   : 流程实例明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程实例明细</title>
<%@include file="/commons/dynamic.jspf"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/common/util.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/uploadpanel/jquery-uploadpanel.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery-grid.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jquery.img.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/bpmAnimate.js" type="text/javascript"></script>
<script language="javascript" src="${ctxPath}/scripts/jquery/plugins/jQuery.print.js"></script>
<!-- 处理流程图上的提示 -->
<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>

<!-- 加上扩展控件的支持 -->
<script type="text/javascript" charset="utf-8" src="${ctxPath}/scripts/ueditor/form-design/ueditor-ext.js"></script>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" excludeButtons="prevRecord,nextRecord,remove"/>
	<div class="mini-fit">
			<div title="业务数据">
				<textarea id="jsonData" style="display:none">${formInst.jsonData}</textarea>
				<div id="jsonview"></div>
			</div>
	</div>
	
	<rx:detailScript baseUrl="bpm/core/bpmInst" entityName="com.redxun.bpm.core.entity.BpmInst" formId="form1" />
	<script type="text/javascript">
	var useBmodel='{bpmInst.useBmodel}';
		
		$(function(){
			if(useBmodel!='YES'){
				//解析动态表单
				reqFormParse({
					solId:'${bpmInst.solId}',
					instId:'${bpmInst.instId}',
					containerId:'formView'
				});
			}
			
			var json=$("#jsonData").val();
			if(json!=''){ 
				$("#jsonview").jsonViewer(mini.decode(json));
			}
		 });
			
	</script>
</body>
</html>