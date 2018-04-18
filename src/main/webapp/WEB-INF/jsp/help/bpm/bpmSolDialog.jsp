<%
	//用户对话框示例
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>流程解决方案对话框</title>
    <%@include file="/commons/list.jsp"%>
</head>
<body>
	<h2>流程解决方案对话框</h2>
	<input type="button" value="流程解决方案对话框" onclick="showBpmSolutionDlg()"/>

	<script type="text/javascript">
		mini.parse();
		function showBpmSolutionDlg(){
			_BpmSolutionDialog(true,function(sols){
				alert('您选择了流程解决方案--'+sols[0].name);
			});
		}
		
	</script>
</body>
</html>