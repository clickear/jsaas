<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案导入</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<table cellpadding="0" cellspacing="0" class="table-detail" style="padding:6px;">
		<caption>第二步：流程导入结果</caption>
		<tr>
			<td>
				<c:choose>
					<c:when test="${empty msgSet }">
						成功!	
					</c:when>
					<c:otherwise>
						<ul>
						 <c:forEach items="${msgSet}" var="msg">
						 	<li>${msg}</li>
					     </c:forEach>
					    </ul> 
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
	
	<script type="text/javascript">
		mini.parse();
	</script>
</body>
</html>