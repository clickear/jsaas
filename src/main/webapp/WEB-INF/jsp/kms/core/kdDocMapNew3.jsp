<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建信息</title>
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctxPath}/scripts/cut/map/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/map/jquery-notes_1.0.8.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxPath}/scripts/cut/pic/normalize.css">
<script type="text/javascript" src="${ctxPath}/scripts/cut/pic/panzoom.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/cut/pic/jquery.mousewheel.js"></script>

<style>
.topButton {
	display: inline-block;
	line-height: 25px;
	height: 25px;
	width: 70px;
	background-color: #00BFFF;
	color: #fff;
	text-align: center;
	cursor: pointer;
	font-size: 9px;
	vertical-align: middle;
	border: 1px solid #0492C1;
	margin: 8px 5px 0 5px;
}

table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	font-weight: normal;
	text-align: right;
	font-size: 12px;
	color: #888;
}

table.gridtable td {
	border-width: 0px;
	padding: 8px;
	border-style: solid;
}

hr {
	border: 1px dashed #ccc;
	border-bottom: 0;
	border-right: 0;
	border-left: 0;
}
</style>
</head>
<body>
	<div style="float: right;">
		<a class="topButton">暂存</a>
	</div>
	<div style="clear: both;"></div>
	<hr style="height: 4px; border: none; border-top: 2px groove #CFCFCF;" />
	<div style="font-size: 10px; margin-left: 70px;">首页 &gt; 知识地图 &gt;</div>
	<div style="margin-left: 50px; margin-top: 10px;">
		<a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #39C150;" class="topButton">1 填写基本内容</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #39C150;" class="topButton">2 填写内容</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #1FC1FF;" class="topButton">3 完善属性</a> <a style="margin-left: 10px; line-height: 50px; height: 50px; width: 150px; background-color: #909090;" class="topButton">4 权限及流程</a>
	</div>

	<div style="float: right; margin-top: 10px; margin-right: 10%; background: #E9F0F1; height: 140px; width: 300px;">
		<div style="position: relative; padding: 0 20px; height: 35px; line-height: 35px; font-weight: bold; font-size: 16px; font-family: '微软雅黑'; color: #fff; background: rgba(58, 194, 239, 0.91); cursor: pointer; border-bottom: none">基本信息</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">
			创建者 ：<span style="color: #EA5252;">${userName}</span>
		</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">地图状态 ：草稿</div>
		<div style="font-size: 10px; margin-left: 10px; margin-top: 10px;">创建时间 ： <fmt:formatDate value="${kdDoc.createTime}" type="both"/></div>
	</div>
	<div style="height: 50px;"></div>

	<div style="margin-left: 100px;">
		<form id="form1" method="post">
		<input id="docId" name="docId" type="hidden" value="${param['docId']}" />
			<table class="gridtable" cellspacing="1" cellpadding="0">
				<tr>
					<th style="vertical-align: top;">地图热点区域：</th>
					<td colspan="3"> 
						<div>
							<section id="focal">
								<div class="parent">
									<div class="panzoom">
										<img src="${ctxPath}/sys/core/file/imageView.do?fileId=${kdDoc.coverImgId}" alt="" class="jquery-note"/>
									</div>
								</div>
							</section>	
						</div>	
					</td>
				</tr>
				<tr>
					<th style="vertical-align: top;">摘要 ：</th>
					<td style=""><textarea name="summary" style="height: 100px; width: 500px;">${kdDoc.summary}</textarea></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">标签：</th>
					<td><input name="tags" value="${kdDoc.tags}" type="text" style="width: 100%" /></td>
				</tr>
				<tr>
					<th style="vertical-align: top;">存放年限 ：</th>
					<td style=""><input id="storePeroid" name="storePeroid" value="${kdDoc.storePeroid}" class="mini-spinner" minValue="0" maxValue="20" />&nbsp;年（0年表示永不过期，地图到达过期时间后转为过期地图）</td>
				</tr>


			</table>
		</form>
	</div>
	<hr style="margin-top: 30px;">
	<div style="margin-top: 20px; margin-left: 40%;">
		<a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" href="${ctxPath}/kms/core/kdDoc/mapNew2.do?docId=${param['docId']}">上一步</a> <a class="topButton" style="height: 40px; width: 90px; line-height: 40px; text-decoration: none;" onclick="savePost3()">下一步</a>
	</div>
	<script>
        (function() {
          var $section = $('#focal');
          var $panzoom = $section.find('.panzoom').panzoom();
          $panzoom.parent().on('mousewheel.focal', function( e ) {
            e.preventDefault();
            var delta = e.delta || e.originalEvent.wheelDelta;
            var zoomOut = delta ? (delta < 0 ): (e.originalEvent.deltaY > 0);
            $panzoom.panzoom('zoom', zoomOut, {
              increment: 0.1,
              animate: false,
              focal: e
            });
          });
        })();
    </script>
	
	<script type="text/javascript">
		//var form = new mini.Form("form1");
		var kdDocId = "${param['docId']}";
		/* var kdDocCoverId= */
/* 		console.log(kdDocId);
		console.log("${kdDoc.storePeroid}"); */
		
/* 		$(function(){
			$('.jquery-note').attr("src");
			
		}); */
		
		
		
		$(function() {
		 	$('.jquery-note').jQueryNotes({
		 		allowHide:false,
		 		allowCounter:false,
				operator:"${ctxPath}/kms/core/kdDoc/getPoint.do"
			});
		});
		
		

/* 		  $(function() {
			    var spinner =$("#storePeroid").spinner();
		  }); */
		
		$(function() {
			var time = "${kdDoc.storePeroid}";
			var t = mini.get("storePeroid");
			//var t=$("#storePeroid");
			if (time == "") {
				t.setValue("0");
			}
		});
		
		//提交第三步表单
		function savePost3() {
			
			/* form.validate();
			if (!form.isValid()) {
				return;
			} */
			var formData = $("#form1").serializeArray();
			console.log(formData);
			$.ajax({
				type : "POST",
				url : __rootPath + '/kms/core/kdDoc/saveNew3.do',
				async : false,
				data : formData,
				success : function(result) {
					var docId = result;
					window.location.href = "${ctxPath}/kms/core/kdDoc/mapNew4.do?docId=" + docId;
				}
			});
		}
		
		function openDocsInNote(docIds){
			_OpenWindow({
				title:'文档列表',
				url:__rootPath+'/kms/core/kdDoc/docsInMapList.do?docIds='+docIds,
				width:350,
				height:250
			});
		}
	</script>
</body>
</html>