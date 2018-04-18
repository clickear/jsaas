<%-- 
    Document   : 业务模型管理历史版本
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务对象的数据格式</title>
<%@include file="/commons/list.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>

</head>
<body>
	<div class="mini-fit">
		<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;border:none" plain="false">
		    <div title="JSON格式示例" iconCls="icon-script">
		        <pre id="jsonview"></pre>
		    </div>
		    <div title="纯文本格式" iconCls="icon-text">
		        <textarea  class="mini-textarea" id="jsonCode" style="width:100%;height:100%;">${jsonCode}</textarea>
		    </div>
		</div>
	</div>	
	<script type="text/javascript">
		mini.parse();
		$(function(){
			var json=mini.get('jsonCode').getValue();
			$("#jsonview").jsonViewer(mini.decode(json));	
		});
		
	</script>
</body>
</html>

