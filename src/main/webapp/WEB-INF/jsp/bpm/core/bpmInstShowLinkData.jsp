<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>流程实例关联的数据</title>
<%@include file="/commons/list.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
</head>
<body>
	<div class="mini-fit">
		<div id="tabs1" class="mini-tabs"  style="width:100%;height:99%;">
			<div title="JSON视图" >
				<div id="jsonViewer"></div>
			</div>
			<div title="JSON数据">
				<textarea class="mini-textarea" id="json" style="width:100%;height:100%">${json}</textarea>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var json=$("#json").val();
			
			if(json!=''){ 
				$("#jsonViewer").jsonViewer(mini.decode(json));
			}
		});
	</script>
</body>
</html>