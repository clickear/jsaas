<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/get.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.css" />
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/json-viewer/jquery.json-viewer.js"></script>
   <style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body>
	<div class="mini-fit">
		<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;" plain="false">
		    <div title="流程图">
		    	 <img src="${ctxPath}/bpm/activiti/processImage.do?actDefId=${param['actDefId']}"/>
		    </div>
		</div>
    </div>
	
	<script type="text/javascript">
		mini.parse();
	</script>
</body>
</html>