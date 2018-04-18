<%
	//用户组对话框示例
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户组选择框</title>
 <%@include file="/commons/list.jsp"%>
</head>
<body>
	<h2>用户组对话框示例</h2>
	<input type="button" value="..." onclick="showGroupDlg()"/>
	<script type="text/javascript">
		mini.parse();
		function showGroupDlg(){
			_GroupDlg(false,function(groups){
					//为多个用户
					for(var i=0;i<groups.length;i++){
						alert(groups[i].name);
					}
					//返回单个用户
					//alert(user.fullname);
			},true);
		}
	</script>
</body>
</html>