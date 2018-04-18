<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html >
<head>
	<title>表单打印</title>
	<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
	<script src="${ctxPath}/scripts/mini/boot.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
	<script type="text/javascript">
		var __rootPath='${ctxPath}';
	</script>
	<link href="${ctxPath}/styles/cover_list.css" rel="stylesheet" type="text/css" />
	<link href="${ctxPath}/styles/print.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		#form-panel>table > tbody>tr>th{
			width: 15%;
		}
		#form-panel>table > tbody>tr>td {
		    width: 35%;
		}
		
		#form-panel>table>caption,
		#form-panel>table th{
			background: transparent;
		}
		#form-panel>table>caption{
			padding-top: 0;
		}
		#form-panel{
			background: #fff;
/* 			box-shadow: none; */
		}
	</style>
	<script type="text/javascript">
		var formData={};
		function setData(data){
			formData=data;
			
			var url="${ctxPath}/bpm/form/bpmFormView/getPdfForm.do";
			var params={
					taskId:"${param['taskId']}",
					solId:"${param['solId']}",
					instId:"${param['instId']}",
					formAlias:"${param['formAlias']}",
					json:data
				}
			$.post(url,params,function(html){
				$("#form-panel").html(html);
			})
		}
		
		function downloadPdf(){
			$("#json").val(formData);
			$("#myForm")[0].submit();
		}
	</script>
</head>
<body>
	<div class="mini-toolbar noPrint topBtn">
		<a class="mini-button" iconCls="icon-print" onclick="Print();" plain="true">打印</a>
<!-- 		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow();" plain="true">关闭</a> -->
		<a class="mini-button" iconCls="icon-download" onclick="downloadPdf();" plain="true">下载</a>
		<form action="${ctxPath}/bpm/form/bpmFormView/downloadPdf.do" id="myForm" method="post" style="display:hidden">
			<input type="hidden" name="taskId" value="${param['taskId']}"/>
			<input type="hidden" name="solId" value="${param['solId']}"/>
			<input type="hidden" name="instId" value="${param['instId']}"/>
			<input type="hidden" name="formAlias" value="${param['formAlias']}"/>
			<input type="hidden" id="json" name="json"/>
		</form>
	</div>
	<div id="form-panel" class="shadowBox90">
	</div>
	<script type="text/javascript">
		addBody();
	</script>
</body>
</html>