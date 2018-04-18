<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
<head>
<title>业务流程解决方案导入</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-next" onclick="doNext">下一步</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<form id="zipForm" action="${ctxPath}/bpm/core/bpmSolution/importDirect.do" method="post" enctype="multipart/form-data">
		<table cellpadding="0" cellspacing="0" class="table-detail column_2_m" style="padding:6px;">
			<caption>第一步：上传流程解决方案的压缩文件</caption>
			<tr>
				<th>
					解决方案的ZIP文件
				</th>
    			<td>
    				<input id="zipFile" type="file" name="zipFile"/>
    			</td>
			</tr>
			<tr>
				<th>发布解决方案</th>
				<td>
					<input name="deploy" type="checkbox" value="true" />
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();
		function doNext(){
			var file=document.getElementById("zipFile");
    		if(file.value==''){
    			alert('请上传流程解决方案的压缩文件！');
    			return;
    		}
    		$("#zipForm").submit();
		}
	</script>
</body>
</html>