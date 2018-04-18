<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>新建信息</title>
<%@include file="/commons/edit.jsp"%>

<style>
*{font-family: '微软雅黑'}
.topButton {
	display: inline-block;
	line-height: 25px;
	height: 25px;
	padding: 0 10px; 
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 14px;
	margin: 8px 5px 0 5px;
	border-radius: 4px;
}

table.gridtable{
	font-size: 14px;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 0px;
	padding: 8px;
	font-weight: normal;
	font-size: 14px;
	color: #666;
}

.stepBox{
	padding-left: 70px;
	box-sizing: border-box;
}

.stepBox span{
	float: left;
/* 	width: 60px; */
	padding: 10px;
	border-radius: 4px;
	background: #00BFFF;
	color: #fff;
	margin-right: 8px;
	opacity: .5;
	font-size: 14px;
}

.stepBox .active{
	opacity: 1;
}

.stepBox .complete{
	background: #39C150;
	opacity: 1;
}

.contentBox{
	margin-top: 40px;
	border-bottom: 1px dashed #ccc;
}

.contentBox::after,
.stepBox::after{
	content: '';
	display: block;
	clear: both;
}

.shadowBox{
	float: left;
}
</style>
</head>
<body>
	<div style="float: right;">
		<a class="topButton" onclick="temporary()">暂存</a>
	</div>
	<div style="clear: both;"></div>
<!-- 	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" /> -->
	<div style="font-size: 10px; margin-left: 70px;">首页 > 知识仓库 ></div>
	<div class="stepBox">
		<span class="complete">1 填写基本内容</span> 
		<span class="active">2 填写内容</span>
		<span>3 完善属性</span>
		<span>4 权限及流程</span>
	</div>
<div class="contentBox">
	<div  style="float: right; margin-top: 10px; margin-right: 10%; background: #E9F0F1; height: 140px; width: 250px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;">${userName}</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">文档状态 ：草稿</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建时间 ：
			<fmt:formatDate value="${kdDoc.createTime}" type="both" />
		</div>
	</div>
<!-- 	<div style="height: 50px;"></div> -->

	<div class="shadowBox90" style="float: left;">
		<form id="form1" method="post">
			<input id="docId" name="docId" class="mini-hidden" value="${param['docId']}" />
			<table class="gridtable column_2" cellspacing="1" cellpadding="0">
				<tr>
					<th style="vertical-align: top;">知识正文内容</th>
					<td colspan="1"><ui:UEditor height="300" width="100%" name="content" id="content">${kdDoc.content}</ui:UEditor></td>
				</tr>
				<tr>
					<th>附件 </th>
					<td><input id="attFileids" name="attFileids" class="upload-panel" plugins="upload-panel" allowupload="true" label="附件" fname="fileNames" allowlink="true" zipdown="true" fileIds="${kdDoc.attFileids}" fileNames="${fileNames}" /></td>
				</tr>

			</table>
		</form>
	</div>
	
	
	</div>
<!-- 	<hr style="margin-top: 30px;"> -->
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" href="${ctxPath}/kms/core/kdDoc/new1.do?docId=${param['docId']}">上一步</a> <a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" onclick="savePost2()">下一步</a>
	</div>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		var kdDocId = "${param['docId']}";
		console.log(kdDocId);
		function savePost2() {
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			$.ajax({
				type : "POST",
				url : __rootPath + '/kms/core/kdDoc/saveNew2.do',
				async : false,
				data : formData,
				success : function(result) {
					var docId = result;
					window.location.href = "${ctxPath}/kms/core/kdDoc/new3.do?docId=" + docId;
				}
			});
		}
		
		function temporary(){
			form.validate();
			if (!form.isValid()) {
				return;
			}
			var formData = $("#form1").serializeArray();
			$.ajax({
				type : "POST",
				url : __rootPath + '/kms/core/kdDoc/saveNew2.do',
				async : false,
				data : formData,
				success : function(result) {
					alert("知识文档已暂存，可以在个人中心中继续编辑！");
					CloseWindow();
				}
			});
		}
		
		//上传附件
		$(function() {
			$('.upload-panel').each(function() {
				$(this).uploadPanel();
			});
		});

	</script>
</body>
</html>