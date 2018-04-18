
<%-- 
    Document   : [文章]编辑页
    Created on : 2017-12-26 14:39:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>上传页面</title>
<%@include file="/commons/edit.jsp"%>
</head>

<body>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-next" onclick="submitZip">导入</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow('cancel')">关闭</a>
	</div>
	<form id="zipForm" action="${ctxPath}/oa/article/proArticle/importZip.do" method="post" enctype="multipart/form-data">
		<table cellpadding="0" cellspacing="0" class="table-detail column_2_m" style="padding: 6px;">
			<tr>
				<th>文档zip文件</th>
				<td><input id="zip" name="zip" type="file" />
				<input id="articleId" name="articleId" type="hidden"  value="${param.articleId}"/>
				<input id="itemId" name="itemId" type="hidden"  value="${param.itemId}"/></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();
		
		
		
		function submitZip(){
				// 提交表单
				$('#zipForm').ajaxSubmit(function(){
					CloseWindow('ok');
				});
			
		}
	</script>
</body>
</html>