<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程模型设计管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<input type="button" value="创建新模型" alt="" onclick="showCreateModelDlg()"/>
	<script type="text/javascript">
		function showCreateModelDlg(){
			_OpenWindow({
				title:'创建模型',
				url:__rootPath+'/bpm/def/bpmModel/edit.do',
				height:400,
				width:800
			});
		}
	</script>
</body>
</html>