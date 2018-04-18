<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>系统菜单管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@include file="/commons/dynamic.jspf" %>
    <link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
    
    <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script> 
    <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
</head>

	<div id="layout1" class="mini-layout" style="width:100%;height:100%;" >
		    <div title="用户组维度" region="west" width="180"  showSplitIcon="true" >
		    </div>
		    <div title="用户组" region="center" showHeader="true" showCollapseButton="false">
		    </div>
			<div title="用户" region="east"  width="380"  showSplitIcon="true" >
			</div>
	</div>
	<script type="text/javascript">
		mini.parse();
	</script>
<body>

</body>
</html>