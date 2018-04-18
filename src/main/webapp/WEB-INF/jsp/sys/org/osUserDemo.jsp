<%
	//用户选择框
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户选择框</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@include file="/commons/dynamic.jspf"%>
    <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
    <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
  
    
</head>
<body>

	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
			<div region="north" showSplit="false" showHeader="false" height="50" showSplitIcon="false" >
		 	north
		 </div>
	
			<div region="south" showSplit="false" showHeader="false" height="50" showSplitIcon="false" >
		 	south
		 </div>

		 <div title="用户组" region="west" width="180"  showSplitIcon="true">
		 	west
		 </div>
		 <div region="center" title="用户列表"  bodyStyle="padding:2px;" showHeader="true" showCollapseButton="false">
			center
		</div><!-- end of the region center -->
		
		
		<div region="east" title="选中用户列表"  bodyStyle="padding:2px;" width="250" showHeader="true" showCollapseButton="false">
			east
			
		</div><!-- end of the region east -->

		
		 
	</div>

	
	<script type="text/javascript">
		mini.parse();
		
	</script>
</body>
</html>