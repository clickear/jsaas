<%
	//用户对话框示例
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户组选择框</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<h2>用户组选择示例</h2>
	<input type="button" value="用户组选择框" onclick="showGroupDlg()"/>
	<input type="button" value="部门选择框" onclick="showDepDlg()"/>
	
	<script type="text/javascript">
		mini.parse();
		function showDepDlg(){
			_DepDlg(false,function(groups){
					//为多个用户
					for(var i=0;i<groups.length;i++){
						alert(groups[i].name);
					}
			});
		}
		
		function showGroupDlg(){
			_GroupDlg(false,function(groups){
				//为多个用户
				for(var i=0;i<groups.length;i++){
					alert(groups[i].name);
				}
			});
		}
	</script>
</body>
</html>